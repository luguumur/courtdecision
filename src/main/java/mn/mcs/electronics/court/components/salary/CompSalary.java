package mn.mcs.electronics.court.components.salary;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuLevel;
import mn.mcs.electronics.court.entities.configuration.salary.AdditionalSalaryType;
import mn.mcs.electronics.court.entities.configuration.salary.TuriinZahirgaaSalaryNetwork;
import mn.mcs.electronics.court.entities.employee.AdditionalSalary;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.EmployeeSalaryHistory;
import mn.mcs.electronics.court.enums.AdditionalSalaryCategory;
import mn.mcs.electronics.court.enums.Month;
import mn.mcs.electronics.court.model.OccupationLevelSelectionModel;
import mn.mcs.electronics.court.model.SalaryScaleSelectionModel;
import mn.mcs.electronics.court.util.StringUtil;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
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

public class CompSalary {

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

	@InjectComponent
	private Zone mainSalary, totalSalary, addSalary, salZone;

	@Inject
	private Request request;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	/*
	 * PERSIST
	 */

	@Persist
	private Employee employee;

	@Persist
	private Double otherIncome;

	@Persist
	private Double percent;

	@Persist
	private String amount;

	@Persist
	@Property
	private Double currentSalary;

	@Persist
	@Property
	private EmployeeSalaryHistory salary;

	@Persist
	@Property
	private List<EmployeeSalaryHistory> listSalary;

	@Persist
	private List<AdditionalSalary> listAdditionalSalary;

	@Property
	@Persist
	private AdditionalSalary valueAdditionalSalary;

	@Property
	@Persist
	private AdditionalSalaryType valueAdditionSalaryType;

	@Property
	@Persist
	private List<AdditionalSalaryType> listAdditionalSalaryType;

	@Property
	@Persist
	private List<AdditionalSalaryType> listAdditionalOtherSalaryType;

	@Property
	private EmployeeSalaryHistory _rowSalary;

	/*
	 * SELECT
	 */

	void beginRender() {

		if (salary == null)
			salary = new EmployeeSalaryHistory();

		if (listAdditionalSalary == null)
			listAdditionalSalary = new ArrayList<AdditionalSalary>();

		listAdditionalSalaryType = dao
				.getListAdditionalSalaryTypes(AdditionalSalaryCategory.MAIN);

		listAdditionalOtherSalaryType = dao
				.getListAdditionalSalaryTypes(AdditionalSalaryCategory.OTHER);

		initList();
	}

	void initList() {
		listSalary = dao.getEmployeeSalaryHistory(employee.getId());
	}

