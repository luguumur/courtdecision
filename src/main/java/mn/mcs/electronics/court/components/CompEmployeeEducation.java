package mn.mcs.electronics.court.components;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.City;
import mn.mcs.electronics.court.entities.configuration.CityProvince;
import mn.mcs.electronics.court.entities.configuration.Country;
import mn.mcs.electronics.court.entities.configuration.DegreeTypeOfEducationDoctor;
import mn.mcs.electronics.court.entities.configuration.DegreeTypeOfScienceDoctor;
import mn.mcs.electronics.court.entities.employee.DegreeGradeRank;
import mn.mcs.electronics.court.entities.employee.Degrees;
import mn.mcs.electronics.court.entities.employee.Educations;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.enums.DoctorType;
import mn.mcs.electronics.court.enums.SchoolType;
import mn.mcs.electronics.court.enums.UniversityType;
import mn.mcs.electronics.court.model.AcademicRankSelectionModel;
import mn.mcs.electronics.court.model.CityProvinceSelectionModel;
import mn.mcs.electronics.court.model.CitySelectionModel;
import mn.mcs.electronics.court.model.CountrySelectionModel;
import mn.mcs.electronics.court.model.DegreeTypeOfEducationDoctorSelectionModel;
import mn.mcs.electronics.court.model.DegreeTypeOfScienceDoctorSelectionModel;
import mn.mcs.electronics.court.model.DegreeTypeSelectionModel;
import mn.mcs.electronics.court.model.DistrictSumSelectionModel;
import mn.mcs.electronics.court.model.EducationDegreeSelectionModel;
import mn.mcs.electronics.court.model.OccupationSelectionModel;
import mn.mcs.electronics.court.model.SchoolSelectionModel;
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

public class CompEmployeeEducation {

	/*
	 * INJECTS
	 */

	@Inject
	@Path("context:/images/excel.jpg")
	private Asset imgExcel;

	@Inject
	private CoreDAO dao;

	@Persist
	private Employee employee;

	@Inject
	private Messages messages;

	@Inject
	private Response response;

	@SessionState
	private LoginState loginState;

	@InjectComponent
	private Zone surguuliZone, schoolTypeZone, doctorZone, sumZone,
			educationInfoZone, degreeInfoZone, empAcademicRankZone, eduLocZone,
			educationListZone, degreeListZone, empAcademicRankListZone;

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
	private UniversityType universityType;

	@Property
	@Persist
	private Educations education;

	@Property
	@Persist
	private DoctorType doctorType;

	@Property
	@Persist
	private DegreeTypeOfScienceDoctor scienceDoctorType;

	@Property
	@Persist
	private DegreeTypeOfEducationDoctor educationDoctorType;

	@Property
	@Persist
	private SchoolType schoolType;

	@Property
	@Persist
	private DegreeGradeRank degreeGradeRank;

	@Property
	@Persist
	private DegreeGradeRank valueDegreeGradeRank;

	@Property
	@Persist
	private CityProvince city;

	@Property
	@Persist
	private City province;

	@Property
	@Persist
	private Country country;

	@Property
	@Persist
	private Degrees degrees;

	/*
	 * PROPERTIES
	 */

	@Property
	private List<DegreeGradeRank> listDegreeGradeRank;

	@Property
	private List<Degrees> listDegrees;

	@Property
	private Degrees valueDegrees;

	@Property
	private List<Educations> listEducation;

