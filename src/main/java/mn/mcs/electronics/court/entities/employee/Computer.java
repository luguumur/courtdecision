package mn.mcs.electronics.court.entities.employee;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.entities.configuration.ComputerProgram;
import mn.mcs.electronics.court.enums.LanguageLevel;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="computer")
public class Computer extends BaseObject {

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
	

	@ManyToOne
	@JoinColumn(name="program")
	private ComputerProgram program;
		
	@Column(name="programlevel")
	private  LanguageLevel programlevel;
		
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

	public ComputerProgram getProgram() {
		return program;
	}

	public void setProgram(ComputerProgram program) {
		this.program = program;
	}

	public LanguageLevel getProgramlevel() {
		return programlevel;
	}

	public void setProgramlevel(LanguageLevel programlevel) {
		this.programlevel = programlevel;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Employee getEmployee() {
		return employee;
	}

	public Computer(){
		uuid = UUIDUtil.getUUID();
	}
	
	
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof Computer)) {
			return false;
		}
		
		final Computer e = (Computer) o;

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
