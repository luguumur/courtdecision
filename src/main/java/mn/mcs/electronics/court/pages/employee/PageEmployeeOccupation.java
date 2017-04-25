package mn.mcs.electronics.court.pages.employee;

import java.util.Date;
import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.components.LayoutEmployee;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.OccupationType;
import mn.mcs.electronics.court.entities.configuration.SalaryNetwork;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuLevel;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuSort;
import mn.mcs.electronics.court.entities.employee.Displacement;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.Experience;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.AppointmentSortEmployee;
import mn.mcs.electronics.court.enums.AppointmentType;
import mn.mcs.electronics.court.enums.EmployeeStatus;
import mn.mcs.electronics.court.enums.OrganizationTypeExperience;
import mn.mcs.electronics.court.enums.UtTzTtTuCategory;
import mn.mcs.electronics.court.model.AppointmentOrgSelectionModel;
import mn.mcs.electronics.court.model.AppointmentSortSelectionModel;
import mn.mcs.electronics.court.model.DepartmentSelectionModel;
import mn.mcs.electronics.court.model.OccupationLevelSelectionModel;
import mn.mcs.electronics.court.model.OccupationRoleSelectionModel;
import mn.mcs.electronics.court.model.OccupationTypeSelectionModel;
import mn.mcs.electronics.court.model.SalaryNetworkSelectionModel;
import mn.mcs.electronics.court.model.SalaryScaleSelectionModel;
import mn.mcs.electronics.court.model.TuAaSortSelectionModel;
import mn.mcs.electronics.court.model.UtTzTtTuSortSelectionModel;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

public class PageEmployeeOccupation {

	@Inject
	private Messages messages;

	@SessionState
	private LoginState loginState;

	@Inject
	private CoreDAO dao;

	@InjectComponent
	private LayoutEmployee layoutEmployee;

	@Persist
	private Employee employee;

	@Property
	@Persist
	private Displacement currentOccupation;

	@Property
	@Persist
	private Boolean mainOccupation;

	@Property
	@Persist
	private Boolean tenderer;

	@Property
	@Persist
	private boolean directory;

	@Property
	@Persist
	private boolean qualification;

	@Property
	@Persist
	private boolean major;

	/*
	 * @Property
	 * 
	 * @Persist private TuAaSort tuAaSort;
	 */
	@Property
	@Persist
	private UtTzTtTuSort utTzTtTuSort;

	@Property
	@Persist
	private UtTzTtTuLevel utTzTtTuLevel;

	@Property
	@Persist
	private OccupationType occupationType;

	@Property
	@Persist
	private SalaryNetwork salaryNetwork;

	@Persist
	private Organization org;

	@Persist
	private boolean viewEmployee;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	/*
	 * @InjectComponent private Zone offZeregZone;
	 */

	@InjectComponent
	private Zone uTtzZeregZone;

	@InjectComponent
	private Zone tsalinZone;

	@Inject
	private Request request;

	private Long empID;

	void onActivate(Long id) {
		empID = id;
	}

	Long onPassivate() {
		return empID;
	}

	void beginRender() {
		viewEmployee = true;

		this.employee = (Employee) dao.getObject(Employee.class, empID);

		layoutEmployee.setValueEmployee(employee);

		currentOccupation = dao.getCurrentOccupation(employee);

		if (currentOccupation != null)
			occupationType = currentOccupation.getOccupationType();

		if (currentOccupation == null)
			currentOccupation = new Displacement();

		if (currentOccupation != null
				&& currentOccupation.getMainOccupation() != null) {
			if (currentOccupation.getMainOccupation() == true)
				mainOccupation = true;
			else
				mainOccupation = false;
		}

		if (currentOccupation != null
				&& currentOccupation.getTenderer() != null) {
			if (currentOccupation.getTenderer() == true)
				tenderer = true;
			else
				tenderer = false;

//			if (currentOccupation.isDirectoryTraining() == true)
//				directory = true;
//			else
//				directory = false;
		}

		if (currentOccupation != null
				&& currentOccupation.isQualificationTraining() == true)
			qualification = true;
		else
			qualification = false;

		if (currentOccupation != null
				&& currentOccupation.getUtTzTtTuLevel() != null) {
			utTzTtTuSort = currentOccupation.getUtTzTtTuLevel()
					.getUtTzTtTuSort();
			utTzTtTuLevel = currentOccupation.getUtTzTtTuLevel();
		} else {
			utTzTtTuSort = null;
			utTzTtTuLevel = null;
		}

		if (utTzTtTuSort == null) {
			List<UtTzTtTuSort> lst = dao.getListUtTzTtTuSort();
			if (!lst.isEmpty())
				utTzTtTuSort = lst.get(1);
		}

		if (org == null)
			org = new Organization();

		org = employee.getOrganization();
	}

