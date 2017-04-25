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

import mn.mcs.electronics.court.entities.configuration.Appointment;
import mn.mcs.electronics.court.entities.organization.Organization;

public class OpenPositionListSearchBean {
	
	private Organization organization;
	
	private Appointment appointment;
	
	private Integer establishment;

	/* getter, setter */
	
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public Integer getEstablishment() {
		return establishment;
	}

	public void setEstablishment(Integer establishment) {
		this.establishment = establishment;
	}

}
