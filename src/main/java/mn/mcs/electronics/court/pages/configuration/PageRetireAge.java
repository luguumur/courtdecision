package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.RetireAge;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageRetireAge {
	
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
	private RetireAge retireAge;
	
	@Property
	private List<RetireAge> listRetireAge;
	
	@Property
	private RetireAge valueRetireAge;
	
	void beginRender(){
		
		if(retireAge ==null)
			retireAge = new RetireAge();
		
		if(valueRetireAge ==null)
			valueRetireAge = new RetireAge();
		
		listRetireAge = dao.getListRetireAge();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(retireAge);
		retireAge = new RetireAge();
	}
	
	void onActionFromEdit(RetireAge retireAge){
		this.retireAge = retireAge;
	}
	
	/* getter, setter */
	
	public String getMaleAge(){
		if(valueRetireAge==null||valueRetireAge.getMaleAge()==null)
			return "";
		return valueRetireAge.getMaleAge().toString();
	}
	
	public String getFemaleAge(){
		if(valueRetireAge==null||valueRetireAge.getFemaleAge()==null)
			return "";
		return valueRetireAge.getFemaleAge().toString();
	}
	
	/*public String getGeneralJudgeDate(){
		if(valueRetireAge==null||valueRetireAge.getGeneralJudgeDate()==null)
			return "";
		return valueRetireAge.getGeneralJudgeDate().toString();
	}*/
}
