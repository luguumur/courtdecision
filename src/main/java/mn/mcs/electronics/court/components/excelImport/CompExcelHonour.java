package mn.mcs.electronics.court.components.excelImport;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.Award;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.Honour;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.AwardType;
import mn.mcs.electronics.court.enums.Gender;
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

public class CompExcelHonour {

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
	private Honour honour;

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

		int savedHonour = 0;

		try {
			fis = fileEx.getStream();

			HSSFWorkbook wb = new HSSFWorkbook(fis);

			HSSFSheet sheet = wb.getSheetAt(0);

			Iterator rows = sheet.rowIterator();

			while (rows.hasNext()) {

				HSSFRow row = (HSSFRow) rows.next();

				isAdd = true;

				if (row.getRowNum() >= 1) {

					honour = new Honour();

					this.importExcel(row);

					if (isAdd) {

						dao.saveOrUpdateObject(honour);
						savedHonour++;
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
		manager.alert(Duration.SINGLE, Severity.INFO, "Нэмэгдсэн шагналын тоо : " + savedHonour);
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

					if (cellIndex == 0) {

						try {
							employee = dao.getEmployeeByOrg(organization.getId(),
									Integer.parseInt(obj.toString().substring(0, (obj.toString().contains("."))
											? obj.toString().indexOf(".") : obj.toString().length())));

							if (employee == null) {

								isAdd = false;
								importUtil.regErrMsg("Ажилтан олдсонгүй!", row.getRowNum() + 1, cellIndex + 1);
							} else
								honour.setEmployee(employee);

						} catch (NumberFormatException ex) {
							System.err.println("№ талбар: " + ex);
						} catch (Exception ex) {
							System.err.println("№ талбар: " + ex);
						}
					} else {

						if (isAdd) {

							switch (cellIndex) {

							case 1:

								// if
								// (obj.getClass().getSimpleName().equals("String"))
								// {
								// if (String.valueOf(obj).contains("ШШГБ-ын
								// шагнал")) {
								// honour.setAwardType(AwardType.COURTPRIZE);
								// } else if
								// (String.valueOf(obj).contains("Засгийн газрын
								// шагнал")) {
								// honour.setAwardType(AwardType.GOVERNMENTPRIZE);
								// } else if
								// (String.valueOf(obj).contains("Хууль зүйн
								// яамны шагнал")) {
								// honour.setAwardType(AwardType.JUSTICE_MINISTRYPRIZE);
								// } else if
								// (String.valueOf(obj).contains("Бусад")) {
								// honour.setAwardType(AwardType.OTHER_ORGANIZATIONPRIZE);
								// } else if
								// (String.valueOf(obj).contains("Төрийн
								// шагнал")) {
								// honour.setAwardType(AwardType.STATEPRIZE);
								// } else {
								// isAdd = false;
								// importUtil.regErrMsg("Шагналын төрөл заавал
								// сонгоно уу!", row.getRowNum() + 1,
								// cellIndex + 1);
								// }
								//
								// } else {
								// isAdd = false;
								//
								// importUtil.regErrMsg("Шагналын төрөл буруу
								// байна - " + employee.getEmpNumber(),
								// row.getRowNum() + 1, cellIndex + 1);
								// }
								AwardType awardType = getAwardType(obj.toString());

								if (awardType != null)
									honour.setAwardType(awardType);
								else {
									isAdd = false;
									importUtil.regErrMsg("Шагналын төрөл заавал сонгоно уу!", row.getRowNum() + 1,
											cellIndex + 1);
								}

								if (honour.getAwardType() == null) {
									isAdd = false;

									importUtil.regErrMsg("Шагналын төрөл буруу байна - " + employee.getEmpNumber(),
											row.getRowNum() + 1, cellIndex + 1);
								}

								break;

							case 2:
								if (honour.getAwardType() != null
										&& honour.getAwardType().equals(AwardType.OTHER_ORGANIZATIONPRIZE)) {
									honour.setOtherPrize(obj.toString());
									honour.setAward(null);
								} else if (honour.getAwardType() != null
										&& honour.getAwardType() != (AwardType.OTHER_ORGANIZATIONPRIZE)) {
									Award award = dao.getAwardByNameName(obj.toString());
									if (award != null) {
										honour.setAward(award);
										honour.setOtherPrize(null);
									} else {
										isAdd = false;

										importUtil.regErrMsg("Шагналын нэр буруу байна - " + employee.getEmpNumber(),
												row.getRowNum() + 1, cellIndex + 1);
									}
								}
								break;
							case 3:

								if (obj.getClass().getSimpleName().equals("Date"))
									honour.setDateOfAwarded((Date) obj);
								else {
									importUtil.regErrMsg("Буруу форматтай огноо оруулсан байна.", row.getRowNum() + 1,
											cellIndex + 1);
								}

								break;
							case 4:
								honour.setAuthenticationId(obj.toString());
								break;
							case 5:
								honour.setDictateId(obj.toString());
								break;
							case 6:
								honour.setDescription(obj.toString());
								break;

							}
						}
					}
				} else {
					if (cellIndex == 0)
						isAdd = false;
				}
			}
		}
	}

	public AwardType getAwardType(String awardTypeName) {
		for (AwardType awardType : AwardType.values()) {
			String awardTypeTitle = messages.get(awardType.name());
			if (awardTypeTitle.equals(awardTypeName) == true) {
				return awardType;
			}
		}

		return null;
	}

	public Award getAward(AwardType awardType, String awardName) {
		if (awardType != null && awardName != null) {
			Award award = dao.getAwardByName(awardType, awardName);

			return (award == null) ? null : award;
		}

		return null;
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
