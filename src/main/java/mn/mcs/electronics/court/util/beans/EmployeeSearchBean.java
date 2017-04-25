package mn.mcs.electronics.court.util.beans;

import java.util.Date;

import mn.mcs.electronics.court.entities.configuration.AcademicRank;
import mn.mcs.electronics.court.entities.configuration.Appointment;
import mn.mcs.electronics.court.entities.configuration.Ascription;
import mn.mcs.electronics.court.entities.configuration.City;
import mn.mcs.electronics.court.entities.configuration.CityProvince;
import mn.mcs.electronics.court.entities.configuration.CommandSubject;
import mn.mcs.electronics.court.entities.configuration.Country;
import mn.mcs.electronics.court.entities.configuration.DegreeType;
import mn.mcs.electronics.court.entities.configuration.DistrictSum;
import mn.mcs.electronics.court.entities.configuration.MilitaryRank;
import mn.mcs.electronics.court.entities.configuration.Occupation;
import mn.mcs.electronics.court.entities.configuration.OccupationType;
import mn.mcs.electronics.court.entities.configuration.Origin;
import mn.mcs.electronics.court.entities.configuration.QuitJobCause;
import mn.mcs.electronics.court.entities.employee.AllowanceType;
import mn.mcs.electronics.court.entities.employee.Educations;
import mn.mcs.electronics.court.entities.employee.SahilgaShiitgel;
import mn.mcs.electronics.court.entities.employee.SahilgaTurul;
import mn.mcs.electronics.court.entities.employee.VacationType;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.AwardType;
import mn.mcs.electronics.court.enums.DisplacementCauseType;
import mn.mcs.electronics.court.enums.DisplacementType;
import mn.mcs.electronics.court.enums.EmployeeCurrentStatus;
import mn.mcs.electronics.court.enums.EmployeeDegreeStatus;
import mn.mcs.electronics.court.enums.EmployeeStatus;
import mn.mcs.electronics.court.enums.FamilyStatus;
import mn.mcs.electronics.court.enums.Gender;
import mn.mcs.electronics.court.enums.MilitaryRankType;
import mn.mcs.electronics.court.enums.TsolOlgohTurul;
import mn.mcs.electronics.court.enums.YesNo;

public class EmployeeSearchBean {
	
	private String firstname;
	
	private String lastname;
	
	private String registerNo;
	
	private Gender gender;
	
	private Organization organization;
	
	private OccupationType occupationType;
	
	private Country country;
	
	private City cityProvince; 
	
	private DistrictSum districtSum;	
	
//	private Origin origin;
	
	private Ascription ascription;

	private Occupation occupation;
	
	private String candidateGSchool;
	
	private Appointment appointment;
	
	private OccupationType appointmentType;
	
		/* Тэтгэврийн нас */
	private boolean retiredDate;
	
	/* Ерөнхий шүүгчийн томилогдох хугацаа */
	private boolean generalJudgeDate;
	
	private EmployeeStatus status;
	
	private EmployeeCurrentStatus currentStatus;
	
	private QuitJobCause quitJobType;
	
	private DegreeType educationDegree;
	
	private String quiteDateFrom;
	
	private String quiteDateTo;
	
	private DisplacementType displacementType;
	
	private DisplacementCauseType displacementCause;
	
	private VacationType vacationType;
	
	private Date chuluuEhlehOgnoo;
	
	private Date chuluuDuusahOgnoo;
	
	private Date chuluuDuusahOgnooJiremsen;
	
	private Date chuluuDuusahOgnooSurgalt;
	
	private String workedYearFrom;
	
	private String workedYearTo;
	
	private String ageFrom;
	
	private String ageTo;
	
	private String courtWorkedYearFrom;
	
	private String courtWorkedYearTo;
	
	private FamilyStatus familyStatus;
	
	/** Тэтгэмж **/
	
	private YesNo isAllowance;
	
	private String allowanceDateStart;
	
	private String allowanceDateEnd;
	
	private Double allowanceMoneyFrom;
	
	private Double allowanceMoneyTo;
	
	private CommandSubject allowanceCommandSubject;
	
	private AllowanceType allowanceType;
	
