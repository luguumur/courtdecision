package mn.mcs.electronics.court.util.beans;

import mn.mcs.electronics.court.entities.organization.Organization;


public class EquipmentSearchBean {

	private String name;
	
	private String seriNumber;
	
	private Organization organization;

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

	public String getSeriNumber() {
		return seriNumber;
	}

	public void setSeriNumber(String seriNumber) {
		this.seriNumber = seriNumber;
	}
	
}
