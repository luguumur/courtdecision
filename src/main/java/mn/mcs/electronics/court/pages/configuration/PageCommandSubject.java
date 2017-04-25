package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.CommandSubject;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageCommandSubject {
	
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
	private CommandSubject commandSubject;
	
	@Property
	private List<CommandSubject> listCommandSubject;
	
	@Property
	private CommandSubject valueCommandSubject;
	
	private static final String GRID_ROW_CSS = "myGrid";
	
	void beginRender(){
		
		if(commandSubject ==null)
			commandSubject = new CommandSubject();
		
		listCommandSubject = dao.getListCommandSubject();
	}
		
	public int getNumber() {
		return listCommandSubject.indexOf(valueCommandSubject) + 1;
	}
	
	public static String getGRID_ROW_CSS() {
		return GRID_ROW_CSS;
	}

	public String getGridRowCSS() {
		return GRID_ROW_CSS;
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(commandSubject);
		commandSubject = new CommandSubject();
	}
	
	void onActionFromEdit(CommandSubject commandSubject){
		this.commandSubject = commandSubject;
	}
	
	@CommitAfter
	void onActionFromDelete(CommandSubject commandSubject){
		dao.deleteObject(commandSubject);
	}

}
