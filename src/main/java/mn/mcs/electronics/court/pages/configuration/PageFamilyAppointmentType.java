package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.FamilyAppointmentType;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageFamilyAppointmentType {
	
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
	private FamilyAppointmentType familyAppointmentType;
	
	@Property
	private List<FamilyAppointmentType> listFamilyAppointmentType;
	
	@Property
	private FamilyAppointmentType valueFamilyAppointmentType;
	
	void beginRender(){
		if(familyAppointmentType ==null)
			familyAppointmentType = new FamilyAppointmentType();
		
		listFamilyAppointmentType = dao.getListFamilyAppointmentType();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(familyAppointmentType);
		familyAppointmentType = new FamilyAppointmentType();
	}
	
	void onActionFromEdit(FamilyAppointmentType familyAppointmentType){
		this.familyAppointmentType = familyAppointmentType;
	}
	
	@CommitAfter
	void onActionFromDelete(FamilyAppointmentType familyAppointmentType){
		dao.deleteObject(familyAppointmentType);
	}
}
