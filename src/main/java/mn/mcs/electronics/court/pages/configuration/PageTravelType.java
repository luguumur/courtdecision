package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.TravelType;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageTravelType {
	
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
	private TravelType travelType;
	
	@Property
	private List<TravelType> listTravelType;
	
	@Property
	private TravelType valueTravelType;
	
	void beginRender(){
		if(travelType ==null)
			travelType = new TravelType();
		
		listTravelType = dao.getListTravelType();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(travelType);
		travelType = new TravelType();
	}
	
	void onActionFromEdit(TravelType travelType){
		this.travelType = travelType;
	}
	
	@CommitAfter
	void onActionFromDelete(TravelType travelType){
		dao.deleteObject(travelType);
	}
}
