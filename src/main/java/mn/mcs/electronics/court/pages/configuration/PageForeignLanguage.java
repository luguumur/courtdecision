package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.ForeignLanguage;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageForeignLanguage {
	
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
	private ForeignLanguage foreignLanguage;
	
	@Property
	private List<ForeignLanguage> listForeignLanguage;
	
	@Property
	private ForeignLanguage valueForeignLanguage;
	
	void beginRender(){
		if(foreignLanguage ==null)
			foreignLanguage = new ForeignLanguage();
		
		listForeignLanguage = dao.getListForeignLanguage();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(foreignLanguage);
		foreignLanguage = new ForeignLanguage();
	}
	
	void onActionFromEdit(ForeignLanguage foreignLanguage){
		this.foreignLanguage = foreignLanguage;
	}
	
	@CommitAfter
	void onActionFromDelete(ForeignLanguage foreignLanguage){
		dao.deleteObject(foreignLanguage);
	}
}
