package mn.mcs.electronics.court.components.excelImport;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.CommandSubject;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.Vacation;
import mn.mcs.electronics.court.entities.employee.VacationType;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.ChuluuTimeType;
import mn.mcs.electronics.court.enums.YesNo;
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

public class CompExcelVacation {

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
	private Vacation vacation;

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

					vacation = new Vacation();

					this.importExcel(row);

					if (isAdd) {
						dao.saveOrUpdateObject(vacation);
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
		manager.alert(Duration.SINGLE, Severity.INFO, messages.get("saved_vacation_num") + savedEmp);
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

						/* Чөлөө олгосон ажилтан */
						if (employee != null) {
							vacation.setEmployee(employee);
							try {
								CommandSubject commandSubject = dao.getCommandSubjectByName(obj.toString());
								if (commandSubject != null) {
									vacation.setCommandSubject(commandSubject);

								}
							} catch (Exception ex) {
								System.err.println(ex);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						if (vacation.getCommandSubject() == null) {
							isAdd = false;

							importUtil.regErrMsg("Чөлөө олгосон ажилтан талбар буруу байна", row.getRowNum() + 1,
									cellIndex + 1);
						}
						break;

					case 2:
						/* Чөлөө хүсэх төрөл */
						if (employee != null) {
							vacation.setEmployee(employee);
							try {

								VacationType vacationType = dao.getVacationTypeByName(obj.toString());
								if (vacationType != null) {
									vacation.setVacationType(vacationType);

								}
							} catch (Exception ex) {
								System.err.println(ex);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						if (vacation.getVacationType() == null) {
							isAdd = false;

							importUtil.regErrMsg("Чөлөө хүсэх төрөл талбар буруу байна", row.getRowNum() + 1,
									cellIndex + 1);
						}
						break;

					case 3:
						/* Чөлөөний хугацааны төрөл */
						if (employee != null) {
							vacation.setEmployee(employee);
							
							if (obj.getClass().getSimpleName().equals("String")) {

								if (String.valueOf(obj).contains("Урт хугацааны")) {
									vacation.setChuluuTimeType(ChuluuTimeType.LONGTIME);
								} else if (String.valueOf(obj).contains("Богино хугацааны")) {
									vacation.setChuluuTimeType(ChuluuTimeType.SHORTTIME);
								} else {
									isAdd = false;
									importUtil.regErrMsg("Чөлөөний хугацааны төрөл талбар тохирохгүй байна",
											row.getRowNum() + 1, cellIndex + 1);
								}

							} else {
								isAdd = false;
								importUtil.regErrMsg("Чөлөөний хугацааны төрөл талбарын төрөл талбар буруу байна - ",
										row.getRowNum() + 1, cellIndex + 1);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						if (vacation.getChuluuTimeType() == null) {
							isAdd = false;

							importUtil.regErrMsg("Чөлөөний хугацааны төрөл талбар буруу байна", row.getRowNum() + 1,
									cellIndex + 1);
						}
						break;

					case 4:

						/* Цалинтай эсэх */
						if (employee != null) {
							vacation.setEmployee(employee);
							if (obj.getClass().getSimpleName().equals("String")) {

								if (String.valueOf(obj).contains("Цалинтай")) {
									vacation.setHasSalary(YesNo.YES);
								} else if (String.valueOf(obj).contains("Цалингүй")) {
									vacation.setHasSalary(YesNo.NO);
								} else {
									isAdd = false;
									importUtil.regErrMsg("Цалинтай эсэх талбар тохирохгүй байна", row.getRowNum() + 1,
											cellIndex + 1);
								}

							} else {
								isAdd = false;
								importUtil.regErrMsg("Цалинтай эсэх талбарын төрөл талбар буруу байна",
										row.getRowNum() + 1, cellIndex + 1);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						if (vacation.getHasSalary() == null) {
							isAdd = false;

							importUtil.regErrMsg("Цалинтай эсэх талбар буруу байна", row.getRowNum() + 1,
									cellIndex + 1);
						}
						break;

					case 5:
						/* Чөлөө олгосон огноо */

						if (employee != null) {
							vacation.setEmployee(employee);
							try {
								if (obj.getClass().getSimpleName().equals("Date")) {
									vacation.setChuluuOlgosonOgnoo((Date) obj);
								} else if (obj.getClass().getSimpleName().equals("String")) {
									Date chuluuOlgosonOgnoo = importUtil.getDateFromString((String) obj);
									vacation.setChuluuOlgosonOgnoo(chuluuOlgosonOgnoo);

								}
							} catch (Exception ex) {
								System.err.println(ex);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						if (vacation.getChuluuOlgosonOgnoo() == null) {
							isAdd = false;

							importUtil.regErrMsg(
									"Чөлөө олгосон огноо талбар буруу байна - "
											+ (employee != null ? employee.getRegisterNo() : ""),
									row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 6:
						/* Чөлөө эхлэх огноо */

						if (employee != null) {
							vacation.setEmployee(employee);
							try {
								if (obj.getClass().getSimpleName().equals("Date")) {
									vacation.setChuluuEhlehOgnoo((Date) obj);
								} else if (obj.getClass().getSimpleName().equals("String")) {
									Date chuluuOlgosonOgnoo = importUtil.getDateFromString((String) obj);
									vacation.setChuluuEhlehOgnoo(chuluuOlgosonOgnoo);

								}
							} catch (Exception ex) {
								System.err.println(ex);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						if (vacation.getChuluuEhlehOgnoo() == null) {
							isAdd = false;

							importUtil.regErrMsg(
									"Чөлөө эхлэх огноо талбар буруу байна - "
											+ (employee != null ? employee.getRegisterNo() : ""),
									row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 7:
						/* Чөлөө дуусах огноо */
						if (employee != null) {
							vacation.setEmployee(employee);
							try {
								if (obj.getClass().getSimpleName().equals("Date")) {
									vacation.setChuluuDuusahOgnoo((Date) obj);
								} else if (obj.getClass().getSimpleName().equals("String")) {
									Date chuluuOlgosonOgnoo = importUtil.getDateFromString((String) obj);
									vacation.setChuluuDuusahOgnoo(chuluuOlgosonOgnoo);

								}
							} catch (Exception ex) {
								System.err.println(ex);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						if (vacation.getChuluuDuusahOgnoo() == null) {
							isAdd = false;

							importUtil.regErrMsg(
									"Чөлөө дуусах огноо талбар буруу байна - "
											+ (employee != null ? employee.getRegisterNo() : ""),
									row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 8:

						/* Тайлбар */
						if (employee != null) {
							vacation.setEmployee(employee);
							try {
								vacation.setVacationCause(obj.toString());
							} catch (Exception ex) {
								System.err.println(ex);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						if (vacation.getVacationCause() == null) {
							isAdd = false;

							importUtil.regErrMsg("Тайлбар талбар буруу байна", row.getRowNum() + 1, cellIndex + 1);
						}
						break;

					}
				} else {
					if (cellIndex == 0 || cellIndex == 1 || cellIndex == 2 || cellIndex == 3 || cellIndex == 4
							|| cellIndex == 5 || cellIndex == 6 || cellIndex == 7) {
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
