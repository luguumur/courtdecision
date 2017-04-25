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
import mn.mcs.electronics.court.entities.configuration.MilitaryRank;

/**
 * @author Jargalsuren.S
 *
 */
public class EmployeeCardDegreeBean {

	/*-------------------------------------------
	 |    I N S T A N C E   V A R I A B L E S    |
	 ============================================*/
	private MilitaryRank degree;
	
	private Date issuedDate;
	
	private String commandNumber;

	/*-------------------------------------------
	 |  G E T T E R S/ S E T T E R S             |
	 ============================================*/
	
	public MilitaryRank getDegree() {
		return degree;
	}

	public void setDegree(MilitaryRank degree) {
		this.degree = degree;
	}

	public Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	public String getCommandNumber() {
		return commandNumber;
	}

	public void setCommandNumber(String commandNumber) {
		this.commandNumber = commandNumber;
	}	
}
