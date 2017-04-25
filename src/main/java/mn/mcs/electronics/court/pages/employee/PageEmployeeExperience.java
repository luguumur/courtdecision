package mn.mcs.electronics.court.pages.employee;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.components.LayoutEmployee;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.Appointment;
import mn.mcs.electronics.court.entities.configuration.ApprovedPositions;
import mn.mcs.electronics.court.entities.employee.Displacement;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.Experience;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.EmployeeStatus;
import mn.mcs.electronics.court.enums.MilitaryOrSimple;
import mn.mcs.electronics.court.enums.OrganizationTypeExperience;
import mn.mcs.electronics.court.enums.YesNo;
import mn.mcs.electronics.court.model.AppointmentOrgSelectionModel;
import mn.mcs.electronics.court.model.ApprovedPositionSelectionModel;
import mn.mcs.electronics.court.model.CommandSubjectSelectionModel;
import mn.mcs.electronics.court.model.OrganizationSelectionModel;
import mn.mcs.electronics.court.util.Constants;
import mn.mcs.electronics.court.util.ExcelAPI;
import mn.mcs.electronics.court.util.ReportUtil;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.Field;
import org.apache.tapestry5.FieldValidator;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.FieldValidatorSource;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.util.EnumSelectModel;

public class PageEmployeeExperience {
	
	@Inject
	private CoreDAO dao;	
	
	@Inject 
    private FieldValidatorSource source;
	
	@Inject
	private Messages messages;
	
	@Inject
	private Response response;
	
	@InjectComponent 
    private Field outgoingDate; 
	
	@SessionState
	private LoginState loginState;

	@InjectComponent
	private LayoutEmployee layoutEmployee;
	
	@InjectPage
	private PageEmployeeDetail pageEmployee;
	
	@Persist
	private Employee employee;
	
	@Property
	@Persist
	private Experience experience;
	
	@Property
	@Persist
	private ApprovedPositions approvedPosition;
	
	@Property
	@Persist
	private Appointment appointment;
	
	@Property
	@Persist
	private Experience valueExperience;
	
	@Property
	@Persist
	private Displacement currentOccupation;
	
	@Property
	@Persist
	private Organization organizations;
	
	@Inject
	@Property
	@Path("context:/images/b_edit.png")
	private Asset editIcon;
	
	@Inject
	@Property
	@Path("context:/images/b_drop.png")
	private Asset deleteIcon;
	
	@Inject
	@Path("context:/images/excel.jpg")
	private Asset imgExcel;
	
	
	/* Grid value */
	@Property
	@Persist
	private List<Experience> listExperience;
	
	@Persist
	private boolean viewEmployee;
	
	/*@InjectComponent
	private Zone statusZone;
	
	@InjectComponent
	private Zone albanTushaalZone;*/

