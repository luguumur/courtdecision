package mn.mcs.electronics.court.components;

import javax.servlet.http.HttpSession;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.RetireAge;
import mn.mcs.electronics.court.pages.Home;
import mn.mcs.electronics.court.pages.configuration.RoleConfig;
import mn.mcs.electronics.court.pages.configuration.UserConfig;
import mn.mcs.electronics.court.pages.employee.PageEmployeeList;
import mn.mcs.electronics.court.pages.report.ReportHome;
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
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.RequestGlobals;
import org.tynamo.security.services.SecurityService;

@Import(stylesheet = "classpath:org/tynamo/themes/tapestryskin/theme.css")
public class LayoutAdmin
{

	@SessionState
	private LoginState loginState;
	
	@Inject
	private CoreDAO dao;
	
	@Inject
	private Environment environment;

	@Inject
	private Context context;

	@Property
	@Parameter
	private String title;

	@Property
	@Parameter(required = false, defaultPrefix = BindingConstants.MESSAGE)
	private String heading;

	@Property
	@Parameter(required = false, defaultPrefix = BindingConstants.MESSAGE)
	private String menu;

	@Property
	@Parameter(required = false, defaultPrefix = BindingConstants.LITERAL)
	private String bodyId;
	
	@Inject
	@Path("context:/css/index.css")
	private Asset styles;
	
	@Inject
	@Path("context:/css/jquery-ui-1.8.21.custom.css")
	private Asset styles2;
	
	@Inject
	private SecurityService securityService;
	
	@Persist
	private RetireAge ra;

	@InjectPage
	private Home home;
	
	@InjectPage
	private ReportHome report;
	
	@InjectPage
	private UserConfig userConfig;
	
	@InjectPage
	private PageEmployeeList employeeList;
	
	@InjectPage
	private RoleConfig roleConfig;
	
	@Property(write = false)
	@Parameter(value = "block:subMenuBlock", defaultPrefix = BindingConstants.LITERAL)
	private Block subMenuBlock;

	@Property(write = false)
	@Parameter(value = "block:navBlock", defaultPrefix = BindingConstants.LITERAL)
	private Block navBlock;
	
	private static final String TAB_DEFAULT = "plain";

	private static final String TAB_SELECTED = "admin selected";
	
	@Inject
	private RequestGlobals requestGlobals;
	
	@Inject
	private ComponentResources resources;
	
	
	private static String activeTab;

	@Persist
	private EmployeeSearchBean bean;
	
	void beginRender(){
		if(activeTab==null)
			activeTab = "admin";
		
		if(dao.getListRetireAge() != null){
			ra = (RetireAge) dao.getListRetireAge().get(0);
		}
		
/*		LoginState.breadCrumb = new ArrayList<Object>();
		LoginState.breadCrumbCell = new ArrayList<Object>(2);
		
		LoginState.breadCrumbCell.add("Системийн тохиргоо");
		LoginState.breadCrumbCell.add("configuration/userConfig");
		
		LoginState.breadCrumb.add(LoginState.breadCrumbCell);*/
	}
	
	/*Link onActionFromLogout() {
		return this.getLogout();
	}*/
	
	/* Main menu */
	/*Object onActionFromHome(){
		activeTab = "home";
		
		return home;
	}
	
	Object onActionFromReport(){
		activeTab = "report";

		Layout.setActiveTab(activeTab);
		return report;
	}
	
	Object onActionFromUserConfig(){
		activeTab = "admin";
		Layout.setActiveTab(activeTab);
		return userConfig;
	}
	
	Object onActionFromSearch(){
		activeTab = "search";
		Layout.setActiveTab(activeTab);
		return employeeList;
	}*/
	
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
	
//	void onActionFromGoHonourTime(){
//		
//	}
	
	Object onActionFromGoRoleConfig(){
		roleConfig.setSwitchBlock(false);
		return roleConfig;
	}
	
	/* getter, setter */
	public JSONObject getParam() {
		JSONObject defaults = new JSONObject();
        defaults.put("modal", false);
        defaults.put("resizable", false);
        defaults.put("draggable", true);
        defaults.put("autoOpen", false);
        defaults.put("width", 450);
        return defaults;
	}
	
//	public String getCountRetire(){
//		
//		return dao.getListEmployeeSearch(bean, ra).size()+"";
//	}
	
	public Asset getStyles() {
		return styles;
	}
	
	public Asset getStyles2() {
		return styles2;
	}
	

	public String getUsername(){
		if(loginState.getEmployee()==null||loginState.getEmployee().getFullNameFirstChar()==null)
			return getUsername();
		
		return loginState.getEmployee().getFullNameFirstChar();
	}
	
	private Link getLogout() {
		HttpSession session = requestGlobals.getHTTPServletRequest()
				.getSession(false);

		if (session != null) {
			session.invalidate();
		}
		return resources.createPageLink("Login", false);
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

	public static String getActiveTab() {
		return activeTab;
	}

	public static void setActiveTab(String activeTab) {
		LayoutAdmin.activeTab = activeTab;
	}
}
