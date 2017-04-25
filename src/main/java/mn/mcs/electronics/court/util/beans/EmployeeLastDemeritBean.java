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

import mn.mcs.electronics.court.entities.configuration.Appointment;
import mn.mcs.electronics.court.entities.configuration.CommandSubject;
import mn.mcs.electronics.court.entities.configuration.MilitaryRank;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.SahilgaShiitgel;
import mn.mcs.electronics.court.entities.employee.SahilgaTurul;

/**
 * @author Jargalsuren.S
 *
 */
public class EmployeeLastDemeritBean {

	/*-------------------------------------------
	 |    I N S T A N C E   V A R I A B L E S    |
	 ============================================*/
	private CommandSubject commandSubject;
	
	private SahilgaShiitgel sahilgaShiitgel;
	
	private SahilgaTurul sahilgaTurul;
	
	private String cause;
	
	private Date shiitgelAvagdsanOgnoo;
	
	private Employee employee;
	
	//private Employee employee;

	/*-------------------------------------------
	 |  G E T T E R S/ S E T T E R S             |
	 ============================================*/
	
	public CommandSubject getCommandSubject() {
		return commandSubject;
	}

	public void setCommandSubject(CommandSubject commandSubject) {
		this.commandSubject = commandSubject;
	}

	public SahilgaShiitgel getSahilgaShiitgel() {
		return sahilgaShiitgel;
	}

	public void setSahilgaShiitgel(SahilgaShiitgel sahilgaShiitgel) {
		this.sahilgaShiitgel = sahilgaShiitgel;
	}

	public SahilgaTurul getSahilgaTurul() {
		return sahilgaTurul;
	}

	public void setSahilgaTurul(SahilgaTurul sahilgaTurul) {
		this.sahilgaTurul = sahilgaTurul;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public Date getShiitgelAvagdsanOgnoo() {
		return shiitgelAvagdsanOgnoo;
	}

	public void setShiitgelAvagdsanOgnoo(Date shiitgelAvagdsanOgnoo) {
		this.shiitgelAvagdsanOgnoo = shiitgelAvagdsanOgnoo;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Employee getEmployee() {
		return employee;
	}
}
