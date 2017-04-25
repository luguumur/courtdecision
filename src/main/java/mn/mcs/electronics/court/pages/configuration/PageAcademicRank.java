package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.AcademicRank;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageAcademicRank {
	
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
	private AcademicRank militaryRank;
	
	@Property
	private List<AcademicRank> listMilitaryRank;
	
	@Property
	private AcademicRank valueMilitaryRank;
	
	void beginRender(){
		if(militaryRank ==null)
			militaryRank = new AcademicRank();
		
		listMilitaryRank = dao.getListMilitaryRank();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(militaryRank);
		militaryRank = new AcademicRank();
	}
	
	void onActionFromEdit(AcademicRank militaryRank){
		this.militaryRank = militaryRank;
	}

}
