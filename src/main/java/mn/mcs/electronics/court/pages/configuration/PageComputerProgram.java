package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.ComputerProgram;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageComputerProgram {
	
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
	private ComputerProgram computerProgram;
	
	@Property
	private List<ComputerProgram> listComputerProgram;
	
	@Property
	private ComputerProgram valueComputerProgram;
	
	void beginRender(){
		if(computerProgram ==null)
			computerProgram = new ComputerProgram();
		
		listComputerProgram = dao.getListComputerProgram();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(computerProgram);
		computerProgram = new ComputerProgram();
	}
	
	void onActionFromEdit(ComputerProgram computerProgram){
		this.computerProgram = computerProgram;
	}
	
	@CommitAfter
	void onActionFromDelete(ComputerProgram computerProgram){
		dao.deleteObject(computerProgram);
	}
}