	@Inject
	private Request request;
	
	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;
	
	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);
	
	private static final String GRID_ROW_CSS = "myGrid";
	
	@Persist
	private boolean notWorking;
	
	@Persist
	@Property
	private boolean editMode;
	
	private Long empID;

	void onActivate(Long id) {
		empID = id;
	}

	Long onPassivate() {
		return empID;
	}
	
	void beginRender(){
		
		viewEmployee = true;

		this.employee = (Employee) dao.getObject(Employee.class, empID);

		layoutEmployee.setValueEmployee(employee);
		
//		layoutEmployee.setValueEmployee(getEmployee());
//	
//		this.employee = pageEmployee.getEmployee();
		
		if(experience == null)
			experience = new Experience();
		
		if(currentOccupation == null)
			currentOccupation = new Displacement();		
		
		listExperience = dao.getExperience(employee);
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		experience.setEmployee(employee);
		if(experience.getEndDate()!=null)
		{
			if(experience.getStartDate().compareTo(experience.getEndDate())<0||experience.getStatus()==EmployeeStatus.TIRED){
				experience.setAppointment(appointment.getAppointmentName());
				dao.saveOrUpdateObject(experience);
				experience= new Experience();
			}
			else{			
				//loginState.setErrMessage(messages.get("StartdateIsLaterThanEndDate"));
				System.err.println(messages.get("StartdateIsLaterThanEndDate"));
			}
		}
		else{
			if(experience.getStartDate().compareTo(new Date())<0&&experience.getStatus()==EmployeeStatus.WORKING){
				experience.setAppointment(appointment.getAppointmentName());		
				experience.setOrganizationname(getOrganizationName());
				experience.setOrganizationtype(OrganizationTypeExperience.SHUUH);
				
				dao.saveOrUpdateObject(experience);
				
				currentOccupation.setEmployee(employee);
				
				if(appointment!=null){
					currentOccupation.setOccupationType(appointment.getOccupationType());
					currentOccupation.setAppointment(appointment);
					currentOccupation.setIsNowDisplacement(true);
				}
				currentOccupation.setIssuedDate(experience.getStartDate());
				
				dao.saveOrUpdateObject(currentOccupation);
				
				
				employee.setAppointment(currentOccupation.getAppointment());
				dao.saveOrUpdateObject(employee);
				
				experience= new Experience();
			}
			else{			
//				loginState.setErrMessage(messages.get("StartdateIsBeforeThanCurrentDate"));
				System.err.println(messages.get("StartdateIsBeforeThanCurrentDate"));
			}	
		}		
		editMode=false;
	}
	
	void onActionFromEdit(Experience experience){
		editMode = true;
		this.experience =experience;
		
		if(experience.getStatus()==EmployeeStatus.WORKING ||
				(experience.getStatus()==EmployeeStatus.TIRED && experience.getOrganizationtype()==OrganizationTypeExperience.SHUUH)){ 
			
			//appointment=dao.getAppointmentByName(experience.getAppointment());
		}
	}
	
	void onActionFromCancel(){
		editMode = false;
		experience = new Experience();
	}
	
	@CommitAfter
	void onActionFromDelete(Experience experience){
		dao.deleteObject(experience);
	}
	
	@CommitAfter
	void onSelectedFromSaveExamPlaceholder(){
		dao.saveOrUpdateObject(employee);		
	}
	
	/*getter,setter*/
	
	public String getGridRowCSS() {
		return GRID_ROW_CSS;
	}
	
	public int getNumber(){
		return listExperience.indexOf(valueExperience)+1;
	}
	
	public Asset getImgExcel() {
		return imgExcel;
	}
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public String getDateFormat(){
		return Constants.DATE_FORMAT;
	}
	
	public String getStartDay(){
		if(valueExperience==null||valueExperience.getStartDate()==null)
			return "";
		return format.format(valueExperience.getStartDate());
	}
	
	public String getEndDay(){
		if(valueExperience==null||valueExperience.getEndDate()==null)
			return "";
		return format.format(valueExperience.getEndDate());
	}
	
	/* Dec 07, 2012 Jargalsuren.S Start */
	public String getWorkedYear(){
		Long startDate;
		Long endDate;
		Date nowDate = new Date();
		
		if(valueExperience ==null||valueExperience.getStartDate()==null)
			startDate = 0l;
		else
			startDate = valueExperience.getStartDate().getTime();
		
		if(valueExperience==null||valueExperience.getEndDate()==null)			
			endDate = nowDate.getTime();
		else
			endDate = valueExperience.getEndDate().getTime();
				
		Long year = endDate - startDate; 
		
		Long diffDays = year / (24 * 60 * 60 * 1000);		
		Long diffYears = diffDays / 365;
		diffDays = diffDays - diffYears*365;
		Long diffMonths = diffDays / 30;
		diffDays = diffDays - diffMonths*30;
			
		return diffYears + " жил " + diffMonths + " сар " + diffDays + " хоног";		
	}

	public String getWorkedYearExport(Experience exp){
		Long startDate;
		Long endDate;
		Date nowDate = new Date();
		
		if(exp ==null||exp.getStartDate()==null)
			startDate = 0l;
		else
			startDate = exp.getStartDate().getTime();
		
		if(exp==null||exp.getEndDate()==null)			
			endDate = nowDate.getTime();
		else
			endDate = exp.getEndDate().getTime();
				
		Long year = endDate - startDate; 
		
		Long diffDays = year / (24 * 60 * 60 * 1000);		
		Long diffYears = diffDays / 365;
		diffDays = diffDays - diffYears*365;
		Long diffMonths = diffDays / 30;
		diffDays = diffDays - diffMonths*30;
			
		return diffYears + " жил " + diffMonths + " сар " + diffDays + " хоног";		
	}
	/* Dec 07, 2012 Jargalsuren.S End */
	
	public String getIsWorkingJudge(){
		if(valueExperience==null||valueExperience.getIsJudge()==null)
		 return "";
		if(valueExperience==null||valueExperience.getIsJudge()==true)
			return messages.get("yes");
		return messages.get("no");
	}	
	
	public String getIsWorkingMajor(){		
		if(valueExperience==null||valueExperience.getIsMajor()==null)
			return "";
		if(valueExperience==null||valueExperience.getIsMajor()==true)
			return messages.get("yes");
		return messages.get("no");
	}
	
	public FieldValidator getCustomValidation(){		
		 String validationString = "none"; 
	        if(experience.getStatus()==EmployeeStatus.TIRED) { 
	            validationString = "required"; 
	        } 
	        return source.createValidator(outgoingDate, validationString, null); 
		
	}
	
	public String getOrganizationName(){
		if(employee.getOrganization()==null||employee.getOrganization().getName()==null)
			return "";
		
		return employee.getOrganization().getName();
	}
	
	/* Dec 07, 2012 Jargalsuren.S Start */
	public String getOrganizationTypeName(){
		if(experience.getOrganizationtype()==null)
			return "";
		
		return experience.getOrganizationtype().name();
	}
	/* Dec 07, 2012 Jargalsuren.S End */
	
	 public YesNo getYes() {
		 return YesNo.YES; 
	}

	 public YesNo getNo() { 
		 return YesNo.NO; }
	 
	 public YesNo getYesSwear() {		 
		 return YesNo.YES; }

	 public YesNo getNoSwear() { 
		 return YesNo.NO; }
	 
	 public YesNo getYesDeclaration() {		 
		 return YesNo.YES; }

	 public YesNo getNoDeclaration() { 
		 return YesNo.NO; }
	 
	 public boolean isNowWorking(){
		
		 if(isNotWorking())
			 return false;
		 
		 if(experience.getStatus()==EmployeeStatus.WORKING||experience.getStatus()==null)
			 return true;
		 
		
		 return false;
	 }
	 	
	 public boolean isShuuhBaiguullaga(){
		 if(experience.getStatus()==null)
			 experience.setStatus(EmployeeStatus.WORKING);
		 
		 if(experience.getOrganizationtype()==null)
			 experience.setOrganizationtype(OrganizationTypeExperience.ULSIIN);
		 
		 //if(experience.getOrganizationtype()!=OrganizationTypeExperience.SHUUH||experience.getStatus()==EmployeeStatus.WORKING)
		 if(experience.getStatus()==EmployeeStatus.WORKING)
			 return false;
		 
		 if(isNotWorking())
			 return false;	 
		
		 
		 return true;
	 }
	 
	 /*Ajax zone*/
		/*Object onValueChangedFromStatusClick() {
			if(request.isXHR()){
				ajaxResponseRenderer.addRender(statusZone).addRender(albanTushaalZone);
				}
			return null;
		}
		
		Object onValueChangedFromOrganizationTypeClick() {
			if(request.isXHR()){
				ajaxResponseRenderer.addRender(statusZone).addRender(albanTushaalZone);
				}
			return null;
		}*/
	
	/* selection model */
	public SelectModel getOrganizationTypeSelectionModel(){
		return new EnumSelectModel(OrganizationTypeExperience.class,messages);
	}
	
	public SelectModel getEmployeeStatusSelectionModel(){
		
		EnumSelectModel status=new EnumSelectModel(EmployeeStatus.class,messages);
		
		boolean hasNowWorking=false;
		
		if(experience !=null && (experience.getId()==null)){
			if(listExperience!=null && listExperience.size()>0){
				
				for(Experience ex:listExperience){
					if(ex.getStatus().equals(EmployeeStatus.WORKING))
						hasNowWorking=true;
				}
			}
			
			if(hasNowWorking){
				
				status.getOptions().remove(EmployeeStatus.WORKING.getVal());
				experience.setStatus(EmployeeStatus.TIRED);
			}
		}/*else
			experience.setStatus(EmployeeStatus.WORKING);*/
		
		return status; 
	}
	
	public SelectModel getAppointmentSelectionModel(){
		AppointmentOrgSelectionModel sm = new AppointmentOrgSelectionModel(dao);
		return sm;
	}
	
	public SelectModel getApprovedPositionSelectionModel(){
		ApprovedPositionSelectionModel sm = new ApprovedPositionSelectionModel(dao,employee.getOrganization(),0);
		return sm;
	}
	
	public SelectModel getOrganizationSelectionModel(){
		OrganizationSelectionModel sm = new OrganizationSelectionModel(dao);
		return sm;
	}
	
	/* Dec 07, 2012 Jargalsuren.S Start */
	public SelectModel getCommandSubjectSelectionModel(){
		CommandSubjectSelectionModel sm = new CommandSubjectSelectionModel(dao);
		return sm;
	}	
	
	public SelectModel getMilitaryOrSimpleSelectionModel(){
		return new EnumSelectModel(MilitaryOrSimple.class,messages);
	}
	/* Dec 07, 2012 Jargalsuren.S End */

	void onActionFromExport() {

		try {
			HSSFWorkbook document = new HSSFWorkbook();
			HSSFSheet sheet = ReportUtil.createSheet(document);

			sheet.setMargin((short) 0, 0.5);
			ReportUtil.setColumnWidths(sheet, 0, 0.5, 1, 0.5, 2, 2, 3, 2, 3, 2,
					4, 3, 5, 2, 6, 2, 7, 2, 8, 2, 9, 2);

			Map<String, HSSFCellStyle> styles = ReportUtil
					.createStyles(document);

			int sheetNumber = 0;
			int rowIndex = 1;
			int colIndex = 1;
			Long index = 1L;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2, messages
					.get("employeeExpierence"), styles.get("title"), 5);

			ExcelAPI.setCellValue(document, sheetNumber, ++rowIndex, 1,
					messages.get("date") + ": " + format.format(new Date()),
					styles.get("plain-left-wrap"), 5);
			ExcelAPI.setCellValue(document, sheetNumber, ++rowIndex, 1,
					messages.get("employeeLastNameFirstName") + ": " + employee.getLastname()+" "+ messages.get("lastnme")+ " " +employee.getFirstName(),
					styles.get("plain-left-wrap"), 5);
			rowIndex += 2;

			/* column headers */

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1, messages
					.get("number-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2, messages
					.get("organizationtype-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3, messages
					.get("organizationname-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 4, messages
					.get("appointment-label"), styles.get("header-wrap"));
			/* Dec 07, 2012 Jargalsuren.S Start */
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 5, messages
					.get("status-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 6, messages
					.get("militaryOrSimple-label"), styles.get("header-wrap"));
			/* Dec 07, 2012 Jargalsuren.S End */
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 7, messages
					.get("issuedDate-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 8, messages
					.get("outgoingDate-label"), styles.get("header-wrap"));
			/* Dec 07, 2012 Jargalsuren.S Start */
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 9, messages
					.get("workedYear-label"), styles.get("header-wrap"));
			/* Dec 07, 2012 Jargalsuren.S End */
			
			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;
			if (listExperience != null)
				for (Experience s : listExperience) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,( listExperience.indexOf(s)+1)+"", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, messages.get(s.getOrganizationtype().toString()), styles
									.get("plain-left-wrap-border"));

					 
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, s.getOrganizationname().toString(), styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getAppointment()!=null)?s.getAppointment().toString():"", styles
									.get("plain-left-wrap-border"));

					/* Dec 07, 2012 Jargalsuren.S Start */
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, messages.get(s.getStatus().toString()), styles.get("plain-left-wrap-border"));
					
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, messages.get(s.getMilitaryOrSimple().toString()), styles.get("plain-left-wrap-border"));
					/* Dec 07, 2012 Jargalsuren.S End */
					 
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getStartDate()!=null)?format.format(s.getStartDate()):"", styles
									.get("plain-left-wrap-border"));
					
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getEndDate()!=null)?format.format(s.getEndDate()):"", styles
									.get("plain-left-wrap-border"));

					/* Dec 07, 2012 Jargalsuren.S Start */
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, getWorkedYearExport(s), styles.get("plain-left-wrap-border"));
					/* Dec 07, 2012 Jargalsuren.S End */
					
					ReportUtil.setRowHeight(sheet, rowIndex, 3);
					rowIndex++;
					index++;
					colIndex = 1;

				}

			rowIndex += 2;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1,
					"ТАЙЛАН ГАРГАСАН: " + ".....................................  / "
							+ loginState.getEmployee().getLastname().charAt(0) + "." +loginState.getEmployee().getFirstName() + " /", styles
							.get("plain-left-wrap"), 8);
			rowIndex++;

			OutputStream out = response
					.getOutputStream("application/vnd.ms-excel");
			response.setHeader("Content-Disposition",
					"attachment; filename=\"employeeExperience.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isShow(){
		if(viewEmployee==true)
			return true;
		else return false;
	}

	public boolean isViewEmployee() {
		return viewEmployee;
	}

	public void setViewEmployee(boolean viewEmployee) {
		this.viewEmployee = viewEmployee;
	}

	 public boolean isNotWorking(){
		 
		 if(experience.getStatus()==null)
			 experience.setStatus(EmployeeStatus.WORKING);
		 
		 if(experience.getOrganizationtype()==null)
		 	experience.setOrganizationtype(OrganizationTypeExperience.ULSIIN);
		 
		 //if(experience.getStatus()==EmployeeStatus.WORKING||experience.getOrganizationtype()==OrganizationTypeExperience.SHUUH)
		 if(experience.getStatus()==EmployeeStatus.WORKING)
			 notWorking = false;
		 else
			 notWorking = true;
		 
		 /*if(editMode)
			 notWorking = true;*/
		
		 return notWorking;
	 }

	 
}
