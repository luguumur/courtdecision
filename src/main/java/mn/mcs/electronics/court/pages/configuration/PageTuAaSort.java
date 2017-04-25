package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.TuAaSort;
import mn.mcs.electronics.court.enums.ToriinUndurAjilAlbaCategory;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.EnumSelectModel;

public class PageTuAaSort {
	
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
	private TuAaSort tuAaSort;
	
	@Property
	private List<TuAaSort> listTuAaSort;
	
	@Property
	private TuAaSort valueTuAaSort;
	
	void beginRender(){
		if(tuAaSort ==null)
			tuAaSort = new TuAaSort();
		
		listTuAaSort = dao.getListTuAaSort();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(tuAaSort);
		tuAaSort = new TuAaSort();
	}
	
	void onActionFromEdit(TuAaSort tuAaSort){
		this.tuAaSort = tuAaSort;
	}
	
	@CommitAfter
	void onActionFromDelete(TuAaSort tuAaSort){
		dao.deleteObject(tuAaSort);
	}
	
	/* selection model */
	public SelectModel getTuAaSortNameSelectionModel(){
		return new EnumSelectModel(ToriinUndurAjilAlbaCategory.class,messages);
	}
	

}
