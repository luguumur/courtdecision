package mn.mcs.electronics.court.pages.report;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.model.OrganizationSelectionModel;
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

public class PageRetireSoonReport {
	
	@SessionState
	private LoginState loginState;
	
	@Inject
	private Response response;
	
	@Inject 
	private CoreDAO dao;
	
	@Inject
	private Messages messages;
	
	private static final String gridRowCss = "myGrid";

	
	@Inject
	@Path("context:/images/excel.jpg")
	private Asset imgExcel;
	
	@Persist
	private Organization org;
	
	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);
	
	@Property
	@Persist
	private List<Object[]> listRetireSoon;
	
	@Property
	@Persist
	private Object[] valueRetireSoon;
	
	void onActivate(){
		System.err.println("activate");
	}
	
	void beginRender(){
		listRetireSoon= dao.getListRetireSoonReport(org);
		
	}
	
	public String getQuitCauseName(){
		if(valueRetireSoon==null||valueRetireSoon[1]==null)
			return "";
		
		
		return messages.get(valueRetireSoon[1].toString());
		
	}
	
	public String getCountEdu(){
		if(valueRetireSoon==null)
			return "";
		
		return valueRetireSoon[0].toString();
	}
	
	public String getGridrowcss() {
		return gridRowCss;
	}
	
	public Asset getImgExcel() {
		return imgExcel;
	}
	
	/* select model */
	public SelectModel getOrganizationSelectionModel(){
		OrganizationSelectionModel sm = new OrganizationSelectionModel(dao);
		
		return sm;
	}

	/*getter, setter*/

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}
	
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
					.get("RetireSoonEmployee"), styles.get("title"), 3);

			ExcelAPI.setCellValue(document, sheetNumber, ++rowIndex, 1,
					messages.get("date") + ": " + format.format(new Date()),
					styles.get("plain-left-wrap"), 5);

			rowIndex += 2;

			/* column headers */

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1, messages
					.get("number-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2, messages
					.get("name-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3, messages
					.get("countOfficer-label"), styles.get("header-wrap"));
			
			
			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;
			if (listRetireSoon != null)
				for (Object[] s : listRetireSoon) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (listRetireSoon.indexOf(s)+1)+"", styles
									.get("plain-left-wrap-border"));
		
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,(s[1]!=null)?messages.get(s[1].toString()):"", styles
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
					"attachment; filename=\"retireSoonEmployeeReport.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
