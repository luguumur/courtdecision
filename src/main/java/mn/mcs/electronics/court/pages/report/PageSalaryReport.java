package mn.mcs.electronics.court.pages.report;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.salary.AdditionalSalaryType;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.model.OrganizationSelectionModel;
import mn.mcs.electronics.court.model.YearSelectionModel;
import mn.mcs.electronics.court.util.Constants;
import mn.mcs.electronics.court.util.ExcelAPI;
import mn.mcs.electronics.court.util.ReportUtil;
import mn.mcs.electronics.court.util.StringUtil;

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

public class PageSalaryReport {
	
	@SessionState
	private LoginState loginState;
	
	@Inject
	private Response response;
	
	@Inject
	private Messages messages;
	
	@Inject 
	private CoreDAO dao;
	
	@Inject
	@Path("context:/images/excel.jpg")
	private Asset imgExcel;
	
	@Persist
	private Organization org;
	
	@Persist
	private List<Object[]> listSalary;
	
	@Persist
	@Property
	private List<Object[]> listSalaryHistory;
	
	@Property
	@Persist
	private Object[] valueSalaryHistory;
	
	@Persist
	@Property
	private List<Object[]> listAdditionalSalaryHistory;
	
	@Property
	@Persist
	private Object[] valueAdditionalSalaryHistory;
	
