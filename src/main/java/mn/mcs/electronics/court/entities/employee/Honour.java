package mn.mcs.electronics.court.entities.employee;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.entities.configuration.Award;
import mn.mcs.electronics.court.enums.AwardType;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="honour")
public class Honour extends BaseObject {

	private static final long serialVersionUID = -5373130093823590189L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	
	@ManyToOne
	@JoinColumn(name="employee")
	private Employee employee;
	
	@JoinColumn(name="award_type")
	@Enumerated(EnumType.ORDINAL)
	private AwardType awardType;
	
	@Column(name="other_name")
	private String otherPrize;
	
	@ManyToOne
	@JoinColumn(name="award")
	private Award award;
	
	@Column(name="date_of_awarded")
	private Date dateOfAwarded;
	
	@Column(name="authentication_id")
	private  String authenticationId;
	
	@Column(name="dictate_id")
	private  String dictateId;
	
	@Lob
	@Column(name="description") 
	private  String description;
	
		/*getter, setter*/
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Honour(){
		uuid = UUIDUtil.getUUID();
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public AwardType getAwardType() {
		return awardType;
	}

	public void setAwardType(AwardType awardType) {
		this.awardType = awardType;
	}

	public String getOtherPrize() {
		return otherPrize;
	}

	public void setOtherPrize(String otherPrize) {
		this.otherPrize = otherPrize;
	}

	public Date getDateOfAwarded() {
		return dateOfAwarded;
	}

	public void setDateOfAwarded(Date dateOfAwarded) {
		this.dateOfAwarded = dateOfAwarded;
	}
	
	public String getAuthenticationId() {
		return authenticationId;
	}

	public void setAuthenticationId(String authenticationid) {
		this.authenticationId = authenticationid;
	}

	public String getDictateId() {
		return dictateId;
	}

	public void setDictateId(String dictateId) {
		this.dictateId = dictateId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Award getAward() {
		return award;
	}

	public void setAward(Award award) {
		this.award = award;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof Honour)) {
			return false;
		}
		
		final Honour e = (Honour) o;

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
		return getUuid().hashCode();
	}

	
}
