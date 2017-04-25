package mn.mcs.electronics.court.components.salary;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.salary.AdditionalSalaryType;
import mn.mcs.electronics.court.entities.configuration.salary.TuriinZahirgaaSalaryNetwork;
import mn.mcs.electronics.court.entities.employee.AdditionalSalary;
import mn.mcs.electronics.court.entities.employee.Displacement;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.SalaryHistory;
import mn.mcs.electronics.court.enums.AdditionalSalaryCategory;
import mn.mcs.electronics.court.enums.AppointmentType;
import mn.mcs.electronics.court.enums.Month;
import mn.mcs.electronics.court.model.SalaryDateSelectionModel;
import mn.mcs.electronics.court.util.Constants;
import mn.mcs.electronics.court.util.ExcelAPI;
import mn.mcs.electronics.court.util.ReportUtil;
import mn.mcs.electronics.court.util.StringUtil;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.util.EnumSelectModel;

public class CompEmployeeSalary {

	/*
	 * STATES
	 */

	@SessionState
	private LoginState loginState;

	/*
	 * INJETCS
	 */

	@Inject
	private CoreDAO dao;

	@Inject
	private Messages messages;

	@Inject
	private Response response;

	@Inject
	@Path("context:/images/excel.jpg")
	private Asset imgExcel;

	@Inject
	private Block blockDetail;

	@Inject
	private Block blockList;

	@Inject
	private Request request;

	@Inject
	private AlertManager manager;

	@InjectComponent
	private Zone salaryFormZone;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	/*
	 * PERSISTS
	 */

	@Persist
	private boolean switchBlock;

	@Persist
	private Employee employee;

	@Persist
	private Displacement displacement;

	@Property
	@Persist
	private List<AdditionalSalaryType> listAdditionalSalaryType;

	@Property
	@Persist
	private AdditionalSalaryType valueAdditionSalaryType;

	@Property
	@Persist
	private List<AdditionalSalaryType> listAdditionalOtherSalaryType;

	@Persist
	@Property
	private Double currentSalary;

	@Persist
	private Boolean onload;

	@Persist
	private Double percent;

	@Persist
	private String amount;

	@Persist
	private String date;

	@Persist
	private Date dateS;

	@Persist
	private List<AdditionalSalary> listAdditionalSalary;

	@Property
	@Persist
	private AdditionalSalary valueAdditionalSalary;

	@Property
	@Persist
	private SalaryHistory salary;

	@Property
	@Persist
	private SalaryHistory valueSalary;

	@Property
	@Persist
	private List<SalaryHistory> listSalary;

	@Persist
	private TuriinZahirgaaSalaryNetwork current;

