package mn.mcs.electronics.court.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.components.CompEmployeeAddition;
import mn.mcs.electronics.court.components.CompEmployeeEducation;
import mn.mcs.electronics.court.components.CompEmployeeExperience;
import mn.mcs.electronics.court.components.CompEmployeeFamily;
import mn.mcs.electronics.court.components.CompEmployeeGrade;
import mn.mcs.electronics.court.components.CompEmployeeLanguage;
import mn.mcs.electronics.court.components.CompEmployeeMovement;
import mn.mcs.electronics.court.components.CompEmployeeOccupation;
import mn.mcs.electronics.court.components.CompEmployeeOther;
import mn.mcs.electronics.court.components.CompEmployeePersonal;
import mn.mcs.electronics.court.components.CompEmployeePreResult;
import mn.mcs.electronics.court.components.CompEmployeeSkill;
import mn.mcs.electronics.court.components.CompEmployeeTraining;
import mn.mcs.electronics.court.components.salary.CompSalary;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.Role;
import mn.mcs.electronics.court.entities.employee.Educations;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.EmployeeMilitary;
import mn.mcs.electronics.court.entities.employee.Experience;
import mn.mcs.electronics.court.entities.employee.Honour;
import mn.mcs.electronics.court.entities.employee.Relatives;
import mn.mcs.electronics.court.enums.AwardType;
import mn.mcs.electronics.court.enums.EmployeeStatus;
import mn.mcs.electronics.court.enums.MilitaryOrSimple;
import mn.mcs.electronics.court.enums.OrganizationTypeExperience;
import mn.mcs.electronics.court.enums.RelativeFamily;
import mn.mcs.electronics.court.enums.SchoolType;
import mn.mcs.electronics.court.pages.report.Anket;
import mn.mcs.electronics.court.util.Constants;
import mn.mcs.electronics.court.util.WordAPI;
import mn.mcs.electronics.court.util.beans.EmployeeSearchBean;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.got5.tapestry5.jquery.JQueryEventConstants;
import org.got5.tapestry5.jquery.components.AjaxUpload;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;

public class PageEmployeeGeneral {

	/*
	 * CONSTANTS
	 */

	public static final String IMAGE_PATH_EMP = "/images/profile/";
	private static String fontStyleItalic = "/fonts/ariali.ttf";
	private static String docPath = "/documents/";

	/*
	 * INJECTS
	 */

	@InjectPage
	private Anket anket;

	@Inject
	private Context _context;

	@Inject
	private Messages messages;

	@Inject
	private Response response;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@SessionState
	private LoginState loginState;

	@Inject
	private ComponentResources resources;

	@Inject
	@Path("context:/images/excel.jpg")
	private Asset imgExcel;

	@Inject
	private CoreDAO dao;

	@Inject
	private AssetSource imageUrl;

	@InjectComponent
	private Zone uploadResult, yearCount;

	@InjectComponent
	private CompEmployeePersonal personal;

	@InjectComponent
	private CompEmployeeTraining training;

	@InjectComponent
	private CompEmployeeSkill skill;

	@InjectComponent
	private CompEmployeePreResult empPreResult;

	@InjectComponent
	private CompEmployeeOther other;

	@InjectComponent
	private CompEmployeeOccupation occupation;

	@InjectComponent
	private CompEmployeeAddition addition;

	@InjectComponent
	private CompEmployeeEducation education;

	@InjectComponent
	private CompEmployeeExperience experience;

	@InjectComponent
	private CompEmployeeFamily empFamily;

	@InjectComponent
	private CompEmployeeGrade empGrade;

	@InjectComponent
	private CompEmployeeLanguage empLanguage;

	@InjectComponent
	private CompEmployeeMovement empMovement;

	// @InjectComponent
	// private CompEmployeeJobHistory empJobHistory;

	// @InjectComponent
	// private CompEmployeeSalary salary;

	@InjectComponent
	private CompSalary salaryComp;

	@Inject
	private AlertManager manager;

	@Inject
	private Request request;
	/*
	 * PROPERTIES
	 */
	/*
	 * PERSISTS
	 */

	@Persist
	private String allText;

	@Property
	@Persist
	private Role empRole;

