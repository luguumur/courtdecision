package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.TuAaLevel;
import mn.mcs.electronics.court.model.OccupationRankSelectionModel;
import mn.mcs.electronics.court.model.TuAaSortNameSelectionModel;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageTuAaLevel {
	
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
	private TuAaLevel tuAaLevel;
	
	@Property
	private TuAaLevel valueTuAaLevel;
	
	@Property
	private List<TuAaLevel> listTuAaLevel;
	
	void beginRender(){
		if( tuAaLevel==null)
			tuAaLevel = new TuAaLevel();
		
		listTuAaLevel = dao.getListTuAaLevel();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(tuAaLevel);
		tuAaLevel = new TuAaLevel();
	}
	
	void onActionFromEdit(TuAaLevel tuAaLevel){
		this.tuAaLevel = tuAaLevel;
	}
	
	@CommitAfter
	void onActionFromDelete(TuAaLevel tuAaLevel){
		dao.deleteObject(tuAaLevel);
	}
	
	/* Selection model */
	public SelectModel getTuAaSortSelectionModel(){
		SelectModel sm= new TuAaSortNameSelectionModel(dao);
		
		return sm;
		
	}
	
	public SelectModel getOccupationRankSelectionModel(){
		SelectModel sm= new OccupationRankSelectionModel(dao);
		
		return sm;
		
	}
	
	public String getSortName(){
		if(valueTuAaLevel==null|| valueTuAaLevel.getTuAaSort()==null)
			return "";
		
		return valueTuAaLevel.getTuAaSort().getName();
	}
	
	public String getRankName(){
		if(valueTuAaLevel==null|| valueTuAaLevel.getTuAaRank()==null)
			return "";
		
		return valueTuAaLevel.getTuAaRank().getRank().toString();
	}
}
