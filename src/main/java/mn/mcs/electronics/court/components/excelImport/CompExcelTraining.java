package mn.mcs.electronics.court.components.excelImport;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.Country;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.Training;
import mn.mcs.electronics.court.entities.organization.Organization;
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

public class CompExcelTraining {

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
	private Training training;

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

					training = new Training();

					this.importExcel(row);

					if (isAdd) {
						dao.saveOrUpdateObject(training);
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
		manager.alert(Duration.SINGLE, Severity.INFO, messages.get("saved_trainig_num") + savedEmp);
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
							System.err.println("№ талбар: " + ex);
						} catch (Exception ex) {
							System.err.println("№ талбар: " + ex);
						}

						break;

					case 1:

						/* Улс */
						if (employee != null) {
							training.setEmployee(employee);
							try {

								Country country = dao.getCountryByName(obj.toString());
								if (country != null) {
									training.setCountry(country);

								}
							} catch (Exception ex) {
								System.err.println(ex);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						if (training.getCountry() == null) {
							isAdd = false;

							importUtil.regErrMsg(
									"Улс талбар буруу байна - " + (employee != null ? employee.getEmpNumber() : ""),
									row.getRowNum() + 1, cellIndex + 1);
						}
						break;

					case 2:

						/* Хот */
						if (employee != null) {
							training.setEmployee(employee);
							try {
								training.setCity(obj.toString());
							} catch (Exception ex) {
								System.err.println(ex);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						if (training.getCity() == null) {
							isAdd = false;

							importUtil.regErrMsg(
									"Хот талбар буруу байна - " + (employee != null ? employee.getRegisterNo() : ""),
									row.getRowNum() + 1, cellIndex + 1);
						}
						break;
					case 3:

						/* Сургалт явуулсан байгууллага */
						if (employee != null) {
							training.setEmployee(employee);
							try {
								training.setTrainingOrganization(obj.toString());
							} catch (Exception ex) {
								System.err.println(ex);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						if (training.getTrainingOrganization() == null) {
							isAdd = false;

							importUtil.regErrMsg(
									"Сургалт явуулсан байгууллага талбар буруу байна - "
											+ (employee != null ? employee.getRegisterNo() : ""),
									row.getRowNum() + 1, cellIndex + 1);
						}
						break;

					case 4:

						/* Эхэлсэн он */
						if (employee != null) {
							training.setEmployee(employee);
							Integer startYear = null;
							if (obj.getClass().getSimpleName().equals("String")) {
								if (tryParseInt((String) obj))
									startYear = Integer.valueOf((String) obj);

								training.setStartDate(startYear);

							} else if (obj.getClass().getSimpleName().equals("Double")) {
								startYear = importUtil.getInteger(obj);
								training.setStartDate(startYear);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						if (training.getStartDate() == null) {
							isAdd = false;

							importUtil.regErrMsg(
									"Эхэлсэн он талбар буруу байна - "
											+ (employee != null ? employee.getRegisterNo() : ""),
									row.getRowNum() + 1, cellIndex + 1);
						}
						break;
					case 5:

						/* Дууссан он */
						if (employee != null) {
							training.setEmployee(employee);
							Integer endYear = null;
							if (obj.getClass().getSimpleName().equals("String")) {
								if (tryParseInt((String) obj))
									endYear = Integer.valueOf((String) obj);

								training.setEndDate(endYear);

							} else if (obj.getClass().getSimpleName().equals("Double")) {
								endYear = importUtil.getInteger(obj);
								training.setEndDate(endYear);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						if (training.getStartDate() == null) {
							isAdd = false;

							importUtil.regErrMsg(
									"Дууссан он талбар буруу байна - "
											+ (employee != null ? employee.getRegisterNo() : ""),
									row.getRowNum() + 1, cellIndex + 1);
						}
						break;

					case 6:

						/* Чиглэл */
						if (employee != null) {
							training.setEmployee(employee);
							try {
								training.setSubject(obj.toString());
							} catch (Exception ex) {
								System.err.println(ex);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						if (training.getSubject() == null) {
							isAdd = false;

							importUtil.regErrMsg(
									"Чиглэл талбар буруу байна - " + (employee != null ? employee.getRegisterNo() : ""),
									row.getRowNum() + 1, cellIndex + 1);
						}
						break;

					case 7:
						/* Үнэмлэх олгосон огноо */

						if (employee != null) {
							training.setEmployee(employee);
							try {
								if (obj.getClass().getSimpleName().equals("Date")) {
									training.setGradeDate((Date) obj);
								} else if (obj.getClass().getSimpleName().equals("String")) {
									Date gradeDate = importUtil.getDateFromString((String) obj);
									training.setGradeDate(gradeDate);

								}
							} catch (Exception ex) {
								System.err.println(ex);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						if (training.getGradeDate() == null) {
							isAdd = false;

							importUtil.regErrMsg(
									"Үнэмлэх олгосон огноо талбар буруу байна - "
											+ (employee != null ? employee.getRegisterNo() : ""),
									row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					}
				} else {
					if (cellIndex == 0 || cellIndex == 1 || cellIndex == 2 || cellIndex == 3 || cellIndex == 4
							|| cellIndex == 5) {
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

}
