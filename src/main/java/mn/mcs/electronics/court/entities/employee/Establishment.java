package mn.mcs.electronics.court.entities.employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.entities.configuration.Appointment;
import mn.mcs.electronics.court.entities.configuration.OccupationType;
import mn.mcs.electronics.court.entities.configuration.SalaryNetwork;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuLevel;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuSort;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.MilitaryRankType;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="establishment")
public class Establishment extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2495756500603000007L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;

	@Column(name="name")
	private String name;
	
	@Column(name="num")
	private Integer num;
	
	@Column(name="status")
	private Boolean status;
	
	@Column(name="main")
	private Boolean main;
	
	@OneToOne
	@JoinColumn(name = "appointment")
	private Appointment appointment;
	
	@ManyToOne
	@JoinColumn(name = "appointment_type")
	private OccupationType appointmentType;
	
	@ManyToOne
	@JoinColumn(name="organization")
	private Organization organization;
	
	@ManyToOne
	@JoinColumn(name="salary_network")
	private SalaryNetwork salaryNetwork;
	
	@ManyToOne
	@JoinColumn(name="utTzTtTuSort")
	private UtTzTtTuSort utTzTtTuSort;
	
	@ManyToOne
	@JoinColumn(name="utTzTtTuLevel")
	private UtTzTtTuLevel utTzTtTuLevel;
	
	@Column(name = "military_rank_type")
	@Enumerated(EnumType.ORDINAL)
	private MilitaryRankType militaryRankType;
	
	public Establishment(){
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Boolean getMain() {
		return main;
	}

	public void setMain(Boolean main) {
		this.main = main;
	}

	public UtTzTtTuSort getUtTzTtTuSort() {
		return utTzTtTuSort;
	}

	public void setUtTzTtTuSort(UtTzTtTuSort utTzTtTuSort) {
		this.utTzTtTuSort = utTzTtTuSort;
	}

	public UtTzTtTuLevel getUtTzTtTuLevel() {
		return utTzTtTuLevel;
	}

	public OccupationType getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(OccupationType appointmentType) {
		this.appointmentType = appointmentType;
	}

	public void setUtTzTtTuLevel(UtTzTtTuLevel utTzTtTuLevel) {
		this.utTzTtTuLevel = utTzTtTuLevel;
	}

	public MilitaryRankType getMilitaryRankType() {
		return militaryRankType;
	}

	public void setMilitaryRankType(MilitaryRankType militaryRankType) {
		this.militaryRankType = militaryRankType;
	}

	public SalaryNetwork getSalaryNetwork() {
		return salaryNetwork;
	}

	public void setSalaryNetwork(SalaryNetwork salaryNetwork) {
		this.salaryNetwork = salaryNetwork;
	}

	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof Establishment)) {
			return false;
		}
		
		final Establishment e = (Establishment) o;

		if (e.getUuid() == null)
			return false;
		
		/* May 31, 2011 У.Наранхүү Start */
		if (this.getUuid() == null)
			return false;
		/* May 31, 2011 У.Наранхүү End */

		return getUuid().equals(e.getUuid());
	}

	public int hashCode() {
		return getUuid().hashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("uuid",
				this.uuid).toString();
	}

}
