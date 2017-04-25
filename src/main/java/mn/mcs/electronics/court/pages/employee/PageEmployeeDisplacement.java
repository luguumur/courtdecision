package mn.mcs.electronics.court.pages.employee;

import mn.mcs.electronics.court.components.LayoutEmployee;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.organization.Organization;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;

public class PageEmployeeDisplacement {
	
	@InjectComponent
	private LayoutEmployee layoutEmployee;
	
	@Persist
	private Employee employee;

	@Persist
	private Organization organization;
	
	private Long empID;

	void onActivate(Long id) {
		empID = id;
	}

	Long onPassivate() {
		return empID;
	}
	
	void beginRender(){
		
		if(getEmployee()!=null && getEmployee().getId()!=null)
			layoutEmployee.setValueEmployee(getEmployee());
		
		layoutEmployee.setOrganization(getOrganization());
	}
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	
}

