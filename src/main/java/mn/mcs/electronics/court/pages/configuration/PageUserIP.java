package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.UserIP;
import mn.mcs.electronics.court.model.UserSelectionModel;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageUserIP {
	
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
	private UserIP userIP;
	
	@Property
	private List<UserIP> listUserIP;
	
	@Property
	private UserIP valueUserIP;
	
	void beginRender(){
		if(userIP ==null)
			userIP = new UserIP();
		
		listUserIP = dao.getListUserIP();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(userIP);
		userIP = new UserIP();
	}
	
	void onActionFromEdit(UserIP userIP){
		this.userIP = userIP;
	}
	
	@CommitAfter
	void onActionFromDelete(UserIP userIP){
		dao.deleteObject(userIP);
	}
	
	public String getUserName(){
		if(valueUserIP==null||valueUserIP.getUser()==null||valueUserIP.getUser().getUsername()==null)
			return "";
		return valueUserIP.getUser().getUsername();
	}
	
	public SelectModel getUserSelectionModel(){
		
		UserSelectionModel sm = new UserSelectionModel(dao);
		
		return sm;
	}

}
