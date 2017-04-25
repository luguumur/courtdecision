package mn.mcs.electronics.court.util.beans;

import mn.mcs.electronics.court.entities.configuration.City;
import mn.mcs.electronics.court.entities.organization.OrganizationType;

public class OrganizationSearchBean {

	private String name;
	
	private String register;
	
	private String shortName;
	
	private City cityProvince;
	
	private OrganizationType organizationType;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegister() {
		return register;
	}

	public void setRegister(String register) {
		this.register = register;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public City getCityProvince() {
		return cityProvince;
	}

	public void setCityProvince(City cityProvince) {
		this.cityProvince = cityProvince;
	}

	public OrganizationType getOrganizationType() {
		return organizationType;
	}

	public void setOrganizationType(OrganizationType organizationType) {
		this.organizationType = organizationType;
	}
	
}
