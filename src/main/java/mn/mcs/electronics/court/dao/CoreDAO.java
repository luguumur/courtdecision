/* 
 * File Name: CommonDAO.java
 * 
 * Created By: T.Maralerdene
 * Created Date: 2011/03/21
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * 2011/03/23 1.0.0 			T.Maralerdene				Шинээр үүсгэв.
 * 
 * -----------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2011 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */
package mn.mcs.electronics.court.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import mn.mcs.electronics.court.entities.Permission;
import mn.mcs.electronics.court.entities.Role;
import mn.mcs.electronics.court.entities.User;
import mn.mcs.electronics.court.entities.configuration.AcademicRank;
import mn.mcs.electronics.court.entities.configuration.Appointment;
import mn.mcs.electronics.court.entities.configuration.AppointmentSort;
import mn.mcs.electronics.court.entities.configuration.ApprovedPositions;
import mn.mcs.electronics.court.entities.configuration.Ascription;
import mn.mcs.electronics.court.entities.configuration.Award;
import mn.mcs.electronics.court.entities.configuration.City;
import mn.mcs.electronics.court.entities.configuration.CityProvince;
import mn.mcs.electronics.court.entities.configuration.CommandSubject;
import mn.mcs.electronics.court.entities.configuration.ComputerProgram;
import mn.mcs.electronics.court.entities.configuration.Country;
import mn.mcs.electronics.court.entities.configuration.DegreeType;
import mn.mcs.electronics.court.entities.configuration.DegreeTypeOfEducationDoctor;
import mn.mcs.electronics.court.entities.configuration.DegreeTypeOfScienceDoctor;
import mn.mcs.electronics.court.entities.configuration.DisplacementCause;
import mn.mcs.electronics.court.entities.configuration.DistrictSum;
import mn.mcs.electronics.court.entities.configuration.EducationDegree;
import mn.mcs.electronics.court.entities.configuration.Facility;
import mn.mcs.electronics.court.entities.configuration.FamilyAppointmentType;
import mn.mcs.electronics.court.entities.configuration.ForeignLanguage;
import mn.mcs.electronics.court.entities.configuration.LandIntention;
import mn.mcs.electronics.court.entities.configuration.MilitaryRank;
import mn.mcs.electronics.court.entities.configuration.Occupation;
import mn.mcs.electronics.court.entities.configuration.OccupationRank;
import mn.mcs.electronics.court.entities.configuration.OccupationRole;
import mn.mcs.electronics.court.entities.configuration.OccupationType;
import mn.mcs.electronics.court.entities.configuration.Origin;
import mn.mcs.electronics.court.entities.configuration.ProjectConfig;
import mn.mcs.electronics.court.entities.configuration.ProjectMenuConfig;
import mn.mcs.electronics.court.entities.configuration.QuitJobCause;
import mn.mcs.electronics.court.entities.configuration.RelativeType;
import mn.mcs.electronics.court.entities.configuration.RetireAge;
import mn.mcs.electronics.court.entities.configuration.SalaryNetwork;
import mn.mcs.electronics.court.entities.configuration.SalaryScale;
import mn.mcs.electronics.court.entities.configuration.School;
import mn.mcs.electronics.court.entities.configuration.SkillGroup;
import mn.mcs.electronics.court.entities.configuration.SkillType;
import mn.mcs.electronics.court.entities.configuration.TravelType;
import mn.mcs.electronics.court.entities.configuration.TuAaLevel;
import mn.mcs.electronics.court.entities.configuration.TuAaSort;
import mn.mcs.electronics.court.entities.configuration.UserIP;
import mn.mcs.electronics.court.entities.configuration.UserLoginHistory;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuLevel;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuSort;
import mn.mcs.electronics.court.entities.configuration.WorkedYear;
import mn.mcs.electronics.court.entities.configuration.salary.AdditionalSalaryType;
import mn.mcs.electronics.court.entities.configuration.salary.TuriinZahirgaaSalaryNetwork;
import mn.mcs.electronics.court.entities.employee.Addition;
import mn.mcs.electronics.court.entities.employee.AdditionalSalary;
import mn.mcs.electronics.court.entities.employee.Allowance;
import mn.mcs.electronics.court.entities.employee.AllowanceType;
import mn.mcs.electronics.court.entities.employee.Computer;
import mn.mcs.electronics.court.entities.employee.ComputerOther;
import mn.mcs.electronics.court.entities.employee.DegreeGrade;
import mn.mcs.electronics.court.entities.employee.DegreeGradeRank;
import mn.mcs.electronics.court.entities.employee.Degrees;
import mn.mcs.electronics.court.entities.employee.Demerit;
import mn.mcs.electronics.court.entities.employee.Displacement;
import mn.mcs.electronics.court.entities.employee.Educations;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.EmployeeMilitary;
import mn.mcs.electronics.court.entities.employee.EmployeeSalaryHistory;
import mn.mcs.electronics.court.entities.employee.Establishment;
import mn.mcs.electronics.court.entities.employee.Experience;
import mn.mcs.electronics.court.entities.employee.Honour;
import mn.mcs.electronics.court.entities.employee.Language;
import mn.mcs.electronics.court.entities.employee.OfficeEquipment;
import mn.mcs.electronics.court.entities.employee.Passport;
import mn.mcs.electronics.court.entities.employee.QuitJob;
import mn.mcs.electronics.court.entities.employee.Relatives;
import mn.mcs.electronics.court.entities.employee.ResultContract;
import mn.mcs.electronics.court.entities.employee.Sahilga;
import mn.mcs.electronics.court.entities.employee.SahilgaShiitgel;
import mn.mcs.electronics.court.entities.employee.SahilgaTurul;
import mn.mcs.electronics.court.entities.employee.SalaryHistory;
import mn.mcs.electronics.court.entities.employee.Skills;
import mn.mcs.electronics.court.entities.employee.Training;
import mn.mcs.electronics.court.entities.employee.TravelAbroad;
import mn.mcs.electronics.court.entities.employee.Vacation;
import mn.mcs.electronics.court.entities.employee.VacationType;
import mn.mcs.electronics.court.entities.map.MapRolePermission;
import mn.mcs.electronics.court.entities.organization.AppurtenanceLead;
import mn.mcs.electronics.court.entities.organization.AppurtenanceLocation;
import mn.mcs.electronics.court.entities.organization.Department;
import mn.mcs.electronics.court.entities.organization.FinancingType;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.entities.organization.OrganizationType;
import mn.mcs.electronics.court.entities.organization.Saving;
import mn.mcs.electronics.court.entities.organization.SubDepartment;
import mn.mcs.electronics.court.entities.other.LoginHistory;
import mn.mcs.electronics.court.entities.other.Notice;
import mn.mcs.electronics.court.entities.other.OpenPosition;
import mn.mcs.electronics.court.entities.other.OpenPositionList;
import mn.mcs.electronics.court.enums.AdditionalSalaryCategory;
import mn.mcs.electronics.court.enums.AppointmentLevel;
import mn.mcs.electronics.court.enums.AppointmentSortEmployee;
import mn.mcs.electronics.court.enums.AppointmentType;
import mn.mcs.electronics.court.enums.AwardType;
import mn.mcs.electronics.court.enums.EmployeeCurrentStatus;
import mn.mcs.electronics.court.enums.Gender;
import mn.mcs.electronics.court.enums.MilitaryOrSimple;
import mn.mcs.electronics.court.enums.MilitaryRankType;
import mn.mcs.electronics.court.enums.OrganizationGroup;
import mn.mcs.electronics.court.enums.OrganizationTypeExperience;
import mn.mcs.electronics.court.enums.QuitJobCauseName;
import mn.mcs.electronics.court.enums.RelativeFamily;
import mn.mcs.electronics.court.enums.SahilgaSubject;
import mn.mcs.electronics.court.enums.SchoolType;
import mn.mcs.electronics.court.enums.UniversityType;
import mn.mcs.electronics.court.util.beans.AlertEmployeeSalaryBean;
import mn.mcs.electronics.court.util.beans.AlertMilitaryRankEmployeeBean;
import mn.mcs.electronics.court.util.beans.AlertUdaanJilEmployeeBean;
import mn.mcs.electronics.court.util.beans.DisciplinedEmployeeBean;
import mn.mcs.electronics.court.util.beans.EmployeeCardDegreeBean;
import mn.mcs.electronics.court.util.beans.EmployeeCardDemeritBean;
import mn.mcs.electronics.court.util.beans.EmployeeCardEducationBean;
import mn.mcs.electronics.court.util.beans.EmployeeCardExperienceBean;
import mn.mcs.electronics.court.util.beans.EmployeeCardOtherAwardBean;
import mn.mcs.electronics.court.util.beans.EmployeeCardSalaryBean;
import mn.mcs.electronics.court.util.beans.EmployeeCardStateAwardBean;
import mn.mcs.electronics.court.util.beans.EmployeeLastDemeritBean;
import mn.mcs.electronics.court.util.beans.EmployeeSearchBean;
import mn.mcs.electronics.court.util.beans.EstReportBean;
import mn.mcs.electronics.court.util.beans.EstablishmentSearchBean;
import mn.mcs.electronics.court.util.beans.GeneralReportBean;
import mn.mcs.electronics.court.util.beans.GridPager;
import mn.mcs.electronics.court.util.beans.OpenPositionListSearchBean;
import mn.mcs.electronics.court.util.beans.OpenPositionSearchBean;
import mn.mcs.electronics.court.util.beans.OrganizationSearchBean;
import mn.mcs.electronics.court.util.beans.ShagnuulahEmployeeBean;
import mn.mcs.electronics.court.util.beans.TetgevriinEmployeeBean;
import mn.mcs.electronics.court.util.beans.UserLoginHistorySearchBean;

