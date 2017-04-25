package mn.mcs.electronics.court.pages.employee;

import mn.mcs.electronics.court.components.LayoutEmployee;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.QuitJob;
import mn.mcs.electronics.court.enums.EmployeeCurrentStatus;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.EnumSelectModel;

public class PageEmployeeQuitJob {
	
	@Inject
	private Messages messages;
	
	@Inject
	private CoreDAO dao;
	
	@InjectComponent
	private LayoutEmployee layoutEmployee;
	
	@Persist
	private Employee employee;
	
	@Property
	@Persist
	private QuitJob quitJob;

	void beginRender(){
		layoutEmployee.setValueEmployee(getEmployee());
		
		if(quitJob==null)
			quitJob=new QuitJob();
	
	}
	
	/*selection model*/
	
	public SelectModel getQuitTypeSelectionModel(){
		return new EnumSelectModel(EmployeeCurrentStatus.class,messages);
	}
	
	/*getter, setter*/
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}


	
}
