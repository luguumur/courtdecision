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
import mn.mcs.electronics.court.enums.AppointmentType;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="appointment")
public class Appointment extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3276693123218294319L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;

	@Column(name="appointment_name")
	private String appointmentName;

	/*@Column(name="salary")
	private Double salary;*/
	
	@Column(name="appointment_type")
	private AppointmentType appointmentType;
	
	/*@Column(name="date")
	private Date date;
	
	@Column(name="active")
	private Boolean active;*/
	
	@ManyToOne
	@JoinColumn(name = "occupation_type")
	private OccupationType occupationType;
	
	@Column(name="ga_nemegdel")
	private Boolean gaNemegdel;
	
	@OneToMany(orphanRemoval=true,cascade={CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "appointment")
	private Set<ApprovedPositions> approvedPositions = new HashSet<ApprovedPositions>();
	
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
	
	public String getAppointmentName() {
		return appointmentName;
	}

	public void setAppointmentName(String appointmentName) {
		this.appointmentName = appointmentName;
	}

	/*public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}*/

	public AppointmentType getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(AppointmentType appointmentType) {
		this.appointmentType = appointmentType;
	}
	
	public OccupationType getOccupationType() {
		return occupationType;
	}

	public void setOccupationType(OccupationType occupationType) {
		this.occupationType = occupationType;
	}	

	public Boolean getGaNemegdel() {
		return gaNemegdel;
	}

	public void setGaNemegdel(Boolean gaNemegdel) {
		this.gaNemegdel = gaNemegdel;
	}

	public Set<ApprovedPositions> getApprovedPositions() {
		return approvedPositions;
	}

	public void setApprovedPositions(Set<ApprovedPositions> approvedPositions) {
		this.approvedPositions = approvedPositions;
	}
	
	
	
	/*public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}*/

	public Appointment(){
		uuid = UUIDUtil.getUUID();
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof Appointment)) {
			return false;
		}
		
		final Appointment e = (Appointment) o;

		if (e.getId() == null)
			return false;
		
		if (this.getId() == null)
			return false;

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
