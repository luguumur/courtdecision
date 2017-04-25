package mn.mcs.electronics.court.entities.employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.entities.configuration.AcademicRank;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;


@Entity
@Table(name="degree_grade_rank")
public class DegreeGradeRank extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8534862713499510069L;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;

	@ManyToOne
	@JoinColumn(name="employee")
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name="academic_rank")
	private AcademicRank academicRank;
	
	@Column(name="rank_organization")
	private String rankOrganization;
	
	@Column(name="rank_date")
	private String rankDate;
	
	@Column(name="rank_certificate")
	private String rankCertificate;
	
	/* getter, setter */
	
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
	
	public DegreeGradeRank(){
		uuid = UUIDUtil.getUUID();
	}

	public AcademicRank getAcademicRank() {
		return academicRank;
	}

	public void setAcademicRank(AcademicRank academicRank) {
		this.academicRank = academicRank;
	}

	public String getRankOrganization() {
		return rankOrganization;
	}

	public void setRankOrganization(String rankOrganization) {
		this.rankOrganization = rankOrganization;
	}
	
	public String getRankDate() {
		return rankDate;
	}

	public void setRankDate(String rankDate) {
		this.rankDate = rankDate;
	}

	public String getRankCertificate() {
		return rankCertificate;
	}

	public void setRankCertificate(String rankCertificate) {
		this.rankCertificate = rankCertificate;
	}

	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof Degrees)) {
			return false;
		}
		
		final Degrees e = (Degrees) o;

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
