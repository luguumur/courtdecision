package mn.mcs.electronics.court.pages.employee;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.components.CompEmp;
import mn.mcs.electronics.court.components.LayoutEmployee;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.City;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.Relatives;
import mn.mcs.electronics.court.enums.RelativeFamily;
import mn.mcs.electronics.court.model.CityProvinceSelectionModel;
import mn.mcs.electronics.court.model.DistrictSumSelectionModel;
import mn.mcs.electronics.court.model.FamilyAppointmentSelectionModel;
import mn.mcs.electronics.court.model.RelativeTypeSelectionModel;
import mn.mcs.electronics.court.util.Constants;
import mn.mcs.electronics.court.util.ExcelAPI;
import mn.mcs.electronics.court.util.ReportUtil;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.util.EnumSelectModel;

public class PageEmployeeFamily {
		
	@Inject
	private CoreDAO dao;
	
	@Inject
	private Messages messages;
	
	@Inject
	private Response response;
	
	@SessionState
	private LoginState loginState;
	
	@InjectComponent
	private LayoutEmployee layoutEmployee;
	
	@InjectPage
	private PageEmployeeDetail pageEmployee;
	
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
	
	/* form value */
	@Property
	@Persist
	private Relatives relative;
	
	@Persist
	private Employee employee;
	
	/* Grid value */
	@Property
	private List<Relatives> listRelative;
	
	@Property
	private List<Relatives> listFamily;
	
	@Property
	private Relatives valueRelative;
	
	@Persist
	private boolean viewEmployee;
	
	@InjectComponent
	private Zone aimagHotZone;

	private Long empID;

