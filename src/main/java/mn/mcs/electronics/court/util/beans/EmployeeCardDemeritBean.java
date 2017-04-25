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
import mn.mcs.electronics.court.entities.employee.SahilgaTurul;

/**
 * @author Jargalsuren.S
 *
 */
public class EmployeeCardDemeritBean {

	/*-------------------------------------------
	 |    I N S T A N C E   V A R I A B L E S    |
	 ============================================*/
	private String cause;
	
	private SahilgaTurul demeritType;
	
	private String avagdsanShiitgelDugaar;
	
	private Date arilgasanTushaalOgnoo;
	
	private String arilgasanTushaalDugaar;

	/*-------------------------------------------
	 |  G E T T E R S/ S E T T E R S             |
	 ============================================*/

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public SahilgaTurul getDemeritType() {
		return demeritType;
	}

	public void setDemeritType(SahilgaTurul demeritType) {
		this.demeritType = demeritType;
	}

	public String getAvagdsanShiitgelDugaar() {
		return avagdsanShiitgelDugaar;
	}

	public void setAvagdsanShiitgelDugaar(String avagdsanShiitgelDugaar) {
		this.avagdsanShiitgelDugaar = avagdsanShiitgelDugaar;
	}

	public Date getArilgasanTushaalOgnoo() {
		return arilgasanTushaalOgnoo;
	}

	public void setArilgasanTushaalOgnoo(Date arilgasanTushaalOgnoo) {
		this.arilgasanTushaalOgnoo = arilgasanTushaalOgnoo;
	}

	public String getArilgasanTushaalDugaar() {
		return arilgasanTushaalDugaar;
	}

	public void setArilgasanTushaalDugaar(String arilgasanTushaalDugaar) {
		this.arilgasanTushaalDugaar = arilgasanTushaalDugaar;
	}	
}
