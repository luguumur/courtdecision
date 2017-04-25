package mn.mcs.electronics.court.components;

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

import javax.servlet.http.HttpSession;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.Educations;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.EmployeeMilitary;
import mn.mcs.electronics.court.entities.employee.Experience;
import mn.mcs.electronics.court.entities.employee.Honour;
import mn.mcs.electronics.court.entities.employee.Relatives;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.AwardType;
import mn.mcs.electronics.court.enums.EmployeeStatus;
import mn.mcs.electronics.court.enums.MilitaryOrSimple;
import mn.mcs.electronics.court.enums.OrganizationTypeExperience;
import mn.mcs.electronics.court.enums.RelativeFamily;
import mn.mcs.electronics.court.enums.SchoolType;
import mn.mcs.electronics.court.pages.employee.PageEmployee;
import mn.mcs.electronics.court.pages.report.Anket;
import mn.mcs.electronics.court.pages.report.EmployeeCard;
import mn.mcs.electronics.court.util.Constants;
import mn.mcs.electronics.court.util.WordAPI;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.tynamo.security.services.SecurityService;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;

@Import(stylesheet = "classpath:org/tynamo/themes/tapestryskin/theme.css")
public class LayoutEmployee {

	@SessionState
	private LoginState loginState;

	@Inject
	private RequestGlobals requestGlobals;

	@Inject
	private ComponentResources resources;

	@Inject
	private Environment environment;

	@Inject
	private Messages messages;

	@Inject
	private Context context;

	@Persist
	private String allText;

	@Inject
	private SecurityService securityService;

	@Inject
	private CoreDAO dao;

	@InjectPage
	private Anket anket;

	@InjectPage
	private EmployeeCard employeeCard;
	
	@InjectPage
	private PageEmployee listEmployee;

	@Inject
	private AssetSource imageUrl;

	public static final String IMAGE_PATH_EMP = "/images/profile/";

	@Inject
	private Response response;

	@Inject
	private Context _context;

	@Persist
	private Object item;

	/*@Persist
	private String filename;*/

	@InjectPage
	private PageEmployee pageEmployee;

	@InjectComponent
	private Layout layout;

	@Property
	@Parameter(required = true)
	private String title;

	@Property(write = false)
	@Parameter(value = "block:subMenuBlock", defaultPrefix = BindingConstants.LITERAL)
	private Block subMenuBlock;

	@Property(write = false)
	@Parameter(value = "block:navBlock", defaultPrefix = BindingConstants.LITERAL)
	private Block navBlock;

	@Property
	@Parameter(required = false, defaultPrefix = BindingConstants.MESSAGE)
	private String heading;

	@Property
	@Parameter(required = false, defaultPrefix = BindingConstants.MESSAGE)
	private String menu;

	@Property
	@Parameter(required = false, defaultPrefix = BindingConstants.LITERAL)
	private String bodyId;

	@Persist(PersistenceConstants.FLASH)
	@Property
	private String message;

	@Persist
	private Employee valueEmployee;

	@Persist
	private Organization organization;

	@Persist
	@Property
	private UploadedFile file;

	@Inject
	@Path("context:/css/index.css")
	private Asset styles;

	private static Integer switchBlock;

	@Persist
	private boolean isView;

	@Persist
	private String errorMsg;

	private static String activeTab;

	private static String activeSubTab;

	private static final String TAB_DEFAULT = "plain";

