/* 
 * File Name: 		AlertEmployeeSalaryBean.java
 * 
 * Created By: 		maralerdene.t
 * Created Date: 	Mar 18, 2013
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Mar 18, 2013 			            maralerdene.t					    	Шинээр үүсгэв.
 * 
 * -----------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2013 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */
package mn.mcs.electronics.court.util.beans;

import java.util.Date;

import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.enums.Gender;

/**
 * @author maralerdene.t
 *
 */
public class AlertEmployeeSalaryBean {

	/*-------------------------------------------
	 |    I N S T A N C E   V A R I A B L E S    |
	 ============================================*/
	
	private Long id;
	
	private String firstname;
	
	private String lastname;
	
	private Employee employee;
	
	private Gender gender;
	
	private String odoogiinShatlal;
	
	private Date odoogiinShatlalOgnoo;
	
	private Date duusahOgnoo;

	private String hetersenHonog;
	
	private String tsalinSuljee;
	
	/*-------------------------------------------
	 |  G E T T E R S/ S E T T E R S             |
	 ============================================*/
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getOdoogiinShatlal() {
		return odoogiinShatlal;
	}

	public void setOdoogiinShatlal(String odoogiinShatlal) {
		this.odoogiinShatlal = odoogiinShatlal;
	}

	public Date getOdoogiinShatlalOgnoo() {
		return odoogiinShatlalOgnoo;
	}

	public void setOdoogiinShatlalOgnoo(Date odoogiinShatlalOgnoo) {
		this.odoogiinShatlalOgnoo = odoogiinShatlalOgnoo;
	}

	public Date getDuusahOgnoo() {
		return duusahOgnoo;
	}

	public void setDuusahOgnoo(Date duusahOgnoo) {
		this.duusahOgnoo = duusahOgnoo;
	}

	public String getHetersenHonog() {
		return hetersenHonog;
	}

	public void setHetersenHonog(String hetersenHonog) {
		this.hetersenHonog = hetersenHonog;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setTsalinSuljee(String tsalinSuljee) {
		this.tsalinSuljee = tsalinSuljee;
	}

	public String getTsalinSuljee() {
		return tsalinSuljee;
	}
}
