package mn.mcs.electronics.court.entities.configuration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.util.UUIDUtil;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="worked_year")
public class WorkedYear extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1780385925417185617L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	
	@Column(name="state_worked_year")
	private Long stateWorkedYear;
	
	@Column(name="major_worked_year")
	private Long majorWorkedYear;
	
	
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

	public Long getStateWorkedYear() {
		return stateWorkedYear;
	}

	public void setStateWorkedYear(Long stateWorkedYear) {
		this.stateWorkedYear = stateWorkedYear;
	}

	public Long getMajorWorkedYear() {
		return majorWorkedYear;
	}

	public void setMajorWorkedYear(Long majorWorkedYear) {
		this.majorWorkedYear = majorWorkedYear;
	}

	public WorkedYear(){
		uuid = UUIDUtil.getUUID();
	}
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof WorkedYear)) {
			return false;
		}
		
		final WorkedYear e = (WorkedYear) o;

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