public interface CoreDAO {

	public void saveObject(Object obj);

	public void updateObject(Object obj);

	public void saveOrUpdateObject(Object obj);

	public void deleteObject(Object obj);

	public Object mergeObject(Object obj);

	public Object getObject(Class clazz, Long id);

	public Employee getEmployee(String username);

	public List<Role> getRoleList();

	public List<Permission> getPermissionList();

	public List<Permission> getPermissionList(Role role);

	public List<Employee> getEmployeeList(Employee emp);

	/*
	 * public List<OrganizationType> getListOrganizationType( OrganizationGroup
	 * group);
	 */

	public List<OrganizationType> getListOrganizationType();

	public List<Organization> getListOrganization();

	public List<Country> getListCountry();

	public List<Employee> getListEmployee(Organization org);

	public List<Employee> getListEmployee(Organization org, Appointment appointment);

	public List<Employee> getListEmployeeJudje(Organization org, AppointmentLevel appointmentLevel);

	public List<Employee> getListEmployee();

	public List<RelativeType> getRelativeType();

	public List<Relatives> getRelatives(Long empID, RelativeFamily type);

	public List<Training> getTraining(Employee emp);

	public List<Language> getLanguage(Employee emp);

	public List<Computer> getComputer(Employee emp);

	public List<CityProvince> getListCityProvince(Country country);

