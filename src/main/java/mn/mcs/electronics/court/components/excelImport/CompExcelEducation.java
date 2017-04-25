package mn.mcs.electronics.court.components.excelImport;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.City;
import mn.mcs.electronics.court.entities.configuration.Country;
import mn.mcs.electronics.court.entities.configuration.DegreeType;
import mn.mcs.electronics.court.entities.configuration.Occupation;
import mn.mcs.electronics.court.entities.configuration.School;
import mn.mcs.electronics.court.entities.employee.Educations;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.SchoolType;
import mn.mcs.electronics.court.enums.UniversityType;
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

public class CompExcelEducation {

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

	@Persist
	private Employee employee;

	@Persist
	private Educations education;

	/*
	 * PROPERTIES
	 */
	@Property
	private String strRow;

	private ImportUtil importUtil;
	private Boolean isAdd;

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

					education = new Educations();

					this.importExcel(row);

					if (isAdd) {
						dao.saveOrUpdateObject(education);
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
		manager.alert(Duration.SINGLE, Severity.INFO, "Хадгалагдсан сургуулийн тоо : " + savedEmp);
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
						if (employee != null && employee.getId() != null) {
							try {
								education.setEmployee(employee);

								Country country = dao.getCountryByName(obj.toString());

								if (country != null)
									education.setCountry(country);

								if (education.getCountry() == null) {
									isAdd = false;

									importUtil.regErrMsg("Улс талбар буруу байна - " + employee.getEmpNumber(),
											row.getRowNum() + 1, cellIndex + 1);
								}

							} catch (Exception ex) {
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}
						break;

					case 2:
						if (employee != null && employee.getId() != null) {
							try {

								City city = dao.getCityByName(String.valueOf(obj));

								if (city != null)
									education.setCity(city);

								if (education.getCity() == null) {
									isAdd = false;

									importUtil.regErrMsg("Хот аймаг талбар буруу байна - " + employee.getEmpNumber(),
											row.getRowNum() + 1, cellIndex + 1);
								}

							} catch (Exception ex) {
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 3:
						if (employee != null && employee.getId() != null) {
							try {
								if (obj.getClass().getSimpleName().equals("String")) {
									if (String.valueOf(obj).equalsIgnoreCase(messages.get("PRIMARYSCHOOL"))) {
										education.setSchoolType(SchoolType.PRIMARYSCHOOL);
									} else if (String.valueOf(obj).equalsIgnoreCase(messages.get("MSUT"))) {
										education.setSchoolType(SchoolType.MSUT);
									} else if (String.valueOf(obj).equalsIgnoreCase(messages.get("COLLEGE"))) {
										education.setSchoolType(SchoolType.COLLEGE);
									} else if (String.valueOf(obj).equalsIgnoreCase(messages.get("UNIVERSITY"))) {
										education.setSchoolType(SchoolType.UNIVERSITY);
									}
								} else {
									if (education.getSchoolType() == null) {
										isAdd = false;

										importUtil.regErrMsg(
												"Сургуулийн төрөл талбар буруу байна - " + employee.getEmpNumber(),
												row.getRowNum() + 1, cellIndex + 1);
									}
								}

							} catch (Exception ex) {
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 4:
						if (employee != null && employee.getId() != null) {
							try {
								if (obj.getClass().getSimpleName().equals("String")) {
									if (String.valueOf(obj).equalsIgnoreCase(messages.get("TuriinSurguuli"))) {
										education.setUniversityType(UniversityType.TuriinSurguuli);
									} else if (String.valueOf(obj).equalsIgnoreCase(messages.get("HuviinSurguuli"))) {
										education.setUniversityType(UniversityType.HuviinSurguuli);
									}
								} else {
									if (education.getUniversityType() == null) {
										isAdd = false;

										importUtil.regErrMsg(
												"Их сургуулийн төрөл талбар буруу байна - " + employee.getEmpNumber(),
												row.getRowNum() + 1, cellIndex + 1);
									}
								}
							} catch (Exception ex) {
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					// case 5:
					// if (employee != null && employee.getId() != null) {
					// try {
					//
					// School school = dao.getSchoolByName(String.valueOf(obj));
					//
					// if (school != null) {
					// education.setUniversity(school);
					// } else {
					// education.setSchool(String.valueOf(obj));
					// }
					//
					// if (education.getSchool() == null &&
					// education.getUniversity() == null) {
					// isAdd = false;
					// importUtil.regErrMsg("Сургууль талбар буруу байна - " +
					// employee.getEmpNumber(),
					// row.getRowNum() + 1, cellIndex + 1);
					// }
					//
					// } catch (Exception ex) {
					// }
					//
					// } else {
					// isAdd = false;
					// importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum()
					// + 1, cellIndex + 1);
					// }
					// break;

					case 5:
						if (employee != null && employee.getId() != null) {
							try {
								education.setIngoingDate(
										Integer.parseInt(obj.toString().substring(0, (obj.toString().contains("."))
												? obj.toString().indexOf(".") : obj.toString().length())));

								if (education.getIngoingDate() == null) {
									isAdd = false;
									importUtil.regErrMsg("Элссэн он талбар буруу байна - " + employee.getEmpNumber(),
											row.getRowNum() + 1, cellIndex + 1);
								}

							} catch (NumberFormatException ex) {

							} catch (Exception ex) {
							}

						} else {
							isAdd = false;
							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 6:
						if (employee != null && employee.getId() != null) {
							try {
								education.setGraduatedDate(
										Integer.parseInt(obj.toString().substring(0, (obj.toString().contains("."))
												? obj.toString().indexOf(".") : obj.toString().length())));

								if (education.getGraduatedDate() == null) {
									isAdd = false;
									importUtil.regErrMsg("Төгссөн он талбар буруу байна - " + employee.getEmpNumber(),
											row.getRowNum() + 1, cellIndex + 1);
								}

							} catch (NumberFormatException ex) {

							} catch (Exception ex) {
							}

						} else {
							isAdd = false;
							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 7:
						if (employee != null && employee.getId() != null) {
							try {
								DegreeType degree = dao.getDegreeTypeName(String.valueOf(obj));

								if (degree != null)
									education.setDegreeType(degree);

								if (education.getDegreeType() == null) {
									isAdd = false;

									importUtil.regErrMsg(
											"Эзэмшсэн боловсрол талбар буруу байна - " + employee.getEmpNumber(),
											row.getRowNum() + 1, cellIndex + 1);
								}

							} catch (Exception ex) {
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 8:
						if (employee != null && employee.getId() != null) {
							try {

								Occupation occupation = dao.getOccupationName(String.valueOf(obj));

								if (occupation != null)
									education.setOccupation(occupation);

								if (education.getOccupation() == null) {
									isAdd = false;

									importUtil.regErrMsg("Мэргэжил талбар буруу байна - " + employee.getEmpNumber(),
											row.getRowNum() + 1, cellIndex + 1);
								}

							} catch (Exception ex) {
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 9:
						if (employee != null && employee.getId() != null) {
							try {
								education.setDiplomaNo(String.valueOf(obj));

								if (education.getDiplomaNo() == null) {
									isAdd = false;
									importUtil.regErrMsg(
											"Дипломын дугаар талбар буруу байна - " + employee.getEmpNumber(),
											row.getRowNum() + 1, cellIndex + 1);
								}

							} catch (Exception ex) {
							}

						} else {
							isAdd = false;
							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						break;
					}

				} else {
					if (cellIndex == 0 || cellIndex == 1 || cellIndex == 2) {
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
