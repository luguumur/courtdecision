package mn.mcs.electronics.court.entities.employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.util.UUIDUtil;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="addition")
public class Addition extends BaseObject {


	
	private static final long serialVersionUID = -5373130093823590189L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	
		
	@Column(name="travel_abroad")
	private  String travelabroad;

	@Column(name="apartment")
	private  String apartment;
	
	@Column(name = "request_displacelment")
	private Boolean requestDisplacelment;
	
	@Column(name="passport_no")
	private  String passportNo;
	
	
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

	public String getTravelabroad() {
		return travelabroad;
	}

	public void setTravelabroad(String travelabroad) {
		this.travelabroad = travelabroad;
	}

	public String getApartment() {
		return apartment;
	}

	public void setApartment(String apartment) {
		this.apartment = apartment;
	}
	
	
	public Boolean getRequestDisplacelment() {
		return requestDisplacelment;
	}

	public void setRequestDisplacelment(Boolean requestDisplacelment) {
		this.requestDisplacelment = requestDisplacelment;
	}
	
	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
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
	
	public Addition(){
		uuid = UUIDUtil.getUUID();
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof Addition)) {
			return false;
		}
		
		final Addition e = (Addition) o;

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
