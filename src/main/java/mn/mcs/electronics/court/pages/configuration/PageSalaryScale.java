package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.SalaryScale;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageSalaryScale {
	
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
	private SalaryScale salaryScale;
	
	@Property
	private List<SalaryScale> listSalaryScale;
	
	@Property
	private SalaryScale valueSalaryScale;
	
	void beginRender(){
		if(salaryScale ==null)
			salaryScale = new SalaryScale();
		
		listSalaryScale = dao.getListSalaryScale();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(salaryScale);
		salaryScale = new SalaryScale();
	}
	
	void onActionFromEdit(SalaryScale salaryScale){
		this.salaryScale = salaryScale;
	}
	
	@CommitAfter
	void onActionFromDelete(SalaryScale salaryScale){
		dao.deleteObject(salaryScale);
	}

}
