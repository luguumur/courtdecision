package mn.mcs.electronics.court.entities.other;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="open_position")
public class OpenPosition extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8278496713894605292L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	
	@Column(name = "op_number")
	private Integer opNumber;
	
	@Column(name = "op_date")
	private Date opDate;
	
	@Column(name = "exam_date")
	private Date examDate;
	
	@Column(name = "registered_date")
	private Date registeredDate;
	
	@Column(name = "position_term")
	private Date positionTerm;
	
	@Column(name = "op_quantity")
	private Integer opQuantity;
	
	@Column(name = "institutor")
	private String institutor;
	
	@Column(name = "start_time")
	private String startTime;
	
	@Column(name = "end_time")
	private String endTime;
	
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

	public int hashCode() {
		return getUuid().hashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("uuid",
				this.uuid).toString();
	}

	public Date getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}

	public Date getPositionTerm() {
		return positionTerm;
	}

	public void setPositionTerm(Date positionTerm) {
		this.positionTerm = positionTerm;
	}

	public Integer getOpNumber() {
		return opNumber;
	}

	public void setOpNumber(Integer opNumber) {
		this.opNumber = opNumber;
	}

	public Integer getOpQuantity() {
		return opQuantity;
	}

	public void setOpQuantity(Integer opQuantity) {
		this.opQuantity = opQuantity;
	}

	public String getInstitutor() {
		return institutor;
	}

	public void setInstitutor(String institutor) {
		this.institutor = institutor;
	}

	public Date getOpDate() {
		return opDate;
	}

	public void setOpDate(Date opDate) {
		this.opDate = opDate;
	}

	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public OpenPosition(){
		uuid = UUIDUtil.getUUID();
	}

	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof OpenPosition)) {
			return false;
		}
		
		final OpenPosition e = (OpenPosition) o;

		if (e.getId() == null)
			return false;
		
		/* May 31, 2011 У.Наранхүү Start */
		if (this.getId() == null)
			return false;
		/* May 31, 2011 У.Наранхүү End */

		return getId().equals(e.getId());
	}

}
