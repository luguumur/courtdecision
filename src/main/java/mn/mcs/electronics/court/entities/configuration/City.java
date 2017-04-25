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
import mn.mcs.electronics.court.entities.employee.Educations;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="city")
public class City extends BaseObject{


	/**
	 * 
	 */
	private static final long serialVersionUID = -2354676180316881092L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	
	@Column(name="city_name")
	private String cityName;

	@OneToMany(orphanRemoval=true,cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "addCity")
	private Set<Employee> employeeAdd = new HashSet<Employee>();
	
	@OneToMany(orphanRemoval=true,cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "birthCityProvince")
	private Set<Employee> employeeBirth = new HashSet<Employee>();
	
	@OneToMany(orphanRemoval=true,cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "cityProvince")
	private Set<Organization> organization = new HashSet<Organization>();
		
	@OneToMany(orphanRemoval=true,cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "city")
	private Set<DistrictSum> districtSum = new HashSet<DistrictSum>();
	
	@OneToMany(orphanRemoval=true,cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "city")
	private Set<Educations> education = new HashSet<Educations>();

	/* getter, setter */
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<DistrictSum> getDistrictSum() {
		return districtSum;
	}

	public void setDistrictSum(Set<DistrictSum> districtSum) {
		this.districtSum = districtSum;
	}

	public Set<Organization> getOrganization() {
		return organization;
	}

	public void setOrganization(Set<Organization> organization) {
		this.organization = organization;
	}

	public Set<Employee> getEmployeeBirth() {
		return employeeBirth;
	}

	public void setEmployeeBirth(Set<Employee> employeeBirth) {
		this.employeeBirth = employeeBirth;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Set<Educations> getEducation() {
		return education;
	}

	public void setEducation(Set<Educations> education) {
		this.education = education;
	}

	public Set<Employee> getEmployeeAdd() {
		return employeeAdd;
	}

	public void setEmployeeAdd(Set<Employee> employeeAdd) {
		this.employeeAdd = employeeAdd;
	}

	public City(){
		uuid = UUIDUtil.getUUID();
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof City)) {
			return false;
		}
		
		final City e = (City) o;

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
