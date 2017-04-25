package mn.mcs.electronics.court.entities.employee;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.entities.configuration.CityProvince;
import mn.mcs.electronics.court.entities.configuration.Country;
import mn.mcs.electronics.court.entities.configuration.DistrictSum;
import mn.mcs.electronics.court.entities.configuration.Occupation;
import mn.mcs.electronics.court.entities.configuration.RelativeType;
import mn.mcs.electronics.court.enums.GCCMemberStatus;
import mn.mcs.electronics.court.enums.LanguageLevel;
import mn.mcs.electronics.court.enums.Score;
import mn.mcs.electronics.court.util.UUIDUtil;

@Entity
@Table(name="resultcontract")
public class ResultContract extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3780851650558989539L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	

	@Column(name="all_supply_score")
	private Integer allSupplyScore;
	
	@Column(name="special_supply_score")
	private Integer specialSupplyScore;
	
	@Column(name="qualification_level")
	private Integer qualificationLevel;
	
	@Column(name="contract_date")
	private Date contractDate;
	
	@Column(name="direct_superior_score")
	private Score directSuperiorScore;
	
	@Lob
	@Column(name="direct_superior_cause")
	private String directSuperiorCause;
	
	@Column(name="superior_score")
	private Score superiorScore;
	
	@Lob
	@Column(name="superior_cause")
	private String superiorCause;
	
		
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
		
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Employee getEmployee() {
		return employee;
	}

	public Integer getAllSupplyScore() {
		return allSupplyScore;
	}

	public void setAllSupplyScore(Integer allSupplyScore) {
		this.allSupplyScore = allSupplyScore;
	}

	public Integer getSpecialSupplyScore() {
		return specialSupplyScore;
	}

	public void setSpecialSupplyScore(Integer specialSupplyScore) {
		this.specialSupplyScore = specialSupplyScore;
	}

	public Integer getQualificationLevel() {
		return qualificationLevel;
	}

	public void setQualificationLevel(Integer qualificationLevel) {
		this.qualificationLevel = qualificationLevel;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	public String getDirectSuperiorCause() {
		return directSuperiorCause;
	}

	public void setDirectSuperiorCause(String directSuperiorCause) {
		this.directSuperiorCause = directSuperiorCause;
	}

	public Score getDirectSuperiorScore() {
		return directSuperiorScore;
	}

	public void setDirectSuperiorScore(Score directSuperiorScore) {
		this.directSuperiorScore = directSuperiorScore;
	}

	public Score getSuperiorScore() {
		return superiorScore;
	}

	public void setSuperiorScore(Score superiorScore) {
		this.superiorScore = superiorScore;
	}

	public String getSuperiorCause() {
		return superiorCause;
	}

	public void setSuperiorCause(String superiorCause) {
		this.superiorCause = superiorCause;
	}

	public ResultContract(){
		uuid = UUIDUtil.getUUID();
	}
	
	
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof ResultContract)) {
			return false;
		}
		
		final ResultContract e = (ResultContract) o;

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
