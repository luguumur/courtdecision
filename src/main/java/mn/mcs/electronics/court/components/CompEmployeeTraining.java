package mn.mcs.electronics.court.components;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.Role;
import mn.mcs.electronics.court.entities.configuration.City;
import mn.mcs.electronics.court.entities.configuration.CityProvince;
import mn.mcs.electronics.court.entities.configuration.Country;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.Training;
import mn.mcs.electronics.court.model.CityProvinceSelectionModel;
import mn.mcs.electronics.court.model.CitySelectionModel;
import mn.mcs.electronics.court.model.CountrySelectionModel;
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

public class CompEmployeeTraining {

	@SessionState
	private LoginState loginState;

	/*
	 * INJECTS
	 */

	@Inject
	private Response response;

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
	private Zone trainingZone, trainingListZone, trainingUlsZone;

	@Inject
	@Path("context:/images/excel.jpg")
	private Asset imgExcel;

	@Inject
	private AlertManager manager;

	/*
	 * PERSISTS
	 */

	@Persist
	private Employee employee;

	@Property
	@Persist
	private Role empRole;

	@Property
	@Persist
	private Training training;

	@Property
	@Persist
	private Training valueTraining;

	@Property
	@Persist
	private Country country;

	@Property
	@Persist
	private CityProvince city;

	@Property
	@Persist
	private City province;

	/* Grid value */
	@Property
	private List<Training> listTraining;

	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);

	void beginRender() {
		if (employee == null)
			employee = new Employee();

		if (training == null)
			training = new Training();

		if (employee != null && employee.getId() != null) {
			initGrid();
		}
	}

	void initGrid() {
		listTraining = dao.getTraining(getEmployee());
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

	public boolean isMongolia() {
		if (country != null && country.getCountryName().equals("Монгол"))
			return true;
		return false;
	}

	public int getNumber() {
		return listTraining.indexOf(valueTraining) + 1;
	}

	public String getDateFormat() {
		return Constants.DATE_FORMAT;
	}

	public String getStartDay() {
		if (valueTraining == null || valueTraining.getStartDate() == null)
			return "";
		return Integer.toString(valueTraining.getStartDate());
	}

	public String getEndDay() {
		if (valueTraining == null || valueTraining.getEndDate() == null)
			return "";
		return Integer.toString(valueTraining.getEndDate());
	}

	public String getCountryName() {
		if (valueTraining == null || valueTraining.getCountry() == null)
			return "";
		return valueTraining.getCountry().getCountryName();
	}

	public String getCityName() {
		if (valueTraining == null || valueTraining.getCountry() == null)
			return "";
		return valueTraining.getCity();
	}

	public Asset getImgExcel() {
		return imgExcel;
	}

	/*
	 * EVENTS
	 */

	@CommitAfter
	void onSuccessFromTrainingForm() {
		if (request.isXHR()) {
			training.setEmployee(employee);
			training.setCountry(country);

			if (training.getStartDate() != null
					&& training.getEndDate() != null
					&& training.getStartDate() <= training.getEndDate()) {

				if (isMongolia() == true)
					training.setCity(province.getCityName());
				else
					training.setCity(city.getCityProvinceName());

				dao.saveOrUpdateObject(training);

				training = new Training();
				manager.alert(Duration.SINGLE, Severity.INFO,
						messages.get("successMessage"));

			} else {
				manager.alert(Duration.SINGLE, Severity.ERROR,
						messages.get("StartdateIsLaterThanEndDate"));
			}

			initGrid();

			ajaxResponseRenderer.addRender(trainingZone).addRender(
					trainingListZone);
		}
	}

	void onActionFromEdit(Training training) {
		if (request.isXHR()) {
			this.training = training;
			country = this.training.getCountry();
			ajaxResponseRenderer.addRender(trainingZone);
		}
	}

	void onActionFromCancel() {
		if (request.isXHR()) {
			training = new Training();
			ajaxResponseRenderer.addRender(trainingZone);
		}
	}

	@CommitAfter
	void onActionFromDelete(Training training) {
//		if (request.isXHR()) {
			dao.deleteObject(training);
			initGrid();
//			ajaxResponseRenderer.addRender(trainingListZone);
			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successDeleteMessage"));
//		}
	}

	void onValueChangedFromUlsClick() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(trainingUlsZone);
		}
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

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2,
					messages.get("employeeTraining"), styles.get("title"), 8);

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
					messages.get("country-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3,
					messages.get("city-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 4,
					messages.get("training_organization-label"),
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 5,
					messages.get("startDate-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 6,
					messages.get("endDate-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 7,
					messages.get("course-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 8,
					messages.get("gradeDate-label"), styles.get("header-wrap"));

			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;
			if (listTraining != null)
				for (Training s : listTraining) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (listTraining.indexOf(s) + 1) + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getCountry() != null) ? s
									.getCountry().getCountryName() : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getCity() != null) ? s.getCity()
									: "", styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,
							(s.getTrainingOrganization() != null) ? s
									.getTrainingOrganization().toString() : "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(
							document,
							sheetNumber,
							rowIndex,
							colIndex++,
							(s.getStartDate() != null) ? format.format(s
									.getStartDate()) : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(
							document,
							sheetNumber,
							rowIndex,
							colIndex++,
							(s.getEndDate() != null) ? format.format(s
									.getEndDate()) : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getCourse() != null) ? s.getCourse()
									: "", styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(
							document,
							sheetNumber,
							rowIndex,
							colIndex++,
							(s.getGradeDate() != null) ? format.format(s
									.getGradeDate()) : "", styles
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
					"attachment; filename=\"employeeTraining.xls\"");

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

	public SelectModel getCitySelectionModel() {

		if (country == null) {
			List<Country> lst = dao.getListCountry();
			if (!lst.isEmpty())
				country = lst.get(0);
		}
		CitySelectionModel sm = new CitySelectionModel(dao, country);
		return sm;
	}

	public SelectModel getProvinceSelectionModel() {

		CityProvinceSelectionModel sm = new CityProvinceSelectionModel(dao);
		return sm;
	}

}
