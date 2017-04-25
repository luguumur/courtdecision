package mn.mcs.electronics.court.pages.employee;

import java.util.Date;
import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.components.LayoutEmployee;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.AppointmentSort;
import mn.mcs.electronics.court.entities.configuration.OccupationType;
import mn.mcs.electronics.court.entities.configuration.SalaryNetwork;
import mn.mcs.electronics.court.entities.configuration.TuAaSort;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuLevel;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuSort;
import mn.mcs.electronics.court.entities.employee.Displacement;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.Experience;
import mn.mcs.electronics.court.entities.employee.QuitJob;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.AppointmentSortEmployee;
import mn.mcs.electronics.court.enums.AppointmentType;
import mn.mcs.electronics.court.enums.DisplacementCauseType;
import mn.mcs.electronics.court.enums.DisplacementType;
import mn.mcs.electronics.court.enums.EmployeeCurrentStatus;
import mn.mcs.electronics.court.enums.EmployeeStatus;
import mn.mcs.electronics.court.enums.MilitaryOrSimple;
import mn.mcs.electronics.court.enums.OrganizationTypeExperience;
import mn.mcs.electronics.court.enums.UtTzTtTuCategory;
import mn.mcs.electronics.court.model.AppointmentSelectionModel;
import mn.mcs.electronics.court.model.AppointmentSortSelectionModel;
import mn.mcs.electronics.court.model.DepartmentSelectionModel;
import mn.mcs.electronics.court.model.OccupationLevelSelectionModel;
import mn.mcs.electronics.court.model.OccupationRoleSelectionModel;
import mn.mcs.electronics.court.model.OccupationTypeSelectionModel;
import mn.mcs.electronics.court.model.OrganizationSelectionModel;
import mn.mcs.electronics.court.model.QuitJobCauseSelectionModel;
import mn.mcs.electronics.court.model.SalaryNetworkSelectionModel;
import mn.mcs.electronics.court.model.SalaryScaleSelectionModel;
import mn.mcs.electronics.court.model.TuAaOccupationLevelSelectionModel;
import mn.mcs.electronics.court.model.TuAaSortSelectionModel;
import mn.mcs.electronics.court.model.UtTzTtTuSortSelectionModel;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.Block;
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
import org.apache.tapestry5.util.EnumSelectModel;

public class PageEmployeeMovement {

	@Inject
	private Messages messages;

	@Inject
	private CoreDAO dao;

	@SessionState
	private LoginState loginState;

	@InjectComponent
	private LayoutEmployee layoutEmployee;

	@Inject
	private Block shiljih;

	@Inject
	private Block garah;

	@Persist
	private Employee employee;

	@Persist
	private Displacement currentOccupation;

	@Persist
	private Displacement displacement;

	@Persist
	private QuitJob quiJob;

	@Persist
	private DisplacementType selectedBlockId;

	@Property
	@Persist
	private boolean mainOccupation;

	@Property
	@Persist
	private boolean tenderer;

	@Property
	@Persist
	private boolean directory;

	@Property
	@Persist
	private boolean qualification;

	@Property
	@Persist
	private boolean major;

	@Persist
	private DisplacementType displacementTypeSelect;

	@Property
	@Persist
	private QuitJob quitJob;

	@Property
	@Persist
	private TuAaSort tuAaSort;

	@Property
	@Persist
	private UtTzTtTuSort utTzTtTuSort;

	@Property
	@Persist
	private UtTzTtTuLevel utTzTtTuLevel;

	@Property
	@Persist
	private SalaryNetwork salaryNetwork;

	@Property
	@Persist
	private AppointmentSort appointmentSort;

	@Property
	@Persist
	private EmployeeCurrentStatus quitJobType;

	@Property
	@Persist
	private Organization organization;

	@Property
	@Persist
	private Organization org;

	@Property
	@Persist
	private Experience experience;

	@Property
	@Persist
	private Experience experience1;

	@Property
	@Persist
	private OccupationType occupationType;

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

	@InjectComponent
	private Zone orgZone;

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

		// layoutEmployee.setValueEmployee(getEmployee());

		displacement = dao.getCurrentOccupation(employee);

