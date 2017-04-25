/****************** Shiidver guitsetgeh *************************/
package mn.mcs.electronics.court.entities.employee;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.entities.configuration.CommandSubject;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "sahilga")
public class Sahilga extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6818598629077519670L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;

	@ManyToOne
	@JoinColumn(name = "sahilga_shiitgel")
	private SahilgaShiitgel sahilgaShiitgel;

	@ManyToOne
	@JoinColumn(name = "sahilga_turul")
	private SahilgaTurul sahilgaTurul;
	
	@Column(name = "sahilga_busad_turul")
	private String sahilgaBusadTurul;
	
	@Column(name = "sahilga_busad_shiitgel")
	private String sahilgaBusadShiitgel;

	@Lob
	@Column(name = "cause")
	private String cause;

	@Column(name = "shiitgel_avagdsan_ognoo")
	private Date shiitgelAvagdsanOgnoo;

	@Column(name = "shiitgel_duusah_ognoo")
	private Date shiitgelDuusahOgnoo;

	@Column(name = "avagdsan_shiitgel_dugaar")
	private String avagdsanShiitgelDugaar;

	@Column(name = "arilgasan_tushaal_ognoo")
	private Date arilgasanTushaalOgnoo;
	
	@Column(name = "arilgasan_tushaal_dugaar")
	private String arilgasanTushaalDugaar;

	@ManyToOne
	@JoinColumn(name = "command_subject")
	private CommandSubject commandSubject;

	@ManyToOne
	@JoinColumn(name = "employee")
	private Employee employee;

	/*@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "employee")
	private Set<EmployeeMilitary> employeeMilitary = new HashSet<EmployeeMilitary>();*/
	
	public Sahilga() {
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

	public SahilgaShiitgel getSahilgaShiitgel() {
		return sahilgaShiitgel;
	}

	public void setSahilgaShiitgel(SahilgaShiitgel sahilgaShiitgel) {
		this.sahilgaShiitgel = sahilgaShiitgel;
	}

	public SahilgaTurul getSahilgaTurul() {
		return sahilgaTurul;
	}

	public void setSahilgaTurul(SahilgaTurul sahilgaTurul) {
		this.sahilgaTurul = sahilgaTurul;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public Date getShiitgelAvagdsanOgnoo() {
		return shiitgelAvagdsanOgnoo;
	}

	public void setShiitgelAvagdsanOgnoo(Date shiitgelAvagdsanOgnoo) {
		this.shiitgelAvagdsanOgnoo = shiitgelAvagdsanOgnoo;
	}

	public String getAvagdsanShiitgelDugaar() {
		return avagdsanShiitgelDugaar;
	}

	public void setAvagdsanShiitgelDugaar(String avagdsanShiitgelDugaar) {
		this.avagdsanShiitgelDugaar = avagdsanShiitgelDugaar;
	}

	public Date getArilgasanTushaalOgnoo() {
		return arilgasanTushaalOgnoo;
	}

	public void setArilgasanTushaalOgnoo(Date arilgasanTushaalOgnoo) {
		this.arilgasanTushaalOgnoo = arilgasanTushaalOgnoo;
	}

	public String getArilgasanTushaalDugaar() {
		return arilgasanTushaalDugaar;
	}

	public void setArilgasanTushaalDugaar(String arilgasanTushaalDugaar) {
		this.arilgasanTushaalDugaar = arilgasanTushaalDugaar;
	}

	public CommandSubject getCommandSubject() {
		return commandSubject;
	}

	public void setCommandSubject(CommandSubject commandSubject) {
		this.commandSubject = commandSubject;
	}

	public String getSahilgaBusadTurul() {
		return sahilgaBusadTurul;
	}

	public void setSahilgaBusadTurul(String sahilgaBusadTurul) {
		this.sahilgaBusadTurul = sahilgaBusadTurul;
	}

	public String getSahilgaBusadShiitgel() {
		return sahilgaBusadShiitgel;
	}

	public void setSahilgaBusadShiitgel(String sahilgaBusadShiitgel) {
		this.sahilgaBusadShiitgel = sahilgaBusadShiitgel;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getShiitgelDuusahOgnoo() {
		return shiitgelDuusahOgnoo;
	}

	public void setShiitgelDuusahOgnoo(Date shiitgelDuusahOgnoo) {
		this.shiitgelDuusahOgnoo = shiitgelDuusahOgnoo;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;

		if (o == null)
			return false;

		if (!(o instanceof Sahilga)) {
			return false;
		}

		final Sahilga e = (Sahilga) o;

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

	/*public void setEmployeeMilitary(Set<EmployeeMilitary> employeeMilitary) {
		this.employeeMilitary = employeeMilitary;
	}

	public Set<EmployeeMilitary> getEmployeeMilitary() {
		return employeeMilitary;
	}*/

}
