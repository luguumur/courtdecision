package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.Country;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageCountry {
	
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
	private Country country;
	
	@Property
	private List<Country> listCountry;
	
	@Property
	private Country valueCountry;
	
	void beginRender(){
		if(country ==null)
			country = new Country();
		
		listCountry = dao.getListCountry();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(country);
		country = new Country();
	}
	
	void onActionFromEdit(Country country){
		this.country = country;
	}
	
	@CommitAfter
	void onActionFromDelete(Country country){
		dao.deleteObject(country);
	}
}