	private static final String TAB_SELECTED = "selected";

	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.YEAR_FORMAT);
	
	private SimpleDateFormat formatMonth = new SimpleDateFormat(
			Constants.MONTH_FORMAT);
	
	private static String docPath = "/documents/";

	@Persist
	private boolean hasForm;
	
	@Persist
	String employeeRegister;
	
	private static String fontType = "/fonts/arial.ttf";
	private static String fontTypeBold = "/fonts/arialbd.ttf";
	private static String fontStyleItalic = "/fonts/ariali.ttf";

	void beginRender() {
		
		layout.setHasForm(true);

		if (activeTab == null)
			activeTab = "detail";

		if (activeSubTab == null)
			activeSubTab = "personal";

		if (valueEmployee == null)
			valueEmployee = new Employee();

		if (switchBlock == null)
			switchBlock = 1;

		employeeRegister=valueEmployee.getRegisterNo();
		allText = "";
	}
	

	@CommitAfter
	void onSelectedFromSave() {

		if (file != null) {
			valueEmployee.setPicturePath(file.getFileName());

			String[] arr = valueEmployee.getPicturePath().split("\\.");

			String imageName = valueEmployee.getUuid() + "."
					+ arr[arr.length - 1];

			valueEmployee.setPicturePath(imageName);

			File copied = new File(_context.getRealFile(
					IMAGE_PATH_EMP + imageName).getPath());

			file.write(copied);

		}
		
		if(valueEmployee.getPicturePath() != null){
			if (dao.getEmployeeByRegister(valueEmployee.getRegisterNo()).size()==0) {
				if (valueEmployee.getId() != null) {
					valueEmployee.setEmployeeStatus(EmployeeStatus.WORKING);
					dao.saveOrUpdateObject(dao.mergeObject(valueEmployee));
				} else {
					valueEmployee.setOrganization(getOrganization());
					dao.saveOrUpdateObject(valueEmployee);
				}
			}
			
			if(dao.getEmployeeByRegister(valueEmployee.getRegisterNo()).size()==1 && 
					dao.getEmployeeByRegister(valueEmployee.getRegisterNo()).get(0).equals(valueEmployee)){
				if (valueEmployee.getId() != null) {
					valueEmployee.setEmployeeStatus(EmployeeStatus.WORKING);
					dao.saveOrUpdateObject(dao.mergeObject(valueEmployee));
				} else {
					valueEmployee.setOrganization(getOrganization());
					dao.saveOrUpdateObject(valueEmployee);
				}
			}		
			else
				//loginState.setErrMessage("'" + valueEmployee.getRegisterNo() + "' " + messages.get("Registeredregister"));
				System.err.println("'" + valueEmployee.getRegisterNo() + "' " + messages.get("Registeredregister"));
				
		}else
			//loginState.setErrMessage("Зураг оруулна уу");
			System.err.println("Зураг оруулна уу");

	}

	Object onActionFromCancel() {
		return item;
	}

	Object onUploadException(FileUploadException ex) {
		message = "Upload exception: " + ex.getMessage();

		return this;
	}

	/* main 4 function */
	/*Object onActionFromGoPageEmployeeDetail() {
		pageEmployeeDetail.setEmployee(valueEmployee);
		pageEmployeeDetail.setMemberType("employee");
		switchBlock = 1;
		activeTab = "detail";
		activeSubTab = "personal";
		Layout.setActiveTab(activeTab);
		return pageEmployeeDetail;
	}
	
	Object onActionFromGoPageEmployeeDisplacement() {

		switchBlock = 2;
		activeTab = "displacement";
		activeSubTab = "occupation";
		Layout.setActiveTab(activeTab);
		pageEmployeeOccupation.setEmployee(valueEmployee);
		if(pageEmployeeDetail.isViewEmployee()==false)
			pageEmployeeOccupation.setViewEmployee(false);
		else
			pageEmployeeOccupation.setViewEmployee(true);
		return pageEmployeeOccupation;
	}*/

/*	Object onActionFromGoPageEmployeeSalary() {

		switchBlock = 3;
		activeTab = "salary";
		activeSubTab = "salaryNetwork";
		Layout.setActiveTab(activeTab);
		pageEmployeeSalary.setEmployee(valueEmployee);

		pageEmployeeSalary.setOnload(null);
		pageEmployeeSalary.setSwitchBlock(false);
		
		if(pageEmployeeDetail.isViewEmployee()==false)
			pageEmployeeSalary.setViewEmployee(false);
		else
			pageEmployeeSalary.setViewEmployee(true);
		
		return pageEmployeeSalary;
	}

	Object onActionFromGoPageEmployeeResult() {
		switchBlock = 4;
		activeTab = "result";
		activeSubTab = "nowResult";
		Layout.setActiveTab(activeTab);
		return pageemployeenowresult;
	}*/

	/* hurungu orlogiin meduuleg */
	/*Object onActionFromGoPageEmployeeDeclarationIncome() {
		switchBlock = 5;
		activeTab = "income";
		activeSubTab = "income";
		Layout.setActiveTab(activeTab);
		pageEmployeeDeclarationIncome.setEmployee(valueEmployee);

		pageEmployeeDeclarationIncome.setOnload(null);
		pageEmployeeDeclarationIncome.setSwitchBlock(false);
		return pageEmployeeDeclarationIncome;
	}*/

