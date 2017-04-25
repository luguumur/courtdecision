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
@Table(name="retire_age")
public class RetireAge extends BaseObject{

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
	
	@Column(name="male_age")
	private Integer maleAge;
	
	@Column(name="female_age")
	private Integer femaleAge;
	
	@Column(name="general_judge_date")
	private Integer generalJudgeDate;
	
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

	public Integer getMaleAge() {
		return maleAge;
	}

	public void setMaleAge(Integer maleAge) {
		this.maleAge = maleAge;
	}

	public Integer getFemaleAge() {
		return femaleAge;
	}

	public Integer getGeneralJudgeDate() {
		return generalJudgeDate;
	}

	public void setGeneralJudgeDate(Integer generalJudgeDate) {
		this.generalJudgeDate = generalJudgeDate;
	}

	public void setFemaleAge(Integer femaleAge) {
		this.femaleAge = femaleAge;
	}

	public RetireAge(){
		uuid = UUIDUtil.getUUID();
	}
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof RetireAge)) {
			return false;
		}
		
		final RetireAge e = (RetireAge) o;

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