		if (currentOccupation == null)
			currentOccupation = new Displacement();

		if (org == null)
			org = employee.getOrganization();

		if (displacement == null)
			displacement = new Displacement();

		if (quitJob == null)
			quitJob = new QuitJob();

		if (displacement != null || displacement.getMainOccupation() == true)
			mainOccupation = true;
		else
			tenderer = true;

		if (displacement != null
				|| displacement.isQualificationTraining() == true)
			qualification = true;
		else
			qualification = false;

		selectedBlockId = displacementTypeSelect;

		if (experience == null)
			experience = new Experience();

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
		Date nowDate = new Date();
		/* Dec 07, 2012 Jargalsuren.S End */

		if (displacement.getIssuedDate().compareTo(
				currentOccupation.getIssuedDate()) < 0) {
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

			currentOccupation.setAppointment(employee.getAppointment());
//			currentOccupation.setDepartment(employee.getDepartment());
//			currentOccupation.setToOrganization(org);
			currentOccupation.setOrganization(employee.getOrganization());
			currentOccupation.setOccupationType(occupationType);
			currentOccupation.setUtTzTtTuLevel(utTzTtTuLevel);
			currentOccupation.setSalaryNetwork(salaryNetwork);

			dao.saveOrUpdateObject(currentOccupation);

			displacement.setQuitDate(currentOccupation.getIssuedDate());
			/*
			 * displacement.setDisplacementType(currentOccupation.
			 * getDisplacementType());
			 * displacement.setDisplacementCause(currentOccupation
			 * .getDisplacementCause());
			 */
			dao.saveOrUpdateObject(displacement);

//			employee.setOrganization(currentOccupation.getToOrganization());
//			employee.setAppointment(currentOccupation.getToAppointment());
			// employee.setDepartment(currentOccupation.getToDepartment());

			dao.saveObject(dao.mergeObject(employee));
			int x = dao.getExperience(employee).size() - 1;
			experience1 = dao.getExperience(employee).get(x);
			experience1.setStatus(EmployeeStatus.TIRED);
			experience1.setEndDate(displacement.getQuitDate());

			dao.saveOrUpdateObject(experience1);

			experience.setEmployee(employee);
			experience.setOrganizationtype(OrganizationTypeExperience.SHUUH);
			experience.setOrganizationname(""
					+ employee.getOrganization().getName());
			experience.setStatus(EmployeeStatus.WORKING);

			/*
			 * if(currentOccupation.getToAppointment().getAppointmentType()==
			 * AppointmentType.JUDGE) experience.setIsJudge(true);
			 */

			experience.setStartDate(currentOccupation.getIssuedDate());
			experience
					.setAppointment(""
							+ employee.getAppointment().getAppointmentName()
									.toString());

			/*
			 * if(employee.getAppointment().getAppointmentType()==AppointmentType
			 * .JUDGE) experience.setIsJudge(true); else
			 * experience.setIsJudge(false);
			 * 
			 * if(major==true) experience.setIsMajor(true); else
			 * experience.setIsMajor(false);
			 */

			dao.saveObject(experience);

		} else {
			// loginState.setErrMessage(messages.get("IssuedDateIsLaterThanQuitDate"));
			System.err.println(messages.get("IssuedDateIsLaterThanQuitDate"));
		}

