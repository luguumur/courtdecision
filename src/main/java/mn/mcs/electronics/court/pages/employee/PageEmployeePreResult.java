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
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.ResultContract;
import mn.mcs.electronics.court.util.Constants;
import mn.mcs.electronics.court.util.ExcelAPI;
import mn.mcs.electronics.court.util.ReportUtil;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;

public class PageEmployeePreResult {
	
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
	
	@Persist
	private Employee employee;	
	
	@Property
	@Persist
	private ResultContract result;
	
	@Property
	@Persist
	private ResultContract valueResultContract;
	
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
	
	@InjectPage
	private PageEmployeeNowResult pageEmployeeNowResult;
	
	/* Grid value */
	@Property
	private List<ResultContract> listResultContract;
	
	private SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
	
	private static final String GRID_ROW_CSS = "myGrid";
	
	private Long empID;

	void onActivate(Long id) {
		empID = id;
	}

	Long onPassivate() {
		return empID;
	}

	void beginRender(){
		
		this.employee = (Employee) dao.getObject(Employee.class, empID);
		layoutEmployee.setValueEmployee(employee);
		listResultContract = dao.getResultContract(employee);
		if(result == null)
			result = new ResultContract();
	}
	
	@CommitAfter
	public Object onActionFromEdit(ResultContract result){
		this.result = result;
		pageEmployeeNowResult.setResult(valueResultContract);
		return pageEmployeeNowResult;
	}
	
	@CommitAfter
	void onActionFromDelete(ResultContract result){
		dao.deleteObject(result);
	}
	
		/*getter,setter*/
	public int getNumber(){
		return listResultContract.indexOf(valueResultContract)+1;
	}
	
	public String getGridRowCSS() {
		return GRID_ROW_CSS;
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
	
	public String getContractDate(){
		if(valueResultContract==null||valueResultContract.getContractDate()==null)
			return "";
		return format.format(valueResultContract.getContractDate());
	}
	
	public String getAllSupply(){
		if(valueResultContract==null||valueResultContract.getAllSupplyScore()==null)
			return "";
		return valueResultContract.getAllSupplyScore().toString();
	}
	
	public String getSpecialSupply(){
		if(valueResultContract==null||valueResultContract.getSpecialSupplyScore()==null)
			return "";
		return valueResultContract.getSpecialSupplyScore().toString();
	}
	
	public String getQualification(){
		if(valueResultContract==null||valueResultContract.getQualificationLevel()==null)
			return "";
		return valueResultContract.getQualificationLevel().toString();
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
					.get("employeeResult"), styles.get("title"), 7);

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
					.get("allSupplyScore-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3, messages
					.get("specialSupplyScore-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 4, messages
					.get("qualificationLevel-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 5, messages
					.get("directSuperiorScore-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 6, messages
					.get("superiorScore-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 7, messages
					.get("startContractDate-label"), styles.get("header-wrap"));;
			
			
			ReportUtil.setRowHeight(sheet, rowIndex, 3.5);

			rowIndex++;
			if (listResultContract!= null)
				for (ResultContract s : listResultContract) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (listResultContract.indexOf(s)+1)+"", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,(s.getAllSupplyScore()!=null)?s.getAllSupplyScore().toString():"", styles
									.get("plain-left-wrap-border"));

					 
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getSpecialSupplyScore()!=null)?s.getSpecialSupplyScore().toString():"", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getQualificationLevel()!=null)?s.getQualificationLevel().toString():"", styles
									.get("plain-left-wrap-border"));
					
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getDirectSuperiorScore()!=null)?messages.get(s.getDirectSuperiorScore().toString()):"", styles
									.get("plain-left-wrap-border"));
					
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getSuperiorScore()!=null)?messages.get(s.getSuperiorScore().toString()):"", styles
									.get("plain-left-wrap-border"));
					
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getContractDate()!=null)?format.format(s.getContractDate()):"", styles
									.get("plain-left-wrap-border"));

					ReportUtil.setRowHeight(sheet, rowIndex, 3);
					rowIndex++;
					index++;
					colIndex = 1;

				}

			rowIndex += 2;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1,
					"ГАРГАСАН: "
							+ loginState.getLogin()
							+ "  : ................  / "
							+ loginState.getLogin() + " /", styles
							.get("plain-left-wrap"), 8);
			rowIndex++;

			OutputStream out = response
					.getOutputStream("application/vnd.ms-excel");
			response.setHeader("Content-Disposition",
					"attachment; filename=\"employeeResultContract.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

