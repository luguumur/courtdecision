package mn.mcs.electronics.court.components.excelImport;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.ForeignLanguage;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.EmployeeMilitary;
import mn.mcs.electronics.court.entities.employee.Language;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.LanguageLevel;
import mn.mcs.electronics.court.util.ExcelAPI;
import mn.mcs.electronics.court.util.ImportUtil;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.upload.services.UploadedFile;

public class CompExcelLanguage {

	/*
	 * INJECTS
	 */

	@Inject
	private Request request;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@Inject
	private CoreDAO dao;

	@Inject
	private AlertManager manager;

	@Inject
	private Messages messages;

	/*
	 * PERSISTS
	 */

	@Persist
	private Organization organization;

	@Persist
	@Property
	private UploadedFile fileEx;

	@Persist("flash")
	@Property
	private List<String> errMsg;

	/*
	 * PROPERTIES
	 */
	@Property
	private String strRow;

	private ImportUtil importUtil;
	private Boolean isAdd;
	private Employee employee;
	private Language empLanguage;

	void beginRender() {

	}

	/** EXCEL-ээс оруулах **/
	@CommitAfter
	void onSelectedFromImportFromExcel() throws Exception {

		String extension = fileEx.getFileName().substring(fileEx.getFileName().length() - 3,
				fileEx.getFileName().length());

		if (!extension.equals("xls"))
			manager.alert(Duration.SINGLE, Severity.ERROR, messages.get("pleaseInsertXSL"));
		else {

			importExcel();
		}
	}

	public void onActionFromCancelImport() {
		fileEx = null;
	}

	void importExcel() throws IOException {

		InputStream fis = null;

		importUtil = new ImportUtil();

		int savedEmp = 0;

		try {
			fis = fileEx.getStream();

			HSSFWorkbook wb = new HSSFWorkbook(fis);

			HSSFSheet sheet = wb.getSheetAt(0);

			Iterator rows = sheet.rowIterator();

			while (rows.hasNext()) {

				HSSFRow row = (HSSFRow) rows.next();

				isAdd = true;

				if (row.getRowNum() >= 1) {

					empLanguage = new Language();

					this.importExcel(row);

					if (isAdd) {
						dao.saveOrUpdateObject(empLanguage);
						savedEmp++;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				fis.close();
			}

		}

		errMsg = importUtil.getErrMsgs();
		manager.alert(Duration.SINGLE, Severity.INFO, "Хадгалагдсан тоо" + savedEmp);
	}

	void importExcel(HSSFRow row) {

		if (organization != null && organization.getId() != null) {
			Iterator cells = row.cellIterator();
			Object obj = null;
			while (cells.hasNext()) {
				HSSFCell cell = (HSSFCell) cells.next();
				int cellIndex = cell.getCellNum();
				obj = ExcelAPI.getCellValue(cell);
				if (obj != null) {
					switch (cellIndex) {
					case 0:
						try {
							employee = dao.getEmployeeByOrg(organization.getId(),
									Integer.parseInt(obj.toString().substring(0, (obj.toString().contains("."))
											? obj.toString().indexOf(".") : obj.toString().length())));
						} catch (NumberFormatException ex) {
							System.err.println("№: " + ex);
						} catch (Exception ex) {
							System.err.println(ex);
						}
						break;
					case 1:
						// Language name
						if (employee != null && employee.getId() != null) {

							try {
								if (obj.getClass().getSimpleName().equals("String")) {
									if (empLanguage != null)
										empLanguage.setEmployee(employee);

									ForeignLanguage language = dao.getListForeignLanguage(String.valueOf(obj));

									if (language != null) {
										empLanguage.setForeignLanguage(language);
									}
								} else {
									if (empLanguage.getForeignLanguage() == null) {
										isAdd = false;

										importUtil.regErrMsg("Гадаад хэлний нэр ", row.getRowNum() + 1, cellIndex + 1);
									}
								}

							} catch (Exception ex) {
								System.err.println(ex);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 2:

						// Listening
						if (employee != null && employee.getId() != null) {

							try {
								if (empLanguage != null)
									empLanguage.setEmployee(employee);

								if (String.valueOf(obj)
										.equalsIgnoreCase(messages.get(getLangLevel(String.valueOf(obj)).toString()))) {
									empLanguage.setListening(getLangLevel(String.valueOf(obj)));
								}
							} catch (Exception ex) {
								System.err.println(ex);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						if (empLanguage.getListening() == null) {
							isAdd = false;

							importUtil.regErrMsg("Сонсох чадвар", row.getRowNum() + 1, cellIndex + 1);
						}

						break;
					case 3:
						// Speaking
						if (employee != null && employee.getId() != null) {

							try {

								if (empLanguage != null)
									empLanguage.setEmployee(employee);
								if (String.valueOf(obj)
										.equalsIgnoreCase(messages.get(getLangLevel(String.valueOf(obj)).name()))) {
									empLanguage.setSpeaking(getLangLevel(String.valueOf(obj)));
								}
							} catch (Exception ex) {
								System.err.println(ex);
							}
						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						if (empLanguage.getSpeaking() == null) {
							isAdd = false;

							importUtil.regErrMsg("Ярих чадвар", row.getRowNum() + 1, cellIndex + 1);
						}

						break;
					case 4:
						// Reading
						if (employee != null && employee.getId() != null) {

							try {

								if (empLanguage != null)
									empLanguage.setEmployee(employee);

								if (String.valueOf(obj)
										.equalsIgnoreCase(messages.get(getLangLevel(String.valueOf(obj)).name()))) {
									empLanguage.setReading(getLangLevel(String.valueOf(obj)));
								}
							} catch (Exception ex) {
								System.err.println(ex);
							}
						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						if (empLanguage.getReading() == null) {
							isAdd = false;

							importUtil.regErrMsg("Унших чадвар", row.getRowNum() + 1, cellIndex + 1);
						}

						break;
					case 5:
						// Reading
						if (employee != null && employee.getId() != null) {

							try {

								if (empLanguage != null)
									empLanguage.setEmployee(employee);

								if (String.valueOf(obj)
										.equalsIgnoreCase(messages.get(getLangLevel(String.valueOf(obj)).name()))) {
									empLanguage.setWriting(getLangLevel(String.valueOf(obj)));
								}
							} catch (Exception ex) {
								System.err.println(ex);
							}
						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						if (empLanguage.getWriting() == null) {
							isAdd = false;

							importUtil.regErrMsg("Бичих чадвар", row.getRowNum() + 1, cellIndex + 1);
						}

						break;
					}
				} else {
					if (cellIndex == 0 || cellIndex == 1) {
						importUtil.regErrMsg("Заавал оруулах талбар оруулаагүй байна", row.getRowNum() + 1,
								cellIndex + 1);

						isAdd = false;
					}
				}
			}
		}
	}

	public boolean tryParseInt(String value) {
		try {
			Long id = Long.parseLong(value);
		} catch (NumberFormatException nfe) {
			return false;
		}

		return true;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	private LanguageLevel getLangLevel(String level) {

		if (level == null)
			return null;

		if (level.equals(messages.get(LanguageLevel.EXCELLENT.name())))
			return LanguageLevel.EXCELLENT;

		if (level.equals(messages.get(LanguageLevel.GOOD.name())))
			return LanguageLevel.GOOD;

		if (level.equals(messages.get(LanguageLevel.MEDIUM.name())))
			return LanguageLevel.MEDIUM;

		if (level.equals(messages.get(LanguageLevel.BAD.name())))
			return LanguageLevel.BAD;

		return null;
	}
}
