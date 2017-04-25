package mn.mcs.electronics.court.components.excelImport;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.City;
import mn.mcs.electronics.court.entities.configuration.DistrictSum;
import mn.mcs.electronics.court.entities.configuration.RelativeType;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.Relatives;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.Gender;
import mn.mcs.electronics.court.enums.RelativeFamily;
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

public class CompExcelFamily {

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
	private Relatives relative;

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

					relative = new Relatives();

					this.importExcel(row);

					if (isAdd) {
						dao.saveOrUpdateObject(relative);
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
		manager.alert(Duration.SINGLE, Severity.INFO, "Хадгалагдсан гэр бүлийн гишүүдийн тоо : " + savedEmp);
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
							// try {
							// if (relative != null)
							// relative.setEmployee(employee);
							//
							// if
							// (String.valueOf(obj).equalsIgnoreCase(messages.get("ISFAMILY")))
							// {
							//
							// relative.setRelativeType(RelativeFamily.ISFAMILY);
							//
							// } else if
							// (String.valueOf(obj).equalsIgnoreCase(messages.get("ISRELATIVE")))
							// {
							//
							// relative.setRelativeType(RelativeFamily.ISRELATIVE);
							// }
							//
							// } catch (Exception ex) {
							// System.err.println("Садан төрөл : " + ex);
							// }
							//
							// if (relative.getRelativeType() == null) {
							// isAdd = false;
							//
							// importUtil.regErrMsg("Садан төрөл талбар буруу
							// байна - " + employee.getEmpNumber(),
							// row.getRowNum() + 1, cellIndex + 1);
							// }
							if (obj.getClass().getSimpleName().equals("String")) {

								if (relative != null)
									relative.setEmployee(employee);

								if (String.valueOf(obj).equalsIgnoreCase(messages.get("ISFAMILY"))) {

									relative.setRelativeType(RelativeFamily.ISFAMILY);

								} else if (String.valueOf(obj).equalsIgnoreCase(messages.get("ISRELATIVE"))) {

									relative.setRelativeType(RelativeFamily.ISRELATIVE);
								}
							} else {
								isAdd = false;

								importUtil.regErrMsg("Садан төрөл талбар буруу байна - " + employee.getEmpNumber(),
										row.getRowNum() + 1, cellIndex + 1);
							}

							// } else {
							// isAdd = false;
							// importUtil.regErrMsg("Хүйс талбарын төрөл талбар
							// буруу байна - " + employee.getEmpNumber(),
							// row.getRowNum() + 1, cellIndex + 1);
							// }

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 2:
						if (employee != null && employee.getId() != null) {
							try {
								RelativeType type = dao.getRelativeTypeByName(String.valueOf(obj));

								if (type != null)
									relative.setRelation(type);

							} catch (Exception ex) {
								System.err.println("Юу нь болох: " + ex);
							}

							if (relative.getRelation() == null) {
								isAdd = false;

								importUtil.regErrMsg("Юу нь болох талбар буруу байна - " + employee.getEmpNumber(),
										row.getRowNum() + 1, cellIndex + 1);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 3:
						if (employee != null && employee.getId() != null) {
							try {
								relative.setLastName(String.valueOf(obj));
							} catch (Exception ex) {
								System.err.println("Овог: " + ex);
							}

							if (relative.getLastName() == null) {
								isAdd = false;
								importUtil.regErrMsg("Овог талбар буруу байна - " + employee.getEmpNumber(),
										row.getRowNum() + 1, cellIndex + 1);
							}

						} else {
							isAdd = false;
							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 4:
						if (employee != null && employee.getId() != null) {
							try {
								relative.setFirstName(String.valueOf(obj));
							} catch (Exception ex) {
								System.err.println("Нэр: " + ex);
							}

							if (relative.getFirstName() == null) {
								isAdd = false;
								importUtil.regErrMsg("Нэр талбар буруу байна - " + employee.getEmpNumber(),
										row.getRowNum() + 1, cellIndex + 1);
							}

						} else {
							isAdd = false;
							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 5:
						if (employee != null && employee.getId() != null) {

							try {
								relative.setBirthDate(
										Integer.parseInt(obj.toString().substring(0, (obj.toString().contains("."))
												? obj.toString().indexOf(".") : obj.toString().length())));

							} catch (NumberFormatException ex) {
								System.err.println("Төрсөн он: " + ex);

							} catch (Exception ex) {
								System.err.println("Төрсөн он: " + ex);
							}

							if (relative.getBirthDate() == null) {
								isAdd = false;
								importUtil.regErrMsg("Төрсөн он талбар буруу байна - " + employee.getEmpNumber(),
										row.getRowNum() + 1, cellIndex + 1);
							}

						} else {
							isAdd = false;
							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 6:
						if (employee != null && employee.getId() != null) {

							City aimagNiislel = dao.getCityByName(String.valueOf(obj));

							if (aimagNiislel != null) {
								relative.setBirthCityProvince(aimagNiislel);
							}

							if (relative.getBirthCityProvince() == null) {
								isAdd = false;
								importUtil.regErrMsg("Төрсөн хот,аймаг талбар буруу байна - " + employee.getEmpNumber(),
										row.getRowNum() + 1, cellIndex + 1);
							}

						} else {
							isAdd = false;
							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 7:
						if (employee != null && employee.getId() != null) {

							DistrictSum sumDuureg = dao.getDistrictSumByName(relative.getBirthCityProvince(),
									String.valueOf(obj));

							if (sumDuureg != null) {
								relative.setDistrictSum(sumDuureg);
							}

							if (relative.getDistrictSum() == null) {
								isAdd = false;
								importUtil.regErrMsg(
										"Төрсөн сум,дүүрэг талбар буруу байна - " + employee.getEmpNumber(),
										row.getRowNum() + 1, cellIndex + 1);
							}

						} else {
							isAdd = false;
							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 8:
						if (employee != null && employee.getId() != null) {
							try {
								relative.setOccupation(String.valueOf(obj));
							} catch (Exception ex) {
								System.err.println("Одоогийн ажил: " + ex);
							}

							if (relative.getOccupation() == null) {
								isAdd = false;
								importUtil.regErrMsg("Одоогийн ажил талбар буруу байна - " + employee.getEmpNumber(),
										row.getRowNum() + 1, cellIndex + 1);
							}

						} else {
							isAdd = false;
							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}
						break;

					}
				} else {
					if (cellIndex == 0 || cellIndex == 3 || cellIndex == 4) {
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