	void onValueChangedFromSalaryPhase() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(mainSalary).addRender(totalSalary);
		}
	}

	void onValueChangedFromSalaryLevel(UtTzTtTuLevel level) {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(mainSalary).addRender(totalSalary);
		}
	}

	@CommitAfter
	void onSelectedFromSave() {
		if (request.isXHR()) {
			for (AdditionalSalary as : listAdditionalSalary) {
				as.setSalaryHistory(salary);
				dao.saveOrUpdateObject(as);
			}

			salary.setAdditionalSalary(listAdditionalSalary);
			salary.setEmployee(employee);
			salary.setMain(currentSalary);
			salary.setTotal(Double.parseDouble(getTotal()));

			dao.saveOrUpdateObject(salary);

			initList();
			salary = new EmployeeSalaryHistory();
			ajaxResponseRenderer.addRender(salZone);
		}
	}

	void onActionFromEdit(EmployeeSalaryHistory salary) {
		if (request.isXHR()) {
			this.salary = salary;
			listAdditionalSalary = dao.getListAdditionalSalary(this.salary);
			currentSalary = salary.getMain();
			initList();
			ajaxResponseRenderer.addRender(salZone);
		}
	}

	void onActionFromCancel() {
		if (request.isXHR()) {
			this.salary = new EmployeeSalaryHistory();
			listAdditionalSalary = new ArrayList<AdditionalSalary>();
			initList();
			ajaxResponseRenderer.addRender(salZone);
		}
	}

	@CommitAfter
	void onActionFromDelete(EmployeeSalaryHistory salary) {
//		if (request.isXHR()) {
			dao.deleteObject(salary);
			initList();
//			ajaxResponseRenderer.addRender(salZone);
//		}
	}

	public String getOtherIncome() {
		for (AdditionalSalary as : listAdditionalSalary)
			if (as.getAdditionalSalaryType().equals(valueAdditionSalaryType))
				return as.getAdditionalAmount().toString();

		return "0";
	}

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
		}
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

	public Double getPercent() {
		for (AdditionalSalary as : listAdditionalSalary)
			if (as.getAdditionalSalaryType().equals(valueAdditionSalaryType))
				return as.getPercent();

		return 0d;
	}

	public void setPercent(Double per) {

		for (AdditionalSalary as : listAdditionalSalary) {
			if (as.getAdditionalSalaryType().equals(valueAdditionSalaryType)) {
				as.setPercent(per);
				return;
			}
		}

		AdditionalSalary as = new AdditionalSalary();
		as.setDate(new Date());

		as.setPercent(per);
		as.setAdditionalSalaryType(valueAdditionSalaryType);

		listAdditionalSalary.add(as);
	}

	public String getMain() {
		if (salary != null && salary.getYear() != null
				&& salary.getMonth() != null) {

			Calendar cal = Calendar.getInstance();
			cal.set(salary.getYear(), salary.getMonth().getVal(), 01);

			TuriinZahirgaaSalaryNetwork sal = dao.getCurrentSalaryAmount(
					salary.getLevel(), salary.getScale(), cal.getTime());

			if (sal != null) {
				currentSalary = sal.getAmount();
				return currentSalary.toString();
			}

			currentSalary = 0d;
		}

		return "0";
	}

	public String getTotal() {
		Double sum = 0d;

		if (!listAdditionalSalary.isEmpty())
			for (AdditionalSalary as : listAdditionalSalary)
				if (as.getAdditionalAmount() != null)
					sum = sum + as.getAdditionalAmount();

		if (currentSalary == null)
			currentSalary = 0d;

		return StringUtil.getCurrencyFormat(currentSalary + sum);
	}

	public String getTotalSal() {

		if (_rowSalary != null && _rowSalary.getYear() != null
				&& _rowSalary.getMonth() != null) {

			Calendar cal = Calendar.getInstance();
			cal.set(_rowSalary.getYear(), _rowSalary.getMonth().getVal() + 1,
					01);

			TuriinZahirgaaSalaryNetwork sal = dao
					.getCurrentSalaryAmount(_rowSalary.getLevel(),
							_rowSalary.getScale(), cal.getTime());

			if (sal != null) {

				Double sum = 0d;

				listAdditionalSalary = dao.getListAdditionalSalary(_rowSalary);

				if (!listAdditionalSalary.isEmpty())
					for (AdditionalSalary as : listAdditionalSalary)
						if (as.getAdditionalAmount() != null)
							sum = sum + as.getAdditionalAmount();

				return StringUtil.getCurrencyFormat(sal.getAmount() + sum);
			}
		}

		return "0";
	}

	public String getAdd() {
		Double sum = 0d;

		if (!listAdditionalSalary.isEmpty())
			for (AdditionalSalary as : listAdditionalSalary)
				if (as.getAdditionalAmount() != null)
					sum = sum + as.getAdditionalAmount();

		if (currentSalary == null)
			return "0";

		return StringUtil.getCurrencyFormat(sum);
	}

	public String getMainSal() {
		if (_rowSalary != null && _rowSalary.getYear() != null
				&& _rowSalary.getMonth() != null) {

			Calendar cal = Calendar.getInstance();
			cal.set(_rowSalary.getYear(), _rowSalary.getMonth().getVal() + 1,
					01);

			TuriinZahirgaaSalaryNetwork sal = dao
					.getCurrentSalaryAmount(_rowSalary.getLevel(),
							_rowSalary.getScale(), cal.getTime());

			if (sal != null) {
				currentSalary = sal.getAmount();
				return sal.getAmount().toString();
			}
		}

		currentSalary = 0d;

		return "0";
	}

	public String getScaleType() {
		if (_rowSalary.getScale() != null)
			return _rowSalary.getScale().getSalaryScale();
		return "0";
	}

	public String getLevelType() {
		return _rowSalary.getLevel().getUtTzTtTuSort().getName() + "-"
				+ _rowSalary.getLevel().getUtTzTtTuRank().getRank();
	}

	public String getYearType() {
		return _rowSalary.getYear().toString();
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public SelectModel getLevelSelectionModel() {
		return new OccupationLevelSelectionModel(dao, null);
	}

	public SelectModel getSalaryPhaseSelectionModel() {
		return new SalaryScaleSelectionModel(dao);
	}

	public SelectModel getMonthSM() {
		return new EnumSelectModel(Month.class, messages);
	}

}
