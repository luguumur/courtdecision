package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.SalaryNetwork;
import mn.mcs.electronics.court.enums.GetWage;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.EnumSelectModel;

public class PageSalaryNetwork {
	
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
	private SalaryNetwork salaryNetwork;
	
	@Property
	private List<SalaryNetwork> listSalaryNetwork;
	
	@Property
	private SalaryNetwork valueSalaryNetwork;
	
	void beginRender(){
		if(salaryNetwork ==null)
			salaryNetwork = new SalaryNetwork();
		
		listSalaryNetwork = dao.getListSalaryNetwork();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(salaryNetwork);
		salaryNetwork = new SalaryNetwork();
	}
	
	void onActionFromEdit(SalaryNetwork salaryNetwork){
		this.salaryNetwork = salaryNetwork;
	}
	
	@CommitAfter
	void onActionFromDelete(SalaryNetwork salaryNetwork){
		dao.deleteObject(salaryNetwork);
	}

	/* select model */
	public SelectModel getWageSelectionMoel(){
		return new EnumSelectModel(GetWage.class,messages);
	}
	
	public int getNumber(){
		return listSalaryNetwork.indexOf(valueSalaryNetwork)+1; 
	}
}