	public List<School> getListSchool(UniversityType type);

	public List<School> getListSchool();

	public List<DegreeType> getListDegreeType();

	public List<DegreeTypeOfScienceDoctor> getListScienceDoctor();

	public List<DegreeTypeOfEducationDoctor> getListEducationDoctor();

	public List<Occupation> getListOccupation(SchoolType type);

	public List<Educations> getListEducation(Employee emp);

	public List<Degrees> getListDegrees(Employee emp);

	public List<Honour> getListHonour(Employee emp);

	public List<Honour> getListStateHonourEmpCard(Employee emp);

	public List<Honour> getListOtherHonourEmpCard(Employee emp);

	public List<Employee> getListEmployeeDecrypt(Employee emp);

	public List<SkillGroup> getListSkillGroup();

	public List<Skills> getListSkills(Employee emp);

	public List<SkillType> getListSkillType(SkillGroup skill);

	public List<Experience> getExperience(Employee emp);

	public List<Addition> getAddition(Employee emp);

	public List<ResultContract> getResultContract(Employee emp);

	public List<Employee> getListEmployeeSearch(EmployeeSearchBean bean, RetireAge ra, HashSet<Organization> orgs,
			GridPager pager);

	public List<OccupationRole> getListOccupationRole();

	public List<OccupationType> getListOccupationType();

