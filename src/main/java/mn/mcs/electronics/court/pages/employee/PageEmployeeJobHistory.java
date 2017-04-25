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
import mn.mcs.electronics.court.entities.employee.Displacement;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.Experience;
import mn.mcs.electronics.court.entities.employee.QuitJob;
import mn.mcs.electronics.court.enums.DisplacementType;
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

public class PageEmployeeJobHistory {
	
	@Inject
	private Messages messages;
	
	@Inject
	private Response response;
	
	@Inject
	private CoreDAO dao;
	
	@InjectPage
	private PageEmployeeMovement pageEmployeeMovement;
	
	@SessionState
	private LoginState loginState;
	
	@InjectComponent
	private LayoutEmployee layoutEmployee;
	
	@Persist
	private Employee employee;
	
	@Property
	@Persist
	private Experience experience;
	
	@Property
	@Persist
	private Experience valueExperience;
	
	@Property
	@Persist
	private QuitJob quitJob;
	
	@Property
	@Persist
	private QuitJob valueQuitJob;
	
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
	
	@Property
	@Persist
	private Displacement currentOccupation;
	
	@Property
	@Persist
	private Displacement valueCurrentOccupation;
	
	/* Grid value */
	@Property
	private List<Displacement> listCurrentOccupation;
	
	@Property
	private List<Experience> listExperience;
	
	@Property
	private List<QuitJob> listQuitJob;
	
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
		
//		layoutEmployee.setValueEmployee(getEmployee());
		listExperience = dao.getExperience(employee);
		listQuitJob = dao.getQuitJob(employee);
		listCurrentOccupation = dao.getListCurrentOccupation(employee);
		
