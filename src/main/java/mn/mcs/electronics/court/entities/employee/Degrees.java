package mn.mcs.electronics.court.entities.employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.entities.configuration.Country;
import mn.mcs.electronics.court.entities.configuration.DegreeType;
import mn.mcs.electronics.court.entities.configuration.EducationDegree;
import mn.mcs.electronics.court.enums.DoctorType;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="degrees")
public class Degrees extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1747676702051594619L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	
	@ManyToOne
	@JoinColumn(name="employee")
	private Employee employee;

	@ManyToOne
	@JoinColumn(name="degree")
	private EducationDegree degree;
		
	@Column(name="doctor_type")
	private DoctorType doctorType;

	@Column(name="doctor")
	private String doctor;

	@ManyToOne
	@JoinColumn(name="degree_type")
	private DegreeType degreeType;
	
	@ManyToOne
	@JoinColumn(name="issued_country")
	private Country issuedCountry;
	
	@Column(name="subject")
	private String subject;
	
	@Column(name="issued_date")
	private String issuedDate;
	
	@Column(name="certificated_no")
	private String certificatedNo;
	
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
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public EducationDegree getDegree() {
		return degree;
	}

	public void setDegree(EducationDegree degree) {
		this.degree = degree;
	}

	public Country getIssuedCountry() {
		return issuedCountry;
	}

	public void setIssuedCountry(Country issuedCountry) {
		this.issuedCountry = issuedCountry;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(String issuedDate) {
		this.issuedDate = issuedDate;
	}

	public String getCertificatedNo() {
		return certificatedNo;
	}

	public void setCertificatedNo(String certificatedNo) {
		this.certificatedNo = certificatedNo;
	}

	public Degrees(){
		uuid = UUIDUtil.getUUID();
	}

	public DoctorType getDoctorType() {
		return doctorType;
	}

	public void setDoctorType(DoctorType doctorType) {
		this.doctorType = doctorType;
	}

	public DegreeType getDegreeType() {
		return degreeType;
	}

	public void setDegreeType(DegreeType degreeType) {
		this.degreeType = degreeType;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof Degrees)) {
			return false;
		}
		
		final Degrees e = (Degrees) o;

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