	private String allowanceTushaalDugaar;
	
	/** Шагнал **/
	
	private String award;
		
	private AwardType awardType;
	
	private Date awardDateStart;
	
	private Date awardDateEnd;
	
	private YesNo isAward;
	
	private String shagnalTushaalDugaar;	
	
	/** Сахилга **/
	
	private SahilgaShiitgel sahilga;
	
	private Date sahilgaDateStart;
	
	private Date sahilgaDateEnd;
	
	private Date sahilgaCancelDateStart;
	
	private Date sahilgaCancelDateEnd;
	
	private CommandSubject commandSubject;
	
	private SahilgaTurul sahilgaTurul;
	
	private YesNo isDemerit;
	
	private String sahilgaTushaalDugaar; 
	
	/** Цэргийн цол **/	
	private Date degreeDateFrom;
	
	private Date degreeDateTo;
	
	private EmployeeDegreeStatus employeeDegreeStatus;
	
	private MilitaryRank military;
	
	private MilitaryRankType militaryType; 
	
	private TsolOlgohTurul tsolOlgohTurul;
	
	private String tsolTushaalDugaar;
	
	/** Боловсрол **/
	private Educations education;
	
	private String eduStartDateFrom;
	
	private String eduStartDateTo;
	
	private String eduGraduateDateFrom;
	
	private String eduGraduateDateTo;
	
	private AcademicRank academinRank;
	
	private String academicRankFrom;
	
	private String academicRankTo;
	
	/**Сургалт**/
	private Country trainingCountry;

	private CityProvince trainingCity;

	private City trainingProvince;
	
	private String trainingStartDate;
	
	private String trainingEndDate;
	
	/**tsolOlgohTurul,
	 * tsolTushaalDugaar, sahilgaTushaalDugaar,allowanceCommandSubject,allowanceType, allowanceTushaalDugaar **/
	
	/* getter, setter */

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	
	public Occupation getOccupation() {
		return occupation;
	}

	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}
	
	public Ascription getAscription() {
		return ascription;
	}

	public void setAscription(Ascription ascription) {
		this.ascription = ascription;
	}
