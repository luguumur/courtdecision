package mn.mcs.electronics.court.entities.configuration.salary;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.entities.configuration.SalaryScale;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuLevel;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="turiin_zahirgaa_salary_netwrok")
public class TuriinZahirgaaSalaryNetwork extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2568381204619588355L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	
	@ManyToOne
	@JoinColumn(name="level")
	private UtTzTtTuLevel level;
	
	@ManyToOne
	@JoinColumn(name="salary_scale")
	private SalaryScale phase;
	
	@Column(name="amount")
	private Double amount;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="active")
	private Boolean active;
	
	@Column(name="valid_time")
	private Integer validTime;
	
	public TuriinZahirgaaSalaryNetwork(){
		uuid = UUIDUtil.getUUID();
	}
	
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

	public UtTzTtTuLevel getLevel() {
		return level;
	}

	public void setLevel(UtTzTtTuLevel level) {
		this.level = level;
	}
	
	public SalaryScale getPhase() {
		return phase;
	}

	public void setPhase(SalaryScale phase) {
		this.phase = phase;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}	

	public Integer getValidTime() {
		return validTime;
	}

	public void setValidTime(Integer validTime) {
		this.validTime = validTime;
	}

	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof TuriinZahirgaaSalaryNetwork)) {
			return false;
		}
		
		final TuriinZahirgaaSalaryNetwork e = (TuriinZahirgaaSalaryNetwork) o;

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