	@Persist
	private boolean viewEmployee;

	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);

	@Persist
	private Double otherIncome;

	void beginRender() {

		viewEmployee = true;

		displacement = dao.getCurrentPosition(employee);

		if (onload == null) {
			listAdditionalSalaryType = dao
					.getListAdditionalSalaryTypes(AdditionalSalaryCategory.MAIN);
			listAdditionalOtherSalaryType = dao
					.getListAdditionalSalaryTypes(AdditionalSalaryCategory.OTHER);
		}

		if (current != null)
			currentSalary = current.getAmount();

		onload = true;

		if (listAdditionalSalary == null)
			listAdditionalSalary = new ArrayList<AdditionalSalary>();

		if (salary == null)
			salary = new SalaryHistory();

		listSalary = dao.getListSalary(employee);
	}

	@CommitAfter
	void onSelectedFromSave() {

		for (AdditionalSalary as : listAdditionalSalary) {
			as.setSalary(salary);
			dao.saveOrUpdateObject(as);
		}

		salary.setAdditionalSalary(listAdditionalSalary);
		salary.setEmployee(employee);
		salary.setFirstAmount(currentSalary);
		salary.setTotalAmount(Double.parseDouble(getTotal()));
		dao.saveOrUpdateObject(salary);
		switchBlock = false;
		ajaxResponseRenderer.addRender(salaryFormZone);
		listSalary = dao.getListSalary(employee);

	}

	void onActionFromBack() {
		ajaxResponseRenderer.addRender(salaryFormZone);
		listSalary = dao.getListSalary(employee);
		switchBlock = false;
	}

	void onActionFromEdit(SalaryHistory salary) {

		ajaxResponseRenderer.addRender(salaryFormZone);
		this.salary = salary;
		listAdditionalSalary = dao.getListAdditionalSalary(this.salary);
		currentSalary = salary.getFirstAmount();
		switchBlock = true;

	}

	@CommitAfter
	void onActionFromDelete(SalaryHistory salary) {
		dao.deleteObject(salary);
//		ajaxResponseRenderer.addRender(salaryFormZone);
		listSalary = dao.getListSalary(employee);
	}

	void onActionFromAdd() throws ParseException {

		if (request.isXHR()) {
			salary = new SalaryHistory();

			listAdditionalSalary = new ArrayList<AdditionalSalary>();
			if (displacement != null && displacement.getSalaryNetwork() != null) {
				if (date != null)
					dateS = new SimpleDateFormat(Constants.DATE_FORMAT)
							.parse(date);
				current = dao.getCurrentSalaryAmount(
						displacement.getUtTzTtTuLevel(),
						displacement.getSalaryScale(), dateS);
				if (current != null) {
					currentSalary = current.getAmount();
					switchBlock = true;
				} else {
					manager.alert(Duration.SINGLE, Severity.ERROR,
							messages.get("currentSalaryIsEmpty"));

				}
				ajaxResponseRenderer.addRender(salaryFormZone);
			} else {
				manager.alert(Duration.SINGLE, Severity.ERROR,
						messages.get("displacementOrSalaryNetworkIsEmpty"));
			}
		}
	}

	/* select model */
	public SelectModel getMonthSelectionModel() {
		return new EnumSelectModel(Month.class, messages);
	}

	public SelectModel getSalaryDateSelectionModel() {
		SalaryDateSelectionModel sm = new SalaryDateSelectionModel(dao,
				displacement.getUtTzTtTuLevel(), displacement.getSalaryScale());

		return sm;
	}

	/* getter setter */

	public String getAmount() {
		for (AdditionalSalary as : listAdditionalSalary)
			if (as.getAdditionalSalaryType().equals(valueAdditionSalaryType))
				return as.getAdditionalAmount() + "";

		return amount;
	}

	public void setAmount(String amount) {

		for (AdditionalSalary as : listAdditionalSalary) {
			if (as.getAdditionalSalaryType().equals(valueAdditionSalaryType)
					&& currentSalary != null)
				as.setAdditionalAmount(currentSalary * as.getPercent() / 100);

			// return;
		}
	}

	public String getOtherIncome() {
		for (AdditionalSalary as : listAdditionalSalary)
			if (as.getAdditionalSalaryType().equals(valueAdditionSalaryType))
				return as.getAdditionalAmount().toString();

		return "0";
	}

	public void setOtherIncome(String otherIncome) {

		for (AdditionalSalary as : listAdditionalSalary) {
			if (as.getAdditionalSalaryType().equals(valueAdditionSalaryType)) {
				as.setAdditionalAmount(Double.parseDouble(otherIncome));
				return;
			}
		}

		AdditionalSalary as = new AdditionalSalary();
		as.setDate(new Date());

		as.setAdditionalAmount(Double.parseDouble(otherIncome));
		as.setAdditionalSalaryType(valueAdditionSalaryType);

		listAdditionalSalary.add(as);
	}

	public Block getActiveBlock() {
		if (switchBlock)
			return blockDetail;

		return blockList;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Boolean getOnload() {
		return onload;
	}

	public void setOnload(Boolean onload) {
		this.onload = onload;
	}

	public List<AdditionalSalary> getListAdditionalSalary() {
		return listAdditionalSalary;
	}

	public void setListAdditionalSalary(
			List<AdditionalSalary> listAdditionalSalary) {
		this.listAdditionalSalary = listAdditionalSalary;
	}

	public String getTotal() {
		Double sum = 0d;

		if (!listAdditionalSalary.isEmpty())
			for (AdditionalSalary as : listAdditionalSalary)
				if (as.getAdditionalAmount() != null)
					sum = sum + as.getAdditionalAmount();

		if (currentSalary == null)
			return "0";

		return StringUtil.getCurrencyFormat(currentSalary + sum);
	}

	public int getNumber() {
		return listSalary.indexOf(valueSalary) + 1;
	}

	public boolean isSwitchBlock() {
		return switchBlock;
	}

	public void setSwitchBlock(boolean switchBlock) {
		this.switchBlock = switchBlock;
	}

	public Asset getImgExcel() {
		return imgExcel;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public boolean isJudge() {
		if (employee.getAppointment() != null)
			if (employee.getAppointment().getAppointmentType() == AppointmentType.JUDGE)
				return true;
		return false;
	}

	public String getAdditionalSalaryValue() {
		Double totalAS = 0d;
		for (AdditionalSalary as : valueSalary.getAdditionalSalary()) {
			if (as.getAdditionalAmount() != null)
				totalAS = totalAS + as.getAdditionalAmount();
		}

		return totalAS + "";
	}

	/* export excel */
	void onActionFromExport() {

		try {
			HSSFWorkbook document = new HSSFWorkbook();
			HSSFSheet sheet = ReportUtil.createSheet(document);

			sheet.setMargin((short) 0, 0.5);
			ReportUtil.setColumnWidths(sheet, 0, 0.5, 1, 0.5, 2, 2, 3, 2, 3, 2,
					4, 3, 5, 2, 6, 2, 7, 2, 8, 2, 9, 2);

			Map<String, HSSFCellStyle> styles = ReportUtil
					.createStyles(document);

			int sheetNumber = 0;
			int rowIndex = 1;
			int colIndex = 1;
			Long index = 1L;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2,
					messages.get("salaryHistory"), styles.get("title"), 5);

			ExcelAPI.setCellValue(document, sheetNumber, ++rowIndex, 1,
					messages.get("date") + ": " + format.format(new Date()),
					styles.get("plain-left-wrap"), 5);
			rowIndex += 2;

			/* column headers */

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1,
					messages.get("number-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2,
					messages.get("year-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3,
					messages.get("month-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 4,
					messages.get("mainSalary-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 5,
					messages.get("totalSalary-label"),
					styles.get("header-wrap"));

			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;
			if (listSalary != null)
				for (SalaryHistory s : listSalary) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, listSalary.indexOf(s) + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, s.getYear().toString(),
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, messages.get(s.getMonth().toString()),
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, s.getFirstAmount().toString(),
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, s.getTotalAmount().toString(),
							styles.get("plain-left-wrap-border"));

					ReportUtil.setRowHeight(sheet, rowIndex, 3);
					rowIndex++;
					index++;
					colIndex = 1;

				}

			rowIndex += 2;

			ExcelAPI.setCellValue(
					document,
					sheetNumber,
					rowIndex,
					1,
					"ГАРГАСАН: " + loginState.getLogin()
							+ "  : ................  / "
							+ loginState.getLogin() + " /",
					styles.get("plain-left-wrap"), 8);
			rowIndex++;

			OutputStream out = response
					.getOutputStream("application/vnd.ms-excel");
			response.setHeader("Content-Disposition",
					"attachment; filename=\"employeeSalary.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isShow() {
		if (viewEmployee == true)
			return true;
		else
			return false;
	}

	public boolean isViewEmployee() {
		return viewEmployee;
	}

	public void setViewEmployee(boolean viewEmployee) {
		this.viewEmployee = viewEmployee;
	}
}
