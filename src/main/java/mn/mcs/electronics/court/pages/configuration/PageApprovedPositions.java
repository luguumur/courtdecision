package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.Appointment;
import mn.mcs.electronics.court.entities.configuration.ApprovedPositions;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.model.AppointmentOrgSelectionModel;
import mn.mcs.electronics.court.util.beans.OrganizationSearchBean;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageApprovedPositions {

	@Inject
	private CoreDAO dao;
	
	@Inject
	private Messages messages;
	
	@Inject
	@Property
	@Path("context:/images/b_edit.png")
	private Asset editIcon;

	@Property
	@Inject
	@Path("context:/images/b_drop.png")
	private Asset deleteIcon;
	
	@Property
	@Persist
	private OrganizationSearchBean bean;
	
	@Property
	@Persist
	private Organization organization;
	
	@Property
	private List<Organization> listOrganization;
	
	@Property
	private Organization valueOrganization;
	
	@Property
	@Persist
	private Appointment appointment;
	
	@Property
	@Persist
	private ApprovedPositions approvedPositions;
	
	@Property
	private List<ApprovedPositions> listApprovedPositions;
	
	@Property
	private ApprovedPositions valueApprovedPositions;
	
	@Persist
	private String selectedBlockId;
	
	@Inject
	private Block register;
	
	@Inject
	private Block grid;
	
	void beginRender(){
		
		if(organization == null)
			organization = new Organization();
		
		if(approvedPositions == null)
			approvedPositions = new ApprovedPositions();
		
		if(appointment == null)
			appointment = new Appointment();
		
		listOrganization = dao.getListOrganizationSearch(bean);
		listApprovedPositions = dao.getListApprovedPosition(approvedPositions.getOrganization(),null);
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		approvedPositions.setActivePosition(approvedPositions.getEstablishment());
		dao.saveOrUpdateObject(approvedPositions);
		approvedPositions = new ApprovedPositions();
		setSelectedBlockId("grid");
	}
	
	void onActionFromAppointment(Organization org){
		approvedPositions = new ApprovedPositions();
		approvedPositions.setOrganization(org);
		setSelectedBlockId("register");
	}
	
	void onActionFromCancel(){
		setSelectedBlockId("grid");
	}
	
	/* getter, setter */
	
	public Block getActiveBlock(){
		if (selectedBlockId == "register")
			return register;
		
		if(selectedBlockId == "grid")
			return grid;
		
		return grid;
	}
	
	public String isSelectedBlockId() {
		return selectedBlockId;
	}

	public void setSelectedBlockId(String selectedBlockId) {
		this.selectedBlockId = selectedBlockId;
	}
	
	public String getName(){
		if(valueOrganization == null)
			return "";
		return valueOrganization.getName();
	}
	
	public String getAppointmentName(){
		if(valueApprovedPositions == null || valueApprovedPositions.getAppointment() ==  null)
			return "";
		return valueApprovedPositions.getAppointment().getAppointmentName();
	}
	
	public String getEstablishmentSize(){
		if(valueApprovedPositions == null || valueApprovedPositions.getEstablishment() ==  null)
			return "";
		return valueApprovedPositions.getEstablishment().toString();
	}
	
	/* selection model */
	
	public SelectModel getAppointmentSelectionModel(){
		AppointmentOrgSelectionModel sm = new AppointmentOrgSelectionModel(dao);
		return sm;
	}
}
