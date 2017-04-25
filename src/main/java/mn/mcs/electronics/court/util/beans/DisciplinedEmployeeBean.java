/* 
 * File Name: 		DisciplinedEmployeeBean.java
 * 
 * Created By: 		maralerdene.t
 * Created Date: 	Jan 15, 2013
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Jan 15, 2013 			            maralerdene.t					    	Шинээр үүсгэв.
 * 
 * -----------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2013 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */
package mn.mcs.electronics.court.util.beans;

import java.util.Date;

import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.entities.other.AES;
import mn.mcs.electronics.court.enums.Gender;

/**
 * @author maralerdene.t
 *
 */
public class DisciplinedEmployeeBean {

	/*-------------------------------------------
	 |    I N S T A N C E   V A R I A B L E S    |
	 ============================================*/
		
	private Long id;
	
	private String firstname;
	
	private String lastname;
	
	private String registerNo;
	
	private Gender gender;
	
	private String shiitgelName;
	
	private Date shiitgelAvagdsanOgnoo;
	
	private Date shiitgelDuusahOgnoo;

	private String hetersenHonog;
	
	private String org;
	
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
	String regDec = null;
		
		if(registerNo!=null){
			try {
				regDec = AES.decrypt(registerNo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return regDec;
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

	public String getShiitgelName() {
		return shiitgelName;
	}

	public void setShiitgelName(String shiitgelName) {
		this.shiitgelName = shiitgelName;
	}

	public Date getShiitgelAvagdsanOgnoo() {
		return shiitgelAvagdsanOgnoo;
	}

	public void setShiitgelAvagdsanOgnoo(Date shiitgelAvagdsanOgnoo) {
		this.shiitgelAvagdsanOgnoo = shiitgelAvagdsanOgnoo;
	}

	public Date getShiitgelDuusahOgnoo() {
		return shiitgelDuusahOgnoo;
	}

	public void setShiitgelDuusahOgnoo(Date shiitgelDuusahOgnoo) {
		this.shiitgelDuusahOgnoo = shiitgelDuusahOgnoo;
	}

	public String getHetersenHonog() {
		return hetersenHonog;
	}

	public void setHetersenHonog(String hetersenHonog) {
		this.hetersenHonog = hetersenHonog;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getOrg() {
		return org;
	}


	
}