	@CommitAfter
	void onSelectedFromSave() {
		employee = (Employee) dao.getObject(Employee.class, employee.getId());

		currentOccupation.setEmployee(employee);

		/* Dec 07, 2012 Jargalsuren.S Start */
		if (currentOccupation.getIssuedDate() == null) {
			// loginState.setErrMessage(messages.get("IssuedDateIsNull"));
			System.err.println(messages.get("IssuedDateIsNull"));
			return;
		}
		/* Dec 07, 2012 Jargalsuren.S End */

		if (currentOccupation.getIssuedDate().compareTo(new Date()) < 0
				|| currentOccupation.getIssuedDate().compareTo(new Date()) == 0) {

			if (mainOccupation)
				currentOccupation.setMainOccupation(true);
			else
				currentOccupation.setMainOccupation(false);

			if (tenderer)
				currentOccupation.setTenderer(true);
			else
				currentOccupation.setTenderer(false);

//			if (directory)
//				currentOccupation.setDirectoryTraining(true);
//			else
//				currentOccupation.setDirectoryTraining(false);

			if (qualification)
				currentOccupation.setQualificationTraining(true);
			else
				currentOccupation.setQualificationTraining(false);

			/*
			 * currentOccupation.setDisplacementCause("Одоо ажиллаж байгаа");
			 * 
			 * 
			 * currentOccupation.setDisplacementType(DisplacementCauseType.ODOOGIIN
			 * );
			 */

			currentOccupation.setOccupationType(occupationType);

			currentOccupation.setUtTzTtTuLevel(utTzTtTuLevel);

			currentOccupation.setSalaryNetwork(salaryNetwork);

			currentOccupation.setOrganization(org);

			currentOccupation.setIsNowDisplacement(true);
			dao.saveOrUpdateObject(currentOccupation);

			Experience exp = dao.getWorkingExperience(employee);

			if (exp == null) {

				exp = new Experience();
				exp.setOrganizationtype(OrganizationTypeExperience.SHUUH);
				exp.setOrganizationname(org.getName());
				exp.setStatus(EmployeeStatus.WORKING);
				exp.setEmployee(employee);
			}

			exp.setAppointment(currentOccupation.getAppointment()
					.getAppointmentName());
			exp.setStartDate(currentOccupation.getIssuedDate());
			dao.saveOrUpdateObject(exp);

			// employee.setAppointment(currentOccupation.getToAppointment());

			dao.saveObject(dao.mergeObject(employee));
		}

		else {
			// loginState.setErrMessage(messages.get("IssuedDateIsLaterThanCurrentDate"));
			System.err
					.println(messages.get("IssuedDateIsLaterThanCurrentDate"));
		}
	}

	/* selection model */

	public SelectModel getOfficiaryCategorySelectionModel() {
		AppointmentSortSelectionModel sm = new AppointmentSortSelectionModel(
				dao, AppointmentType.JUDGE);

		return sm;
	}

	public SelectModel getOccupationRoleSelectionModel() {
		OccupationRoleSelectionModel sm = new OccupationRoleSelectionModel(dao);

		return sm;
	}

	public SelectModel getOccupationTypeSelectionModel() {
		OccupationTypeSelectionModel sm = new OccupationTypeSelectionModel(dao);

		return sm;
	}

	public SelectModel getAppointmentSelectionModel() {
		Subject currentUser = SecurityUtils.getSubject();

		/*
		 * if(currentUser.isPermitted("add_judge")){ AppointmentSelectionModel
		 * sm;
		 * 
		 * if(occupationType==null) sm = new AppointmentSelectionModel(dao ,
		 * null, null); else sm = new AppointmentSelectionModel(dao , null,
		 * occupationType.getType()); return sm; } else{
		 * AppointmentSelectionModel sm; if(occupationType==null) sm = new
		 * AppointmentSelectionModel(dao,AppointmentType.OTHEREMPLOYEE, null);
		 * else sm = new
		 * AppointmentSelectionModel(dao,AppointmentType.OTHEREMPLOYEE,
		 * occupationType.getType()); return sm; }
		 */
		AppointmentOrgSelectionModel sm = new AppointmentOrgSelectionModel(dao);
		return sm;
	}