	@Property
	@Persist
	private Integer year;
	
	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);
	
	private Subject currentUser = SecurityUtils.getSubject();
	
	private static final String GRID_ROW_CSS = "myGrid";
	
	void beginRender(){
		
		if(!currentUser.isPermitted("show_all_organization"))
			org = loginState.getOrganization();
		
		listSalary = dao.getListSalaryWage(org);
		
		listSalaryHistory = dao.getListSalaryReport(org,year);
		
		listAdditionalSalaryHistory = dao.getListAdditionalSalaryReport(org,year);
	
	}
	
	/* select model */
	public SelectModel getOrganizationSelectionModel(){
		OrganizationSelectionModel sm = new OrganizationSelectionModel(dao);
		
		return sm;
	}
	
	public SelectModel getYearSelectionModel(){
		YearSelectionModel sm = new YearSelectionModel(dao.getFirstYearSalaryHistory());
		return sm;
	}
	
	/*  getter setter */
	public String getSalaryNetworkCount(){
		if(listSalary.isEmpty())
			return "0";
		
		Object[] obj = listSalary.get(0);
		
		return obj[0].toString();
	}
	
	public String getSalaryJudgeCount(){
		if(listSalary.isEmpty()||listSalary.size() >= 1)
			return "0";
		
		Object[] obj = listSalary.get(1);
		
		return obj[0].toString();
	}
	
	public String getTotalSalary(){
		if(valueSalaryHistory[0]==null)
			return "0";
		
		double d = (Double) valueSalaryHistory[0];
		
		String str = StringUtil.getCurrencyFormat(d);
	
		return str;
	}
	
	public String getCountEmp(){
		if(valueSalaryHistory[1]==null)
			return "0";
		
		long i = (Long) valueSalaryHistory[1];
		return i+"";
	}
	
	public String getOrganizationName(){
		if(org==null)
			return messages.get("all");
		
		return org.getName();
	}

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}
	
	/* Additional salary */
	public String getAddType(){
		
		if(valueAdditionalSalaryHistory[2]==null)
			return "";
		
		AdditionalSalaryType as = (AdditionalSalaryType) valueAdditionalSalaryHistory[2];
		
		return as.getName();
	}
	
	public String getAddAmount(){
		if(valueAdditionalSalaryHistory[0]==null)
			return "0";
		
		String str = StringUtil.getCurrencyFormat((Double) valueAdditionalSalaryHistory[0]);
		
		return  str;
		
	}
	
	public String getAvgPercent(){
		if(valueAdditionalSalaryHistory[1]==null)
			return "0";
		
		String str = StringUtil.getCurrencyFormat((Double) valueAdditionalSalaryHistory[1]);
		return str;
	}
	
	public String getNumber(){
		return listAdditionalSalaryHistory.indexOf(valueAdditionalSalaryHistory)+"";
	}
	
	public Asset getImgExcel() {
		return imgExcel;
	}

	public String getGridRowCSS() {
		return GRID_ROW_CSS;
	}

	/* export to excel */
	void onActionFromExport() {

		try {
			HSSFWorkbook document = new HSSFWorkbook();
			HSSFSheet sheet = ReportUtil.createSheet(document);

			sheet.setMargin((short) 0, 0.5);
			ReportUtil.setColumnWidths(sheet, 0, 0.5, 1, 0.5, 2, 2, 3, 4, 4, 2,
					 5, 2, 6, 2, 7, 2, 8, 2, 9, 2);

			Map<String, HSSFCellStyle> styles = ReportUtil
					.createStyles(document);

			int sheetNumber = 0;
			int rowIndex = 1;
			int colIndex = 1;
			int index = 1;

			ReportUtil.setRowHeight(sheet, rowIndex, 5);
			
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2, messages
					.get("salaryReport"), styles.get("title"), 5);

			ExcelAPI.setCellValue(document, sheetNumber, ++rowIndex, 1,
					messages.get("date") + ": " + format.format(new Date()),
					styles.get("plain-left-wrap"), 2);
			
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3,
					messages.get("org-label") + ": " + getOrganizationName(),
					styles.get("plain-left-wrap"), 2);
			
			rowIndex += 2;

			/* column headers */
		
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1, messages
					.get("governmentSalary-label"), styles.get("plain-left-wrap-border"),3);
			
			
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
					4, getSalaryNetworkCount(), styles
							.get("plain-left-wrap-border"));
			
			rowIndex++;
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1, messages
					.get("judgeSalary-label"), styles.get("plain-left-wrap-border"),3);
			
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
					4, getSalaryJudgeCount(), styles
							.get("plain-left-wrap-border"));
			
		

			rowIndex+=2;
			
			/* Total salary */
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1, messages
					.get("totalSalary-label"),styles.get("header-wrap"),2);
			
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3, messages
					.get("countEmp-label"), styles.get("header-wrap"));
			
			rowIndex++;
			
			if (!listSalaryHistory.isEmpty()){
					valueSalaryHistory = listSalaryHistory.get(0);
					String str = StringUtil.getCurrencyFormat((Double) valueSalaryHistory[0]);
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							1,str, styles
									.get("plain-left-wrap-border"),2);

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							3,valueSalaryHistory[1]+"", styles.get("plain-left-wrap-border"));
					
					rowIndex++;
					
					ReportUtil.setRowHeight(sheet, rowIndex, 3);
					rowIndex++;
					index++;
					colIndex = 1;
			}
			rowIndex += 2;

			ReportUtil.setRowHeight(sheet, rowIndex, 5);
			
			/* Additional salary */
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1, messages
					.get("number-label"),styles.get("header-wrap"));
			
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2, messages
					.get("addType-label"), styles.get("header-wrap"));
			
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3, messages
					.get("addAmount-label"),styles.get("header-wrap"));
			
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 4, messages
					.get("avgPercent-label"), styles.get("header-wrap"));
			
			rowIndex++;
			
			if (listAdditionalSalaryHistory != null)
				for (Object[] obj : listAdditionalSalaryHistory) {
				
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,listAdditionalSalaryHistory.indexOf(obj)+1+"", styles
									.get("plain-left-wrap-border"));

					
					AdditionalSalaryType as = (AdditionalSalaryType) obj[2];
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,as.getName(), styles.get("plain-left-wrap-border"));
					
					double d = 0;
					if(obj[1]!=null)
						d=(Double) obj[0];
					else
						d=0;
					String str = StringUtil.getCurrencyFormat(d);
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,str, styles.get("plain-left-wrap-border"));
						
					if(obj[1]!=null)
						d=(Double) obj[1];
					else
						d=0;
					
					 str = StringUtil.getCurrencyFormat(d);
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,str, styles.get("plain-left-wrap-border"));
					
					ReportUtil.setRowHeight(sheet, rowIndex, 3);
					rowIndex++;
					index++;
					colIndex = 1;

				}
			rowIndex++;
			
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
					"attachment; filename=\"salaryReport.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
