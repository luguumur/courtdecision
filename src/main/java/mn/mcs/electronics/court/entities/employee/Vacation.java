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
import mn.mcs.electronics.court.entities.configuration.CommandSubject;
import mn.mcs.electronics.court.enums.ChuluuTimeType;
import mn.mcs.electronics.court.enums.YesNo;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "vacation")
public class Vacation extends BaseObject {

	private static final long serialVersionUID = 1164521160387323801L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;

	@Column(name = "vacation_cause")
	private String vacationCause;

	@ManyToOne
	@JoinColumn(name = "vacationType")
	private VacationType vacationType;

	@ManyToOne
	@JoinColumn(name = "command_subject")
	private CommandSubject commandSubject;

	@Column(name = "chuluu_olgoson_ognoo")
	private Date chuluuOlgosonOgnoo;

	@Column(name = "chuluu_ehleh_ognoo")
	private Date chuluuEhlehOgnoo;

	@Column(name = "chuluu_duusah_ognoo")
	private Date chuluuDuusahOgnoo;

	@Column(name = "chuluu_time_type")
	private ChuluuTimeType chuluuTimeType;

	@Column(name = "has_salary")
	private YesNo hasSalary;

	@ManyToOne
	@JoinColumn(name = "employee")
	private Employee employee;

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

	public void setVacationCause(String vacationCause) {
		this.vacationCause = vacationCause;
	}

	public String getVacationCause() {
		return vacationCause;
	}

	public VacationType getVacationType() {
		return vacationType;
	}

	public void setVacationType(VacationType vacationType) {
		this.vacationType = vacationType;
	}

	public CommandSubject getCommandSubject() {
		return commandSubject;
	}

	public void setCommandSubject(CommandSubject commandSubject) {
		this.commandSubject = commandSubject;
	}

	public Date getChuluuOlgosonOgnoo() {
		return chuluuOlgosonOgnoo;
	}

	public void setChuluuOlgosonOgnoo(Date chuluuOlgosonOgnoo) {
		this.chuluuOlgosonOgnoo = chuluuOlgosonOgnoo;
	}

	public Date getChuluuEhlehOgnoo() {
		return chuluuEhlehOgnoo;
	}

	public void setChuluuEhlehOgnoo(Date chuluuEhlehOgnoo) {
		this.chuluuEhlehOgnoo = chuluuEhlehOgnoo;
	}

	public Date getChuluuDuusahOgnoo() {
		return chuluuDuusahOgnoo;
	}

	public void setChuluuDuusahOgnoo(Date chuluuDuusahOgnoo) {
		this.chuluuDuusahOgnoo = chuluuDuusahOgnoo;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public ChuluuTimeType getChuluuTimeType() {
		return chuluuTimeType;
	}

	public void setChuluuTimeType(ChuluuTimeType chuluuTimeType) {
		this.chuluuTimeType = chuluuTimeType;
	}

	public YesNo getHasSalary() {
		return hasSalary;
	}

	public void setHasSalary(YesNo hasSalary) {
		this.hasSalary = hasSalary;
	}

	public Vacation() {
		uuid = UUIDUtil.getUUID();
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;

		if (o == null)
			return false;

		if (!(o instanceof Vacation)) {
			return false;
		}

		final Vacation e = (Vacation) o;

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
