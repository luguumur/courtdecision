package mn.mcs.electronics.court.components.excelImport;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.AcademicRank;
import mn.mcs.electronics.court.entities.configuration.MilitaryRank;
import mn.mcs.electronics.court.entities.employee.DegreeGradeRank;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.EmployeeMilitary;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.EmployeeDegreeStatus;
import mn.mcs.electronics.court.enums.MilitaryRankType;
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

public class CompExcelAcademicRank {

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
	private DegreeGradeRank degreeGradeRank;

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
			fis = fileEx.getStream();

			HSSFWorkbook wb = new HSSFWorkbook(fis);

			HSSFSheet sheet = wb.getSheetAt(0);

			Iterator rows = sheet.rowIterator();

			while (rows.hasNext()) {

				HSSFRow row = (HSSFRow) rows.next();

				isAdd = true;

				if (row.getRowNum() >= 1) {

					degreeGradeRank = new DegreeGradeRank();

					this.importExcel(row);

					if (isAdd) {
						dao.saveOrUpdateObject(degreeGradeRank);
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
		manager.alert(Duration.SINGLE, Severity.INFO, "Хадгалагдсан цолны тоо"
				+ savedEmp);
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
						} catch (Exception ex) {
						}

						break;

					case 1:
						if (employee != null && employee.getId() != null) {
							try {
								if (degreeGradeRank != null)
									degreeGradeRank.setEmployee(employee);

								AcademicRank rank = dao.getAcademicRank(String
										.valueOf(obj));

								if (rank != null)
									degreeGradeRank.setAcademicRank(rank);

							} catch (Exception ex) {
							}

							if (degreeGradeRank.getAcademicRank() == null) {
								isAdd = false;

								importUtil.regErrMsg(
										"Цол талбар буруу байна - "
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
						if (employee != null && employee.getId() != null) {
							try {
								if (degreeGradeRank != null)
									degreeGradeRank.setEmployee(employee);

								degreeGradeRank.setRankOrganization(obj
										.toString());
							} catch (Exception ex) {
							}

							if (degreeGradeRank.getRankOrganization() == null) {
								isAdd = false;

								importUtil.regErrMsg(
										"Цол олгосон байгууллага талбар буруу байна - "
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
						if (employee != null && employee.getId() != null) {
							try {
								if (degreeGradeRank != null)
									degreeGradeRank.setEmployee(employee);

								degreeGradeRank.setRankDate(obj.toString());
							} catch (Exception ex) {
							}

							if (degreeGradeRank.getRankDate() == null) {
								isAdd = false;

								importUtil.regErrMsg(
										"Цол олгосон он талбар буруу байна - "
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
						if (employee != null && employee.getId() != null) {
							try {
								if (degreeGradeRank != null)
									degreeGradeRank.setEmployee(employee);

								degreeGradeRank.setRankCertificate(obj
										.toString());
							} catch (Exception ex) {
							}

							if (degreeGradeRank.getRankCertificate() == null) {
								isAdd = false;

								importUtil.regErrMsg(
										"Гэрчилгээ, дипломын дугаар талбар буруу байна - "
												+ employee.getEmpNumber(),
										row.getRowNum() + 1, cellIndex + 1);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй",
									row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					}
				} else {
					if (cellIndex == 0 || cellIndex == 1 || cellIndex == 2
							|| cellIndex == 3) {
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
