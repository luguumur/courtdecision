package mn.mcs.electronics.court.entities.employee;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.entities.configuration.City;
import mn.mcs.electronics.court.entities.configuration.DistrictSum;
import mn.mcs.electronics.court.entities.configuration.FamilyAppointmentType;
import mn.mcs.electronics.court.entities.configuration.RelativeType;
import mn.mcs.electronics.court.enums.RelativeFamily;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="relatives")
public class Relatives extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2495756500603000007L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	
	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lastName;
	
	@ManyToOne
	@JoinColumn(name="relation")
	private RelativeType relation;
	
	@Column(name="relative_type")
	private RelativeFamily relativeType;
	
	@Column(name="birth_date")
	private Integer birthDate;
	
	@ManyToOne
	@JoinColumn(name="birth_city_province")
	private City birthCityProvince;
	
	@ManyToOne
	@JoinColumn(name="birth_sum")
	private DistrictSum districtSum;
	
	@ManyToOne
	@JoinColumn(name="family_appointment_type")
	private FamilyAppointmentType appointmentType;

	@JoinColumn(name="occupation")
	private String occupation;
	
	@Column(name="mobile_phone")
	private String mobilePhone;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="mail")
	private String mail;

	@ManyToOne
	@JoinColumn(name="employee")
	private Employee employee;

	@Column(name="work_address")
	private Date workAddress;
	
	public Date getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(Date workAddress) {
		this.workAddress = workAddress;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Integer birthDate) {
		this.birthDate = birthDate;
	}

	public City getBirthCityProvince() {
		return birthCityProvince;
	}

	public void setBirthCityProvince(City birthCityProvince) {
		this.birthCityProvince = birthCityProvince;
	}

	public DistrictSum getDistrictSum() {
		return districtSum;
	}

	public void setDistrictSum(DistrictSum districtSum) {
		this.districtSum = districtSum;
	}

	public FamilyAppointmentType getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(FamilyAppointmentType appointmentType) {
		this.appointmentType = appointmentType;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public RelativeType getRelation() {
		return relation;
	}

	public void setRelation(RelativeType relation) {
		this.relation = relation;
	}

	public RelativeFamily getRelativeType() {
		return relativeType;
	}

	public void setRelativeType(RelativeFamily relativeType) {
		this.relativeType = relativeType;
	}	

	public Relatives(){
		uuid = UUIDUtil.getUUID();
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof Relatives)) {
			return false;
		}
		
		final Relatives e = (Relatives) o;

		if (e.getUuid() == null)
			return false;
		
		/* May 31, 2011 У.Наранхүү Start */
		if (this.getUuid() == null)
			return false;
		/* May 31, 2011 У.Наранхүү End */

		return getUuid().equals(e.getUuid());
	}

	public int hashCode() {
		return getUuid().hashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("uuid",
				this.uuid).toString();
	}

}
