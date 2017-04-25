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
import mn.mcs.electronics.court.entities.employee.Allowance;
import mn.mcs.electronics.court.entities.employee.AllowanceType;
import mn.mcs.electronics.court.entities.employee.Demerit;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.Honour;
import mn.mcs.electronics.court.entities.employee.Sahilga;
import mn.mcs.electronics.court.enums.AwardType;
import mn.mcs.electronics.court.model.AllowanceTypeSelectionModel;
import mn.mcs.electronics.court.model.CommandSubjectSelectionModel;
import mn.mcs.electronics.court.model.SahilgaShiitgelSelectionModel;
import mn.mcs.electronics.court.model.SahilgaTurulSelectionModel;
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
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.util.EnumSelectModel;

public class PageEmployeeAddition {
	@Inject
	private CoreDAO dao;

	@InjectComponent
	private LayoutEmployee layoutEmployee;

	@InjectPage
	private PageEmployeeDetail pageEmployee;

	@Inject
	@Path("context:/images/b_edit.png")
	private Asset editIcon;

	@Inject
	@Path("context:/images/b_drop.png")
	private Asset deleteIcon;

	@Inject
	@Path("context:/css/index.css")
	private Asset styles;
	@Persist
	private Employee employee;

	@Property
	private List<Honour> listHonour;

	@Property
	@Persist
	private Honour honour;

	@Property
	@Persist
	private Honour valueHonour;

	@Property
	@Persist
	private AwardType awardType;

	@Property
	@Persist
	private Demerit demerit;

	@Property
	@Persist
	private Demerit valueDemerit;

	@Property
	@Persist
	private List<Demerit> listDemerit;

	/*@Property
	@Persist
	private StatePrize statePrizeName;

	@Property
	@Persist
	private CourtPrize courtPrizeName;
	@Property
	@Persist
	private JusticeMinistryPrize justiceMinistryPrizeName;
	
	@Property
	@Persist
	private GovernmentPrize governmentPrizeName;*/

	@Inject
	private Response response;

	@Inject
	private Messages messages;

	@Property
	@Persist
	private Employee valueEmployee;

	@Inject
	@Path("context:/images/excel.jpg")
	private Asset imgExcel;

	@Persist
	private boolean selectedBlockId;

	@SessionState
	private LoginState loginState;

	@Persist
	private boolean viewEmployee;

	@InjectComponent
	private Zone shagnalZone;
	
	@Inject
	private Request request;
	
	/* Dec 19, 2012 Bolorchimeg Begin */
	
	@Persist
	@Property
	private Sahilga sahilga;
	
	@Property
	private Sahilga valueSahilga;

	@Property
	private List<Sahilga> listSahilga;
	
	@Persist
	@Property
	private Allowance allowance;
	
	@Persist
	@Property
	private AllowanceType allowanceType;
	
	@Property
	private Allowance valueAllowance;
	
	@Property
	private List<Allowance> listAllowance;
	
	/* Dec 19, 2012 Bolorchimeg End */

	private SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
	
	@Persist
	private String otherString;

	private static final String GRID_ROW_CSS = "myGrid";
	
	private Long empID;

	void onActivate(Long id) {
		empID = id;
	}

	Long onPassivate() {
		return empID;
	}

	void beginRender() {
		viewEmployee = true;

		this.employee = (Employee) dao.getObject(Employee.class, empID);

		layoutEmployee.setValueEmployee(employee);
		
//		layoutEmployee.setValueEmployee(getEmployee());
//
//		this.employee = pageEmployee.getEmployee();

		listHonour = dao.getListHonour(employee);
		listSahilga = dao.getListSahilga(employee);
		listAllowance = dao.getListAllowance(employee);
		
		if (honour == null)
			honour = new Honour();

		/* Dec 19, 2012 Jargalsuren.S Begin */
		if (awardType == null)
			awardType = AwardType.STATEPRIZE;

		if (awardType == null)
			awardType = AwardType.COURTPRIZE;

		if (awardType == null)
			awardType = AwardType.JUSTICE_MINISTRYPRIZE;
		
		if (awardType == null)
			awardType = AwardType.GOVERNMENTPRIZE;
		/* Dec 19, 2012 Jargalsuren.S End */		
		
		if (demerit == null)
			demerit = new Demerit();
		
		if (sahilga == null)
			sahilga = new Sahilga();
		
		if (allowance == null)
			allowance = new Allowance();
		
		/*if(sahilgaType!=null && sahilgaType.equals(SahilgaType.ToriinAlbaniiTuhaiHuuli))
			sahilgaSubject=null;*/
	}

