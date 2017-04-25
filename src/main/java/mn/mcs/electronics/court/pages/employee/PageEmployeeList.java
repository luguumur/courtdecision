package mn.mcs.electronics.court.pages.employee;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.Award;
import mn.mcs.electronics.court.entities.configuration.RetireAge;
import mn.mcs.electronics.court.entities.employee.Displacement;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.EmployeeMilitary;
import mn.mcs.electronics.court.entities.employee.Honour;
import mn.mcs.electronics.court.entities.employee.Relatives;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.AwardType;
import mn.mcs.electronics.court.enums.DisplacementCauseType;
import mn.mcs.electronics.court.enums.DisplacementType;
import mn.mcs.electronics.court.enums.EmployeeCurrentStatus;
import mn.mcs.electronics.court.enums.EmployeeDegreeStatus;
import mn.mcs.electronics.court.enums.EmployeeStatus;
import mn.mcs.electronics.court.enums.FamilyStatus;
import mn.mcs.electronics.court.enums.Gender;
import mn.mcs.electronics.court.enums.MilitaryOrSimple;
import mn.mcs.electronics.court.enums.MilitaryRankType;
import mn.mcs.electronics.court.enums.OrganizationTypeExperience;
import mn.mcs.electronics.court.enums.RelativeFamily;
import mn.mcs.electronics.court.enums.TsolOlgohTurul;
import mn.mcs.electronics.court.enums.YesNo;
import mn.mcs.electronics.court.model.AcademicRankSelectionModel;
import mn.mcs.electronics.court.model.AllowanceTypeSelectionModel;
import mn.mcs.electronics.court.model.AppointmentOrgSelectionModel;
import mn.mcs.electronics.court.model.CityProvinceSelectionModel;
import mn.mcs.electronics.court.model.CitySelectionModel;
import mn.mcs.electronics.court.model.CommandSubjectSelectionModel;
import mn.mcs.electronics.court.model.CountrySelectionModel;
import mn.mcs.electronics.court.model.CourtPrizeSelectionModel;
import mn.mcs.electronics.court.model.DegreeTypeSelectionModel;
import mn.mcs.electronics.court.model.GovernmentPrizeSelectionModel;
import mn.mcs.electronics.court.model.JusticeMinistryPrizeSelectionModel;
import mn.mcs.electronics.court.model.MilitaryRankSelectionModel;
import mn.mcs.electronics.court.model.OccupationSelectionModel;
import mn.mcs.electronics.court.model.OccupationTypeSelectionModel;
import mn.mcs.electronics.court.model.OrganizationSelectionModel;
import mn.mcs.electronics.court.model.QuitJobCauseSelectionModel;
import mn.mcs.electronics.court.model.SahilgaShiitgelSelectionModel;
import mn.mcs.electronics.court.model.SahilgaTurulSelectionModel;
import mn.mcs.electronics.court.model.StatePrizeSelectionModel;
import mn.mcs.electronics.court.model.VacationTypeSelectionModel;
import mn.mcs.electronics.court.util.Constants;
import mn.mcs.electronics.court.util.ExcelAPI;
import mn.mcs.electronics.court.util.ReportUtil;
import mn.mcs.electronics.court.util.beans.EmployeeSearchBean;
import mn.mcs.electronics.court.util.beans.GridPager;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.ajax.JavaScriptCallback;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.util.EnumSelectModel;

@Import(library = { "context:js/scroll.js", "context:css/jquery/jquery-1.9.0.js",
		"context:css/jquery/jquery-migrate-1.2.1.js" })
public class PageEmployeeList {

	/*
	 * INJECTS
	 */

	@Inject
	private Response response;

	@Inject
	private Messages messages;

	@Inject
	@Path("context:/images/excel.jpg")
	private Asset imgExcel;

	@Inject
	private CoreDAO dao;

	@Inject
	private Request request;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@SessionState
	private LoginState loginState;

	@InjectComponent
	private Zone shagnalZone, employeeStatusZone, displacementTypeZone, militaryZone, trainingUlsZone,
			ocAppointmentZone;
			// , listZone, formZone

	/*
	 * PERSISTS
	 */

	@Persist
	private EmployeeMilitary valueEmployeeMilitary;

	@Persist
	private EmployeeSearchBean bean;

	@Persist
	private Integer i;

	private RetireAge ra;

	private String size;

	@Property
	@Persist
	private boolean lastname;

	@Property
	@Persist
	private boolean origin1;

	@Property
	@Persist
	private boolean gender;

	@Property
	@Persist
	private boolean email;

	@Property
	@Persist
	private boolean register;

	@Property
	@Persist
	private boolean status;

	@Property
	@Persist
	private boolean phoneNo;

	@Property
	@Persist
	private boolean org;

	@Property
	@Persist
	private boolean occ;

	@Property
	@Persist
	private boolean appointment;

	@Property
	@Persist
	private boolean birthday;

	@Property
	@Persist
	private boolean familyCount;

	@Property
	@Persist
	private boolean childCount;

	@Property
	@Persist
	private boolean CourtWorkedYear;

	@Property
	@Persist
	private boolean TotalWorkedYear;

	@Property
	@Persist
	private boolean currentOrganizationWorkedYear;

	@Property
	@Persist
	private boolean militaryDegree;

	@Property
	@Persist
	private boolean militaryDegreeStatus;

