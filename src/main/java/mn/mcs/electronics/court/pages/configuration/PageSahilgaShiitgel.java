package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.SahilgaShiitgel;
import mn.mcs.electronics.court.enums.SahilgaSubject;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.util.EnumSelectModel;

public class PageSahilgaShiitgel {
	
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
	private SahilgaShiitgel sahilgaShiitgel;
	
	@Property
	private List<SahilgaShiitgel> listSahilgaShiitgel;
	
	@Property
	private SahilgaShiitgel valueSahilgaShiitgel;
	
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
		
		if(sahilgaShiitgel ==null){
			sahilgaShiitgel = new SahilgaShiitgel();
			//sahilgaShiitgel.setSahilgaType(SahilgaType.ToriinAlbaniiTuhaiHuuli);
		}
	
		listSahilgaShiitgel = dao.getListSahilgaShiitgel();		
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		
		//System.err.println(sahilgaShiitgel.getSahilgaType());
		dao.saveOrUpdateObject(sahilgaShiitgel);
		sahilgaShiitgel = new SahilgaShiitgel();
		
	}

	public boolean getTor(){
	
		//return (sahilgaShiitgel!=null && sahilgaShiitgel.getSahilgaType()!=null && sahilgaShiitgel.getSahilgaType().equals(SahilgaType.ToriinAlbaniiTuhaiHuuli))?true:false;	
		return true;
	}
	
	void onActionFromEdit(SahilgaShiitgel sahilgaShiitgel){
		this.sahilgaShiitgel = sahilgaShiitgel;
	}

	@CommitAfter
	void onActionFromDelete(SahilgaShiitgel sahilgaShiitgel){
		
		if(sahilgaShiitgel.getSahilga()!=null && !sahilgaShiitgel.getSahilga().isEmpty())
			//loginState.setErrMessage("errOtherObjectUsing");
			System.err.println(messages.get("notImageErr"));
		else
			dao.deleteObject(sahilgaShiitgel);
	}
	
	/*public SelectModel getSahilgaTypeSelectionModel(){
		
		return new EnumSelectModel(SahilgaType.class,messages);
		
	}*/
	
	public SelectModel getSahilgaSubjectSelectionModel(){
		
		return new EnumSelectModel(SahilgaSubject.class,messages);	
	}
	
	
	public String getSahilgaSubject(){
		
		//return (valueSahilgaShiitgel!=null && valueSahilgaShiitgel.getSahilgaSubject()!=null)? messages.get(valueSahilgaShiitgel.getSahilgaSubject().name()):"";
		return null;
	}
	
	public String getSahilgaType(){
		//return (valueSahilgaShiitgel!=null && valueSahilgaShiitgel.getSahilgaType()!=null)?messages.get(valueSahilgaShiitgel.getSahilgaType().name()):"";
		return null;
	}
	
	/*Object onValueChangedFromTypeClick() {
		return request.isXHR() ? typeZone.getBody() : null;
	}*/
	
	public int getNumber() {
		return listSahilgaShiitgel.indexOf(valueSahilgaShiitgel) + 1;
	}
	
	public static String getGRID_ROW_CSS() {
		return GRID_ROW_CSS;
	}

	public String getGridRowCSS() {
		return GRID_ROW_CSS;
	}
	
	public String getDuration(){
		if(valueSahilgaShiitgel.getDuration()==null)
			return "";
		
		return valueSahilgaShiitgel.getDuration()+"";
	}
}
