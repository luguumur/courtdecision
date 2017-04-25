package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.School;
import mn.mcs.electronics.court.enums.UniversityType;
import mn.mcs.electronics.court.model.CountrySelectionModel;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.EnumSelectModel;

public class PageSchool {
	
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
	private School school;
	
	@Property
	private List<School> listSchool;
	
	@Property
	private School valueSchool;
		
	@Inject
	private Messages messages;
	
	void beginRender(){
		if(school ==null)
			school = new School();
		
		listSchool = dao.getListSchool();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(school);
		school = new School();
	}
	
	void onActionFromEdit(School school){
		this.school = school;
	}
	
	@CommitAfter
	void onActionFromDelete(School school){
		dao.deleteObject(school);
	}
	
	/* Selection model */
	public SelectModel getCountrySelectionModel(){
		CountrySelectionModel sm = new CountrySelectionModel(dao);
		
		return sm;
	}
	
	public SelectModel getUniversityTypeSelectionModel(){
		return new EnumSelectModel(UniversityType.class,messages);
	}
}
