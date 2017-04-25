/* 
 * File Name: 		Displacement.java
 * 
 * Created By: 		B.Erdenetuya
 * Created Date: 	Jul 4, 2012
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Jul 4, 2012 1.0.0 			B.Erdenetuya					Шинээр үүсгэв.
 * 
 * -----------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2011 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */
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

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.entities.configuration.Appointment;
import mn.mcs.electronics.court.entities.configuration.OccupationRole;
import mn.mcs.electronics.court.entities.configuration.OccupationType;
import mn.mcs.electronics.court.entities.configuration.SalaryNetwork;
import mn.mcs.electronics.court.entities.configuration.SalaryScale;
import mn.mcs.electronics.court.entities.configuration.TuAaLevel;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuLevel;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.DisplacementCauseType;
import mn.mcs.electronics.court.enums.MilitaryOrSimple;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "displacement")
public class Displacement extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2606796086270217131L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;

	@ManyToOne
	@JoinColumn(name = "employee")
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "organization")
	private Organization organization;

	@Lob
	@Column(name = "displacement_cause")
	private String displacementCause;

	@Column(name = "displacement_type")
	private DisplacementCauseType displacementType;

	@Column(name = "is_mainoccupation")
	private Boolean mainOccupation;

	@Column(name = "is_tenderer")
	private Boolean tenderer;

	@Column(name = "issued_date")
	private Date issuedDate;

	@Column(name = "quit_date")
	private Date quitDate;

	@Column(name = "is_request_displacement")
	private boolean isRequestDisplacement;
	
	@Column(name="military_or_simple")
	private MilitaryOrSimple militaryOrSimple;

	@ManyToOne
	@JoinColumn(name = "occupation_role")
	private OccupationRole occupationRole;

	@ManyToOne
	@JoinColumn(name = "appointment")
	private Appointment appointment;

	@ManyToOne
	@JoinColumn(name = "occupaton_type")
	private OccupationType occupationType;

	@ManyToOne
	@JoinColumn(name = "ut_tz_tt_tu_level")
	private UtTzTtTuLevel utTzTtTuLevel;

	@Column(name = "is_directory_training")
	private Boolean isDirectoryTraining;

	@Column(name = "is_qualification_training")
	private Boolean isQualificationTraining;

	@ManyToOne
	@JoinColumn(name = "tu_aa_level")
	private TuAaLevel tuAaLevel;

	@Column(name = "officiary_sort")
	private String officiarySort;

	@Column(name = "category_sort")
	private String officiaryCategory;

	/* Цалинжих хэлбэр */
	@ManyToOne
	@JoinColumn(name = "salary_network")
	private SalaryNetwork salaryNetwork;

	@ManyToOne
	@JoinColumn(name = "salary_scale")
	private SalaryScale salaryScale;

	/* Цалин тогтоогдсон огноо */
	@Column(name = "salary_date")
	private Date salaryDate;

	@Column(name = "is_now_displacement")
	private Boolean isNowDisplacement;

	/* Getter, Setter */

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

	public Displacement() {
		uuid = UUIDUtil.getUUID();
	}

	public void setDisplacementCause(String displacementCause) {
		this.displacementCause = displacementCause;
	}

	public String getDisplacementCause() {
		return displacementCause;
	}

	public Boolean getMainOccupation() {
		return mainOccupation;
	}

	public void setMainOccupation(Boolean mainOccupation) {
		this.mainOccupation = mainOccupation;
	}

	public Boolean getTenderer() {
		return tenderer;
	}

	public void setTenderer(Boolean tenderer) {
		this.tenderer = tenderer;
	}

	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	public Date getIssuedDate() {
		return issuedDate;
	}

	public Date getQuitDate() {
		return quitDate;
	}

	public void setQuitDate(Date quitDate) {
		this.quitDate = quitDate;
	}

	public boolean isRequestDisplacement() {
		return isRequestDisplacement;
	}

	public void setRequestDisplacement(boolean isRequestDisplacement) {
		this.isRequestDisplacement = isRequestDisplacement;
	}

	public MilitaryOrSimple getMilitaryOrSimple() {
		return militaryOrSimple;
	}

	public void setMilitaryOrSimple(MilitaryOrSimple militaryOrSimple) {
		this.militaryOrSimple = militaryOrSimple;
	}

	public OccupationRole getOccupationRole() {
		return occupationRole;
	}

	public void setOccupationRole(OccupationRole occupationRole) {
		this.occupationRole = occupationRole;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public OccupationType getOccupationType() {
		return occupationType;
	}

	public void setOccupationType(OccupationType occupationType) {
		this.occupationType = occupationType;
	}

	public UtTzTtTuLevel getUtTzTtTuLevel() {
		return utTzTtTuLevel;
	}

	public void setUtTzTtTuLevel(UtTzTtTuLevel utTzTtTuLevel) {
		this.utTzTtTuLevel = utTzTtTuLevel;
	}

	public Boolean getIsDirectoryTraining() {
		return isDirectoryTraining;
	}

	public void setIsDirectoryTraining(Boolean isDirectoryTraining) {
		this.isDirectoryTraining = isDirectoryTraining;
	}

	public Boolean getIsQualificationTraining() {
		return isQualificationTraining;
	}

	public void setIsQualificationTraining(Boolean isQualificationTraining) {
		this.isQualificationTraining = isQualificationTraining;
	}

	public boolean isQualificationTraining() {
		return isQualificationTraining;
	}

	public void setQualificationTraining(boolean isQualificationTraining) {
		this.isQualificationTraining = isQualificationTraining;
	}

	public TuAaLevel getTuAaLevel() {
		return tuAaLevel;
	}

	public void setTuAaLevel(TuAaLevel tuAaLevel) {
		this.tuAaLevel = tuAaLevel;
	}

	public String getOfficiarySort() {
		return officiarySort;
	}

	public void setOfficiarySort(String officiarySort) {
		this.officiarySort = officiarySort;
	}

	public String getOfficiaryCategory() {
		return officiaryCategory;
	}

	public void setOfficiaryCategory(String officiaryCategory) {
		this.officiaryCategory = officiaryCategory;
	}

	public SalaryNetwork getSalaryNetwork() {
		return salaryNetwork;
	}

	public void setSalaryNetwork(SalaryNetwork salaryNetwork) {
		this.salaryNetwork = salaryNetwork;
	}

	public SalaryScale getSalaryScale() {
		return salaryScale;
	}

	public void setSalaryScale(SalaryScale salaryScale) {
		this.salaryScale = salaryScale;
	}

	public DisplacementCauseType getDisplacementType() {
		return displacementType;
	}

	public void setDisplacementType(DisplacementCauseType displacementType) {
		this.displacementType = displacementType;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Employee getEmployee() {
		return employee;
	}

	public Date getSalaryDate() {
		return salaryDate;
	}

	public void setSalaryDate(Date salaryDate) {
		this.salaryDate = salaryDate;
	}

	public Boolean getIsNowDisplacement() {
		return isNowDisplacement;
	}

	public void setIsNowDisplacement(Boolean isNowDisplacement) {
		this.isNowDisplacement = isNowDisplacement;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;

		if (o == null)
			return false;

		if (!(o instanceof Displacement)) {
			return false;
		}

		final Displacement e = (Displacement) o;

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
		return new ToStringBuilder(this).append("id", this.id)
				.append("uuid", this.uuid).toString();
	}

}
