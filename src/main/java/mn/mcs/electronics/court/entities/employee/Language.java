package mn.mcs.electronics.court.entities.employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.entities.configuration.ForeignLanguage;
import mn.mcs.electronics.court.enums.LanguageLevel;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="language")
public class Language extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7859774086867179246L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	
	@ManyToOne
	@JoinColumn(name="foreign_language")
	private ForeignLanguage foreignLanguage;
		
	@Column(name="listening")
	private  LanguageLevel listening;
	
	@Column(name="speaking")
	private  LanguageLevel speaking;
	
	@Column(name="reading")
	private  LanguageLevel reading;
	
	@Column(name="writing")
	private  LanguageLevel writing;
	
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

	public ForeignLanguage getForeignLanguage() {
		return foreignLanguage;
	}

	public void setForeignLanguage(ForeignLanguage foreignLanguage) {
		this.foreignLanguage = foreignLanguage;
	}

	public LanguageLevel getListening() {
		return listening;
	}

	public void setListening(LanguageLevel listening) {
		this.listening = listening;
	}

	public LanguageLevel getSpeaking() {
		return speaking;
	}

	public void setSpeaking(LanguageLevel speaking) {
		this.speaking = speaking;
	}

	public LanguageLevel getReading() {
		return reading;
	}

	public void setReading(LanguageLevel reading) {
		this.reading = reading;
	}

	public LanguageLevel getWriting() {
		return writing;
	}

	public void setWriting(LanguageLevel writing) {
		this.writing = writing;
	}

		
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Employee getEmployee() {
		return employee;
	}

	public Language(){
		uuid = UUIDUtil.getUUID();
	}
		
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof Language)) {
			return false;
		}
		
		final Language e = (Language) o;

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
