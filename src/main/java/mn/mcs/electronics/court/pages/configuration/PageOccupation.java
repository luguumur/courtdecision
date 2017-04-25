package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.Occupation;
import mn.mcs.electronics.court.enums.SchoolType;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.EnumSelectModel;

public class PageOccupation {
	
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
	private Occupation occupation;
	
	@Property
	private List<Occupation> listOccupation;
	
	@Property
	private Occupation valueOccupation;
	
	void beginRender(){
		if(occupation ==null)
			occupation = new Occupation();
		
		listOccupation = dao.getListOccupation(null);
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(occupation);
		occupation = new Occupation();
	}
	
	void onActionFromEdit(Occupation occupation){
		this.occupation = occupation;
	}
	
	@CommitAfter
	void onActionFromDelete(Occupation occupation){
		dao.deleteObject(occupation);
	}
	
	public SelectModel getSchoolTypeSelectionModel(){
		return new EnumSelectModel(SchoolType.class,messages);
	}

}
