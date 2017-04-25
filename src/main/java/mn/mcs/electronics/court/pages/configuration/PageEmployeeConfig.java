package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.AcademicRank;
import mn.mcs.electronics.court.entities.configuration.Appointment;
import mn.mcs.electronics.court.entities.configuration.Ascription;
import mn.mcs.electronics.court.entities.configuration.City;
import mn.mcs.electronics.court.entities.configuration.CityProvince;
import mn.mcs.electronics.court.entities.configuration.CommandSubject;
import mn.mcs.electronics.court.entities.configuration.ComputerProgram;
import mn.mcs.electronics.court.entities.configuration.Country;
import mn.mcs.electronics.court.entities.configuration.DegreeType;
import mn.mcs.electronics.court.entities.configuration.DegreeTypeOfScienceDoctor;
import mn.mcs.electronics.court.entities.configuration.DisplacementCause;
import mn.mcs.electronics.court.entities.configuration.DistrictSum;
import mn.mcs.electronics.court.entities.configuration.EducationDegree;
import mn.mcs.electronics.court.entities.configuration.Facility;
import mn.mcs.electronics.court.entities.configuration.FamilyAppointmentType;
import mn.mcs.electronics.court.entities.configuration.ForeignLanguage;
import mn.mcs.electronics.court.entities.configuration.MilitaryRank;
import mn.mcs.electronics.court.entities.configuration.Occupation;
import mn.mcs.electronics.court.entities.configuration.OccupationRank;
import mn.mcs.electronics.court.entities.configuration.OccupationRole;
import mn.mcs.electronics.court.entities.configuration.OccupationType;
import mn.mcs.electronics.court.entities.configuration.Origin;
import mn.mcs.electronics.court.entities.configuration.QuitJobCause;
import mn.mcs.electronics.court.entities.configuration.RelativeType;
import mn.mcs.electronics.court.entities.configuration.RetireAge;
import mn.mcs.electronics.court.entities.configuration.SalaryNetwork;
import mn.mcs.electronics.court.entities.configuration.SalaryScale;
import mn.mcs.electronics.court.entities.configuration.School;
import mn.mcs.electronics.court.entities.configuration.SkillGroup;
import mn.mcs.electronics.court.entities.configuration.SkillType;
import mn.mcs.electronics.court.entities.configuration.TravelType;
import mn.mcs.electronics.court.entities.configuration.salary.AdditionalSalaryType;
import mn.mcs.electronics.court.entities.configuration.salary.TuriinZahirgaaSalaryNetwork;
import mn.mcs.electronics.court.entities.employee.AllowanceType;
import mn.mcs.electronics.court.entities.employee.VacationType;
import mn.mcs.electronics.court.enums.AdditionalSalaryCategory;
import mn.mcs.electronics.court.enums.AppointmentSortEmployee;
import mn.mcs.electronics.court.enums.AppointmentType;
import mn.mcs.electronics.court.enums.EmployeeCurrentStatus;
import mn.mcs.electronics.court.enums.GetWage;
import mn.mcs.electronics.court.enums.MilitaryRankType;
import mn.mcs.electronics.court.enums.SchoolType;
import mn.mcs.electronics.court.enums.UniversityType;
import mn.mcs.electronics.court.model.CityProvinceSelectionModel;
import mn.mcs.electronics.court.model.CountrySelectionModel;
import mn.mcs.electronics.court.model.OccupationLevelSelectionModel;
import mn.mcs.electronics.court.model.OccupationTypeSelectionModel;
import mn.mcs.electronics.court.model.SalaryScaleSelectionModel;
import mn.mcs.electronics.court.model.SkillGroupSelectionModel;

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
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.util.EnumSelectModel;

public class PageEmployeeConfig {

	/*
	 * INJECTS
	 */

	@Inject
	private CoreDAO dao;

	@Inject
	private Messages messages;

	@SessionState
	private LoginState loginState;

	@Inject
	private Request request;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@Inject
	private AlertManager manager;

	@InjectComponent
	private Zone cityProvinceListZone, cityProvinceFormZone, cityListZone,
			cityFormZone, sumFormZone, sumListZone, schoolListZone,
			schoolFormZone, occupationFormZone, occupationListZone,
			occupationRoleFormZone, occupationRoleListZone,
			appointmentListZone, appointmentFormZone, occupationTypeFormZone,
			salaryNetworkListZone, salaryNetworkFormZone,
			occupationTypeListZone, salaryNetworkConfigFormZone,
			salaryNetworkConfigListZone, salaryScaleFormZone,
			salaryScaleListZone, displacementCauseFormZone,
			displacementCauseListZone, quitJobCauseFormZone,
			quitJobCauseListZone, additionalSalaryFormZone,
			additionalSalaryListZone, retireAgeFormZone, retireAgeListZone,
			occupationRankListZone, occupationRankFormZone, countryFormZone,
			countryListZone, degreeTypeListZone, degreeTypeFormZone,
			educationDegreeFormZone, educationDegreeListZone,
			skillTypeFormZone, skillTypeListZone, skillFormZone, skillListZone,
			originFormZone, originListZone, ascriptionFormZone,
			ascriptionListZone, languageFormZone, languageListZone,
			computerFormZone, computerListZone, facilityListZone,
			facilityFormZone, academicRankListZone, academicRankFormZone,
			relativeTypeListZone, relativeTypeFormZone, travelTypeListZone,
			travelTypeFormZone, familyAppointmentListZone,
			familyAppointmentFormZone, militaryRankFormZone,
			militaryRankListZone, commandSubjectFormZone,
			commandSubjectListZone, allowanceTypeFormZone,
			allowanceTypeListZone, vacationTypeFormZone, vacationTypeListZone,
			ScienceDoctorFormZone, ScienceDoctorListZone;

