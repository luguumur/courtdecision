package mn.mcs.electronics.court.components.excelImport;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.CommandSubject;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.Sahilga;
import mn.mcs.electronics.court.entities.employee.SahilgaShiitgel;
import mn.mcs.electronics.court.entities.employee.SahilgaTurul;
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

public class CompExcelSahilga {

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
	private Sahilga sahilga;

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

		int savedSahilga = 0;

		try {
			fis = fileEx.getStream();

			HSSFWorkbook wb = new HSSFWorkbook(fis);

			HSSFSheet sheet = wb.getSheetAt(0);

			Iterator rows = sheet.rowIterator();

			while (rows.hasNext()) {

				HSSFRow row = (HSSFRow) rows.next();

				isAdd = true;

				if (row.getRowNum() >= 1) {

					sahilga = new Sahilga();

					this.importExcel(row);

					if (isAdd) {
						dao.saveOrUpdateObject(sahilga);
						savedSahilga++;
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
		manager.alert(Duration.SINGLE, Severity.INFO, "Нэмэгдсэн сахилгын тоо : " + savedSahilga);
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

							sahilga.setEmployee(employee);

							CommandSubject commandSubject = dao.getCommandSubjectByName(obj.toString());

							if (commandSubject != null)
								sahilga.setCommandSubject(commandSubject);
							else {
								isAdd = false;
								importUtil.regErrMsg("Тушаал гаргасан субьект талбарын утга буруу байна!",
										row.getRowNum() + 1, cellIndex + 1);
							}
						} else {
							isAdd = false;
							importUtil.regErrMsg("Ажилтан олдсонгүй!", row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 2:
						if (employee != null && employee.getId() != null) {
							SahilgaTurul sahilgaTurul = dao.getSahilgaTurulByName(obj.toString());

							if (sahilgaTurul != null)
								sahilga.setSahilgaTurul(sahilgaTurul);
							else {
								isAdd = false;
								importUtil.regErrMsg("Сахилгын үндэслэлийн төрөл талбарын утга буруу байна!",
										row.getRowNum() + 1, cellIndex + 1);
							}
						} else {
							isAdd = false;
							importUtil.regErrMsg("Ажилтан олдсонгүй!", row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 3:
						if (employee != null && employee.getId() != null) {
							if (sahilga.getSahilgaTurul() != null
									&& sahilga.getSahilgaTurul().getSahilgaTurulName().equalsIgnoreCase("Бусад")) {
								sahilga.setSahilgaBusadTurul(obj.toString());
							}

						} else {
							isAdd = false;
							importUtil.regErrMsg("Ажилтан олдсонгүй!", row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 4:
						if (employee != null && employee.getId() != null) {
							SahilgaShiitgel sahilgaShiitgel = dao.getSahilgaShiitgelByName(obj.toString());

							if (sahilgaShiitgel != null) {
								sahilga.setSahilgaShiitgel(sahilgaShiitgel);

							} else {
								isAdd = false;
								importUtil.regErrMsg("Сахилгын шийтгэлийн төрөл талбарын утга буруу байна!",
										row.getRowNum() + 1, cellIndex + 1);
							}

						} else {
							isAdd = false;
							importUtil.regErrMsg("Ажилтан олдсонгүй!", row.getRowNum() + 1, cellIndex + 1);
						}
						break;

					case 5:
						if (employee != null && employee.getId() != null) {
							if (sahilga.getSahilgaShiitgel() != null
									&& sahilga.getSahilgaShiitgel().getShiitgelName().equalsIgnoreCase("Бусад")) {
								sahilga.setSahilgaBusadShiitgel(obj.toString());
							}

						} else {
							isAdd = false;
							importUtil.regErrMsg("Ажилтан олдсонгүй!", row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 6:

						if (employee != null && employee.getId() != null) {

							if (obj.getClass().getSimpleName().equals("Date"))
								sahilga.setShiitgelAvagdsanOgnoo((Date) obj);
							else
								importUtil.regErrMsg("Сахилгын шийтгэл оногдуулсан огноо талбарын утга буруу байна!",
										row.getRowNum() + 1, cellIndex + 1);

						} else {
							isAdd = false;
							importUtil.regErrMsg("Ажилтан олдсонгүй!", row.getRowNum() + 1, cellIndex + 1);
						}

						break;
					case 7:

						if (employee != null && employee.getId() != null) {

							if (obj.getClass().getSimpleName().equals("Date"))
								sahilga.setShiitgelDuusahOgnoo((Date) obj);
							else
								importUtil.regErrMsg("Сахилгын шийтгэл дуусах огноо талбарын утга буруу байна!",
										row.getRowNum() + 1, cellIndex + 1);
						} else {
							isAdd = false;
							importUtil.regErrMsg("Ажилтан олдсонгүй!", row.getRowNum() + 1, cellIndex + 1);
						}

						break;
					case 8:
						if (employee != null && employee.getId() != null) {
							sahilga.setArilgasanTushaalDugaar(obj.toString());
						} else {
							isAdd = false;
							importUtil.regErrMsg("Ажилтан олдсонгүй!", row.getRowNum() + 1, cellIndex + 1);
						}
						break;

					case 9:
						if (employee != null && employee.getId() != null) {
							if (obj.getClass().getSimpleName().equals("Date"))
								sahilga.setArilgasanTushaalOgnoo((Date) obj);
							else
								importUtil.regErrMsg("Сахилгын шийтгэл арилгасан огноо талбарын утга буруу байна!",
										row.getRowNum() + 1, cellIndex + 1);

						} else {
							isAdd = false;
							importUtil.regErrMsg("Ажилтан олдсонгүй!", row.getRowNum() + 1, cellIndex + 1);
						}

						break;
					}

				} else {

					if (cellIndex == 0)
						isAdd = false;
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
