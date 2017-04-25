package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.QuitJobCause;
import mn.mcs.electronics.court.enums.EmployeeCurrentStatus;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.EnumSelectModel;

public class PageQuitCause {
		
	@Inject
	private Messages messages;
	
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
	private QuitJobCause quitJobCause;
	
	@Property
	private List<QuitJobCause> listQuitJobCause;
	
	@Property
	private QuitJobCause valueQuitJobCause;
	
	@Property
	@Persist
	private EmployeeCurrentStatus quitJobType;
	
	void beginRender(){
		if(quitJobCause ==null)
			quitJobCause = new QuitJobCause();
		
		listQuitJobCause = dao.getListQuitJobCause();
	}
	
	public int getNumber() {
		return listQuitJobCause.indexOf(valueQuitJobCause) + 1;
	}	
	
	public SelectModel getQuitTypeSelectionModel(){
		return new EnumSelectModel(EmployeeCurrentStatus.class,messages);
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(quitJobCause);
		quitJobCause = new QuitJobCause();
	}
	
	void onActionFromEdit(QuitJobCause quitJobCause){
		this.quitJobCause = quitJobCause;
	}
	
	@CommitAfter
	void onActionFromDelete(QuitJobCause quitJobCause){
		dao.deleteObject(quitJobCause);
	}
}
