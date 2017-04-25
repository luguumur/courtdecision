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
import mn.mcs.electronics.court.entities.employee.Computer;
import mn.mcs.electronics.court.entities.employee.ComputerOther;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.Language;
import mn.mcs.electronics.court.entities.employee.OfficeEquipment;
import mn.mcs.electronics.court.enums.LanguageLevel;
import mn.mcs.electronics.court.model.ComputerProgramSelectionModel;
import mn.mcs.electronics.court.model.ForeignLanguageSelectionModel;
import mn.mcs.electronics.court.model.OfficeFacilitySelectionModel;
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
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.util.EnumSelectModel;

public class PageEmployeeLanguage {

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
	private Language language;

	@Property
	@Persist
	private Computer computer;

	@Property
	@Persist
	private ComputerOther computerOther;

	@Property
	@Persist
	private OfficeEquipment officeEquipment;

	@Property
	@Persist
	private Language valueLanguage;

	@Property
	@Persist
	private Computer valueComputer;

	@Property
	@Persist
	private ComputerOther valueComputerOther;

	@Property
	@Persist
	private OfficeEquipment valueOfficeEquipment;

	/* Grid value */
	@Property
	private List<Language> listLanguage;

	@Property
	private List<Computer> listComputer;

	@Property
	private List<ComputerOther> listComputerOther;

	@Property
	private List<OfficeEquipment> listOfficeEquipment;

	@Persist
	private boolean viewEmployee;