	@Property
	@Persist
	private boolean militaryDegreeDate;

	@Persist
	@Property
	private Award statePrizeName;

	@Persist
	@Property
	private Award justicePrizeName;

	@Persist
	@Property
	private Award courtPrizeName;

	@Persist
	@Property
	private Award governmentPrizeName;

	@Property
	@Persist
	private Honour honour;

	@Property
	@Persist
	private boolean CourtMilitaryWorkedYear;

	@Property
	@Persist
	private boolean CourtSimpleWorkedYear;

	@Property
	@Persist(PersistenceConstants.CLIENT)
	private boolean StateWorkedYear;

	@Persist
	@Property
	private List<Employee> listEmployee;

	@Persist
	@Property
	private GridPager pager;

	/*
	 * PROPERTIES
	 */

	@Property
	private Employee valueEmployee;

	@Property
	private boolean showAwardSearch, showEduSearch, showDemeritSearch, showAllowanceSearch, showMilitarySearch,
			showVacationSearch, showDisplacementSearch, showTrainingSearch;

	@Property
	@Persist
	private boolean isViewVacation;

	@Persist
	private HashSet<Organization> orgs;

	@Persist
	private Boolean orgLoaded;

	private SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);

	void beginRender() {

		if (bean == null)
			bean = new EmployeeSearchBean();

		if (dao.getListRetireAge() != null) {
			ra = dao.getListRetireAge().get(0);
		}

		if (pager == null) {
			pager = new GridPager();

			pager.setFirstRow(0);
			pager.setMaxResult(20);
		}

		if (orgLoaded == null || orgLoaded == false) {
			orgs = new HashSet<Organization>(loginState.getEmployee().getMapEmpOrg());

			if (orgs.isEmpty())
				orgs.add(loginState.getOrganization());

			orgLoaded = true;
		}

		initGrid();

		i = 0;

		/*
		 * Default columns
		 */
		lastname = true;
		register = true;
		org = true;
		appointment = true;
		gender = true;
		email = true;
		militaryDegreeStatus = true;
		militaryDegree = true;

		if (honour == null)
			honour = new Honour();
	}

	void initGrid() {

		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isPermitted("show_all_organization")) {
			listEmployee = dao.getListEmployeeSearch(bean, null, orgs, pager);
		} else {
			HashSet<Organization> org = new HashSet<Organization>();
			org.add(loginState.getUser().getEmployee().getOrganization());

			listEmployee = dao.getListEmployeeSearch(bean, null, org, pager);
		}
	}

	void onSuccessFromColumnForm() {
		// ajaxResponseRenderer.addRender(listZone);
	}

	void onSelectedFromSave() {
		initGrid();
		if (request.isXHR()) {

			// ajaxResponseRenderer.addRender(listZone);
		}
	}

	void onValueChangedFromUlsClick() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(trainingUlsZone);
		}
	}

	void onSelectedFromSearch() {

		// if (request.isXHR()) {
		try {

			if (bean.getAwardType() != null && bean.getAwardType() == AwardType.STATEPRIZE && statePrizeName != null) {
				bean.setAward(statePrizeName.getName().toString());
			}

			if (bean.getAwardType() != null && bean.getAwardType() == AwardType.COURTPRIZE && courtPrizeName != null) {
				bean.setAward(courtPrizeName.getName().toString());
			}

			if (bean.getAwardType() != null && bean.getAwardType() == AwardType.GOVERNMENTPRIZE
					&& governmentPrizeName != null) {
				bean.setAward(governmentPrizeName.getName().toString());
			}

			if (bean.getAwardType() != null && bean.getAwardType() == AwardType.JUSTICE_MINISTRYPRIZE
					&& justicePrizeName != null) {
				bean.setAward(justicePrizeName.getName().toString());
			}

			if (bean.getAwardType() != null && bean.getAwardType() == AwardType.OTHER_ORGANIZATIONPRIZE
					&& honour.getOtherPrize() != null) {
				bean.setAward(honour.getOtherPrize().toString());
			}

			initGrid();
			// listEmployee = dao.getListEmployeeSearch(bean, ra, orgs);
			// ajaxResponseRenderer.addRender(listZone);
			ajaxResponseRenderer.addCallback(new JavaScriptCallback() {
				public void run(JavaScriptSupport javaScriptSupport) {
					javaScriptSupport.addScript("new loaderHide('loader');");
				}
			});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// }

	}

	void onActionFromCancel() {
		this.bean = new EmployeeSearchBean();
		if (request.isXHR()) {
			this.bean = new EmployeeSearchBean();

			initGrid();

			// ajaxResponseRenderer.addRender(formZone).addRender(listZone);

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
			// ajaxResponseRenderer.addRender(listZone);
		}
	}

	void onActionFromNext() {
		pager.setFirstRow(pager.getFirstRow() + 20);
		pager.setMaxResult(pager.getMaxResult() + 20);

		initGrid();
		if (request.isXHR()) {

			// ajaxResponseRenderer.addRender(listZone);
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
			// ajaxResponseRenderer.addRender(listZone);
		}
	}

	void onActionFromLast() {
		pager.setFirstRow(pager.getCount().intValue() - 20);
		pager.setMaxResult(pager.getCount().intValue());

		initGrid();
		if (request.isXHR()) {

			// ajaxResponseRenderer.addRender(listZone);
		}
	}

	Object onValueChangedFromShagnalClick() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(shagnalZone);
		}
		return null;

		// return request.isXHR() ? shagnalZone.getBody() : null;
	}

	Object onValueChangedFromDisplacementTypeClick() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender("displacementTypeZone", displacementTypeZone).addRender("employeeStatusZone",
					employeeStatusZone);
		}
		return null;
		// return request.isXHR() ? displacementTypeZone.getBody() : null;
	}

	Object onValueChangedFromEmployeeStatusClick() {
		return request.isXHR() ? employeeStatusZone.getBody() : null;
	}

	/*
	 * Object onValueChangedFromDisplacementStatusClick() { return
	 * request.isXHR() ? displacementStatusZone.getBody() : null; }
	 */

	/* selection model */
	public SelectModel getOccupationSelectionModel() {
		OccupationSelectionModel sm = new OccupationSelectionModel(dao, null);

		return sm;
	}

	public SelectModel getMilitarySelectionModel() {
		MilitaryRankSelectionModel sm = new MilitaryRankSelectionModel(dao, bean.getMilitaryType());

		return sm;
	}

	public SelectModel getMilitaryTypeSelectionModel() {
		return new EnumSelectModel(MilitaryRankType.class, messages);
	}

	public SelectModel getVacationTypeSelectionModel() {
		VacationTypeSelectionModel sm = new VacationTypeSelectionModel(dao);
		return sm;
	}

	public SelectModel getDisplacementCauseTypeSelectionModel() {

		return new EnumSelectModel(DisplacementCauseType.class, messages);
	}

	public SelectModel getEmployeeDegreeStatusSelectionModel() {
		return new EnumSelectModel(EmployeeDegreeStatus.class, messages);
	}

	public SelectModel getQuitJobChuluulugdukhSelectionModel() {
		QuitJobCauseSelectionModel sm = new QuitJobCauseSelectionModel(dao, EmployeeCurrentStatus.CHULUULUGDUKH);

		return sm;
	}

	public SelectModel getQuitJobKhalagdakhSelectionModel() {
		QuitJobCauseSelectionModel sm = new QuitJobCauseSelectionModel(dao, EmployeeCurrentStatus.KHALAGDAH);

		return sm;
	}

	public SelectModel getQuitJobTetgeverSelectionModel() {
		QuitJobCauseSelectionModel sm = new QuitJobCauseSelectionModel(dao, EmployeeCurrentStatus.TETGEVERTGARAKH);

		return sm;
	}

	public SelectModel getOrganizationSelectionModel() {
		OrganizationSelectionModel sm = new OrganizationSelectionModel(dao);
		return sm;
	}

	public SelectModel getAppointmentSelectionModel() {
		AppointmentOrgSelectionModel sm = new AppointmentOrgSelectionModel(dao, bean.getOccupationType());
		return sm;
	}

	public SelectModel getGenderSelectionModel() {
		return new EnumSelectModel(Gender.class, messages);
	}

	public SelectModel getEducationSelectionModel() {
		return null;
	}

	public SelectModel getStatusSelectionModel() {
		return new EnumSelectModel(EmployeeStatus.class, messages);
	}

	public SelectModel getCurrentStatusSelectionModel() {
		return new EnumSelectModel(EmployeeCurrentStatus.class, messages);
	}

	public SelectModel getquitJobTypeSelectionModel() {
		return new QuitJobCauseSelectionModel(dao, bean.getCurrentStatus());
	}

	public SelectModel getAwardTypeSelectionModel() {
		return new EnumSelectModel(AwardType.class, messages);
	}

	public SelectModel getSahilgaShiitgelSelectionModel() {
		return new SahilgaShiitgelSelectionModel(dao);
	}

	public SelectModel getSahilgaTurulSelectionModel() {
		return new SahilgaTurulSelectionModel(dao);
	}

	public SelectModel getDegreeTypeSelectionModel() {
		return new DegreeTypeSelectionModel(dao);
	}

	public SelectModel getGovernmentPrizeSelectionModel() {
		return new GovernmentPrizeSelectionModel(dao);
	}

	public SelectModel getJusticeMinistryPrizeSelectionModel() {
		return new JusticeMinistryPrizeSelectionModel(dao);
	}

	public SelectModel getCommandSubjectSelectionModel() {
		return new CommandSubjectSelectionModel(dao);
	}

	public SelectModel getStatePrizeSelectionModel() {
		return new StatePrizeSelectionModel(dao, AwardType.STATEPRIZE);
	}

	public SelectModel getCourtPrizeSelectionModel() {
		return new CourtPrizeSelectionModel(dao);
	}

	public SelectModel getYesNoSelectionModel() {
		return new EnumSelectModel(YesNo.class, messages);
	}

	public SelectModel getDisplacementTypeSelectionModel() {
		return new EnumSelectModel(DisplacementType.class, messages);
	}

	void onValueChangedFromMilitaryClick() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender("militaryZone", militaryZone);
		}
	}

	public SelectModel getFamilyStatusSelectionModel() {
		return new EnumSelectModel(FamilyStatus.class, messages);
	}

	public SelectModel getCountrySelectionModel() {
		return new CountrySelectionModel(dao);
	}

	public SelectModel getOccupationTypeSelectionModel() {
		return new OccupationTypeSelectionModel(dao);
	}

	public SelectModel getCitySelectionModel() {
		return new CitySelectionModel(dao, bean.getTrainingCountry());
	}

	public SelectModel getProvinceSelectionModel() {
		return new CityProvinceSelectionModel(dao);
	}

	public SelectModel getAcademicRankSelectionModel() {
		return new AcademicRankSelectionModel(dao);
	}

	public SelectModel getTsolOlgohTurulSelectionModel() {
		return new EnumSelectModel(TsolOlgohTurul.class, messages);
	}

	public SelectModel getAllowanceTypeSelectionModel() {
		return new AllowanceTypeSelectionModel(dao);
	}

	/* Getter setter */

	public int getNumber() {
		return listEmployee.indexOf(valueEmployee) + 1;
	}

	public Asset getImgExcel() {
		return imgExcel;
	}

	public String getOrganizationName() {
		if (valueEmployee.getOrganization() == null)
			return "";
		return valueEmployee.getOrganization().getName();
	}

	public String getOccupationName() {
		if (valueEmployee.getOccupation() == null)
			return "";
		return valueEmployee.getOccupation().getName();
	}

	public String getListSize() {
		return String.valueOf(pager.getCount().intValue());
	}

	// public String getListSize() {
	// if ((listEmployee != null && listEmployee.isEmpty())
	// || listEmployee == null)
	// return "0";
	//
	// return listEmployee.size() + "";
	// }

	public EmployeeSearchBean getBean() {
		return bean;
	}

	public void setBean(EmployeeSearchBean bean) {
		this.bean = bean;
	}

	public String getEmpGender() {
		if (valueEmployee == null || valueEmployee.getGender() == null)
			return "";

		return messages.get(valueEmployee.getGender().toString());
	}

	public String getFirstName() {
		if (valueEmployee == null || valueEmployee.getFirstName() == null)
			return "";

		return valueEmployee.getFirstName();
	}

	public String getBirthDate() {
		if (valueEmployee == null || valueEmployee.getBirthDate() == null)
			return "";

		return format.format(valueEmployee.getBirthDate());
	}

	public String getMilitaryDegreeExport() {
		valueEmployeeMilitary = dao.getEmployeeMilitary(valueEmployee.getId());

		if (valueEmployeeMilitary == null)
			return "";

		return valueEmployeeMilitary.getMilitary().getMilitaryName();
	}

	public String getMilitaryDegreeDateExport() {
		valueEmployeeMilitary = dao.getEmployeeMilitary(valueEmployee.getId());

		if (valueEmployeeMilitary == null)
			return "";

		return format.format(valueEmployeeMilitary.getOlgosonOgnoo());
	}

	public String getMilitaryDegreeStatusExport() {
		valueEmployeeMilitary = dao.getEmployeeMilitary(valueEmployee.getId());

		if (valueEmployeeMilitary == null)
			return "";

		String name = "";

		if (valueEmployeeMilitary.getDegreeStatus().name() == "TSOLNEMEKH")
			name = "Цэргийн цол нэмсэн";
		else if (valueEmployeeMilitary.getDegreeStatus().name() == "TSOLSERGEEKH")
			name = "Цэргийн цол сэргээсэн";
		else if (valueEmployeeMilitary.getDegreeStatus().name() == "TSOLBUURAKH")
			name = "Цэргийн цол буурсан";

		return name;
	}

	public boolean isStatePrize() {
		bean.setAward(null);

		if (bean.getAwardType() == AwardType.STATEPRIZE)
			return true;

		return false;
	}

	public boolean isCourtPrize() {
		bean.setAward(null);
		if (bean.getAwardType() == AwardType.COURTPRIZE)
			return true;

		return false;
	}

	public boolean isGovernmentPrize() {
		bean.setAward(null);
		if (bean.getAwardType() == AwardType.GOVERNMENTPRIZE)
			return true;

		return false;
	}

	public boolean isJusticeMinistryPrize() {
		bean.setAward(null);
		if (bean.getAwardType() == AwardType.JUSTICE_MINISTRYPRIZE)
			return true;

		return false;
	}

	public boolean isOtherOrganizationPrize() {
		bean.setAward(null);
		if (bean.getAwardType() == AwardType.OTHER_ORGANIZATIONPRIZE)
			return true;
		return false;
	}

	public boolean isAllPrize() {
		bean.setAward(null);
		if (bean.getAwardType() == null)
			return true;
		return false;
	}

	public boolean isChuluulugdukh() {
		if (bean.getCurrentStatus() == EmployeeCurrentStatus.CHULUULUGDUKH)
			return true;

		return false;
	}

	public boolean isKhalagdah() {
		if (bean.getCurrentStatus() == EmployeeCurrentStatus.KHALAGDAH)
			return true;

		return false;
	}

	public boolean isTetgever() {
		if (bean.getCurrentStatus() == EmployeeCurrentStatus.TETGEVERTGARAKH)
			return true;

		return false;
	}

	public boolean isEmployeeStatusAll() {
		if (bean.getCurrentStatus() == null)
			return true;
		return false;
	}

	public boolean isQuiteJobDisplacement() {
		if (bean.getDisplacementType() == DisplacementType.AJLAASGARAH)
			return true;
		return false;
	}

	public boolean isMovementDisplacement() {
		if (bean.getDisplacementType() == DisplacementType.SHILJIH)
			return true;
		return false;
	}

	public boolean isDisplacementAll() {
		if (bean.getDisplacementType() == null)
			return true;
		return false;
	}

	public String getAllPrizeText() {
		return "Бүгд";
	}

	public boolean getVisibleCause() {
		if (bean.getDisplacementType() != null) {
			if (bean.getDisplacementType().name().contains("Ажлаас гарах")) {
				return true;
			}
		}
		return false;
	}

	public boolean isMongolia() {
		if (bean.getTrainingCountry() != null && bean.getTrainingCountry().getCountryName().equals("Монгол"))
			return true;
		return false;
	}

	/** ШШГЕГ-т ажилласан жил **/
	public String getCourtOrgTotalWorkedYear() {
		if (valueEmployee == null || valueEmployee.getId() == null)
			return "";
		Long xm = 0l, xs = 0l, yearX = 0l, monthX = 0l, dayX = 0l;
		if (valueEmployee.getId() != null)
			xs = dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(), OrganizationTypeExperience.SHUUH,
					MilitaryOrSimple.workingSimple);

		if (valueEmployee.getId() != null)
			xm = dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(), OrganizationTypeExperience.SHUUH,
					MilitaryOrSimple.workingMilitary);
		dayX = xs + xm;

		yearX = dayX / 365;
		dayX = dayX - yearX * 365;
		monthX = dayX / 30;
		dayX = dayX - monthX * 30;
		return yearX + " жил " + monthX + " сар " + dayX + " хоног";
	}

	public String getTotalOrgWorkedYear() {
		if (valueEmployee == null || valueEmployee.getId() == null)
			return "";
		Long x = 0l, temp = 0l, monthX = 0l, dayX = 0l;
		if (valueEmployee.getId() != null) {
			x = dao.getTotalWorkedYear(valueEmployee.getId());
			temp = x;
		}
		x = x / 365;
		dayX = temp - x * 365;
		monthX = dayX / 30;
		dayX = dayX - monthX * 30;
		return x + " жил " + monthX + " сар " + dayX + " хоног";
	}

	public String getTotalOrgWorkedYearExport(Long empID) {
		if (empID == null)
			return "";
		Long x = 0l, temp = 0l, monthX = 0l, dayX = 0l;
		if (empID != null) {
			x = dao.getTotalWorkedYear(empID);
			temp = x;
		}
		x = x / 365;
		dayX = temp - x * 365;
		monthX = dayX / 30;
		dayX = dayX - monthX * 30;
		return x + " жил " + monthX + " сар " + dayX + " хоног";
	}

	public String getCourtOrgTotalWorkedYearExport(Long empID) {
		if (empID == null)
			return "";
		Long xm = 0l, xs = 0l, yearX = 0l, monthX = 0l, dayX = 0l;
		if (empID != null)
			xs = dao.getMilitaryOrSimpleWorkedYear(empID, OrganizationTypeExperience.SHUUH,
					MilitaryOrSimple.workingSimple);

		if (valueEmployee.getId() != null)
			xm = dao.getMilitaryOrSimpleWorkedYear(empID, OrganizationTypeExperience.SHUUH,
					MilitaryOrSimple.workingMilitary);
		dayX = xs + xm;

		yearX = dayX / 365;
		dayX = dayX - yearX * 365;
		monthX = dayX / 30;
		dayX = dayX - monthX * 30;
		return yearX + " жил " + monthX + " сар " + dayX + " хоног";
	}

	public String getStateWorkedYearExport() {
		if (valueEmployee.getId() == null)
			return "";
		Long x = 0l, temp = 0l, monthX = 0l, dayX = 0l;
		if (valueEmployee.getId() != null) {
			x = dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(), OrganizationTypeExperience.ULSIIN,
					MilitaryOrSimple.workingSimple)
					+ dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(), OrganizationTypeExperience.ULSIIN,
							MilitaryOrSimple.workingMilitary)
					+ dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(), OrganizationTypeExperience.SHUUH,
							MilitaryOrSimple.workingSimple)
					+ dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(), OrganizationTypeExperience.SHUUH,
							MilitaryOrSimple.workingMilitary);
			temp = x;
		}
		x = x / 365;
		dayX = temp - x * 365;
		monthX = dayX / 30;
		dayX = dayX - monthX * 30;
		return x + " жил " + monthX + " сар " + dayX + " хоног";
	}

	public String getStateWorkedYearExport(Long empID) {
		if (empID == null)
			return "";
		Long x = 0l, temp = 0l, monthX = 0l, dayX = 0l;
		if (empID != null) {
			x = dao.getMilitaryOrSimpleWorkedYear(empID, OrganizationTypeExperience.ULSIIN,
					MilitaryOrSimple.workingSimple)
					+ dao.getMilitaryOrSimpleWorkedYear(empID, OrganizationTypeExperience.ULSIIN,
							MilitaryOrSimple.workingMilitary)
					+ dao.getMilitaryOrSimpleWorkedYear(empID, OrganizationTypeExperience.SHUUH,
							MilitaryOrSimple.workingSimple)
					+ dao.getMilitaryOrSimpleWorkedYear(empID, OrganizationTypeExperience.SHUUH,
							MilitaryOrSimple.workingMilitary);
			temp = x;
		}
		x = x / 365;
		dayX = temp - x * 365;
		monthX = dayX / 30;
		dayX = dayX - monthX * 30;
		return x + " жил " + monthX + " сар " + dayX + " хоног";
	}

	public String getCourtMilitaryWorkedYearExport() {
		if (valueEmployee == null || valueEmployee.getId() == null)
			return "";
		Long xm = 0l, yearX = 0l, monthX = 0l, dayX = 0l;

		if (valueEmployee.getId() != null)
			xm = dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(), OrganizationTypeExperience.SHUUH,
					MilitaryOrSimple.workingMilitary);
		dayX = xm;

		yearX = dayX / 365;
		dayX = dayX - yearX * 365;
		monthX = dayX / 30;
		dayX = dayX - monthX * 30;
		return yearX + " жил " + monthX + " сар " + dayX + " хоног";
	}

	public String getCourtMilitaryWorkedYearExport(Long empID) {
		if (empID == null)
			return "";

		Long xm = 0l, yearX = 0l, monthX = 0l, dayX = 0l;

		xm = dao.getMilitaryOrSimpleWorkedYear(empID, OrganizationTypeExperience.SHUUH,
				MilitaryOrSimple.workingMilitary);
		dayX = xm;

		yearX = dayX / 365;
		dayX = dayX - yearX * 365;
		monthX = dayX / 30;
		dayX = dayX - monthX * 30;
		return yearX + " жил " + monthX + " сар " + dayX + " хоног";
	}

	public String getCourtSimpleWorkedYearExport() {
		if (valueEmployee == null || valueEmployee.getId() == null)
			return "";
		Long xs = 0l, yearX = 0l, monthX = 0l, dayX = 0l;
		if (valueEmployee.getId() != null)
			xs = dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(), OrganizationTypeExperience.SHUUH,
					MilitaryOrSimple.workingSimple);

		dayX = xs;

		yearX = dayX / 365;
		dayX = dayX - yearX * 365;
		monthX = dayX / 30;
		dayX = dayX - monthX * 30;
		return yearX + " жил " + monthX + " сар " + dayX + " хоног";
	}

	public String getCourtSimpleWorkedYearExport(Long empID) {
		if (empID == null)
			return "";

		Long xs = 0l, yearX = 0l, monthX = 0l, dayX = 0l;

		xs = dao.getMilitaryOrSimpleWorkedYear(empID, OrganizationTypeExperience.SHUUH, MilitaryOrSimple.workingSimple);

		dayX = xs;

		yearX = dayX / 365;
		dayX = dayX - yearX * 365;
		monthX = dayX / 30;
		dayX = dayX - monthX * 30;
		return yearX + " жил " + monthX + " сар " + dayX + " хоног";
	}

	public int getFamilyCountExport() {
		List<Relatives> rels = dao.getRelatives(valueEmployee.getId(), RelativeFamily.ISFAMILY);
		return rels.size();
	}

	public String getOriginExport() {
		if (valueEmployee.getOrigin() == null)
			return "";
		return valueEmployee.getOrigin().getName();
	}

	public String getAppointmentName() {
		if (valueEmployee != null) {

			Displacement dis = dao.getCurrentOccupation(valueEmployee);

			if (dis != null && dis.getAppointment() != null)
				return dao.getCurrentOccupation(valueEmployee).getAppointment().getAppointmentName();
		}

		return "";
	}

	public String getFamilyCountExport(Long empID) {
		List<Relatives> rels = dao.getRelatives(empID, RelativeFamily.ISFAMILY);

		return rels.size() + 1 + "";
	}

	public int getChildCountExport() {
		List<Relatives> rels = dao.getRelatives(valueEmployee.getId(), RelativeFamily.ISFAMILY);
		int childCount = 0;

		for (int i = 0; i < rels.size(); i++) {
			if (rels.get(i).getRelation().getName().equals("Хүү")
					|| rels.get(i).getRelation().getName().equals("Охин")) {
				childCount++;
			}
		}
		return childCount;
	}

	public String getChildCountExport(Long empID) {
		List<Relatives> rels = dao.getRelatives(empID, RelativeFamily.ISFAMILY);
		int childCount = 0;

		for (int i = 0; i < rels.size(); i++) {
			if (rels.get(i).getRelation().getName().equals("Хүү")
					|| rels.get(i).getRelation().getName().equals("Охин")) {
				childCount++;
			}
		}
		return childCount + "";
	}

	public String getSize() {
		return size;
	}

	void onValueChangedFromOccupationType() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(ocAppointmentZone);
		}
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getGridColumn() {
		String str = "firstName";

		if (lastname)
			str += ",lastname";

		if (gender)
			str += ",gender";

		if (email)
			str += ",eMail";

		if (register)
			str += ",registerNo";

		if (status)
			str += ",employeeStatus";

		if (phoneNo)
			str += ",phoneNo";

		if (occ)
			str += ",occupation";

		if (birthday)
			str += ",birthdate";

		return str;
	}

	public String getAddedColumn() {
		String str = "number";

		if (org)
			str += ",organizationName";

		if (appointment)
			str += ",appointmentName";

		if (TotalWorkedYear)
			str += ",totalOrgWorkedYear";

		if (StateWorkedYear)
			str += ",stateWorkedYear";

		if (currentOrganizationWorkedYear)
			str += ",currentOrgWorkedYear";

		if (CourtWorkedYear)
			str += ",courtOrgTotalWorkedYear";

		if (CourtMilitaryWorkedYear)
			str += ",courtMilitaryWorkedYear";

		if (CourtSimpleWorkedYear)
			str += ",courtSimpleWorkedYear";

		if (origin1)
			str += ",origin1";

		if (familyCount)
			str += ", familyCount";

		if (childCount)
			str += ", childCount";

		if (militaryDegree)
			str += ", militaryDegree";

		if (militaryDegreeDate)
			str += ", militaryDegreeDate";

		if (militaryDegreeStatus)
			str += ", militaryDegreeStatus";

		return str;
	}

	public JSONObject getParam() {
		JSONObject defaults = new JSONObject();
		defaults.put("modal", true);
		defaults.put("resizable", false);
		defaults.put("draggable", true);
		defaults.put("autoOpen", false);
		defaults.put("width", 300);
		defaults.put("top", 50);
		return defaults;
	}

	/* Export to excel */
	void onActionFromExport() {
		try {
			HSSFWorkbook document = new HSSFWorkbook();
			HSSFSheet sheet = ReportUtil.createSheet(document);

			sheet.setMargin((short) 0, 0.5);
			ReportUtil.setColumnWidths(sheet, 0, 0.5, 1, 0.5, 2, 2, 3, 2, 3, 2, 4, 3, 5, 2, 6, 2, 7, 2, 8, 3, 9, 3, 10,
					3, 11, 2, 12, 2);

			Map<String, HSSFCellStyle> styles = ReportUtil.createStyles(document);

			int sheetNumber = 0;
			int rowIndex = 1;
			int colIndex = 1;
			Long index = 1L;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2, messages.get("empList"), styles.get("title"), 5);

			ExcelAPI.setCellValue(document, sheetNumber, ++rowIndex, 1,
					messages.get("date") + ": " + format.format(new Date()), styles.get("plain-left-wrap"), 5);
			rowIndex += 2;

			/* column headers */

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1, messages.get("number-label"),
					styles.get("header-wrap"));

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2, messages.get("firstname-label"),
					styles.get("header-wrap"));

			if (lastname) {
				i++;
				ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2 + i, messages.get("lastname-label"),
						styles.get("header-wrap"));
			}

			if (origin1) {
				i++;
				ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2 + i, messages.get("persuasion-label"),
						styles.get("header-wrap"));
			}

			if (register) {
				i++;
				ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2 + i, messages.get("register-label"),
						styles.get("header-wrap"));
			}

			if (status) {
				i++;
				ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2 + i, messages.get("status-label"),
						styles.get("header-wrap"));
			}

			if (gender) {
				i++;
				ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2 + i, messages.get("gender-label"),
						styles.get("header-wrap"));
			}

			if (occ) {
				i++;
				ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2 + i, messages.get("occupation-label"),
						styles.get("header-wrap"));
			}
			if (birthday) {
				i++;
				ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2 + i, messages.get("birthDate-label"),
						styles.get("header-wrap"));
			}
			if (phoneNo) {
				i++;
				ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2 + i, messages.get("phoneNo-label"),
						styles.get("header-wrap"));
			}
			if (email) {
				i++;
				ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2 + i, messages.get("email-label"),
						styles.get("header-wrap"));
			}
			if (org) {
				i++;
				ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2 + i, messages.get("organization-label"),
						styles.get("header-wrap"));
			}
			if (appointment) {
				i++;
				ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2 + i, messages.get("appointment-label"),
						styles.get("header-wrap"));
			}
			if (militaryDegree) {
				i++;
				ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2 + i, "Цэргийн цол", styles.get("header-wrap"));
			}
			if (militaryDegreeStatus) {
				i++;
				ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2 + i, "Цолны статус",
						styles.get("header-wrap"));
			}
			if (militaryDegreeDate) {
				i++;
				ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2 + i, "Цол авсан огноо",
						styles.get("header-wrap"));
			}
			if (TotalWorkedYear) {
				i++;
				ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2 + i, messages.get("TotalOrgWorkedYear-label"),
						styles.get("header-wrap"));
			}

			if (StateWorkedYear) {
				i++;
				ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2 + i, messages.get("stateWorkedYear-label"),
						styles.get("header-wrap"));
			}

			if (CourtWorkedYear) {
				i++;
				ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2 + i,
						messages.get("courtOrgTotalWorkedYear-label"), styles.get("header-wrap"));
			}

			if (CourtMilitaryWorkedYear) {
				i++;
				ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2 + i,
						messages.get("CourtMilitaryWorkedYear-label"), styles.get("header-wrap"));
			}

			if (CourtSimpleWorkedYear) {
				i++;
				ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2 + i,
						messages.get("CourtSimpleWorkedYear-label"), styles.get("header-wrap"));
			}

			if (familyCount) {
				i++;
				ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2 + i, messages.get("familyCount-label"),
						styles.get("header-wrap"));
			}
			if (childCount) {
				i++;
				ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2 + i, messages.get("childCount-label"),
						styles.get("header-wrap"));
			}

			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;
			if (listEmployee != null)
				for (Employee empDTO : listEmployee) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++,
							listEmployee.indexOf(empDTO) + 1 + "", styles.get("plain-left-wrap-border"));

					if (lastname) {
						ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++,
								(empDTO.getLastname() != null) ? empDTO.getLastname() : "",
								styles.get("plain-left-wrap-border"));
					}

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++, empDTO.getFirstName(),
							styles.get("plain-left-wrap-border"));
					
					if (origin1) {
						ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++,
								(empDTO.getOrigin().getName() != null) ? empDTO.getOrigin().getName() : "",
								styles.get("plain-left-wrap-border"));
					}
					if (register) {
						ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++,
								(empDTO.getRegisterNo() != null) ? empDTO.getRegisterNo() : "",
								styles.get("plain-left-wrap-border"));
					}

					if (status) {
						ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++,
								(empDTO.getEmployeeStatus() != null) ? empDTO.getEmployeeStatus().name() : "",
								styles.get("plain-left-wrap-border"));
					}

					if (gender) {
						ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++,
								messages.get((empDTO.getGender() != null) ? empDTO.getGender().toString() : ""),
								styles.get("plain-left-wrap-border"));
					}

					if (occ) {
						ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++,
								(empDTO.getOccupation() != null) ? empDTO.getOccupation().getName() : "",
								styles.get("plain-left-wrap-border"));
					}

					if (birthday) {
						ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++,
								((empDTO.getBirthDate() != null) ? format.format(empDTO.getBirthDate()) : ""),
								styles.get("plain-left-wrap-border"));
					}

					if (phoneNo) {
						ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++,
								((empDTO.getPhoneNo() != null) ? empDTO.getPhoneNo() : ""),
								styles.get("plain-left-wrap-border"));
					}

					if (email) {
						ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++,
								((empDTO.geteMail() != null) ? empDTO.geteMail() : ""),
								styles.get("plain-left-wrap-border"));
					}

					if (org) {
						ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++,
								((empDTO.getOrganization() != null) ? empDTO.getOrganization().getName() : ""),
								styles.get("plain-left-wrap-border"));
					}

					if (appointment) {
						ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++,
								((empDTO.getAppointment() != null) ? empDTO.getAppointment().getAppointmentName() : ""),
								styles.get("plain-left-wrap-border"));
					}
					if (militaryDegree) {
						ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++,
								((dao.getEmployeeMilitary(empDTO.getId()) != null)
										? dao.getEmployeeMilitary(empDTO.getId()).getMilitary().getMilitaryName() : ""),
								styles.get("plain-left-wrap-border"));
					}
					if (militaryDegreeStatus) {
						ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++,
								((dao.getEmployeeMilitary(empDTO.getId()) != null)
										? dao.getEmployeeMilitary(empDTO.getId()).getDegreeStatus().name() : ""),
								styles.get("plain-left-wrap-border"));
					}
					if (militaryDegreeDate) {
						ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++,
								((dao.getEmployeeMilitary(empDTO.getId()) != null)
										? format.format(dao.getEmployeeMilitary(empDTO.getId()).getOlgosonOgnoo())
										: ""),
								styles.get("plain-left-wrap-border"));
					}
					if (TotalWorkedYear) {
						ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++,
								((getTotalOrgWorkedYearExport(empDTO.getId()) != null)
										? getTotalOrgWorkedYearExport(empDTO.getId()).toString() : ""),
								styles.get("plain-left-wrap-border"));
						if (StateWorkedYear) {
							ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++,
									((getStateWorkedYearExport(empDTO.getId()) != null)
											? getStateWorkedYearExport(empDTO.getId()).toString() : ""),
									styles.get("plain-left-wrap-border"));
						}
					}
					if (CourtWorkedYear) {
						ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++,
								((getCourtOrgTotalWorkedYearExport(empDTO.getId()) != null)
										? getCourtOrgTotalWorkedYearExport(empDTO.getId()).toString() : ""),
								styles.get("plain-left-wrap-border"));
					}
					if (CourtMilitaryWorkedYear) {
						ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++,
								((getCourtMilitaryWorkedYearExport(empDTO.getId()) != null)
										? getCourtMilitaryWorkedYearExport(empDTO.getId()).toString() : ""),
								styles.get("plain-left-wrap-border"));
					}

					if (CourtSimpleWorkedYear) {
						ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++,
								((getCourtSimpleWorkedYearExport(empDTO.getId()) != null)
										? getCourtSimpleWorkedYearExport(empDTO.getId()).toString() : ""),
								styles.get("plain-left-wrap-border"));
					}

					if (familyCount) {
						ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
								colIndex++, ((getFamilyCountExport(empDTO.getId()) != null)
										? getFamilyCountExport(empDTO.getId()) : "0"),
								styles.get("plain-left-wrap-border"));
					}

					if (childCount) {
						ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
								colIndex++, ((getChildCountExport(empDTO.getId()) != null)
										? getChildCountExport(empDTO.getId()) : "0"),
								styles.get("plain-left-wrap-border"));
					}

					ReportUtil.setRowHeight(sheet, rowIndex, 3);
					rowIndex++;
					index++;
					colIndex = 1;

				}

			rowIndex += 2;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1,
					"ТАЙЛАН ГАРГАСАН: " + ".....................................  / "
							+ loginState.getEmployee().getLastname().charAt(0) + "."
							+ loginState.getEmployee().getFirstName() + " /",
					styles.get("plain-left-wrap"), 8);
			rowIndex++;

			OutputStream out = response.getOutputStream("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=\"employeeList.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
