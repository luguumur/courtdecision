package mn.mcs.electronics.court.pages.configuration;

import java.text.SimpleDateFormat;
import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuLevel;
import mn.mcs.electronics.court.entities.configuration.salary.TuriinZahirgaaSalaryNetwork;
import mn.mcs.electronics.court.model.OccupationLevelSelectionModel;
import mn.mcs.electronics.court.model.SalaryScaleSelectionModel;
import mn.mcs.electronics.court.util.Constants;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;


public class PageSalaryNetworkConfig {
	
	@Inject
	private CoreDAO dao;
	
	@Inject
	private Messages messages;
	
	@SessionState
	private LoginState loginState;
	
	@Inject
	@Property
	@Path("context:/images/b_edit.png")
	private Asset editIcon;

	@Property
	@Inject
	@Path("context:/images/b_drop.png")
	private Asset deleteIcon;
	
	@Inject
	@Property
	@Path("context:/images/lightbulb1.png")
	private Asset lightbulb1;
	 
	@Inject
	@Property
	@Path("context:/images/lightbulb2.png")
	private Asset lightbulb2;
	
	@Property
	@Persist
	private List<UtTzTtTuLevel> listSalaryNetwork;
	
	@Property
	@Persist
	private TuriinZahirgaaSalaryNetwork salary;
	
	@Property
	@Persist
	private TuriinZahirgaaSalaryNetwork searchSalary;

	@Property
	@Persist
	private List<TuriinZahirgaaSalaryNetwork> listTuriinZahirgaaSalaryNetwork;
		
	@Property
	@Persist
	private TuriinZahirgaaSalaryNetwork valueTuriinZahirgaaSalaryNetwork;
	
	private SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
	
	void beginRender(){		
		listSalaryNetwork = dao.getListUtTzTtTuLevel(null);
		
		if(salary == null)
			salary = new TuriinZahirgaaSalaryNetwork();
		
		if(searchSalary == null)
			searchSalary = new TuriinZahirgaaSalaryNetwork();
		//listTuriinZahirgaaSalaryNetwork = dao.getListTuriinZahirgaaSalaryNetwork();
		
		listTuriinZahirgaaSalaryNetwork = dao.getListTuriinZahirgaaSalaryDate(searchSalary.getLevel(), searchSalary.getPhase());
	}
	
	void onSelectedFromSearch(){
		listTuriinZahirgaaSalaryNetwork = dao.getListTuriinZahirgaaSalaryDate(searchSalary.getLevel(), searchSalary.getPhase());
	}	
	
	@CommitAfter
	void onSelectedFromSave(){		
        dao.saveOrUpdateObject(salary);
		
		salary = new TuriinZahirgaaSalaryNetwork();		
	}

	void onActionFromEdit(TuriinZahirgaaSalaryNetwork turiinZahirgaaSalaryNetwork){
		this.salary = turiinZahirgaaSalaryNetwork;
		this.salary.setAmount(turiinZahirgaaSalaryNetwork.getAmount());
	}

	@CommitAfter
	void onActionFromDelete(TuriinZahirgaaSalaryNetwork turiinZahirgaaSalaryNetwork){
		dao.deleteObject(turiinZahirgaaSalaryNetwork);
	}
	
	
	/* selection model */
	
	public SelectModel getLevelSelectionModel(){
		OccupationLevelSelectionModel sm = new OccupationLevelSelectionModel(dao,null);
		return sm;
	}
	
	public SelectModel getSalaryPhaseSelectionModel(){
		SalaryScaleSelectionModel sm = new SalaryScaleSelectionModel(dao);
		return sm;
	}

	/* getter setter */
	public String getAmount(){
		if(valueTuriinZahirgaaSalaryNetwork==null||valueTuriinZahirgaaSalaryNetwork.getAmount()==null)
			return "";

		return Double.toString(valueTuriinZahirgaaSalaryNetwork.getAmount());
	}
	
	public String getPhase(){
		if(valueTuriinZahirgaaSalaryNetwork==null||valueTuriinZahirgaaSalaryNetwork.getPhase()==null)
			return "";
		
		return valueTuriinZahirgaaSalaryNetwork.getPhase().getSalaryScale().toString();
	}
	
	public String getLevel(){
		if(valueTuriinZahirgaaSalaryNetwork==null)
			return ""; 
			
		return valueTuriinZahirgaaSalaryNetwork.getLevel().getUtTzTtTuSort().getName()+"-"+
		valueTuriinZahirgaaSalaryNetwork.getLevel().getUtTzTtTuRank().getRank();
	}
	
	public String getDate(){
		if(valueTuriinZahirgaaSalaryNetwork==null || valueTuriinZahirgaaSalaryNetwork.getDate()==null)
			return "";
		
		return format.format(valueTuriinZahirgaaSalaryNetwork.getDate());
	}
	
	public String getActive(){
		if(valueTuriinZahirgaaSalaryNetwork==null || valueTuriinZahirgaaSalaryNetwork.getActive()==null || valueTuriinZahirgaaSalaryNetwork.getActive() == false)
			return "";
		
		return "Тийм";
	}
	
	public int getNumber(){
		return listTuriinZahirgaaSalaryNetwork.indexOf(valueTuriinZahirgaaSalaryNetwork)+1; 
	}
}
