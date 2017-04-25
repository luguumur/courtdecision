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
import mn.mcs.electronics.court.entities.employee.Experience;
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

public class PageWorkedYearReport {
	
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
	private List<Experience> listExperience;
	
	@Property
	@Persist
	private Experience valueExperience;
	
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
	private List<Integer> listWorkedYear;
	
	@Persist
	private Integer valueWorkedYear;
	
	@Persist
	private Boolean onload;
	
	private int i = -1;

	@Property
	@Persist
	private List<Object[]> listObj;
	
	
	
	void onActivate(){
		System.err.println("activate");
	}
	
	void beginRender(){
		listExperience = dao.getListEmployeeWorkedYearReport(org, appointmentType);
		
		listWorkedYear = new ArrayList<Integer>();
	
	
			for(int i =0; i<6; i++){
	
		
				listWorkedYear.add(0);
			}
		
		if(!listExperience.isEmpty()){
			for(Experience currentOccupation : listExperience){
			if(currentOccupation.getStartDate()!=null)
					count(CalendarUtil.calculateDifferent(currentOccupation.getStartDate()));
			}
				
			}
		
		
	}
	
	private void count(Object obj){
		long age = (Long) obj;
	
		if(age <= 5)
			listWorkedYear.set(0,(listWorkedYear.get(0)+1));
		
		if(age >= 6 && age<=10)
			listWorkedYear.set(1, (listWorkedYear.get(1)+1));
		
		if(age >=11 && age<=15)
			listWorkedYear.set(2, (listWorkedYear.get(2)+1));
		
		if(age >=16 && age<=20)
			listWorkedYear.set(3, (listWorkedYear.get(3)+1));
		
		if(age >=21 && age<=25)
			listWorkedYear.set(4, (listWorkedYear.get(4)+1));
		
		if(age >=26 && age<=30)
			listWorkedYear.set(5, (listWorkedYear.get(5)+1));
		
		
	}
	
	/* select model */
	public SelectModel getOrganizationSelectionModel(){
		OrganizationSelectionModel sm = new OrganizationSelectionModel(dao);
		
		return sm;
	}
	
	
	/* getter setter */
	
	public String getGridrowcss() {
		return gridRowCss;
	}
	
	public Integer getValueWorkedYear() {
		return valueWorkedYear;
	}

	public void setValueWorkedYear(Integer valueWorkedYear) {
		this.valueWorkedYear = valueWorkedYear;
	}
	
	public Asset getImgExcel() {
		return imgExcel;
	}

	public String getLevel(){
		i++;
		
		if(i==0)
			return "5-с доош";
		if(i==1)
			return "5-10";
		if(i==2)
			return "10-15";
		if(i==3)
			return "15-20";
		if(i==4)
			return "20-25";
		if(i==5)
			return "25-30";
		else
			return "";
	
	}
	
	public SelectModel getAppointmentTypeSelectionModel(){
		return new EnumSelectModel(AppointmentType.class,messages);
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
					.get("workedYearReportList"), styles.get("title"), 5);
			
			ExcelAPI.setCellValue(document, sheetNumber, ++rowIndex, 1,
					messages.get("date") + ": " + format.format(new Date()),
					styles.get("plain-left-wrap"), 5);
			
			ExcelAPI.setCellValue(document, sheetNumber, ++rowIndex, 1,
					messages.get("name") + ": " + ((org!=null&&org.getName()!=null)?org.getName():messages.get("all")),
					styles.get("plain-left-wrap"), 5);
			
			rowIndex += 2;

			/* column headers */

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1, messages
					.get("number-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2, messages
					.get("levelWorkedYear-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3, messages
					.get("countOfficer-label"), styles.get("header-wrap"));
			
			
			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;
			i = -1;
			if (listWorkedYear != null)
				for (Integer s : listWorkedYear) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (listWorkedYear.indexOf(s)+1)+"", styles
									.get("plain-left-wrap-border"));
		
				
					
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,getLevel() + "", styles
							
									.get("plain-left-wrap-border"));

					 
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,listWorkedYear.get(index-1).toString(), styles
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
					+ loginState.getEmployee().getLastname().charAt(0)  + "."
					+ loginState.getEmployee().getFirstName() + " /", styles
					.get("plain-left-wrap"), 8);
			rowIndex++;

			OutputStream out = response
					.getOutputStream("application/vnd.ms-excel");
			response.setHeader("Content-Disposition",
					"attachment; filename=\"employeeWorkedYearReport.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
