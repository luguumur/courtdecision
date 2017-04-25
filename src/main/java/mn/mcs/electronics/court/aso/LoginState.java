/* 
 * File Name: LoginState.java
 * 
 * Created By: S.Khishigbaatar
 * Created Date: 2011/03/23
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Descripti
 * ------------------------------------------------------------------------------
 * 2011/02/21 1.0.0 			S.Khishigbaatar					Шинээр үүсгэв.
 * ------------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2011 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */
package mn.mcs.electronics.court.aso;

import java.io.Serializable;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.User;
import mn.mcs.electronics.court.entities.configuration.Appointment;
import mn.mcs.electronics.court.entities.employee.Displacement;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.organization.Organization;
import org.apache.tapestry5.ioc.annotations.Inject;

public class LoginState implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CoreDAO dao;
	
//	protected Session session;
	
	private String login;

	private Employee employee;
	
	private Organization organization;
	
	private Appointment appointment;

	private Displacement discplacement;
	
	private User user;
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		employee = dao.getEmployee(login);
		this.login = login;
	}

	public Employee getEmployee() {
		employee = dao.getEmployee(login);
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Organization getOrganization() {
		return getEmployee().getOrganization();
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	
	public String getAppointment() {
		if(getEmployee()==null||getEmployee().getAppointment()==null)
			return "";
		
		return getEmployee().getAppointment().getAppointmentName();
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Displacement getDiscplacement() {
		return discplacement;
	}

	public void setDiscplacement(Displacement discplacement) {
		this.discplacement = discplacement;
	}	
}