	public List<UtTzTtTuSort> getListUtTzTtTuSort();

	public List<TuAaSort> getListTuAaSort();

	public List<SalaryNetwork> getListSalaryNetwork();

	public List<SalaryScale> getListSalaryScale();

	public List<AcademicRank> getListMilitaryRank();

	public List<LandIntention> getListLandIntention();

	public List<AppurtenanceLead> getListAppurtenanceLead();

	public List<AppurtenanceLocation> getListAppurtenanceLocation();

	public List<DistrictSum> getListDistrictSum(City city);

	public List<Department> getListDepartmentSearch(Organization org);

	public List<DisplacementCause> getListDisplacementCause();

	public List<Displacement> getListCurrentOccupation(Employee emp);
	
	public Displacement getCurrentOccupationId(Experience emp);

	public MapRolePermission getMapRolePermission(Permission perm, Role role);

	public List<FinancingType> getListFinancingType();

	public List<Employee> getListUser();

	public User getUser(Employee emp);

	public List<OccupationRank> getListOccupationRank();

	public List<UtTzTtTuLevel> getListUtTzTtTuLevel(UtTzTtTuSort cat);

	public List<TuAaLevel> getListTuAaLevel(TuAaSort cat);

	public List<TuriinZahirgaaSalaryNetwork> getListTuriinZahirgaaSalaryNetwork();

	public List<TuriinZahirgaaSalaryNetwork> getListTuriinZahirgaaSalaryDate(UtTzTtTuLevel level, SalaryScale scale);

	public Displacement getCurrentPosition(Employee emp);

	public Organization getOrganization(OrganizationGroup org);

	public TuriinZahirgaaSalaryNetwork getCurrentSalaryAmount(UtTzTtTuLevel level, SalaryScale phase, Date date);

	public List<AdditionalSalaryType> getListAdditionalSalaryTypes(AdditionalSalaryCategory category);

	public List<Department> getListDepartment(Organization org);

	public List<Appointment> getListAppointment(AppointmentType type, AppointmentSortEmployee sort);

	public List<Origin> getListOrigin();

	public List<Ascription> getListAscription();

	public List<AdditionalSalary> getListAdditionalSalary(SalaryHistory salary);

	public List<SalaryHistory> getListSalary(Employee emp);

	public List<QuitJob> getQuitJob(Employee emp);

	public Notice getContent();

	public Displacement getCurrentOccupation(Employee emp);

	public List<City> getListCity();

	public List<Object[]> getListEmployeeTuSearch(Organization organization);

	public List<Object[]> getListEmployeeGenderReport(Organization organization, AppointmentType appointmentType);

	public List<Object[]> getListEmployeeLanguageSearch(Organization organization);

	public List<Employee> getListEmployeeCeoSearch(Organization organization);

	public List<Employee> getListEmployeeAgeReport(Organization org, AppointmentType appointmentType);

	public List<Organization> getListOrganizationSearch(OrganizationSearchBean bean);

	public List<Object[]> getListSalaryWage(Organization org);

	public List<Object[]> getListSalaryReport(Organization org, Integer year);

	public List<Object[]> getListAdditionalSalaryReport(Organization org, Integer integer);

	public List<Object[]> getCompetenceCommitteeCandidateList(Organization org, Date fromDate, Date toDate,
			OrganizationType orgType);

	public List<Object[]> getDisciplineCommitteeMembersList(Organization org, Date fromDate, Date toDate);

	public User getMaxUserCode();

	public List<DegreeGrade> getListDegreeGrade(Employee emp);

	public List<DegreeGradeRank> getListDegreeGradeRank(Employee emp);

	public List<Object[]> getListEmployeeTrainingReport(Organization org, Date fromDate, Date toDate,
			AppointmentType appointment);

	/*
	 * public List<Object[]> getListEmployeeDegreeGradeReport( Organization
	 * organization, AppointmentType appointmentType);
	 */

	public List<Object[]> getListEmployeeDegreeGradeReport(Organization organization);

