package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.VacationType;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageVacationType {
	
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
	private VacationType vacationType;
	
	@Property
	private List<VacationType> listVacationType;
	
	@Property
	private VacationType valueVacationType;
	
	void beginRender(){
		if(vacationType ==null)
			vacationType = new VacationType();
		
		listVacationType = dao.getListVacationType();
	}
	
	public int getNumber() {
		return listVacationType.indexOf(valueVacationType) + 1;
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(vacationType);
		vacationType = new VacationType();
	}
	
	void onActionFromEdit(VacationType vacationType){
		this.vacationType = vacationType;
	}

	@CommitAfter
	void onActionFromDelete(VacationType vacationType){
		dao.deleteObject(vacationType);
	}
}
