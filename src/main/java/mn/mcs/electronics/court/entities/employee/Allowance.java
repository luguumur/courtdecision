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
import mn.mcs.electronics.court.entities.configuration.CommandSubject;
import mn.mcs.electronics.court.util.UUIDUtil;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="allowance")
public class Allowance extends BaseObject {

	private static final long serialVersionUID = 1437889591200654434L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
		
	@ManyToOne
	@JoinColumn(name="allowance_type")
	private  AllowanceType allowanceType;

	@Lob
	@Column(name="cause")
	private String cause;
	
	@Column(name="olgoson_ognoo")
	private Date olgosonOgnoo;
	
	@ManyToOne
	@JoinColumn(name="command_subject")
	private CommandSubject commandSubject;
	
	@ManyToOne
	@JoinColumn(name="employee")
	private Employee employee;
	
	@Column(name="allowance_money")
	private Double allowanceMoney;	
	
	@Column(name="tushaal")
	private String tushaalDugaar;
	
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

	public AllowanceType getAllowanceType() {
		return allowanceType;
	}

	public void setAllowanceType(AllowanceType allowanceType) {
		this.allowanceType = allowanceType;
	}
	
	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public Date getOlgosonOgnoo() {
		return olgosonOgnoo;
	}

	public void setOlgosonOgnoo(Date olgosonOgnoo) {
		this.olgosonOgnoo = olgosonOgnoo;
	}

	public CommandSubject getCommandSubject() {
		return commandSubject;
	}

	public void setCommandSubject(CommandSubject commandSubject) {
		this.commandSubject = commandSubject;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Double getAllowanceMoney() {
		return allowanceMoney;
	}

	public void setAllowanceMoney(Double allowanceMoney) {
		this.allowanceMoney = allowanceMoney;
	}
	
	public String getTushaalDugaar() {
		return tushaalDugaar;
	}

	public void setTushaalDugaar(String tushaalDugaar) {
		this.tushaalDugaar = tushaalDugaar;
	}
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	public Allowance(){
		uuid = UUIDUtil.getUUID();
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof Allowance)) {
			return false;
		}
		
		final Allowance e = (Allowance) o;

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
