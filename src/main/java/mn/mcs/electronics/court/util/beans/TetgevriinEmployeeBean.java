/* 
 * File Name: 		TetgevriinEmployeeBean.java
 * 
 * Created By: 		bolorchimeg.g
 * Created Date: 	Mar 13, 2013
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Mar 13, 2013 			    bolorchimeg.g					Шинээр үүсгэв.
 * 
 * -----------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2013 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */
package mn.mcs.electronics.court.util.beans;

import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.enums.Gender;

/**
 * @author bolorchimeg.g
 *
 */
public class TetgevriinEmployeeBean {

	/*-------------------------------------------
	 |    I N S T A N C E   V A R I A B L E S    |
	 ============================================*/
		
	private Long id;
	
	private String firstname;
	
	private String lastname;
	
	private String registerNo;
	
	private Gender gender;

	private Long age;
	
	private String ajillasanHugatsaa;

	private String hetersenHonog;

	private String appointment;
	
	private String baiguullaga;

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

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public String getAjillasanHugatsaa() {
		return ajillasanHugatsaa;
	}

	public void setAjillasanHugatsaa(String ajillasanHugatsaa) {
		this.ajillasanHugatsaa = ajillasanHugatsaa;
	}

	public String getHetersenHonog() {
		return hetersenHonog;
	}

	public void setHetersenHonog(String hetersenHonog) {
		this.hetersenHonog = hetersenHonog;
	}

	public String getAppointment() {
		return appointment;
	}

	public void setAppointment(String appointment) {
		this.appointment = appointment;
	}

	public String getBaiguullaga() {
		return baiguullaga;
	}

	public void setBaiguullaga(String baiguullaga) {
		this.baiguullaga = baiguullaga;
	}
}
