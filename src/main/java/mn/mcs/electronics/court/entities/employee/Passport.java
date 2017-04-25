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
import mn.mcs.electronics.court.util.UUIDUtil;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="passport")
public class Passport extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5968465137307585542L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
		
	@Column(name="passport_no")
	private String passportNo;
	
	@Column(name="is_given")
	private Boolean isGiven;

	@Column(name="given_date")
	private Date givenDate;
	
	@Column(name = "is_diplomat")
	private Boolean isDiplomat;
	
	@Column(name="expire_date")
	private  Date expireDate;
	
	@Column(name="extent_date1")
	private Date extentDate1;
	
	@Column(name="extent_date2")
	private Date extentDate2;
	
	@Column(name = "is_retrieve")
	private Boolean isRetrieve;
	
	@Column(name="retrieve_date")
	private Date retrieveDate;
	
		
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

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public Date getGivenDate() {
		return givenDate;
	}

	public void setGivenDate(Date givenDate) {
		this.givenDate = givenDate;
	}

	public Boolean getIsDiplomat() {
		return isDiplomat;
	}

	public void setIsDiplomat(Boolean isDiplomat) {
		this.isDiplomat = isDiplomat;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public Date getExtentDate1() {
		return extentDate1;
	}

	public void setExtentDate1(Date extentDate1) {
		this.extentDate1 = extentDate1;
	}

	public Date getExtentDate2() {
		return extentDate2;
	}

	public void setExtentDate2(Date extentDate2) {
		this.extentDate2 = extentDate2;
	}

	public Boolean getIsGiven() {
		return isGiven;
	}

	public void setIsGiven(Boolean isGiven) {
		this.isGiven = isGiven;
	}

	public Boolean getIsRetrieve() {
		return isRetrieve;
	}

	public void setIsRetrieve(Boolean isRetrieve) {
		this.isRetrieve = isRetrieve;
	}

	public Date getRetrieveDate() {
		return retrieveDate;
	}

	public void setRetrieveDate(Date retrieveDate) {
		this.retrieveDate = retrieveDate;
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
	
	public Passport(){
		uuid = UUIDUtil.getUUID();
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof Passport)) {
			return false;
		}
		
		final Passport e = (Passport) o;

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
