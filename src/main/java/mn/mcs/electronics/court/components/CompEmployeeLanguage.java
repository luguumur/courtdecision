package mn.mcs.electronics.court.components;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mn.mcs.electronics.court.aso.LoginState;
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
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
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

public class CompEmployeeLanguage {

	/*
	 * INJECTS
	 */

	@Inject
	private CoreDAO dao;

	@Inject
	private Messages messages;

	@Inject
	private Response response;

	@SessionState
	private LoginState loginState;

	@Inject
	private AlertManager manager;

	@Inject
	private Request request;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@InjectComponent
	private Zone formLanguageZone, listLanguageZone, formProgramZone,
			listProgramZone, formOtherProgramZone, listOtherProgramZone,
			formEquipmentZone, listEquipmentZone;

	/*
	 * PERSISTS
	 */

	@Persist
	private Employee employee;

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

	/*
	 * PROPERTIES
	 */

	@Property
	private List<Language> listLanguage;

	@Property
	private List<Computer> listComputer;

	@Property
	private List<ComputerOther> listComputerOther;

	@Property
	private List<OfficeEquipment> listOfficeEquipment;

	private int numberCo = 1;

	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);

	void beginRender() {

		if (language == null)
			language = new Language();

		if (computer == null)
			computer = new Computer();

		if (computerOther == null)
			computerOther = new ComputerOther();

		if (officeEquipment == null)
			officeEquipment = new OfficeEquipment();

		try {
			if (employee != null && employee.getId() != null) {
				initGridLanguage();
				initGridComputer();
				initGridComputerOther();
				initGridLanguage();
				initGridEquipment();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	void initGridLanguage() {
		listLanguage = dao.getLanguage(employee);
	}

	void initGridComputer() {
		listComputer = dao.getComputer(employee);
	}

	void initGridComputerOther() {
		listComputerOther = dao.getListComputerOther(employee);
	}

	void initGridEquipment() {
		listOfficeEquipment = dao.getListOfficeEquipment(employee);
	}

	/*
	 * EVENTS
	 */

	@CommitAfter
	void onSelectedFromSave() {
		if (request.isXHR()) {
			language.setEmployee(employee);
			dao.saveOrUpdateObject(language);
			language = new Language();

			initGridLanguage();

			ajaxResponseRenderer.addRender(formLanguageZone).addRender(
					listLanguageZone);

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));
		}
	}

	void onActionFromEdit(Language language) {
		if (request.isXHR()) {
			this.language = language;
			ajaxResponseRenderer.addRender(formLanguageZone);
		}
	}

	void onActionFromCancel() {
		if (request.isXHR()) {
			language = new Language();
			ajaxResponseRenderer.addRender(formLanguageZone);
		}
	}

	@CommitAfter
	void onSelectedFromSaveProgram() {
		if (request.isXHR()) {
			computer.setEmployee(employee);
			dao.saveOrUpdateObject(computer);
			computer = new Computer();

			initGridComputer();

			ajaxResponseRenderer.addRender(formProgramZone).addRender(
					listProgramZone);

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));
		}
	}

	void onActionFromEditProgram(Computer computer) {
		if (request.isXHR()) {
			this.computer = computer;
			ajaxResponseRenderer.addRender(formProgramZone);
		}
	}

	void onActionFromCancelProgram() {
		if (request.isXHR()) {
			computer = new Computer();
			ajaxResponseRenderer.addRender(formProgramZone);
		}
	}

	@CommitAfter
	void onActionFromDelete(Language language) {
//		if (request.isXHR()) {
			dao.deleteObject(language);

			initGridLanguage();

//			ajaxResponseRenderer.addRender(listLanguageZone);

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successDeleteMessage"));
//		}
	}

	@CommitAfter
	void onSelectedFromSaveOtherProgram() {
		if (request.isXHR()) {
			computerOther.setEmployee(employee);
			dao.saveOrUpdateObject(computerOther);
			computerOther = new ComputerOther();

			initGridComputerOther();

			ajaxResponseRenderer.addRender(formOtherProgramZone).addRender(
					listOtherProgramZone);

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));

		}
	}

	void onActionFromEditOtherProgram(ComputerOther computerOther) {
		if (request.isXHR()) {
			this.computerOther = computerOther;
			ajaxResponseRenderer.addRender(formOtherProgramZone);
		}
	}

	void onActionFromCancelOtherProgram() {
		if (request.isXHR()) {
			computerOther = new ComputerOther();
			ajaxResponseRenderer.addRender(formOtherProgramZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteOtherProgram(ComputerOther computerOther) {
//		if (request.isXHR()) {
			dao.deleteObject(computerOther);
			initGridComputerOther();
			ajaxResponseRenderer.addRender(listOtherProgramZone);

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successDeleteMessage"));
//		}
	}

	@CommitAfter
	void onActionFromDeleteProgram(Computer computer) {
//		if (request.isXHR()) {
			dao.deleteObject(computer);
			initGridComputer();
//			ajaxResponseRenderer.addRender(listProgramZone);

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successDeleteMessage"));
//		}

	}

	@CommitAfter
	void onSelectedFromSaveOfficeEquipment() {
		if (request.isXHR()) {
			officeEquipment.setEmployee(employee);
			dao.saveOrUpdateObject(officeEquipment);
			officeEquipment = new OfficeEquipment();

			initGridEquipment();
			ajaxResponseRenderer.addRender(formEquipmentZone).addRender(
					listEquipmentZone);

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));
		}
	}

	void onActionFromEditOfficeEquipment(OfficeEquipment officeEquipment) {
		if (request.isXHR()) {
			this.officeEquipment = officeEquipment;
			ajaxResponseRenderer.addRender(formEquipmentZone);
		}
	}

	void onActionFromCancelOfficeEquipment() {
		if (request.isXHR()) {
			officeEquipment = new OfficeEquipment();
			ajaxResponseRenderer.addRender(formEquipmentZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteOfficeEquipment(OfficeEquipment officeEquipment) {
//		if (request.isXHR()) {
			dao.deleteObject(officeEquipment);
			initGridEquipment();
//			ajaxResponseRenderer.addRender(listEquipmentZone);

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successDeleteMessage"));
//		}
	}

	/* selection model */
	public SelectModel getLanguageLevelSelectionModel() {
		return new EnumSelectModel(LanguageLevel.class, messages);
	}

	public SelectModel getForeignLanguageSelectionModel() {
		return new ForeignLanguageSelectionModel(dao);
	}

	public SelectModel getComputerProgramSelectionModel() {
		return new ComputerProgramSelectionModel(dao);
	}

	public SelectModel getOfficeEquipmentSelectionModel() {
		return new OfficeFacilitySelectionModel(dao);
	}

	/* getter,setter */

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

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getForeignLanguage() != null) ? s
									.getForeignLanguage().toString() : "",
							styles.get("plain-left-wrap-border"));
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
}
