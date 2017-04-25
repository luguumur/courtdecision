package mn.mcs.electronics.court.entities.employee;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.entities.configuration.Facility;
import mn.mcs.electronics.court.enums.LanguageLevel;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="emp_office_equipment")
public class OfficeEquipment extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7069890013825290236L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	

	@ManyToOne
	@JoinColumn(name="program")
	private Facility facility;
		
	@Column(name="programlevel")
	private  LanguageLevel facilityLevel;
		
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

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	public LanguageLevel getFacilityLevel() {
		return facilityLevel;
	}

	public void setFacilityLevel(LanguageLevel facilityLevel) {
		this.facilityLevel = facilityLevel;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Employee getEmployee() {
		return employee;
	}


	public OfficeEquipment(){
		uuid = UUIDUtil.getUUID();
	}
	
	
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof OfficeEquipment)) {
			return false;
		}
		
		final OfficeEquipment e = (OfficeEquipment) o;

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
