package mn.mcs.electronics.court.entities.organization;

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
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="appurtenance_lead")
public class AppurtenanceLead extends BaseObject {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2433719773899130422L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	
	@Column(name="name")
	private String name;

	@OneToMany(orphanRemoval = true, cascade={CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "appurtenanceLead")
	private Set<Organization> organization = new HashSet<Organization>();
	
	/*getter, setter*/
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Set<Organization> getOrganization() {
		return organization;
	}

	public void setOrganization(Set<Organization> organization) {
		this.organization = organization;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public AppurtenanceLead(){
		uuid = UUIDUtil.getUUID();
	}

	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof AppurtenanceLead)) {
			return false;
		}
		
		final AppurtenanceLead e = (AppurtenanceLead) o;

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
