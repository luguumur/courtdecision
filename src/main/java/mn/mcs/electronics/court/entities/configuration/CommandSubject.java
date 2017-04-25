/****************** Shiidver guitsetgeh *************************/
package mn.mcs.electronics.court.entities.configuration;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.entities.employee.Allowance;
import mn.mcs.electronics.court.entities.employee.Experience;
import mn.mcs.electronics.court.entities.employee.Sahilga;
import mn.mcs.electronics.court.entities.employee.Vacation;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="command_subject")
public class CommandSubject extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6428849680149765092L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	
	@Column(name="subject_name")
	private String subjectName;
	
	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "commandSubject")
	private Set<Allowance> allowance = new HashSet<Allowance>();
	
	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "commandSubject")
	private Set<Experience> experience = new HashSet<Experience>();
	
	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "commandSubject")
	private Set<Sahilga> sahilga = new HashSet<Sahilga>();
	
	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "commandSubject")
	private Set<Vacation> vacation = new HashSet<Vacation>();
	
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

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public CommandSubject(){
		uuid = UUIDUtil.getUUID();
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof CommandSubject)) {
			return false;
		}
		
		final CommandSubject e = (CommandSubject) o;

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

	public void setAllowance(Set<Allowance> allowance) {
		this.allowance = allowance;
	}

	public Set<Allowance> getAllowance() {
		return allowance;
	}

	public void setExperience(Set<Experience> experience) {
		this.experience = experience;
	}

	public Set<Experience> getExperience() {
		return experience;
	}

	public void setSahilga(Set<Sahilga> sahilga) {
		this.sahilga = sahilga;
	}

	public Set<Sahilga> getSahilga() {
		return sahilga;
	}

	public void setVacation(Set<Vacation> vacation) {
		this.vacation = vacation;
	}

	public Set<Vacation> getVacation() {
		return vacation;
	}
	
}
