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
import mn.mcs.electronics.court.entities.employee.Educations;
import mn.mcs.electronics.court.enums.UniversityType;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="school")
public class School extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8343443089057658760L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	
	@Column(name="name")
	private String name;

	@ManyToOne
	@JoinColumn(name="country")
	private Country country;

	@ManyToOne
	@JoinColumn(name="district_sum")
	private DistrictSum districtSum;
		
	@Column(name="universityType")
	private UniversityType universityType;
	
	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "university")
	private Set<Educations> educations = new HashSet<Educations>();
	
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

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public DistrictSum getDistrictSum() {
		return districtSum;
	}

	public void setDistrictSum(DistrictSum districtSum) {
		this.districtSum = districtSum;
	}

	public Set<Educations> getEducations() {
		return educations;
	}

	public void setEducations(Set<Educations> educations) {
		this.educations = educations;
	}

	public void setUniversityType(UniversityType universityType) {
		this.universityType = universityType;
	}

	public UniversityType getUniversityType() {
		return universityType;
	}
	
	public School(){
		uuid = UUIDUtil.getUUID();
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof School)) {
			return false;
		}
		
		final School e = (School) o;

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
