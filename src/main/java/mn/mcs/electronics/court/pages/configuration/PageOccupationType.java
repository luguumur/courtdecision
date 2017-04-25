package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.OccupationType;
import mn.mcs.electronics.court.enums.AppointmentSortEmployee;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.EnumSelectModel;

public class PageOccupationType {
	
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
	private OccupationType occupationType;
	
	@Property
	private List<OccupationType> listOccupationType;
	
	@Property
	private OccupationType valueOccupationType;
	
	void beginRender(){
		if(occupationType ==null)
			occupationType = new OccupationType();
		
		listOccupationType = dao.getListOccupationType();
	}
	
	public int getNumber() {
		return listOccupationType.indexOf(valueOccupationType) + 1;
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(occupationType);
		occupationType = new OccupationType();
	}
	
	void onActionFromEdit(OccupationType occupationType){
		this.occupationType = occupationType;
	}
	
	@CommitAfter
	void onActionFromDelete(OccupationType occupationType){
		dao.deleteObject(occupationType);
	}
	
	public SelectModel getTypeSelectionModel(){
		
		return new EnumSelectModel(AppointmentSortEmployee.class, messages);
	}

}
