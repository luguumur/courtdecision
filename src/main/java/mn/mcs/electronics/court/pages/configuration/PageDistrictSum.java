package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.DistrictSum;
import mn.mcs.electronics.court.model.CityProvinceSelectionModel;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageDistrictSum {
	
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
	private DistrictSum districtSum;
	
	@Property
	private List<DistrictSum> listDistrictSum;
	
	@Property
	private DistrictSum valueDistrictSum;
	
	void beginRender(){
		if(districtSum ==null)
			districtSum = new DistrictSum();
		
		listDistrictSum = dao.getListDistrictSum(null);
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(districtSum);
		districtSum = new DistrictSum();
	}
	
	void onActionFromEdit(DistrictSum districtSum){
		this.districtSum = districtSum;
	}
	
	/* getter, setter */
	public String getCityName(){
		if(valueDistrictSum==null||valueDistrictSum.getCity()==null)
			return "";
		
		return valueDistrictSum.getCity().getCityName();
	}
	
	/* Selection model */
	public SelectModel getCityProvinceSelectionModel(){
		CityProvinceSelectionModel sm = new CityProvinceSelectionModel(dao);
		return sm;
	}
}
