/* 
 * File Name: 		ShagnuulahEmployeeBean.java
 * 
 * Created By: 		bolorchimeg.g
 * Created Date: 	Mar 15, 2013
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

import java.util.Date;

import mn.mcs.electronics.court.enums.Gender;

/**
 * @author bolorchimeg.g
 *
 */
public class ShagnuulahEmployeeBean {

	/*-------------------------------------------
	 |    I N S T A N C E   V A R I A B L E S    |
	 ============================================*/
		
	private Long id;
	
	private String baiguullaga;
	
	private String heltes;
	
	private String firstname;
	
	private String lastname;
	
	private String registerNo;
	
	private Gender gender;

	private Date lastAwardedDate;
	
	private String hetersenHonog;



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

	public Date getLastAwardedDate() {
		return lastAwardedDate;
	}

	public void setLastAwardedDate(Date lastAwardedDate) {
		this.lastAwardedDate = lastAwardedDate;
	}

	public String getHetersenHonog() {
		return hetersenHonog;
	}

	public void setHetersenHonog(String hetersenHonog) {
		this.hetersenHonog = hetersenHonog;
	}
}
