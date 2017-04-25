package mn.mcs.electronics.court.entities.employee;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.entities.configuration.MilitaryRank;
import mn.mcs.electronics.court.enums.EmployeeDegreeStatus;
import mn.mcs.electronics.court.enums.MilitaryRankType;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="employee_military")
public class EmployeeMilitary extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9210159736169013353L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	
	@ManyToOne
	@JoinColumn(name="employee")
	private Employee employee;
	
	@JoinColumn(name="military_type")
	@Enumerated(EnumType.ORDINAL)
	private MilitaryRankType militaryType;

	@ManyToOne
	@JoinColumn(name="military")
	private MilitaryRank military;
	
	@Column(name="olgoson_date")
	private Date olgosonOgnoo;
	
	@Column(name="tushaal")
	private String tushaalDugaar;	
	
	@JoinColumn(name="degreeStatus")
	@Enumerated(EnumType.ORDINAL)
	private EmployeeDegreeStatus degreeStatus;

	
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
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public MilitaryRankType getMilitaryType() {
		return militaryType;
	}

	public void setMilitaryType(MilitaryRankType militaryType) {
		this.militaryType = militaryType;
	}

	public MilitaryRank getMilitary() {
		return military;
	}

	public void setMilitary(MilitaryRank military) {
		this.military = military;
	}

	public Date getOlgosonOgnoo() {
		return olgosonOgnoo;
	}

	public void setOlgosonOgnoo(Date olgosonOgnoo) {
		this.olgosonOgnoo = olgosonOgnoo;
	}

	public String getTushaalDugaar() {
		return tushaalDugaar;
	}

	public void setTushaalDugaar(String tushaalDugaar) {
		this.tushaalDugaar = tushaalDugaar;
	}
	
	public void setDegreeStatus(EmployeeDegreeStatus degreeStatus) {
		this.degreeStatus = degreeStatus;
	}

	public EmployeeDegreeStatus getDegreeStatus() {
		return degreeStatus;
	}
	
	public EmployeeMilitary(){
		uuid = UUIDUtil.getUUID();
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof EmployeeMilitary)) {
			return false;
		}
		
		final EmployeeMilitary e = (EmployeeMilitary) o;

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
