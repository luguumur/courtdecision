package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.CityProvince;
import mn.mcs.electronics.court.model.CountrySelectionModel;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageCity {
	
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
	private CityProvince city;
	
	@Property
	private List<CityProvince> listCity;
	
	@Property
	private CityProvince valueCity;
	
	void beginRender(){
		if(city ==null)
			city = new CityProvince();
		
		listCity = dao.getListCityProvince(null);
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(city);
		city = new CityProvince();
	}
	
	void onActionFromEdit(CityProvince city){
		this.city = city;
	}
	
	@CommitAfter
	void onActionFromDelete(CityProvince city){
		dao.deleteObject(city);
	}
	
	/* Selection model */
	public SelectModel getCountrySelectionModel(){
		CountrySelectionModel sm = new CountrySelectionModel(dao);
		
		return sm;
	}
}