	public SelectModel getOccupationLevelSelectionModel() {
		if (utTzTtTuSort == null) {
			List<UtTzTtTuSort> lst = dao.getListUtTzTtTuSort();
			if (!lst.isEmpty())
				utTzTtTuSort = lst.get(1);
		}
		OccupationLevelSelectionModel sm = new OccupationLevelSelectionModel(
				dao, utTzTtTuSort);
		return sm;
	}

	/*
	 * public SelectModel getTuAaOccupationLevelSelectionModel() { if (tuAaSort
	 * == null) { List<TuAaSort> lst = dao.getListTuAaSort(); if
	 * (!lst.isEmpty()) tuAaSort = lst.get(1); }
	 * TuAaOccupationLevelSelectionModel sm = new
	 * TuAaOccupationLevelSelectionModel(dao, tuAaSort);
	 * 
	 * return sm; }
	 */
	public SelectModel getUtTzTtTuSortSelectionModel() {
		UtTzTtTuSortSelectionModel sm = new UtTzTtTuSortSelectionModel(dao);
		return sm;
	}

	public SelectModel getTuAaCategorySelectionModel() {
		TuAaSortSelectionModel sm = new TuAaSortSelectionModel(dao);

		return sm;
	}

	public SelectModel getTuAaSortSelectionModel() {
		TuAaSortSelectionModel sm = new TuAaSortSelectionModel(dao);

		return sm;
	}

	public SelectModel getSalaryNetworkSelectionModel() {
		SalaryNetworkSelectionModel sm = new SalaryNetworkSelectionModel(dao);

		return sm;
	}

	public SelectModel getSalaryScaleSelectionModel() {
		SalaryScaleSelectionModel sm = new SalaryScaleSelectionModel(dao);

		return sm;
	}

	public SelectModel getDepartmentSelectionModel() {
		if (org == null) {
			org = employee.getOrganization();
		}

		DepartmentSelectionModel sm = new DepartmentSelectionModel(dao, org);

		return sm;
	}

	/* Ajax zone */

	Object onValueChangedFromUtTzClick() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(uTtzZeregZone);
		}
		return null;
	}

	/*
	 * Object onValueChangedFromTuAaClick() { if(request.isXHR()){
	 * ajaxResponseRenderer.addRender(tuAaZeregZone); } return null; }
	 */

	/*
	 * Object onValueChangedFromOffClick() { if(request.isXHR()){
	 * ajaxResponseRenderer.addRender(offZeregZone); } return null; }
	 */

	Object onValueChangedFromTsalinClick() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(tsalinZone);
		}
		return null;
	}

	/* getter, setter */

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;

		currentOccupation = dao.getCurrentOccupation(employee);
	}

	// public Boolean getTuAadisabled() {
	// if (occupationType == null)
	// return false;
	// else {
	// if (utTzTtTuLevel != null
	// && utTzTtTuSort.getCategory() == UtTzTtTuCategory.TURIINZAHIRGAANII
	// || utTzTtTuSort.getCategory() == UtTzTtTuCategory.TURIINUILCHILGEENII
	// || occupationType.getType() == AppointmentSortEmployee.SHUUGCH) {
	// currentOccupation.setTuAaLevel(null);
	// return true;
	// } else
	// return false;
	//
	// }
	// }
	//
	// public Boolean getOffdisabled() {
	// if (utTzTtTuSort.getCategory() == UtTzTtTuCategory.TURIINUILCHILGEENII) {
	// currentOccupation.setOfficiarySort(null);
	// return true;
	// } else
	// return false;
	// }

	public Boolean getUtTzDisabled() {
		System.err.println("oc::" + occupationType);
		if (occupationType == null)
			return false;
		else {
			if (occupationType.getType() == AppointmentSortEmployee.SHUUGCH
					|| occupationType.getType() == AppointmentSortEmployee.AJLIINALBA) {
				currentOccupation.setUtTzTtTuLevel(null);
				return true;

			} else
				return false;
		}
	}

	public Boolean getJudgeSalary() {
		return false;
	}

	public boolean isJudge() {
		if (occupationType == null)
			return false;
		if (occupationType.getType() == AppointmentSortEmployee.SHUUGCH)
			return true;
		else
			return false;
	}

	public boolean isNotJudge() {
		if (occupationType == null)
			return true;
		if (occupationType.getType() == AppointmentSortEmployee.SHUUGCH)
			return false;
		else
			return true;
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
