package mn.mcs.electronics.court.entities.configuration.salary;

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
import mn.mcs.electronics.court.entities.employee.AdditionalSalary;
import mn.mcs.electronics.court.enums.AdditionalSalaryCategory;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="additional_salary_type")
public class AdditionalSalaryType extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7760278026118668329L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;

	@Column(name="name")
	private String name;
	
	@Column(name="percent")
	private Double percent;
	
	@Column(name="amount")
	private Double amount;
	
	@Column(name="category")
	private AdditionalSalaryCategory category;

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "additionalSalaryType")
	private Set<AdditionalSalary> salary = new HashSet<AdditionalSalary>();
	
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
	
	public AdditionalSalaryCategory getCategory() {
		return category;
	}

	public void setCategory(AdditionalSalaryCategory category) {
		this.category = category;
	}
	
	public Set<AdditionalSalary> getSalary() {
		return salary;
	}

	public void setSalary(Set<AdditionalSalary> salary) {
		this.salary = salary;
	}
	
	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public AdditionalSalaryType(){
		uuid = UUIDUtil.getUUID();
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof AdditionalSalaryType)) {
			return false;
		}
		
		final AdditionalSalaryType e = (AdditionalSalaryType) o;

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
	