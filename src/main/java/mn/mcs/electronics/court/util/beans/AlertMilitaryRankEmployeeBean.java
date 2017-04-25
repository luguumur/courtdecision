/* 
 * File Name: 		MilitaryRankEmployeeBean.java
 * 
 * Created By: 		maralerdene.t
 * Created Date: 	Mar 11, 2013
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Mar 11, 2013 			            maralerdene.t					    	Шинээр үүсгэв.
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
public class AlertMilitaryRankEmployeeBean {

	/*-------------------------------------------
	 |    I N S T A N C E   V A R I A B L E S    |
	 ============================================*/

	private Long id;
	
	private String firstname;
	
	private String lastname;
	
	private Employee employee;
	
	private Gender gender;
	
	private String odoogiinTsol;
	
	private Date tsolAvsanOgnoo;
	
	private Date tsolAvahOgnoo;

	private String hetersenHonog;

	private Integer tsolHugatsaa;
	
	private Boolean isTop;
	
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

	public String getOdoogiinTsol() {
		return odoogiinTsol;
	}

	public void setOdoogiinTsol(String odoogiinTsol) {
		this.odoogiinTsol = odoogiinTsol;
	}

	public Date getTsolAvsanOgnoo() {
		return tsolAvsanOgnoo;
	}

	public void setTsolAvsanOgnoo(Date tsolAvsanOgnoo) {
		this.tsolAvsanOgnoo = tsolAvsanOgnoo;
	}

	public Date getTsolAvahOgnoo() {
		return tsolAvahOgnoo;
	}

	public void setTsolAvahOgnoo(Date tsolAvahOgnoo) {
		this.tsolAvahOgnoo = tsolAvahOgnoo;
	}

	public String getHetersenHonog() {
		return hetersenHonog;
	}

	public void setHetersenHonog(String hetersenHonog) {
		this.hetersenHonog = hetersenHonog;
	}

	public Integer getTsolHugatsaa() {
		return tsolHugatsaa;
	}

	public void setTsolHugatsaa(Integer tsolHugatsaa) {
		this.tsolHugatsaa = tsolHugatsaa;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Employee getEmployee() {
		return employee;
	}

	public Boolean getIsTop() {
		return isTop;
	}

	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

}
