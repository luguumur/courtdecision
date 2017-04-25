package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.DegreeType;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageDegreeType {
	
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
	private DegreeType degreeType;
	
	@Property
	private List<DegreeType> listDegreeType;
	
	@Property
	private DegreeType valueDegreeType;
	
	void beginRender(){
		if(degreeType ==null)
			degreeType = new DegreeType();
		
		listDegreeType = dao.getListDegreeType();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(degreeType);
		degreeType = new DegreeType();
	}
	
	void onActionFromEdit(DegreeType degreeType){
		this.degreeType = degreeType;
	}
	
	@CommitAfter
	void onActionFromDelete(DegreeType degreeType){
		dao.deleteObject(degreeType);
	}

}
