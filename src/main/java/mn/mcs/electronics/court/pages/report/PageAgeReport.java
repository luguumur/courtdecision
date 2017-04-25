package mn.mcs.electronics.court.pages.report;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.AppointmentType;
import mn.mcs.electronics.court.model.OrganizationSelectionModel;
import mn.mcs.electronics.court.util.CalendarUtil;
import mn.mcs.electronics.court.util.Constants;
import mn.mcs.electronics.court.util.ExcelAPI;
import mn.mcs.electronics.court.util.ReportUtil;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.util.EnumSelectModel;

public class PageAgeReport {
	
	@SessionState
	private LoginState loginState;
	
	@Inject
	private Response response;
	
	@Inject 
	private CoreDAO dao;
	
	@Inject
	private Messages messages;
	
	@Property
	@Persist
	private List<Employee> listEmployee;
	
	@Property
	@Persist
	private Employee valueEmployee;
	
	private static final String gridRowCss = "myGrid";
	
	@Inject
	@Path("context:/images/excel.jpg")
	private Asset imgExcel;
	
	@Property
	@Persist
	private Organization org;
	
	@Property
	@Persist
	private AppointmentType appointmentType;
	
	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);
	
	@Property
	@Persist
	private List<Integer> listAge;
	
	@Persist
	private Integer valueAge;
	
	@Persist
	private Boolean onload;
	
	private int i = -1;

	private Subject currentUser =  SecurityUtils.getSubject();
	
	void beginRender(){
	
		if(!currentUser.isPermitted("show_all_organization"))
			org = loginState.getOrganization();
		
		listEmployee = dao.getListEmployeeAgeReport(org, appointmentType);
		
		listAge = new ArrayList<Integer>();
	
	
			for(int i =0; i<6; i++){
	
		
				listAge.add(0);
			}
		
		if(!listEmployee.isEmpty()){
			for(Employee emp : listEmployee){
				if(emp.getBirthDate()!=null)
					count(CalendarUtil.calculateDifferent(emp.getBirthDate()));
			}
		}
		
	}
	
	private void count(Object obj){
		long age = (Long) obj;
	
		if(age <= 35)
			listAge.set(0,(listAge.get(0)+1));
		
		if(age >= 36 && age<=40)
			listAge.set(1, (listAge.get(1)+1));
		
		if(age >=41 && age<=45)
			listAge.set(2, (listAge.get(2)+1));
		
		if(age >=46 && age<=50)
			listAge.set(3, (listAge.get(3)+1));
		
		if(age >=51 && age<=55)
			listAge.set(4, (listAge.get(4)+1));
		
		if(age >=56 && age<=60)
			listAge.set(5, (listAge.get(5)+1));
		
		
	}
	
	/* select model */
	public SelectModel getOrganizationSelectionModel(){
		OrganizationSelectionModel sm = new OrganizationSelectionModel(dao);
		
		return sm;
	}
	
	public SelectModel getAppointmentTypeSelectionModel(){
		return new EnumSelectModel(AppointmentType.class,messages);
	}
	
	
	/* getter setter */
	
	public String getGridrowcss() {
		return gridRowCss;
	}
	
	public Integer getValueAge() {
		return valueAge;
	}

	public void setValueAge(Integer valueAge) {
		this.valueAge = valueAge;
	}
	
	public Asset getImgExcel() {
		return imgExcel;
	}

	public String getLevel(){
		i++;
		
		if(i==0)
			return "35-с доош";
		if(i==1)
			return "35-40";
		if(i==2)
			return "40-45";
		if(i==3)
			return "45-50";
		if(i==4)
			return "50-55";
		if(i==5)
			return "55-60";
		else
			return "";
	
	}
	
	/* export to excel */
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
			int index = 1;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2, messages
					.get("ageReportList"), styles.get("title"), 5);

			rowIndex++;
			
			ExcelAPI.setCellValue(document, sheetNumber, ++rowIndex, 1,
					messages.get("date") + ": " + format.format(new Date()),
					styles.get("plain-left-wrap"), 2);
			
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3,
					messages.get("org-label")+": "+((org!=null&&org.getName()!=null)?org.getName():messages.get("all")),
					styles.get("plain-left-wrap"), 3);
			
			rowIndex += 2;

			/* column headers */

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1, messages
					.get("number-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2, messages
					.get("level-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3, messages
					.get("countOfficer-label"), styles.get("header-wrap"));
			
			
			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;
			i = -1;
			if (listAge != null)
				for (Integer s : listAge) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, listAge.indexOf(s)+"", styles
									.get("plain-left-wrap-border"));
		
				
					
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,getLevel() + "", styles
							
									.get("plain-left-wrap-border"));

					 
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,listAge.get(index-1).toString(), styles
									.get("plain-left-wrap-border"));

					 
				

					ReportUtil.setRowHeight(sheet, rowIndex, 3);
					rowIndex++;
					index++;
					colIndex = 1;

				}

			rowIndex += 2;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1,
					"ТАЙЛАН ГАРГАСАН: "
					//+ loginState.getLogin()
					+ "......................  / "
					+ loginState.getEmployee().getLastname().charAt(0) + "."
					+ loginState.getEmployee().getFirstName() + " /", styles
					.get("plain-left-wrap"), 8);
			rowIndex++;

			OutputStream out = response
					.getOutputStream("application/vnd.ms-excel");
			response.setHeader("Content-Disposition",
					"attachment; filename=\"employeeAgeReport.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
