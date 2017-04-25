package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.salary.AdditionalSalaryType;
import mn.mcs.electronics.court.enums.AdditionalSalaryCategory;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.EnumSelectModel;

public class PageAdditionalSalary {
	
	@Inject
	private CoreDAO dao;
	
	@Inject
	private Messages messages;
	
	@Property
	@Inject
	@Path("context:/images/b_edit.png")
	private Asset editIcon;
	
	@Property
	@Inject
	@Path("context:/images/b_drop.png")
	private Asset deleteIcon;

	@Property
	@Persist
	private AdditionalSalaryType salary;
	
	@Property
	@Persist
	private List<AdditionalSalaryType> listAdditionalSalaryType;
	
	@Property
	@Persist
	private AdditionalSalaryType valueAdditionalSalaryType;
	
	void beginRender(){
		
		if(salary==null)
			salary = new AdditionalSalaryType();
		
		listAdditionalSalaryType = dao.getListAdditionalSalaryTypes(null);
	}
	
	public int getNumber() {
		return listAdditionalSalaryType.indexOf(valueAdditionalSalaryType)+1;
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(salary);
		salary = new AdditionalSalaryType();
	}
	
	void onActionFromEdit(AdditionalSalaryType type){
		this.salary = type;
	}
	
	@CommitAfter
	void onActionFromDelete(AdditionalSalaryType type){
		dao.deleteObject(type);	
	}
	
	/* select model */
	public SelectModel getCategorySelectionModel(){
		return new EnumSelectModel(AdditionalSalaryCategory.class, messages);
	}
}
