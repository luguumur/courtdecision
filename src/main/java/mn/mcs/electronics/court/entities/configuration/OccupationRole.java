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
import mn.mcs.electronics.court.entities.employee.Displacement;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="occupationrole")
public class OccupationRole extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4764197452258194707L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	
	@Column(name="name")
	private String name;
	
	@Column(name = "is_udirdah")
	private Boolean isUdirdah;
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "occupationRole")
	@org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Set<Displacement> currentOccupation = new HashSet<Displacement>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Boolean getIsUdirdah() {
		return isUdirdah;
	}

	public void setIsUdirdah(Boolean isUdirdah) {
		this.isUdirdah = isUdirdah;
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
	
	public void setCurrentOccupation(Set<Displacement> currentOccupation) {
		this.currentOccupation = currentOccupation;
	}

	public Set<Displacement> getCurrentOccupation() {
		return currentOccupation;
	}

	public OccupationRole(){
		uuid = UUIDUtil.getUUID();
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof OccupationRole)) {
			return false;
		}
		
		final OccupationRole e = (OccupationRole) o;

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
