package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.DegreeTypeOfScienceDoctor;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageScienceDoctorType {
	
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
	private DegreeTypeOfScienceDoctor scienceDoctorType;
	
	@Property
	private List<DegreeTypeOfScienceDoctor> listScienceDoctor;
	
	@Property
	private DegreeTypeOfScienceDoctor valueDegreeTypeOfScienceDoctor;
	
	void beginRender(){
		if(scienceDoctorType ==null)
			scienceDoctorType = new DegreeTypeOfScienceDoctor();
		
		listScienceDoctor = dao.getListScienceDoctor();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(scienceDoctorType);
		scienceDoctorType = new DegreeTypeOfScienceDoctor();
	}
	
	void onActionFromEdit(DegreeTypeOfScienceDoctor scienceDoctorType){
		this.scienceDoctorType = scienceDoctorType;
	}
	
	@CommitAfter
	void onActionFromDelete(DegreeTypeOfScienceDoctor scienceDoctorType){
		dao.deleteObject(scienceDoctorType);
	}

}