	@Property
	private Educations valueEducation;

	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);

	private Long empID;

	void onActivate(Long id) {
		empID = id;
	}

	Long onPassivate() {
		return empID;
	}

	void beginRender() {

		if (employee != null && employee.getId() != null) {
			initGridEducation();
			initGridDegreeGrade();
			initGridDegree();
		}

		if (education == null)
			education = new Educations();

		if (degreeGradeRank == null)
			degreeGradeRank = new DegreeGradeRank();

		/* Эрдмийн зэрэг цол */
		if (degrees == null)
			degrees = new Degrees();

		if (schoolType == null)
			schoolType = SchoolType.PRIMARYSCHOOL;

		if (doctorType == null)
			doctorType = DoctorType.EDUCATIONDOCTOR;

		if (doctorType == null)
			doctorType = DoctorType.SCIENCEDOCTOR;

	}

	void initGridEducation() {
		listEducation = dao.getListEducation(employee);
	}

	void initGridDegreeGrade() {
		listDegreeGradeRank = dao.getListDegreeGradeRank(employee);
	}

	void initGridDegree() {
		listDegrees = dao.getListDegrees(employee);
	}

	/*
	 * EVENTS
	 */

	@CommitAfter
	void onSelectedFromSave() {
		if (request.isXHR()) {
			education.setEmployee(getEmployee());
			dao.saveOrUpdateObject(education);
			education = new Educations();
			initGridEducation();
			ajaxResponseRenderer.addRender(educationInfoZone).addRender(
					educationListZone);
			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));
		}
	}

	void onActionFromCancel() {
		if (request.isXHR()) {
			education = new Educations();
			ajaxResponseRenderer.addRender(educationInfoZone);
		}
	}

	void onActionFromEdit(Educations education) {
		if (request.isXHR()) {
			this.education = education;
			schoolType = this.education.getSchoolType();

			ajaxResponseRenderer.addRender(educationInfoZone);
		}
	}

	@CommitAfter
	void onActionFromDelete(Educations education) {
//		if (request.isXHR()) {
			dao.deleteObject(education);
//			ajaxResponseRenderer.addRender(educationListZone);
			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successDeleteMessage"));
//		}
	}

	@CommitAfter
	void onSelectedFromSaveDegree() {
		if (request.isXHR()) {
			degrees.setEmployee(getEmployee());
			degrees.setDoctorType(doctorType);

			if (isScienceDoctor() == true)
				degrees.setDoctor(scienceDoctorType.getName());
			if (isEducationDoctor() == true)
				degrees.setDoctor(educationDoctorType.getName());

			dao.saveOrUpdateObject(degrees);
			degrees = new Degrees();

			initGridDegree();

			ajaxResponseRenderer.addRender(degreeInfoZone).addRender(
					degreeListZone);

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));
		}
	}

	void onActionFromCancelDegree() {
		if (request.isXHR()) {
			degrees = new Degrees();
			ajaxResponseRenderer.addRender(degreeInfoZone);
		}
	}

	void onActionFromEditDegree(Degrees degree) {
		if (request.isXHR()) {
			this.degrees = degree;
			this.doctorType = degree.getDoctorType();
			ajaxResponseRenderer.addRender(degreeInfoZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteDegree(Degrees degree) {
//		if (request.isXHR()) {
			dao.deleteObject(degree);
//			ajaxResponseRenderer.addRender(degreeListZone);

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successDeleteMessage"));
//		}
	}

	// Эрдмийн цол, зэрэг
	@CommitAfter
	void onSelectedFromSaveRank() {
		if (request.isXHR()) {
			degreeGradeRank.setEmployee(employee);
			dao.saveOrUpdateObject(degreeGradeRank);
			degreeGradeRank = new DegreeGradeRank();

			initGridDegreeGrade();

			ajaxResponseRenderer.addRender(empAcademicRankZone).addRender(
					empAcademicRankListZone);

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));
		}
	}

	void onActionFromCancelRank() {
		if (request.isXHR()) {
			degreeGradeRank = new DegreeGradeRank();
			ajaxResponseRenderer.addRender(empAcademicRankZone);
		}
	}

	void onActionFromEditRank(DegreeGradeRank degreeGradeRank) {
		if (request.isXHR()) {
			this.degreeGradeRank = degreeGradeRank;
			ajaxResponseRenderer.addRender(empAcademicRankZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteRank(DegreeGradeRank degreeGradeRank) {
//		if (request.isXHR()) {
			dao.deleteObject(degreeGradeRank);
			ajaxResponseRenderer.addRender(empAcademicRankListZone);
			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successDeleteMessage"));
//		}
	}

	void onValueChangedFromUlsClick() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(eduLocZone);
		}
	}

	void onValueChangedFromSchoolTypeClick() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(surguuliZone).addRender(
					schoolTypeZone);
		}
	}

	void onValueChangedFromUniversityTypeClick() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(surguuliZone);
		}
	}

	void onValueChangedFromAimagClick() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(sumZone);
		}
	}

	void onValueChangedFromDoctorTypeClick() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(doctorZone);
		}
	}

	void onValueChangedFromDegreeType() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(schoolTypeZone);
		}
	}

	/*
	 * SELECT MODELS
	 */

	public SelectModel getCountrySelectionModel() {
		return new CountrySelectionModel(dao);
	}

	public SelectModel getCitySelectionModel() {
		return new CitySelectionModel(dao, education.getCountry());
	}

	public SelectModel getProvinceSelectionModel() {
		return new CityProvinceSelectionModel(dao);
	}

	public SelectModel getSchoolSelectionModel() {
		return new SchoolSelectionModel(dao, education.getCountry(),
				education.getUniversityType());
	}

	public SelectModel getDegreeTypeSelectionModel() {
		return new DegreeTypeSelectionModel(dao);
	}

	public SelectModel getScienceDoctorTypeSelectionModel() {
		return new DegreeTypeOfScienceDoctorSelectionModel(dao);
	}

	public SelectModel getEducationDoctorTypeSelectionModel() {
		return new DegreeTypeOfEducationDoctorSelectionModel(dao);
	}

	public SelectModel getDoctorTypeSelectionModel() {
		return new EnumSelectModel(DoctorType.class, messages);
	}

	public SelectModel geteducationDegreeSelectionModel() {
		return new EducationDegreeSelectionModel(dao);
	}

	public SelectModel getOccupationSelectionModel() {
		return new OccupationSelectionModel(dao, education.getSchoolType());
	}

	public SelectModel getSchoolTypeSelectionModel() {
		return new EnumSelectModel(SchoolType.class, messages);
	}

	public SelectModel getAcademicRankSelectionModel() {
		return new AcademicRankSelectionModel(dao);
	}

	public SelectModel getDistrictSumSelectionModel() {
		return new DistrictSumSelectionModel(dao, education.getCity());
	}

	public SelectModel getUniversityTypeSelectionModel() {
		return new EnumSelectModel(UniversityType.class, messages);
	}

	public boolean isPrimarySchool() {
		if (education.getSchoolType() == SchoolType.PRIMARYSCHOOL
				|| schoolType == null)
			return true;
		return false;
	}

	public boolean isUniversity() {
		if (education.getSchoolType() == SchoolType.UNIVERSITY)
			return true;
		return false;
	}

	/* Dec 07, 2012 Jargalsuren.S Start */
	public boolean isMSUT() {
		if (education.getSchoolType() == SchoolType.MSUT)
			return true;
		return false;
	}

	public boolean isCOLLEGE() {
		if (education.getSchoolType() == SchoolType.COLLEGE)
			return true;
		return false;
	}

	/* Dec 07, 2012 Jargalsuren.S End */

	public boolean isMongolia() {
		if (education.getCountry() != null
				&& education.getCountry().getCountryName().equals("Монгол"))
			return true;
		return false;
	}

	public boolean isScienceDoctor() {
		if (doctorType == DoctorType.SCIENCEDOCTOR)
			return true;

		return false;
	}

	public boolean isEducationDoctor() {
		if (doctorType == DoctorType.EDUCATIONDOCTOR)
			return true;

		return false;
	}

	/* Getter Setter */

	public int getNumberCo() {
		return listEducation.indexOf(valueEducation) + 1;
	}

	public int getNumber() {
		return listDegrees.indexOf(valueDegrees) + 1;
	}

	public int getNumberRank() {
		return listDegreeGradeRank.indexOf(valueDegreeGradeRank) + 1;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getIngoingDate() {
		if (valueEducation.getIngoingDate() == null || valueEducation == null)
			return "";

		return valueEducation.getIngoingDate().toString();
	}

	public String getGraduatedDate() {
		if (valueEducation == null || valueEducation.getGraduatedDate() == null)
			return "";

		return valueEducation.getGraduatedDate().toString();
	}

	public String getDegreeDate() {
		if (valueDegrees == null || valueDegrees.getIssuedDate() == null)
			return "";

		return valueDegrees.getIssuedDate();
	}

	public String getCountryName() {
		if (valueEducation == null || valueEducation.getCountry() == null)
			return "";

		return valueEducation.getCountry().getCountryName();
	}

	public String getIssuedCountryName() {
		if (valueDegrees == null || valueDegrees.getIssuedCountry() == null)
			return "";

		return valueDegrees.getIssuedCountry().getCountryName();
	}

	public String getDegreeName() {
		if (valueDegrees == null || valueDegrees.getDegree() == null)
			return "";

		return valueDegrees.getDegree().getName();
	}

	public String getDoctorTypeName() {
		if (valueDegrees == null || valueDegrees.getDoctorType() == null)
			return "";

		return messages.get(valueDegrees.getDoctorType().name());
	}

	public String getAcademicRank() {
		if (valueDegreeGradeRank == null
				|| valueDegreeGradeRank.getAcademicRank() == null)
			return "";
		return valueDegreeGradeRank.getAcademicRank().getName();
	}

	public String getRankOrganization() {
		if (valueDegreeGradeRank == null
				|| valueDegreeGradeRank.getRankOrganization() == null)
			return "";
		return valueDegreeGradeRank.getRankOrganization();
	}

	public String getRankDate() {
		if (valueDegreeGradeRank == null
				|| valueDegreeGradeRank.getRankDate() == null)
			return "";
		return valueDegreeGradeRank.getRankDate();
	}

	public String getRankCertificate() {
		if (valueDegreeGradeRank == null
				|| valueDegreeGradeRank.getRankCertificate() == null)
			return "";
		return valueDegreeGradeRank.getRankCertificate();
	}

	public String getSchoolCell() {
		if (valueEducation != null || valueEducation.getSchoolType() != null) {
			if (valueEducation.getSchoolType() == SchoolType.PRIMARYSCHOOL
					&& valueEducation.getSchool() != null)
				return valueEducation.getSchool();

			if (valueEducation.getSchoolType() == SchoolType.UNIVERSITY
					&& valueEducation.getUniversity() != null)
				return valueEducation.getUniversity().getName();

			if (valueEducation.getSchoolType() == SchoolType.COLLEGE
					&& valueEducation.getSchool() != null)
				return valueEducation.getSchool();

			if (valueEducation.getSchoolType() == SchoolType.MSUT
					&& valueEducation.getSchool() != null)
				return valueEducation.getSchool();
		}

		return "";
	}

	public boolean getVisibleSumDuureg() {
		if (education.getCountry() != null) {
			if (education.getCountry().getCountryName().contains("Монгол")) {
				return false;
			}
		}

		return true;
	}

	public boolean getOccDisabled() {
		if (education.getDegreeType() != null
				&& (education.getDegreeType().getName().contains("Бүрэн") || education
						.getDegreeType().getName().contains("Бага"))) {
			return true;
		}

		return false;
	}

	public Asset getImgExcel() {
		return imgExcel;
	}

	public String getDateFormat() {
		return Constants.DATE_FORMAT;
	}

	/* export excel */
	void onActionFromExportEducation() {

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
					messages.get("employeeEducation"), styles.get("title"), 7);

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
					messages.get("school-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3,
					messages.get("degreeType-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 4,
					messages.get("occupation-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 5,
					messages.get("ingoingDate-label"),
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 6,
					messages.get("graduatedDate-label"),
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 7,
					messages.get("country-label"), styles.get("header-wrap"));
			;

			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;
			if (listEducation != null)
				for (Educations s : listEducation) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (listEducation.indexOf(s) + 1) + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getSchool() != null) ? s.getSchool()
									: "", styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getDegreeType() != null) ? s
									.getDegreeType().getName() : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getOccupation() != null) ? s
									.getOccupation().getName() : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getIngoingDate() != null) ? s
									.getIngoingDate().toString() : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getGraduatedDate() != null) ? s
									.getGraduatedDate().toString() : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getCountry() != null) ? s
									.getCountry().getCountryName().toString()
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
					"attachment; filename=\"employeeEducation.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void onActionFromExportDegrees() {

		try {
			HSSFWorkbook document = new HSSFWorkbook();
			HSSFSheet sheet = ReportUtil.createSheet(document);

			sheet.setMargin((short) 0, 0.5);
			ReportUtil.setColumnWidths(sheet, 0, 0.5, 1, 0.5, 2, 2, 3, 2, 3, 2,
					4, 3, 5, 4, 6, 2, 7, 2, 8, 2, 9, 2);

			Map<String, HSSFCellStyle> styles = ReportUtil
					.createStyles(document);

			int sheetNumber = 0;
			int rowIndex = 1;
			int colIndex = 1;
			Long index = 1L;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2,
					messages.get("employeeDegrees"), styles.get("title"), 7);

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
					messages.get("degree-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3,
					messages.get("issuedCountry-label"),
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 4,
					messages.get("degreeDate-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 5,
					messages.get("certificatedNo-label"),
					styles.get("header-wrap"));

			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;
			if (listDegrees != null)
				for (Degrees s : listDegrees) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (listDegrees.indexOf(s) + 1) + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getDoctor() != null) ? s.getDoctor()
									: "", styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getIssuedCountry() != null) ? s
									.getIssuedCountry().getCountryName() : "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,
							(s.getIssuedDate() != null) ? s.getIssuedDate()
									: "", styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getCertificatedNo() != null) ? s
									.getCertificatedNo().toString() : "",
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
					"attachment; filename=\"employeeDegrees.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// зэрэг дэв
	void onActionFromExportRank() {

		try {
			HSSFWorkbook document = new HSSFWorkbook();
			HSSFSheet sheet = ReportUtil.createSheet(document);

			sheet.setMargin((short) 0, 0.5);
			ReportUtil.setColumnWidths(sheet, 0, 0.5, 1, 0.5, 2, 2, 3, 4, 4, 3,
					5, 4);

			Map<String, HSSFCellStyle> styles = ReportUtil
					.createStyles(document);

			int sheetNumber = 0;
			int rowIndex = 1;
			int colIndex = 1;
			Long index = 1L;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2,
					messages.get("employeeAcademicRank"), styles.get("title"),
					5);

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
					messages.get("numberRank-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2,
					messages.get("academicRank-label"),
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3,
					messages.get("rankOrganization-label"),
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 4,
					messages.get("rankDate-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 5,
					messages.get("rankCertificate-label"),
					styles.get("header-wrap"));

			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;
			if (listDegreeGradeRank != null)
				for (DegreeGradeRank s : listDegreeGradeRank) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (listDegreeGradeRank.indexOf(s) + 1)
									+ "", styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getAcademicRank() != null) ? s
									.getAcademicRank().getName() : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(
							document,
							sheetNumber,
							rowIndex,
							colIndex++,
							(s.getRankOrganization() != null) ? s
									.getRankOrganization() : "", styles
									.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,
							(s.getRankDate() != null) ? s.getRankDate() : "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(
							document,
							sheetNumber,
							rowIndex,
							colIndex++,
							(s.getRankCertificate() != null) ? s
									.getRankCertificate() : "", styles
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
					"attachment; filename=\"employeeDegreeGrade.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isShow() {
		return true;
	}
}
