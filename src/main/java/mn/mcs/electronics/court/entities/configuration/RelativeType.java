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

import org.apache.commons.lang.builder.ToStringBuilder;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.Relatives;
import mn.mcs.electronics.court.util.UUIDUtil;

@Entity
@Table(name="relative_type")
public class RelativeType extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6510310827758638702L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;

	@Column(name="name")
	private String name;
	
	@OneToMany(mappedBy = "eCallPersonRelativeType", orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<Employee> employee = new HashSet<Employee>();

	@OneToMany(mappedBy = "relativeType", orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private Set<Relatives> relative = new HashSet<Relatives>();
	
	public Set<Relatives> getRelative() {
		return relative;
	}

	public void setRelative(Set<Relatives> relative) {
		this.relative = relative;
	}

	public Set<Employee> getEmployee() {
		return employee;
	}

	public void setEmployee(Set<Employee> employee) {
		this.employee = employee;
	}
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public RelativeType(){
		uuid = UUIDUtil.getUUID();
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof RelativeType)) {
			return false;
		}
		
		final RelativeType e = (RelativeType) o;

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