//	
//	public Origin getOrigin() {
//		return origin;
//	}
//
//	public void setOrigin(Origin origin) {
//		this.origin = origin;
//	}

	public Educations getEducation() {
		return education;
	}

	public void setEducation(Educations education) {
		this.education = education;
	}

	public City getCityProvince() {
		return cityProvince;
	}

	public void setCityProvince(City cityProvince) {
		this.cityProvince = cityProvince;
	}

	public DistrictSum getDistrictSum() {
		return districtSum;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public void setDistrictSum(DistrictSum districtSum) {
		this.districtSum = districtSum;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getCandidateGSchool() {
		return candidateGSchool;
	}

	public void setCandidateGSchool(String candidateGSchool) {
		this.candidateGSchool = candidateGSchool;
	}
	
	public boolean isRetiredDate() {
		return retiredDate;
	}

	public void setRetiredDate(boolean retiredDate) {
		this.retiredDate = retiredDate;
	}

	public boolean isGeneralJudgeDate() {
		return generalJudgeDate;
	}

	public void setGeneralJudgeDate(boolean generalJudgeDate) {
		this.generalJudgeDate = generalJudgeDate;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public EmployeeStatus getStatus() {
		return status;
	}

	public void setStatus(EmployeeStatus status) {
		this.status = status;
	}

	public EmployeeCurrentStatus getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(EmployeeCurrentStatus currentStatus) {
		this.currentStatus = currentStatus;
	}

	public QuitJobCause getQuitJobType() {
		return quitJobType;
	}

	public void setQuitJobType(QuitJobCause quitJobType) {
		this.quitJobType = quitJobType;
	}
	
	public DegreeType getEducationDegree() {
		return educationDegree;
	}

	public void setEducationDegree(DegreeType educationDegree) {
		this.educationDegree = educationDegree;
	}
	
	public AwardType getAwardType() {
		return awardType;
	}

	public void setAwardType(AwardType awardType) {
		this.awardType = awardType;
	}
	
	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}
	
	public SahilgaShiitgel getSahilga() {
		return sahilga;
	}

	public void setSahilga(SahilgaShiitgel sahilga) {
		this.sahilga = sahilga;
	}
	
	public Date getAwardDateStart() {
		return awardDateStart;
	}

	public void setAwardDateStart(Date awardDateStart) {
		this.awardDateStart = awardDateStart;
	}
	
	public Date getAwardDateEnd() {
		return awardDateEnd;
	}

	public void setAwardDateEnd(Date awardDateEnd) {
		this.awardDateEnd = awardDateEnd;
	}
	
	public Date getSahilgaDateStart() {
		return sahilgaDateStart;
	}

	public void setSahilgaDateStart(Date sahilgaDateStart) {
		this.sahilgaDateStart = sahilgaDateStart;
	}
	
	public Date getSahilgaDateEnd() {
		return sahilgaDateEnd;
	}

	public void setSahilgaDateEnd(Date sahilgaDateEnd) {
		this.sahilgaDateEnd = sahilgaDateEnd;
	}
	
	public Date getSahilgaCancelDateStart() {
		return sahilgaCancelDateStart;
	}

	public void setSahilgaCancelDateStart(Date sahilgaCancelDateStart) {
		this.sahilgaCancelDateStart = sahilgaCancelDateStart;
	}
	
	public Date getSahilgaCancelDateEnd() {
		return sahilgaCancelDateEnd;
	}

	public void setSahilgaCancelDateEnd(Date sahilgaCancelDateEnd) {
		this.sahilgaCancelDateEnd = sahilgaCancelDateEnd;
	}
	
	public CommandSubject getCommandSubject() {
		return commandSubject;
	}

	public void setCommandSubject(CommandSubject commandSubject) {
		this.commandSubject = commandSubject;
	}
	
	public SahilgaTurul getSahilgaTurul() {
		return sahilgaTurul;
	}

	public void setSahilgaTurul(SahilgaTurul sahilgaTurul) {
		this.sahilgaTurul = sahilgaTurul;
	}
	
	public YesNo getIsAward() {
		return isAward;
	}

	public void setIsAward(YesNo isAward) {
		this.isAward = isAward;
	}
	
	public YesNo getIsDemerit() {
		return isDemerit;
	}

	public void setIsDemerit(YesNo isDemerit) {
		this.isDemerit = isDemerit;
	}
	
	public YesNo getIsAllowance() {
		return isAllowance;
	}

	public void setIsAllowance(YesNo isAllowance) {
		this.isAllowance = isAllowance;
	}

	public void setEmployeeDegreeStatus(EmployeeDegreeStatus employeeDegreeStatus) {
		this.employeeDegreeStatus = employeeDegreeStatus;
	}

	public EmployeeDegreeStatus getEmployeeDegreeStatus() {
		return employeeDegreeStatus;
	}

	public void setAllowanceDateStart(String allowanceDateStart) {
		this.allowanceDateStart = allowanceDateStart;
	}

	public String getAllowanceDateStart() {
		return allowanceDateStart;
	}

	public void setAllowanceDateEnd(String allowanceDateEnd) {
		this.allowanceDateEnd = allowanceDateEnd;
	}

	public String getAllowanceDateEnd() {
		return allowanceDateEnd;
	}

	public void setAllowanceMoneyFrom(Double allowanceMoneyFrom) {
		this.allowanceMoneyFrom = allowanceMoneyFrom;
	}

	public Double getAllowanceMoneyFrom() {
		return allowanceMoneyFrom;
	}

	public void setAllowanceMoneyTo(Double allowanceMoneyTo) {
		this.allowanceMoneyTo = allowanceMoneyTo;
	}

	public Double getAllowanceMoneyTo() {
		return allowanceMoneyTo;
	}

	public void setDegreeDateFrom(Date degreeDateFrom) {
		this.degreeDateFrom = degreeDateFrom;
	}

	public Date getDegreeDateFrom() {
		return degreeDateFrom;
	}

	public void setDegreeDateTo(Date degreeDateTo) {
		this.degreeDateTo = degreeDateTo;
	}

	public Date getDegreeDateTo() {
		return degreeDateTo;
	}

	public void setQuiteDateFrom(String quiteDateFrom) {
		this.quiteDateFrom = quiteDateFrom;
	}

	public String getQuiteDateFrom() {
		return quiteDateFrom;
	}

	public void setQuiteDateTo(String quiteDateTo) {
		this.quiteDateTo = quiteDateTo;
	}

	public String getQuiteDateTo() {
		return quiteDateTo;
	}

	public void setDisplacementType(DisplacementType displacementType) {
		this.displacementType = displacementType;
	}

	public DisplacementType getDisplacementType() {
		return displacementType;
	}

	public void setDisplacementCause(DisplacementCauseType displacementCause) {
		this.displacementCause = displacementCause;
	}

	public DisplacementCauseType getDisplacementCause() {
		return displacementCause;
	}

	public void setVacationType(VacationType vacationType) {
		this.vacationType = vacationType;
	}

	public VacationType getVacationType() {
		return vacationType;
	}

	public void setChuluuEhlehOgnoo(Date chuluuEhlehOgnoo) {
		this.chuluuEhlehOgnoo = chuluuEhlehOgnoo;
	}

	public Date getChuluuEhlehOgnoo() {
		return chuluuEhlehOgnoo;
	}

	public void setChuluuDuusahOgnoo(Date chuluuDuusahOgnoo) {
		this.chuluuDuusahOgnoo = chuluuDuusahOgnoo;
	}

	public Date getChuluuDuusahOgnoo() {
		return chuluuDuusahOgnoo;
	}

	public MilitaryRank getMilitary() {
		return military;
	}

	public void setMilitary(MilitaryRank military) {
		this.military = military;
	}

	public MilitaryRankType getMilitaryType() {
		return militaryType;
	}

	public void setMilitaryType(MilitaryRankType militaryType) {
		this.militaryType = militaryType;
	}

	public String getWorkedYearFrom() {
		return workedYearFrom;
	}

	public void setWorkedYearFrom(String workedYearFrom) {
		this.workedYearFrom = workedYearFrom;
	}

	public String getWorkedYearTo() {
		return workedYearTo;
	}

	public void setWorkedYearTo(String workedYearTo) {
		this.workedYearTo = workedYearTo;
	}

	public String getAgeFrom() {
		return ageFrom;
	}

	public void setAgeFrom(String ageFrom) {
		this.ageFrom = ageFrom;
	}

	public String getAgeTo() {
		return ageTo;
	}

	public void setAgeTo(String ageTo) {
		this.ageTo = ageTo;
	}

	public String getCourtWorkedYearFrom() {
		return courtWorkedYearFrom;
	}

	public void setCourtWorkedYearFrom(String courtWorkedYearFrom) {
		this.courtWorkedYearFrom = courtWorkedYearFrom;
	}

	public String getCourtWorkedYearTo() {
		return courtWorkedYearTo;
	}

	public void setCourtWorkedYearTo(String courtWorkedYearTo) {
		this.courtWorkedYearTo = courtWorkedYearTo;
	}

	public FamilyStatus getFamilyStatus() {
		return familyStatus;
	}

	public void setFamilyStatus(FamilyStatus familyStatus) {
		this.familyStatus = familyStatus;
	}

	public Country getTrainingCountry() {
		return trainingCountry;
	}

	public void setTrainingCountry(Country trainingCountry) {
		this.trainingCountry = trainingCountry;
	}

	public CityProvince getTrainingCity() {
		return trainingCity;
	}

	public void setTrainingCity(CityProvince trainingCity) {
		this.trainingCity = trainingCity;
	}

	public City getTrainingProvince() {
		return trainingProvince;
	}

	public void setTrainingProvince(City trainingProvince) {
		this.trainingProvince = trainingProvince;
	}

	public String getTrainingStartDate() {
		return trainingStartDate;
	}

	public void setTrainingStartDate(String trainingStartDate) {
		this.trainingStartDate = trainingStartDate;
	}

	public String getTrainingEndDate() {
		return trainingEndDate;
	}

	public void setTrainingEndDate(String trainingEndDate) {
		this.trainingEndDate = trainingEndDate;
	}

	public AcademicRank getAcademinRank() {
		return academinRank;
	}

	public void setAcademinRank(AcademicRank academinRank) {
		this.academinRank = academinRank;
	}

	public String getAcademicRankFrom() {
		return academicRankFrom;
	}

	public void setAcademicRankFrom(String academicRankFrom) {
		this.academicRankFrom = academicRankFrom;
	}

	public String getAcademicRankTo() {
		return academicRankTo;
	}

	public void setAcademicRankTo(String academicRankTo) {
		this.academicRankTo = academicRankTo;
	}

	public TsolOlgohTurul getTsolOlgohTurul() {
		return tsolOlgohTurul;
	}

	public void setTsolOlgohTurul(TsolOlgohTurul tsolOlgohTurul) {
		this.tsolOlgohTurul = tsolOlgohTurul;
	}

	public String getTsolTushaalDugaar() {
		return tsolTushaalDugaar;
	}

	public void setTsolTushaalDugaar(String tsolTushaalDugaar) {
		this.tsolTushaalDugaar = tsolTushaalDugaar;
	}

	public String getSahilgaTushaalDugaar() {
		return sahilgaTushaalDugaar;
	}

	public void setSahilgaTushaalDugaar(String sahilgaTushaalDugaar) {
		this.sahilgaTushaalDugaar = sahilgaTushaalDugaar;
	}

	public String getShagnalTushaalDugaar() {
		return shagnalTushaalDugaar;
	}

	public Date getChuluuDuusahOgnooJiremsen() {
		return chuluuDuusahOgnooJiremsen;
	}

	public void setChuluuDuusahOgnooJiremsen(Date chuluuDuusahOgnooJiremsen) {
		this.chuluuDuusahOgnooJiremsen = chuluuDuusahOgnooJiremsen;
	}

	public void setShagnalTushaalDugaar(String shagnalTushaalDugaar) {
		this.shagnalTushaalDugaar = shagnalTushaalDugaar;
	}

	public CommandSubject getAllowanceCommandSubject() {
		return allowanceCommandSubject;
	}

	public void setAllowanceCommandSubject(CommandSubject allowanceCommandSubject) {
		this.allowanceCommandSubject = allowanceCommandSubject;
	}

	public AllowanceType getAllowanceType() {
		return allowanceType;
	}

	public void setAllowanceType(AllowanceType allowanceType) {
		this.allowanceType = allowanceType;
	}

	public String getAllowanceTushaalDugaar() {
		return allowanceTushaalDugaar;
	}

	public void setAllowanceTushaalDugaar(String allowanceTushaalDugaar) {
		this.allowanceTushaalDugaar = allowanceTushaalDugaar;
	}

	public String getEduStartDateFrom() {
		return eduStartDateFrom;
	}

	public void setEduStartDateFrom(String eduStartDateFrom) {
		this.eduStartDateFrom = eduStartDateFrom;
	}

	public String getEduStartDateTo() {
		return eduStartDateTo;
	}

	public void setEduStartDateTo(String eduStartDateTo) {
		this.eduStartDateTo = eduStartDateTo;
	}

	public String getEduGraduateDateFrom() {
		return eduGraduateDateFrom;
	}

	public void setEduGraduateDateFrom(String eduGraduateDateFrom) {
		this.eduGraduateDateFrom = eduGraduateDateFrom;
	}

	public String getEduGraduateDateTo() {
		return eduGraduateDateTo;
	}

	public void setEduGraduateDateTo(String eduGraduateDateTo) {
		this.eduGraduateDateTo = eduGraduateDateTo;
	}

	public OccupationType getOccupationType() {
		return occupationType;
	}

	public void setOccupationType(OccupationType occupationType) {
		this.occupationType = occupationType;
	}

	public Date getChuluuDuusahOgnooSurgalt() {
		return chuluuDuusahOgnooSurgalt;
	}

	public void setChuluuDuusahOgnooSurgalt(Date chuluuDuusahOgnooSurgalt) {
		this.chuluuDuusahOgnooSurgalt = chuluuDuusahOgnooSurgalt;
	}

	public OccupationType getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(OccupationType appointmentType) {
		this.appointmentType = appointmentType;
	}	
	
}