		if(currentOccupation == null)
			currentOccupation = new Displacement();
		if(experience == null)
			experience = new Experience();
	
	}
	
	@CommitAfter
	void onActionFromDelete(Displacement currentOccupation){
		dao.deleteObject(currentOccupation);
	}
	
	@CommitAfter
	void onActionFromDeleteQuitJob(QuitJob quitJob){
		dao.deleteObject(quitJob);
	}
	
	Object onActionFromEditDisplacement(Displacement currentOccupation){
		this.currentOccupation=currentOccupation;
		pageEmployeeMovement.setEmployee(employee);
		pageEmployeeMovement.setCurrentOccupation(currentOccupation);
		pageEmployeeMovement.setDisplacementTypeSelect(DisplacementType.SHILJIH);
		return pageEmployeeMovement;
	}
	
	Object onActionFromEditQuitJob(QuitJob quitJob){
		this.quitJob=quitJob;
		pageEmployeeMovement.setEmployee(employee);
		pageEmployeeMovement.setQuiJob(quitJob);
		pageEmployeeMovement.setDisplacementTypeSelect(DisplacementType.AJLAASGARAH);
		return pageEmployeeMovement;
	}
	
	/*getter, setter*/
	
	public int getNumber(){
		return listExperience.indexOf(valueExperience)+1;
	}
	
	public int getNumberCo(){
		return listCurrentOccupation.indexOf(valueCurrentOccupation)+1;
	}
	
	public int getNumberQ(){
		return listQuitJob.indexOf(valueQuitJob)+1;
	}
	
	public String getGridRowCSS() {
		return GRID_ROW_CSS;
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
	
	public String getIssuedDatea(){
		
		if(valueCurrentOccupation==null||valueCurrentOccupation.getIssuedDate()==null)
			return "";
		
		return format.format(valueCurrentOccupation.getIssuedDate());
	}
	public String getQuitDatea(){
		
		if(valueCurrentOccupation==null||valueCurrentOccupation.getQuitDate()==null)
			return "";
		
		return format.format(valueCurrentOccupation.getQuitDate());
	}
	public String getTuAaLevel(){
		if(valueCurrentOccupation.getTuAaLevel()==null||valueCurrentOccupation==null)
			return"";
		
		return valueCurrentOccupation.getTuAaLevel().getTuAaSort().getName()+"-"+valueCurrentOccupation.getTuAaLevel().getTuAaRank().getRank();
	}
	
	public String getAppointmentName(){
		if(valueCurrentOccupation==null||valueCurrentOccupation.getAppointment()==null)
			return "";
		
		return valueCurrentOccupation.getAppointment().getAppointmentName();
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
	
	public String getQuitDat(){
		if(valueQuitJob==null||valueQuitJob.getQuitDate()==null)
			return "";
		return format.format(valueQuitJob.getQuitDate());
	}
	
	public String getMonthQuantity(){
		if(valueQuitJob==null||valueQuitJob.getMonth()==null)
			return "";
		return valueQuitJob.getMonth().toString();
		
	}
	public String getQuitTypeName(){
		if(valueQuitJob==null||valueQuitJob.getQuitType()==null)
			return "";
		return valueQuitJob.getQuitType().name();
		
	}
	
	public String getQuitCauseName(){
		if(valueQuitJob==null||valueQuitJob.getQuitCause()==null)
			return "";
		return valueQuitJob.getQuitCause().getCauseName();
		
	}
	
//	public String getDepartmentName(){
//		if(valueCurrentOccupation==null||valueCurrentOccupation.getDepartment()==null)
//			return "";
//		
//		return valueCurrentOccupation.getDepartment().getName();
//	}
//	
	public String getOrganizationName(){
		if(valueCurrentOccupation==null||valueCurrentOccupation.getOrganization()==null)
			return "";
		
		return valueCurrentOccupation.getOrganization().getName();
	}
	
//	public String getToOrganizationName(){
//		if(valueCurrentOccupation==null||valueCurrentOccupation.getToOrganization()==null)
//			return "";
//		
//		return valueCurrentOccupation.getToOrganization().getName();
//	}
//	
//	public String getToAppointmentName(){
//		if(valueCurrentOccupation==null||valueCurrentOccupation.getToAppointment()==null)
//			return "";
//		
//		return valueCurrentOccupation.getToAppointment().getAppointmentName();
//	}
	
//	public String getToDepartmentName(){
//		if(valueCurrentOccupation==null||valueCurrentOccupation.getToDepartment()==null)
//			return "";
//		
//		return valueCurrentOccupation.getToDepartment().getName();
//	}
	
	public Asset getImgExcel() {
		return imgExcel;
	}
	
	/* export excel */
	void onActionFromExportEmpQuitJob() {

		try {
			HSSFWorkbook document = new HSSFWorkbook();
			HSSFSheet sheet = ReportUtil.createSheet(document);

			sheet.setMargin((short) 0, 0.5);
			ReportUtil.setColumnWidths(sheet, 0, 0.5, 1, 2, 2, 3, 3, 3, 3, 3,
					4, 3, 5, 2, 6, 2, 7, 2, 8, 2, 9, 2);

			Map<String, HSSFCellStyle> styles = ReportUtil
					.createStyles(document);

			int sheetNumber = 0;
			int rowIndex = 1;
			int colIndex = 1;
			Long index = 1L;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2, messages
					.get("employeeQuitJob"), styles.get("title"), 5);

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
					.get("type-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3, messages
					.get("quitCause-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 4, messages
					.get("month-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 5, messages
					.get("money-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 6, messages
					.get("quitDate-label"), styles.get("header-wrap"));
			
			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;
			if (listQuitJob != null)
				for (QuitJob s : listQuitJob) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,(listQuitJob.indexOf(s)+1)+"", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,(s.getQuitType()!=null)?messages.get(s.getQuitType().toString()):"", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getQuitCause()!=null)?s.getQuitCause().getCauseName():"", styles
									.get("plain-left-wrap-border"));
					
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getMonth()!=null)?s.getMonth().toString():"", styles
									.get("plain-left-wrap-border"));

					 
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getMoney()!=null)?s.getMoney().toString():"", styles
									.get("plain-left-wrap-border"));
					
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getQuitDate()!=null)?format.format(s.getQuitDate()):"", styles
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
					"attachment; filename=\"employeeQuitJob.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	void onActionFromexportEmpMovement() {

		try {
			HSSFWorkbook document = new HSSFWorkbook();
			HSSFSheet sheet = ReportUtil.createSheet(document);

			sheet.setMargin((short) 0, 0.8);
			ReportUtil.setColumnWidths(sheet, 0, 0.5, 1, 0.5, 2, 3, 3, 2, 4, 2,
					5, 1.8, 6, 2.5, 7, 3, 8, 3, 9, 2, 10, 2, 11, 2, 11, 2);

			Map<String, HSSFCellStyle> styles = ReportUtil
					.createStyles(document);

			int sheetNumber = 0;
			int rowIndex = 1;
			int colIndex = 1;
			Long index = 1L;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2, messages
					.get("employeeMovement"), styles.get("title"), 9);

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
					.get("appointment-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3, messages
					.get("toAppointment-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 4, messages
					.get("tuAaLevel-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 5, messages
					.get("department-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 6, messages
					.get("toDepartment-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 7, messages
					.get("organization-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 8, messages
					.get("toOrganization-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 9, messages
					.get("IssuedDatea-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 10, messages
					.get("QuitDatea-label"), styles.get("header-wrap"));
			
			
			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;
			if (listCurrentOccupation != null)
				for (Displacement s : listCurrentOccupation) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (listCurrentOccupation.indexOf(s)+1)+"", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,(s.getAppointment()!=null)?s.getAppointment().getAppointmentName():"", styles
									.get("plain-left-wrap-border"));
					
					// ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
					// colIndex++,(s.getToAppointment()!=null)?s.getToAppointment().getAppointmentName():"",
					// styles
					// .get("plain-left-wrap-border"));
					//
					// ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
					// colIndex++,(s.getTuAaLevel()!=null)?s.getTuAaLevel().getTuAaSort().getName()+"-"+s.getTuAaLevel().getTuAaRank().getRank().toString():"",
					// styles
					// .get("plain-left-wrap-border"));
					//
					// ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
					// colIndex++,(s.getDepartment()!=null)?s.getDepartment().getName():"",
					// styles
					// .get("plain-left-wrap-border"));
					
					// ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
					// colIndex++,(s.getToDepartment()!=null)?s.getToDepartment().getName():"",
					// styles
					// .get("plain-left-wrap-border"));
					//
					// ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
					// colIndex++,(s.getOrganization()!=null)?s.getEmployee().getOrganization().getName():"",
					// styles
					// .get("plain-left-wrap-border"));
					//
					// ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
					// colIndex++,(s.getToOrganization()!=null)?s.getToOrganization().getName():"",
					// styles
					// .get("plain-left-wrap-border"));
					//
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,(s.getIssuedDate()!=null)?format.format(s.getIssuedDate()):"", styles
									.get("plain-left-wrap-border"));
					
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,(s.getQuitDate()!=null)?format.format(s.getQuitDate()):"", styles
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
					"attachment; filename=\"employeeMovement.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void onActionFromexportEmpExpierence() {

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
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 5, messages
					.get("issuedDate-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 6, messages
					.get("quitDate-label"), styles.get("header-wrap"));
			
			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;
			if (listExperience != null)
				for (Experience s : listExperience) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (listExperience.indexOf(s)+1)+"", styles
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

					 
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getStartDate()!=null)?format.format(s.getStartDate()):"", styles
									.get("plain-left-wrap-border"));
					
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getEndDate()!=null)?format.format(s.getEndDate()):"", styles
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
					"attachment; filename=\"employeeExperience.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
