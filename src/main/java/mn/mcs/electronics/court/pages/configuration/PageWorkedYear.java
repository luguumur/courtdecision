package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.WorkedYear;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageWorkedYear {
	
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
	private WorkedYear workedYear;
	
	@Property
	private List<WorkedYear> listWorkedYear;
	
	@Property
	private WorkedYear valueWorkedYear;
	
	void beginRender(){
		
		if(workedYear ==null)
			workedYear = new WorkedYear();
		
		if(valueWorkedYear ==null)
			valueWorkedYear = new WorkedYear();
		
		listWorkedYear = dao.getListWorkedYear();
		
		if(listWorkedYear!=null&&listWorkedYear.size()>0)
		workedYear=dao.getListWorkedYear().get(0);
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(workedYear);
		//workedYear = new WorkedYear();
	}
	
	void onActionFromEdit(WorkedYear workedYear){
		this.workedYear = workedYear;
	}
	
	/* getter, setter */
	
	public String getStateWorkedYear(){
		if(valueWorkedYear==null||valueWorkedYear.getStateWorkedYear()==null)
			return "";
		return valueWorkedYear.getStateWorkedYear().toString();
	}
	
	public String getMajorWorkedYear(){
		if(valueWorkedYear==null||valueWorkedYear.getMajorWorkedYear()==null)
			return "";
		return valueWorkedYear.getMajorWorkedYear().toString();
	}
	
}
