package mn.mcs.electronics.court.util.beans;

import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.organization.Organization;


public class HouseSearchBean {

	private String name;
	
	private Organization organization;
	
	private Employee owner;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Employee getOwner() {
		return owner;
	}

	public void setOwner(Employee owner) {
		this.owner = owner;
	}
}

