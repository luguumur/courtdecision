package mn.mcs.electronics.court.entities.employee;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.entities.configuration.Appointment;
import mn.mcs.electronics.court.entities.configuration.CommandSubject;
import mn.mcs.electronics.court.entities.configuration.OccupationType;
import mn.mcs.electronics.court.enums.EmployeeStatus;
import mn.mcs.electronics.court.enums.MilitaryOrSimple;
import mn.mcs.electronics.court.enums.OrganizationTypeExperience;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "experience")
public class Experience extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5373130093823590189L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;

	@Column(name = "organization_type")
	private OrganizationTypeExperience organizationtype;

	@Column(name = "organization_name")
	private String organizationname;

	@Column(name = "appointment")
	private String appointment;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "is_judge")
	private Boolean isJudge;

	@Column(name = "is_major")
	private Boolean isMajor;

	@Column(name = "status")
	private EmployeeStatus status;

	@Column(name = "days_of_worked_major")
	private Integer daysOfWorkedMajor;

	/* Dec 07, 2012 Jargalsuren.S Start */
	@Column(name = "command_number")
	private String commandNumber;

	@ManyToOne
	@JoinColumn(name = "command_subject")
	private CommandSubject commandSubject;

	@Column(name = "command_start_date")
	private Date commandStartDate;

	@Column(name = "command_end_date")
	private Date commandEndDate;

	@Column(name = "militaryOrSimple")
	private MilitaryOrSimple militaryOrSimple;
	/* Dec 07, 2012 Jargalsuren.S End */

	@ManyToOne
	@JoinColumn(name = "employee")
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "appointment_name")
	private Appointment appointmentName;

	@ManyToOne
	@JoinColumn(name = "occupation_type")
	private OccupationType occupationType;

	@OneToOne
	@JoinColumn(name = "displacement")
	private Displacement displacement;

	/*
	 * @OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch =
	 * FetchType.LAZY, mappedBy = "employee") private Set<Relatives> relatives =
	 * new HashSet<Relatives>();
	 */

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

	public OrganizationTypeExperience getOrganizationtype() {
		return organizationtype;
	}

	public void setOrganizationtype(OrganizationTypeExperience organizationtype) {
		this.organizationtype = organizationtype;
	}

	public String getOrganizationname() {
		return organizationname;
	}

	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}

	public String getAppointment() {
		return appointment;
	}

	public void setAppointment(String appointment) {
		this.appointment = appointment;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Boolean getIsJudge() {
		return isJudge;
	}

	public void setIsJudge(Boolean isJudge) {
		this.isJudge = isJudge;
	}

	public Boolean getIsMajor() {
		return isMajor;
	}

	public void setIsMajor(Boolean isMajor) {
		this.isMajor = isMajor;
	}

	public EmployeeStatus getStatus() {
		return status;
	}

	public void setStatus(EmployeeStatus status) {
		this.status = status;
	}

	public Integer getDaysOfWorkedMajor() {
		return daysOfWorkedMajor;
	}

	public void setDaysOfWorkedMajor(Integer daysOfWorkedMajor) {
		this.daysOfWorkedMajor = daysOfWorkedMajor;
	}

	public String getCommandNumber() {
		return commandNumber;
	}

	public void setCommandNumber(String commandNumber) {
		this.commandNumber = commandNumber;
	}

	public CommandSubject getCommandSubject() {
		return commandSubject;
	}

	public void setCommandSubject(CommandSubject commandSubject) {
		this.commandSubject = commandSubject;
	}

	public Date getCommandStartDate() {
		return commandStartDate;
	}

	public void setCommandStartDate(Date commandStartDate) {
		this.commandStartDate = commandStartDate;
	}

	public Date getCommandEndDate() {
		return commandEndDate;
	}

	public void setCommandEndDate(Date commandEndDate) {
		this.commandEndDate = commandEndDate;
	}

	public MilitaryOrSimple getMilitaryOrSimple() {
		return militaryOrSimple;
	}

	public void setMilitaryOrSimple(MilitaryOrSimple militaryOrSimple) {
		this.militaryOrSimple = militaryOrSimple;
	}

	public OccupationType getOccupationType() {
		return occupationType;
	}

	public void setOccupationType(OccupationType occupationType) {
		this.occupationType = occupationType;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Employee getEmployee() {
		return employee;
	}

	public Displacement getDisplacement() {
		return displacement;
	}

	public void setDisplacement(Displacement displacement) {
		this.displacement = displacement;
	}

	public Appointment getAppointmentName() {
		return appointmentName;
	}

	public void setAppointmentName(Appointment appointmentName) {
		this.appointmentName = appointmentName;
	}

	public Experience() {
		uuid = UUIDUtil.getUUID();
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;

		if (o == null)
			return false;

		if (!(o instanceof Experience)) {
			return false;
		}

		final Experience e = (Experience) o;

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
