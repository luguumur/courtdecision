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
import mn.mcs.electronics.court.entities.employee.Passport;
import mn.mcs.electronics.court.entities.employee.TravelAbroad;
import mn.mcs.electronics.court.entities.employee.Vacation;
import mn.mcs.electronics.court.enums.ChuluuTimeType;
import mn.mcs.electronics.court.enums.YesNo;
import mn.mcs.electronics.court.model.CommandSubjectSelectionModel;
import mn.mcs.electronics.court.model.CountrySelectionModel;
import mn.mcs.electronics.court.model.TravelTypeSelectionModel;
import mn.mcs.electronics.court.model.VacationTypeSelectionModel;
import mn.mcs.electronics.court.pages.Home;
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
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.util.EnumSelectModel;

public class CompEmployeeOther {

	@SessionState
	private LoginState loginState;;

	/*
	 * INJECTS
	 */

	@Inject
	private Environment environment;

	@Inject
	private Context context;

	@Inject
	private Messages messages;

	@Inject
	private CoreDAO dao;

	@Inject
	private Request request;

	@InjectPage
	private Home home;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@InjectComponent
	private Zone chuluuFormZone, chuluuListZone, travelAbroadFormZone,
			traveAbroadListZone, officialPassportFormZone,
			officialPassportListZone;

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
	private AlertManager manager;

	@Inject
	private Response response;

	/*
	 * PERSISTS
	 */

	@Persist
	private Employee employee;

	@Property
	@Persist
	private Vacation vacation;

	@Property
	@Persist
	private Vacation valueVacation;

	@Property
	private List<Vacation> listVacation;

	@Property
	@Persist
	private TravelAbroad travelAbroad;

	@Property
	@Persist
	private TravelAbroad valueTravelAbroad;

	@Property
	private List<TravelAbroad> listTravelAbroad;

	@Property
	@Persist
	private Passport passport;

	@Property
	@Persist
	private Passport valuePassport;

