package mn.mcs.electronics.court.entities.employee;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.entities.configuration.SalaryScale;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuLevel;
import mn.mcs.electronics.court.enums.Month;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "employee_salary_history")
public class EmployeeSalaryHistory extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4815985846451018537L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;

	@ManyToOne
	@JoinColumn(name = "employee")
	private Employee employee;

	@Column(name = "main")
	private Double main;

	@Column(name = "total")
	private Double total;

	@Column(name = "year")
	private Integer year;

	@Column(name = "month")
	@Enumerated(EnumType.ORDINAL)
	private Month month;

	@ManyToOne
	@JoinColumn(name = "scale")
	private SalaryScale scale;
	
	@ManyToOne
	@JoinColumn(name = "level")
	private UtTzTtTuLevel level;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "salary")
	private List<AdditionalSalary> additionalSalary = new ArrayList<AdditionalSalary>();

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

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Double getMain() {
		return main;
	}

	public void setMain(Double main) {
		this.main = main;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Month getMonth() {
		return month;
	}

	public void setMonth(Month month) {
		this.month = month;
	}

	public UtTzTtTuLevel getLevel() {
		return level;
	}

	public void setLevel(UtTzTtTuLevel level) {
		this.level = level;
	}

	public SalaryScale getScale() {
		return scale;
	}

	public void setScale(SalaryScale scale) {
		this.scale = scale;
	}

	public List<AdditionalSalary> getAdditionalSalary() {
		return additionalSalary;
	}

	public void setAdditionalSalary(List<AdditionalSalary> additionalSalary) {
		this.additionalSalary = additionalSalary;
	}

	public EmployeeSalaryHistory() {
		uuid = UUIDUtil.getUUID();
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;

		if (o == null)
			return false;

		if (!(o instanceof EmployeeSalaryHistory)) {
			return false;
		}

		final EmployeeSalaryHistory e = (EmployeeSalaryHistory) o;

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
		return new ToStringBuilder(this).append("id", this.id)
				.append("uuid", this.uuid).toString();
	}

}
