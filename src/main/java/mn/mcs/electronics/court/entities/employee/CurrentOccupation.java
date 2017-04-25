/* 
 * File Name: 		CurrentOccupation.java
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.entities.configuration.AcademicRank;
import mn.mcs.electronics.court.entities.configuration.DisplacementCause;
import mn.mcs.electronics.court.entities.configuration.Occupation;
import mn.mcs.electronics.court.entities.configuration.OccupationRole;
import mn.mcs.electronics.court.entities.configuration.OccupationType;
import mn.mcs.electronics.court.entities.configuration.SalaryNetwork;
import mn.mcs.electronics.court.entities.configuration.SalaryScale;
import mn.mcs.electronics.court.entities.configuration.TuAaSort;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuSort;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.ToriinUndurAjilAlbaCategory;
import mn.mcs.electronics.court.enums.UtTzTtTuCategory;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "current_occupation")
public class CurrentOccupation extends BaseObject {

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

	@Column(name = "displacement_cause")
	private String displacementCause;

	@ManyToOne
	@JoinColumn(name = "displacement_type")
	private DisplacementCause displacementType;

	@Column(name = "is_mainoccupation")
	private boolean mainOccupation;

	@Column(name = "is_tenderer")
	private boolean tenderer;

	@Column(name = "issued_date")
	private Date issuedDate;

	@Column(name = "is_request_displacement")
	private boolean isRequestDisplacement;

	@Column(name = "owned_unit")
	private String ownedUnit;

	@ManyToOne
	@JoinColumn(name = "occupation_role")
	private OccupationRole occupationRole;

	@ManyToOne
	@JoinColumn(name = "occupation")
	private Occupation occupation;

	@ManyToOne
	@JoinColumn(name = "occupaton_type")
	private OccupationType occupationType;

	@Column(name = "ut_tz_tt_tu_category")
	private UtTzTtTuCategory utTzTtTuCategory;

	@ManyToOne
	@JoinColumn(name = "ut_tz_tt_tu_sort")
	private UtTzTtTuSort utTzTtTuSort;

	@Column(name = "is_directory_training")
	private boolean isDirectoryTraining;

	@Column(name = "is_qualification_training")
	private boolean isQualificationTraining;

	@Column(name = "tu_aa_category")
	private ToriinUndurAjilAlbaCategory tuAaCategory;

	@ManyToOne
	@JoinColumn(name = "tu_aa_sort")
	private TuAaSort tuAaSort;

	@ManyToOne
	@JoinColumn(name = "salary_network")
	private SalaryNetwork salaryNetwork;

	@ManyToOne
	@JoinColumn(name = "salary_scale")
	private SalaryScale salaryScale;

	@ManyToOne
	@JoinColumn(name = "military_rank")
	private AcademicRank militaryRank;

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

	public CurrentOccupation() {
		uuid = UUIDUtil.getUUID();
	}

	public void setDisplacementCause(String displacementCause) {
		this.displacementCause = displacementCause;
	}

	public String getDisplacementCause() {
		return displacementCause;
	}

	public void setMainOccupation(boolean mainOccupation) {
		this.mainOccupation = mainOccupation;
	}

	public boolean isMainOccupation() {
		return mainOccupation;
	}

	public void setTenderer(boolean tenderer) {
		this.tenderer = tenderer;
	}

	public boolean isTenderer() {
		return tenderer;
	}

	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	public Date getIssuedDate() {
		return issuedDate;
	}

	public boolean isRequestDisplacement() {
		return isRequestDisplacement;
	}

	public void setRequestDisplacement(boolean isRequestDisplacement) {
		this.isRequestDisplacement = isRequestDisplacement;
	}

	public void setOwnedUnit(String ownedUnit) {
		this.ownedUnit = ownedUnit;
	}

	public String getOwnedUnit() {
		return ownedUnit;
	}

	public OccupationRole getOccupationRole() {
		return occupationRole;
	}

	public void setOccupationRole(OccupationRole occupationRole) {
		this.occupationRole = occupationRole;
	}

	public Occupation getOccupation() {
		return occupation;
	}

	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}

	public OccupationType getOccupationType() {
		return occupationType;
	}

	public void setOccupationType(OccupationType occupationType) {
		this.occupationType = occupationType;
	}

	public UtTzTtTuCategory getUtTzTtTuCategory() {
		return utTzTtTuCategory;
	}

	public void setUtTzTtTuCategory(UtTzTtTuCategory utTzTtTuCategory) {
		this.utTzTtTuCategory = utTzTtTuCategory;
	}

	public UtTzTtTuSort getUtTzTtTuSort() {
		return utTzTtTuSort;
	}

	public void setUtTzTtTuSort(UtTzTtTuSort utTzTtTuSort) {
		this.utTzTtTuSort = utTzTtTuSort;
	}

	public boolean isDirectoryTraining() {
		return isDirectoryTraining;
	}

	public void setDirectoryTraining(boolean isDirectoryTraining) {
		this.isDirectoryTraining = isDirectoryTraining;
	}

	public boolean isQualificationTraining() {
		return isQualificationTraining;
	}

	public void setQualificationTraining(boolean isQualificationTraining) {
		this.isQualificationTraining = isQualificationTraining;
	}

	public ToriinUndurAjilAlbaCategory getTuAaCategory() {
		return tuAaCategory;
	}

	public void setTuAaCategory(ToriinUndurAjilAlbaCategory tuAaCategory) {
		this.tuAaCategory = tuAaCategory;
	}

	public TuAaSort getTuAaSort() {
		return tuAaSort;
	}

	public void setTuAaSort(TuAaSort tuAaSort) {
		this.tuAaSort = tuAaSort;
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

	public AcademicRank getMilitaryRank() {
		return militaryRank;
	}

	public void setMilitaryRank(AcademicRank militaryRank) {
		this.militaryRank = militaryRank;
	}

	public DisplacementCause getDisplacementType() {
		return displacementType;
	}

	public void setDisplacementType(DisplacementCause displacementType) {
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

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;

		if (o == null)
			return false;

		if (!(o instanceof CurrentOccupation)) {
			return false;
		}

		final CurrentOccupation e = (CurrentOccupation) o;

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
