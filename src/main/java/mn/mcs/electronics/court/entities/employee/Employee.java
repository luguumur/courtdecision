package mn.mcs.electronics.court.entities.employee;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.entities.User;
import mn.mcs.electronics.court.entities.configuration.AcademicRank;
import mn.mcs.electronics.court.entities.configuration.Appointment;
import mn.mcs.electronics.court.entities.configuration.Ascription;
import mn.mcs.electronics.court.entities.configuration.City;
import mn.mcs.electronics.court.entities.configuration.CityProvince;
import mn.mcs.electronics.court.entities.configuration.Country;
import mn.mcs.electronics.court.entities.configuration.DistrictSum;
import mn.mcs.electronics.court.entities.configuration.EmployeeLevel;
import mn.mcs.electronics.court.entities.configuration.Occupation;
import mn.mcs.electronics.court.entities.configuration.Origin;
import mn.mcs.electronics.court.entities.configuration.RelativeType;
import mn.mcs.electronics.court.entities.organization.Department;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.EmployeeCurrentStatus;
import mn.mcs.electronics.court.enums.EmployeeStatus;
import mn.mcs.electronics.court.enums.FamilyStatus;
import mn.mcs.electronics.court.enums.Gender;
import mn.mcs.electronics.court.enums.YesNo;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "employee")
public class Employee extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6875281881776128376L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;

	@Column(name = "register_no")
	private String registerNo;

	@Column(name = "old_reg")
	private String oldReg;

	@Column(name = "social_insurance_number")
	private String socialInsuranceNumber;

	@Column(name = "health_insurance_number")
	private String healthInsuranceNumber;

	@Column(name = "id_card_number")
	private String idCardNumber;

	@Column(name = "military_number")
	private String militaryNumber;

	@Column(name = "court_number")
	private String courtNumber;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastname;

	@Column(name = "family_name")
	private String familyName;

	@Column(name = "birth_date")
	private Date birthDate;

	@ManyToOne
	@JoinColumn(name = "occupation")
	private Occupation occupation;

	@Column(name = "occupation_other")
	private String occupationOther;

	@ManyToOne
	@JoinColumn(name = "military_rank")
	private AcademicRank militaryRank;

	@Column(name = "gender")
	private Gender gender;

	@ManyToOne
	@JoinColumn(name = "birth_country")
	private Country birthCountry;
	
	@ManyToOne
	@JoinColumn(name = "birth_city_province")
	private City birthCityProvince;
	
	@ManyToOne
	@JoinColumn(name = "birth_city")
	private CityProvince birthCity;

	@ManyToOne
	@JoinColumn(name = "birth_district_sum")
	private DistrictSum birthDistrictSum;

	@Column(name = "birth_place")
	private String birthPlace;

	@ManyToOne
	@JoinColumn(name = "origin")
	private Origin origin;

	@ManyToOne
	@JoinColumn(name = "ascription")
	private Ascription ascription;

	@ManyToOne
	@JoinColumn(name = "add_country")
	private Country addCountry;

	@ManyToOne
	@JoinColumn(name = "add_city")
	private City addCity;

	@ManyToOne
	@JoinColumn(name = "add_district_sum")
	private DistrictSum addDistrictSum;

	@Column(name = "add_khoroo_bag")
	private String addKhorooBag;

	@Column(name = "add_street")
	private String addStreet;

	@Column(name = "address")
	private String address;

	@Column(name = "phone_no")
	private String phoneNo;

	@Column(name = "mobile_no")
	private String mobileNo;

	@Column(name = "work_phone_no")
	private String workPhoneNo;

	@Column(name = "work_fax")
	private String workFax;

	@Column(name = "eMail")
	private String eMail;

	@Column(name = "post_address")
	private String postAddress;

	@Column(name = "post_index")
	private String postIndex;

	@Column(name = "emergency_call_person_name")
	private String eCallPersonName;

	@ManyToOne
	@JoinColumn(name = "emergency_call_person_relative_type")
	private RelativeType eCallPersonRelativeType;

	@Column(name = "emergency_call_person_address")
	private String eCallPersonAddress;

	@Column(name = "emergency_call_person_phone_no")
	private String eCallPersonPhoneNo;

	@Column(name = "emergency_call_person_mobile_no")
	private String eCallPersonMobileNo;

	@Column(name = "emergency_call_person_email")
	private String eCallPersonEmail;

	@Column(name = "employee_status")
	private EmployeeStatus employeeStatus;

	@ManyToOne
	@JoinColumn(name = "department")
	private Department department;

	@ManyToOne
	@JoinColumn(name = "organization")
	private Organization organization;

	@ManyToOne
	@JoinColumn(name = "appointment")
	private Appointment appointment;

	@ManyToOne
	@JoinColumn(name = "employee_level")
	private EmployeeLevel employeeLevel;

	@Column(name = "picture_path")
	private String picturePath;

	@Column(name = "limit_of_split")
	private Integer limitOfSplit = 1;

	@Column(name = "is_exam_placeholder")
	private YesNo examPlaceHolder;

	@Column(name = "exam_placeholder_date")
	private Date examPlaceholderDate;

	@Column(name = "is_swear")
	private YesNo swear;

	@Column(name = "is_member")
	private Boolean isMember;

	@Column(name = "is_fill_declaration")
	private YesNo declaration;// Хөрөнгө орлогын мэдээлэл бүртгэх эсэх

	@Column(name = "swear_date")
	private Date swearDate;

	@Column(name = "employee_current_status")
	private EmployeeCurrentStatus employeeCurrentStatus;

	@Column(name = "system_user")
	private Boolean systemUser;

	@Column(name = "busad_skill_name1")
	private String busadSkillName1;

	@Column(name = "busad_skill_onoo1")
	private Integer busadSkillOnoo1;

	@Column(name = "busad_skill_name2")
	private String busadSkillName2;

	@Column(name = "busad_skill_onoo2")
	private Integer busadSkillOnoo2;

	@Column(name = "busad_skill_name3")
	private String busadSkillName3;

	@Column(name = "busad_skill_onoo3")
	private Integer busadSkillOnoo3;

	@Column(name = "mcse_admin")
	private Boolean mcseAdmin;

	/**
	 * Гэр бүлийн байдал
	 */

	@Column(name = "family_status")
	private FamilyStatus familyStatus;

	@Column(name = "emp_number")
	private Integer empNumber;

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "employee")
	private Set<Relatives> relative = new HashSet<Relatives>();

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "employee")
	private Set<Educations> educations = new HashSet<Educations>();

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "employee")
	private Set<Language> language = new HashSet<Language>();

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "employee")
	private Set<Displacement> displacement = new HashSet<Displacement>();

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "employee")
	private Set<Degrees> degrees = new HashSet<Degrees>();

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "employee")
	private Set<Honour> honour = new HashSet<Honour>();

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "employee")
	private Set<Skills> skills = new HashSet<Skills>();

	@OneToMany(orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "employee")
	private Set<User> user = new HashSet<User>();

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "employee")
	private Set<SalaryHistory> salary = new HashSet<SalaryHistory>();

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "employee")
	private Set<DegreeGrade> degreeGrade = new HashSet<DegreeGrade>();

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "employee")
	private Set<Training> training = new HashSet<Training>();

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "employee")
	private Set<QuitJob> quitJob = new HashSet<QuitJob>();

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "employee")
	private Set<Computer> computer = new HashSet<Computer>();

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "employee")
	private Set<Experience> experience = new HashSet<Experience>();

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "employee")
	private Set<TravelAbroad> travel = new HashSet<TravelAbroad>();

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "employee")
	private Set<Passport> passport = new HashSet<Passport>();

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "employee")
	private Set<ResultContract> resultContract = new HashSet<ResultContract>();

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "employee")
	private Set<Demerit> demerit = new HashSet<Demerit>();

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "employee")
	private Set<Sahilga> sahilga = new HashSet<Sahilga>();

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "employee")
	private Set<DegreeGradeRank> degreeGradeRank = new HashSet<DegreeGradeRank>();

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "employee")
	private Set<EmployeeMilitary> employeeMilitary = new HashSet<EmployeeMilitary>();

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "employee")
	private Set<Allowance> allowance = new HashSet<Allowance>();

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "employee")
	private Set<Vacation> vacation = new HashSet<Vacation>();

	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinTable(name = "EMPLOYEE_ORGANIZATION", joinColumns = { @JoinColumn(name = "EMPLOYEE_ID") }, inverseJoinColumns = { @JoinColumn(name = "ORGANIZATION_ID") })
	private Set<Organization> mapEmpOrg = new HashSet<Organization>();

	/* getter, setter */

	public String getPicturePath() {
		return picturePath;
	}

	public Set<Training> getTraining() {
		return training;
	}

	public void setTraining(Set<Training> training) {
		this.training = training;
	}

	public Boolean getSystemUser() {
		return systemUser;
	}

	public void setSystemUser(Boolean systemUser) {
		this.systemUser = systemUser;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public String getOccupationOther() {
		return occupationOther;
	}

	public void setOccupationOther(String occupationOther) {
		this.occupationOther = occupationOther;
	}

	public Set<Educations> getEducations() {
		return educations;
	}

	public void setEducations(Set<Educations> educations) {
		this.educations = educations;
	}

	public Set<Honour> getHonour() {
		return honour;
	}

	public void setHonour(Set<Honour> honour) {
		this.honour = honour;
	}

	public Set<Relatives> getRelative() {
		return relative;
	}

	public void setRelative(Set<Relatives> relative) {
		this.relative = relative;
	}

	public String geteCallPersonEmail() {
		return eCallPersonEmail;
	}

	public void seteCallPersonEmail(String eCallPersonEmail) {
		this.eCallPersonEmail = eCallPersonEmail;
	}

	public EmployeeStatus getEmployeeStatus() {
		return employeeStatus;
	}

	public void setEmployeeStatus(EmployeeStatus employeeStatus) {
		this.employeeStatus = employeeStatus;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public EmployeeLevel getEmployeeLevel() {
		return employeeLevel;
	}

	public void setEmployeeLevel(EmployeeLevel employeeLevel) {
		this.employeeLevel = employeeLevel;
	}

	public Integer getLimitOfSplit() {
		return limitOfSplit;
	}

	public void setLimitOfSplit(Integer limitOfSplit) {
		this.limitOfSplit = limitOfSplit;
	}

	public String geteCallPersonAddress() {
		return eCallPersonAddress;
	}

	public void seteCallPersonAddress(String eCallPersonAddress) {
		this.eCallPersonAddress = eCallPersonAddress;
	}

	public String geteCallPersonPhoneNo() {
		return eCallPersonPhoneNo;
	}

	public void seteCallPersonPhoneNo(String eCallPersonPhoneNo) {
		this.eCallPersonPhoneNo = eCallPersonPhoneNo;
	}

	public String geteCallPersonMobileNo() {
		return eCallPersonMobileNo;
	}

	public void seteCallPersonMobileNo(String eCallPersonMobileNo) {
		this.eCallPersonMobileNo = eCallPersonMobileNo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getWorkPhoneNo() {
		return workPhoneNo;
	}

	public void setWorkPhoneNo(String workPhoneNo) {
		this.workPhoneNo = workPhoneNo;
	}

	public String getWorkFax() {
		return workFax;
	}

	public void setWorkFax(String workFax) {
		this.workFax = workFax;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getPostAddress() {
		return postAddress;
	}

	public void setPostAddress(String postAddress) {
		this.postAddress = postAddress;
	}

	public String getPostIndex() {
		return postIndex;
	}

	public void setPostIndex(String postIndex) {
		this.postIndex = postIndex;
	}

	public String geteCallPersonName() {
		return eCallPersonName;
	}

	public void seteCallPersonName(String eCallPersonName) {
		this.eCallPersonName = eCallPersonName;
	}

	public RelativeType geteCallPersonRelativeType() {
		return eCallPersonRelativeType;
	}

	public void seteCallPersonRelativeType(RelativeType eCallPersonRelativeType) {
		this.eCallPersonRelativeType = eCallPersonRelativeType;
	}

	public Country getAddCountry() {
		return addCountry;
	}

	public void setAddCountry(Country addCountry) {
		this.addCountry = addCountry;
	}

	public DistrictSum getAddDistrictSum() {
		return addDistrictSum;
	}

	public void setAddDistrictSum(DistrictSum addDistrictSum) {
		this.addDistrictSum = addDistrictSum;
	}

	public String getAddKhorooBag() {
		return addKhorooBag;
	}

	public void setAddKhorooBag(String addKhorooBag) {
		this.addKhorooBag = addKhorooBag;
	}

	public String getAddStreet() {
		return addStreet;
	}

	public void setAddStreet(String addStreet) {
		this.addStreet = addStreet;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Ascription getAscription() {
		return ascription;
	}

	public void setAscription(Ascription ascription) {
		this.ascription = ascription;
	}

	public Origin getOrigin() {
		return origin;
	}

	public void setOrigin(Origin origin) {
		this.origin = origin;
	}

	public String getOldReg() {
		return oldReg;
	}

	public void setOldReg(String oldReg) {
		this.oldReg = oldReg;
	}

	public City getBirthCityProvince() {
		return birthCityProvince;
	}

	public void setBirthCityProvince(City birthCityProvince) {
		this.birthCityProvince = birthCityProvince;
	}

	public DistrictSum getBirthDistrictSum() {
		return birthDistrictSum;
	}

	public void setBirthDistrictSum(DistrictSum birthDistrictSum) {
		this.birthDistrictSum = birthDistrictSum;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public AcademicRank getMilitaryRank() {
		return militaryRank;
	}

	public void setMilitaryRank(AcademicRank militaryRank) {
		this.militaryRank = militaryRank;
	}

	public Set<Demerit> getDemerit() {
		return demerit;
	}

	public void setDemerit(Set<Demerit> demerit) {
		this.demerit = demerit;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthdate) {
		this.birthDate = birthdate;
	}

	public Occupation getOccupation() {
		return occupation;
	}

	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstname) {
		this.firstName = firstname;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyname) {
		this.familyName = familyname;
	}

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerno) {
		this.registerNo = registerno;
	}

	public String getSocialInsuranceNumber() {
		return socialInsuranceNumber;
	}

	public void setSocialInsuranceNumber(String socialInsuranceNumber) {
		this.socialInsuranceNumber = socialInsuranceNumber;
	}

	public CityProvince getBirthCity() {
		return birthCity;
	}

	public void setBirthCity(CityProvince birthCity) {
		this.birthCity = birthCity;
	}

	public String getHealthInsuranceNumber() {
		return healthInsuranceNumber;
	}

	public void setHealthInsuranceNumber(String healthInsuranceNumber) {
		this.healthInsuranceNumber = healthInsuranceNumber;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	public String getMilitaryNumber() {
		return militaryNumber;
	}

	public void setMilitaryNumber(String militaryNumber) {
		this.militaryNumber = militaryNumber;
	}

	public String getCourtNumber() {
		return courtNumber;
	}

	public void setCourtNumber(String courtNumber) {
		this.courtNumber = courtNumber;
	}

	public String getBusadSkillName1() {
		return busadSkillName1;
	}

	public void setBusadSkillName1(String busadSkillName1) {
		this.busadSkillName1 = busadSkillName1;
	}

	public String getBusadSkillName2() {
		return busadSkillName2;
	}

	public void setBusadSkillName2(String busadSkillName2) {
		this.busadSkillName2 = busadSkillName2;
	}

	public String getBusadSkillName3() {
		return busadSkillName3;
	}

	public void setBusadSkillName3(String busadSkillName3) {
		this.busadSkillName3 = busadSkillName3;
	}

	public Integer getBusadSkillOnoo1() {
		return busadSkillOnoo1;
	}

	public void setBusadSkillOnoo1(Integer busadSkillOnoo1) {
		this.busadSkillOnoo1 = busadSkillOnoo1;
	}

	public Integer getBusadSkillOnoo2() {
		return busadSkillOnoo2;
	}

	public void setBusadSkillOnoo2(Integer busadSkillOnoo2) {
		this.busadSkillOnoo2 = busadSkillOnoo2;
	}

	public Integer getBusadSkillOnoo3() {
		return busadSkillOnoo3;
	}

	public void setBusadSkillOnoo3(Integer busadSkillOnoo3) {
		this.busadSkillOnoo3 = busadSkillOnoo3;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Set<Degrees> getDegrees() {
		return degrees;
	}

	public void setDegrees(Set<Degrees> degrees) {
		this.degrees = degrees;
	}

	public Set<Skills> getSkills() {
		return skills;
	}

	public void setSkills(Set<Skills> skills) {
		this.skills = skills;
	}

	public City getAddCity() {
		return addCity;
	}

	public void setAddCity(City addCity) {
		this.addCity = addCity;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Set<Language> getLanguage() {
		return language;
	}

	public void setLanguage(Set<Language> language) {
		this.language = language;
	}

	public Set<User> getUser() {
		return user;
	}

	public void setUser(Set<User> user) {
		this.user = user;
	}

	public Set<SalaryHistory> getSalary() {
		return salary;
	}

	public void setSalary(Set<SalaryHistory> salary) {
		this.salary = salary;
	}

	public Set<DegreeGrade> getDegreeGrade() {
		return degreeGrade;
	}

	public void setDegreeGrade(Set<DegreeGrade> degreeGrade) {
		this.degreeGrade = degreeGrade;
	}

	public String getFullNameFirstChar() {
		String ret = "";

		if (lastname != null) {
			if (lastname.length() > this.limitOfSplit && lastname.length() != 0) {
				ret = lastname.substring(0, this.limitOfSplit);
			} else {
				ret = lastname;
			}
		} else
			return firstName;
		return ret + "." + firstName;
	}

	public YesNo getExamPlaceHolder() {
		return examPlaceHolder;
	}

	public void setExamPlaceHolder(YesNo examPlaceHolder) {
		this.examPlaceHolder = examPlaceHolder;
	}

	public YesNo getSwear() {
		return swear;
	}

	public void setSwear(YesNo swear) {
		this.swear = swear;
	}

	public YesNo getDeclaration() {
		return declaration;
	}

	public void setDeclaration(YesNo declaration) {
		this.declaration = declaration;
	}

	public void setSwearDate(Date swearDate) {
		this.swearDate = swearDate;
	}

	public Date getSwearDate() {
		return swearDate;
	}

	public Date getExamPlaceholderDate() {
		return examPlaceholderDate;
	}

	public void setExamPlaceholderDate(Date examPlaceholderDate) {
		this.examPlaceholderDate = examPlaceholderDate;
	}

	public void setEmployeeCurrentStatus(
			EmployeeCurrentStatus employeeCurrentStatus) {
		this.employeeCurrentStatus = employeeCurrentStatus;
	}

	public EmployeeCurrentStatus getEmployeeCurrentStatus() {
		return employeeCurrentStatus;
	}

	public Set<Displacement> getDisplacement() {
		return displacement;
	}

	public void setDisplacement(Set<Displacement> displacement) {
		this.displacement = displacement;
	}

	public Set<QuitJob> getQuitJob() {
		return quitJob;
	}

	public void setQuitJob(Set<QuitJob> quitJob) {
		this.quitJob = quitJob;
	}

	public Set<Passport> getPassport() {
		return passport;
	}

	public void setPassport(Set<Passport> passport) {
		this.passport = passport;
	}

	public Set<ResultContract> getResultContract() {
		return resultContract;
	}

	public void setResultContract(Set<ResultContract> resultContract) {
		this.resultContract = resultContract;
	}

	public Employee() {
		uuid = UUIDUtil.getUUID();
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;

		if (o == null)
			return false;

		if (!(o instanceof Employee)) {
			return false;
		}

		final Employee e = (Employee) o;

		if (e.getId() == null)
			return false;

		/* May 31, 2011 У.Наранхүү Start */
		if (this.getId() == null)
			return false;
		/* May 31, 2011 У.Наранхүү End */

		return getId().equals(e.getId());
	}

	public int hashCode() {
		return getUuid().hashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("uuid", this.uuid).toString();
	}

	public int compareTo(Employee employee) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setComputer(Set<Computer> computer) {
		this.computer = computer;
	}

	public Set<Computer> getComputer() {
		return computer;
	}

	public void setExperience(Set<Experience> experience) {
		this.experience = experience;
	}

	public Set<Experience> getExperience() {
		return experience;
	}

	public void setTravel(Set<TravelAbroad> travel) {
		this.travel = travel;
	}

	public Set<TravelAbroad> getTravel() {
		return travel;
	}

	public Set<DegreeGradeRank> getDegreeGradeRank() {
		return degreeGradeRank;
	}

	public void setDegreeGradeRank(Set<DegreeGradeRank> degreeGradeRank) {
		this.degreeGradeRank = degreeGradeRank;
	}

	public Boolean getIsMember() {
		return isMember;
	}

	public void setIsMember(Boolean isMember) {
		this.isMember = isMember;
	}

	public Boolean getMcseAdmin() {
		return mcseAdmin;
	}

	public void setMcseAdmin(Boolean mcseAdmin) {
		this.mcseAdmin = mcseAdmin;
	}

	public FamilyStatus getFamilyStatus() {
		return familyStatus;
	}

	public void setFamilyStatus(FamilyStatus familyStatus) {
		this.familyStatus = familyStatus;
	}

	public Integer getEmpNumber() {
		return empNumber;
	}

	public void setEmpNumber(Integer empNumber) {
		this.empNumber = empNumber;
	}

	public Set<Sahilga> getSahilga() {
		return sahilga;
	}

	public void setSahilga(Set<Sahilga> sahilga) {
		this.sahilga = sahilga;
	}

	public Set<EmployeeMilitary> getEmployeeMilitary() {
		return employeeMilitary;
	}

	public void setEmployeeMilitary(Set<EmployeeMilitary> employeeMilitary) {
		this.employeeMilitary = employeeMilitary;
	}

	public Set<Allowance> getAllowance() {
		return allowance;
	}

	public void setAllowance(Set<Allowance> allowance) {
		this.allowance = allowance;
	}

	public Set<Vacation> getVacation() {
		return vacation;
	}

	public void setVacation(Set<Vacation> vacation) {
		this.vacation = vacation;
	}

	public Country getBirthCountry() {
		return birthCountry;
	}

	public void setBirthCountry(Country birthCountry) {
		this.birthCountry = birthCountry;
	}

	public Set<Organization> getMapEmpOrg() {
		return mapEmpOrg;
	}

	public void setMapEmpOrg(Set<Organization> mapEmpOrg) {
		this.mapEmpOrg = mapEmpOrg;
	}

}
