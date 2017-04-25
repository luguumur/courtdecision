package mn.mcs.electronics.court.entities.employee;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.entities.configuration.Country;
import mn.mcs.electronics.court.entities.configuration.TravelType;
import mn.mcs.electronics.court.util.UUIDUtil;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="travel_abroad")
public class TravelAbroad extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7957257652765770494L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	
	@ManyToOne
	@JoinColumn(name="country")
	private Country country;
	
	@ManyToOne
	@JoinColumn(name="travel_type")
	private TravelType travelType;
	
	@Lob
	@Column(name="task")
	private String jobTask;
	
	@Column(name ="went_date")
	private Date wentDate;
	
	@Column(name="came_date")
	private Date cameDate;
	
	@ManyToOne
	@JoinColumn(name="employee")
	private Employee employee;
	
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

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getJobTask() {
		return jobTask;
	}

	public void setJobTask(String jobTask) {
		this.jobTask = jobTask;
	}

	public Date getWentDate() {
		return wentDate;
	}

	public void setWentDate(Date wentDate) {
		this.wentDate = wentDate;
	}

	public Date getCameDate() {
		return cameDate;
	}

	public void setCameDate(Date cameDate) {
		this.cameDate = cameDate;
	}

	public TravelType getTravelType() {
		return travelType;
	}

	public void setTravelType(TravelType travelType) {
		this.travelType = travelType;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	public TravelAbroad(){
		uuid = UUIDUtil.getUUID();
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof TravelAbroad)) {
			return false;
		}
		
		final TravelAbroad e = (TravelAbroad) o;

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
