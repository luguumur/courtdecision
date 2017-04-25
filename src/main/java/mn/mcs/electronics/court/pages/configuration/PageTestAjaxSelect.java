package mn.mcs.electronics.court.pages.configuration;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.model.EmployeeOrgSelectionModel;
import mn.mcs.electronics.court.model.OrganizationSelectionModel;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

public class PageTestAjaxSelect {
	
	static final private String[] ALL_MAKES = new String[] { "Honda", "Toyota" };
	static final private String[] NO_MODELS = new String[] {};
	static final private String[] HONDA_MODELS = new String[] { "Accord", "Civic", "Jazz" };
	static final private String[] TOYOTA_MODELS = new String[] { "Camry", "Corolla" };

	// Screen fields

	@Inject
	private CoreDAO dao;
	
	@Persist
	@Property
	private Organization organization;
	
	@Persist
	@Property
	private Employee employee;

	@Property
	@SuppressWarnings("unused")
	private SelectModel empModels;

	@InjectComponent
	private Zone empOrgZone;


	@Inject
	private Request request;

	// The code

	void setupRender() {

	}
	
	public SelectModel getOrganizationSelectionModel(){
		OrganizationSelectionModel sm = new OrganizationSelectionModel(dao);
		return sm;
	}
	
	public SelectModel getEmployeeOrgSelectionModel(){
		EmployeeOrgSelectionModel sm = new EmployeeOrgSelectionModel(dao,organization);
		return sm;
	}
	
	Object onValueChangedFromOrgClick(Organization org) {
		this.organization = org;

		if (organization == null) {
			empModels =new EmployeeOrgSelectionModel(dao,null);
		}
		else  {
			empModels = new EmployeeOrgSelectionModel(dao,organization);
		}

		return request.isXHR() ? empOrgZone.getBody() : null;
	}
	
}
