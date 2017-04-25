/* 
 * File Name: 		EmployeeCardExperienceBean.java
 * 
 * Created By: 		Jargalsuren.S
 * Created Date: 	April 1, 2013
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * April 1, 2013 			    Jargalsuren.S					Шинээр үүсгэв.
 * 
 * -----------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2013 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */
package mn.mcs.electronics.court.util.beans;

import java.util.Date;

import mn.mcs.electronics.court.entities.configuration.UtTzTtTuLevel;
import mn.mcs.electronics.court.entities.configuration.salary.TuriinZahirgaaSalaryNetwork;
import mn.mcs.electronics.court.entities.employee.Employee;

/**
 * @author Jargalsuren.S
 *
 */
public class EmployeeCardSalaryBean {

	/*-------------------------------------------
	 |    I N S T A N C E   V A R I A B L E S    |
	 ============================================*/
	private Date olgosonOgnoo;
	
	private String organization;
	
	private String undsenTsalin;
	
	private TuriinZahirgaaSalaryNetwork salaryScale;
	
	private String TTAddition;
	
	private String TAAddition;

	private String GAAddition;
	
	private String otherAddition;
	
	private Date togtoosonOgnoo;
	
	private String togtoosonMergejilten;
	
	private String hyanasanDarga;
	
	private Employee employee;
	
	private UtTzTtTuLevel level;
	
	/*-------------------------------------------
	 |  G E T T E R S/ S E T T E R S             |
	 ============================================*/
	public Date getOlgosonOgnoo() {
		return olgosonOgnoo;
	}

	public void setOlgosonOgnoo(Date olgosonOgnoo) {
		this.olgosonOgnoo = olgosonOgnoo;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getUndsenTsalin() {
		return undsenTsalin;
	}

	public void setUndsenTsalin(String undsenTsalin) {
		this.undsenTsalin = undsenTsalin;
	}

	public TuriinZahirgaaSalaryNetwork getSalaryScale() {
		return salaryScale;
	}

	public void setSalaryScale(TuriinZahirgaaSalaryNetwork salaryScale) {
		this.salaryScale = salaryScale;
	}

	public String getTTAddition() {
		return TTAddition;
	}

	public void setTTAddition(String tTAddition) {
		TTAddition = tTAddition;
	}

	public String getTAAddition() {
		return TAAddition;
	}

	public void setTAAddition(String tAAddition) {
		TAAddition = tAAddition;
	}

	public String getGAAddition() {
		return GAAddition;
	}

	public void setGAAddition(String gAAddition) {
		GAAddition = gAAddition;
	}

	public String getOtherAddition() {
		return otherAddition;
	}

	public void setOtherAddition(String otherAddition) {
		this.otherAddition = otherAddition;
	}

	public Date getTogtoosonOgnoo() {
		return togtoosonOgnoo;
	}

	public void setTogtoosonOgnoo(Date togtoosonOgnoo) {
		this.togtoosonOgnoo = togtoosonOgnoo;
	}

	public String getTogtoosonMergejilten() {
		return togtoosonMergejilten;
	}

	public void setTogtoosonMergejilten(String togtoosonMergejilten) {
		this.togtoosonMergejilten = togtoosonMergejilten;
	}

	public String getHyanasanDarga() {
		return hyanasanDarga;
	}

	public void setHyanasanDarga(String hyanasanDarga) {
		this.hyanasanDarga = hyanasanDarga;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setLevel(UtTzTtTuLevel level) {
		this.level = level;
	}

	public UtTzTtTuLevel getLevel() {
		return level;
	}	
}
