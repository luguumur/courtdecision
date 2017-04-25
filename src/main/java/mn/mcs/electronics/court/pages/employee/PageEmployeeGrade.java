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
import mn.mcs.electronics.court.entities.employee.EmployeeMilitary;
import mn.mcs.electronics.court.enums.AppointmentType;
import mn.mcs.electronics.court.enums.EmployeeDegreeStatus;
import mn.mcs.electronics.court.enums.MilitaryRankType;
import mn.mcs.electronics.court.model.MilitaryRankSelectionModel;
import mn.mcs.electronics.court.util.Constants;
import mn.mcs.electronics.court.util.ExcelAPI;
import mn.mcs.electronics.court.util.ReportUtil;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
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
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.util.EnumSelectModel;

public class PageEmployeeGrade {

	@Inject
	private CoreDAO dao;

	@Inject
	private Messages messages;

	@Inject
	private Response response;

	@SessionState
	private LoginState loginState;

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

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);

	@InjectComponent
	private LayoutEmployee layoutEmployee;

	@Persist
	private Employee employee;

	@Property
	@Persist
	private EmployeeMilitary military;

	@Property
	@Persist
	private List<EmployeeMilitary> listMilitary;

	@Property
	@Persist
	private EmployeeMilitary valueMilitary;

	private static final String GRID_ROW_CSS = "myGrid";

	@Persist
	private boolean viewEmployee;

	@InjectComponent
	private Zone militaryZone;

	@Inject
	private Request request;

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

		// layoutEmployee.setValueEmployee(getEmployee());
		//
		// this.employee = pageEmployee.getEmployee();

		listMilitary = dao.getListEmployeeMilitary(employee);

		if (military == null)
			military = new EmployeeMilitary();
	}

	@CommitAfter
	void onSelectedFromSaveMilitary() {
		military.setEmployee(employee);
		dao.saveOrUpdateObject(military);
		military = new EmployeeMilitary();
	}

	void onActionFromEditMilitary(EmployeeMilitary military) {
		this.military = military;
	}

	void onActionFromCancelMilitary() {
		military = new EmployeeMilitary();
	}

	@CommitAfter
	void onActionFromDeleteMilitary(EmployeeMilitary military) {
		dao.deleteObject(military);
	}

	/* getter, setter */

	public String getGridRowCSS() {
		return GRID_ROW_CSS;
	}

	public Asset getImgExcel() {
		return imgExcel;
	}

	public int getNumberMilitary() {
		return listMilitary.indexOf(valueMilitary) + 1;
	}

	public String getDateFormat() {
		return Constants.DATE_FORMAT;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public CoreDAO getDao() {
		return dao;
	}

	public void setDao(CoreDAO dao) {
		this.dao = dao;
	}

	public String getOlgosonOgnooCell() {
		if (valueMilitary == null || valueMilitary.getOlgosonOgnoo() == null)
			return "";
		return format.format(valueMilitary.getOlgosonOgnoo());
	}

	public String getMilitaryCell() {
		if (valueMilitary == null || valueMilitary.getMilitary() == null)
			return "";
		return valueMilitary.getMilitary().getMilitaryName();
	}

	/* selection model */

	public SelectModel getAppointmentTypeSelectionModel() {
		return new EnumSelectModel(AppointmentType.class, messages);
	}

	public SelectModel getEmployeeDegreeStatusSelectionModel() {
		return new EnumSelectModel(EmployeeDegreeStatus.class, messages);
	}

	public SelectModel getMilitarySelectionModel() {

		MilitaryRankSelectionModel sm = new MilitaryRankSelectionModel(dao,
				military.getMilitaryType());

		return sm;
	}

	public SelectModel getMilitaryTypeSelectionModel() {
		return new EnumSelectModel(MilitaryRankType.class, messages);
	}

	/* Ajax zone */

	void onValueChangedFromMilitaryClick() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender("militaryZone", militaryZone);
		}
	}

	/* export excel */

	void onActionFromExportMilitary() {

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

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2,
					messages.get("employeeMilitary"), styles.get("title"), 8);

			ExcelAPI.setCellValue(document, sheetNumber, ++rowIndex, 1,
					messages.get("date") + ": " + format.format(new Date()),
					styles.get("plain-left-wrap"), 5);
			ExcelAPI.setCellValue(
					document,
					sheetNumber,
					++rowIndex,
					1,
					messages.get("employeeLastNameFirstName") + ": "
							+ employee.getLastname() + " "
							+ messages.get("lastnme") + " "
							+ employee.getFirstName(),
					styles.get("plain-left-wrap"), 5);
			rowIndex += 2;

			/* column headers */

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1,
					messages.get("number-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2,
					messages.get("military-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3,
					messages.get("olgosonOgnoo-label"),
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 4,
					messages.get("tushaalDugaar-label"),
					styles.get("header-wrap"));

			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;
			if (listMilitary != null)
				for (EmployeeMilitary s : listMilitary) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (listMilitary.indexOf(s) + 1) + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getMilitary() != null) ? s
									.getMilitary().getMilitaryName() : "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(
							document,
							sheetNumber,
							rowIndex,
							colIndex++,
							(s.getOlgosonOgnoo() != null) ? format.format(s
									.getOlgosonOgnoo()) : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(
							document,
							sheetNumber,
							rowIndex,
							colIndex++,
							(s.getTushaalDugaar() != null) ? s
									.getTushaalDugaar() : "", styles
									.get("plain-left-wrap-border"));

					ReportUtil.setRowHeight(sheet, rowIndex, 3);
					rowIndex++;
					index++;
					colIndex = 1;

				}

			rowIndex += 2;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1,
					"ТАЙЛАН ГАРГАСАН: "
							+ ".....................................  / "
							+ loginState.getEmployee().getLastname().charAt(0)
							+ "." + loginState.getEmployee().getFirstName()
							+ " /", styles.get("plain-left-wrap"), 8);
			rowIndex++;

			OutputStream out = response
					.getOutputStream("application/vnd.ms-excel");
			response.setHeader("Content-Disposition",
					"attachment; filename=\"employeeMilitary.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isShow() {
		if (viewEmployee == true)
			return true;
		else
			return false;
	}

	public boolean isViewEmployee() {
		return viewEmployee;
	}

	public void setViewEmployee(boolean viewEmployee) {
		this.viewEmployee = viewEmployee;
	}
}