	@Inject
	private Request request;
	
	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);
	
	private static final String GRID_ROW_CSS = "myGrid";
	
	void onActivate(Long id) {
		empID = id;
	}
	
	@InjectComponent
	private Zone familyZone;
	
	@InjectComponent
	private Zone familyListZone;
	
	Long onPassivate(){
		return empID;
	}
	
	void beginRender(){
		
		viewEmployee = true;

		this.employee = (Employee) dao.getObject(Employee.class, empID);
		
		layoutEmployee.setValueEmployee(employee);
		
		listFamily = dao.getRelatives(employee.getId(),RelativeFamily.ISFAMILY);
		listRelative= dao.getRelatives(employee.getId(),RelativeFamily.ISRELATIVE);
		
		if(relative == null)
			relative = new Relatives();
	}
	
	public JSONObject getParam() {
		JSONObject defaults = new JSONObject();
	        defaults.put("modal", true);
	        defaults.put("resizable", false);
	        defaults.put("draggable", true);
	        defaults.put("autoOpen", false);
	        defaults.put("width", 450);
	        
        return defaults;
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		relative.setEmployee(employee);
		dao.saveOrUpdateObject(relative);
		relative = new Relatives();
	}
	
	@CommitAfter
	void onActionFromDelete(Relatives relative){
		dao.deleteObject(relative);
	}
	
	@CommitAfter
	void onActionFromDeleteFamily(Relatives relative){
		dao.deleteObject(relative);
	}
	
	void onActionFromEdit(Relatives relative){
		this.relative = relative;
	}
	
	void onActionFromEditFamily(Relatives relative){
		this.relative = relative;
	}
	
	
	void onActionFromCancel(){
		relative = new Relatives();
	}
	
	/*Ajax zone*/
	Object onValueChangedFromAimagHotClick() {
		return request.isXHR() ? aimagHotZone.getBody() : null;
	}
	
	
	/* Select model */
	public SelectModel getRelativeSelectionModel(){
	
		return new EnumSelectModel(RelativeFamily.class,messages);
	}
	
	public SelectModel getRelationSelectionModel(){
		RelativeTypeSelectionModel sm = new RelativeTypeSelectionModel(dao);	
		return sm;
	}
	
	public SelectModel getFamilyAppointmentSelectionModel(){
		FamilyAppointmentSelectionModel sm = new FamilyAppointmentSelectionModel(dao);	
		return sm;
	}
	
	public SelectModel getCityProvinceSelectionModel(){
		CityProvinceSelectionModel sm = new CityProvinceSelectionModel(dao);	
		return sm;
	}
	
	public SelectModel getDistrictSumSelectionModel(){
		/*if(relative.getBirthCityProvince()==null)
		{
			List<City> lst=dao.getListCity();
			relative.setBirthCityProvince(lst.get(0));
		}*/
		DistrictSumSelectionModel sm = new DistrictSumSelectionModel(dao, relative.getBirthCityProvince());	
		return sm;
	}


	/* Getter setter */
	
	public String getGridRowCSS() {
		return GRID_ROW_CSS;
	}

	public int getNumber(){
		return listRelative.indexOf(valueRelative)+1;
	}
	public int getNumberF(){
		return listFamily.indexOf(valueRelative)+1;
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
	
	public String getBirthday(){
		if(valueRelative==null || valueRelative.getBirthDate() == null)
			return "";
		return Integer.toString(valueRelative.getBirthDate());
	}
	
	public String getRelationName(){
		if(valueRelative==null||valueRelative.getRelation()==null)
			return "";
		return valueRelative.getRelation().getName();
	}
	
	public String getAppointmentName(){
		if(valueRelative==null||valueRelative.getAppointmentType()==null)
			return "";
		return valueRelative.getAppointmentType().getName();
	}
	
	public String getCurrentJob(){
		if(valueRelative==null||valueRelative.getOccupation()==null)
			return "";
		return valueRelative.getOccupation();
	}
	
	public Asset getImgExcel() {
		return imgExcel;
	}
	
	/* export excel */
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
					.get("employeeRelativeInformation"), styles.get("title"), 8);

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
					.get("relation-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3, messages
					.get("lastname-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 4, messages
					.get("firstname-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 5, messages
					.get("birthdateFamily-label"), styles.get("header-wrap"));			
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 6, messages
					.get("birthCityProvince-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 7, messages
					.get("appointmentName-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 8, messages
					.get("currentJob-label"), styles.get("header-wrap"));
			
			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;
			if (listRelative != null)
				for (Relatives s : listRelative) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (listRelative.indexOf(s)+1)+"", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,(s.getRelation()!=null)?s.getRelation().getName():"", styles
									.get("plain-left-wrap-border"));

					 
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getLastName()!=null)?s.getLastName().toString():"", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getFirstName()!=null)?s.getFirstName().toString():"", styles
									.get("plain-left-wrap-border"));

					 
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getBirthDate()!=null)?s.getBirthDate().toString():"", styles
									.get("plain-left-wrap-border"));
					
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getBirthCityProvince()!=null)?s.getBirthCityProvince().getCityName():"", styles
									.get("plain-left-wrap-border"));
					
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getAppointmentType()!=null)?s.getAppointmentType().getName():"", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getOccupation()!=null)?s.getOccupation():"", styles
									.get("plain-left-wrap-border"));
					
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
					"attachment; filename=\"employeeRelative.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/* export excel */
	void onActionFromExportFamily() {

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
					.get("employeeFamily"), styles.get("title"), 8);

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
					.get("relation-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3, messages
					.get("lastname-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 4, messages
					.get("firstname-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 5, messages
					.get("birthdateFamily-label"), styles.get("header-wrap"));			
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 6, messages
					.get("birthCityProvince-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 7, messages
					.get("appointmentName-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 8, messages
					.get("currentJob-label"), styles.get("header-wrap"));			
			
			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;
			if (listFamily != null)
				for (Relatives s : listFamily) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (listFamily.indexOf(s)+1)+"", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,(s.getRelation()!=null)?s.getRelation().getName():"", styles
									.get("plain-left-wrap-border"));

					 
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getLastName()!=null)?s.getLastName().toString():"", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getFirstName()!=null)?s.getFirstName().toString():"", styles
									.get("plain-left-wrap-border"));

					 
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getBirthDate()!=null)?Integer.toString(s.getBirthDate()):"", styles
									.get("plain-left-wrap-border"));
					
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getBirthCityProvince()!=null)?s.getBirthCityProvince().getCityName():"", styles
									.get("plain-left-wrap-border"));
					
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getAppointmentType()!=null)?s.getAppointmentType().getName():"", styles
									.get("plain-left-wrap-border"));
					
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getOccupation()!=null)?s.getOccupation():"", styles
									.get("plain-left-wrap-border"));

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
					"attachment; filename=\"employeeFamily.xls\"");

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
}

