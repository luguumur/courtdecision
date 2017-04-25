package mn.mcs.electronics.court.pages.report;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.Honour;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.AppointmentType;
import mn.mcs.electronics.court.enums.AwardType;
import mn.mcs.electronics.court.model.OrganizationSelectionModel;
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

public class PageHonourReport {

	@Inject
	private Response response;
	
	@Inject 
	private CoreDAO dao;
	
	@Inject
	private Messages messages;
	
	@SessionState
	private LoginState loginState;
	
	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);
	
	@Property
	@Persist
	private List<Object[]> listEmployee;
	
	@Property
	@Persist
	private Object[] valueEmployee;
	
	@Property
	@Persist
	private AwardType awards;
	
	@Property
	@Persist
	private Honour honour;
	
	@Property
	@Persist
	private Date fromDate;
	
	@Property
	@Persist
	private Date toDate;

	@Property
	@Persist
	private Organization org;
	
	@Property
	@Persist
	private AppointmentType appointmentType;
	
	private static final String gridRowCss = "myGrid";
	
	@Inject
	@Path("context:/images/excel.jpg")
	private Asset imgExcel;
	
	void beginRender(){	
		
		Subject currentUser =
		    SecurityUtils.getSubject();
	
		if(!currentUser.isPermitted("show_all_organization"))
			org = loginState.getOrganization();
		
		listEmployee = dao.getListHonourReport(org, fromDate, toDate, appointmentType);
		
	}
	
/* getter, setter */
	
	public String getGridrowcss() {
		return gridRowCss;
	}
	
	public Asset getImgExcel() {
		return imgExcel;
	}
	
	public String getCount(){
		if(valueEmployee==null||valueEmployee[0]==null)
			return "";
		
		return valueEmployee[0].toString();
	}

	public String getSize(){
		return listEmployee.size()+"";
	}
	
	public String getHonourType(){
		if(valueEmployee== null||valueEmployee[1]==null)
			return "";
		
		
			AwardType d =  (AwardType) valueEmployee[1];
			return ((d!=null)?messages.get(d.name()):"");
	}
	
/* Jul 27, 2012 tuguldur.j Start*/
	
	public SelectModel getOrganizationSelectionModel(){
		OrganizationSelectionModel sm = new OrganizationSelectionModel(dao);
		return sm;
	}
	
	public SelectModel getAwardTypeSelectionModel() {
		return new EnumSelectModel(AwardType.class, messages);
	}
	
	public SelectModel getAppointmentTypeSelectionModel(){
		return new EnumSelectModel(AppointmentType.class,messages);
	}
	
/* Jul 27, 2012 tuguldur.j End*/
	
	/* export to excel */
	void onActionFromExport() {

		try {
			HSSFWorkbook document = new HSSFWorkbook();
			HSSFSheet sheet = ReportUtil.createSheet(document);

			sheet.setMargin((short) 0, 0.5);
			ReportUtil.setColumnWidths(sheet, 0, 0.5, 1, 0.5, 2, 2, 3, 2, 3, 2,
					4, 3, 5, 2, 6, 2);

			Map<String, HSSFCellStyle> styles = ReportUtil
					.createStyles(document);

			int sheetNumber = 0;
			int rowIndex = 1;
			int colIndex = 1;
			Long index = 1L;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2, messages
					.get("honourReport"), styles.get("title"), 5);
			
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
					.get("awardType-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3, messages
					.get("countHonour-label"), styles.get("header-wrap"));
			
			
			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;
			if (listEmployee != null)
				for (Object[] s : listEmployee) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, listEmployee.indexOf(s)+1+"", styles
									.get("plain-left-wrap-border"));
					
					AwardType d = (AwardType) valueEmployee[1];
					
					d = (AwardType) s[1];
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,((d!=null)?messages.get(d.name()):"") ,styles
									.get("plain-left-wrap-border"));
					 
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,(s[0]!=null)?s[0].toString():"", styles
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
					"attachment; filename=\"ShagnaliinTailan.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