	/*
	 * PERSISTS
	 */

	@Property
	@Persist
	private CityProvince cityProvince;

	@Property
	@Persist
	private City city;

	@Property
	@Persist
	private DistrictSum districtSum;

	@Property
	@Persist
	private School school;

	@Property
	@Persist
	private Occupation occupation;

	@Property
	@Persist
	private OccupationRole occupationRole;

	@Property
	@Persist
	private Appointment appointment;

	@Property
	@Persist
	private AppointmentType appointmentType;

	@Persist
	@Property
	private List<Appointment> listAppointment;

	@Property
	@Persist
	private OccupationType occupationType;

	@Property
	@Persist
	private SalaryNetwork salaryNetwork;

	@Property
	@Persist
	private TuriinZahirgaaSalaryNetwork salary;

	@Property
	@Persist
	private SalaryScale salaryScale;

	@Property
	@Persist
	private DisplacementCause displacementCause;

	@Property
	@Persist
	private QuitJobCause quitJobCause;

	@Property
	@Persist
	private AdditionalSalaryType additionalSalary;

	@Property
	@Persist
	private RetireAge retireAge;

	@Property
	@Persist
	private OccupationRank occupationRank;

	@Property
	@Persist
	private Country country;

	@Property
	@Persist
	private DegreeType degreeType;

	@Property
	@Persist
	private EducationDegree educationDegree;

	@Property
	@Persist
	private SkillGroup skillType;

	@Property
	@Persist
	private SkillType skill;

	@Property
	@Persist
	private Origin origin;

	@Property
	@Persist
	private Ascription ascription;

	@Property
	@Persist
	private ForeignLanguage foreignLanguage;

	@Property
	@Persist
	private ComputerProgram computerProgram;

	@Property
	@Persist
	private Facility facility;

	@Property
	@Persist
	private AcademicRank academicRank;

	@Property
	@Persist
	private RelativeType relativeType;

	@Property
	@Persist
	private TravelType travelType;

	@Property
	@Persist
	private FamilyAppointmentType familyAppointmentType;

	@Property
	@Persist
	private MilitaryRank militaryRank;

	@Property
	@Persist
	private CommandSubject commandSubject;

	@Property
	@Persist
	private DegreeTypeOfScienceDoctor scienceDoctor;

	/*
	 * PROPERTIES
	 */

	@Property
	private Appointment valueAppointment;

	@Property
	@Persist
	private List<CityProvince> listCityProvince;

	@Property
	private CityProvince valueCityProvince;

	@Property
	@Persist
	private List<City> listCity;

	@Property
	private City valueCity;

	@Property
	@Persist
	private List<DistrictSum> listDistrictSum;

	@Property
	private DistrictSum valueDistrictSum;

	@Property
	@Persist
	private List<School> listSchool;

	@Property
	private School valueSchool;

	@Property
	@Persist
	private List<Occupation> listOccupation;

	@Property
	private List<OccupationRole> listOccupationRole;

	@Property
	private OccupationRole valueOccupationRole;

	@Property
	private Occupation valueOccupation;

	@Property
	@Persist
	private List<OccupationType> listOccupationType;

	@Property
	private OccupationType valueOccupationType;

	@Property
	@Persist
	private List<SalaryNetwork> listSalaryNetwork;

	@Property
	private SalaryNetwork valueSalaryNetwork;

	@Property
	@Persist
	private List<TuriinZahirgaaSalaryNetwork> listTuriinZahirgaaSalaryNetwork;

	@Property
	private TuriinZahirgaaSalaryNetwork valueTuriinZahirgaaSalaryNetwork;

	@Property
	@Persist
	private List<SalaryScale> listSalaryScale;

	@Property
	private SalaryScale valueSalaryScale;

	@Property
	@Persist
	private List<DisplacementCause> listDisplacementCause;

	@Property
	private DisplacementCause valueDisplacementCause;

	@Property
	@Persist
	private List<QuitJobCause> listQuitJobCause;

	@Property
	private QuitJobCause valueQuitJobCause;

	@Property
	@Persist
	private List<AdditionalSalaryType> listAdditionalSalaryType;

	@Property
	private AdditionalSalaryType valueAdditionalSalaryType;

	@Property
	@Persist
	private List<RetireAge> listRetireAge;

	@Property
	@Persist
	private List<OccupationRank> listOccupationRank;

	@Property
	private OccupationRank valueOccupationRank;

	@Property
	private RetireAge valueRetireAge;

	@Property
	@Persist
	private List<Country> listCountry;

	@Property
	private Country valueCountry;

	@Property
	private List<DegreeType> listDegreeType;

	@Property
	private DegreeType valueDegreeType;

	@Property
	@Persist
	private List<EducationDegree> listEducationDegree;

	@Property
	private EducationDegree valueEducationDegree;

	@Property
	@Persist
	private List<SkillGroup> listSkillType;

	@Property
	private SkillGroup valueSkillType;

	@Property
	@Persist
	private List<SkillType> listSkill;

	@Property
	private SkillType valueSkill;

	@Property
	@Persist
	private List<Origin> listOrigin;

	@Property
	private Origin valueOrigin;

	@Property
	@Persist
	private List<Ascription> listAscription;

	@Property
	private Ascription valueAscription;

	@Property
	@Persist
	private List<ForeignLanguage> listForeignLanguage;

	@Property
	private ForeignLanguage valueForeignLanguage;

	@Property
	@Persist
	private List<ComputerProgram> listComputerProgram;

	@Property
	private ComputerProgram valueComputerProgram;

	@Property
	@Persist
	private List<Facility> listFacility;

	@Property
	private Facility valueFacility;

	@Property
	private AcademicRank valueAcademicRank;

	@Property
	@Persist
	private List<AcademicRank> listAcademicRank;

