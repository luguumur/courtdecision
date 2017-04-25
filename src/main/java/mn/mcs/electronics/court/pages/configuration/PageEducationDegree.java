package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.EducationDegree;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageEducationDegree {
	
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
	private EducationDegree educationDegree;
	
	@Property
	private List<EducationDegree> listEducationDegree;
	
	@Property
	private EducationDegree valueEducationDegree;
	
	void beginRender(){
		if(educationDegree ==null)
			educationDegree = new EducationDegree();
		
		listEducationDegree = dao.getListEducationDegree();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(educationDegree);
		educationDegree = new EducationDegree();
	}
	
	void onActionFromEdit(EducationDegree educationDegree){
		this.educationDegree = educationDegree;
	}
	
	@CommitAfter
	void onActionFromDelete(EducationDegree educationDegree){
		dao.deleteObject(educationDegree);
	}
}
