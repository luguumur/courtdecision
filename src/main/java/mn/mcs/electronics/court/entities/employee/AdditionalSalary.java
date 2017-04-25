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
import mn.mcs.electronics.court.entities.configuration.salary.AdditionalSalaryType;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="additional_salary")
public class AdditionalSalary extends BaseObject{

	private static final long serialVersionUID = 1565066361047700114L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	
	@ManyToOne
	@JoinColumn(name="additional_salary")
	private AdditionalSalaryType additionalSalaryType;
	
	@ManyToOne
	@JoinColumn(name="salary")
	private SalaryHistory salary;
	
	@ManyToOne
	@JoinColumn(name="salary_history")
	private EmployeeSalaryHistory salaryHistory;
	
	@Column(name="percent")
	private Double percent;
	
	@Column(name="additional_amount")
	private Double additionalAmount;
	
	@Column(name="date")
	private Date date;
	
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

	public AdditionalSalaryType getAdditionalSalaryType() {
		return additionalSalaryType;
	}

	public void setAdditionalSalaryType(AdditionalSalaryType additionalSalaryType) {
		this.additionalSalaryType = additionalSalaryType;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Double getAdditionalAmount() {
		return additionalAmount;
	}

	public void setAdditionalAmount(Double additionalAmount) {
		this.additionalAmount = additionalAmount;
	}

	public SalaryHistory getSalary() {
		return salary;
	}

	public void setSalary(SalaryHistory salary) {
		this.salary = salary;
	}

	public EmployeeSalaryHistory getSalaryHistory() {
		return salaryHistory;
	}

	public void setSalaryHistory(EmployeeSalaryHistory salaryHistory) {
		this.salaryHistory = salaryHistory;
	}

	public AdditionalSalary(){
		uuid = UUIDUtil.getUUID();
	}

	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof AdditionalSalary)) {
			return false;
		}
		
		final AdditionalSalary e = (AdditionalSalary) o;

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
