package mn.mcs.electronics.court.entities.configuration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.enums.EmployeeCurrentStatus;
import mn.mcs.electronics.court.enums.QuitJobCauseName;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="quit_cause")
public class QuitJobCause extends BaseObject{

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

	@Column(name="cause_name")
	private String causeName;
	
	
	@Column(name="cause_type")
	private EmployeeCurrentStatus causeType;
	
	@Column(name="cause_type_name")
	private QuitJobCauseName quitCauseName;
	
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

	public String getCauseName() {
		return causeName;
	}

	public void setCauseName(String causeName) {
		this.causeName = causeName;
	}

	public EmployeeCurrentStatus getCauseType() {
		return causeType;
	}

	public void setCauseType(EmployeeCurrentStatus causeType) {
		this.causeType = causeType;
	}

	public void setQuitCauseName(QuitJobCauseName quitCauseName) {
		this.quitCauseName = quitCauseName;
	}

	public QuitJobCauseName getQuitCauseName() {
		return quitCauseName;
	}
	
	public QuitJobCause(){
		uuid = UUIDUtil.getUUID();
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof QuitJobCause)) {
			return false;
		}
		
		final QuitJobCause e = (QuitJobCause) o;

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
