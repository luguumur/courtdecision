package mn.mcs.electronics.court.entities.configuration;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.entities.employee.Degrees;
import mn.mcs.electronics.court.entities.employee.Educations;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.Training;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="country")
public class Country extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1780385925417185617L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	
	@Column(name="country_name")
	private String countryName;
	
	@Column(name="language")
	private String language;
	
	@Column(name="is_mongolia")
	private Boolean isMongolia;
	
	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "country")
	private Set<Organization> organization = new HashSet<Organization>();
	
	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "country")
	private Set<CityProvince> cityProvince = new HashSet<CityProvince>();

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "addCountry")
	private Set<Employee> employeeAdd = new HashSet<Employee>();
		
	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "country")
	private Set<School> school = new HashSet<School>();
		
	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "country")
	private Set<Educations> education = new HashSet<Educations>();
	
	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "issuedCountry")
	private Set<Degrees> degree = new HashSet<Degrees>();
	
	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "country")
	private Set<Training> training = new HashSet<Training>();
	
	public Set<School> getSchool() {
		return school;
	}

	public void setSchool(Set<School> school) {
		this.school = school;
	}

	public Set<Training> getTraining() {
		return training;
	}

	public void setTraining(Set<Training> training) {
		this.training = training;
	}

	public Set<Employee> getEmployeeAdd() {
		return employeeAdd;
	}

	public void setEmployeeAdd(Set<Employee> employeeAdd) {
		this.employeeAdd = employeeAdd;
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

	public Set<Degrees> getDegree() {
		return degree;
	}

	public void setDegree(Set<Degrees> degree) {
		this.degree = degree;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	public Set<Organization> getOrganization() {
		return organization;
	}

	public void setOrganization(Set<Organization> organization) {
		this.organization = organization;
	}
	
	public Set<CityProvince> getCityProvince() {
		return cityProvince;
	}

	public void setCityProvince(Set<CityProvince> cityProvince) {
		this.cityProvince = cityProvince;
	}
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	public Set<Educations> getEducation() {
		return education;
	}

	public void setEducation(Set<Educations> education) {
		this.education = education;
	}

	public Boolean getIsMongolia() {
		return isMongolia;
	}

	public void setIsMongolia(Boolean isMongolia) {
		this.isMongolia = isMongolia;
	}

	public Country(){
		uuid = UUIDUtil.getUUID();
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof Country)) {
			return false;
		}
		
		final Country e = (Country) o;

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
		return new ToStringBuilder(this).append("id", this.id).append("uuid",
				this.uuid).toString();
	}
	
}
