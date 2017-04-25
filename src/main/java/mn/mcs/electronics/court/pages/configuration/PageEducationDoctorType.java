package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.DegreeTypeOfEducationDoctor;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageEducationDoctorType {
	
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
	private DegreeTypeOfEducationDoctor educationDoctorType;
	
	@Property
	private List<DegreeTypeOfEducationDoctor> listEducationDoctor;
	
	@Property
	private DegreeTypeOfEducationDoctor valueDegreeTypeOfEducationDoctor;
	
	void beginRender(){
		if(educationDoctorType ==null)
			educationDoctorType = new DegreeTypeOfEducationDoctor();
		
		listEducationDoctor = dao.getListEducationDoctor();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(educationDoctorType);
		educationDoctorType = new DegreeTypeOfEducationDoctor();
	}
	
	void onActionFromEdit(DegreeTypeOfEducationDoctor educationDoctorType){
		this.educationDoctorType = educationDoctorType;
	}
	
	@CommitAfter
	void onActionFromDelete(DegreeTypeOfEducationDoctor educationDoctorType){
		dao.deleteObject(educationDoctorType);
	}

}
