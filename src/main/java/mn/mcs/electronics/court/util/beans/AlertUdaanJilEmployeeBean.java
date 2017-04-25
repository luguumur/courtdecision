/* 
 * File Name: 		UdaanJilEmployeeBean.java
 * 
 * Created By: 		Jargalsuren.S
 * Created Date: 	Apr 8, 2013
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Apr 8, 2013   			    Jargalsuren.S					Шинээр үүсгэв. 
 * -----------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2013 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */
package mn.mcs.electronics.court.util.beans;

import mn.mcs.electronics.court.enums.Gender;

/**
 * @author bolorchimeg.g
 *
 */
public class AlertUdaanJilEmployeeBean {

	/*-------------------------------------------
	 |    I N S T A N C E   V A R I A B L E S    |
	 ============================================*/
		
	private Long id;
	
	private String baiguullaga;
	
	private String heltes;
	
	private String firstname;
	
	private String lastname;
	
	private String appointment;
	
	private Gender gender;

	private String workedYear;

	/*-------------------------------------------
	 |  G E T T E R S/ S E T T E R S             |
	 ============================================*/
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBaiguullaga() {
		return baiguullaga;
	}

	public void setBaiguullaga(String baiguullaga) {
		this.baiguullaga = baiguullaga;
	}

	public String getHeltes() {
		return heltes;
	}

	public void setHeltes(String heltes) {
		this.heltes = heltes;
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

	public void setAppointment(String appointment) {
		this.appointment = appointment;
	}

	public String getAppointment() {
		return appointment;
	}

	public void setWorkedYear(String workedYear) {
		this.workedYear = workedYear;
	}

	public String getWorkedYear() {
		return workedYear;
	}
}
