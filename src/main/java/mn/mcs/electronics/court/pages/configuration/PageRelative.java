package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.RelativeType;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageRelative {
	
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
	private RelativeType relativeType;
	
	@Property
	private List<RelativeType> listRelativeType;
	
	@Property
	private RelativeType valueRelativeType;
	
	void beginRender(){
		if(relativeType ==null)
			relativeType = new RelativeType();
		
		listRelativeType = dao.getRelativeType();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(relativeType);
		relativeType = new RelativeType();
	}
	
	void onActionFromEdit(RelativeType relativeType){
		this.relativeType = relativeType;
	}

}
