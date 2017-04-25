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

import mn.mcs.electronics.court.entities.configuration.CommandSubject;

/**
 * @author Jargalsuren.S
 *
 */
public class EmployeeCardExperienceBean {

	/*-------------------------------------------
	 |    I N S T A N C E   V A R I A B L E S    |
	 ============================================*/
	private String organization;
	
	private String appointment;
	
	private Date issuedDate;
	
	private Date quitDate;
	
	private Date commandStartDate;
	
	private Date commandEndDate;

	private String commandNumber;
	
	private CommandSubject commandSubject;


	/*-------------------------------------------
	 |  G E T T E R S/ S E T T E R S             |
	 ============================================*/
	
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getAppointment() {
		return appointment;
	}

	public void setAppointment(String appointment) {
		this.appointment = appointment;
	}

	public Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	public Date getQuitDate() {
		return quitDate;
	}

	public void setQuitDate(Date quitDate) {
		this.quitDate = quitDate;
	}

	public Date getCommandStartDate() {
		return commandStartDate;
	}

	public void setCommandStartDate(Date commandStartDate) {
		this.commandStartDate = commandStartDate;
	}

	public Date getCommandEndDate() {
		return commandEndDate;
	}

	public void setCommandEndDate(Date commandEndDate) {
		this.commandEndDate = commandEndDate;
	}

	public String getCommandNumber() {
		return commandNumber;
	}

	public void setCommandNumber(String commandNumber) {
		this.commandNumber = commandNumber;
	}

	public CommandSubject getCommandSubject() {
		return commandSubject;
	}

	public void setCommandSubject(CommandSubject commandSubject) {
		this.commandSubject = commandSubject;
	}	
}
