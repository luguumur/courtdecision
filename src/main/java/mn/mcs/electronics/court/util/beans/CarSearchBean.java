package mn.mcs.electronics.court.util.beans;

import mn.mcs.electronics.court.entities.organization.Organization;


public class CarSearchBean {

	private String name;
	
	private Organization organization;
	
	private String carNumber;

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

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	
}
