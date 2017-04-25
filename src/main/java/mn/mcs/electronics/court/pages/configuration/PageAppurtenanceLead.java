package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.organization.AppurtenanceLead;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageAppurtenanceLead {
	
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
	private AppurtenanceLead appurtenanceLead;
	
	@Property
	private List<AppurtenanceLead> listAppurtenanceLead;
	
	@Property
	private AppurtenanceLead valueAppurtenanceLead;
	
	void beginRender(){
		if(appurtenanceLead ==null)
			appurtenanceLead = new AppurtenanceLead();
		
		listAppurtenanceLead = dao.getListAppurtenanceLead();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(appurtenanceLead);
		appurtenanceLead = new AppurtenanceLead();
	}
	
	void onActionFromEdit(AppurtenanceLead appurtenanceLead){
		this.appurtenanceLead = appurtenanceLead;
	}
	
	@CommitAfter
	void onActionFromDelete(AppurtenanceLead appurtenanceLead){
		dao.deleteObject(appurtenanceLead);
	}
	
	
	public int getNumber() {
		return listAppurtenanceLead.indexOf(valueAppurtenanceLead) + 1;
	}
}
