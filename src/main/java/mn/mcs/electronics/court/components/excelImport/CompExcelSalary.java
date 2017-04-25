package mn.mcs.electronics.court.components.excelImport;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.OccupationRank;
import mn.mcs.electronics.court.entities.configuration.SalaryScale;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuLevel;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuSort;
import mn.mcs.electronics.court.entities.configuration.salary.AdditionalSalaryType;
import mn.mcs.electronics.court.entities.employee.AdditionalSalary;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.SalaryHistory;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.Month;
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

public class CompExcelSalary {

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

	@Persist
	private SalaryHistory salaryHistory;

	@Persist
	private List<AdditionalSalary> addList;

	UtTzTtTuLevel utTzTtTuLevel = null;
	SalaryScale salaryScale = null;
	UtTzTtTuSort utTzTtTuSort = null;
	OccupationRank occupationRank = null;
	Date salaryDate = null;

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

					salaryHistory = new SalaryHistory();

					this.importExcel(row);

					if (isAdd) {

						if (occupationRank != null) {
							utTzTtTuLevel = dao.getUtTzLevel(utTzTtTuSort, occupationRank);
						} else {
							System.err.println("occupationRank is null");
						}

						if (utTzTtTuLevel != null && utTzTtTuLevel.getUtTzTtTuSort() != null
								&& utTzTtTuLevel.getUtTzTtTuRank() != null) {
							salaryHistory.setLevel(utTzTtTuLevel);
						} else {
							System.err.println("utTzTtTuLevel is null");
						}

						if (addList != null) {
							for (AdditionalSalary sal : addList) {
								sal.setSalary(salaryHistory);
							}
						}

						if (!addList.isEmpty()) {
							salaryHistory.setAdditionalSalary(addList);
						}

						dao.saveOrUpdateObject(salaryHistory);
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
		manager.alert(Duration.SINGLE, Severity.INFO, "Хадгалагдсан цалин" + savedEmp);
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

						if (employee != null) {

							addList = new ArrayList<AdditionalSalary>();

							salaryHistory
									.setYear(Integer.parseInt(obj.toString().substring(0, (obj.toString().contains("."))
											? obj.toString().indexOf(".") : obj.toString().length())));

							if (salaryHistory.getYear() == null) {
								isAdd = false;

								importUtil.regErrMsg("Он талбар буруу байна - " + employee.getEmpNumber(),
										row.getRowNum() + 1, cellIndex + 1);
							}

						} else {
							isAdd = false;
							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 2:

						if (employee != null) {
							salaryHistory.setEmployee(employee);
							Month temp = null;

							String monthStr = String.valueOf(obj).trim();

							if (monthStr.equalsIgnoreCase(messages.get("jan").trim())) {
								temp = Month.JAN;
							} else if (monthStr.equalsIgnoreCase(messages.get("feb").trim())) {
								temp = Month.FEB;
							} else if (monthStr.equalsIgnoreCase(messages.get("mar").trim())) {
								temp = Month.MAR;
							} else if (monthStr.equalsIgnoreCase(messages.get("apr").trim())) {
								temp = Month.APR;
							} else if (monthStr.equalsIgnoreCase(messages.get("may").trim())) {
								temp = Month.MAY;
							} else if (monthStr.equalsIgnoreCase(messages.get("jun").trim())) {
								temp = Month.JUN;
							} else if (monthStr.equalsIgnoreCase(messages.get("jul").trim())) {
								temp = Month.JUL;
							} else if (monthStr.equalsIgnoreCase(messages.get("aug").trim())) {
								temp = Month.AUG;
							} else if (monthStr.equalsIgnoreCase(messages.get("sep").trim())) {
								temp = Month.SEP;
							} else if (monthStr.equalsIgnoreCase(messages.get("oct").trim())) {
								temp = Month.OCT;
							} else if (monthStr.equalsIgnoreCase(messages.get("nov").trim())) {
								temp = Month.NOV;
							} else if (monthStr.equalsIgnoreCase(messages.get("dec").trim())) {
								temp = Month.DEC;
							}

							salaryHistory.setMonth(temp);

							if (salaryHistory.getMonth() == null) {
								isAdd = false;

								importUtil.regErrMsg("Сар талбар буруу байна - " + employee.getEmpNumber(),
										row.getRowNum() + 1, cellIndex + 1);
							}

						} else {
							isAdd = false;
							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 3:
						if (employee != null) {
							salaryHistory.setEmployee(employee);
							utTzTtTuSort = dao.getUtTzTtTuSortByName(obj.toString());

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 5:
						if (employee != null) {
							salaryHistory.setEmployee(employee);
							Integer rank = null;
							if (obj.getClass().getSimpleName().equals("String")) {
								if (tryParseInt((String) obj))
									rank = Integer.valueOf((String) obj);
								occupationRank = dao.getOccupationRankByRank(rank);

							} else if (obj.getClass().getSimpleName().equals("Double")) {
								rank = importUtil.getInteger(obj);
								occupationRank = dao.getOccupationRankByRank(rank);
							}

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 6:

						if (employee != null) {
							salaryHistory.setEmployee(employee);

							salaryScale = dao
									.getSalaryScaleByName(obj.toString().substring(0, (obj.toString().contains("."))
											? obj.toString().indexOf(".") : obj.toString().length()));

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						break;
					case 7:
						if (employee != null) {
							salaryHistory.setEmployee(employee);
							if (obj.getClass().getSimpleName().equals("Date")) {

								salaryDate = (Date) obj;

							} else if (obj.getClass().getSimpleName().equals("String")) {
								salaryDate = importUtil.getDateFromString((String) obj);
							}

							/*
							 * if (salaryDate == null) { isAdd = false;
							 * 
							 * importUtil.regErrMsg(
							 * "Цалин тогтоосон огноо талбар буруу байна - " +
							 * employee.getEmpNumber(), row.getRowNum() + 1,
							 * cellIndex + 1); }
							 */

						} else {
							isAdd = false;

							importUtil.regErrMsg("Ажилтан олдсонгүй", row.getRowNum() + 1, cellIndex + 1);
						}

						break;

					case 8:

						if (employee != null) {

							AdditionalSalaryType addSalaryType = dao.getAdditionalSalaryTypeById(1l);

							addSalaryType.setPercent(
									Double.valueOf(obj.toString().substring(0, (obj.toString().contains("."))
											? obj.toString().indexOf(".") : obj.toString().length())));

							AdditionalSalary salary = new AdditionalSalary();

							salary.setAdditionalSalaryType(addSalaryType);
							salary.setPercent(Double.valueOf(obj.toString().substring(0, (obj.toString().contains("."))
									? obj.toString().indexOf(".") : obj.toString().length())));

							addList.add(salary);

						}

						break;

					case 9:

						if (employee != null) {
							if (addList != null) {

								AdditionalSalaryType addSalaryType = dao.getAdditionalSalaryTypeById(2l);

								addSalaryType.setPercent(
										Double.valueOf(obj.toString().substring(0, (obj.toString().contains("."))
												? obj.toString().indexOf(".") : obj.toString().length())));

								AdditionalSalary salary = new AdditionalSalary();

								salary.setAdditionalSalaryType(addSalaryType);
								salary.setPercent(
										Double.valueOf(obj.toString().substring(0, (obj.toString().contains("."))
												? obj.toString().indexOf(".") : obj.toString().length())));

								addList.add(salary);

							}
						}

						break;

					case 10:

						if (employee != null) {
							if (addList != null) {

								AdditionalSalaryType addSalaryType = dao.getAdditionalSalaryTypeById(3l);

								addSalaryType.setPercent(
										Double.valueOf(obj.toString().substring(0, (obj.toString().contains("."))
												? obj.toString().indexOf(".") : obj.toString().length())));

								AdditionalSalary salary = new AdditionalSalary();

								salary.setAdditionalSalaryType(addSalaryType);
								salary.setPercent(
										Double.valueOf(obj.toString().substring(0, (obj.toString().contains("."))
												? obj.toString().indexOf(".") : obj.toString().length())));

								addList.add(salary);

							}
						}

						break;

					case 11:

						if (employee != null) {
							if (addList != null) {

								AdditionalSalaryType addSalaryType = dao.getAdditionalSalaryTypeById(5l);

								addSalaryType.setPercent(
										Double.valueOf(obj.toString().substring(0, (obj.toString().contains("."))
												? obj.toString().indexOf(".") : obj.toString().length())));

								AdditionalSalary salary = new AdditionalSalary();

								salary.setAdditionalSalaryType(addSalaryType);
								salary.setPercent(
										Double.valueOf(obj.toString().substring(0, (obj.toString().contains("."))
												? obj.toString().indexOf(".") : obj.toString().length())));

								addList.add(salary);

							}
						}

						break;

					case 12:

						if (employee != null) {
							if (addList != null) {

								AdditionalSalaryType addSalaryType = dao.getAdditionalSalaryTypeById(7l);

								addSalaryType.setPercent(
										Double.valueOf(obj.toString().substring(0, (obj.toString().contains("."))
												? obj.toString().indexOf(".") : obj.toString().length())));

								AdditionalSalary salary = new AdditionalSalary();

								salary.setAdditionalSalaryType(addSalaryType);
								salary.setPercent(
										Double.valueOf(obj.toString().substring(0, (obj.toString().contains("."))
												? obj.toString().indexOf(".") : obj.toString().length())));

								addList.add(salary);

							}
						}

						break;

					case 13:

						if (employee != null) {
							if (addList != null) {

								AdditionalSalaryType addSalaryType = dao.getAdditionalSalaryTypeById(6l);

								addSalaryType.setPercent(
										Double.valueOf(obj.toString().substring(0, (obj.toString().contains("."))
												? obj.toString().indexOf(".") : obj.toString().length())));

								AdditionalSalary salary = new AdditionalSalary();

								salary.setAdditionalSalaryType(addSalaryType);
								salary.setPercent(
										Double.valueOf(obj.toString().substring(0, (obj.toString().contains("."))
												? obj.toString().indexOf(".") : obj.toString().length())));

								addList.add(salary);

							}
						}

						break;

					case 14:

						if (employee != null) {
							if (addList != null) {

								AdditionalSalaryType addSalaryType = dao.getAdditionalSalaryTypeById(7l);

								addSalaryType.setPercent(
										Double.valueOf(obj.toString().substring(0, (obj.toString().contains("."))
												? obj.toString().indexOf(".") : obj.toString().length())));

								AdditionalSalary salary = new AdditionalSalary();

								salary.setAdditionalSalaryType(addSalaryType);
								salary.setPercent(
										Double.valueOf(obj.toString().substring(0, (obj.toString().contains("."))
												? obj.toString().indexOf(".") : obj.toString().length())));

								addList.add(salary);

							}
						}

						break;

					}
				} else {
					if (cellIndex == 0 || cellIndex == 1 || cellIndex == 2 || cellIndex == 3 || cellIndex == 4
							|| cellIndex == 5 || cellIndex == 6) {
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