	@Property
	private List<RelativeType> listRelativeType;

	@Property
	private RelativeType valueRelativeType;

	@Property
	@Persist
	private List<TravelType> listTravelType;

	@Property
	private TravelType valueTravelType;

	@Property
	@Persist
	private List<FamilyAppointmentType> listFamilyAppointmentType;

	@Property
	private FamilyAppointmentType valueFamilyAppointmentType;

	@Property
	@Persist
	private List<MilitaryRank> listMilitaryRank;

	@Property
	private MilitaryRank valueMilitaryRank;

	@Property
	@Persist
	private List<CommandSubject> listCommandSubject;

	@Property
	private CommandSubject valueCommandSubject;

	@Property
	@Persist
	private AllowanceType allowanceType;

	@Property
	@Persist
	private List<AllowanceType> listAllowanceType;

	@Property
	private AllowanceType valueAllowanceType;

	@Property
	@Persist
	private VacationType vacationType;

	@Property
	@Persist
	private List<VacationType> listVacationType;

	@Property
	private VacationType valueVacationType;

	@Property
	@Persist
	private List<DegreeTypeOfScienceDoctor> listScienceDoctorType;

	@Property
	private DegreeTypeOfScienceDoctor valueScienceDoctor;

	void beginRender() {

		if (cityProvince == null)
			cityProvince = new CityProvince();

		if (city == null)
			city = new City();

		if (districtSum == null)
			districtSum = new DistrictSum();

		if (school == null)
			school = new School();

		if (occupation == null)
			occupation = new Occupation();

		if (occupationRole == null)
			occupationRole = new OccupationRole();

		if (appointment == null)
			appointment = new Appointment();

		if (occupationType == null)
			occupationType = new OccupationType();

		if (salaryNetwork == null)
			salaryNetwork = new SalaryNetwork();

		if (salary == null)
			salary = new TuriinZahirgaaSalaryNetwork();

		if (salaryScale == null)
			salaryScale = new SalaryScale();

		if (displacementCause == null)
			displacementCause = new DisplacementCause();

		if (quitJobCause == null)
			quitJobCause = new QuitJobCause();

		if (additionalSalary == null)
			additionalSalary = new AdditionalSalaryType();

		if (retireAge == null)
			retireAge = new RetireAge();

		if (occupationRank == null)
			occupationRank = new OccupationRank();

		if (country == null)
			country = new Country();

		if (degreeType == null)
			degreeType = new DegreeType();

		if (educationDegree == null)
			educationDegree = new EducationDegree();

		if (skillType == null)
			skillType = new SkillGroup();

		if (skill == null)
			skill = new SkillType();

		if (origin == null)
			origin = new Origin();

		if (ascription == null)
			ascription = new Ascription();

		if (foreignLanguage == null)
			foreignLanguage = new ForeignLanguage();

		if (computerProgram == null)
			computerProgram = new ComputerProgram();

		if (facility == null)
			facility = new Facility();

		if (academicRank == null)
			academicRank = new AcademicRank();

		if (relativeType == null)
			relativeType = new RelativeType();

		if (travelType == null)
			travelType = new TravelType();

		if (familyAppointmentType == null)
			familyAppointmentType = new FamilyAppointmentType();

		if (militaryRank == null)
			militaryRank = new MilitaryRank();

		if (commandSubject == null)
			commandSubject = new CommandSubject();

		if (allowanceType == null)
			allowanceType = new AllowanceType();

		if (vacationType == null)
			vacationType = new VacationType();

		if (scienceDoctor == null)
			scienceDoctor = new DegreeTypeOfScienceDoctor();

		listVacationType = dao.getListVacationType();
		listAllowanceType = dao.getListAllowanceType();
		listCommandSubject = dao.getListCommandSubject();
		listMilitaryRank = dao.getListMilitary();
		listFamilyAppointmentType = dao.getListFamilyAppointmentType();
		listTravelType = dao.getListTravelType();
		listRelativeType = dao.getRelativeType();
		listAcademicRank = dao.getListMilitaryRank();
		listFacility = dao.getListFacility();
		listComputerProgram = dao.getListComputerProgram();
		listForeignLanguage = dao.getListForeignLanguage();
		listAscription = dao.getListAscription();
		listOrigin = dao.getListOrigin();
		listSkill = dao.getListSkillType(null);
		listSkillType = dao.getListSkillGroup();
		listEducationDegree = dao.getListEducationDegree();
		listDegreeType = dao.getListDegreeType();
		listCountry = dao.getListCountry();
		listOccupationRank = dao.getListOccupationRank();
		listRetireAge = dao.getListRetireAge();
		listAdditionalSalaryType = dao.getListAdditionalSalaryTypes(null);
		listQuitJobCause = dao.getListQuitJobCause();
		listDisplacementCause = dao.getListDisplacementCause();
		listSalaryScale = dao.getListSalaryScale();
		listAppointment = dao.getListAppointment(null, null);
		listOccupationType = dao.getListOccupationType();
		listTuriinZahirgaaSalaryNetwork = dao.getListTuriinZahirgaaSalaryDate(
				null, null);

		listScienceDoctorType = dao.getListScienceDoctor();

		try {

			initGridCity();
			initGridCityProvince();
			initGridDistrictSum();
			initGridOccupation();
			initGridOccupationRole();
			initGridSchool();
			initGridSalaryNetwork();

		} catch (Exception e) {
			System.err.println("PageEmployeeConfig.java beginRender : "
					+ e.getMessage());
		}

	}

	void initGridOccupationRole() {
		listOccupationRole = dao.getListOccupationRole();
	}

	void initGridOccupation() {
		listOccupation = dao.getListOccupation(null);
	}

	void initGridSchool() {
		listSchool = dao.getListSchool();
	}

	void initGridDistrictSum() {
		listDistrictSum = dao.getListDistrictSum(null);
	}