	public List<Experience> getListEmployeeWorkedYearReport(Organization org, AppointmentType type);

	public List<Object[]> getListEmployeeQuitTypeReport(Organization organization, AppointmentType appointmentType);

	public List<QuitJobCause> getListQuitJobCause(EmployeeCurrentStatus cat);

	public List<Object[]> getListRetiredEmployeeSearch(Organization organization, QuitJobCauseName cause);

	public List<Object[]> getListRetireSoonReport(Organization organization);

	public List<Object[]> getListHonourReport(Organization org, Date fromDate, Date toDate,
			AppointmentType appointmentType);

	public List<Object[]> getListGeneralJudgeReport(Organization org);

	public List<Object[]> getListPromotionReport(Organization org, Integer year);

	public List<Object[]> getListMovementReport(Organization org, Integer year);

	public List<TuAaLevel> getListTuAaLevel();

	public List<UtTzTtTuLevel> getListUtTzTtTuLevel();

	public Long getPromoteReport(Organization org, Date fromDate, Date toDate, AppointmentType appointmentType);

	public Integer getFirstYearSalaryHistory();

	public List<QuitJobCause> getListQuitJobCause();

	public Integer getFirstYearDisplacementHistory();

	public Long getTotalWorkedYear(Long empID);

	public Long getStateOrganizationWorkedYear(Employee emp, OrganizationTypeExperience orgType);

	public Long getCurrentOrganizationWorkedYear(Employee emp, OrganizationTypeExperience orgType);

	public Educations getCurrentEducation(Long empID);

	public List<Notice> getListNotice();

	public List<Object[]> getListDisplacementTypeSearch(Organization organization, String type, Gender gender);

	public List<LoginHistory> getListLoginHistory(Employee emp);

	public List<Appointment> getJudgeAppointment();

	public List<OpenPosition> getListOpenPositionSearch(OpenPositionSearchBean bean);

	public List<Object[]> getListPhoneReport(Organization org);

	public List<EducationDegree> getListEducationDegree();

	public List<ForeignLanguage> getListForeignLanguage();

	public List<ComputerProgram> getListComputerProgram();

	public List<ComputerOther> getListComputerOther(Employee emp);

	public List<Facility> getListFacility();

	public List<OfficeEquipment> getListOfficeEquipment(Employee emp);

	public Long getEmployeeAge(Employee emp);

	public List<Saving> getListSavingType();

	public List<OpenPosition> getListOpenPosition();

	public Long getJudgeWorkedYear(Employee emp, Boolean judge);

	public List<TravelAbroad> getListTravelAbroad(Employee emp);

	public List<Passport> getListPassport(Employee emp);

	public Long getMajorWorkedYear(Employee emp, Boolean major);

	public List<AppointmentSort> getAppointmentSort();

	public List<AppointmentSort> getAppointmentSort(AppointmentType type);

	public List<mn.mcs.electronics.court.entities.configuration.AppointmentLevel> getListAppointmentLevel(
			AppointmentSort cat, AppointmentType type);

	public List<mn.mcs.electronics.court.entities.configuration.AppointmentLevel> getListAppointmentLevel();

	public List<RetireAge> getListRetireAge();

	public List<Demerit> getListDemerit(Employee emp);

	public List<Employee> getEmployeeByRegister(String register);

	public List<TravelType> getListTravelType();

	public List<FamilyAppointmentType> getListFamilyAppointmentType();

	public List<OpenPositionList> getListOpenPositionListSearch(Integer opNumber, OpenPositionListSearchBean bean);

	public List<ApprovedPositions> getListApprovedPosition(Organization org, Integer belenOronToo);

	public List<WorkedYear> getListWorkedYear();

	public List<ApprovedPositions> getListApprovedPositions(Organization org);

	public List<Object[]> getListAppointmentQuantity(Organization organization);

	public List<Employee> getMajorWorkedYearEmployee(Long year);

	public List<Employee> getStateWorkedYearEmployee(Long year);

	public List<User> getListUserName();

	public List<UserIP> getListUserIP();

	public List<Appointment> getListAppointmentByName(String app);

