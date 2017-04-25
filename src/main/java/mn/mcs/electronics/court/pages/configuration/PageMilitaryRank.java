package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.MilitaryRank;
import mn.mcs.electronics.court.entities.employee.EmployeeMilitary;
import mn.mcs.electronics.court.enums.MilitaryRankType;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.EnumSelectModel;

public class PageMilitaryRank {
	
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
	private MilitaryRank militaryRank;
	
	@Property
	private List<MilitaryRank> listMilitaryRank;
	
	@Property
	private MilitaryRank valueMilitaryRank;
	
	@SessionState
	private LoginState loginState;
	
	void beginRender(){
		if(militaryRank ==null)
			militaryRank = new MilitaryRank();
		
		listMilitaryRank = dao.getListMilitary();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(militaryRank);
		militaryRank = new MilitaryRank();
	}
	
	void onActionFromEdit(MilitaryRank militaryRank){
		this.militaryRank = militaryRank;
	}
	
	@CommitAfter
	void onActionFromDelete(MilitaryRank militaryRank){
		
		List<EmployeeMilitary> militaryList=dao.getListMilitaryByMilitaryRank(militaryRank);
		
		if(militaryList!=null && militaryList.size()>0){
			
			
			System.err.println(militaryList.size());
		}else{
			dao.deleteObject(militaryRank);
		}	
	}
	
	/* Selection model */
	public SelectModel getTypeSelectionModel(){
		return new EnumSelectModel(MilitaryRankType.class,messages);
	}
	
	public int getNumber() {
		return listMilitaryRank.indexOf(valueMilitaryRank) + 1;
	}
	
	/* Getter Setter */
	public Integer getPeriod(){
		
		if(valueMilitaryRank.getPeriod()==null)
			return 0;
		
		return valueMilitaryRank.getPeriod();
	}
}
