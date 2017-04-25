package mn.mcs.electronics.court.components;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpSession;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.User;
import mn.mcs.electronics.court.entities.configuration.ProjectMenuConfig;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.entities.other.LoginHistory;
import mn.mcs.electronics.court.pages.DemeritWarning;
import mn.mcs.electronics.court.pages.Home;
import mn.mcs.electronics.court.pages.Warning;
import mn.mcs.electronics.court.pages.configuration.UserConfig;
import mn.mcs.electronics.court.pages.employee.PageEmployeeList;
import mn.mcs.electronics.court.pages.organization.PageWorkedYearEmployees;
import mn.mcs.electronics.court.pages.report.ReportHome;
import mn.mcs.electronics.court.util.Constants;
import mn.mcs.electronics.court.util.UserUtil;
import mn.mcs.electronics.court.util.beans.EmployeeSearchBean;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.RequestGlobals;
import org.tynamo.security.services.SecurityService;

@Import(stylesheet = "classpath:org/tynamo/themes/tapestryskin/theme.css")
public class LayoutIndex
{

	@SessionState
	private LoginState loginState;
	
	@Inject
	private Environment environment;

	@Inject
	private Context context;

	@Inject
	private Messages messages;
	
	@Inject
	private CoreDAO dao;
	
	@InjectPage
	private Home home;
	
	@InjectPage
	private ReportHome report;
	
	@InjectPage
	private UserConfig userConfig;
	
	@InjectPage
	private PageEmployeeList employeeList;
	
	@InjectPage
	private PageWorkedYearEmployees pageWorkedYearEmployees;
	
	@InjectPage
	private Warning warningPage;
	
	@InjectPage
	private DemeritWarning warningDemeritPage;
	
	@Inject
	@Property
	@Path("context:/images/favicon.ico")
	private Asset favicon;
	
	@Inject
	@Path("context:/images/demeritWarning.png")
	private Asset demeritWarning;
	
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
	
	@Property
	@Persist
	private List<ProjectMenuConfig> listProjectMenuConfigSecond;
	
	@Property
	@Persist
	private List<ProjectMenuConfig> listProjectMenuConfigThird;
	
	@Property
	@Persist
	private List<ProjectMenuConfig> listProjectMenuConfigFourth;
	
	@Inject
	private AssetSource imageUrl;
	
	@Inject
	private AssetSource backUrl;

	@Inject
	private SecurityService securityService;
	
	@Inject
	private RequestGlobals requestGlobals;
	
	@Inject
	private ComponentResources resources;
	
	@Inject
	@Path("context:/css/index.css")
	private Asset styles;
	
	private static final String TAB_DEFAULT = "plain";

	private static final String TAB_SELECTED = "home selected";
	
	private static String activeTab;

	@Persist
	private EmployeeSearchBean bean;
	
	@Persist("flash")
	private boolean checkSucMessage;
	
	@Persist("flash")
	private String errMessage;
	
	/* password change*/
	@Property
	@Persist
	private String currentPassword;
	
	@Property
	@Persist
	private String newPassword;
	
	@Property
	@Persist
	private String confirmPassword;
	
	@Persist
	private Boolean hasForm;
	
	@Property
	@Persist
	private List<LoginHistory> listLoginHistory;
	
	@Persist
	@Property
	private LoginHistory valueLoginHistory;
	
