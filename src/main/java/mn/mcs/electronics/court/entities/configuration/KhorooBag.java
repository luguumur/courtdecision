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
@Table(name="khoroo_bag")
public class KhorooBag extends BaseObject{
	
	private static final long serialVersionUID = 2213875400803151427L;


	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	
	@Column(name="khoroo_bag_name")
	private String khorooBagName;
	
	@ManyToOne
	@JoinColumn(name="district_sum")
	private DistrictSum districtSum;
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "khorooBag")
	@org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Set<Street> street = new HashSet<Street>();

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "addKhorooBag")
	@org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Set<Employee> employeeAdd = new HashSet<Employee>();
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "khorooBag")
	@org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Set<Organization> organization = new HashSet<Organization>();
		
	public Set<Organization> getOrganization() {
		return organization;
	}

	public void setOrganization(Set<Organization> organization) {
		this.organization = organization;
	}

	public Set<Street> getStreet() {
		return street;
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
	
	public String getKhorooBagName() {
		return khorooBagName;
	}

	public void setKhorooBagName(String khorooBagName) {
		this.khorooBagName = khorooBagName;
	}

	public DistrictSum getDistrictSum() {
		return districtSum;
	}

	public void setDistrictSum(DistrictSum districtSum) {
		this.districtSum = districtSum;
	}

	public void setStreet(Set<Street> street) {
		this.street = street;
	}

	public KhorooBag(){
		uuid = UUIDUtil.getUUID();
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof KhorooBag)) {
			return false;
		}
		
		final KhorooBag e = (KhorooBag) o;

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
