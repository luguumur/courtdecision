package mn.mcs.electronics.court.components.excelImport;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.Appointment;
import mn.mcs.electronics.court.entities.configuration.CommandSubject;
import mn.mcs.electronics.court.entities.employee.Displacement;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.Experience;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.EmployeeStatus;
import mn.mcs.electronics.court.enums.MilitaryOrSimple;
import mn.mcs.electronics.court.enums.OrganizationTypeExperience;
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

public class CompExcelExperience {

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
	private Experience experience;
	private Displacement occupation;

	void beginRender() {

	}

	/** EXCEL-ээс оруулах **/
	@CommitAfter
	void onSelectedFromImportFromExcel() throws Exception {

		String extension = fileEx.getFileName().substring(
				fileEx.getFileName().length() - 3,
				fileEx.getFileName().length());

		if (!extension.equals("xls"))
			manager.alert(Duration.SINGLE, Severity.ERROR,
					messages.get("pleaseInsertXSL"));
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
		int savedOcc = 0;

		try {
			fis = fileEx.getStream();

			HSSFWorkbook wb = new HSSFWorkbook(fis);

			HSSFSheet sheet = wb.getSheetAt(0);

			Iterator rows = sheet.rowIterator();

			while (rows.hasNext()) {

				HSSFRow row = (HSSFRow) rows.next();

				isAdd = true;

				if (row.getRowNum() >= 1) {

					experience = new Experience();
					occupation = new Displacement();

					this.importExcel(row);

					if (isAdd) {
						dao.saveOrUpdateObject(experience);
						savedEmp++;
						if (occupation.getOrganization() != null) {
							dao.saveOrUpdateObject(occupation);
							savedOcc++;
							System.err.println("savedOcc: " + savedOcc);
						}

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
		manager.alert(Duration.SINGLE, Severity.INFO,
				messages.get("saved_experience_num") + savedEmp
						+ " Occupation: " + savedOcc);
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
							employee = dao
									.getEmployeeByOrg(
											organization.getId(),
											Integer.parseInt(obj
													.toString()
													.substring(
															0,
															(obj.toString()
																	.contains(".")) ? obj
																	.toString()
																	.indexOf(
																			".")
																	: obj.toString()
																			.length())));
						} catch (NumberFormatException ex) {
							System.err.println("№ талбар: " + ex);
						} catch (Exception ex) {
							System.err.println("№ талбар: " + ex);
						}

						break;

					case 1:
						/* Статус */
						if (employee != null) {
							try {
								if (String.valueOf(obj).equalsIgnoreCase(
										messages.get("TIRED"))) {
									experience.setEmployee(employee);
									experience.setStatus(EmployeeStatus.TIRED);
								} else if (String.valueOf(obj)
										.equalsIgnoreCase(
												messages.get("WORKING"))) {
									experience.setEmployee(employee);
									experience
											.setStatus(EmployeeStatus.WORKING);
									occupation.setEmployee(employee);
									occupation.setIsNowDisplacement(true);

								}
							} catch (Exception ex) {
								System.err.println(ex);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй",
									row.getRowNum() + 1, cellIndex + 1);
						}

						if (experience.getStatus() == null) {
							isAdd = false;

							importUtil.regErrMsg("Статус", row.getRowNum() + 1,
									cellIndex + 1);
						}
						break;
					case 2:
						/* Байгууллагын төрөл */
						if (employee != null) {
							try {
								experience.setEmployee(employee);
								if (String.valueOf(obj).equalsIgnoreCase(
										messages.get("HUVIIN"))) {
									experience
											.setOrganizationtype(OrganizationTypeExperience.HUVIIN);
								} else if (String.valueOf(obj)
										.equalsIgnoreCase(
												messages.get("ULSIIN"))) {
									experience
											.setOrganizationtype(OrganizationTypeExperience.ULSIIN);

								} else if (String
										.valueOf(obj)
										.equalsIgnoreCase(messages.get("SHUUH"))) {
									experience
											.setOrganizationtype(OrganizationTypeExperience.SHUUH);

								}
							} catch (Exception ex) {
								System.err.println(ex);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй",
									row.getRowNum() + 1, cellIndex + 1);
						}

						if (experience.getOrganizationtype() == null) {
							isAdd = false;

							importUtil.regErrMsg("Байгууллагын төрөл",
									row.getRowNum() + 1, cellIndex + 1);
						}
						break;

					case 3:
						/* Байгууллагын нэр */
						if (employee != null) {
							experience.setEmployee(employee);
							try {
								experience.setOrganizationname(obj.toString());
								if (experience.getStatus() == EmployeeStatus.WORKING
										&& experience.getOrganizationtype() == OrganizationTypeExperience.SHUUH) {
									occupation.setEmployee(employee);
									occupation.setOrganization(organization);
								}
							} catch (Exception ex) {
								System.err.println(ex);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй",
									row.getRowNum() + 1, cellIndex + 1);
						}

						if (experience.getOrganizationname() == null) {
							isAdd = false;

							importUtil.regErrMsg("Байгууллагын нэр",
									row.getRowNum() + 1, cellIndex + 1);
						}
						break;

					case 4:
						/* Албан тушаал */

						if (employee != null) {
							experience.setEmployee(employee);
							try {
								experience.setAppointment(obj.toString());
								if (experience.getStatus() == EmployeeStatus.WORKING) {
									occupation.setEmployee(employee);
									occupation.setIsNowDisplacement(true);
									Appointment appointment = dao
											.getAppointmentByName(Long
													.parseLong(obj.toString()));

									if (appointment != null) {
										occupation.setAppointment(appointment);
									} else {
										isAdd = false;
										importUtil.regErrMsg(
												"Албан тушаал  олдсонгүй",
												row.getRowNum() + 1,
												cellIndex + 1);
									}

								}
							} catch (Exception ex) {
								System.err.println(ex);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй",
									row.getRowNum() + 1, cellIndex + 1);
						}

						if (experience.getAppointment() == null) {
							isAdd = false;

							importUtil.regErrMsg("Албан тушаал ",
									row.getRowNum() + 1, cellIndex + 1);
						}
						break;

					case 5:
						/* Ажиллах төлөв */

						if (employee != null) {
							try {
								experience.setEmployee(employee);
								if (String.valueOf(obj).contains(
										messages.get("workingSimple"))) {

									experience
											.setMilitaryOrSimple(MilitaryOrSimple.workingSimple);
								} else if (String.valueOf(obj).contains(
										messages.get("workingMilitary"))) {
									experience
											.setMilitaryOrSimple(MilitaryOrSimple.workingMilitary);

								}
							} catch (Exception ex) {
								System.err.println(ex);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй",
									row.getRowNum() + 1, cellIndex + 1);
						}

						if (experience.getMilitaryOrSimple() == null) {
							isAdd = false;

							importUtil.regErrMsg("Ажиллах төлөв",
									row.getRowNum() + 1, cellIndex + 1);
						}
						break;

					case 6:
						/* Томилогдсон огноо */

						if (employee != null) {
							experience.setEmployee(employee);
							try {
								if (obj.getClass().getSimpleName()
										.equals("Date")) {
									experience.setStartDate((Date) obj);
									if (experience.getStatus() == EmployeeStatus.WORKING
											&& experience.getOrganizationtype() == OrganizationTypeExperience.SHUUH) {
										occupation.setEmployee(employee);
										occupation.setIssuedDate((Date) obj);

									}
								} else if (obj.getClass().getSimpleName()
										.equals("String")) {
									Date createdDate = importUtil
											.getDateFromString((String) obj);

									experience.setStartDate(createdDate);
									if (experience.getStatus() == EmployeeStatus.WORKING
											&& experience.getOrganizationtype() == OrganizationTypeExperience.SHUUH) {
										occupation.setEmployee(employee);
										occupation.setIssuedDate(createdDate);

									}
								}

							} catch (Exception ex) {
								System.err.println(ex);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй",
									row.getRowNum() + 1, cellIndex + 1);
						}

						if (experience.getStartDate() == null) {
							isAdd = false;

							importUtil.regErrMsg("Томилогдсон огноо",
									row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 7:
						/* Тушаалын дугаар */
						if (employee != null) {
							experience.setEmployee(employee);
							try {
								experience.setCommandNumber(obj.toString());
							} catch (Exception ex) {
								System.err.println(ex);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй",
									row.getRowNum() + 1, cellIndex + 1);
						}

						if (experience.getCommandNumber() == null) {
							isAdd = false;

							importUtil.regErrMsg("Тушаалын дугаар",
									row.getRowNum() + 1, cellIndex + 1);
						}
						break;

					case 8:
						/* Тушаал гаргасан огноо */
						if (employee != null) {
							experience.setEmployee(employee);
							if (obj.getClass().getSimpleName().equals("Date")) {
								experience.setCommandStartDate((Date) obj);
							} else if (obj.getClass().getSimpleName()
									.equals("String")) {
								Date commandDate = importUtil
										.getDateFromString((String) obj);
								experience.setCommandStartDate(commandDate);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй",
									row.getRowNum() + 1, cellIndex + 1);
						}

						if (experience.getCommandStartDate() == null) {
							isAdd = false;

							importUtil.regErrMsg("Тушаал гаргасан огноо",
									row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 9:
						/* Тушаал гаргасан субьект */

						if (employee != null) {
							experience.setEmployee(employee);
							try {
								CommandSubject commandSubject = dao
										.getCommandSubjectByName(obj.toString());

								if (commandSubject != null) {
									experience
											.setCommandSubject(commandSubject);
								}

							} catch (Exception ex) {
								System.err.println(ex);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй",
									row.getRowNum() + 1, cellIndex + 1);
						}

						if (experience.getCommandSubject() == null) {
							isAdd = false;

							importUtil.regErrMsg("Тушаал гаргасан субьект ",
									row.getRowNum() + 1, cellIndex + 1);
						}
						break;

					case 10:
						/*
						 * Тушаал гаргасан субъект /Бусад - гэдгийг сонгосон
						 * тохиолдолд бичнэ/
						 */
						if (employee != null) {

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй",
									row.getRowNum() + 1, cellIndex + 1);
						}
						break;

					case 11:
						/* Чөлөөлөгдсөн огноо */

						if (employee != null) {
							experience.setEmployee(employee);
							if (obj.getClass().getSimpleName().equals("Date")) {
								experience.setEndDate((Date) obj);
							} else if (obj.getClass().getSimpleName()
									.equals("String")) {
								Date endDate = importUtil
										.getDateFromString((String) obj);
								experience.setEndDate(endDate);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй",
									row.getRowNum() + 1, cellIndex + 1);
						}

						if (experience.getEndDate() == null) {
							isAdd = false;

							importUtil.regErrMsg("Чөлөөлөгдсөн огноо",
									row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 12:
						/* Чөлөөлөх тушаал гарсан огноо */
						if (employee != null) {
							experience.setEmployee(employee);
							if (obj.getClass().getSimpleName().equals("Date")) {
								experience.setCommandEndDate((Date) obj);
							} else if (obj.getClass().getSimpleName()
									.equals("String")) {
								Date endDate = importUtil.getDateFromString(obj
										.toString());
								experience.setCommandEndDate(endDate);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй",
									row.getRowNum() + 1, cellIndex + 1);
						}

						if (experience.getCommandEndDate() == null) {
							isAdd = false;

							importUtil.regErrMsg(
									"Чөлөөлөх тушаал гарсан огноо",
									row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					}
				} else {
					if (cellIndex == 0 || cellIndex == 1 || cellIndex == 2
							|| cellIndex == 3 || cellIndex == 4
							|| cellIndex == 5 || cellIndex == 6) {
						importUtil.regErrMsg(
								"Заавал оруулах талбар оруулаагүй байна",
								row.getRowNum() + 1, cellIndex + 1);
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
