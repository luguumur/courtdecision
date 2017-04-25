package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.organization.Saving;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageSaving {
	
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
	private Saving saving;
	
	@Property
	private List<Saving> listSaving;
	
	@Property
	private Saving valueSaving;
	
	void beginRender(){
		if(saving ==null)
			saving = new Saving();
		
		listSaving = dao.getListSavingType();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(saving);
		saving = new Saving();
	}
	
	void onActionFromEdit(Saving saving){
		this.saving = saving;
	}
	
	@CommitAfter
	void onActionFromDelete(Saving saving){
		dao.deleteObject(saving);
	}

}
