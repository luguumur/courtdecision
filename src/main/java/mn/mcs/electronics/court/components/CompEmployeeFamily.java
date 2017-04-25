package mn.mcs.electronics.court.components;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.Relatives;
import mn.mcs.electronics.court.enums.RelativeFamily;
import mn.mcs.electronics.court.model.CityProvinceSelectionModel;
import mn.mcs.electronics.court.model.DistrictSumSelectionModel;
import mn.mcs.electronics.court.model.FamilyAppointmentSelectionModel;
import mn.mcs.electronics.court.model.RelativeTypeSelectionModel;
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

public class CompEmployeeFamily {

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
	@Path("context:/images/excel.jpg")
	private Asset imgExcel;

	@InjectComponent
	private Zone familyZone, listZone, listRelativeListZone, familyAimagZone;

	@Inject
	private Request request;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@Inject
	private AlertManager manager;

	/*
	 * PERSISTS
	 */

	@Property
	@Persist
	private Relatives relative;

	@Persist
	private Employee employee;

	/*
	 * PROPERTIES
	 */

	@Property
	private List<Relatives> listRelative;

	@Property
	private List<Relatives> listFamily;

	@Property
	private Relatives valueRelative;

	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);

	void beginRender() {

		if (employee != null && employee.getId() != null) {

			listFamily = dao.getRelatives(employee.getId(),
					RelativeFamily.ISFAMILY);

			listRelative = dao.getRelatives(employee.getId(),
					RelativeFamily.ISRELATIVE);
		}

		if (relative == null)
			relative = new Relatives();
	}

	/*
	 * EVENTS
	 */

	@CommitAfter
	void onSelectedFromSave() {
		if (request.isXHR()) {
			try {
				relative.setEmployee(employee);				
				dao.saveOrUpdateObject(relative);

				relative = new Relatives();

				if (employee != null && employee.getId() != null) {

					listRelative = dao.getRelatives(employee.getId(),
							RelativeFamily.ISRELATIVE);

					listFamily = dao.getRelatives(employee.getId(),
							RelativeFamily.ISFAMILY);
				}

				manager.alert(Duration.SINGLE, Severity.INFO,
						messages.get("successMessage"));

				ajaxResponseRenderer.addRender(familyZone).addRender(listZone)
						.addRender(listRelativeListZone);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}

	@CommitAfter
	void onDelete(Relatives relative) {
		if (request.isXHR()) {
			dao.deleteObject(relative);
			listRelative = dao.getRelatives(employee.getId(),
					RelativeFamily.ISRELATIVE);

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successDeleteMessage"));

			ajaxResponseRenderer.addRender(listRelativeListZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteFamily(Relatives relative) {
//		if (request.isXHR()) {
			dao.deleteObject(relative);
			listFamily = dao.getRelatives(employee.getId(),
					RelativeFamily.ISFAMILY);
//			ajaxResponseRenderer.addRender("listZone", listZone);
//		}
	}

	void onActionFromEdit(Relatives relative) {
		if (request.isXHR()) {
			this.relative = relative;
			ajaxResponseRenderer.addRender(familyZone);
		}
	}

	void onActionFromEditFamily(Relatives relative) {
		if (request.isXHR()) {
			this.relative = relative;
			ajaxResponseRenderer.addRender(familyZone);
		}
	}

	void onActionFromCancel() {
		if (request.isXHR()) {
			relative = new Relatives();
			ajaxResponseRenderer.addRender(familyZone);
		}

	}

	void onValueChangedFromAimagHotClick() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(familyAimagZone);
		}
	}

	/*
	 * SELECT MODELS
	 */

	public SelectModel getRelativeSelectionModel() {
		return new EnumSelectModel(RelativeFamily.class, messages);
	}

	public SelectModel getRelationSelectionModel() {
		return new RelativeTypeSelectionModel(dao);
	}

	public SelectModel getFamilyAppointmentSelectionModel() {
		return new FamilyAppointmentSelectionModel(dao);
	}

	public SelectModel getCityProvinceSelectionModel() {
		return new CityProvinceSelectionModel(dao);
	}

	public SelectModel getDistrictSumSelectionModel() {
		return new DistrictSumSelectionModel(dao,
				relative.getBirthCityProvince());
	}

	/*
	 * GETTER, SETTER
	 */

	public int getNumber() {
		return listRelative.indexOf(valueRelative) + 1;
	}

	public int getNumberF() {
		return listFamily.indexOf(valueRelative) + 1;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getDateFormat() {
		return Constants.DATE_FORMAT;
	}

	public String getBirthday() {
		if (valueRelative == null || valueRelative.getBirthDate() == null)
			return "";
		return Integer.toString(valueRelative.getBirthDate());
	}

	public String getRelationName() {
		if (valueRelative == null || valueRelative.getRelation() == null)
			return "";
		return valueRelative.getRelation().getName();
	}

	public String getAppointmentName() {
		if (valueRelative == null || valueRelative.getAppointmentType() == null)
			return "";
		return valueRelative.getAppointmentType().getName();
	}

	public String getCurrentJob() {
		if (valueRelative == null || valueRelative.getOccupation() == null)
			return "";
		return valueRelative.getOccupation();
	}

	public Asset getImgExcel() {
		return imgExcel;
	}

	/*
	 * EXCEL
	 */
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

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2,
					messages.get("employeeRelativeInformation"),
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
					messages.get("relation-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3,
					messages.get("lastname-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 4,
					messages.get("firstname-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 5,
					messages.get("birthdateFamily-label"),
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 6,
					messages.get("birthCityProvince-label"),
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 7,
					messages.get("appointmentName-label"),
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 8,
					messages.get("currentJob-label"), styles.get("header-wrap"));

			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;
			if (listRelative != null)
				for (Relatives s : listRelative) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (listRelative.indexOf(s) + 1) + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getRelation() != null) ? s
									.getRelation().getName() : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getLastName() != null) ? s
									.getLastName().toString() : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getFirstName() != null) ? s
									.getFirstName().toString() : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getBirthDate() != null) ? s
									.getBirthDate().toString() : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getBirthCityProvince() != null) ? s
									.getBirthCityProvince().getCityName() : "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getAppointmentType() != null) ? s
									.getAppointmentType().getName() : "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,
							(s.getOccupation() != null) ? s.getOccupation()
									: "", styles.get("plain-left-wrap-border"));

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
					"attachment; filename=\"employeeRelative.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* export excel */
	void onActionFromExportFamily() {

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
					messages.get("employeeFamily"), styles.get("title"), 8);

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
					messages.get("relation-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3,
					messages.get("lastname-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 4,
					messages.get("firstname-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 5,
					messages.get("birthdateFamily-label"),
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 6,
					messages.get("birthCityProvince-label"),
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 7,
					messages.get("appointmentName-label"),
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 8,
					messages.get("currentJob-label"), styles.get("header-wrap"));

			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;
			if (listFamily != null)
				for (Relatives s : listFamily) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (listFamily.indexOf(s) + 1) + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getRelation() != null) ? s
									.getRelation().getName() : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getLastName() != null) ? s
									.getLastName().toString() : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getFirstName() != null) ? s
									.getFirstName().toString() : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(
							document,
							sheetNumber,
							rowIndex,
							colIndex++,
							(s.getBirthDate() != null) ? Integer.toString(s
									.getBirthDate()) : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getBirthCityProvince() != null) ? s
									.getBirthCityProvince().getCityName() : "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getAppointmentType() != null) ? s
									.getAppointmentType().getName() : "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,
							(s.getOccupation() != null) ? s.getOccupation()
									: "", styles.get("plain-left-wrap-border"));

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
					"attachment; filename=\"employeeFamily.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