	@Property
	private List<Passport> listPassport;

	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);

	private static final String GRID_ROW_CSS = "myGrid";

	void beginRender() {
		if (employee == null)
			employee = new Employee();

		if (vacation == null)
			vacation = new Vacation();

		if (travelAbroad == null)
			travelAbroad = new TravelAbroad();

		if (passport == null)
			passport = new Passport();

		if (employee != null && employee.getId() != null) {
			listTravelAbroad = dao.getListTravelAbroad(employee);
			listVacation = dao.getListVacation(employee);
			listPassport = dao.getListPassport(employee);
		}
	}

	/*
	 * GETTER, SETTER
	 */

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getVacationDate() {
		if (valueVacation == null
				|| valueVacation.getChuluuOlgosonOgnoo() == null)
			return "";

		return format.format(valueVacation.getChuluuOlgosonOgnoo());
	}

	public String getVacationFromDate() {
		if (valueVacation == null
				|| valueVacation.getChuluuEhlehOgnoo() == null)
			return "";

		return format.format(valueVacation.getChuluuEhlehOgnoo());
	}

	public String getVacationToDate() {
		if (valueVacation == null
				|| valueVacation.getChuluuDuusahOgnoo() == null)
			return "";

		return format.format(valueVacation.getChuluuDuusahOgnoo());
	}

	public String getWentDateName() {
		if (valueTravelAbroad == null
				|| valueTravelAbroad.getWentDate() == null)
			return "";

		return format.format(valueTravelAbroad.getWentDate());
	}

	public String getCameDateName() {
		if (valueTravelAbroad == null
				|| valueTravelAbroad.getCameDate() == null)
			return "";

		return format.format(valueTravelAbroad.getCameDate());
	}

	public String getCountryName() {
		if (valueTravelAbroad == null
				|| valueTravelAbroad.getCountry().getCityProvince() == null)
			return "";

		return valueTravelAbroad.getCountry().getCountryName();
	}

	public String getIsDiplomatPassport() {
		if (valuePassport == null || valuePassport.getIsDiplomat() == null)
			return "";
		if (valuePassport == null || valuePassport.getIsDiplomat() == true)
			return messages.get("yes");
		return messages.get("no");
	}

	public String getGivenDateName() {
		if (valuePassport == null || valuePassport.getGivenDate() == null)
			return "";

		return format.format(valuePassport.getGivenDate());
	}

	public String getexpireDateName() {
		if (valuePassport == null || valuePassport.getExpireDate() == null)
			return "";

		return format.format(valuePassport.getExpireDate());
	}

	public String getGridRowCSS() {
		return GRID_ROW_CSS;
	}

	public int getNumberVacation() {
		return listVacation.indexOf(valueVacation) + 1;
	}

	public int getNumberTravel() {
		return listTravelAbroad.indexOf(valueTravelAbroad) + 1;
	}

	public int getNumber() {
		return listPassport.indexOf(valuePassport) + 1;
	}

	public Asset getImgExcel() {
		return imgExcel;
	}

	public void setImgExcel(Asset imgExcel) {
		this.imgExcel = imgExcel;
	}

	/*
	 * EVENTS
	 */

	/* Чөлөөний мэдээлэл */

	@CommitAfter
	void onSelectedFromSaveVacation() {
		if (request.isXHR()) {
			vacation.setEmployee(employee);
			dao.saveOrUpdateObject(vacation);
			listVacation = dao.getListVacation(employee);
			vacation = new Vacation();
			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));
		}
		ajaxResponseRenderer.addRender(chuluuListZone)
				.addRender(chuluuFormZone);

	}

	@CommitAfter
	void onActionFromDeleteVacation(Vacation vacation) {
//		if (request.isXHR()) {
			dao.deleteObject(vacation);
			listVacation = dao.getListVacation(employee);

//		}
//		ajaxResponseRenderer.addRender(chuluuListZone);
	}

	void onActionFromEditVacation(Vacation vacation) {
		if (request.isXHR()) {
			this.vacation = vacation;
		}
		ajaxResponseRenderer.addRender(chuluuFormZone);
	}

	void onActionFromCancelVacation() {
		if (request.isXHR()) {
			vacation = new Vacation();
		}
		ajaxResponseRenderer.addRender(chuluuFormZone);
	}

	/* Гадаадад зорчсон мэдээлэл */

	@CommitAfter
	void onSelectedFromSaveTravel() {
		if (request.isXHR()) {
			travelAbroad.setEmployee(employee);
			dao.saveOrUpdateObject(travelAbroad);
			listTravelAbroad = dao.getListTravelAbroad(employee);
			travelAbroad = new TravelAbroad();
			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));

		}
		ajaxResponseRenderer.addRender(traveAbroadListZone).addRender(
				travelAbroadFormZone);

	}

	@CommitAfter
	void onActionFromDeleteTravel(TravelAbroad travelAbroad) {
//		if (request.isXHR()) {
			dao.deleteObject(travelAbroad);
			listTravelAbroad = dao.getListTravelAbroad(employee);
//		}
//		ajaxResponseRenderer.addRender(traveAbroadListZone);
	}

	void onActionFromEditTravel(TravelAbroad travelAbroad) {
		if (request.isXHR()) {
			this.travelAbroad = travelAbroad;
		}
		ajaxResponseRenderer.addRender(travelAbroadFormZone);
	}

	void onActionFromCancelTravel() {
		if (request.isXHR()) {
			travelAbroad = new TravelAbroad();
		}
		ajaxResponseRenderer.addRender(travelAbroadFormZone);
	}

	/* Албан пасспортын мэдээлэл */

	@CommitAfter
	void onSelectedFromSavePassport() {
		if (request.isXHR()) {
			passport.setEmployee(employee);
			dao.saveOrUpdateObject(passport);
			listPassport = dao.getListPassport(employee);
			passport = new Passport();
			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));
		}

		ajaxResponseRenderer.addRender(officialPassportFormZone).addRender(
				officialPassportListZone);
	}

	@CommitAfter
	void onActionFromDeletePassport(Passport passport) {
//		if (request.isXHR()) {
			dao.deleteObject(passport);
			listPassport = dao.getListPassport(employee);
//		}
//		ajaxResponseRenderer.addRender(officialPassportListZone);
	}

	void onActionFromEditPassport(Passport passport) {
		if (request.isXHR()) {
			this.passport = passport;
		}
		ajaxResponseRenderer.addRender(officialPassportFormZone);
	}

	void onActionFromCancelPassport() {
		if (request.isXHR()) {
			passport = new Passport();
		}
		ajaxResponseRenderer.addRender(officialPassportFormZone);

	}

	/* export excel */
	void onActionFromExport() {

		try {
			HSSFWorkbook document = new HSSFWorkbook();
			HSSFSheet sheet = ReportUtil.createSheet(document);

			sheet.setMargin((short) 0, 0.5);
			ReportUtil.setColumnWidths(sheet, 0, 0.5, 1, 0.5, 2, 1, 3, 1.5, 4,
					2, 5, 2, 6, 2, 7, 2, 8, 2, 9, 2, 10, 2);

			Map<String, HSSFCellStyle> styles = ReportUtil
					.createStyles(document);

			int sheetNumber = 0;
			int rowIndex = 1;
			int colIndex = 1;
			Long index = 1L;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2,
					messages.get("officialPassport-label"),
					styles.get("title"), 5);

			ExcelAPI.setCellValue(document, sheetNumber, ++rowIndex, 1,
					messages.get("date") + ": " + format.format(new Date()),
					styles.get("plain-left-wrap"), 5);

			ExcelAPI.setCellValue(
					document,
					sheetNumber,
					++rowIndex,
					1,
					messages.get("employee") + ": "
							+ employee.getFullNameFirstChar(),
					styles.get("plain-left-wrap"), 5);
			rowIndex += 2;

			/* column headers */
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1,
					messages.get("number-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2,
					messages.get("passportNo-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3,
					messages.get("isGiven-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 4,
					messages.get("givenDate-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 5,
					messages.get("isDiplomat-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 6,
					messages.get("expireDate-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 7,
					messages.get("extentDate1-label"),
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 8,
					messages.get("extentDate2-label"),
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 9,
					messages.get("isRetrieve-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 10,
					messages.get("retrieveDate-label"),
					styles.get("header-wrap"));

			rowIndex++;
			if (listPassport != null)
				for (Passport s : listPassport) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (listPassport.indexOf(s) + 1) + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getPassportNo() != null) ? s
									.getPassportNo().toString() : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(
							document,
							sheetNumber,
							rowIndex,
							colIndex++,
							(s.getIsGiven() != null) ? messages.get(s
									.getIsGiven().toString()) : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(
							document,
							sheetNumber,
							rowIndex,
							colIndex++,
							(s.getGivenDate() != null) ? format.format(s
									.getGivenDate()) : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(
							document,
							sheetNumber,
							rowIndex,
							colIndex++,
							(s.getIsDiplomat() != null) ? messages.get(s
									.getIsDiplomat().toString()) : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(
							document,
							sheetNumber,
							rowIndex,
							colIndex++,
							(s.getExpireDate() != null) ? format.format(s
									.getExpireDate()) : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(
							document,
							sheetNumber,
							rowIndex,
							colIndex++,
							(s.getExtentDate1() != null) ? format.format(s
									.getExtentDate1()) : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(
							document,
							sheetNumber,
							rowIndex,
							colIndex++,
							(s.getExtentDate2() != null) ? format.format(s
									.getExtentDate2()) : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(
							document,
							sheetNumber,
							rowIndex,
							colIndex++,
							(s.getIsRetrieve() != null) ? messages.get(s
									.getIsRetrieve().toString()) : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(
							document,
							sheetNumber,
							rowIndex,
							colIndex++,
							(s.getRetrieveDate() != null) ? format.format(s
									.getRetrieveDate()) : "", styles
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
					"attachment; filename=\"AlbanPassport.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void onActionFromExportTravel() {

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

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2,
					messages.get("TravelAbroadInformation-label"),
					styles.get("title"), 5);

			ExcelAPI.setCellValue(document, sheetNumber, ++rowIndex, 1,
					messages.get("date") + ": " + format.format(new Date()),
					styles.get("plain-left-wrap"), 5);
			rowIndex += 2;

			/* column headers */
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1,
					messages.get("number-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2,
					messages.get("country-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3,
					messages.get("travelType-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 4,
					messages.get("jobTask-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 5,
					messages.get("wentDate-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 6,
					messages.get("cameDate-label"), styles.get("header-wrap"));

			rowIndex++;
			if (listTravelAbroad != null)
				for (TravelAbroad s : listTravelAbroad) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (listTravelAbroad.indexOf(s) + 1) + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getCountry() != null) ? s
									.getCountry().getCountryName() : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getTravelType() != null) ? s
									.getTravelType().getName() : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,
							(s.getJobTask() != null) ? s.getJobTask() : "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(
							document,
							sheetNumber,
							rowIndex,
							colIndex++,
							(s.getWentDate() != null) ? format.format(s
									.getWentDate()) : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(
							document,
							sheetNumber,
							rowIndex,
							colIndex++,
							(s.getCameDate() != null) ? format.format(s
									.getCameDate()) : "", styles
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
					"attachment; filename=\"GadaadadZorchsonMedeelel.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * SELECT MODELS
	 */

	public SelectModel getCountrySelectionModel() {
		CountrySelectionModel sm = new CountrySelectionModel(dao);
		return sm;
	}

	public SelectModel getTravelTypeSelectionModel() {
		TravelTypeSelectionModel sm = new TravelTypeSelectionModel(dao);
		return sm;
	}

	public SelectModel getCommandSubjectSelectionModel() {
		CommandSubjectSelectionModel sm = new CommandSubjectSelectionModel(dao);
		return sm;
	}

	public SelectModel getVacationTypeSelectionModel() {
		VacationTypeSelectionModel sm = new VacationTypeSelectionModel(dao);
		return sm;
	}

	public SelectModel getChuluuTimeTypeSelectionModel() {
		EnumSelectModel sm = new EnumSelectModel(ChuluuTimeType.class, messages);
		return sm;
	}

	public SelectModel getYesNoSelectionModel() {
		EnumSelectModel sm = new EnumSelectModel(YesNo.class, messages);
		return sm;
	}
}
