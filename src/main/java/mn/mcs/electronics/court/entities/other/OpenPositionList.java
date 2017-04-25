package mn.mcs.electronics.court.entities.other;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.entities.configuration.Appointment;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="open_position_list")
public class OpenPositionList extends BaseObject {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6215955718920563881L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	
	@Column(name = "op_number")
	private Integer opNumber;
	
	@Column(name = "establishment")
	private Integer establishment;
	
	@ManyToOne
	@JoinColumn(name="organization")
	private Organization organization;
	
	@ManyToOne
	@JoinColumn(name="appointment")
	private Appointment appointment;
	
	/* getter, setter */

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

	public Integer getOpNumber() {
		return opNumber;
	}

	public void setOpNumber(Integer opNumber) {
		this.opNumber = opNumber;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public Integer getEstablishment() {
		return establishment;
	}

	public void setEstablishment(Integer establishment) {
		this.establishment = establishment;
	}

	public int hashCode() {
		return getUuid().hashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("uuid",
				this.uuid).toString();
	}

	public OpenPositionList(){
		uuid = UUIDUtil.getUUID();
	}

	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof OpenPositionList)) {
			return false;
		}
		
		final OpenPositionList e = (OpenPositionList) o;

		if (e.getId() == null)
			return false;
		
		/* May 31, 2011 У.Наранхүү Start */
		if (this.getId() == null)
			return false;
		/* May 31, 2011 У.Наранхүү End */

		return getId().equals(e.getId());
	}

}
