package mn.mcs.electronics.court.pages.employee;

import java.util.ArrayList;
import java.util.List;

import mn.mcs.electronics.court.components.LayoutEmployee;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.Role;
import mn.mcs.electronics.court.entities.User;
import mn.mcs.electronics.court.entities.configuration.City;
import mn.mcs.electronics.court.entities.configuration.RelativeType;
import mn.mcs.electronics.court.entities.employee.Computer;
import mn.mcs.electronics.court.entities.employee.DegreeGrade;
import mn.mcs.electronics.court.entities.employee.DegreeGradeRank;
import mn.mcs.electronics.court.entities.employee.Degrees;
import mn.mcs.electronics.court.entities.employee.Displacement;
import mn.mcs.electronics.court.entities.employee.Educations;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.Experience;
import mn.mcs.electronics.court.entities.employee.Language;
import mn.mcs.electronics.court.entities.employee.Relatives;
import mn.mcs.electronics.court.entities.employee.Skills;
import mn.mcs.electronics.court.entities.employee.Training;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.EmployeeStatus;
import mn.mcs.electronics.court.enums.Gender;
import mn.mcs.electronics.court.model.AscriptionSelectionModel;
import mn.mcs.electronics.court.model.CityProvinceSelectionModel;
import mn.mcs.electronics.court.model.CountrySelectionModel;
import mn.mcs.electronics.court.model.DegreeTypeSelectionModel;
import mn.mcs.electronics.court.model.DistrictSumSelectionModel;
import mn.mcs.electronics.court.model.OccupationSelectionModel;
import mn.mcs.electronics.court.model.OriginSelectionModel;
import mn.mcs.electronics.court.model.RelativeTypeSelectionModel;
import mn.mcs.electronics.court.model.RoleSelectionModel;
import mn.mcs.electronics.court.util.UserUtil;
import mn.mcs.electronics.court.util.beans.EmployeeSearchBean;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

public class PageEmployeeDetail {

	@Inject
	private CoreDAO dao;

	@InjectComponent
	private LayoutEmployee layoutEmployee;

	@Persist
	private Object item;

	@Persist
	private Employee employee;

	@InjectPage
	private PageEmployeeList pageEmployeeList;

	@Persist
	private Organization organization;

	@Persist
	private Displacement currentOccupation;

	@Property
	@Persist
	private EmployeeSearchBean bean;

	@Persist
	private Educations educations;

	@Persist
	private Boolean isView;

	@Persist
	private String memberType;

	@Persist
	private Educations currentEducation;

	@Persist
	private String currentDegree;

	@Persist
	private List<Relatives> listRelative;

	@Persist
	private RelativeType relativeType;

	@Persist
	private List<Educations> listEducations;

	@Persist
	private List<Degrees> listDegrees;

	@Persist
	private List<DegreeGradeRank> listDegreeGradeRank;

	@Persist
	private List<Training> listTraining;

	@Persist
	private List<Experience> listExperience;

	@Persist
	private List<Employee> listEmployee;

	@Persist
	private List<Skills> listSkills;

	@Persist
	private List<Language> listLanguage;

	@Persist
	private List<DegreeGrade> listDegreeGrade;

	@Persist
	private List<Computer> listComputer;

	@Persist
	private Degrees degrees;

	@Persist
	private Language language;

	@Persist
	private boolean viewEmployee;

	@Property
	@Persist
	private City city;

	@Property
	@Persist
	private Role empRole;

	@InjectComponent
	private Zone aimagZone;

	@InjectComponent
	private Zone aimagHotZone;

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

		this.employee = (Employee) dao.getObject(Employee.class,
				((empID != null) ? empID : null));

		layoutEmployee.setValueEmployee(employee);

		// layoutEmployee.setValueEmployee(getEmployee());

		layoutEmployee.setOrganization(getOrganization());

		// layoutEmployee.setIsView(getIsView());

		if (employee == null) {
			employee = new Employee();

		}
		// if(cccMember == null)
		// cccMember = new CCCMember();
		//
		// if(gccMember == null)
		// gccMember = new GCCMember();
		//
		// if(dccMember == null)
		// dccMember = new DCCMember();

		if (relativeType == null)
			relativeType = new RelativeType();

		if (educations == null)
			educations = new Educations();

		if (currentOccupation == null)
			currentOccupation = new Displacement();

		if (listRelative == null)
			listRelative = new ArrayList<Relatives>();

		if (listEducations == null)
			listEducations = new ArrayList<Educations>();

		if (listDegreeGradeRank == null)
			listDegreeGradeRank = new ArrayList<DegreeGradeRank>();

		if (listDegrees == null)
			listDegrees = new ArrayList<Degrees>();

		if (listTraining == null)
			listTraining = new ArrayList<Training>();

		if (listExperience == null)
			listExperience = new ArrayList<Experience>();

		if (listEmployee == null)
			listEmployee = new ArrayList<Employee>();

		if (listDegreeGrade == null)
			listDegreeGrade = new ArrayList<DegreeGrade>();

		if (listLanguage == null)
			listLanguage = new ArrayList<Language>();

		if (listComputer == null)
			listComputer = new ArrayList<Computer>();

		if (listSkills == null)
			listSkills = new ArrayList<Skills>();

		if (educations == null)
			educations = new Educations();

		if (degrees == null)
			degrees = new Degrees();

		if (relativeType == null)
			relativeType = new RelativeType();

		if (bean == null)
			bean = new EmployeeSearchBean();

		if (degrees == null)
			degrees = new Degrees();

