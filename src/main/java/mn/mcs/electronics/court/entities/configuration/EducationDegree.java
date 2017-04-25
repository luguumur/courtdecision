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
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="education_degree")
public class EducationDegree extends BaseObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1706874890080265133L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	
	@Column(name="name")
	private String name;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "degreeType")
	@org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
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

	public Set<Educations> getEducations() {
		return educations;
	}

	public void setEducations(Set<Educations> educations) {
		this.educations = educations;
	}

	public EducationDegree(){
		uuid = UUIDUtil.getUUID();
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof EducationDegree)) {
			return false;
		}
		
		final EducationDegree e = (EducationDegree) o;

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
