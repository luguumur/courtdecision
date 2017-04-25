package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.organization.OrganizationType;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageOrganizationType {
	
	@Inject
	private CoreDAO dao;
	
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
	private OrganizationType organizationType;
	
	@Property
	private List<OrganizationType> listOrganizationType;
	
	@Property
	private OrganizationType valueOrganizationType;
	
	private static final String GRID_ROW_CSS = "myGrid";
	
	void beginRender(){
		if(organizationType ==null)
			organizationType = new OrganizationType();
		
		listOrganizationType = dao.getListOrganizationType();
	}
	
	public int getNumber() {
		return listOrganizationType.indexOf(valueOrganizationType) + 1;
	}
	
	public static String getGRID_ROW_CSS() {
		return GRID_ROW_CSS;
	}

	public String getGridRowCSS() {
		return GRID_ROW_CSS;
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(organizationType);
		organizationType = new OrganizationType();
	}
	
	void onActionFromEdit(OrganizationType organizationType){
		this.organizationType = organizationType;
	}

	@CommitAfter
	void onActionFromDelete(OrganizationType organizationType){
		dao.deleteObject(organizationType);
	}
}
