package mn.mcs.electronics.court.pages.organization;

import java.text.SimpleDateFormat;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.Appointment;
import mn.mcs.electronics.court.entities.configuration.ApprovedPositions;
import mn.mcs.electronics.court.entities.configuration.TuAaSort;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuSort;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.Gender;
import mn.mcs.electronics.court.util.Constants;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageOrganization {
	
	@Inject 
	private CoreDAO dao;
	
	@Persist
	private Organization organizationInfo;

	@Persist
	private Organization organization;
	
	@Property
	@Persist
	private List<Object[]> listDisplacement;
	
	@Property
	@Persist
	private Object[] valueDisplacement;
	
	@Property
	@Persist
	private List<Object[]> listDisplacementMale;
	
	@Property
	@Persist
	private Object[] valueDisplacementMale;
	
	@Property
	@Persist
	private List<Object[]> listDisplacementFemale;
	
	@Property
	@Persist
	private Object[] valueDisplacementFemale;
	
	@Property
	@Persist
	private List<Object[]> listDisplacement2;
	
	@Property
	@Persist
	private Object[] valueDisplacement2;
	
	@Property
	@Persist
	private List<Object[]> listDisplacement2Male;
	
	@Property
	@Persist
	private Object[] valueDisplacement2Female;
	
	@Property
	@Persist
	private List<Object[]> listDisplacement2Female;
	
	@Property
	@Persist
	private Object[] valueDisplacement2Male;
	
	@Property
	@Persist
	private List<ApprovedPositions> listApprovedPositions;
	
	@Property
	@Persist
	private ApprovedPositions valueApprovedPositions;
	
	@Property
	@Persist
	private List<Object[]> listAppointmentQuantity;
	
	@Property
	@Persist
	private Object[] valueAppointmentQuantity;
	
	@Persist
	private String ceoName;
	
	@Persist
	private String ceoLName;
	
	@Persist
	private String ceoBDate;
	
	@Persist
	private String ceoPNumber;
	
	@Persist
	private String ceoAscription;
	
	@Persist
	private String ceoEmail;
	
	private SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);

	void beginRender(){
		
		listDisplacement = dao.getListDisplacementTypeSearch(organizationInfo, "utTzTtTu", null);
		listDisplacement2 = dao.getListDisplacementTypeSearch(organizationInfo, "tuAa", null);
		
		listDisplacementMale = dao.getListDisplacementTypeSearch(organizationInfo, "utTzTtTu", Gender.MALE);
		listDisplacement2Male = dao.getListDisplacementTypeSearch(organizationInfo, "tuAa", Gender.MALE);
		
		listDisplacementFemale = dao.getListDisplacementTypeSearch(organizationInfo, "utTzTtTu", Gender.FEMALE);
		listDisplacement2Female = dao.getListDisplacementTypeSearch(organizationInfo, "tuAa", Gender.FEMALE);
		
		listApprovedPositions = dao.getListApprovedPositions(organizationInfo);

		listAppointmentQuantity = dao.getListAppointmentQuantity(organizationInfo);
		List<Employee> ceo = dao.getListEmployeeCeoSearch(organizationInfo);

		for(Employee obj : ceo)
		{
			if(obj.getLastname() == null)
				ceoLName = "";
			else
				ceoLName = obj.getLastname();
			
			if(obj.getFirstName() == null)
				ceoName = "";
			else
				ceoName = obj.getFirstName();
			
			if(obj.getPhoneNo() == null)
				ceoPNumber = "";
			else
				ceoPNumber = obj.getPhoneNo();
			
			if(obj.getBirthDate() == null)
				ceoBDate = "";
			else
				ceoBDate = format.format(obj.getBirthDate());
			
			if(obj.getAscription() == null)
				ceoAscription = "";
			else
				ceoAscription = obj.getAscription().getName();
			
			if(obj.geteMail() == null)
				ceoEmail = "";
			else
				ceoEmail = obj.geteMail();
		}	
	}
	
	/* getter, setter */
	
	public Organization getOrganizationInfo() {
		return organizationInfo;
	}

	public void setOrganizationInfo(Organization organizationInfo) {
		this.organizationInfo = organizationInfo;
	}

	public String getCeoName() {
		return ceoName;
	}

	public void setCeoName(String ceoName) {
		this.ceoName = ceoName;
	}

	public String getCeoLName() {
		return ceoLName;
	}

	public void setCeoLName(String ceoLName) {
		this.ceoLName = ceoLName;
	}

	public String getCeoPNumber() {
		return ceoPNumber;
	}

	public void setCeoPNumber(String ceoPNumber) {
		this.ceoPNumber = ceoPNumber;
	}

	public String getCeoBDate() {
		return ceoBDate;
	}

	public void setCeoBDate(String ceoBDate) {
		this.ceoBDate = ceoBDate;
	}


	public String getCeoEmail() {
		return ceoEmail;
	}

	public void setCeoEmail(String ceoEmail) {
		this.ceoEmail = ceoEmail;
	}

	public String getCeoAscription() {
		return ceoAscription;
	}

	public void setCeoAscription(String ceoAscription) {
		this.ceoAscription = ceoAscription;
	}
	
	public String getDisplacementType(){
		if(valueDisplacement==null)
			return "";
		
			UtTzTtTuSort d = new UtTzTtTuSort();
			d = (UtTzTtTuSort) valueDisplacement[1];
		
			return d.getName();
		
	}
	
	public String getDisplacement2Type(){
		if(valueDisplacement2==null)
			return "";
		
			TuAaSort d = new TuAaSort();
			d = (TuAaSort) valueDisplacement2[1];
		
			return d.getName();
	}
	
	public String getCountDis(){
		if(valueDisplacement==null)
			return "0";
		
		return valueDisplacement[0].toString();
	}
	
	public String getCountDis2(){
		if(valueDisplacement2==null)
			return "0";
		
		return valueDisplacement2[0].toString();
	}
	
	public String getCountDisMale(){
		if(valueDisplacementMale==null)
			return "0";
		
		return valueDisplacementMale[0].toString();
	}
	
	public String getCountDis2Male(){
		if(valueDisplacement2Male==null)
			return "0";
		
		return valueDisplacement2Male[0].toString();
	}
	
	public String getCountDisFemale(){
		if(valueDisplacementFemale==null)
			return "0";
		
		return valueDisplacementFemale[0].toString();
	}
	
	public String getCountDis2Female(){
		if(valueDisplacement2Female==null)
			return "0";
		
		return valueDisplacement2Female[0].toString();
	}
	
	public String getApprovedPositions(){
		if(valueApprovedPositions==null)
			return "";
		
		return valueApprovedPositions.getAppointment().getAppointmentName();
		
	}
	
	public String getCountApprovedPositions(){
		if(valueApprovedPositions==null)
			return "";
		
		return valueApprovedPositions.getEstablishment().toString();
	}
	
	public String getAppointmentQuantity(){
		if(valueAppointmentQuantity==null)
			return "0";
		
		Appointment d = new Appointment();
		d = (Appointment) valueAppointmentQuantity[1];
		
		return d.getAppointmentName();	
	}
	
	public String getCountAppointmentQuantity(){
		if(valueAppointmentQuantity == null)
			return "0";
		return valueAppointmentQuantity[0].toString();
	}
}