	/*public Block getActiveBlock() {
		if (employee != null
				&& employee.getAppointment() != null
				&& employee.getAppointment().getAppointmentType() != null
				&& employee.getAppointment().getAppointmentType() == AppointmentType.OTHEREMPLOYEE) {
			return newEmpDemerits;
		} else {
			return judgeDemerits;
		}
	}*/
	
	@CommitAfter
	void onSelectedFromSaveState() {
		honour.setEmployee(employee);
		honour.setAwardType(awardType);
		/*if (isStatePrize() == true)
			honour.setStatePrize(statePrizeName.getName());		
		if (isCourtPrize() == true)
			honour.setStatePrize(courtPrizeName.getName());
		if (isGovernmentPrize() == true)
			honour.setStatePrize(governmentPrizeName.getName());
		if (isJusticeMinistryPrize() == true)
			honour.setStatePrize(justiceMinistryPrizeName.getName());*/
		dao.saveOrUpdateObject(honour);
		honour = new Honour();
	}

	@CommitAfter
	void onActionFromDeleteState(Honour honour) {
		dao.deleteObject(honour);
	}

	void onActionFromEditState(Honour honour) {
		this.honour = honour;
	}

	void onActionFromCancelState() {
		honour = new Honour();
	}

	/* Ajax zone */
	Object onValueChangedFromShagnalClick() {
		return request.isXHR() ? shagnalZone.getBody() : null;
	}

	/* selection model */

	public SelectModel getAwardTypeSelectionModel() {
		return new EnumSelectModel(AwardType.class, messages);
	}

	/*public SelectModel getStatePrizeSelectionModel() {
		StatePrizeSelectionModel sm = new StatePrizeSelectionModel(dao);
		return sm;
	}

	public SelectModel getCourtPrizeSelectionModel() {
		CourtPrizeSelectionModel sm = new CourtPrizeSelectionModel(dao);
		return sm;
	}
	public SelectModel getGovernmentPrizeSelectionModel() {
		GovernmentPrizeSelectionModel sm = new GovernmentPrizeSelectionModel(dao);
		return sm;
	}
	
	public SelectModel getJusticeMinistryPrizeSelectionModel() {
		JusticeMinistryPrizeSelectionModel sm = new JusticeMinistryPrizeSelectionModel(dao);
		return sm;
	}	*/
	
	public SelectModel getCommandSubjectSelectionModel(){
		CommandSubjectSelectionModel sm = new CommandSubjectSelectionModel(dao);
		return sm;
	}
	
	public SelectModel getAllowanceTypeSelectionModel(){
		AllowanceTypeSelectionModel sm = new AllowanceTypeSelectionModel(dao);
		return sm;
	}
	/* Dec 19, 2012 Jargalsuren.S End */


	/* Getter Setter */
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Asset getEditIcon() {
		return editIcon;
	}
	
	public void setEditIcon(Asset editIcon) {
		this.editIcon = editIcon;
	}
	public Asset getDeleteIcon() {
		return deleteIcon;
	}

	public void setDeleteIcon(Asset deleteIcon) {
		this.deleteIcon = deleteIcon;
	}

	/*public void setSelectedBlockId(boolean selectedBlockId) {
		this.selectedBlockId = selectedBlockId;
	}
*/
	
	public LayoutEmployee getLayoutEmployee() {
		return layoutEmployee;
	}

	public void setLayoutEmployee(LayoutEmployee layoutEmployee) {
		this.layoutEmployee = layoutEmployee;
	}

	public PageEmployeeDetail getPageEmployee() {
		return pageEmployee;
	}

	public void setPageEmployee(PageEmployeeDetail pageEmployee) {
		this.pageEmployee = pageEmployee;
	}

	public boolean isSelectedBlockId() {
		return selectedBlockId;
	}

	public Asset getImgExcel() {
		return imgExcel;
	}

	public void setImgExcel(Asset imgExcel) {
		this.imgExcel = imgExcel;
	}

	public static String getGRID_ROW_CSS() {
		return GRID_ROW_CSS;
	}

	public String getGridRowCSS() {
		return GRID_ROW_CSS;
	}

	public String getawardedDate() {
		return valueHonour.getDateOfAwarded().toString();
	}

	public SimpleDateFormat getFormat() {
		return format;
	}

	public int getNumber() {
		return listHonour.indexOf(valueHonour) + 1;
	}

	public int getNumberDemerit() {
		return listDemerit.indexOf(valueDemerit) + 1;
	}

	public boolean isStatePrize() {
		if (awardType == AwardType.STATEPRIZE)
			return true;

		return false;
	}
	
	public boolean isCourtPrize(){
		if (awardType == AwardType.COURTPRIZE)
			return true;

		return false;
	}
	
	/* Dec 19, 2012 Jargalsuren.S Begin */	
	public boolean isGovernmentPrize(){
		if (awardType == AwardType.GOVERNMENTPRIZE)
			return true;

		return false;
	}

