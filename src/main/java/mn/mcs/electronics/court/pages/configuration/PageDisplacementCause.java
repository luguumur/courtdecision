package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.DisplacementCause;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageDisplacementCause {
	
	
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
	private DisplacementCause displacementCause;
	
	@Property
	private List<DisplacementCause> listDisplacementCause;
	
	@Property
	private DisplacementCause valueDisplacementCause;
	
	void beginRender(){
		if(displacementCause ==null)
			displacementCause = new DisplacementCause();
		
		listDisplacementCause = dao.getListDisplacementCause();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(displacementCause);
		displacementCause = new DisplacementCause();
	}
	
	void onActionFromEdit(DisplacementCause displacementCause){
		this.displacementCause = displacementCause;
	}
	
	@CommitAfter
	void onActionFromDelete(DisplacementCause displacementCause){
		dao.deleteObject(displacementCause);
	}
}
