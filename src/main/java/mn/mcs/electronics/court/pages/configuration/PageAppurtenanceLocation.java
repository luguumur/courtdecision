package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.organization.AppurtenanceLocation;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageAppurtenanceLocation {
	
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
	private AppurtenanceLocation appurtenanceLocation;
	
	@Property
	private List<AppurtenanceLocation> listAppurtenanceLocation;
	
	@Property
	private AppurtenanceLocation valueAppurtenanceLocation;
	
	void beginRender(){
		if(appurtenanceLocation ==null)
			appurtenanceLocation = new AppurtenanceLocation();
		
		listAppurtenanceLocation = dao.getListAppurtenanceLocation();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(appurtenanceLocation);
		appurtenanceLocation = new AppurtenanceLocation();
	}
	
	void onActionFromEdit(AppurtenanceLocation appurtenanceLocation){
		this.appurtenanceLocation = appurtenanceLocation;
	}
	
	@CommitAfter
	void onActionFromDelete(AppurtenanceLocation appurtenanceLocation){
		dao.deleteObject(appurtenanceLocation);
	}
}