	public boolean isJusticeMinistryPrize(){
		if (awardType == AwardType.JUSTICE_MINISTRYPRIZE)
			return true;

		return false;
	}

	public boolean isOtherOrganizationPrize() {
		if (awardType == AwardType.OTHER_ORGANIZATIONPRIZE)
			return true;
		return false;
	}
	/* Dec 19, 2012 Jargalsuren.S End */

	public String getDateOfDemerit() {
		if (valueDemerit == null || valueDemerit.getDate() == null)
			return "";

		return format.format(valueDemerit.getDate());
	}

	public String getEmployeeName() {
		if (valueEmployee == null)
			return "";

		return valueEmployee.getFirstName();
	}

	public Asset getStyles() {
		return styles;
	}

	public void setStyles(Asset styles) {
		this.styles = styles;
	}

	/* export excel */
	void onActionFromExport() {

		try {
			HSSFWorkbook document = new HSSFWorkbook();
			HSSFSheet sheet = ReportUtil.createSheet(document);

			sheet.setMargin((short) 0, 0.5);
			ReportUtil.setColumnWidths(sheet, 0, 0.5, 1, 0.5, 2, 3, 3, 3, 4, 3,
					5, 3, 6, 3, 7, 6);

			Map<String, HSSFCellStyle> styles = ReportUtil
					.createStyles(document);

			int sheetNumber = 0;
			int rowIndex = 1;
			int colIndex = 1;
			Long index = 1L;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2, messages
					.get("honourPage-label"), styles.get("title"), 5);

			ExcelAPI.setCellValue(document, sheetNumber, ++rowIndex, 1,
					messages.get("date") + ": " + format.format(new Date()),
					styles.get("plain-left-wrap"), 5);
			rowIndex += 2;

			/* column headers */
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1, messages
					.get("number-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2, messages
					.get("awardType-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3, messages
					.get("STATEPRIZE-label"), styles.get("header-wrap"));
			ExcelAPI
					.setCellValue(document, sheetNumber, rowIndex, 4, messages
							.get("dateOfAwardedExcel-label"), styles
							.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 5, messages
					.get("authenticationId-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 6, messages
					.get("dictateId-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 7, messages
					.get("description-label"), styles.get("header-wrap"));
			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;
			if (listHonour != null)
				for (Honour s : listHonour) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (listHonour.indexOf(s) + 1) + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getAwardType() != null) ? messages
									.get(s.getAwardType().toString()) : "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getOtherPrize() != null) ? s
									.getOtherPrize() : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getDateOfAwarded() != null) ? format
									.format(s.getDateOfAwarded()) : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getAuthenticationId() != null) ? s
									.getAuthenticationId() : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getDictateId() != null) ? s
									.getDictateId() : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getDescription() != null) ? s
									.getDescription() : "", styles
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
					"attachment; filename=\"ShagnalUramshuulal.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void onActionFromExportDemerit() {

		try {
			HSSFWorkbook document = new HSSFWorkbook();
			HSSFSheet sheet = ReportUtil.createSheet(document);

			sheet.setMargin((short) 0, 0.5);
			ReportUtil.setColumnWidths(sheet, 0, 0.5, 1, 0.5, 2, 3, 3, 3, 4, 3,
					5, 3, 6, 3, 7, 6);

			Map<String, HSSFCellStyle> styles = ReportUtil
					.createStyles(document);

			int sheetNumber = 0;
			int rowIndex = 1;
			int colIndex = 1;
			Long index = 1L;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2, messages
					.get("demeritInformation-label"), styles.get("title"), 5);

			ExcelAPI.setCellValue(document, sheetNumber, ++rowIndex, 1,
					messages.get("date") + ": " + format.format(new Date()),
					styles.get("plain-left-wrap"), 5);
			rowIndex += 2;

			// column headers 
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1, messages
					.get("number-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2, messages
					.get("commandSubject-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3, messages
					.get("sahilgaShiitgel-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 4, messages
					.get("cause-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 5, messages
					.get("tushaalDugaar-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 6, messages
					.get("shiitgelAvagdsanDate-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 7, messages
					.get("arilgasanTushaalDugaar-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 8, messages
					.get("arilgasanTushaalOgnoo-label"), styles.get("header-wrap"));
			rowIndex++;
			if (listSahilga != null)
				for (Sahilga s : listSahilga) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (listSahilga.indexOf(s) + 1) + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getCommandSubject() != null) ? s
									.getCommandSubject().getSubjectName() : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getSahilgaShiitgel() != null) ? s
									.getSahilgaShiitgel().getShiitgelName() : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getCause() != null) ? s
									.getCause() : "", styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getAvagdsanShiitgelDugaar() != null) ? s.getAvagdsanShiitgelDugaar()
									: "", styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getShiitgelAvagdsanOgnoo() != null) ? format
									.format(s.getShiitgelAvagdsanOgnoo()) : "", styles
									.get("plain-left-wrap-border"));
					
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getArilgasanTushaalDugaar() != null) ? s.getArilgasanTushaalDugaar()
									: "", styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getArilgasanTushaalOgnoo() != null) ? format
									.format(s.getArilgasanTushaalOgnoo()) : "", styles
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
					"attachment; filename=\"SahilgiinMedeelel.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isShow() {
		/*if (viewEmployee == true)
			return true;
		else
			return false;*/
		return true;
	}

	public boolean isViewEmployee() {
		return viewEmployee;
	}

	public void setViewEmployee(boolean viewEmployee) {
		this.viewEmployee = viewEmployee;
	}
	
	/* Dec 19, 2012 Bolorchimeg Begin */

	/*public SelectModel getSahilgaTypeSelectionModel(){
		
		return new EnumSelectModel(SahilgaType.class,messages);
		
	}*/
	
	public SelectModel getSahilgaShiitgelSelectionModel(){
		SahilgaShiitgelSelectionModel sm = new SahilgaShiitgelSelectionModel(dao);
		return sm;
	}
	
	public SelectModel getSahilgaTurulSelectionModel(){
		SahilgaTurulSelectionModel sm = new SahilgaTurulSelectionModel(dao);
		return sm;
	}

	@CommitAfter
	void onSelectedFromSaveSahilga() {
		
		/*Calendar cal = Calendar.getInstance();
		cal.setTime(sahilga.getShiitgelAvagdsanOgnoo());
		cal.add(Calendar.MONTH, sahilga.getSahilgaShiitgel().getDuration());
		
		sahilga.setShiitgelDuusahOgnoo(cal.getTime());*/
		sahilga.setEmployee(employee);
		
		dao.saveOrUpdateObject(sahilga);
		sahilga = new Sahilga();
	}
	
	public void onActionFromEditSahilga(Sahilga sahilga){				
		this.sahilga=sahilga;
	}
	
	@CommitAfter
	public void onActionFromDeleteSahilga(Sahilga sahilga){		
		dao.deleteObject(sahilga);
	}

	void onActionFromCancelSahilga() {
		sahilga = new Sahilga();
	}
	
	/*@CommitAfter
	void onActionFromDelete(Sahilga sahilga) {
		dao.deleteObject(sahilga);
	}
	
	void onActionFromEditSahilga(Sahilga sahilga) {
		this.sahilga = sahilga;
	}*/
	
	/*Object onValueChangedFromTypeClick() {
		return request.isXHR() ? typeZone.getBody() : null;
	}
	
	Object onValueChangedFromSubjectClick() {
		return request.isXHR() ? subjectZone.getBody() : null;
	}*/
	
	/* Dec 19, 2012 Bolorchimeg End */
	
	public int getNumberSahilga() {
		return listSahilga.indexOf(valueSahilga) + 1;
	}
	
	public String getArilgasanTushaalOgnoo(){
		
		if(valueSahilga!=null && valueSahilga.getArilgasanTushaalOgnoo()!=null)
			return format.format(valueSahilga.getArilgasanTushaalOgnoo());
		
		return "";
	}
	
	public String getShiitgelAvagdsanOgnoo(){
		
		if(valueSahilga!=null && valueSahilga.getShiitgelAvagdsanOgnoo()!=null)
			return format.format(valueSahilga.getShiitgelAvagdsanOgnoo());
		
		return "";
	}
	
	public String getDateOfAwarded(){		
		if(valueHonour!=null && valueHonour.getDateOfAwarded()!=null)
			return format.format(valueHonour.getDateOfAwarded());
		
		return "";
	}
	
	public int getNumberAllowance() {
		return listAllowance.indexOf(valueAllowance) + 1;
	}	
	
	public String getDateOfAllowance(){
		
		if(valueAllowance!=null && valueAllowance.getOlgosonOgnoo()!=null)
			return format.format(valueAllowance.getOlgosonOgnoo());
		
		return "";
	}
	@CommitAfter
	void onSelectedFromSaveAllowance() {
		allowance.setEmployee(employee);		
		
		dao.saveOrUpdateObject(allowance);
		allowance = new Allowance();
	}
	
	void onActionFromCancelAllowance() {
		allowance = new Allowance();
	}
	
	public void onActionFromEditAllowance(Allowance allowance){				
		this.allowance=allowance;
	}
	
	@CommitAfter
	public void onActionFromDeleteAllowance(Allowance allowance){		
	
	}
}
