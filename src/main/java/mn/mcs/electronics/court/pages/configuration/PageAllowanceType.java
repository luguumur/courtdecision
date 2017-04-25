package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.AllowanceType;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageAllowanceType {
	
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
	private AllowanceType allowanceType;
	
	@Property
	private List<AllowanceType> listAllowanceType;
	
	@Property
	private AllowanceType valueAllowanceType;
	
	void beginRender(){
		if(allowanceType ==null)
			allowanceType = new AllowanceType();
		
		listAllowanceType = dao.getListAllowanceType();
	}
	
	public int getNumber() {
		return listAllowanceType.indexOf(valueAllowanceType) + 1;
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(allowanceType);
		allowanceType = new AllowanceType();
	}
	
	void onActionFromEdit(AllowanceType allowanceType){
		this.allowanceType = allowanceType;
	}
	
	@CommitAfter
	void onActionFromDelete(AllowanceType allowanceType){
		dao.deleteObject(allowanceType);
	}

}
