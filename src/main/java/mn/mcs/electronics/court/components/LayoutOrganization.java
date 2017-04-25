package mn.mcs.electronics.court.components;

import javax.servlet.http.HttpSession;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.pages.employee.PageEmployee;
import mn.mcs.electronics.court.pages.organization.PageOrganization;
import mn.mcs.electronics.court.pages.organization.PageOrganizationInfo;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.RequestGlobals;
import org.tynamo.security.services.SecurityService;

@Import(stylesheet = "classpath:org/tynamo/themes/tapestryskin/theme.css")
public class LayoutOrganization
{

	@SessionState
	private LoginState loginState;
	
	@Inject
	private RequestGlobals requestGlobals;
	
	@InjectComponent
	private Layout layout;
	
	@InjectPage
	private PageOrganizationInfo pageOrganizationInfo;
	
	@InjectPage
	private PageOrganization pageOrganization;
	
	@Inject
	private ComponentResources resources;
	
	@Inject
	private Environment environment;

	@Inject
	private Context context;

	@Inject
	private SecurityService securityService;

	@Inject
	private CoreDAO dao;
	
	private static final String TAB_DEFAULT = "plain";

	private static final String TAB_SELECTED = "selected";
	
	private static String activeTab;
	
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
	private Organization organization;
	
	void beginRender(){
		organization = pageOrganization.getOrganizationInfo();
		
		if(activeTab==null)
			activeTab = "statistic";
		
/*		if(organization.getRegister() != null)
		{
			LoginState.breadCrumbCell = new ArrayList<Object>(2);
			LoginState.breadCrumbCell.add(organization.getName());
			LoginState.breadCrumbCell.add("organization/pageOrganization");
			
			if(!LoginState.breadCrumb.contains(LoginState.breadCrumbCell))
				LoginState.breadCrumb.add(LoginState.breadCrumbCell);
			
			for(int i = LoginState.breadCrumb.indexOf(LoginState.breadCrumbCell) + 1; i<LoginState.breadCrumb.size();i++){
				LoginState.breadCrumb.remove(i);
			}
		}*/
	}
	
	/* getter, setter */
	
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	
	public String getStatus() {
		return securityService.isAuthenticated() ? "Authenticated" : "Not Authenticated";
	}

	public String getUsername(){
		return loginState.getLogin();
	}
	
	public String getActiveStatistic(){
		if(activeTab.equals("statistic"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}
	
	public String getActiveInfo(){
		if(activeTab.equals("info"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}
	
	public String getActiveDepartment(){
		if(activeTab.equals("department"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}
	
	public String getActiveEmployee(){
		if(activeTab.equals("employee"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}
	
	public String getActiveProperty(){
		if(activeTab.equals("property"))
			return TAB_SELECTED;
		else
			return TAB_DEFAULT;
	}
	
	public static String getActiveTab() {
		return activeTab;
	}

	public static void setActiveTab(String activeTab) {
		LayoutOrganization.activeTab = activeTab;
	}
	
	private Link getLogout() {
		HttpSession session = requestGlobals.getHTTPServletRequest()
				.getSession(false);

		if (session != null) {
			session.invalidate();
		}

		return resources.createPageLink("Login", false);
	}

	/* Organization Profile */
	@InjectPage
	private PageEmployee pageEmployee;

	
	Object onActionFromGoToEmployeePage(){
		activeTab = "employee";
		LayoutOrganization.setActiveTab(activeTab);
		return pageEmployee;
	}
	
	
	Object onActionFromGoToPageOrganizationInfo(){
		activeTab = "info";
		LayoutOrganization.setActiveTab(activeTab);
		return pageOrganizationInfo;
	}
	
	Object onActionFromGoToPageOrganization(){
		activeTab = "statistic";
		LayoutOrganization.setActiveTab(activeTab);
		return pageOrganization;
	}
	
	/*Object onActionFromGoToPageOrganizationProperty(){
		activeTab = "property";
		LayoutOrganization.setActiveTab(activeTab);
		return pageOrganizationProperty;
	}*/
	
	public boolean isNewOrganization(){
		if(organization.getId()!=null)
			return true;
		
		else return false;
	}

}
