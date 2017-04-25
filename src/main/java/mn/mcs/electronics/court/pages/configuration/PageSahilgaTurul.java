package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.SahilgaTurul;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

public class PageSahilgaTurul {
	
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
	private SahilgaTurul sahilgaTurul;
	
	@Property
	private List<SahilgaTurul> listSahilgaTurul;
	
	@Property
	private SahilgaTurul valueSahilgaTurul;
	
	@SessionState
	private LoginState loginState;
	
	private static final String GRID_ROW_CSS = "myGrid";
	
	/*@InjectComponent
	private Zone typeZone;
	*/
	@Inject
	private Request request;
		
	@Inject
	private Messages messages;
	
	void beginRender(){
		
		if(sahilgaTurul ==null)
			sahilgaTurul = new SahilgaTurul();
	
		listSahilgaTurul = dao.getListSahilgaTurul();		
	}
	
	@CommitAfter
	void onSelectedFromSave(){		
		dao.saveOrUpdateObject(sahilgaTurul);
		sahilgaTurul = new SahilgaTurul();		
	}
	
	void onActionFromEdit(SahilgaTurul sahilgaTurul){
		this.sahilgaTurul = sahilgaTurul;
	}

	@CommitAfter
	void onActionFromDelete(SahilgaTurul sahilgaTurul){
		dao.deleteObject(sahilgaTurul);
	}
	
	public int getNumber() {
		return listSahilgaTurul.indexOf(valueSahilgaTurul) + 1;
	}
	
	public static String getGRID_ROW_CSS() {
		return GRID_ROW_CSS;
	}

	public String getGridRowCSS() {
		return GRID_ROW_CSS;
	}
}
