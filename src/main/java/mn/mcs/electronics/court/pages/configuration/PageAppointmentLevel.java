package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.AppointmentLevel;
import mn.mcs.electronics.court.entities.configuration.AppointmentSort;
import mn.mcs.electronics.court.enums.AppointmentType;
import mn.mcs.electronics.court.model.AppointmentSortSelectionModel;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.EnumSelectModel;

public class PageAppointmentLevel {
	
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
	private AppointmentLevel appointmentLevel;
	
	@Property
	@Persist
	private AppointmentSort appointmentSort;
	
	@Property
	private List<AppointmentLevel> listAppointmentLevel;
	
	@Property
	private AppointmentLevel valueAppointmentLevel;
	
	void beginRender(){
		if(appointmentLevel ==null)
			appointmentLevel = new AppointmentLevel();
		
		listAppointmentLevel = dao.getListAppointmentLevel();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(appointmentLevel);
		appointmentLevel = new AppointmentLevel();
	}
	
	void onActionFromEdit(AppointmentLevel appointmentLevel){
		this.appointmentLevel = appointmentLevel;
	}
	
	@CommitAfter
	void onActionFromDelete(AppointmentLevel appointmentLevel){
		dao.deleteObject(appointmentLevel);
	}
	
	/* Selection model */
	public SelectModel getAppointmentSortSelectionModel(){
		AppointmentSortSelectionModel sm = new AppointmentSortSelectionModel(dao,null);
		
		return sm;
	}
	
	public SelectModel getAppointmentTypeSelectionModel(){
		return new EnumSelectModel(AppointmentType.class,messages);
	}
	
	/*getter,setter*/
	
	public String getAppointmentSortName(){
		if(valueAppointmentLevel==null|| valueAppointmentLevel.getAppointmentSort()==null)
			return "";
		return valueAppointmentLevel.getAppointmentSort().getAppointmentSortName();
		
	}
}
