package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.OccupationRank;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageOccupationRank {
	
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
	private OccupationRank occupationRank;
	
	@Property
	private List<OccupationRank> listOccupationRank;
	
	@Property
	private OccupationRank valueOccupationRank;
	
	void beginRender(){
		if(occupationRank ==null)
			occupationRank = new OccupationRank();
		
		listOccupationRank = dao.getListOccupationRank();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(occupationRank);
		occupationRank = new OccupationRank();
	}
	
	void onActionFromEdit(OccupationRank occupationRank){
		this.occupationRank = occupationRank;
	}
	
	@CommitAfter
	void onActionFromDelete(OccupationRank occupationRank){
		dao.deleteObject(occupationRank);
	}
	
	public String getRank(){
		
		return  valueOccupationRank.getRank().toString();
	}
}
