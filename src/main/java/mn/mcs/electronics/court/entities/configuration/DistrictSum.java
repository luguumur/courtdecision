package mn.mcs.electronics.court.entities.configuration;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="district_sum")
public class DistrictSum extends BaseObject{
	
	private static final long serialVersionUID = 2213875400803151427L;


	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	
	@Column(name="district_sum_name")
	private String districtSumName;
	
	@ManyToOne
	@JoinColumn(name="city")
	private City city;
	
	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "districtSum")
	private Set<Organization> organization = new HashSet<Organization>();
	
	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "districtSum")
	private Set<KhorooBag> khorooBag = new HashSet<KhorooBag>();
	
	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "birthDistrictSum")
	private Set<Employee> employeeBirth = new HashSet<Employee>();
	
	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "addDistrictSum")
	private Set<Employee> employeeAdd = new HashSet<Employee>();

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "districtSum")
	private Set<School> school = new HashSet<School>();
	
	public DistrictSum(){
		uuid = UUIDUtil.getUUID();
	}
	
	
	/* getter, setter */

	public String getDistrictSumName() {
		return districtSumName;
	}

	public void setDistrictSumName(String districtSumName) {
		this.districtSumName = districtSumName;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Set<Organization> getOrganization() {
		return organization;
	}

	public void setOrganization(Set<Organization> organization) {
		this.organization = organization;
	}
	
	public Set<KhorooBag> getKhorooBag() {
		return khorooBag;
	}

	public void setKhorooBag(Set<KhorooBag> khorooBag) {
		this.khorooBag = khorooBag;
	}

	public Set<School> getSchool() {
		return school;
	}

	public void setSchool(Set<School> school) {
		this.school = school;
	}

	public Set<Employee> getEmployeeBirth() {
		return employeeBirth;
	}

	public void setEmployeeBirth(Set<Employee> employeeBirth) {
		this.employeeBirth = employeeBirth;
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

	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof DistrictSum)) {
			return false;
		}
		
		final DistrictSum e = (DistrictSum) o;

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
