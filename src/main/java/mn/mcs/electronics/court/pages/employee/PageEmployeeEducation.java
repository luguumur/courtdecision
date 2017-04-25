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

public class PageEmployeeEducation {

	@InjectComponent
	private LayoutEmployee layoutEmployee;

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
	private CoreDAO dao;

	@Persist
	private Employee employee;

	@Inject
	private Messages messages;

	@Inject
	private Response response;

	@SessionState
	private LoginState loginState;

	/* Боловсролын мэдээлэл */
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
	private List<Educations> listEducation;

	@Property
	@Persist
	private Educations valueEducation;

	@Property
	private List<DegreeGradeRank> listDegreeGradeRank;

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

	@InjectComponent
	private Zone aimagZone;

	@InjectComponent
	private Zone surguuliZone;

	@InjectComponent
	private Zone schoolTypeZone;

	@InjectComponent
	private Zone doctorZone;

	@InjectComponent
	private Zone sumZone;

	@InjectComponent
	private Zone sumLabelZone;

	@Inject
	private Request request;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	private static final String GRID_ROW_CSS = "myGrid";

	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);

	/* Эрдмийн зэрэг цол */
	@Property
	@Persist
	private Degrees degrees;

	@Property
	@Persist
	private List<Degrees> listDegrees;

	@Property
	@Persist
	private Degrees valueDegrees;

	@Persist
	private boolean viewEmployee;

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
		listDegreeGradeRank = dao.getListDegreeGradeRank(employee);

		if (education == null)
			education = new Educations();

		if (degreeGradeRank == null)
			degreeGradeRank = new DegreeGradeRank();

		listEducation = dao.getListEducation(employee);

		/* Эрдмийн зэрэг цол */
		if (degrees == null)
			degrees = new Degrees();

		listDegrees = dao.getListDegrees(employee);

		if (schoolType == null)
			schoolType = SchoolType.PRIMARYSCHOOL;

		if (doctorType == null)
			doctorType = DoctorType.EDUCATIONDOCTOR;

		if (doctorType == null)
			doctorType = DoctorType.SCIENCEDOCTOR;

	}

	@CommitAfter
	void onSelectedFromSave() {
		education.setEmployee(getEmployee());
		education.setSchoolType(schoolType);

		dao.saveOrUpdateObject(education);
		education = new Educations();
	}

	void onActionFromCancel() {
		education = new Educations();
	}

	void onActionFromEdit(Educations education) {
		this.education = education;
		schoolType = this.education.getSchoolType();
	}

	@CommitAfter
	void onActionFromDelete(Educations education) {
		dao.deleteObject(education);
	}

	/* Degree */
	@CommitAfter
	void onSelectedFromSaveDegree() {
		degrees.setEmployee(getEmployee());
		degrees.setDoctorType(doctorType);
		if (isScienceDoctor() == true)
			degrees.setDoctor(scienceDoctorType.getName());
		if (isEducationDoctor() == true)
			degrees.setDoctor(educationDoctorType.getName());
		dao.saveOrUpdateObject(degrees);
		degrees = new Degrees();
	}

	void onActionFromCancelDegree() {
		degrees = new Degrees();
	}

	void onActionFromEditDegree(Degrees degree) {
		this.degrees = degree;
		this.doctorType = degree.getDoctorType();
	}

	@CommitAfter
	void onActionFromDeleteDegree(Degrees degree) {
		dao.deleteObject(degree);
	}

	// Эрдмийн цол, зэрэг
	@CommitAfter
	void onSelectedFromSaveRank() {
		degreeGradeRank.setEmployee(employee);
		dao.saveOrUpdateObject(degreeGradeRank);
		degreeGradeRank = new DegreeGradeRank();
	}

	void onActionFromCancelRank() {
		degreeGradeRank = new DegreeGradeRank();
	}

	void onActionFromEditRank(DegreeGradeRank degreeGradeRank) {
		this.degreeGradeRank = degreeGradeRank;
	}

	@CommitAfter
	void onActionFromDeleteRank(DegreeGradeRank degreeGradeRank) {
		dao.deleteObject(degreeGradeRank);
	}

	/* Ajax zone */
	void onValueChangedFromUlsClick() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender("aimagZone", aimagZone)
					.addRender("sumZone", sumZone)
					.addRender("sumLabelZone", sumLabelZone)
					.addRender("surguuliZone", surguuliZone);
		}
	}

	Object onValueChangedFromSchoolTypeClick() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender("surguuliZone", surguuliZone)
					.addRender("schoolTypeZone", schoolTypeZone);
		}
		return null;
	}

	void onValueChangedFromAimagClick() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender("sumZone", sumZone);
		}
	}

	Object onValueChangedFromDoctorTypeClick() {
		return request.isXHR() ? doctorZone.getBody() : null;
	}

	/* Select model */
	public SelectModel getCountrySelectionModel() {
		CountrySelectionModel sm = new CountrySelectionModel(dao);
		return sm;
	}

	public SelectModel getCitySelectionModel() {
		if (education.getCountry() == null) {
			List<Country> lst = dao.getListCountry();
			if (!lst.isEmpty())
				education.setCountry(lst.get(0));// = lst.get(0);
		}

		CitySelectionModel sm = new CitySelectionModel(dao,
				education.getCountry());
		return sm;
	}

	public SelectModel getProvinceSelectionModel() {

		CityProvinceSelectionModel sm = new CityProvinceSelectionModel(dao);
		return sm;
	}

	public SelectModel getSchoolSelectionModel() {
		if (education.getCountry() == null) {
			List<Country> lst = dao.getListCountry();
			if (!lst.isEmpty())
				education.setCountry(lst.get(0));// = lst.get(0);
		}
		SchoolSelectionModel sm = new SchoolSelectionModel(dao,
				education.getCountry(), null);
		return sm;
	}

	public SelectModel getDegreeTypeSelectionModel() {
		DegreeTypeSelectionModel sm = new DegreeTypeSelectionModel(dao);

		return sm;
	}

	public SelectModel getScienceDoctorTypeSelectionModel() {
		DegreeTypeOfScienceDoctorSelectionModel sm = new DegreeTypeOfScienceDoctorSelectionModel(
				dao);

		return sm;
	}

	public SelectModel getEducationDoctorTypeSelectionModel() {
		DegreeTypeOfEducationDoctorSelectionModel sm = new DegreeTypeOfEducationDoctorSelectionModel(
				dao);

		return sm;
	}

	public SelectModel getDoctorTypeSelectionModel() {
		return new EnumSelectModel(DoctorType.class, messages);
	}

	public SelectModel geteducationDegreeSelectionModel() {
		EducationDegreeSelectionModel sm = new EducationDegreeSelectionModel(
				dao);
		return sm;
	}

	public SelectModel getOccupationSelectionModel() {
		OccupationSelectionModel sm = new OccupationSelectionModel(dao,
				schoolType);
		return sm;
	}

	public SelectModel getSchoolTypeSelectionModel() {
		return new EnumSelectModel(SchoolType.class, messages);
	}

	public SelectModel getAcademicRankSelectionModel() {
		AcademicRankSelectionModel sm = new AcademicRankSelectionModel(dao);

		return sm;
	}

	public SelectModel getDistrictSumSelectionModel() {
		DistrictSumSelectionModel sm = new DistrictSumSelectionModel(dao,
				province);
		return sm;
	}

	public SelectModel getUniversityTypeSelectionModel() {
		return new EnumSelectModel(UniversityType.class, messages);
	}

	public boolean isPrimarySchool() {
		if (schoolType == SchoolType.PRIMARYSCHOOL)
			return true;

		return false;
	}

	public boolean isUniversity() {
		if (schoolType == SchoolType.UNIVERSITY)
			return true;

		return false;
	}

	/* Dec 07, 2012 Jargalsuren.S Start */
	public boolean isMSUT() {
		if (schoolType == SchoolType.MSUT)
			return true;

		return false;
	}

	public boolean isCOLLEGE() {
		if (schoolType == SchoolType.COLLEGE)
			return true;

		return false;
	}

	/* Dec 07, 2012 Jargalsuren.S End */

	public boolean isIsMongolia() {
		if (education.getCountry() == null) {
			List<Country> lst = dao.getListCountry();
			if (!lst.isEmpty())
				education.setCountry(lst.get(0));
		}
		if (education.getCountry().getIsMongolia() != null
				&& education.getCountry().getIsMongolia() == true)
			return true;
		else
			return false;
	}

	public boolean isNotMongolia() {
		if (education.getCountry() == null) {
			List<Country> lst = dao.getListCountry();
			if (!lst.isEmpty())
				education.setCountry(lst.get(0));
		}

		if (education.getCountry().getIsMongolia() == null
				|| education.getCountry().getIsMongolia() == false)
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
	public String getGridRowCSS() {
		return GRID_ROW_CSS;
	}

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
				return true;
			}
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
