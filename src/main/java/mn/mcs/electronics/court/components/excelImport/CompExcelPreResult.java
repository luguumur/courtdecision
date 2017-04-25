package mn.mcs.electronics.court.components.excelImport;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.ResultContract;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.Score;
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

public class CompExcelPreResult {

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
	private ResultContract result;

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

		try {
			System.err.println("Файлын нэр:" + fileEx.getFileName());

			fis = fileEx.getStream();

			HSSFWorkbook wb = new HSSFWorkbook(fis);

			HSSFSheet sheet = wb.getSheetAt(0);

			Iterator rows = sheet.rowIterator();

			while (rows.hasNext()) {

				HSSFRow row = (HSSFRow) rows.next();

				isAdd = true;

				if (row.getRowNum() >= 1) {

					result = new ResultContract();

					this.importExcel(row);

					if (isAdd) {
						dao.saveOrUpdateObject(result);
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
		manager.alert(Duration.SINGLE, Severity.INFO,
				messages.get("saved_emp_num") + savedEmp);
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
							System.err
									.println("Нийт бүтээгдэхүүний нийлүүлэлт: "
											+ ex);
						} catch (Exception ex) {
							System.err.println(ex);
						}

						break;

					case 1:

						if (employee != null) {
							result.setEmployee(employee);
							try {
								result.setAllSupplyScore(Integer.parseInt(obj
										.toString()
										.substring(
												0,
												(obj.toString().contains(".")) ? obj
														.toString()
														.indexOf(".") : obj
														.toString().length())));
							} catch (NumberFormatException ex) {
								System.err
										.println("Нийт бүтээгдэхүүний нийлүүлэлт: "
												+ ex);
							} catch (Exception ex) {
								System.err.println(ex);
							}

							if (result.getAllSupplyScore() == null) {
								isAdd = false;

								importUtil.regErrMsg(
										"Нийт бүтээгдэхүүний нийлүүлэлт талбар буруу байна - "
												+ employee.getEmpNumber(),
										row.getRowNum() + 1, cellIndex + 1);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй",
									row.getRowNum() + 1, cellIndex + 1);
						}

						break;
					case 2:
						if (employee != null) {
							result.setEmployee(employee);
							try {
								result.setSpecialSupplyScore(Integer.parseInt(obj
										.toString()
										.substring(
												0,
												(obj.toString().contains(".")) ? obj
														.toString()
														.indexOf(".") : obj
														.toString().length())));
							} catch (NumberFormatException ex) {
								System.err
										.println("Тусгай захиалгат бүтээгдэхүүний нийлүүлэлт: "
												+ ex);
							} catch (Exception ex) {
								System.err.println(ex);
							}

							if (result.getSpecialSupplyScore() == null) {
								isAdd = false;

								importUtil.regErrMsg(
										"Тусгай захиалгат бүтээгдэхүүний нийлүүлэлт талбар буруу байна - "
												+ employee.getEmpNumber(),
										row.getRowNum() + 1, cellIndex + 1);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй",
									row.getRowNum() + 1, cellIndex + 1);
						}

						break;
					case 3:
						if (employee != null) {
							result.setEmployee(employee);
							try {
								result.setQualificationLevel(Integer.parseInt(obj
										.toString()
										.substring(
												0,
												(obj.toString().contains(".")) ? obj
														.toString()
														.indexOf(".") : obj
														.toString().length())));
							} catch (NumberFormatException ex) {
								System.err.println("Мэргэшлийн түвшин : " + ex);
							} catch (Exception ex) {
								System.err.println(ex);
							}

							if (result.getQualificationLevel() == null) {
								isAdd = false;

								importUtil.regErrMsg(
										"Мэргэшлийн түвшин талбар буруу байна - "
												+ employee.getEmpNumber(),
										row.getRowNum() + 1, cellIndex + 1);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй",
									row.getRowNum() + 1, cellIndex + 1);
						}

						break;
					case 4:

						if (obj.getClass().getSimpleName().equals("Date")) {
							result.setContractDate((Date) obj);
						} else if (obj.getClass().getSimpleName()
								.equals("String")) {
							Date createdDate = importUtil
									.getDateFromString((String) obj);

							result.setContractDate(createdDate);
						}

						if (result.getContractDate() == null) {
							isAdd = false;

							importUtil.regErrMsg("Огноо талбар буруу байна - "
									+ employee.getEmpNumber(), row.getRowNum(),
									cellIndex + 1);
						}

						break;

					case 5:

						if (obj.toString().equalsIgnoreCase("A")) {
							result.setDirectSuperiorScore(Score.A);
						} else if (obj.toString().equalsIgnoreCase("B")) {
							result.setDirectSuperiorScore(Score.B);
						} else if (obj.toString().equalsIgnoreCase("C")) {
							result.setDirectSuperiorScore(Score.C);
						} else if (obj.toString().equalsIgnoreCase("D")) {
							result.setDirectSuperiorScore(Score.D);
						} else if (obj.toString().equalsIgnoreCase("F")) {
							result.setDirectSuperiorScore(Score.F);
						}

						break;

					case 6:

						result.setDirectSuperiorCause(obj.toString());

						if (result.getDirectSuperiorCause() == null) {
							isAdd = false;

							importUtil.regErrMsg(
									"Шалтгаан талбар буруу байна - "
											+ employee.getEmpNumber(),
									row.getRowNum(), cellIndex + 1);
						}

						break;

					case 7:

						if (obj.toString().equalsIgnoreCase("A")) {
							result.setSuperiorScore(Score.A);
						} else if (obj.toString().equalsIgnoreCase("B")) {
							result.setSuperiorScore(Score.B);
						} else if (obj.toString().equalsIgnoreCase("C")) {
							result.setSuperiorScore(Score.C);
						} else if (obj.toString().equalsIgnoreCase("D")) {
							result.setSuperiorScore(Score.D);
						} else if (obj.toString().equalsIgnoreCase("F")) {
							result.setSuperiorScore(Score.F);
						}

						break;

					case 8:

						result.setSuperiorCause(obj.toString());

						if (result.getSuperiorCause() == null) {
							isAdd = false;

							importUtil.regErrMsg(
									"Шалтгаан талбар буруу байна - "
											+ employee.getEmpNumber(),
									row.getRowNum(), cellIndex + 1);
						}

						break;
					}
				} else {
					if (cellIndex == 0 /*cellIndex == 1 || cellIndex == 2 || cellIndex == 3
							|| cellIndex == 4*/) {
						importUtil.regErrMsg(
								"Заавал оруулах талбар оруулаагүй байна",
								row.getRowNum() + 1, cellIndex);

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
