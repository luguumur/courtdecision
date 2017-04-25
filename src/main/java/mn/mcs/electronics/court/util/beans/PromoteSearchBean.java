package mn.mcs.electronics.court.util.beans;

import mn.mcs.electronics.court.entities.configuration.City;
import mn.mcs.electronics.court.entities.configuration.DistrictSum;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.organization.Organization;

public class PromoteSearchBean {
	
	private Employee employee;
	
	private Organization promoteOrganization;
	
	private City cityProvince; 
	
	private DistrictSum districtSum;

	/* getter, setter */


	public City getCityProvince() {
		return cityProvince;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public void setCityProvince(City cityProvince) {
		this.cityProvince = cityProvince;
	}

	public DistrictSum getDistrictSum() {
		return districtSum;
	}

	public void setDistrictSum(DistrictSum districtSum) {
		this.districtSum = districtSum;
	}

	public Organization getPromoteOrganization() {
		return promoteOrganization;
	}

	public void setPromoteOrganization(Organization promoteOrganization) {
		this.promoteOrganization = promoteOrganization;
	}

}
