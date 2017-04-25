/* 
 * File Name: 		PageAdvertisements.java
 * 
 * Created By: 		G.Bileg-Ochir
 * Created Date: 	Aug 7, 2012
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Aug 7, 2012 1.0.0 			G.Bileg-Ochir					Шинээр үүсгэв.
 * 
 * -----------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2011 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */
package mn.mcs.electronics.court.pages.organization;

import java.text.SimpleDateFormat;
import java.util.List;

import mn.mcs.electronics.court.components.CompEmp;
import mn.mcs.electronics.court.components.Layout;
import mn.mcs.electronics.court.components.LayoutEmployee;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.WorkedYear;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.pages.employee.PageEmployeeDetail;
import mn.mcs.electronics.court.util.Constants;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageWorkedYearEmployees {

	@Inject
	private CoreDAO dao;
	
	@InjectPage
	private PageEmployeeDetail pageEmployeeDetail;
	
	@Property
	@Persist
	private List<Employee> listEmployee;
	
	@Property
	@Persist
	private List<Employee> listEmployee1;
	
	@Property
	@Persist
	private Employee valueEmployee;
	
	@Inject
	@Property
	@Path("context:/images/b_drop.png")
	private Asset deleteIcon;
	
	@Inject
	@Property
	@Path("context:/images/b_edit.png")
	private Asset editIcon;
	
	private static final String GRID_ROW_CSS = "myGrid";
	
	@Inject
	@Path("context:/css/index.css")
	private Asset styles;
	
	@Property
	@Persist
	private WorkedYear year;
	
	@Persist
	private boolean test;
	
	private static String activeTab;
	
	private static String activeSubTab;
	
	private SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
	
	void beginRender(){
		if(!dao.getListWorkedYear().isEmpty())
			year = dao.getListWorkedYear().get(0);
		
		if(!dao.getListWorkedYear().isEmpty()){
			Long l=0l;
			l=year.getMajorWorkedYear()*365;
		listEmployee = dao.getMajorWorkedYearEmployee(l);
		}
		
		if(!dao.getListWorkedYear().isEmpty()){
			Long l=0l;
			l=year.getStateWorkedYear()*365;
			listEmployee1 = dao.getStateWorkedYearEmployee(l);
		}
		
		if(activeTab==null)
			activeTab = "detail";
		
		if(activeSubTab==null)
			activeSubTab = "personal";
	}
	
	Object onActionFromEdit(Employee employee){
		this.valueEmployee = employee;
		activeTab = "detail";
		activeSubTab = "personal";
		pageEmployeeDetail.setViewEmployee(true);
		LayoutEmployee.setActiveTab(null);
		LayoutEmployee.setActiveSubTab(null);
		LayoutEmployee.setSwitchBlock(1);
		pageEmployeeDetail.setEmployee(valueEmployee);
		pageEmployeeDetail.setIsView(false);
		return pageEmployeeDetail;
	}
	
	Object onActionFromView(Employee employee){
		this.valueEmployee = employee;
		activeTab = "detail";
		activeSubTab = "personal";
		LayoutEmployee.setActiveTab(null);
		LayoutEmployee.setActiveSubTab(null);
		LayoutEmployee.setSwitchBlock(1);
		pageEmployeeDetail.setEmployee(valueEmployee);
		pageEmployeeDetail.setIsView(false);
		pageEmployeeDetail.setViewEmployee(false);
		return pageEmployeeDetail;
	}
	
	Object onActionFromEditStateWorked(Employee employee){
		this.valueEmployee = employee;
		activeTab = "detail";
		activeSubTab = "personal";
		pageEmployeeDetail.setViewEmployee(true);
		LayoutEmployee.setActiveTab(null);
		LayoutEmployee.setActiveSubTab(null);
		LayoutEmployee.setSwitchBlock(1);
		pageEmployeeDetail.setEmployee(valueEmployee);
		pageEmployeeDetail.setIsView(false);
		return pageEmployeeDetail;
	}
	
	Object onActionFromViewStateWorked(Employee employee){
		this.valueEmployee = employee;
		activeTab = "detail";
		activeSubTab = "personal";
		LayoutEmployee.setActiveTab(null);
		LayoutEmployee.setActiveSubTab(null);
		LayoutEmployee.setSwitchBlock(1);
		pageEmployeeDetail.setEmployee(valueEmployee);
		pageEmployeeDetail.setIsView(false);
		pageEmployeeDetail.setViewEmployee(false);
		return pageEmployeeDetail;
	}
	
	/* getter, setter */
	public Asset getStyles() {
		return styles;
	}
	
	public String getGridRowCSS() {
		return GRID_ROW_CSS;
	}
	
	
	public String getListSize(){
		if(listEmployee == null || listEmployee.size() == 0)
			return "0";
		
		return listEmployee.size()+"";
	}
	
	public Integer getNumber(){
		return listEmployee.indexOf(valueEmployee)+1;
	}
	
	public Integer getNumberState(){
		return listEmployee1.indexOf(valueEmployee)+1;
	}
	
	public String getAppointmentName(){
		if(valueEmployee==null||valueEmployee.getAppointment()==null)
			return "";
		
		return valueEmployee.getAppointment().getAppointmentName();
	}
	
	public String getFirstName(){
		if(valueEmployee==null||valueEmployee.getFirstName()==null)
			return "";
		
		return valueEmployee.getFirstName();
	}

	public boolean isTest() {
		return test;
	}

	public void setTest(boolean test) {
		this.test = test;
	}
	
	public boolean isMajorWorked(){
		if(test==true)
			return true;
		else
			return false;
	}
	
	public boolean isStateworked(){
		if(test==false)
			return true;
		else
			return false;
	}
	
}
