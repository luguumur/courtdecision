package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.AppointmentSort;
import mn.mcs.electronics.court.enums.AppointmentType;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.EnumSelectModel;

public class PageAppointmentSort {
	
	@Inject
	private CoreDAO dao;
	
	@Inject
	private Messages messages;
	
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
	private AppointmentSort appointmentSort;
	
	@Property
	private List<AppointmentSort> listAppointmentSort;
	
	@Property
	private AppointmentSort valueAppointmentSort;
	
	void beginRender(){
		if(appointmentSort ==null)
			appointmentSort = new AppointmentSort();
		
		listAppointmentSort = dao.getAppointmentSort();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(appointmentSort);
		appointmentSort = new AppointmentSort();
	}
	
	void onActionFromEdit(AppointmentSort appointmentSort){
		this.appointmentSort = appointmentSort;
	}
	
	@CommitAfter
	void onActionFromDelete(AppointmentSort appointmentSort){
		dao.deleteObject(appointmentSort);
	}
	
	/* selection model */
	public SelectModel getAppointmentSortNameSelectionModel(){
		return new EnumSelectModel(AppointmentType.class,messages);
	}
	

}