	@Persist
	private HashSet<Organization> orgs;
	
	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_TIME_FORMAT);
	
	private static final String GRID_ROW_CSS = "myGrid";
	
	public static final String IMAGE_PATH_EMP = "/images/profile/";
	
	void beginRender(){
		
		orgs = new HashSet<Organization>(loginState.getEmployee().getMapEmpOrg());
		
		if (orgs.isEmpty())
			orgs.add(loginState.getOrganization());
		
		listLoginHistory=dao.getListLoginHistory(loginState.getEmployee());
		listProjectMenuConfigSecond = dao.getProjectMenu(2);
		listProjectMenuConfigThird = dao.getProjectMenu(3);
		listProjectMenuConfigFourth = dao.getProjectMenu(4);
		
		if(activeTab==null)
			activeTab = "home";
	}

	/* Main menu */
	Object onActionFromHome(){
		activeTab = "home";
		
		return home;
	}
	
	Object onActionFromSecond() {
		activeTab = "report";
		
		String link = listProjectMenuConfigSecond.get(0).getMenuLink();
		Object li = report;
		
		if(link.equals("report"))
			li = report;
		if(link.equals("userConfig"))
			li = userConfig;
		if(link.equals("employeeList"))
			li = employeeList;
		
		return li;
	}
	
	Object onActionFromThird() {
		activeTab = "admin";
		
		String link = listProjectMenuConfigThird.get(0).getMenuLink();
		Object li = userConfig;
		
		if(link.equals("report"))
			li = report;
		if(link.equals("userConfig"))
			li = userConfig;
		if(link.equals("employeeList"))
			li = employeeList;
		
		return li;
	}

	Object onActionFromFourth() {
		activeTab = "search";

		String link = listProjectMenuConfigFourth.get(0).getMenuLink();
		Object li = employeeList;
		
		if(link.equals("report"))
			li = report;
		if(link.equals("userConfig"))
			li = userConfig;
		if(link.equals("employeeList"))
			li = employeeList;
		
		return li;
	}
	
	/* Alarm */
	Object onActionFromGoRetireEmployee(){

		bean = new EmployeeSearchBean();
		bean.setRetiredDate(true);
		
		employeeList.setBean(bean);
		return employeeList;
	}
	
	Object onActionFromGoGeneralJudge(){
		bean = new EmployeeSearchBean();
		bean.setGeneralJudgeDate(true);
		
		employeeList.setBean(bean);
		return employeeList;
	}
	
	Object onActionFromGoWorkedYear(){
		pageWorkedYearEmployees.setTest(true);
		return pageWorkedYearEmployees;
	}
	
	Object onActionFromGoStateWorkedYear(){
		pageWorkedYearEmployees.setTest(false);
		return pageWorkedYearEmployees;
	}
	
	public Asset getDemeritWarning() {
		return demeritWarning;
	}
	
	@CommitAfter
	void onSelectedFromChangePass(){
		boolean encPass = false;
		User user = dao.getUser(loginState.getEmployee());
		
		if(currentPassword != null)
			encPass = UserUtil.setPassword(currentPassword,user.getPasswordSalt(),user.getEncodedPassword());
		else
			/*loginState
			.setErrMessage(messages.get("passwordNull"));*/
			System.err.println(messages.get("passwordNull"));
		
		if(!encPass)
			setErrMessage(messages.get("currentPasswordIsWrong"));
		else{	
		
			if(!newPassword.equals(confirmPassword))
				setErrMessage(messages.get("confrimPasswordIsWrong"));
			else{
				if(newPassword != null && confirmPassword != null){
					user.setPassword(confirmPassword);
					dao.saveOrUpdateObject(user);
				}
				else
//					loginState
//					.setErrMessage(messages.get("passwordNull"));
					System.err.println(messages.get("passwordNull"));
			}
		}
	}
	
	public JSONObject getParam() {
		JSONObject defaults = new JSONObject();
	        defaults.put("modal", true);
	        defaults.put("resizable", false);
	        defaults.put("draggable", true);
	        defaults.put("autoOpen", false);
	        defaults.put("width", 450);
	        
        return defaults;
	}
	
	
	public String getUsername(){
		if(loginState.getEmployee()==null||loginState.getEmployee().getFullNameFirstChar()==null)
			return loginState.getLogin();
		return loginState.getEmployee().getFullNameFirstChar();
	}
	
	Link onActionFromLogout() {
		return this.getLogout();
	}

	private Link getLogout() {
		HttpSession session = requestGlobals.getHTTPServletRequest()
				.getSession(false);

		if (session != null) {
			session.invalidate();
		}

		return resources.createPageLink("Login", false);
	}
	
	public Asset getStyles() {
		return styles;
	}
	
	public String getActiveReport(){
		if(activeTab.equals("report"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}
	
	public String getActiveHome(){
		if(activeTab.equals("home"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}
	
	public String getActiveAdmin(){
		if(activeTab.equals("admin"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}
	
	public String getActiveSearch(){
		if(activeTab.equals("search"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}
	
	public String getActiveSettings(){
		if(activeTab.equals("settings"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}

	public static String getActiveTab() {
		return activeTab;
	}

	public static void setActiveTab(String activeTab) {
		LayoutIndex.activeTab = activeTab;
	}
	
	public boolean isCheckSucMessage() {
	//	return loginState.isCheckSucMessage();
		
		return checkSucMessage;
	}
	
	public String getGridRowCSS() {
		return GRID_ROW_CSS;
	}

	public void setCheckSucMessage(boolean checkSucMessage) {
		this.checkSucMessage = checkSucMessage;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	
	public String getAppointment(){
		return loginState.getAppointment();
	}
	
	public Boolean getHasForm() {
		return hasForm;
	}

	public void setHasForm(Boolean hasForm) {
		this.hasForm = hasForm;
	}

	public String getLastName(){
		if(loginState.getEmployee()==null||loginState.getEmployee().getLastname()==null)
			return "";
		
		return loginState.getEmployee().getLastname();
	}
	
	public String getFirstName(){
		if(loginState.getEmployee()==null||loginState.getEmployee().getFirstName()==null)
			return "";
		
		return loginState.getEmployee().getFirstName();
	}
	
	public String getRegisterNo(){
		if(loginState.getEmployee()==null||loginState.getEmployee().getRegisterNo()==null)
			return "";
		
		return loginState.getEmployee().getRegisterNo();
	}
	
	public String getAppointmentName(){
		if(loginState.getEmployee()==null||loginState.getEmployee().getAppointment()==null)
			return "";
		
		return loginState.getEmployee().getAppointment().getAppointmentName();
	}

	public String getEmployeeName(){
		if(valueLoginHistory.getEmployee()==null||valueLoginHistory.getEmployee().getFirstName()==null)
			return "";
		
		return valueLoginHistory.getEmployee().getFirstName();
	}
	
	public String getLoginDate(){
		if(valueLoginHistory.getLoginDate()==null||valueLoginHistory.getLoginDate()==null)
			return "";
		
		return format.format(valueLoginHistory.getLoginDate());
	}
	
	public boolean isMcseAdmin(){
		if(loginState.getEmployee() != null && 
				loginState.getEmployee().getMcseAdmin() != null && 
				loginState.getEmployee().getMcseAdmin())
			return true;
		return false;
	}
	
	public String getSecondMenuName(){
		return (listProjectMenuConfigSecond != null && !listProjectMenuConfigSecond.isEmpty()) ? 
				listProjectMenuConfigSecond.get(0).getMenuLabel() : "";
	}
	
	public String getSecondMenuPermission(){
		return (listProjectMenuConfigSecond != null && !listProjectMenuConfigSecond.isEmpty()) ? 
				listProjectMenuConfigSecond.get(0).getPermission().getPermissionName() : "nothing";
	}
	
	public String getThirdMenuName(){
		return (listProjectMenuConfigThird != null && !listProjectMenuConfigThird.isEmpty()) ? 
				listProjectMenuConfigThird.get(0).getMenuLabel() : "";
	}
	
	public String getThirdMenuPermission(){
		return (listProjectMenuConfigThird != null  && !listProjectMenuConfigThird.isEmpty()) ? 
				listProjectMenuConfigThird.get(0).getPermission().getPermissionName() : "nothing";
	}
	
	public String getFourthMenuName(){
		return (listProjectMenuConfigFourth != null && !listProjectMenuConfigFourth.isEmpty()) ? 
				listProjectMenuConfigFourth.get(0).getMenuLabel() : "";
	}
	
	public String getFourthMenuPermission(){
		return (listProjectMenuConfigFourth != null  && !listProjectMenuConfigFourth.isEmpty()) ? 
				listProjectMenuConfigFourth.get(0).getPermission().getPermissionName() : "nothing";
	}
	
}