	public List<Appointment> getListAppointmentOrg();

	public List<Role> getRoleListByEmployee(String user);

	public List<ProjectConfig> getProjectHeader();

	public List<ProjectConfig> getProjectConfig();

	public List<Permission> getAdminPermissionList();

	public List<ProjectMenuConfig> getProjectMenuConfig();

	public List<ProjectMenuConfig> getProjectMenu(Integer location);

	public Language getLanguage(Employee emp, String langName);

	/**
	 * @param obj
	 * @return Регистрийн дугаараар ажилтанг буцна
	 */
	public Employee getEmpByRegId(String obj);

	public List<Organization> getListOrganizationByName(String orgName);

	public List<SubDepartment> getListSubDepartment(Organization org, Department dep);

	public List<MilitaryRank> getListMilitary();

	public List<EmployeeMilitary> getListEmployeeMilitary(Employee emp);

	public List<MilitaryRank> getListMilitaryByType(MilitaryRankType type);

	public List<Organization> getListEntityFields(Class obj);

	public List<EmployeeMilitary> getListMilitaryByMilitaryRank(MilitaryRank militaryRank);

	public List<CommandSubject> getListCommandSubject();

	public Long getMilitaryOrSimpleWorkedYear(Long empID, OrganizationTypeExperience orgType,
			MilitaryOrSimple isMilitary);

	public List<AllowanceType> getListAllowanceType();

	public List<Allowance> getListAllowance(Employee emp);

	public List<VacationType> getListVacationType();

	public List<Vacation> getListVacation(Employee emp);

	/* Dec 07, 2012 Jargalsuren.S End */

	/* Dec 19, 2012 Bolorchimeg Begin */

	public List<SahilgaShiitgel> getListSahilgaShiitgelByTypeSubject(SahilgaSubject subject);

	public List<Sahilga> getListSahilga(Employee emp);

	public List<SahilgaShiitgel> getListSahilgaShiitgel();

	public List<Sahilga> getListSahilga(SahilgaShiitgel sahilgaShiitgel);

	public List<SahilgaTurul> getListSahilgaTurul();

	/* Dec 19, 2012 Bolorchimeg End */

	public List<Permission> getComponentPermissionList();

	public List<DisciplinedEmployeeBean> getListEmployeeByDiscipline(HashSet<Organization> organization);

	public List<AlertMilitaryRankEmployeeBean> getListEmployeeByMilitary(HashSet<Organization> organization);

	public List<TetgevriinEmployeeBean> getListEmployeeByTetgevriin(HashSet<Organization> organization,
			GridPager pager);

	public List<ShagnuulahEmployeeBean> getListEmployeeByShagnal(HashSet<Organization> organization, GridPager pager);

	public List<AlertEmployeeSalaryBean> getListEmployeeAlertSalary(HashSet<Organization> organization,
			GridPager pager);

	public List<AlertUdaanJilEmployeeBean> getListEmployeeAlertUdaanJil(HashSet<Organization> organization,
			GridPager pager);

	public Appointment getAppointmentByName(Long appointmentID);

	public Experience getWorkingExperience(Employee emp);

	public List<EmployeeCardExperienceBean> getEmployeeCardExperienceBean(Employee emp);

	public List<EmployeeCardStateAwardBean> getEmployeeCardStateAwardBean(Employee emp);

	public List<EmployeeCardOtherAwardBean> getEmployeeCardOtherAwardBean(Employee emp);

	public List<EmployeeCardDemeritBean> getEmployeeCardDemeritBean(Employee emp);

	public List<EmployeeCardEducationBean> getEmployeeCardEducationBean(Employee emp);

	public List<EmployeeCardSalaryBean> getEmployeeCardSalaryBean(Employee emp);

	public List<EmployeeCardDegreeBean> getEmployeeCardDegreeBean(Employee emp);

	public List<EmployeeLastDemeritBean> getEmployeeLastDemeritBean(Date startDate, Date endDate,
			HashSet<Organization> organization);

	public int getEmployeeFamilyMemberCount(Employee emp);

	public List<Relatives> getEmployeeFamilyMembers(Employee emp);

