package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuSort;
import mn.mcs.electronics.court.enums.UtTzTtTuCategory;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.EnumSelectModel;

public class PageUtTzTtTuFull {
	
	@Inject
	private CoreDAO dao;
	
	@Inject
	private Messages messages;
	
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
	private UtTzTtTuSort utTzTtTuSort;
	
	@Property
	private List<UtTzTtTuSort> listUtTzTtTuSort;
	
	@Property
	private UtTzTtTuSort valueUtTzTtTuSort;
	
	void beginRender(){
		if(utTzTtTuSort ==null)
			utTzTtTuSort = new UtTzTtTuSort();
		
		listUtTzTtTuSort = dao.getListUtTzTtTuSort();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(utTzTtTuSort);
		utTzTtTuSort = new UtTzTtTuSort();
	}
	
	void onActionFromEdit(UtTzTtTuSort utTzTtTuSort){
		this.utTzTtTuSort = utTzTtTuSort;
	}
	
	/* selection model */
	public SelectModel getUtTzTtTuSortNameSelectionModel(){
		return new EnumSelectModel(UtTzTtTuCategory.class,messages);
	}
	

}
