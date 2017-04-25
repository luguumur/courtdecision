package mn.mcs.electronics.court.entities.organization;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.entities.configuration.ApprovedPositions;
import mn.mcs.electronics.court.entities.configuration.City;
import mn.mcs.electronics.court.entities.configuration.Country;
import mn.mcs.electronics.court.entities.configuration.DistrictSum;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.enums.OrganizationGroup;
import mn.mcs.electronics.court.util.UUIDUtil;

@Entity
@Table(name = "organization")
public class Organization extends BaseObject {

	private static final long serialVersionUID = -6281703741751976377L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;

	@Column(name = "name")
	private String name;

	@Column(name = "short_name")
	private String shortName;

	@Column(name = "register")
	private String register;

	@Column(name = "phone_no")
	private String phoneNo;

	@Column(name = "fax")
	private String fax;

	@Column(name = "zip")
	private String zip;

	@Column(name = "email")
	private String email;

	@Column(name = "webpage")
	private String webpage;

	@Column(name = "postaddress")
	private String postAddress;

	@Lob
	@Column(name = "brief_introduction")
	private String briefIntroduction;

	@Column(name = "address")
	private String address;

	@ManyToOne
	@JoinColumn(name = "country")
	private Country country;

	@ManyToOne
	@JoinColumn(name = "organization_type")
	private OrganizationType organizationType;

	@Column(name = "organization_group")
	private OrganizationGroup organizationGroup;

	@ManyToOne
	@JoinColumn(name = "financing_type")
	private FinancingType financingType;

	@ManyToOne
	@JoinColumn(name = "appurtenance_lead")
	private AppurtenanceLead appurtenanceLead;

	@ManyToOne
	@JoinColumn(name = "appurtenance_location")
	private AppurtenanceLocation appurtenanceLocation;

	@ManyToOne
	@JoinColumn(name = "city_province")
	private City cityProvince;

	@ManyToOne
	@JoinColumn(name = "district_sum")
	private DistrictSum districtSum;

	@Column(name = "khoroo_bag")
	private String khorooBag;

	@Column(name = "street")
	private String street;

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "organization")
	private Set<Department> department = new HashSet<Department>();

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "organization")
	private Set<SubDepartment> subDepartment = new HashSet<SubDepartment>();

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "organization")
	private Set<ApprovedPositions> approvedPositions = new HashSet<ApprovedPositions>();

	@ManyToMany(mappedBy = "mapEmpOrg")
	private Set<Employee> employees = new HashSet<Employee>();

	public Set<Department> getDepartment() {
		return department;
	}

	public void setDepartment(Set<Department> department) {
		this.department = department;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getRegister() {
		return register;
	}

	public void setRegister(String register) {
		this.register = register;
	}

	public OrganizationGroup getOrganizationGroup() {
		return organizationGroup;
	}

	public void setOrganizationGroup(OrganizationGroup organizationGroup) {
		this.organizationGroup = organizationGroup;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getWebpage() {
		return webpage;
	}

	public void setWebpage(String webpage) {
		this.webpage = webpage;
	}

	public String getPostAddress() {
		return postAddress;
	}

	public void setPostAddress(String postAddress) {
		this.postAddress = postAddress;
	}

	public String getBriefIntroduction() {
		return briefIntroduction;
	}

	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public City getCityProvince() {
		return cityProvince;
	}

	public void setCityProvince(City cityProvince) {
		this.cityProvince = cityProvince;
	}

	public OrganizationType getOrganizationType() {
		return organizationType;
	}

	public void setOrganizationType(OrganizationType organizationType) {
		this.organizationType = organizationType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public DistrictSum getDistrictSum() {
		return districtSum;
	}

	public void setDistrictSum(DistrictSum districtSum) {
		this.districtSum = districtSum;
	}

	public String getKhorooBag() {
		return khorooBag;
	}

	public void setKhorooBag(String khorooBag) {
		this.khorooBag = khorooBag;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public FinancingType getFinancingType() {
		return financingType;
	}

	public void setFinancingType(FinancingType financingType) {
		this.financingType = financingType;
	}

	public AppurtenanceLead getAppurtenanceLead() {
		return appurtenanceLead;
	}

	public void setAppurtenanceLead(AppurtenanceLead appurtenanceLead) {
		this.appurtenanceLead = appurtenanceLead;
	}

	public AppurtenanceLocation getAppurtenanceLocation() {
		return appurtenanceLocation;
	}

	public void setAppurtenanceLocation(
			AppurtenanceLocation appurtenanceLocation) {
		this.appurtenanceLocation = appurtenanceLocation;
	}

	public Set<ApprovedPositions> getApprovedPositions() {
		return approvedPositions;
	}

	public void setApprovedPositions(Set<ApprovedPositions> approvedPositions) {
		this.approvedPositions = approvedPositions;
	}

	public Set<SubDepartment> getSubDepartment() {
		return subDepartment;
	}

	public void setSubDepartment(Set<SubDepartment> subDepartment) {
		this.subDepartment = subDepartment;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	public Organization() {
		uuid = UUIDUtil.getUUID();
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;

		if (o == null)
			return false;

		if (!(o instanceof Organization)) {
			return false;
		}

		final Organization e = (Organization) o;

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
		return getId() + "";
	}
}
