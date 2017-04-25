package mn.mcs.electronics.court.pages;

import java.util.HashSet;
import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.EmployeeStatus;
import mn.mcs.electronics.court.enums.ExcelTypes;
import mn.mcs.electronics.court.model.AppointmentOrgSelectionModel;
import mn.mcs.electronics.court.model.OccupationTypeSelectionModel;
import mn.mcs.electronics.court.model.OrganizationSelectionModel;
import mn.mcs.electronics.court.util.beans.EmployeeSearchBean;
import mn.mcs.electronics.court.util.beans.GridPager;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.ajax.JavaScriptCallback;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.util.EnumSelectModel;
import org.tynamo.routing.annotations.At;

/**
 * Start page of application example.
 */
@At("/")
public class Home {

	/*
	 * STATES
	 */

	@SessionState
	private LoginState loginState;

	/*
	 * INJECTS
	 */

	@Inject
	private CoreDAO dao;

	@Inject
	private Messages messages;

	@Inject
	private AlertManager manager;

	@Inject
	private Request request;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@InjectComponent
	private Zone formZone, listZone, appointmentTypeZone;

	/*
	 * PERSISTS
	 */

	@Persist
	private EmployeeSearchBean bean;

	@Persist
	@Property
	private Organization organization;

	@Persist
	@Property
	private ExcelTypes excelType;

	@Persist
	@Property
	private GridPager pager;

	/*
	 * PROPERTIES
	 */

	@Persist
	@Property
	private List<Employee> listEmployee;

	@Property
	private Employee valueEmployee;

	@Property
	private String strRow;

	@Persist
	private HashSet<Organization> orgs;

	@Persist
	private Boolean orgLoaded;

	void beginRender() {

		if (pager == null) {
			pager = new GridPager();

			pager.setFirstRow(0);
			pager.setMaxResult(20);
		}

		if (bean == null)
			bean = new EmployeeSearchBean();

		initGrid();
	}

	void initGrid() {
		// Одоо ажиллаж буй ажилчдын жагсаалт
		bean.setStatus(EmployeeStatus.WORKING);

		Subject currentUser = SecurityUtils.getSubject();

		if (orgLoaded == null || orgLoaded == false) {
			orgs = new HashSet<Organization>(loginState.getEmployee()
					.getMapEmpOrg());

			if (orgs.isEmpty())
				orgs.add(loginState.getOrganization());

			orgLoaded = true;
		}
		System.err.println(currentUser.isPermitted("show_all_organization"));
		if (currentUser.isPermitted("show_all_organization")) {
			listEmployee = dao.getListEmployeeSearch(bean, null, orgs, pager);
			
		} else {
			listEmployee = dao.getListEmployeeSearch(bean, null, orgs, pager);
		}
	}

	public EmployeeSearchBean getBean() {
		return bean;
	}

	public void setBean(EmployeeSearchBean bean) {
		this.bean = bean;
	}

	void onSelectedFromSearch() {
		if (request.isXHR()) {
			initGrid();
			ajaxResponseRenderer.addRender(listZone);

			ajaxResponseRenderer.addCallback(new JavaScriptCallback() {
				public void run(JavaScriptSupport javaScriptSupport) {
					javaScriptSupport.addScript("new loaderHide('loader');");
				}
			});

		}
	}

	void onActionFromCancel() {
		if (request.isXHR()) {
			this.bean = new EmployeeSearchBean();

			initGrid();

			ajaxResponseRenderer.addRender(formZone).addRender(listZone);

			ajaxResponseRenderer.addCallback(new JavaScriptCallback() {
				public void run(JavaScriptSupport javaScriptSupport) {
					javaScriptSupport.addScript("new loaderHide('loader');");
				}
			});
		}
	}

	void onActionFromFirst() {
		if (request.isXHR()) {
			pager.setFirstRow(0);
			pager.setMaxResult(20);

			initGrid();
			ajaxResponseRenderer.addRender(listZone);
		}
	}

	void onActionFromNext() {
		if (request.isXHR()) {
			pager.setFirstRow(pager.getFirstRow() + 20);
			pager.setMaxResult(pager.getMaxResult() + 20);

			initGrid();
			ajaxResponseRenderer.addRender(listZone);
		}
	}

	void onActionFromPrev() {
		if (request.isXHR()) {

			if (pager.getFirstRow() >= 20)
				pager.setFirstRow(pager.getFirstRow() - 20);
			else
				pager.setFirstRow(0);

			if (pager.getFirstRow() >= 40)
				pager.setMaxResult(pager.getMaxResult() - 20);
			else
				pager.setMaxResult(20);

			initGrid();
			ajaxResponseRenderer.addRender(listZone);
		}
	}

	void onActionFromLast() {
		if (request.isXHR()) {
			pager.setFirstRow(pager.getCount().intValue() - 20);
			pager.setMaxResult(pager.getCount().intValue());

			initGrid();
			ajaxResponseRenderer.addRender(listZone);
		}
	}

