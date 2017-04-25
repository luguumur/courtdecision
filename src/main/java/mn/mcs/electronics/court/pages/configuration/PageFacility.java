package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.Facility;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageFacility {
	
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
	private Facility facility;
	
	@Property
	private List<Facility> listFacility;
	
	@Property
	private Facility valueFacility;
	
	void beginRender(){
		if(facility ==null)
			facility = new Facility();
		
		listFacility = dao.getListFacility();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(facility);
		facility = new Facility();
	}
	
	void onActionFromEdit(Facility facility){
		this.facility = facility;
	}
	
	@CommitAfter
	void onActionFromDelete(Facility facility){
		dao.deleteObject(facility);
	}
}