/*	Object onActionFromGoPageEmployeeFamily() {
		activeTab = "detail";
		activeSubTab = "family";
		Layout.setActiveTab(activeTab);
		pageEmployeeFamily.setEmployee(valueEmployee);
		if(pageEmployeeDetail.isViewEmployee()==false)
			pageEmployeeFamily.setViewEmployee(false);
		else
			pageEmployeeFamily.setViewEmployee(true);
		return pageEmployeeFamily;
	}*/

	/*Object onActionFromGoPageEmployeePersonal() {
		activeTab = "detail";
		activeSubTab = "personal";
		Layout.setActiveTab(activeTab);
		pageEmployeeDetail.setEmployee(valueEmployee);
		return pageEmployeeDetail;
	}

	Object onActionFromGoPageEmployeeEducation() {
		activeTab = "detail";
		activeSubTab = "education";
		Layout.setActiveTab(activeTab);
		pageEmployeeEducation.setEmployee(valueEmployee);
		if(pageEmployeeDetail.isViewEmployee()==false)
			pageEmployeeEducation.setViewEmployee(false);
		else
			pageEmployeeEducation.setViewEmployee(true);
		
		return pageEmployeeEducation;
	}

	Object onActionFromGoPageEmployeeTraining() {
		activeTab = "detail";
		activeSubTab = "training";
		Layout.setActiveTab(activeTab);
		pageEmployeeTraining.setEmployee(valueEmployee);
		if(pageEmployeeDetail.isViewEmployee()==false)
			pageEmployeeTraining.setViewEmployee(false);
		else
			pageEmployeeTraining.setViewEmployee(true);
		return pageEmployeeTraining;
	}

	Object onActionFromgoPageEmployeeDegreeGrade() {
		activeTab = "detail";
		activeSubTab = "degreeGrade";
		Layout.setActiveTab(activeTab);
		if(pageEmployeeDetail.isViewEmployee()==false)
			pageEmployeeDegreeGrade.setViewEmployee(false);
		else
			pageEmployeeDegreeGrade.setViewEmployee(true);
		
		pageEmployeeDegreeGrade.setEmployee(valueEmployee);
		
		return pageEmployeeDegreeGrade;
	}

	Object onActionFromGoPageEmployeeSkill() {
		activeTab = "detail";
		activeSubTab = "skill";
		Layout.setActiveTab(activeTab);
		pageEmployeeSkill.setEmployee(valueEmployee);
		if(pageEmployeeDetail.isViewEmployee()==false)
			pageEmployeeSkill.setViewEmployee(false);
		else
			pageEmployeeSkill.setViewEmployee(true);
		return pageEmployeeSkill;
	}

	Object onActionFromGoPageEmployeeLanguage() {
		activeTab = "detail";
		activeSubTab = "language";
		Layout.setActiveTab(activeTab);
		pageEmployeeLanguage.setEmployee(valueEmployee);
		if(pageEmployeeDetail.isViewEmployee()==false)
			pageEmployeeLanguage.setViewEmployee(false);
		else
			pageEmployeeLanguage.setViewEmployee(true);
		return pageEmployeeLanguage;
	}

	Object onActionFromGoPageEmployeeExperience() {
		activeTab = "detail";
		activeSubTab = "experience";
		Layout.setActiveTab(activeTab);
		pageEmployeeExperience.setEmployee(valueEmployee);
		if(pageEmployeeDetail.isViewEmployee()==false)
			pageEmployeeExperience.setViewEmployee(false);
		else
			pageEmployeeExperience.setViewEmployee(true);
		return pageEmployeeExperience;
	}

	 nemegdel huudas 
	Object onActionFromGoPageEmployeeAddition() {
		activeTab = "detail";
		activeSubTab = "addition";
		Layout.setActiveTab(activeTab);
		pageEmployeeAddition.setEmployee(valueEmployee);
		if(pageEmployeeDetail.isViewEmployee()==false)
			pageEmployeeAddition.setViewEmployee(false);
		else
			pageEmployeeAddition.setViewEmployee(true);
		return pageEmployeeAddition;
	}

	 shagnal huudas 
	Object onActionFromGoPageEmployeeHonour() {
		activeTab = "detail";
		activeSubTab = "honour";
		Layout.setActiveTab(activeTab);
		pageEmployeeHonour.setEmployee(valueEmployee);
		if(pageEmployeeDetail.isViewEmployee()==false)
			pageEmployeeHonour.setViewEmployee(false);
		else
			pageEmployeeHonour.setViewEmployee(true);
		return pageEmployeeHonour;
	}

	 bureldehuun hudulguun 

	Object onActionFromGoPageEmployeeOccupation() {
		activeTab = "displacement";
		activeSubTab = "occupation";
		Layout.setActiveTab(activeTab);
		pageEmployeeOccupation.setEmployee(valueEmployee);
		if(pageEmployeeDetail.isViewEmployee()==false)
			pageEmployeeOccupation.setViewEmployee(false);
		else
			pageEmployeeOccupation.setViewEmployee(true);
		return pageEmployeeOccupation;
	}

	Object onActionFromGoPageEmployeeMovement() {
		activeTab = "displacement";
		activeSubTab = "movement";
		Layout.setActiveTab(activeTab);
		pageEmployeeMovement.setEmployee(valueEmployee);
		System.err.println("VE::"+pageEmployeeDetail.isViewEmployee());
		if(pageEmployeeDetail.isViewEmployee()==false)
			pageEmployeeMovement.setViewEmployee(false);
		else
			pageEmployeeMovement.setViewEmployee(true);
		return pageEmployeeMovement;
	}*/

	/*Object onActionFromGoPageEmployeeJobHistory() {
		activeTab = "displacement";
		activeSubTab = "jobHistory";
		Layout.setActiveTab(activeTab);
		pageEmployeeJobHistory.setEmployee(valueEmployee);
		return pageEmployeeJobHistory;
	}*/

	/* цалин */
	/*
	 * Object onActionFromGoPageEmployeeSalaryNetwork(){
	 * pageEmployeeSalary.setEmployee(valueEmployee);
	 * pageEmployeeSalary.setSwitchBlock(false); return pageEmployeeSalary; }
	 * 
	 * Object onActionFromgoPageEmployeeOtherSalary(){
	 * 
	 * return pageEmployeeOtherSalary; }
	 */

	/* ur dungiin geree */

	/*Object onActionFromGoPageEmployeeNowResult() {
		activeTab = "result";
		activeSubTab = "nowResult";
		Layout.setActiveTab(activeTab);
		if(pageEmployeeDetail.isViewEmployee()==false)
			pageEmployeeNowResult.setViewEmployee(false);
		else
			pageEmployeeNowResult.setViewEmployee(true);
		return pageEmployeeNowResult;
	}*/

	/*Object onActionFromGoPageEmployeePreResult() {
		activeTab = "result";
		activeSubTab = "preResult";
		Layout.setActiveTab(activeTab);
		pageEmployeePreResult.setEmployee(valueEmployee);
		return pageEmployeePreResult;
	}*/

	public Asset getImageUrl() {

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

	public String getStatus() {
		return securityService.isAuthenticated() ? "Authenticated"
				: "Not Authenticated";
	}

	public String getUsername() {
		return loginState.getLogin();
	}

	private Link getLogout() {
		HttpSession session = requestGlobals.getHTTPServletRequest()
				.getSession(false);

		if (session != null) {
			session.invalidate();
		}

		return resources.createPageLink("Login", false);
	}

	public Employee getValueEmployee() {
		return valueEmployee;
	}

	public void setValueEmployee(Employee valueEmployee) {
		this.valueEmployee = valueEmployee;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public boolean isViewEmployee() {
		if (valueEmployee.getId() != null)
			return true;

		return true;
	}

	public String getTotalWorkedYear() {

		if (valueEmployee == null || valueEmployee.getId() == null)
			return "";
		Long x = 0l, temp = 0l, monthX = 0l, dayX = 0l;
		if (valueEmployee.getId() != null){
			x = dao.getTotalWorkedYear(valueEmployee.getId());
			temp=x;
		}			
		x = x / 365;
		dayX = temp - x * 365;
		monthX = dayX / 30;
		dayX = dayX - monthX * 30;
		return x + " жил " + monthX + " сар " + dayX + " хоног";
	}

	public String getStateOrganizationWorkedYear() {
		if (valueEmployee == null || valueEmployee.getId() == null)
			return "";

		Long x = 0l, x1 = 0l, monthX = 0l, dayX = 0l;
		if (valueEmployee.getId() != null)
			x = dao.getStateOrganizationWorkedYear(valueEmployee,
					OrganizationTypeExperience.ULSIIN)
					+ dao.getCurrentOrganizationWorkedYear(valueEmployee,
							OrganizationTypeExperience.SHUUH);
		x1 = x / 365;
		if (valueEmployee.getId() != null)
			dayX = x - x1 * 365;
		monthX = dayX / 30;
		dayX = dayX - monthX * 30;
		return x1 + " жил " + monthX + " сар " + dayX + " хоног";
	}

	public String getCurrentOrganizationWorkedYear() {
		if (valueEmployee == null || valueEmployee.getId() == null)
			return "";

		Long x = 0l, monthX = 0l, dayX = 0l;
		if (valueEmployee.getId() != null)
			x = dao.getCurrentOrganizationWorkedYear(valueEmployee,
					OrganizationTypeExperience.SHUUH);
		x = x / 365;
		if (valueEmployee.getId() != null)
			dayX = dao.getCurrentOrganizationWorkedYear(valueEmployee,
					OrganizationTypeExperience.SHUUH) - x * 365;
		monthX = dayX / 30;
		dayX = dayX - monthX * 30;
		return x + " жил " + monthX + " сар " + dayX + " хоног";
	}

	public String getAge() {
		if (valueEmployee == null || valueEmployee.getId() == null)
			return "";
		Long empYear = 0l, empMonth = 0l, empDay = 0l;
		if (valueEmployee.getId() != null)
			empYear = dao.getEmployeeAge(valueEmployee);
		empYear = empYear / 365;
		empDay = dao.getEmployeeAge(valueEmployee) - empYear * 365;
		empMonth = empDay / 30;
		empDay = empDay - empMonth * 30;
		return empYear + " нас " + empMonth + " сар " + empDay + " хоног ";
	}

	public String getIsMajorWorkedYear() {
		if (valueEmployee == null || valueEmployee.getId() == null)
			return "";
		Long x = 0l, monthX = 0l, dayX = 0l;
		if (valueEmployee.getId() != null)
			x = dao.getMajorWorkedYear(valueEmployee, true);
		x = x / 365;
		if (valueEmployee.getId() != null)
			dayX = dao.getMajorWorkedYear(valueEmployee, true) - x * 365;
		monthX = dayX / 30;
		dayX = dayX - monthX * 30;
		return x + " жил " + monthX + " сар " + dayX + " хоног";
	}
	
	/* Dec 07, 2012 Jargalsuren.S Start */
	
	/** Төрийн байгууллагад цэргийн цолтой ажилласан жил **/
	public String getMilitaryStateWorkedYear() {
		if (valueEmployee == null || valueEmployee.getId() == null)
			return "";
		Long x = 0l, tempX = 0l, monthX = 0l, dayX = 0l;
		if (valueEmployee.getId() != null){
			x = dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(), OrganizationTypeExperience.ULSIIN, MilitaryOrSimple.workingMilitary)
		    + dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(), OrganizationTypeExperience.SHUUH, MilitaryOrSimple.workingMilitary);
			tempX = x;
		}			
		
		x = x / 365;
		if (valueEmployee.getId() != null)
			dayX = tempX - x * 365;
		monthX = dayX / 30;
		dayX = dayX - monthX * 30;
		return x + " жил " + monthX + " сар " + dayX + " хоног";
	}
	
	/** Төрийн байгууллагад цэргийн цолгүй, энгийнээр ажилласан жил **/
	public String getSimpleStateWorkedYear() {
		if (valueEmployee == null || valueEmployee.getId() == null)
			return "";
		Long x = 0l, tempX = 0l, monthX = 0l, dayX = 0l;
		if (valueEmployee.getId() != null){
			x = dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(), OrganizationTypeExperience.ULSIIN, MilitaryOrSimple.workingSimple)
			   + dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(), OrganizationTypeExperience.SHUUH, MilitaryOrSimple.workingSimple)
			   + dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(), OrganizationTypeExperience.HUVIIN, MilitaryOrSimple.workingSimple);
			tempX = x;
		}
			
		x = x / 365;
		if (valueEmployee.getId() != null){
			dayX = tempX - x * 365;
		}			
		monthX = dayX / 30;
		dayX = dayX - monthX * 30;
		return x + " жил " + monthX + " сар " + dayX + " хоног";
	}
	
	/** ШШГЕГ-д цэргийн цолтой ажилласан жил **/
	public String getMilitaryCourtWorkedYear() {
		if (valueEmployee == null || valueEmployee.getId() == null)
			return "";
		Long x = 0l,tempX = 0l, monthX = 0l, dayX = 0l;
		if (valueEmployee.getId() != null){
			x = dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(), OrganizationTypeExperience.SHUUH, MilitaryOrSimple.workingMilitary);
			tempX = x;
		}
		
		x = x / 365;
		if (valueEmployee.getId() != null)
			dayX = tempX - x * 365;
		monthX = dayX / 30;
		dayX = dayX - monthX * 30;
		return x + " жил " + monthX + " сар " + dayX + " хоног";
	}
	
	/** ШШГЕГ-д цэргийн цолгүй ажилласан жил **/
	public String getSimpleCourtWorkedYear() {
		if (valueEmployee == null || valueEmployee.getId() == null)
			return "";
		Long x = 0l, tempX = 0l, monthX = 0l, dayX = 0l;
		if (valueEmployee.getId() != null){
			x = dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(), OrganizationTypeExperience.SHUUH, MilitaryOrSimple.workingSimple);
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
		if (valueEmployee.getId() != null)
			xs = dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(), OrganizationTypeExperience.SHUUH, MilitaryOrSimple.workingSimple);

		if (valueEmployee.getId() != null)
			xm = dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(), OrganizationTypeExperience.SHUUH, MilitaryOrSimple.workingMilitary);
		dayX = xs + xm;
		
		yearX = dayX/365;
		dayX = dayX - yearX*365;
		monthX = dayX / 30;
		dayX = dayX - monthX * 30;
		return yearX + " жил " + monthX + " сар " + dayX + " хоног";
	}
	/* Dec 07, 2012 Jargalsuren.S End */
	
	@Inject
	private Block case1, case2, case3, case4;

	public Object getCase() {
		switch (switchBlock) {
		case 1:
			return case1;
		case 2:
			return case2;
		case 3:
			return case3;
		case 4:
			return case4;
		default:
			return null;
		}
	}

	public Asset getStyles() {
		return styles;
	}

	public Boolean getIsView() {
		return isView;
	}

	public void setIsView(Boolean isView) {
		this.isView = isView;
	}

	@Property
	@Persist
	private List<Object> list;

	public String getActiveDetail() {
		if (activeTab.equals("detail"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}

	public String getActiveFamily() {
		if (activeSubTab.equals("family"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}

	public String getActiveEducation() {
		if (activeSubTab.equals("education"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}

	public String getActiveTraining() {
		if (activeSubTab.equals("training"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}

	public String getActivePersonal() {
		if (activeSubTab.equals("personal"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}

	public String getActiveDegreeGrade() {
		if (activeSubTab.equals("degreeGrade"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}

	public String getActiveDegreeSkill() {
		if (activeSubTab.equals("skill"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}

	public String getActiveLanguage() {
		if (activeSubTab.equals("language"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}

	public String getActiveExperience() {
		if (activeSubTab.equals("experience"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}

	public String getActiveAddition() {
		if (activeSubTab.equals("addition"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}

	public String getActiveIncome() {
		if (activeTab.equals("income"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}

	public String getActiveHonour() {
		if (activeSubTab.equals("honour"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}

	public String getActiveNowResult() {
		if (activeSubTab.equals("nowResult"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}

	public String getActivePreResult() {
		if (activeSubTab.equals("preResult"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}

	public String getActiveMovement() {
		if (activeSubTab.equals("movement"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}

	public String getActiveOccupation() {
		if (activeSubTab.equals("occupation"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}

	public String getActiveJobHistory() {
		if (activeSubTab.equals("jobHistory"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}

	public String getActiveResult() {
		if (activeTab.equals("result"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}

	public String getActiveSalary() {
		if (activeTab.equals("salary"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}

	public String getActiveDisplacement() {
		if (activeTab.equals("displacement"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}

	public boolean isNewEmployee() {
		if (valueEmployee.getId() != null)
			return true;

		else
			return false;
	}

	public static String getActiveTab() {
		return activeTab;
	}

	public static void setActiveTab(String activeTab) {
		LayoutEmployee.activeTab = activeTab;
	}

	public static String getActiveSubTab() {
		return activeSubTab;
	}

	public static void setActiveSubTab(String activeSubTab) {
		LayoutEmployee.activeSubTab = activeSubTab;
	}

	public boolean isHasForm() {
		return hasForm;
	}

	public void setHasForm(boolean hasForm) {
		this.hasForm = hasForm;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public static Integer getSwitchBlock() {
		return switchBlock;
	}

	public static void setSwitchBlock(Integer switchBlock) {
		LayoutEmployee.switchBlock = switchBlock;
	}

	Object onActionFromGoAnket() throws IOException, DocumentException {

		if (valueEmployee != null && valueEmployee.getId() != null)
			return anket.onSubmit();
		else
			//loginState.setErrMessage("Шинэ гишүүний анкет бүртгэнэ үү!");
			System.err.println("Шинэ гишүүний анкет бүртгэнэ үү!");
		return null;
	}
	
	/*Object onActionFromGoEmployeeCard() throws IOException, DocumentException {

		if (valueEmployee != null && valueEmployee.getId() != null)
			return employeeCard.onSubmit();
		else
			loginState.setErrMessage("Шинэ гишүүний анкет бүртгэнэ үү!");
		return null;
	}*/

	public void getContent()  throws DocumentException, IOException {
		
		Date d = new Date();
		Integer relSize = 0;
		String family = "";
		String edu = "";
		String exp = "";
		String occ = "";
		String degs = "";
		String award = "";
		Relatives husband = null;
		EmployeeMilitary valueEmployeeMilitary = null;
		
		
		List<Relatives> rels = null;
		List<Educations> edus= null;
		List<Experience> exps = null;
		List<Honour> honour = null;
		
		BaseFont arno = BaseFont.createFont(fontStyleItalic, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font f = new Font(arno, 8);
		
		rels = dao.getRelatives(valueEmployee.getId(), RelativeFamily.ISFAMILY);
		
		edus = dao.getListEducation(valueEmployee);
		exps = dao.getExperience(valueEmployee);
		valueEmployeeMilitary = dao.getEmployeeMilitary(valueEmployee.getId());
		honour = dao.getListHonour(valueEmployee);
		
		relSize = rels.size() + 1;
		
		if(!rels.isEmpty()){
			for(int i=0; i < rels.size() ; i++)
			{
				family += rels.get(i).getRelation().getName() + ", ";
				
				if(rels.get(i).getRelation().getName().equals("Эхнэр"))
					husband = rels.get(i);
				
				if(rels.get(i).getRelation().getName().equals("Нөхөр"))
					husband = rels.get(i);
			}
		}

		if(family!=null && family.length() > 2)
			family = family.substring(0, family.length() - 2);
		
		if(edus!=null){
			for(int i=0; i < edus.size(); i++){
				/*edu += format.format(edus.get(i).getIngoingDate()) + "-"
						+ format.format(edus.get(i).getGraduatedDate()) + " онд "
						+ edus.get(i).getSchool() + ", ";*/
				edu += edus.get(i).getIngoingDate() + "-"				
				+ edus.get(i).getGraduatedDate() + " онд "
				+ edus.get(i).getCountry().getCountryName() + "  улсын  "
				+ edus.get(i).getCity() + "  хотын/аймгийн  "
				//+ edus.get(i).getDistrictSum().getDistrictSumName() + "  сумын/дүүргийн  "
				+ edus.get(i).getSchool() + ", ";
				
				if(edus.get(i).getSchoolType() == SchoolType.UNIVERSITY)
					occ += edus.get(i).getOccupation().getName() + ", ";
			}
		}
		
		if(occ!=null && occ.length() > 2)
			occ = occ.substring(0, occ.length() - 2);		
		
		if (allText.equals("")) {
			String eol = System.getProperty("line.separator");  
			allText = "\t\t\t\t\t" + messages.get("wordDescription") + eol + eol;
			allText += eol;
			
			allText += (valueEmployeeMilitary != null ? valueEmployeeMilitary.getMilitary().getMilitaryName() + "  ": "") +  valueEmployee.getLastname() + "-ийн " + valueEmployee.getFirstName()+ "\n";
			//allText += valueEmployee.getLastname() + "-ийн " + valueEmployee.getFirstName()+ "\n";
			allText += eol;
			allText += "\t" + valueEmployee.getFamilyName() + " овогт " + valueEmployee.getLastname() + "-ийн " + valueEmployee.getFirstName() + " нь "
					 + (valueEmployee.getBirthDate()!=null ? "": format.format(valueEmployee.getBirthDate())) 
					 + " онд " + (valueEmployee.getBirthCityProvince()!=null ? "": valueEmployee.getBirthCityProvince().getCityName())
					 + "  аймгийн/хотын " + (valueEmployee.getBirthDistrictSum()!=null ? "": valueEmployee.getBirthDistrictSum().getDistrictSumName()) + " суманд/дүүрэгт төрсөн, " 
					 + getAge().substring(0, 2) + " настай, " 
				     + "  ам бүл - " + relSize + ", " + family + "-ийн/ын хамт амьдардаг.";
			
					
			if(valueEmployee.getEducations() != null){
				allText = allText + "   " + edu + "-г тус тус төгссөн. ";
			}
			
			if(edus.size() > 0){
				if(!edus.get(edus.size() - 1).getOccupation().getName().equals("Үгүй") ||
						!edus.get(edus.size() - 1).getOccupation().getName().contains("Мэргэжилгүй") ||
						edus.get(edus.size() - 1).getOccupation().getName() != null){
					allText += edus.get(edus.size() - 1).getOccupation().getName() + "  мэргэжилтэй. ";
				}
			}
			
			if(valueEmployeeMilitary != null){
				allText = allText + "  " + valueEmployeeMilitary.getMilitary().getMilitaryName() + " цолтой. ";
			}
			
			Long courtX = dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(), OrganizationTypeExperience.SHUUH, MilitaryOrSimple.workingSimple)
			              + dao.getMilitaryOrSimpleWorkedYear(valueEmployee.getId(), OrganizationTypeExperience.SHUUH, MilitaryOrSimple.workingMilitary);
			Long totalX = dao.getTotalWorkedYear(valueEmployee.getId());
			
			allText += "  Улсад  " + totalX/365 +"  жил, " + "  тус байгууллагад  " + courtX/365 +"  жил ажилласан.  ";
			
			if(exps!=null){
				allText +=  eol + eol + eol + "\t" + "Ажил эрхлэлтийн байдал: " + eol;
				for(int i=0; i<exps.size(); i++){
					if(exps.get(i).getStartDate() != null)
						exp += "\t" + format.format(exps.get(i).getStartDate());
					
					if(exps.get(i).getEndDate() != null)
						exp += "-" + format.format(exps.get(i).getEndDate()) + " онд ";
					else{
						exp += " оны " + formatMonth.format(exps.get(i).getStartDate()) + " сараас ";
					}
					
					exp += exps.get(i).getOrganizationname() + "-д " 
						+ exps.get(i).getAppointment() + eol+ eol;				
				}				
				allText += exp;
			}
			
			if(honour != null){				
				award += eol + eol + eol + "\t" + "Шагнагдсан байдал: " + eol+ eol;
				for(int i=0; i<honour.size(); i++){
					if(honour.get(i).getDateOfAwarded() != null)
						award += "\t" + format.format(honour.get(i).getDateOfAwarded()) + "  онд  ";
					
					if(honour.get(i).getAwardType() == AwardType.OTHER_ORGANIZATIONPRIZE)
						award += honour.get(i).getOtherPrize() + eol+ eol;
					else
						award += honour.get(i).getAward().getName() + eol+ eol;
				}				
				allText += award;
			}
			
			allText += eol + eol+ "\t" + "Захиргааны удирдлагын газрын Хүний нөөц, сургалтын хэлтсийн санал: " + eol + eol;
			
			
			allText += eol + eol +  "\t" + "Танилцуулга бичсэн:" + eol + eol;
			allText += "\t" + "Хүний нөөц, сургалтын хэлтсийн дарга," + eol + "\t" + "дэд хурандаа:                                                      С.Зул-Очир";
	}
}

	/* export word */
	@CommitAfter
	void onSelectedFromExport() throws FileNotFoundException, IOException, DocumentException {

		getContent();

		HWPFDocument doc = new HWPFDocument(file("Template.doc"));

		WordAPI.setCellValue(doc, 0, 0, 0, allText);

		OutputStream out = response.getOutputStream("application/vnd.ms-word");
		response.setHeader("Content-Disposition", "attachment; filename=\"DESCRIPTION.doc\"");

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
}
