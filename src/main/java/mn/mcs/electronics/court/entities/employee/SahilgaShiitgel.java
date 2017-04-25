/****************** Shiidver guitsetgeh *************************/
package mn.mcs.electronics.court.entities.employee;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="sahilga_shiitgel")
public class SahilgaShiitgel extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 439491810876542837L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	
	@Lob
	@Column(name="shiitgel_name")
	private String shiitgelName;

	@Column(name="duration")
	private Integer duration;
	
	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "sahilgaShiitgel")
	private Set<Sahilga> sahilga= new HashSet<Sahilga>();
	
	public SahilgaShiitgel(){
		uuid = UUIDUtil.getUUID();
	}
	
	/*getter, setter*/
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
	
	public String getShiitgelName() {
		return shiitgelName;
	}

	public void setShiitgelName(String shiitgelName) {
		this.shiitgelName = shiitgelName;
	}

	public Set<Sahilga> getSahilga() {
		return sahilga;
	}

	public void setSahilga(Set<Sahilga> sahilga) {
		this.sahilga = sahilga;
	}
	
	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof SahilgaShiitgel)) {
			return false;
		}
		
		final SahilgaShiitgel e = (SahilgaShiitgel) o;

		if (e.getId() == null)
			return false;
		
		/* May 31, 2011 У.Наранхүү Start */
		if (this.getId() == null)
			return false;
		/* May 31, 2011 У.Наранхүү End */

		return getId().equals(e.getId());
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("uuid",
				this.uuid).toString();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}
}
