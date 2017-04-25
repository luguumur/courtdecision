package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.organization.FinancingType;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageFinancingType {
	
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
	private FinancingType financingType;
	
	@Property
	private List<FinancingType> listFinancingType;
	
	@Property
	private FinancingType valueFinancingType;
	
	void beginRender(){
		if(financingType ==null)
			financingType = new FinancingType();
		
		listFinancingType = dao.getListFinancingType();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(financingType);
		financingType = new FinancingType();
	}
	
	void onActionFromEdit(FinancingType financingType){
		this.financingType = financingType;
	}
	
	@CommitAfter
	void onActionFromDelete(FinancingType financingType){
		dao.deleteObject(financingType);
	}

	public int getNumber(){
		return listFinancingType.indexOf(valueFinancingType)+1; 
	}
}
