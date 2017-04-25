package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.OccupationRole;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageOccupationRole {
	
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
	private OccupationRole occupationRole;
	
	@Property
	private List<OccupationRole> listOccupationRole;
	
	@Property
	private OccupationRole valueOccupationRole;
	
	void beginRender(){
		if(occupationRole ==null)
			occupationRole = new OccupationRole();
		
		listOccupationRole = dao.getListOccupationRole();
	}
	
	public int getNumber(){
		return listOccupationRole.indexOf(valueOccupationRole)+1; 
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(occupationRole);
		occupationRole = new OccupationRole();
	}
	
	void onActionFromEdit(OccupationRole occupationRole){
		this.occupationRole = occupationRole;
	}
	
	@CommitAfter
	void onActionFromDelete(OccupationRole occupationRole){
		dao.deleteObject(occupationRole);
	}

}