		if (degrees == null)
			degrees = new Degrees();

		currentOccupation.setEmployee(employee);

		if (memberType == null)
			memberType = "search";

		currentEducation = dao.getCurrentEducation(employee.getId());

		if (currentEducation != null
				&& currentEducation.getDegreeType() != null)
			currentDegree = currentEducation.getDegreeType().getName();

		else
			currentDegree = "";

		if (employee != null && employee.getGender() == null)
			employee.setGender(Gender.MALE);
	}

	@CommitAfter
	void onSelectedFromSave() {
		if (memberType.equals("search")) {
			dao.saveOrUpdateObject(employee);
		}

		if (memberType == "emp") {
			employee.setEmployeeStatus(EmployeeStatus.WORKING);
			employee.setOrganization(organization);
			dao.saveOrUpdateObject(employee);
		}

		if (employee.getSystemUser() != null)
			if (employee.getSystemUser()) {
				User user = dao.getUser(employee);

				if (user == null) {
					user = new User();
					user.setEmployee(employee);
					user.setUsername(UserUtil.getUser(dao, employee));
					user.setAccountLocked(false);
				}
				user.setRoles(empRole);

				dao.saveOrUpdateObject(user);
			}

	}

	/*
	 * Object onActionFromBack() { if (memberType == "emp") { item = ; } return
	 * pageEmployee; }
	 */

	/* getter, setter */
	public Boolean getIsView() {
		return isView;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public void setIsView(Boolean isView) {
		this.isView = isView;
	}

	// public CCCMember getCccMember() {
	// return cccMember;
	// }
	//
	// public void setCccMember(CCCMember cccMember) {
	// this.cccMember = cccMember;
	// }
	//
	// public DCCMember getDccMember() {
	// return dccMember;
	// }
	//
	// public void setDccMember(DCCMember dccMember) {
	// this.dccMember = dccMember;
	// }

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public void setRelativeType(RelativeType relativeType) {
		this.relativeType = relativeType;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public void setCurrentOccupation(Displacement currentOccupation) {
		this.currentOccupation = currentOccupation;
	}

	public Displacement getCurrentOccupation() {
		return currentOccupation;
	}

	public Educations getCurrentEducation() {
		return currentEducation;
	}

	public void setCurrentEducation(Educations currentEducation) {
		this.currentEducation = currentEducation;
	}

	public String getCurrentDegree() {
		return currentDegree;
	}

	public void setCurrentDegree(String currentDegree) {
		this.currentDegree = currentDegree;
	}

	public Gender getMaleGender() {
		return Gender.MALE;
	}

	public Gender getFemaleGender() {
		return Gender.FEMALE;
	}

	public LayoutEmployee getLayoutEmployee() {
		return layoutEmployee;
	}

	public void setLayoutEmployee(LayoutEmployee layoutEmployee) {
		this.layoutEmployee = layoutEmployee;
	}

	// public PageEmployeeDetail getPageEmployee() {
	// return pageEmployee;
	// }
	//
	// public void setPageEmployee(PageEmployeeDetail pageEmployee) {
	// this.pageEmployee = pageEmployee;
	// }

	/* Ajax zone */

	Object onValueChangedFromAimagClick() {
		return request.isXHR() ? aimagZone.getBody() : null;
	}

	Object onValueChangedFromAimagHotClick() {
		return request.isXHR() ? aimagHotZone.getBody() : null;
	}

	/* selection model */
	public SelectModel getOccupationSelectionModel() {
		OccupationSelectionModel sm = new OccupationSelectionModel(dao, null);
		return sm;
	}

	public SelectModel getCityProvinceSelectionModel() {
		CityProvinceSelectionModel sm = new CityProvinceSelectionModel(dao);
		return sm;
	}

	public SelectModel getDistrictSumSelectionModel() {
		if (employee.getBirthCityProvince() == null) {
			List<City> lst = dao.getListCity();
			employee.setBirthCityProvince(lst.get(0));
		}
		DistrictSumSelectionModel sm = new DistrictSumSelectionModel(dao,
				employee.getBirthCityProvince());
		return sm;
	}

	public SelectModel getDistrictSumSelectionModel2() {
		DistrictSumSelectionModel sm = new DistrictSumSelectionModel(dao,
				employee.getAddCity());
		return sm;
	}

	public SelectModel getCountrySelectionModel() {
		CountrySelectionModel sm = new CountrySelectionModel(dao);
		return sm;
	}

	public SelectModel getRelativeTypeSelectionModel() {
		RelativeTypeSelectionModel sm = new RelativeTypeSelectionModel(dao);
		return sm;
	}

	public SelectModel getOriginSelectionModel() {
		OriginSelectionModel sm = new OriginSelectionModel(dao);
		return sm;
	}

	public SelectModel getAscriptionSelectionModel() {
		AscriptionSelectionModel sm = new AscriptionSelectionModel(dao);
		return sm;
	}

	public SelectModel getDegreeTypeSelectionModel() {
		DegreeTypeSelectionModel sm = new DegreeTypeSelectionModel(dao);
		return sm;
	}

	public SelectModel getRoleSelectionModel() {
		RoleSelectionModel sm = new RoleSelectionModel(dao);
		return sm;
	}

	public boolean isShow() {
		if (viewEmployee == true)
			return true;
		return false;
	}

	public boolean isViewEmployee() {
		return viewEmployee;
	}

	public void setViewEmployee(boolean viewEmployee) {
		this.viewEmployee = viewEmployee;
	}

}
