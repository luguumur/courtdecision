/* 
 * Package Name: mn.mcs.electronics.court.util.beans
 * File Name: 	 OpenPositionSearchBean.java
 * 
 * Created By: 	 G.Bileg-Ochir
 * Created Date: Aug 10, 2012
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Aug 10, 2012 					G.Bileg-Ochir					Шинээр үүсгэв.
 * ------------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2012 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */
package mn.mcs.electronics.court.util.beans;

import java.util.Date;

public class OpenPositionSearchBean {
	
	private Date registeredDate;
	
	private Date positionTerm;
	
	private String institutor;
	
	private Integer opNumber;
	
	private Integer opQuantity;


	/* getter, setter */

	public Date getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}

	public Date getPositionTerm() {
		return positionTerm;
	}

	public void setPositionTerm(Date positionTerm) {
		this.positionTerm = positionTerm;
	}

	public String getInstitutor() {
		return institutor;
	}

	public void setInstitutor(String institutor) {
		this.institutor = institutor;
	}

	public Integer getOpNumber() {
		return opNumber;
	}

	public void setOpNumber(Integer opNumber) {
		this.opNumber = opNumber;
	}

	public Integer getOpQuantity() {
		return opQuantity;
	}

	public void setOpQuantity(Integer opQuantity) {
		this.opQuantity = opQuantity;
	}

}