	void initGridCity() {
		listCity = dao.getListCity();
	}

	void initGridCityProvince() {
		listCityProvince = dao.getListCityProvince(null);
	}

	void initGridSalaryNetwork() {
		listSalaryNetwork = dao.getListSalaryNetwork();
	}

	/*
	 * EVENTS
	 */

	@CommitAfter
	void onSelectedFromSave() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(cityProvince);
			cityProvince = new CityProvince();
			initGridCityProvince();

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));

			ajaxResponseRenderer.addRender(cityProvinceFormZone).addRender(
					cityProvinceListZone);
		}
	}

	void onActionFromEdit(CityProvince city) {
		if (request.isXHR()) {
			this.cityProvince = city;
			ajaxResponseRenderer.addRender(cityProvinceFormZone);
		}
	}

	@CommitAfter
	void onActionFromDelete(CityProvince city) {
//		if (request.isXHR()) {
			dao.deleteObject(city);
			initGridCityProvince();

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successDeleteMessage"));

//			ajaxResponseRenderer.addRender(cityProvinceListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveCity() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(city);
			city = new City();
			initGridCity();

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));

			ajaxResponseRenderer.addRender(cityFormZone)
					.addRender(cityListZone);
		}
	}

	void onActionFromEditCity(City city) {
		if (request.isXHR()) {
			this.city = city;
			ajaxResponseRenderer.addRender(cityFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteCity(City city) {
//		if (request.isXHR()) {
			dao.deleteObject(city);
			initGridCity();

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successDeleteMessage"));

//			ajaxResponseRenderer.addRender(cityListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveSum() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(districtSum);
			districtSum = new DistrictSum();
			initGridDistrictSum();

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));

			ajaxResponseRenderer.addRender(sumFormZone).addRender(sumListZone);
		}
	}

	void onActionFromEditSum(DistrictSum districtSum) {
		if (request.isXHR()) {
			this.districtSum = districtSum;
			ajaxResponseRenderer.addRender(sumFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteSum(DistrictSum districtSum) {
//		if (request.isXHR()) {
			dao.deleteObject(districtSum);
			initGridDistrictSum();

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successDeleteMessage"));

//			ajaxResponseRenderer.addRender(sumListZone);
//		}
	}

	void onSelectedFromSearchSum() {
		if (request.isXHR()) {
			listDistrictSum = dao.getListDistrictSum(districtSum.getCity());
			ajaxResponseRenderer.addRender(sumListZone);
		}
	}

	@CommitAfter
	void onSelectedFromSaveSchool() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(school);
			school = new School();
			initGridSchool();

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));

			ajaxResponseRenderer.addRender(schoolFormZone).addRender(
					schoolListZone);
		}
	}

	void onActionFromEditSchool(School school) {
		if (request.isXHR()) {
			this.school = school;
			ajaxResponseRenderer.addRender(schoolFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteSchool(School school) {
//		if (request.isXHR()) {
			dao.deleteObject(school);
			initGridSchool();

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successDeleteMessage"));

//			ajaxResponseRenderer.addRender(schoolListZone);
//		}
	}

	void onSelectedFromSearchSchool() {
		if (request.isXHR()) {
			listSchool = dao.getListSchool(school.getName());
			ajaxResponseRenderer.addRender(schoolListZone);
		}
	}

	void onSelectedFromCancelSchool() {
		if (request.isXHR()) {
			school = new School();
			ajaxResponseRenderer.addRender(schoolListZone).addRender(
					schoolFormZone);
		}
	}

	@CommitAfter
	void onSelectedFromSaveOccupation() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(occupation);
			occupation = new Occupation();
			initGridOccupation();

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));

			ajaxResponseRenderer.addRender(occupationFormZone).addRender(
					occupationListZone);
		}
	}

	void onActionFromEditOccupation(Occupation occupation) {
		if (request.isXHR()) {
			this.occupation = occupation;
			ajaxResponseRenderer.addRender(occupationFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteOccupation(Occupation occupation) {
//		if (request.isXHR()) {
			dao.deleteObject(occupation);
			initGridOccupation();

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successDeleteMessage"));

//			ajaxResponseRenderer.addRender(occupationListZone);
//		}
	}

	void onSelectedFromSearchOccupation() {
		if (request.isXHR()) {
			listOccupation = dao.getListOccupation(occupation.getSchoolType());
			ajaxResponseRenderer.addRender(occupationListZone);
		}
	}

	@CommitAfter
	void onSelectedFromSaveOccRole() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(occupationRole);
			occupationRole = new OccupationRole();
			initGridOccupationRole();

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));

			ajaxResponseRenderer.addRender(occupationRoleFormZone).addRender(
					occupationRoleListZone);
		}
	}

	void onActionFromEditOccRole(OccupationRole occupationRole) {
		if (request.isXHR()) {
			this.occupationRole = occupationRole;
			ajaxResponseRenderer.addRender(occupationRoleFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteOccRole(OccupationRole occupationRole) {
//		if (request.isXHR()) {
			dao.deleteObject(occupationRole);
			initGridOccupationRole();

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successDeleteMessage"));

//			ajaxResponseRenderer.addRender(occupationRoleFormZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveAppointment() {
		if (request.isXHR()) {
			appointment.setAppointmentType(appointmentType);
			dao.saveOrUpdateObject(appointment);
			appointment = new Appointment();

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));

			ajaxResponseRenderer.addRender(appointmentFormZone).addRender(
					appointmentListZone);
		}
	}

	void onActionFromEditAppointment(Appointment appointment) {
		if (request.isXHR()) {
			this.appointment = appointment;
			ajaxResponseRenderer.addRender(appointmentFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteAppointment(Appointment appointment) {
//		if (request.isXHR()) {
			dao.deleteObject(appointment);

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successDeleteMessage"));

//			ajaxResponseRenderer.addRender(appointmentListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveOccupationType() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(occupationType);
			occupationType = new OccupationType();

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));

			ajaxResponseRenderer.addRender(occupationTypeFormZone).addRender(
					occupationTypeListZone);
		}
	}

	void onActionFromEditOccupationType(OccupationType occupationType) {
		if (request.isXHR()) {
			this.occupationType = occupationType;
			ajaxResponseRenderer.addRender(occupationTypeFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteOccupationType(OccupationType occupationType) {
//		if (request.isXHR()) {
			dao.deleteObject(occupationType);

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successDeleteMessage"));

//			ajaxResponseRenderer.addRender(occupationTypeListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveSalaryNetwork() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(salaryNetwork);
			salaryNetwork = new SalaryNetwork();
			initGridSalaryNetwork();

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));

			ajaxResponseRenderer.addRender(salaryNetworkFormZone).addRender(
					salaryNetworkListZone);
		}
	}

	void onActionFromEditSalaryNetwork(SalaryNetwork salaryNetwork) {
		if (request.isXHR()) {
			this.salaryNetwork = salaryNetwork;
			ajaxResponseRenderer.addRender(salaryNetworkFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteSalaryNetwork(SalaryNetwork salaryNetwork) {
//		if (request.isXHR()) {
			dao.deleteObject(salaryNetwork);
			initGridSalaryNetwork();

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successDeleteMessage"));

//			ajaxResponseRenderer.addRender(salaryNetworkListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveSalary() {
		if (request.isXHR()) {

			dao.saveOrUpdateObject(salary);

			salary = new TuriinZahirgaaSalaryNetwork();

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));

			ajaxResponseRenderer.addRender(salaryNetworkConfigFormZone)
					.addRender(salaryNetworkConfigListZone);
		}
	}

	void onActionFromEditSalaryNetworkConfig(
			TuriinZahirgaaSalaryNetwork turiinZahirgaaSalaryNetwork) {
		if (request.isXHR()) {
			this.salary = turiinZahirgaaSalaryNetwork;

			ajaxResponseRenderer.addRender(salaryNetworkConfigFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteSalaryNetworkConfig(
			TuriinZahirgaaSalaryNetwork turiinZahirgaaSalaryNetwork) {
//		if (request.isXHR()) {
			dao.deleteObject(turiinZahirgaaSalaryNetwork);

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successDeleteMessage"));

//			ajaxResponseRenderer.addRender(salaryNetworkConfigListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveSalaryScale() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(salaryScale);
			salaryScale = new SalaryScale();

			ajaxResponseRenderer.addRender(salaryScaleFormZone).addRender(
					salaryScaleListZone);
		}
	}

	void onActionFromEditSalaryScale(SalaryScale salaryScale) {
		if (request.isXHR()) {
			this.salaryScale = salaryScale;
			ajaxResponseRenderer.addRender(salaryScaleFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteSalaryScale(SalaryScale salaryScale) {
//		if (request.isXHR()) {
			dao.deleteObject(salaryScale);
			ajaxResponseRenderer.addRender(salaryScaleListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveDisplacementCause() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(displacementCause);
			displacementCause = new DisplacementCause();

			ajaxResponseRenderer.addRender(displacementCauseFormZone)
					.addRender(displacementCauseListZone);
		}
	}

	void onActionFromEditdisplacementCause(DisplacementCause displacementCause) {
		if (request.isXHR()) {
			this.displacementCause = displacementCause;

			ajaxResponseRenderer.addRender(displacementCauseFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeletedisplacementCause(DisplacementCause displacementCause) {
//		if (request.isXHR()) {
			dao.deleteObject(displacementCause);

//			ajaxResponseRenderer.addRender(displacementCauseListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveQuitJobCause() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(quitJobCause);
			quitJobCause = new QuitJobCause();

			ajaxResponseRenderer.addRender(quitJobCauseFormZone).addRender(
					quitJobCauseListZone);
		}
	}

	void onActionFromEditQuitJobCause(QuitJobCause quitJobCause) {
		if (request.isXHR()) {
			this.quitJobCause = quitJobCause;
			ajaxResponseRenderer.addRender(quitJobCauseFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteQuitJobCause(QuitJobCause quitJobCause) {
//		if (request.isXHR()) {
			dao.deleteObject(quitJobCause);
//			ajaxResponseRenderer.addRender(quitJobCauseListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveAdditionalSalary() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(additionalSalary);
			additionalSalary = new AdditionalSalaryType();

			ajaxResponseRenderer.addRender(additionalSalaryFormZone).addRender(
					additionalSalaryListZone);
		}
	}

	void onActionFromEditAdditionalSalary(AdditionalSalaryType type) {
		this.additionalSalary = type;

		ajaxResponseRenderer.addRender(additionalSalaryFormZone);
	}

	@CommitAfter
	void onActionFromDeleteAdditionalSalary(
			AdditionalSalaryType additionalSalary) {
//		if (request.isXHR()) {
			dao.deleteObject(additionalSalary);

//			ajaxResponseRenderer.addRender(additionalSalaryListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveRetireAge() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(retireAge);
			retireAge = new RetireAge();

			ajaxResponseRenderer.addRender(retireAgeFormZone).addRender(
					retireAgeListZone);
		}
	}

	void onActionFromEditRetireAge(RetireAge retireAge) {
		if (request.isXHR()) {
			this.retireAge = retireAge;
			ajaxResponseRenderer.addRender(retireAgeFormZone);
		}
	}

	@CommitAfter
	void onSelectedFromSaveOccupationRank() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(occupationRank);
			occupationRank = new OccupationRank();

			ajaxResponseRenderer.addRender(occupationRankFormZone).addRender(
					occupationRankListZone);
		}
	}

	void onActionFromEditOccupationRank(OccupationRank occupationRank) {
		if (request.isXHR()) {
			this.occupationRank = occupationRank;
			ajaxResponseRenderer.addRender(occupationRankFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteOccupationRank(OccupationRank occupationRank) {
//		if (request.isXHR()) {
			dao.deleteObject(occupationRank);
//			ajaxResponseRenderer.addRender(occupationRankListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveCountry() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(country);
			country = new Country();

			ajaxResponseRenderer.addRender(countryFormZone).addRender(
					countryListZone);
		}
	}

	void onActionFromEditCountry(Country country) {
		if (request.isXHR()) {
			this.country = country;
			ajaxResponseRenderer.addRender(countryFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteCountry(Country country) {
//		if (request.isXHR()) {
			dao.deleteObject(country);
//			ajaxResponseRenderer.addRender(countryListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveDegreeType() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(degreeType);
			degreeType = new DegreeType();

			ajaxResponseRenderer.addRender(degreeTypeFormZone).addRender(
					degreeTypeListZone);
		}
	}

	void onActionFromEditDegreeType(DegreeType degreeType) {
		if (request.isXHR()) {
			this.degreeType = degreeType;
			ajaxResponseRenderer.addRender(degreeTypeFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteDegreeType(DegreeType degreeType) {
//		if (request.isXHR()) {
			dao.deleteObject(degreeType);
//			ajaxResponseRenderer.addRender(degreeTypeListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveEducationDegree() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(educationDegree);
			educationDegree = new EducationDegree();
			ajaxResponseRenderer.addRender(educationDegreeFormZone).addRender(
					educationDegreeListZone);
		}
	}

	void onActionFromEditEducationDegree(EducationDegree educationDegree) {
		if (request.isXHR()) {
			this.educationDegree = educationDegree;
			ajaxResponseRenderer.addRender(educationDegreeFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteEducationDegree(EducationDegree educationDegree) {
//		if (request.isXHR()) {
			dao.deleteObject(educationDegree);
//			ajaxResponseRenderer.addRender(educationDegreeListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveSkillType() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(skillType);
			skillType = new SkillGroup();

			ajaxResponseRenderer.addRender(skillTypeFormZone).addRender(
					skillTypeListZone);
		}
	}

	void onActionFromEditSkillType(SkillGroup skillType) {
		if (request.isXHR()) {
			this.skillType = skillType;

			ajaxResponseRenderer.addRender(skillTypeFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteSkillType(SkillGroup skillType) {
//		if (request.isXHR()) {
			dao.deleteObject(skillType);
//			ajaxResponseRenderer.addRender(skillTypeListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveSkill() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(skill);
			skill = new SkillType();

			ajaxResponseRenderer.addRender(skillFormZone).addRender(
					skillListZone);
		}
	}

	void onActionFromEditSkill(SkillType skill) {
		if (request.isXHR()) {
			this.skill = skill;
			ajaxResponseRenderer.addRender(skillFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteSkill(SkillType skill) {
//		if (request.isXHR()) {
			dao.deleteObject(skill);
//			ajaxResponseRenderer.addRender(skillListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveOrigin() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(origin);
			origin = new Origin();

			ajaxResponseRenderer.addRender(originFormZone).addRender(
					originListZone);
		}
	}

	void onActionFromEditOrigin(Origin origin) {
		if (request.isXHR()) {
			this.origin = origin;
			ajaxResponseRenderer.addRender(originFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteOrigin(Origin origin) {
//		if (request.isXHR()) {
			dao.deleteObject(origin);
//			ajaxResponseRenderer.addRender(originListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveAscription() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(ascription);
			ascription = new Ascription();

			ajaxResponseRenderer.addRender(ascriptionFormZone).addRender(
					ascriptionListZone);
		}
	}

	void onActionFromEditAscription(Ascription ascription) {
		if (request.isXHR()) {
			this.ascription = ascription;
			ajaxResponseRenderer.addRender(ascriptionFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteAscription(Ascription ascription) {
//		if (request.isXHR()) {
			dao.deleteObject(ascription);
//			ajaxResponseRenderer.addRender(ascriptionListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveLanguage() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(foreignLanguage);
			foreignLanguage = new ForeignLanguage();

			ajaxResponseRenderer.addRender(languageFormZone).addRender(
					languageListZone);
		}
	}

	void onActionFromEditLanguage(ForeignLanguage language) {
		if (request.isXHR()) {
			this.foreignLanguage = language;
			ajaxResponseRenderer.addRender(languageFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteLanguage(ForeignLanguage language) {
//		if (request.isXHR()) {
			dao.deleteObject(language);
//			ajaxResponseRenderer.addRender(languageListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveComputer() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(computerProgram);
			computerProgram = new ComputerProgram();

			ajaxResponseRenderer.addRender(computerFormZone).addRender(
					computerListZone);
		}
	}

	void onActionFromEditComputer(ComputerProgram program) {
		if (request.isXHR()) {
			this.computerProgram = program;
			ajaxResponseRenderer.addRender(computerFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteComputer(ComputerProgram program) {
//		if (request.isXHR()) {
			dao.deleteObject(program);
//			ajaxResponseRenderer.addRender(computerListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveFacility() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(facility);
			facility = new Facility();

			ajaxResponseRenderer.addRender(facilityFormZone).addRender(
					facilityListZone);
		}
	}

	void onActionFromEditFacility(Facility facility) {
		if (request.isXHR()) {
			this.facility = facility;
			ajaxResponseRenderer.addRender(facilityFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteFacility(Facility facility) {
//		if (request.isXHR()) {
			dao.deleteObject(facility);
//			ajaxResponseRenderer.addRender(facilityListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveAcademicRank() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(academicRank);
			academicRank = new AcademicRank();

			ajaxResponseRenderer.addRender(academicRankListZone).addRender(
					academicRankFormZone);
		}
	}

	void onActionFromEditAcademicRank(AcademicRank rank) {
		if (request.isXHR()) {
			this.academicRank = rank;
			ajaxResponseRenderer.addRender(facilityFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteAcademicRank(AcademicRank rank) {
//		if (request.isXHR()) {
			dao.deleteObject(rank);
//			ajaxResponseRenderer.addRender(academicRankListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveRelativeType() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(relativeType);
			relativeType = new RelativeType();

			ajaxResponseRenderer.addRender(relativeTypeFormZone).addRender(
					relativeTypeListZone);
		}
	}

	void onActionFromEditRelativeType(RelativeType type) {
		if (request.isXHR()) {
			this.relativeType = type;
			ajaxResponseRenderer.addRender(relativeTypeFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteRelativeType(RelativeType type) {
//		if (request.isXHR()) {
			dao.deleteObject(type);
//			ajaxResponseRenderer.addRender(relativeTypeListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveTravelType() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(travelType);
			travelType = new TravelType();

			ajaxResponseRenderer.addRender(travelTypeFormZone).addRender(
					travelTypeListZone);
		}
	}

	void onActionFromEditTravelType(TravelType type) {
		if (request.isXHR()) {
			this.travelType = type;
			ajaxResponseRenderer.addRender(travelTypeFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteTravelType(TravelType type) {
//		if (request.isXHR()) {
			dao.deleteObject(type);
//			ajaxResponseRenderer.addRender(travelTypeListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveFamilyAppointment() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(familyAppointmentType);
			familyAppointmentType = new FamilyAppointmentType();

			ajaxResponseRenderer.addRender(familyAppointmentFormZone)
					.addRender(familyAppointmentListZone);
		}
	}

	void onActionFromEditFamilyAppointment(FamilyAppointmentType type) {
		if (request.isXHR()) {
			this.familyAppointmentType = type;
			ajaxResponseRenderer.addRender(familyAppointmentFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteFamilyAppointment(TravelType type) {
//		if (request.isXHR()) {
			dao.deleteObject(type);
//			ajaxResponseRenderer.addRender(familyAppointmentListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveMilitaryRank() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(militaryRank);
			militaryRank = new MilitaryRank();

			ajaxResponseRenderer.addRender(militaryRankFormZone).addRender(
					militaryRankListZone);
		}
	}

	void onActionFromEditMilitaryRank(MilitaryRank rank) {
		if (request.isXHR()) {
			this.militaryRank = rank;
			ajaxResponseRenderer.addRender(militaryRankFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteMilitaryRank(MilitaryRank rank) {
//		if (request.isXHR()) {
			dao.deleteObject(rank);
//			ajaxResponseRenderer.addRender(militaryRankListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveCommandSubject() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(commandSubject);
			commandSubject = new CommandSubject();

			ajaxResponseRenderer.addRender(commandSubjectFormZone).addRender(
					commandSubjectListZone);
		}
	}

	void onActionFromEditCommandSubject(CommandSubject subject) {
		if (request.isXHR()) {
			this.commandSubject = subject;
			ajaxResponseRenderer.addRender(commandSubjectFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteCommandSubject(CommandSubject subject) {
//		if (request.isXHR()) {
			dao.deleteObject(subject);
//			ajaxResponseRenderer.addRender(commandSubjectListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveAllowanceType() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(allowanceType);
			allowanceType = new AllowanceType();

			ajaxResponseRenderer.addRender(allowanceTypeFormZone).addRender(
					allowanceTypeListZone);
		}
	}

	void onActionFromEditAllowanceType(AllowanceType _allowanceType) {
		if (request.isXHR()) {
			this.allowanceType = _allowanceType;
			ajaxResponseRenderer.addRender(allowanceTypeFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteAllowanceType(AllowanceType _allowanceType) {
//		if (request.isXHR()) {
			dao.deleteObject(_allowanceType);
//			ajaxResponseRenderer.addRender(allowanceTypeListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveVacationType() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(vacationType);
			vacationType = new VacationType();

			ajaxResponseRenderer.addRender(vacationTypeFormZone).addRender(
					vacationTypeListZone);
		}
	}

	void onActionFromEditVacationType(VacationType _vacationType) {
		if (request.isXHR()) {
			this.vacationType = _vacationType;
			ajaxResponseRenderer.addRender(vacationTypeFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteVacationType(VacationType _vacationType) {
//		if (request.isXHR()) {
			dao.deleteObject(_vacationType);
//			ajaxResponseRenderer.addRender(vacationTypeListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveScienceDoctor() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(scienceDoctor);
			scienceDoctor = new DegreeTypeOfScienceDoctor();
			listScienceDoctorType = dao.getListScienceDoctor();
			ajaxResponseRenderer.addRender(ScienceDoctorFormZone).addRender(
					ScienceDoctorListZone);
		}
	}

	void onActionFromEditScienceDoctor(DegreeTypeOfScienceDoctor _scienceDoctor) {
		if (request.isXHR()) {
			this.scienceDoctor = _scienceDoctor;
			ajaxResponseRenderer.addRender(ScienceDoctorFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteScienceDoctor(
			DegreeTypeOfScienceDoctor _scienceDoctor) {
//		if (request.isXHR()) {
			dao.deleteObject(_scienceDoctor);
//			ajaxResponseRenderer.addRender(ScienceDoctorListZone);
//		}
	}

	/*
	 * METHODS
	 */

	public String getPeriod() {
		if (valueMilitaryRank.getPeriod() == null)
			return "0";
		return valueMilitaryRank.getPeriod().toString();
	}

	public String getRank() {
		return valueOccupationRank.getRank().toString();
	}

	public String getMaleAge() {
		if (valueRetireAge == null || valueRetireAge.getMaleAge() == null)
			return "";
		return valueRetireAge.getMaleAge().toString();
	}

	public String getFemaleAge() {
		if (valueRetireAge == null || valueRetireAge.getFemaleAge() == null)
			return "";
		return valueRetireAge.getFemaleAge().toString();
	}

	public int getNumber() {
		return listAppointment.indexOf(valueAppointment) + 1;
	}

	public String getGuitsetgehAjliinNemegdel() {
		if (valueAppointment != null
				&& valueAppointment.getGaNemegdel() == true)
			return "Тийм";
		return "Үгүй";
	}

	public String getNumberAllowanceType() {
		return listAllowanceType.indexOf(valueAllowanceType) + 1 + "";
	}

	public String getNumberVacationType() {
		return listVacationType.indexOf(valueVacationType) + 1 + "";
	}

	public String getCommandSubjectNumber() {
		return listCommandSubject.indexOf(valueCommandSubject) + 1 + "";
	}

	public String getMilitaryRankNumber() {
		return listMilitaryRank.indexOf(valueMilitaryRank) + 1 + "";
	}

	public String getOccupationNumber() {
		return listOccupationRole.indexOf(valueOccupationRole) + 1 + "";
	}

	public String getOccupationTypeNumber() {
		return listOccupationType.indexOf(valueOccupationType) + 1 + "";
	}

	public String getSalaryNetworkNumber() {
		return listSalaryNetwork.indexOf(valueSalaryNetwork) + 1 + "";
	}

	public String getSalaryScaleNumber() {
		return listSalaryScale.indexOf(valueSalaryScale) + 1 + "";
	}

	public String getDisplacementCauseNumber() {
		return listDisplacementCause.indexOf(valueDisplacementCause) + 1 + "";
	}

	public String getQuitJobCauseNumber() {
		return listQuitJobCause.indexOf(valueQuitJobCause) + 1 + "";
	}

	public String getSalaryNetworkConfigNumber() {
		return listTuriinZahirgaaSalaryNetwork
				.indexOf(valueTuriinZahirgaaSalaryNetwork) + 1 + "";
	}

	public String getAdditionalSalaryNumber() {
		return listAdditionalSalaryType.indexOf(valueAdditionalSalaryType) + 1
				+ "";
	}

	public String getCountryNumber() {
		return listCountry.indexOf(valueCountry) + 1 + "";
	}

	public String getCityProvinceNumber() {
		return listCityProvince.indexOf(valueCityProvince) + 1 + "";
	}

	public String getCityNumber() {
		return listCity.indexOf(valueCity) + 1 + "";
	}

	public String getSumNumber() {
		return listDistrictSum.indexOf(valueDistrictSum) + 1 + "";
	}

	public String getSchoolNumber() {
		return listSchool.indexOf(valueSchool) + 1 + "";
	}

	public String getDegreeTypeNumber() {
		return listDegreeType.indexOf(valueDegreeType) + 1 + "";
	}

	public String getEducationDegreeNumber() {
		return listEducationDegree.indexOf(valueEducationDegree) + 1 + "";
	}

	public String getOccupationListNumber() {
		return listOccupation.indexOf(valueOccupation) + 1 + "";
	}

	public String getFamilyAppointmentNumber() {
		return listFamilyAppointmentType.indexOf(valueFamilyAppointmentType)
				+ 1 + "";
	}

	public String getFacilityNumber() {
		return listFacility.indexOf(valueFacility) + 1 + "";
	}

	public String getLanguageNumber() {
		return listForeignLanguage.indexOf(valueForeignLanguage) + 1 + "";
	}

	public String getTop() {
		if (valueMilitaryRank != null && valueMilitaryRank.getIsTop() != null
				&& valueMilitaryRank.getIsTop())
			return "Тийм";
		return "Үгүй";
	}

	public String getAscriptionNumber() {
		return listAscription.indexOf(valueAscription) + 1 + "";
	}

	public String getScienceDoctorNumber() {
		return listScienceDoctorType.indexOf(valueScienceDoctor) + 1 + "";
	}

	/*
	 * SELECET MODELS
	 */

	public SelectModel getCategorySelectionModel() {
		return new EnumSelectModel(AdditionalSalaryCategory.class, messages);
	}

	public SelectModel getCountrySelectionModel() {
		return new CountrySelectionModel(dao);
	}

	public SelectModel getCityProvinceSelectionModel() {
		return new CityProvinceSelectionModel(dao);
	}

	public SelectModel getUniversityTypeSelectionModel() {
		return new EnumSelectModel(UniversityType.class, messages);
	}

	public SelectModel getSchoolTypeSelectionModel() {
		return new EnumSelectModel(SchoolType.class, messages);
	}

	public SelectModel getOccupationTypeSelectionModel() {
		return new OccupationTypeSelectionModel(dao);
	}

	public SelectModel getTypeSelectionModel() {
		return new EnumSelectModel(AppointmentSortEmployee.class, messages);
	}

	public SelectModel getWageSelectionMoel() {
		return new EnumSelectModel(GetWage.class, messages);
	}

	public SelectModel getLevelSelectionModel() {
		return new OccupationLevelSelectionModel(dao, null);
	}

	public SelectModel getSalaryPhaseSelectionModel() {
		return new SalaryScaleSelectionModel(dao);
	}

	public SelectModel getQuitTypeSelectionModel() {
		return new EnumSelectModel(EmployeeCurrentStatus.class, messages);
	}

	public SelectModel getSkillGroupSelectionModel() {
		return new SkillGroupSelectionModel(dao);
	}

	public SelectModel getMilitaryRankTypeSelectionModel() {
		return new EnumSelectModel(MilitaryRankType.class, messages);
	}

}
