package mn.mcs.electronics.court.entities.configuration;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.entities.employee.Establishment;
import mn.mcs.electronics.court.enums.GetWage;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="salary_network")
public class SalaryNetwork extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4844879539874770640L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;

	@Column(name="salary_network_name")
	private String salaryNetworkName;

	@Column(name="get_wage")
	private GetWage getWage;
	
	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "salaryNetwork")
	private Set<Establishment> establishment = new HashSet<Establishment>();
	
	public void setSalaryNetworkName(String salaryNetworkName) {
		this.salaryNetworkName = salaryNetworkName;
	}

	public String getSalaryNetworkName() {
		return salaryNetworkName;
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

	public GetWage getGetWage() {
		return getWage;
	}

	public void setGetWage(GetWage getWage) {
		this.getWage = getWage;
	}

	public Set<Establishment> getEstablishment() {
		return establishment;
	}

	public void setEstablishment(Set<Establishment> establishment) {
		this.establishment = establishment;
	}

	public SalaryNetwork(){
		uuid = UUIDUtil.getUUID();
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof SalaryNetwork)) {
			return false;
		}
		
		final SalaryNetwork e = (SalaryNetwork) o;

		if (e.getId() == null)
			return false;
		
		if (this.getId() == null)
			return false;

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
