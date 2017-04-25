package mn.mcs.electronics.court.entities.employee;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.enums.LanguageLevel;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="computer_other")
public class ComputerOther extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3317932721590896212L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	
	@Column(name="program")
	private String otherProgram;
		
	@Column(name="programlevel")
	private  LanguageLevel otherProgramlevel;
		
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

	public String getOtherProgram() {
		return otherProgram;
	}

	public void setOtherProgram(String otherProgram) {
		this.otherProgram = otherProgram;
	}

	public LanguageLevel getOtherProgramlevel() {
		return otherProgramlevel;
	}

	public void setOtherProgramlevel(LanguageLevel otherProgramlevel) {
		this.otherProgramlevel = otherProgramlevel;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Employee getEmployee() {
		return employee;
	}


	public ComputerOther(){
		uuid = UUIDUtil.getUUID();
	}
	
	
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof ComputerOther)) {
			return false;
		}
		
		final ComputerOther e = (ComputerOther) o;

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
