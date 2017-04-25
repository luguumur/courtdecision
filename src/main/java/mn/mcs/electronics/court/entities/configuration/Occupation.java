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
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.enums.SchoolType;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="occupation")
public class Occupation extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1865278053343404299L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	
	@Column(name="name")
	private String name;
	
	@Column(name="school_type")
	private SchoolType schoolType;
	
	@ManyToOne
	@JoinColumn(name="occupation_level")
	private OccupationLevel occupationLevel;

	@ManyToOne
	@JoinColumn(name="occupation_type")
	private OccupationType occupationType;
	
	@OneToMany(orphanRemoval=true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "occupation")
	private Set<Employee> employee = new HashSet<Employee>();
	
	@OneToMany(orphanRemoval=true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "occupation")
	private Set<Educations> educations = new HashSet<Educations>();
	
	public OccupationType getOccupationType() {
		return occupationType;
	}

	public void setOccupationType(OccupationType occupationType) {
		this.occupationType = occupationType;
	}

	public OccupationLevel getOccupationLevel() {
		return occupationLevel;
	}

	public void setOccupationLevel(OccupationLevel occupationLevel) {
		this.occupationLevel = occupationLevel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public SchoolType getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(SchoolType schoolType) {
		this.schoolType = schoolType;
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

	public Occupation(){
		uuid = UUIDUtil.getUUID();
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof Occupation)) {
			return false;
		}
		
		final Occupation e = (Occupation) o;

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
