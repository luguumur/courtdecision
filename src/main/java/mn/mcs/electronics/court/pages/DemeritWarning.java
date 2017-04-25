/* 
 * File Name: 		Warning.java
 * 
 * Created By: 		maralerdene.t
 * Created Date: 	Jan 7, 2013
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Jan 7, 2013 			            maralerdene.t					    	Шинээр үүсгэв.
 * 
 * -----------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2013 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */
package mn.mcs.electronics.court.pages;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.components.CompEmp;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.AlertType;
import mn.mcs.electronics.court.pages.employee.PageEmployeeDetail;
import mn.mcs.electronics.court.util.Constants;
import mn.mcs.electronics.court.util.beans.AlertEmployeeSalaryBean;
import mn.mcs.electronics.court.util.beans.AlertMilitaryRankEmployeeBean;
import mn.mcs.electronics.court.util.beans.DisciplinedEmployeeBean;
import mn.mcs.electronics.court.util.beans.EmployeeLastDemeritBean;
import mn.mcs.electronics.court.util.beans.EmployeeSearchBean;
import mn.mcs.electronics.court.util.beans.ShagnuulahEmployeeBean;
import mn.mcs.electronics.court.util.beans.TetgevriinEmployeeBean;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.EnumSelectModel;

/**
 * @author Jargalsuren.S
 *
 */
public class DemeritWarning {

	/*-------------------------------------------
	 |    I N S T A N C E   V A R I A B L E S    |
	 ============================================*/
	@SessionState
	private LoginState loginState;
	
	@Inject
	private Messages messages;
	
	@Inject
	private CoreDAO dao;
	
	@Inject
	@Path("context:/images/b_edit.png")
	private Asset editIcon;
		
	@Inject
	@Path("context:/images/Alarm-Warning-icon.png")
	private Asset alertIcon;

	@Inject
	@Path("context:/images/demeritWarning.png")
	private Asset demeritWarning;
	
	@InjectPage
	private PageEmployeeDetail employeeDetail;
	
	@Persist
	public static ArrayList<Object> breadCrumb;
	
	@Property
	@Persist
	private Date startDate;

	@Property
	@Persist
	private Date endDate;
	/*
	 * Сүүлд сахилгын шийтгэл авсан ажилчидын жагсаалт
	 * */
	@Persist
	@Property
	private List<EmployeeLastDemeritBean> listEmployee;
	
	@Persist
	@Property
	private EmployeeLastDemeritBean rowEmployee;	
	
	/*-------------------------------------------
	 |             C O N S T A N T S             |
	 ============================================*/
	private SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
	
	/*--------------------------------------------
	 |               M E T H O D S               |
	 ============================================*/
	void beginRender() {
		if(listEmployee == null)
			listEmployee = new ArrayList<EmployeeLastDemeritBean>();
		
		listEmployee = dao.getEmployeeLastDemeritBean(startDate, endDate,new HashSet<Organization>(loginState.getEmployee().getMapEmpOrg()));
	}
	
	Object onActionFromEdit(Long id){
	
		Employee emp = (Employee) dao.getObject(Employee.class, id);
		
		employeeDetail.setEmployee(emp);
		employeeDetail.setIsView(false);
		
		return employeeDetail;
	}
	
	void onSelectedFromSearch() {		
		listEmployee = dao.getEmployeeLastDemeritBean(startDate, endDate,new HashSet<Organization>(loginState.getEmployee().getMapEmpOrg()));
	}
	
	void onActionFromCancel(){
		startDate = null;
		endDate = null;
	}
	/*-------------------------------------------
	 |     S E L E C T I O N  M O D E L S        |
	 ============================================*/
	public String getDateOfDemerit(Date inputDate) {
		if (inputDate == null)
			return "";

		return format.format(inputDate);
	}
	
	public SimpleDateFormat getFormat() {
		return format;
	}
	
	public Asset getEditIcon() {
		return editIcon;
	}

	public void setEditIcon(Asset editIcon) {
		this.editIcon = editIcon;
	}
	
	public Asset getAlertIcon() {
		return alertIcon;
	}

	public Asset getDemeritWarning() {
		return demeritWarning;
	}
	
	public String getNumber(){	
		return (listEmployee.indexOf(rowEmployee)+1) + "";
	}
	
	public String getDateOfDemerit() {
		if (rowEmployee == null || rowEmployee.getShiitgelAvagdsanOgnoo() == null)
			return "";

		return format.format(rowEmployee.getShiitgelAvagdsanOgnoo());
	}

	public String getCommandSubject(){	
		return rowEmployee.getCommandSubject().getSubjectName();
	}
	
	public String getSahilgaShiitgel(){	
		return rowEmployee.getSahilgaShiitgel().getShiitgelName();
	}
	
	public String getSahilgaTurul(){	
		return rowEmployee.getSahilgaTurul().getSahilgaTurulName();
	}
	
	public String getAppointment(){	
		return rowEmployee.getEmployee().getAppointment().getAppointmentName();
	}
	
	public String getFirstName(){	
		return rowEmployee.getEmployee().getLastname().charAt(0) + "." + rowEmployee.getEmployee().getFirstName();
	}
}
