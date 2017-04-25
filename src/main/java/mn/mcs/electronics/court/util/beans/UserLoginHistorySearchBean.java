package mn.mcs.electronics.court.util.beans;

import java.util.Date;

import mn.mcs.electronics.court.entities.User;
import mn.mcs.electronics.court.entities.organization.Organization;


public class UserLoginHistorySearchBean {

	public Date dateFrom;
	
	public Date dateTo;
	
	public Organization organization;
	
	public User user;

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}

