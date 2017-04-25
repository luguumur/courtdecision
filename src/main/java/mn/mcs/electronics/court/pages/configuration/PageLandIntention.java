package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.LandIntention;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageLandIntention {
	
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
	private LandIntention intention;
	
	@Property
	private List<LandIntention> listIntention;
	
	@Property
	private LandIntention valueLandIntention;
	
	void beginRender(){
		if(intention ==null)
			intention = new LandIntention();
		
		listIntention= dao.getListLandIntention();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(intention);
		intention = new LandIntention();
	}
	
	void onActionFromEdit(LandIntention intention){
		this.intention = intention;
	}
	
	@CommitAfter
	void onActionFromDelete(LandIntention intention){
		dao.deleteObject(intention);
	}

}