		currentOccupation = new Displacement();
	}

	@CommitAfter
	void onSelectedFromSaveQuitJob() {
		quitJob.setEmployee(employee);
		quitJob.setQuitType(quitJobType);
		dao.saveOrUpdateObject(quitJob);
		employee.setEmployeeCurrentStatus(quitJobType);
		employee.setEmployeeStatus(EmployeeStatus.TIRED);
		dao.saveOrUpdateObject(employee);
	}

	void onActionFromCancel() {
		currentOccupation = new Displacement();
	}

	void onActionFromCancelQuitJob() {
		quiJob = new QuitJob();
	}

	/* Ajax zone */

	Object onValueChangedFromUtTzClick() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(uTtzZeregZone);
		}
		return null;
	}

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

	Object onValueChangedFromOrgClick() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(orgZone);
		}
		return null;
	}

	/* selection model */

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

		if (currentUser.isPermitted("add_judge")) {
			AppointmentSelectionModel sm;

			if (occupationType == null)
				sm = new AppointmentSelectionModel(dao, null, null);
			else
				sm = new AppointmentSelectionModel(dao, null,
						occupationType.getType());
			return sm;
		} else {
			AppointmentSelectionModel sm;
			if (occupationType == null)
				sm = new AppointmentSelectionModel(dao,
						AppointmentType.OTHEREMPLOYEE, null);
			else
				sm = new AppointmentSelectionModel(dao,
						AppointmentType.OTHEREMPLOYEE, occupationType.getType());
			return sm;
		}
	}

	public SelectModel getPreAppointmentSelectionModel() {
		AppointmentSelectionModel sm = new AppointmentSelectionModel(dao, null,
				currentOccupation.getOccupationType().getType());
		return sm;

	}

	public SelectModel getUtTzTtTuCategorySelectionModel() {
		return new EnumSelectModel(UtTzTtTuCategory.class, messages);
	}

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

	public SelectModel getDisplacementCauseTypeSelectionModel() {

		return new EnumSelectModel(DisplacementCauseType.class, messages);
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

	public SelectModel getTuAaOccupationLevelSelectionModel() {
		if (tuAaSort == null) {
			List<TuAaSort> lst = dao.getListTuAaSort();
			if (!lst.isEmpty())
				tuAaSort = lst.get(1);
		}
		TuAaOccupationLevelSelectionModel sm = new TuAaOccupationLevelSelectionModel(
				dao, tuAaSort);

		return sm;
	}

	public SelectModel getDisplacementTypeSelectionModel() {
		return new EnumSelectModel(DisplacementType.class, messages);
	}

	public SelectModel getQuitTypeSelectionModel() {
		return new EnumSelectModel(EmployeeCurrentStatus.class, messages);
	}

	public SelectModel getDepartmentSelectionModel() {
		if (org == null) {
			List<Organization> lst = dao.getListOrganization();
			org = lst.get(0);
		}

		DepartmentSelectionModel sm = new DepartmentSelectionModel(dao, org);

		return sm;
	}

	public SelectModel getOrganizationSelectionModel() {
		OrganizationSelectionModel sm = new OrganizationSelectionModel(dao);

		return sm;
	}

	public SelectModel getQuitJobCauseSelectionModel() {
		if (quitJobType == null) {
			quitJobType = EmployeeCurrentStatus.CHULUULUGDUKH;
		}
		QuitJobCauseSelectionModel sm = new QuitJobCauseSelectionModel(dao,
				quitJobType);

		return sm;
	}

	public SelectModel getOfficiaryCategorySelectionModel() {
		AppointmentSortSelectionModel sm = new AppointmentSortSelectionModel(
				dao, AppointmentType.JUDGE);

		return sm;
	}

	/* Dec 07, 2012 Jargalsuren.S Start */
	public SelectModel getMilitaryOrSimpleSelectionModel() {
		return new EnumSelectModel(MilitaryOrSimple.class, messages);
	}

	/* Dec 07, 2012 Jargalsuren.S End */

	/* getter, setter */

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Displacement getCurrentOccupation() {
		return currentOccupation;
	}

	public void setCurrentOccupation(Displacement currentOccupation) {
		this.currentOccupation = currentOccupation;
	}

	public Displacement getDisplacement() {
		return displacement;
	}

	public void setDisplacement(Displacement displacement) {
		this.displacement = displacement;
	}

	public QuitJob getQuiJob() {
		return quiJob;
	}

	public void setQuiJob(QuitJob quiJob) {
		this.quiJob = quiJob;
	}

	public DisplacementType getSelectedBlockId() {
		return selectedBlockId;
	}

	public void setSelectedBlockId(DisplacementType selectedBlockId) {
		this.selectedBlockId = selectedBlockId;
	}

	public Block getSubBlock() {
		if (selectedBlockId == DisplacementType.SHILJIH) {
			return shiljih;
		} else if (selectedBlockId == DisplacementType.AJLAASGARAH) {
			return garah;
		} else
			return shiljih;
	}

	public DisplacementType getDisplacementTypeSelect() {
		return displacementTypeSelect;
	}

	public void setDisplacementTypeSelect(
			DisplacementType displacementTypeSelect) {
		this.displacementTypeSelect = displacementTypeSelect;
	}

	public String getOccupationRole() {
		if (displacement == null || displacement.getOccupationRole() == null)
			return "";

		return displacement.getOccupationRole().getName();
	}

	public String getOccupationTypeName() {
		if (displacement == null || displacement.getOccupationType() == null)
			return "";

		return displacement.getOccupationType().getName();
	}

	// public String getOccupationName() {
	// if (displacement == null || displacement.getToAppointment() == null)
	// return "";
	// return displacement.getToAppointment().getAppointmentName();
	// }

	public String getUtTzTtTuSortName() {
		if (displacement == null || displacement.getUtTzTtTuLevel() == null)
			return "";

		return displacement.getUtTzTtTuLevel().getUtTzTtTuSort()
				.getNameDetail();
	}

	public String getUtTzTtTuLevelName() {
		if (displacement == null || displacement.getUtTzTtTuLevel() == null)
			return "";

		return displacement.getUtTzTtTuLevel().getUtTzTtTuSort().getName()
				+ "-"
				+ displacement.getUtTzTtTuLevel().getUtTzTtTuRank().getRank()
						.toString();
	}

	public String getTuAaSortName() {
		if (displacement == null || displacement.getTuAaLevel() == null)
			return "";

		return displacement.getTuAaLevel().getTuAaSort().getNameDetail();
	}

	public String getTuAaLevelName() {
		if (displacement == null || displacement.getTuAaLevel() == null)
			return "";

		return displacement.getTuAaLevel().getTuAaSort().getName()
				+ "-"
				+ displacement.getTuAaLevel().getTuAaRank().getRank()
						.toString();
	}

	public String getOfficiaryCategoryName() {
		if (displacement == null || displacement.getOfficiarySort() == null)
			return "";

		return displacement.getOfficiaryCategory();
	}

	public String getOfficiarySortName() {
		if (displacement == null || displacement.getOfficiarySort() == null)
			return "";

		return displacement.getOfficiarySort();
	}

	public String getSalaryNetworkName() {
		if (displacement == null || displacement.getSalaryNetwork() == null)
			return "";

		return displacement.getSalaryNetwork().getSalaryNetworkName();
	}

	public String getsalaryScaleName() {
		if (displacement == null || displacement.getSalaryScale() == null)
			return "";

		return displacement.getSalaryScale().getSalaryScale();
	}

	public String getDispacementCauseTypeName() {

		if (currentOccupation == null
				|| (currentOccupation != null && currentOccupation
						.getDisplacementType() == null))
			return "";

		return messages.get(currentOccupation.getDisplacementType().name());
	}

	public String getDisplacementCauseName() {

		if (currentOccupation != null
				|| (currentOccupation != null && currentOccupation
						.getDisplacementCause() == null))
			return "";

		return currentOccupation.getDisplacementCause();
	}

	public String getIsMainOccupation() {
		if (displacement == null || displacement.getMainOccupation() == null)
			return "";
		if (displacement.getMainOccupation() == true)
			return messages.get("isMainOccupation");

		return messages.get("isTenderer");
	}

//	public Boolean getTuAadisabled() {
//		if (occupationType == null)
//			return false;
//		else {
//			if (utTzTtTuLevel != null
//					&& utTzTtTuSort.getCategory() == UtTzTtTuCategory.TURIINZAHIRGAANII
//					|| utTzTtTuSort.getCategory() == UtTzTtTuCategory.TURIINUILCHILGEENII
//					|| occupationType.getType() == AppointmentSortEmployee.SHUUGCH) {
//				currentOccupation.setTuAaLevel(null);
//				return true;
//			} else
//				return false;
//
//		}
//	}

//	public Boolean getOffdisabled() {
//		if (utTzTtTuSort.getCategory() == UtTzTtTuCategory.TURIINUILCHILGEENII) {
//			currentOccupation.setOfficiarySort(null);
//			return true;
//		} else
//			return false;
//	}

	public Boolean getUtTzDisabled() {
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