	private int numberCo = 0;

	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);

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

		listLanguage = dao.getLanguage(employee);

		if (language == null)
			language = new Language();

		listComputer = dao.getComputer(employee);

		if (computer == null)
			computer = new Computer();

		listComputerOther = dao.getListComputerOther(employee);

		if (computerOther == null)
			computerOther = new ComputerOther();

		listOfficeEquipment = dao.getListOfficeEquipment(employee);

		if (officeEquipment == null)
			officeEquipment = new OfficeEquipment();
	}

	@CommitAfter
	void onSelectedFromSave() {
		language.setEmployee(employee);
		dao.saveOrUpdateObject(language);
		language = new Language();
	}

	void onActionFromEdit(Language language) {
		this.language = language;
	}

	void onActionFromCancel() {
		language = new Language();
	}

	@CommitAfter
	void onSelectedFromSaveProgram() {
		computer.setEmployee(employee);
		dao.saveOrUpdateObject(computer);
		computer = new Computer();
	}

	void onActionFromEditProgram(Computer computer) {
		this.computer = computer;
	}

	void onActionFromCancelProgram() {
		computer = new Computer();
	}

	@CommitAfter
	void onActionFromDelete(Language language) {
		dao.deleteObject(language);
	}

	@CommitAfter
	void onSelectedFromSaveOtherProgram() {
		computerOther.setEmployee(employee);
		dao.saveOrUpdateObject(computerOther);
		computerOther = new ComputerOther();
	}

	void onActionFromEditOtherProgram(ComputerOther computerOther) {
		this.computerOther = computerOther;
	}

	void onActionFromCancelOtherProgram() {
		computerOther = new ComputerOther();
	}

	@CommitAfter
	void onActionFromDeleteOtherProgram(ComputerOther computerOther) {
		dao.deleteObject(computerOther);
	}

	@CommitAfter
	void onActionFromDeleteProgram(Computer computer) {
		dao.deleteObject(computer);
	}

	@CommitAfter
	void onSelectedFromSaveOfficeEquipment() {
		officeEquipment.setEmployee(employee);
		dao.saveOrUpdateObject(officeEquipment);
		officeEquipment = new OfficeEquipment();
	}

	void onActionFromEditOfficeEquipment(OfficeEquipment officeEquipment) {
		this.officeEquipment = officeEquipment;
	}

	void onActionFromCancelOfficeEquipment() {
		officeEquipment = new OfficeEquipment();
	}

	@CommitAfter
	void onActionFromDeleteOfficeEquipment(OfficeEquipment officeEquipment) {
		dao.deleteObject(officeEquipment);
	}

	/* selection model */
	public SelectModel getLanguageLevelSelectionModel() {
		return new EnumSelectModel(LanguageLevel.class, messages);
	}

	public SelectModel getForeignLanguageSelectionModel() {
		SelectModel sm = new ForeignLanguageSelectionModel(dao);
		return sm;
	}

	public SelectModel getComputerProgramSelectionModel() {
		SelectModel sm = new ComputerProgramSelectionModel(dao);
		return sm;
	}

	public SelectModel getOfficeEquipmentSelectionModel() {
		SelectModel sm = new OfficeFacilitySelectionModel(dao);
		return sm;
	}

	/* getter,setter */

	public String getGridRowCSS() {
		return GRID_ROW_CSS;
	}

	public int getNumberCo() {
		return numberCo++;
	}

	public int getNumber() {
		return listComputer.indexOf(valueComputer) + 1;
	}

	public int getNumberOfficeEquipment() {
		return listOfficeEquipment.indexOf(valueOfficeEquipment) + 1;
	}

	public int getNumberComputerOther() {
		return listComputerOther.indexOf(valueComputerOther) + 1;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Asset getImgExcel() {
		return imgExcel;
	}

	public String getDateFormat() {
		return Constants.DATE_FORMAT;
	}

	// public String getForeignLanguageName(){
	// if(valueLanguage==null||valueLanguage.getForeignLanguage()==null)
	// return "";
	// return valueLanguage.getForeignLanguage();
	// }

	public String getProgramName() {
		if (valueComputer == null || valueComputer.getProgram() == null)
			return "";
		return valueComputer.getProgram().getProgramName();
	}

	public String getFacilityName() {
		if (valueOfficeEquipment == null
				|| valueOfficeEquipment.getFacility() == null)
			return "";
		return valueOfficeEquipment.getFacility().getFacilityName();
	}

	/* export excel */
	void onActionFromExportLanguage() {

		try {
			HSSFWorkbook document = new HSSFWorkbook();
			HSSFSheet sheet = ReportUtil.createSheet(document);

			sheet.setMargin((short) 0, 0.5);
			ReportUtil.setColumnWidths(sheet, 0, 0.5, 1, 0.5, 2, 2, 3, 2, 3, 2,
					4, 2, 5, 2, 6, 2, 7, 2, 8, 2, 9, 2);

			Map<String, HSSFCellStyle> styles = ReportUtil
					.createStyles(document);

			int sheetNumber = 0;
			int rowIndex = 1;
			int colIndex = 1;
			Long index = 1L;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2,
					messages.get("employeeLanguage"), styles.get("title"), 8);

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
					messages.get("language-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3,
					messages.get("listening-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 4,
					messages.get("speaking-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 5,
					messages.get("reading-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 6,
					messages.get("writing-label"), styles.get("header-wrap"));

			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;
			if (listLanguage != null)
				for (Language s : listLanguage) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (listLanguage.indexOf(s) + 1) + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(
							document,
							sheetNumber,
							rowIndex,
							colIndex++,
							(s.getForeignLanguage() != null) ? s
									.getForeignLanguage().toString() : "", styles
									.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(
							document,
							sheetNumber,
							rowIndex,
							colIndex++,
							(s.getListening() != null) ? messages.get(s
									.getListening().toString()) : "", styles
									.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(
							document,
							sheetNumber,
							rowIndex,
							colIndex++,
							(s.getSpeaking() != null) ? messages.get(s
									.getSpeaking().toString()) : "", styles
									.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(
							document,
							sheetNumber,
							rowIndex,
							colIndex++,
							(s.getReading() != null) ? messages.get(s
									.getReading().toString()) : "", styles
									.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(
							document,
							sheetNumber,
							rowIndex,
							colIndex++,
							(s.getWriting() != null) ? messages.get(s
									.getWriting().toString()) : "", styles
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
					"attachment; filename=\"employeeLanguage.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void onActionFromExportComputer() {

		try {
			HSSFWorkbook document = new HSSFWorkbook();
			HSSFSheet sheet = ReportUtil.createSheet(document);

			sheet.setMargin((short) 0, 0.5);
			ReportUtil.setColumnWidths(sheet, 0, 0.5, 1, 0.5, 2, 2, 3, 2, 3, 2,
					4, 2, 5, 2, 6, 2, 7, 2, 8, 2, 9, 2);

			Map<String, HSSFCellStyle> styles = ReportUtil
					.createStyles(document);

			int sheetNumber = 0;
			int rowIndex = 1;
			int colIndex = 1;
			Long index = 1L;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2,
					messages.get("employeeComputer"), styles.get("title"), 8);

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
					messages.get("program-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3,
					messages.get("programlevel-label"),
					styles.get("header-wrap"));

			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;
			if (listComputer != null)
				for (Computer s : listComputer) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (listComputer.indexOf(s) + 1) + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getProgram() != null) ? s
									.getProgram().getProgramName() : "", styles
									.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(
							document,
							sheetNumber,
							rowIndex,
							colIndex++,
							(s.getProgramlevel() != null) ? messages.get(s
									.getProgramlevel().toString()) : "", styles
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
					"attachment; filename=\"employeeComputer.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void onActionFromExportOtherProgram() {

		try {
			HSSFWorkbook document = new HSSFWorkbook();
			HSSFSheet sheet = ReportUtil.createSheet(document);

			sheet.setMargin((short) 0, 0.5);
			ReportUtil.setColumnWidths(sheet, 0, 0.5, 1, 0.5, 2, 2, 3, 2, 3, 2,
					4, 2, 5, 2, 6, 2, 7, 2, 8, 2, 9, 2);

			Map<String, HSSFCellStyle> styles = ReportUtil
					.createStyles(document);

			int sheetNumber = 0;
			int rowIndex = 1;
			int colIndex = 1;
			Long index = 1L;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2,
					messages.get("employeeComputer"), styles.get("title"), 8);

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
					messages.get("program-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3,
					messages.get("programlevel-label"),
					styles.get("header-wrap"));

			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;
			if (listComputerOther != null)
				for (ComputerOther s : listComputerOther) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,
							(listComputerOther.indexOf(s) + 1) + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getOtherProgram() != null) ? s
									.getOtherProgram().toString() : "", styles
									.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(
							document,
							sheetNumber,
							rowIndex,
							colIndex++,
							(s.getOtherProgramlevel() != null) ? messages.get(s
									.getOtherProgramlevel().toString()) : "",
							styles.get("plain-left-wrap-border"));

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
					"attachment; filename=\"employeeOtherProgram.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void onActionFromExportOfficeEquipment() {

		try {
			HSSFWorkbook document = new HSSFWorkbook();
			HSSFSheet sheet = ReportUtil.createSheet(document);

			sheet.setMargin((short) 0, 0.5);
			ReportUtil.setColumnWidths(sheet, 0, 0.5, 1, 0.5, 2, 2, 3, 2, 3, 2,
					4, 2, 5, 2, 6, 2, 7, 2, 8, 2, 9, 2);

			Map<String, HSSFCellStyle> styles = ReportUtil
					.createStyles(document);

			int sheetNumber = 0;
			int rowIndex = 1;
			int colIndex = 1;
			Long index = 1L;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2,
					messages.get("employeeOfficeEquipment"),
					styles.get("title"), 8);

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
					messages.get("facility-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3,
					messages.get("facilityLevel-label"),
					styles.get("header-wrap"));

			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;
			if (listOfficeEquipment != null)
				for (OfficeEquipment s : listOfficeEquipment) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (listOfficeEquipment.indexOf(s) + 1)
									+ "", styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getFacility() != null) ? s
									.getFacility().getFacilityName() : "",
							styles.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(
							document,
							sheetNumber,
							rowIndex,
							colIndex++,
							(s.getFacilityLevel() != null) ? messages.get(s
									.getFacilityLevel().toString()) : "",
							styles.get("plain-left-wrap-border"));

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
					"attachment; filename=\"employeeOfficeFacility.xls\"");

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
