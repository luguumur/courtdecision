package mn.mcs.electronics.court.entities.employee;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.entities.configuration.City;
import mn.mcs.electronics.court.entities.configuration.CityProvince;
import mn.mcs.electronics.court.entities.configuration.Country;
import mn.mcs.electronics.court.entities.configuration.DegreeType;
import mn.mcs.electronics.court.entities.configuration.DistrictSum;
import mn.mcs.electronics.court.entities.configuration.Occupation;
import mn.mcs.electronics.court.entities.configuration.School;
import mn.mcs.electronics.court.enums.SchoolType;
import mn.mcs.electronics.court.enums.UniversityType;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="educations")
public class Educations extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7574422721306190728L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;

	@Column(name="ingoing_date")
	private Integer ingoingDate;
	
	@Column(name="graduated_date")
	private Integer graduatedDate;

	@ManyToOne
	@JoinColumn(name="country")
	private Country country;
	
	//Хот аймаг
	@ManyToOne
	@JoinColumn(name="city")
	private City city;
	
	//Гадаад хот
	@ManyToOne
	@JoinColumn(name="city_province")
	private CityProvince cityProvince;
	
	@ManyToOne
	@JoinColumn(name="district_sum")
	private DistrictSum districtSum;
	
	@ManyToOne
	@JoinColumn(name="occupation")
	private Occupation occupation;
	
	@Column(name="occupation_other")
	private String occupationOther;
	
	@ManyToOne
	@JoinColumn(name="degree_type")
	private DegreeType degreeType;

	@Column(name="diploma_no")
	private String diplomaNo;
	
	@Column(name="diploma_subject")
	private String diplomaSubject;
	
	@Column(name="school_primary")
	private String school;
	
	@ManyToOne
	@JoinColumn(name="university")
	private School university;
	
	@Column(name="school_type")
	private SchoolType schoolType;
	
	@Column(name="university_type")
	private UniversityType universityType;
	
	@ManyToOne
	@JoinColumn(name="employee")
	private Employee employee;
	
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

	public Integer getIngoingDate() {
		return ingoingDate;
	}

	public void setIngoingDate(Integer ingoingDate) {
		this.ingoingDate = ingoingDate;
	}

	public Integer getGraduatedDate() {
		return graduatedDate;
	}

	public void setGraduatedDate(Integer graduatedDate) {
		this.graduatedDate = graduatedDate;
	}

	public Occupation getOccupation() {
		return occupation;
	}

	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}

	public DegreeType getDegreeType() {
		return degreeType;
	}

	public void setDegreeType(DegreeType degreeType) {
		this.degreeType = degreeType;
	}

	public String getDiplomaNo() {
		return diplomaNo;
	}

	public void setDiplomaNo(String diplomaNo) {
		this.diplomaNo = diplomaNo;
	}

	public UniversityType getUniversityType() {
		return universityType;
	}

	public void setUniversityType(UniversityType universityType) {
		this.universityType = universityType;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public Employee getEmployee() {
		return employee;
	}

	public SchoolType getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(SchoolType schoolType) {
		this.schoolType = schoolType;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public CityProvince getCityProvince() {
		return cityProvince;
	}

	public void setCityProvince(CityProvince cityProvince) {
		this.cityProvince = cityProvince;
	}

	public DistrictSum getDistrictSum() {
		return districtSum;
	}

	public void setDistrictSum(DistrictSum districtSum) {
		this.districtSum = districtSum;
	}

	public String getOccupationOther() {
		return occupationOther;
	}

	public void setOccupationOther(String occupationOther) {
		this.occupationOther = occupationOther;
	}

	public String getDiplomaSubject() {
		return diplomaSubject;
	}

	public void setDiplomaSubject(String diplomaSubject) {
		this.diplomaSubject = diplomaSubject;
	}

	public School getUniversity() {
		return university;
	}

	public void setUniversity(School university) {
		this.university = university;
	}

	public Educations(){
		uuid = UUIDUtil.getUUID();
	}
	
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof Educations)) {
			return false;
		}
		
		final Educations e = (Educations) o;

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