	public EmployeeMilitary getEmployeeMilitary(Long empID);

	public List<Employee> getEmployeeIsUserList();

	public List<Organization> getListOrganization(OrganizationSearchBean bean);

	public List<Employee> getListEmployeeSearch();

	/* Oct 2, 2013 Erdenetuya.B Begin */

	public City getCityByName(String cityName);

	public DistrictSum getDistrictSumByName(City city, String sumName);

	public Origin getOriginByName(String originName);

	public Ascription getAscriptionByName(String ascriptionName);

	public RelativeType getRelativeTypeByName(String relativeTypeName);

	/* Oct 2, 2013 Erdenetuya.B End */

	public List<Establishment> getListEstablishmentSearch();

	public Employee getEmployeeByExcelRowNumber(Integer rowNumber);

	public FamilyAppointmentType getFamilyAppointmentByName(String appName);

	public List<Establishment> getListEstablishment(EstablishmentSearchBean bean);

	public List<Employee> getListEmployeeSearch(Long appointmentID);

	public Employee getEmployeeByOrg(Long orgID, Integer empNumber);

	public MilitaryRank getMilitaryRankByName(String militaryRankName, MilitaryRankType type);

	/* Oct 7, 2013 Erdenetuya.B Begin */
	public CommandSubject getCommandSubjectByName(String commandSubjectName);

	public Country getCountryByName(String countryName);

	/* Oct 7, 2013 Erdenetuya.B End */

	/* Oct 7, 2013 Bileg-Ochir.G End */

	public DegreeType getDegreeTypeName(String degreeName);

	public Occupation getOccupationName(String occupationName);

	public School getSchoolByName(String schoolName);

	public List<Educations> getListEducation();

	public List<School> getListSchool(String name);

	/* Oct 7, 2013 Bileg-Ochir.G End */

	/* Oct 8, 2013 Jargalsuren.S Begin */
	public ForeignLanguage getListForeignLanguage(String languageName);

	public Facility getOfficeIt(String program);

	public AcademicRank getAcademicRank(String academicRankName);

	/* Oct 8, 2013 Jargalsuren.S Begin */

	/* Oct 10, 2013 Erdenetuya.B Begin */

	public UtTzTtTuSort getUtTzTtTuSortByName(String name);

	public OccupationRank getOccupationRankByRank(Integer rank);

	public SalaryScale getSalaryScaleByName(String name);

	public UtTzTtTuLevel getUtTzLevel(UtTzTtTuSort utTzTtTuSort, OccupationRank occupationRank);

	/* Oct 10, 2013 Erdenetuya.B End */

	public String getOccupation(Long empID);

	public List<SkillType> getEmpSkillTypes();

	public Skills getSkillsByEmpSkillType(Employee employee, SkillType skillType);

	public Employee getEmployeeByNum(Integer empNumber);

	public VacationType getVacationTypeByName(String name);

	public AllowanceType getAllowanceTypeByName(String name);

	public SahilgaTurul getSahilgaTurulByName(String name);

	public SahilgaShiitgel getSahilgaShiitgelByName(String name);

	public AdditionalSalaryType getAdditionalSalaryTypeById(Long id);

	public ComputerProgram getListComputerProgram(String program);

	public User getUserByUsername(String username);

	public List<Award> getListAward(AwardType awardType);

	public Award getAwardByName(AwardType awardType, String name);

	public Award getAwardByNameName(String name);

	public Notice getLastNotice();

	public List<GeneralReportBean> generateReport(Long orgID);

	public List<EstReportBean> generateEstablishmentReport(Long orgID);

	public List<Appointment> getListAppointmentByType(OccupationType type);

	public List<UserLoginHistory> getUserLoginHistory(UserLoginHistorySearchBean bean);

	public List<EmployeeSalaryHistory> getEmployeeSalaryHistory(Long empID);

	public List<AdditionalSalary> getListAdditionalSalary(EmployeeSalaryHistory salary);

	public Experience getExperienceShuuh(Employee emp);

	public Experience getExperienceOcc(Employee emp, Displacement displacement);

	public <T> List<T> getAnyList(Class<T> clss, String eqProperty, Long propertyID);

}