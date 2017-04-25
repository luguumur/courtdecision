package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.Appointment;
import mn.mcs.electronics.court.enums.AppointmentLevel;
import mn.mcs.electronics.court.enums.AppointmentType;
import mn.mcs.electronics.court.model.OccupationTypeSelectionModel;
import mn.mcs.electronics.court.model.OrganizationSelectionModel;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.EnumSelectModel;

public class PageAppointment {
	
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
	private Appointment appointment;
	
	@Property
	@Persist
	private AppointmentType appointmentType;
	
	@Property
	private List<Appointment> listAppointment;
	
	@Property
	private Appointment valueAppointment;
	
	void beginRender(){
		if(appointment ==null)
			appointment = new Appointment();
		
		listAppointment = dao.getListAppointment(null,null);
		
		if(appointmentType==null)
			appointmentType=AppointmentType.JUDGE;		
	}
	
	public int getNumber() {
		return listAppointment.indexOf(valueAppointment) + 1;
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		appointment.setAppointmentType(appointmentType);		
		dao.saveOrUpdateObject(appointment);
		
		appointment = new Appointment();
	}
	
	void onActionFromEdit(Appointment appointment){
		this.appointment = appointment;
	}

	@CommitAfter
	void onActionFromDelete(Appointment appointment){
		dao.deleteObject(appointment);
	}	
	
	public String getOccupationType(){
		if(valueAppointment==null||valueAppointment.getOccupationType()==null)
			return "";
		
		return valueAppointment.getOccupationType().getName();
	}
	
	public boolean isSalary(){
		if(appointmentType==AppointmentType.JUDGE)
			return true;
		
		return false;
	}
	
	public String getGuitsetgehAjliinNemegdel(){
		if(valueAppointment != null && valueAppointment.getGaNemegdel()== true)
			return "Тийм";
		
		if(valueAppointment != null && valueAppointment.getGaNemegdel()== false)
			return "Үгүй";
		
		return "";
	}
	
	/* select model */
	public SelectModel getLevelSelectionModel(){
		return new EnumSelectModel(AppointmentLevel.class,messages);
	}
	
	public SelectModel getTypeSelectionModel(){
		return new EnumSelectModel(AppointmentType.class,messages);
	}
	
	public SelectModel getOccupationTypeSelectionModel(){
		OccupationTypeSelectionModel sm= new OccupationTypeSelectionModel(dao);
		return sm;
	}
	
	public SelectModel getOrganizationSelectionModel(){
		OrganizationSelectionModel sm = new OrganizationSelectionModel(dao);
		return sm;
	}
}
