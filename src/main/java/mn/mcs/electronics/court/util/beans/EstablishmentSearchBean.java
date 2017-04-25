package mn.mcs.electronics.court.util.beans;

import mn.mcs.electronics.court.entities.configuration.Appointment;
import mn.mcs.electronics.court.entities.configuration.OccupationType;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuLevel;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuSort;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.MilitaryRankType;

public class EstablishmentSearchBean {

	private String name;

	private Integer num;

	private Boolean status;

	private OccupationType appointmentType;
	
	private Boolean main;

	private Appointment appointment;

	private Organization organization;	

	private UtTzTtTuSort utTzTtTuSort;

	private UtTzTtTuLevel utTzTtTuLevel;

	private MilitaryRankType militaryRankType;

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

	public void setUtTzTtTuLevel(UtTzTtTuLevel utTzTtTuLevel) {
		this.utTzTtTuLevel = utTzTtTuLevel;
	}

	public MilitaryRankType getMilitaryRankType() {
		return militaryRankType;
	}

	public void setMilitaryRankType(MilitaryRankType militaryRankType) {
		this.militaryRankType = militaryRankType;
	}

	public OccupationType getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(OccupationType appointmentType) {
		this.appointmentType = appointmentType;
	}

}