	void onValueChangedFromOccupationType() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(appointmentTypeZone);
		}
	}

	@CommitAfter
	void onActionFromDelete(Employee emp) {
//		System.err.println("delete");
		// try {
		dao.deleteObject(emp);
		manager.alert(Duration.SINGLE, Severity.INFO,
				messages.get("successMessageDelete"));
		// } catch (Exception ex) {
		// manager.alert(Duration.SINGLE, Severity.WARN,
		// messages.get("unSuccessMessageDelete"));
		// }
	}

	/*
	 * METHODS
	 */

	public String getListSize() {
		return String.valueOf(pager.getCount().intValue());
	}

	public String getNumber() {
		return listEmployee.indexOf(valueEmployee) + 1 + "";
	}

	public JSONObject getParam() {
		JSONObject defaults = new JSONObject();
		defaults.put("modal", true);
		defaults.put("resizable", true);
		defaults.put("draggable", true);
		defaults.put("position", "center");
		defaults.put("width", 800);
		defaults.put("title", messages.get("ImportExcel"));
		return defaults;
	}

	public Block getActiveBlock() {
		return null;
	}

	public String getExcelNumber() {
		if (valueEmployee != null && valueEmployee.getEmpNumber() != null)
			return valueEmployee.getEmpNumber().toString();
		return "-";
	}

	/* SELECT MODEL */

	public SelectModel getOrganizationSelectionModel() {
		return new OrganizationSelectionModel(dao);
	}

	public SelectModel getAppointmentSelectionModel() {
		return new AppointmentOrgSelectionModel(dao, bean.getOccupationType());
	}

	public SelectModel getExcelTypeSelectionModel() {
		return new EnumSelectModel(ExcelTypes.class, messages);
	}

	public SelectModel getOccupationTypeSelectionModel() {
		return new OccupationTypeSelectionModel(dao);
	}

	// @CommitAfter
	// void deleteConstraints(Employee emp) {
	// List<Addition> listAdd = dao.getAnyList(Addition.class, "employee.id",
	// emp.getId());
	//
	// for (Addition empAddition : listAdd) {
	// dao.deleteObject(empAddition);
	// }
	//
	// List<Allowance> listAllowances = dao.getAnyList(Allowance.class,
	// "employee.id", emp.getId());
	//
	// for (Allowance allowance : listAllowances) {
	// dao.deleteObject(allowance);
	// }
	//
	// List<Computer> listComputers = dao.getAnyList(Computer.class,
	// "employee.id", emp.getId());
	//
	// for (Computer computer : listComputers) {
	// dao.deleteObject(computer);
	// }
	//
	// List<ComputerOther> listComputerOthers = dao.getAnyList(
	// ComputerOther.class, "employee.id", emp.getId());
	//
	// for (ComputerOther computerOther : listComputerOthers) {
	// dao.deleteObject(computerOther);
	// }
	//
	// List<CurrentOccupation> listCurrentOccupations = dao.getAnyList(
	// CurrentOccupation.class, "employee.id", emp.getId());
	//
	// for (CurrentOccupation currentOccupation : listCurrentOccupations) {
	// dao.deleteObject(currentOccupation);
	// }
	//
	// List<DegreeGrade> listDegreeGrades = dao.getAnyList(DegreeGrade.class,
	// "employee.id", emp.getId());
	//
	// for (DegreeGrade degreeGrade : listDegreeGrades) {
	// dao.deleteObject(degreeGrade);
	// }
	//
	// List<DegreeGradeRank> listDegreeGradeRanks = dao.getAnyList(
	// DegreeGradeRank.class, "employee.id", emp.getId());
	//
	// for (DegreeGradeRank degreeGradeRank : listDegreeGradeRanks) {
	// dao.deleteObject(degreeGradeRank);
	// }
	//
	// List<Degrees> listDegrees = dao.getAnyList(Degrees.class,
	// "employee.id", emp.getId());
	//
	// for (Degrees degrees : listDegrees) {
	// dao.deleteObject(degrees);
	// }
	//
	// List<Demerit> listDemerits = dao.getAnyList(Demerit.class,
	// "employee.id", emp.getId());
	//
	// for (Demerit demerit : listDemerits) {
	// dao.deleteObject(demerit);
	// }
	//
	// List<Displacement> listDisplacements = dao.getAnyList(
	// Displacement.class, "employee.id", emp.getId());
	//
	// for (Displacement displacement : listDisplacements) {
	// dao.deleteObject(displacement);
	// }
	//
	// List<Educations> educations = dao.getAnyList(Educations.class,
	// "employee.id", emp.getId());
	//
	// for (Educations education : educations) {
	// dao.deleteObject(education);
	// }
	//
	// List<EmployeeMilitary> militaries = dao.getAnyList(
	// EmployeeMilitary.class, "employee.id", emp.getId());
	//
	// for (EmployeeMilitary employeeMilitary : militaries) {
	// dao.deleteObject(employeeMilitary);
	// }
	//
	// List<EmployeeSalaryHistory> employeeSalaryHistories = dao.getAnyList(
	// EmployeeSalaryHistory.class, "employee.id", emp.getId());
	//
	// for (EmployeeSalaryHistory employeeSalaryHistory :
	// employeeSalaryHistories) {
	// dao.deleteObject(employeeSalaryHistory);
	// }
	//
	// List<Experience> experiences = dao.getAnyList(Experience.class,
	// "employee.id", emp.getId());
	//
	// for (Experience experience : experiences) {
	// dao.deleteObject(experience);
	// }
	//
	// List<Honour> honours = dao.getAnyList(Honour.class, "employee.id",
	// emp.getId());
	//
	// for (Honour honour : honours) {
	// dao.deleteObject(honour);
	// }
	//
	// List<Language> languages = dao.getAnyList(Language.class,
	// "employee.id", emp.getId());
	//
	// for (Language language : languages) {
	// dao.deleteObject(language);
	// }
	//
	// List<OfficeEquipment> equipments = dao.getAnyList(
	// OfficeEquipment.class, "employee.id", emp.getId());
	//
	// for (OfficeEquipment equipment : equipments) {
	// dao.deleteObject(equipment);
	// }
	//
	// List<Passport> passports = dao.getAnyList(Passport.class,
	// "employee.id", emp.getId());
	//
	// for (Passport passport : passports) {
	// dao.deleteObject(passport);
	// }
	//
	// List<QuitJob> quitJobs = dao.getAnyList(QuitJob.class, "employee.id",
	// emp.getId());
	//
	// for (QuitJob quitJob : quitJobs) {
	// dao.deleteObject(quitJob);
	// }
	//
	// List<Relatives> relatives = dao.getAnyList(Relatives.class,
	// "employee.id", emp.getId());
	//
	// for (Relatives relative : relatives) {
	// dao.deleteObject(relative);
	// }
	//
	// List<ResultContract> resultContracts = dao.getAnyList(
	// ResultContract.class, "employee.id", emp.getId());
	//
	// for (ResultContract resultContract : resultContracts) {
	// dao.deleteObject(resultContract);
	// }
	//
	// List<Sahilga> sahilgas = dao.getAnyList(Sahilga.class, "employee.id",
	// emp.getId());
	//
	// for (Sahilga sahilga : sahilgas) {
	// dao.deleteObject(sahilga);
	// }
	//
	// List<SalaryHistory> salaryHistories = dao.getAnyList(
	// SalaryHistory.class, "employee.id", emp.getId());
	//
	// for (SalaryHistory salaryHistory : salaryHistories) {
	// dao.deleteObject(salaryHistory);
	// }
	//
	// List<Skills> skills = dao.getAnyList(Skills.class, "employee.id",
	// emp.getId());
	//
	// for (Skills skill : skills) {
	// dao.deleteObject(skill);
	// }
	//
	// List<Training> trainings = dao.getAnyList(Training.class,
	// "employee.id", emp.getId());
	//
	// if (trainings != null && trainings.size() > 0) {
	// for (Training training : trainings) {
	// dao.deleteObject(training);
	// }
	// }
	//
	// List<TravelAbroad> travelAbroads = dao.getAnyList(TravelAbroad.class,
	// "employee.id", emp.getId());
	//
	// for (TravelAbroad travelAbroad : travelAbroads) {
	// dao.deleteObject(travelAbroad);
	// }
	//
	// List<Vacation> vacations = dao.getAnyList(Vacation.class,
	// "employee.id", emp.getId());
	//
	// for (Vacation vacation : vacations) {
	// dao.deleteObject(vacation);
	// }
	//
	// List<MapEmployeeOrganization> mapEmployeeOrganizations = dao
	// .getAnyList(MapEmployeeOrganization.class, "employee.id",
	// emp.getId());
	//
	// for (MapEmployeeOrganization mapEmployeeOrganization :
	// mapEmployeeOrganizations) {
	// dao.deleteObject(mapEmployeeOrganization);
	// }
	//
	// List<LoginHistory> loginHistories = dao.getAnyList(LoginHistory.class,
	// "employee.id", emp.getId());
	//
	// for (LoginHistory loginHistory : loginHistories) {
	// dao.deleteObject(loginHistory);
	// }
	//
	// List<User> users = dao.getAnyList(User.class, "employee.id",
	// emp.getId());
	//
	// if (!users.isEmpty() && users.size() > 0) {
	// User user = users.get(0);
	// user.setAccountLocked(true);
	// user.setEmployee(null);
	// dao.saveOrUpdateObject(user);
	// }
	//
	// }

}