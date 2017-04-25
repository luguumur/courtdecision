package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuLevel;
import mn.mcs.electronics.court.model.OccupationRankSelectionModel;
import mn.mcs.electronics.court.model.UtTzTtTuSortNameSelectionModel;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageUtTzTtTuLevel {
	
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
	private UtTzTtTuLevel utTzTtTuLevel;
	
	@Property
	private UtTzTtTuLevel valueUtTzTtTuLevel;
	
	@Property
	private List<UtTzTtTuLevel> listUtTzTtTuLevel;
	
	private static final String GRID_ROW_CSS = "myGrid";
	
	void beginRender(){
		if( utTzTtTuLevel==null)
			utTzTtTuLevel = new UtTzTtTuLevel();
		
		listUtTzTtTuLevel = dao.getListUtTzTtTuLevel();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(utTzTtTuLevel);
		utTzTtTuLevel = new UtTzTtTuLevel();
	}
	
	void onActionFromEdit(UtTzTtTuLevel utTzTtTuLevel){
		this.utTzTtTuLevel = utTzTtTuLevel;
	}
	
	@CommitAfter
	void onActionFromDelete(UtTzTtTuLevel utTzTtTuLevel){
		dao.deleteObject(utTzTtTuLevel);
	}
	
	public int getNumber() {
		return listUtTzTtTuLevel.indexOf(valueUtTzTtTuLevel) + 1;
	}
	
	public static String getGRID_ROW_CSS() {
		return GRID_ROW_CSS;
	}

	public String getGridRowCSS() {
		return GRID_ROW_CSS;
	}
	
	/* Selection model */
	public SelectModel getUtTzTtTuSortSelectionModel(){
		SelectModel sm= new UtTzTtTuSortNameSelectionModel(dao);
		
		return sm;
		
	}
	
	public SelectModel getOccupationRankSelectionModel(){
		SelectModel sm= new OccupationRankSelectionModel(dao);
		
		return sm;
		
	}
	
	public String getSortName(){
		if(valueUtTzTtTuLevel==null|| valueUtTzTtTuLevel.getUtTzTtTuSort()==null)
			return "";
		
		return valueUtTzTtTuLevel.getUtTzTtTuSort().getName();
	}
	
	public String getRankName(){
		if(valueUtTzTtTuLevel==null|| valueUtTzTtTuLevel.getUtTzTtTuRank()==null)
			return "";
		
		return valueUtTzTtTuLevel.getUtTzTtTuRank().getRank().toString();
	}
}
