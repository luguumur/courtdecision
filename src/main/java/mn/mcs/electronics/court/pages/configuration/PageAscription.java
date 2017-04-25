package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.Ascription;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageAscription {
	
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
	private Ascription ascription;
	
	@Property
	private List<Ascription> listAscription;
	
	@Property
	private Ascription valueAscription;
	
	void beginRender(){
		if(ascription ==null)
			ascription = new Ascription();
		
		listAscription = dao.getListAscription();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(ascription);
		ascription = new Ascription();
	}
	
	void onActionFromEdit(Ascription ascription){
		this.ascription = ascription;
	}

}