	@Property
	@Persist
	private Employee valueEmployee;

	@Property
	@Persist
	private EmployeeSearchBean bean;

	@Persist()
	@Property
	private UploadedFile uploadedFile;

	private Long empID;

	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.YEAR_FORMAT);

	private SimpleDateFormat formatMonth = new SimpleDateFormat(
			Constants.MONTH_FORMAT);

	@Persist
	@Property
	private UploadedFile readFile;

	void onActivate(Long id) {
		empID = id;
	}

	Long onPassivate() {
		return empID;
	}

	void beginRender() {

		// if (empID.equals(-2l)) {
		// this.valueEmployee = new Employee();
		// }

		this.valueEmployee = (Employee) dao.getObject(Employee.class, empID);

		if (valueEmployee == null)
			valueEmployee = new Employee();

		if (valueEmployee != null && valueEmployee.getId() != null) {
			anket.setEmp(valueEmployee);
			personal.setEmployee(valueEmployee);
			training.setEmployee(valueEmployee);
			skill.setEmployee(valueEmployee);
			empPreResult.setEmployee(valueEmployee);
			other.setEmployee(valueEmployee);
			occupation.setEmployee(valueEmployee);
			addition.setEmployee(valueEmployee);
			education.setEmployee(valueEmployee);
			experience.setEmployee(valueEmployee);
			empFamily.setEmployee(valueEmployee);
			empGrade.setEmployee(valueEmployee);
			empLanguage.setEmployee(valueEmployee);
			empMovement.setEmployee(valueEmployee);
			salaryComp.setEmployee(valueEmployee);
			// empJobHistory.setEmployee(valueEmployee);
			// salary.setEmployee(valueEmployee);
		}

		if (allText == null)
			allText = "";
	}

	@CommitAfter
	void onSelectedFromSave() {

		if (readFile != null) {

			// valueEmployee.setPicturePath(readFile.getFileName());

			String[] arr = readFile.getFileName().split("\\.");

			String imageName = valueEmployee.getUuid() + "."
					+ arr[arr.length - 1];

			valueEmployee.setPicturePath(imageName);

			File copied = new File(_context.getRealFile(
					IMAGE_PATH_EMP + imageName).getPath());

			readFile.write(copied);

		}

		if (valueEmployee.getPicturePath() != null) {

			if (dao.getEmployeeByRegister(valueEmployee.getRegisterNo()).size() == 0) {
				if (valueEmployee.getId() != null) {
					valueEmployee.setEmployeeStatus(EmployeeStatus.WORKING);
					dao.saveOrUpdateObject(dao.mergeObject(valueEmployee));

					empID = valueEmployee.getId();

					manager.alert(Duration.SINGLE, Severity.INFO,
							messages.get("successMessage"));
				} else {
					valueEmployee.setOrganization(loginState.getOrganization());
					dao.saveOrUpdateObject(valueEmployee);

					empID = valueEmployee.getId();

					manager.alert(Duration.SINGLE, Severity.INFO,
							messages.get("successMessage"));
				}
			}

			if (dao.getEmployeeByRegister(valueEmployee.getRegisterNo()).size() == 1
					&& dao.getEmployeeByRegister(valueEmployee.getRegisterNo())
							.get(0).equals(valueEmployee)) {

				if (valueEmployee.getId() != null) {
					valueEmployee.setEmployeeStatus(EmployeeStatus.WORKING);
					dao.saveOrUpdateObject(dao.mergeObject(valueEmployee));

					empID = valueEmployee.getId();

					manager.alert(Duration.SINGLE, Severity.INFO,
							messages.get("successMessage"));

				} else {
					valueEmployee.setOrganization(loginState.getOrganization());
					dao.saveOrUpdateObject(valueEmployee);

					empID = valueEmployee.getId();

					manager.alert(Duration.SINGLE, Severity.INFO,
							messages.get("successMessage"));
				}
			} else {
				manager.alert(
						Duration.SINGLE,
						Severity.ERROR,
						valueEmployee.getRegisterNo()
								+ messages.get("Registeredregister"));
			}

		} else {
			manager.alert(Duration.SINGLE, Severity.ERROR, "Зураг оруулна уу!");
		}

	}

	/*
	 * GETTER, SETTER
	 */

	/**
	 * Зураг харагдах
	 */

	public Asset getImageUrl() {

		if (uploadedFile != null) {

			try {
				return imageUrl.getAsset(null, "context:" + IMAGE_PATH_EMP
						+ valueEmployee.getPicturePath(), Locale.ENGLISH);
			} catch (Exception ex) {
				return imageUrl.getAsset(null, "context:/images/default.png",
						Locale.ENGLISH);
			}
		}

		if (valueEmployee.getPicturePath() == null) {
			return imageUrl.getAsset(null, "context:/images/default.png",
					Locale.ENGLISH);
		}
		try {
			return imageUrl.getAsset(null, "context:" + IMAGE_PATH_EMP
					+ valueEmployee.getPicturePath(), Locale.ENGLISH);

		} catch (Exception e) {
			return imageUrl.getAsset(null, "context:/images/default.png",
					Locale.ENGLISH);
		}
	}

	public String getNewPath(UploadedFile file, String imageDir) {
		if (file != null) {
			String[] arr = valueEmployee.getPicturePath().split("\\.");
			String imageName = valueEmployee.getUuid() + "."
					+ arr[arr.length - 1];

			return imageDir + imageName;
		}

		return null;
	}

	public void writePicture(UploadedFile file) {

		valueEmployee.setPicturePath(uploadedFile.getFileName());

		File tmpFile = new File(_context.getRealFile(
				IMAGE_PATH_EMP + file.getFileName()).getPath());

		uploadedFile.write(tmpFile);
	}

	public String getTotalWorkedYear() {

		if (valueEmployee == null || valueEmployee.getId() == null)
			return "";
		Long x = 0l, temp = 0l, monthX = 0l, dayX = 0l;
		if (valueEmployee.getId() != null) {
			x = dao.getTotalWorkedYear(valueEmployee.getId());
			temp = x;
		}
		x = x / 365;
		dayX = temp - x * 365;
		monthX = dayX / 30;
		dayX = dayX - monthX * 30;

		return x + " жил " + monthX + " сар " + dayX + " хоног";
	}

	/** Төрийн байгууллагад цэргийн цолгүй, энгийнээр ажилласан жил **/
	public String getSimpleStateWorkedYear() {

		if (valueEmployee == null || valueEmployee.getId() == null)
			return "";
		Long x = 0l, tempX = 0l, monthX = 0l, dayX = 0l;
		if (valueEmployee.getId() != null) {
			x = dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(),
					OrganizationTypeExperience.ULSIIN,
					MilitaryOrSimple.workingSimple)
					+ dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(),
							OrganizationTypeExperience.SHUUH,
							MilitaryOrSimple.workingSimple)
					+ dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(),
							OrganizationTypeExperience.HUVIIN,
							MilitaryOrSimple.workingSimple);
			tempX = x;
		}

		x = x / 365;
		if (valueEmployee.getId() != null) {
			dayX = tempX - x * 365;
		}
		monthX = dayX / 30;
		dayX = dayX - monthX * 30;

		return x + " жил " + monthX + " сар " + dayX + " хоног";
	}

	/** Төрийн байгууллагад цэргийн цолтой ажилласан жил **/
	public String getMilitaryStateWorkedYear() {

		if (valueEmployee == null || valueEmployee.getId() == null)
			return "";
		Long x = 0l, tempX = 0l, monthX = 0l, dayX = 0l;
		if (valueEmployee.getId() != null) {
			x = dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(),
					OrganizationTypeExperience.ULSIIN,
					MilitaryOrSimple.workingMilitary)
					+ dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(),
							OrganizationTypeExperience.SHUUH,
							MilitaryOrSimple.workingMilitary);
			tempX = x;
		}

		x = x / 365;
		if (valueEmployee.getId() != null)
			dayX = tempX - x * 365;
		monthX = dayX / 30;
		dayX = dayX - monthX * 30;

		return x + " жил " + monthX + " сар " + dayX + " хоног";
	}

	/** ШШГЕГ-т ажилласан жил **/
	public String getCourtTotalWorkedYear() {

		if (valueEmployee == null || valueEmployee.getId() == null)
			return "";
		Long xm = 0l, xs = 0l, yearX = 0l, monthX = 0l, dayX = 0l;
		if (valueEmployee.getId() != null) {

			xs = dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(),
					OrganizationTypeExperience.SHUUH, null);
		}

		/*
		 * if (valueEmployee.getId() != null) { xm =
		 * dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(),
		 * OrganizationTypeExperience.SHUUH, MilitaryOrSimple.workingMilitary);
		 * }
		 */

		dayX = xs + xm;

		yearX = dayX / 365;
		dayX = dayX - yearX * 365;
		monthX = dayX / 30;
		dayX = dayX - monthX * 30;

		return yearX + " жил " + monthX + " сар " + dayX + " хоног";
	}

	public String getSimpleCourtWorkedYear() {

		if (valueEmployee == null || valueEmployee.getId() == null)
			return "";
		Long x = 0l, tempX = 0l, monthX = 0l, dayX = 0l;
		if (valueEmployee.getId() != null) {
			x = dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(),
					OrganizationTypeExperience.SHUUH,
					MilitaryOrSimple.workingSimple);
			tempX = x;
		}

		x = x / 365;
		if (valueEmployee.getId() != null)
			dayX = tempX - x * 365;
		monthX = dayX / 30;
		dayX = dayX - monthX * 30;

		return x + " жил " + monthX + " сар " + dayX + " хоног";
	}

	/** ШШГЕГ-д цэргийн цолтой ажилласан жил **/
	public String getMilitaryCourtWorkedYear() {

		if (valueEmployee == null || valueEmployee.getId() == null)
			return "";
		Long x = 0l, tempX = 0l, monthX = 0l, dayX = 0l;
		if (valueEmployee.getId() != null) {
			x = dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(),
					OrganizationTypeExperience.SHUUH,
					MilitaryOrSimple.workingMilitary);
			tempX = x;
		}

		x = x / 365;
		if (valueEmployee.getId() != null)
			dayX = tempX - x * 365;
		monthX = dayX / 30;
		dayX = dayX - monthX * 30;

		return x + " жил " + monthX + " сар " + dayX + " хоног";
	}

	public boolean isActivated() {
		if (valueEmployee != null && valueEmployee.getId() != null)
			return true;
		return false;
	}

	public String getAge() {

		if (valueEmployee == null || valueEmployee.getId() == null) {
			System.err.println("getAge null");
			return "";
		}
		Long empYear = 0l, empMonth = 0l, empDay = 0l;

		if (valueEmployee.getId() != null)
			empYear = dao.getEmployeeAge(valueEmployee);

		empYear = empYear / 365;
		empDay = dao.getEmployeeAge(valueEmployee) - empYear * 365;
		empMonth = empDay / 30;
		empDay = empDay - empMonth * 30;

		return empYear + " нас " + empMonth + " сар " + empDay + " хоног ";
	}

	/* export word */
	@CommitAfter
	void onSelectedFromExport() throws FileNotFoundException, IOException,
			DocumentException {

		getContent();

		HWPFDocument doc = new HWPFDocument(file("Template.doc"));

		WordAPI.setCellValue(doc, 0, 0, 0, allText);

		OutputStream out = response.getOutputStream("application/vnd.ms-word");
		response.setHeader("Content-Disposition",
				"attachment; filename=\"DESCRIPTION.doc\"");

		doc.write(out);

		out.close();
		out.flush();
	}

	/* file open */

	public POIFSFileSystem file(String wordFileName)
			throws FileNotFoundException, IOException {

		File fl = new File(_context.getRealFile(docPath + wordFileName)
				.getPath());

		if (fl.exists()) {

			InputStream inS = new FileInputStream(fl);

			POIFSFileSystem fs = new POIFSFileSystem(inS);

			return fs;
		}

		return null;
	}

	/* Sep 28, 2013 Erdenetuya.B Begin */
	@OnEvent(component = "uploadImage", value = JQueryEventConstants.AJAX_UPLOAD)
	void onImageUpload(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
		this.readFile = uploadedFile;
		// this.writePicture(uploadedFile);
		ajaxResponseRenderer.addRender("uploadResult", uploadResult);
	}

	@OnEvent(component = "uploadImage", value = JQueryEventConstants.NON_XHR_UPLOAD)
	Object onNonXHRImageUpload(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
		this.writePicture(uploadedFile);
		final JSONObject result = new JSONObject();
		final JSONObject params = new JSONObject().put(
				"url",
				resources.createEventLink("myCustomEvent", "NON_XHR_UPLOAD")
						.toURI()).put("zoneId", "uploadResult");
		result.put(AjaxUpload.UPDATE_ZONE_CALLBACK, params);
		return result;
	}

	@OnEvent(value = "myCustomEvent")
	void onMyCustomEvent(final String someParam) {
		ajaxResponseRenderer.addRender("uploadResult", uploadResult);
	}

	void onUploadException(FileUploadException ex) {
		ajaxResponseRenderer.addRender("uploadResult", uploadResult);
	}

	/* Sep 28, 2013 Erdenetuya.B End */

	Object onActionFromGoAnket() throws IOException, DocumentException {
		if (valueEmployee != null && valueEmployee.getId() != null) {
			return anket.onSubmit();
		}
		return null;
	}

	void onActionFromExperience() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(yearCount);
		}
	}

	public void getContent() throws DocumentException, IOException {

		Date d = new Date();
		Integer relSize = 0;
		String family = "";
		String edu = "";
		String exp = "";
		String occ = "";
		String award = "";
		Relatives husband = null;
		EmployeeMilitary valueEmployeeMilitary = null;

		List<Relatives> rels = null;
		List<Educations> edus = null;
		List<Experience> exps = null;
		List<Honour> honour = null;

		BaseFont arno = BaseFont.createFont(fontStyleItalic,
				BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font f = new Font(arno, 8);

		rels = dao.getRelatives(valueEmployee.getId(), RelativeFamily.ISFAMILY);

		edus = dao.getListEducation(valueEmployee);
		exps = dao.getExperience(valueEmployee);
		valueEmployeeMilitary = dao.getEmployeeMilitary(valueEmployee.getId());
		honour = dao.getListHonour(valueEmployee);

		relSize = rels.size() + 1;

		if (!rels.isEmpty()) {
			for (int i = 0; i < rels.size(); i++) {
				family += rels.get(i).getRelation().getName() + ", ";

				if (rels.get(i).getRelation().getName().equals("Эхнэр"))
					husband = rels.get(i);

				if (rels.get(i).getRelation().getName().equals("Нөхөр"))
					husband = rels.get(i);
			}
		}

		if (family != null && family.length() > 2)
			family = family.substring(0, family.length() - 2);

		if (edus != null) {
			for (int i = 0; i < edus.size(); i++) {
				/*
				 * edu += format.format(edus.get(i).getIngoingDate()) + "-" +
				 * format.format(edus.get(i).getGraduatedDate()) + " онд " +
				 * edus.get(i).getSchool() + ", ";
				 */
				edu += edus.get(i).getIngoingDate() + "-"
						+ edus.get(i).getGraduatedDate() + " онд "
						+ edus.get(i).getCountry().getCountryName()
						+ "  улсын  " + edus.get(i).getCity()
						+ "  хотын/аймгийн  "
						// + edus.get(i).getDistrictSum().getDistrictSumName() +
						// "  сумын/дүүргийн  "
						+ edus.get(i).getSchool() + ", ";

				if (edus.get(i).getSchoolType() == SchoolType.UNIVERSITY)
					occ += edus.get(i).getOccupation().getName() + ", ";
			}

			if (edus != null
					&& edus.get(edus.size() - 1).getOccupation() != null) {
				if (!edus.get(edus.size() - 1).getOccupation().getName()
						.equals("Үгүй")
						|| !edus.get(edus.size() - 1).getOccupation().getName()
								.contains("Мэргэжилгүй")
						|| edus.get(edus.size() - 1).getOccupation().getName() != null) {

					allText += edus.get(edus.size() - 1).getOccupation()
							.getName()
							+ "  мэргэжилтэй. ";
				}
			}
		}

		if (occ != null && occ.length() > 2)
			occ = occ.substring(0, occ.length() - 2);

		if (allText.equals("")) {
			String eol = System.getProperty("line.separator");
			allText = "\t\t\t\t\t" + messages.get("wordDescription") + eol
					+ eol;
			allText += eol;

			allText += (valueEmployeeMilitary != null ? valueEmployeeMilitary
					.getMilitary().getMilitaryName() + "  " : "")
					+ valueEmployee.getLastname()
					+ "-ийн "
					+ valueEmployee.getFirstName() + "\n";
			// allText += valueEmployee.getLastname() + "-ийн " +
			// valueEmployee.getFirstName()+ "\n";
			allText += eol;
			allText += "\t"
					+ valueEmployee.getFamilyName()
					+ " овогт "
					+ valueEmployee.getLastname()
					+ "-ийн "
					+ valueEmployee.getFirstName()
					+ " нь "
					+ (valueEmployee.getBirthDate() != null ? format
							.format(valueEmployee.getBirthDate()) : "")
					+ " онд "
					+ (valueEmployee.getBirthCityProvince() != null ? ""
							: valueEmployee.getBirthCityProvince()
									.getCityName())
					+ "  аймгийн/хотын "
					+ (valueEmployee.getBirthDistrictSum() != null ? valueEmployee
							.getBirthDistrictSum().getDistrictSumName() : "")
					+ " суманд/дүүрэгт төрсөн, " + getAge().substring(0, 2)
					+ " настай, " + "  ам бүл - " + relSize + ", " + family
					+ "-ийн/ын хамт амьдардаг.";

			if (valueEmployee.getEducations() != null) {
				allText = allText + "   " + edu + "-г тус тус төгссөн. ";
			}

			if (valueEmployeeMilitary != null) {
				allText = allText + "  "
						+ valueEmployeeMilitary.getMilitary().getMilitaryName()
						+ " цолтой. ";
			}

			Long courtX = dao.getMilitaryOrSimpleWorkedYear(
					valueEmployee.getId(), OrganizationTypeExperience.SHUUH,
					MilitaryOrSimple.workingSimple)
					+ dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(),
							OrganizationTypeExperience.SHUUH,
							MilitaryOrSimple.workingMilitary);
			Long totalX = dao.getTotalWorkedYear(valueEmployee.getId());

			allText += "  Улсад  " + totalX / 365 + "  жил, "
					+ "  тус байгууллагад  " + courtX / 365
					+ "  жил ажилласан.  ";

			if (exps != null) {
				allText += eol + eol + eol + "\t" + "Ажил эрхлэлтийн байдал: "
						+ eol;
				for (int i = 0; i < exps.size(); i++) {
					if (exps.get(i).getStartDate() != null)
						exp += "\t" + format.format(exps.get(i).getStartDate());

					if (exps.get(i).getEndDate() != null)
						exp += "-" + format.format(exps.get(i).getEndDate())
								+ " онд ";
					else {
						exp += " оны "
								+ formatMonth
										.format(exps.get(i).getStartDate())
								+ " сараас ";
					}

					exp += exps.get(i).getOrganizationname() + "-д "
							+ exps.get(i).getAppointment() + eol + eol;
				}
				allText += exp;
			}

			if (honour != null) {
				award += eol + eol + eol + "\t" + "Шагнагдсан байдал: " + eol
						+ eol;
				for (int i = 0; i < honour.size(); i++) {
					if (honour.get(i).getDateOfAwarded() != null)
						award += "\t"
								+ format.format(honour.get(i)
										.getDateOfAwarded()) + "  онд  ";

					if (honour.get(i).getAwardType() == AwardType.OTHER_ORGANIZATIONPRIZE)
						award += honour.get(i).getOtherPrize() + eol + eol;
					else
						award += honour.get(i).getAward().getName() + eol + eol;
				}
				allText += award;
			}

			allText += eol
					+ eol
					+ "\t"
					+ "Захиргааны удирдлагын газрын Хүний нөөц, сургалтын хэлтсийн санал: "
					+ eol + eol;

			allText += eol + eol + "\t" + "Танилцуулга бичсэн:" + eol + eol;
			allText += "\t"
					+ "Хүний нөөц, сургалтын хэлтсийн дарга,"
					+ eol
					+ "\t"
					+ " ";
		}
	}

}
