package mn.mcs.electronics.court.util.dto;

import java.io.Serializable;
import java.util.Date;

import mn.mcs.electronics.court.entities.configuration.AcademicRank;
import mn.mcs.electronics.court.entities.configuration.Appointment;
import mn.mcs.electronics.court.entities.configuration.Ascription;
import mn.mcs.electronics.court.entities.configuration.City;
import mn.mcs.electronics.court.entities.configuration.Country;
import mn.mcs.electronics.court.entities.configuration.DistrictSum;
import mn.mcs.electronics.court.entities.configuration.EmployeeLevel;
import mn.mcs.electronics.court.entities.configuration.Occupation;
import mn.mcs.electronics.court.entities.configuration.Origin;
import mn.mcs.electronics.court.entities.configuration.RelativeType;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.organization.Department;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.EmployeeCurrentStatus;
import mn.mcs.electronics.court.enums.EmployeeStatus;
import mn.mcs.electronics.court.enums.Gender;
import mn.mcs.electronics.court.enums.YesNo;

public class EmployeeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7285897923864658385L;

	public static final String PROPERTY_ID = "id";
	public static final String PROPERTY_FIRSTNAME = "firstName";
	public static final String PROPERTY_LASTNAME = "lastname";
	public static final String PROPERTY_REGISTER_NUMBER = "registerNo";
	public static final String PROPERTY_GENDER = "gender";
	public static final String PROPERTY_OCCUPATION = "occupation";
	public static final String PROPERTY_ORGANIZATION = "organization";
	public static final String PROPERTY_EMAIL = "eMail";
	public static final String PROPERTY_STATUS = "employeeStatus";
	public static final String PROPERTY_PHONE = "phoneNo";
	public static final String PROPERTY_MILITARY_DEGREE = "militaryRank";

	// TODO ...

	private Long id;

	private String uuid;

	private String registerNo;

	private String socialInsuranceNumber;

	private String healthInsuranceNumber;

	private String idCardNumber;

	private String militaryNumber;

	private String courtNumber;

	private String firstName;

	private String lastname;

	private String familyName;

	private Date birthDate;

	private Occupation occupation;

	private AcademicRank militaryRank;

	private Gender gender;

	private City birthCityProvince;

	private DistrictSum birthDistrictSum;

	private String birthPlace;

	private Origin origin;

	private Ascription ascription;

	private Country addCountry;

	private City addCity;

	private DistrictSum addDistrictSum;

	private String addKhorooBag;

	private String addStreet;

	private String address;

	private String phoneNo;

	private String mobileNo;

	private String workPhoneNo;

	private String workFax;

	private String eMail;

	private String postAddress;

	private String postIndex;

	private String eCallPersonName;

	private RelativeType eCallPersonRelativeType;

	private String eCallPersonAddress;

	private String eCallPersonPhoneNo;

	private String eCallPersonMobileNo;

	private String eCallPersonEmail;

	private EmployeeStatus employeeStatus;

	private Department department;

	private Organization organization;

	private Appointment appointment;

	private EmployeeLevel employeeLevel;

	private String picturePath;

	private Integer limitOfSplit = 1;

	private YesNo examPlaceHolder;

	private Date examPlaceholderDate;

	private YesNo swear;

	private Boolean isMember;

	private YesNo declaration;// Хөрөнгө орлогын мэдээлэл бүртгэх эсэх

	private Date swearDate;

	private EmployeeCurrentStatus employeeCurrentStatus;

	private Boolean systemUser;

	private String busadSkillName1;

	private Integer busadSkillOnoo1;

	private String busadSkillName2;

	private Integer busadSkillOnoo2;

	private String busadSkillName3;

	private Integer busadSkillOnoo3;

	private Boolean mcseAdmin;

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

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getSocialInsuranceNumber() {
		return socialInsuranceNumber;
	}

	public void setSocialInsuranceNumber(String socialInsuranceNumber) {
		this.socialInsuranceNumber = socialInsuranceNumber;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Occupation getOccupation() {
		return occupation;
	}

	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}

	public AcademicRank getMilitaryRank() {
		return militaryRank;
	}

	public void setMilitaryRank(AcademicRank militaryRank) {
		this.militaryRank = militaryRank;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
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

	public Origin getOrigin() {
		return origin;
	}

	public void setOrigin(Origin origin) {
		this.origin = origin;
	}

	public Ascription getAscription() {
		return ascription;
	}

	public void setAscription(Ascription ascription) {
		this.ascription = ascription;
	}

	public Country getAddCountry() {
		return addCountry;
	}

	public void setAddCountry(Country addCountry) {
		this.addCountry = addCountry;
	}

	public City getAddCity() {
		return addCity;
	}

	public void setAddCity(City addCity) {
		this.addCity = addCity;
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

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public EmployeeLevel getEmployeeLevel() {
		return employeeLevel;
	}

	public void setEmployeeLevel(EmployeeLevel employeeLevel) {
		this.employeeLevel = employeeLevel;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public Integer getLimitOfSplit() {
		return limitOfSplit;
	}

	public void setLimitOfSplit(Integer limitOfSplit) {
		this.limitOfSplit = limitOfSplit;
	}

	public YesNo getExamPlaceHolder() {
		return examPlaceHolder;
	}

	public void setExamPlaceHolder(YesNo examPlaceHolder) {
		this.examPlaceHolder = examPlaceHolder;
	}

	public Date getExamPlaceholderDate() {
		return examPlaceholderDate;
	}

	public void setExamPlaceholderDate(Date examPlaceholderDate) {
		this.examPlaceholderDate = examPlaceholderDate;
	}

	public YesNo getSwear() {
		return swear;
	}

	public void setSwear(YesNo swear) {
		this.swear = swear;
	}

	public Boolean getIsMember() {
		return isMember;
	}

	public void setIsMember(Boolean isMember) {
		this.isMember = isMember;
	}

	public YesNo getDeclaration() {
		return declaration;
	}

	public void setDeclaration(YesNo declaration) {
		this.declaration = declaration;
	}

	public Date getSwearDate() {
		return swearDate;
	}

	public void setSwearDate(Date swearDate) {
		this.swearDate = swearDate;
	}

	public EmployeeCurrentStatus getEmployeeCurrentStatus() {
		return employeeCurrentStatus;
	}

	public void setEmployeeCurrentStatus(
			EmployeeCurrentStatus employeeCurrentStatus) {
		this.employeeCurrentStatus = employeeCurrentStatus;
	}

	public Boolean getSystemUser() {
		return systemUser;
	}

	public void setSystemUser(Boolean systemUser) {
		this.systemUser = systemUser;
	}

	public String getBusadSkillName1() {
		return busadSkillName1;
	}

	public void setBusadSkillName1(String busadSkillName1) {
		this.busadSkillName1 = busadSkillName1;
	}

	public Integer getBusadSkillOnoo1() {
		return busadSkillOnoo1;
	}

	public void setBusadSkillOnoo1(Integer busadSkillOnoo1) {
		this.busadSkillOnoo1 = busadSkillOnoo1;
	}

	public String getBusadSkillName2() {
		return busadSkillName2;
	}

	public void setBusadSkillName2(String busadSkillName2) {
		this.busadSkillName2 = busadSkillName2;
	}

	public Integer getBusadSkillOnoo2() {
		return busadSkillOnoo2;
	}

	public void setBusadSkillOnoo2(Integer busadSkillOnoo2) {
		this.busadSkillOnoo2 = busadSkillOnoo2;
	}

	public String getBusadSkillName3() {
		return busadSkillName3;
	}

	public void setBusadSkillName3(String busadSkillName3) {
		this.busadSkillName3 = busadSkillName3;
	}

	public Integer getBusadSkillOnoo3() {
		return busadSkillOnoo3;
	}

	public void setBusadSkillOnoo3(Integer busadSkillOnoo3) {
		this.busadSkillOnoo3 = busadSkillOnoo3;
	}

	public Boolean getMcseAdmin() {
		return mcseAdmin;
	}

	public void setMcseAdmin(Boolean mcseAdmin) {
		this.mcseAdmin = mcseAdmin;
	}

	public static String getPropertyId() {
		return PROPERTY_ID;
	}

	public static String getPropertyFirstname() {
		return PROPERTY_FIRSTNAME;
	}

	public static String getPropertyLastname() {
		return PROPERTY_LASTNAME;
	}

	public static String getPropertyRegisterNumber() {
		return PROPERTY_REGISTER_NUMBER;
	}

	public static String getPropertyGender() {
		return PROPERTY_GENDER;
	}

	public boolean clonEmp(Employee emp) {
		try {
			id = emp.getId();
			firstName = emp.getFirstName();
			lastname = emp.getLastname();
			// TODO...

			return true;
		} catch (Exception e) {
			return false;
		}
	}
}