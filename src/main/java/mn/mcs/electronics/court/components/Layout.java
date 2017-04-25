package mn.mcs.electronics.court.components;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpSession;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.User;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.entities.other.LoginHistory;
import mn.mcs.electronics.court.pages.Home;
import mn.mcs.electronics.court.pages.Warning;
import mn.mcs.electronics.court.util.Constants;
import mn.mcs.electronics.court.util.UserUtil;

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

@Import(library = {
		"classpath:org/got5/tapestry5/jquery/ui_1_8_19/jquery.ui.datepicker.js",
		"context:css/jquery.ui.datepicker-mn.js" }, stylesheet = { "classpath:org/tynamo/themes/tapestryskin/theme.css" })
public class Layout {

	@SessionState
	private LoginState loginState;;

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

	@Persist
	private Employee valueEmployee;

	@Inject
	@Property
	@Path("context:/images/exclamation5.png")
	private Asset warningIcon;

	@Inject
	@Property
	@Path("context:/images/exclamation.png")
	private Asset excIcon;

	@Inject
	@Property
	@Path("context:/images/favicon.ico")
	private Asset favicon;

	@Inject
	@Property
	@Path("context:/documents/Screen_manual.pdf")
	private Asset userGuide;

	@Property
	@Parameter
	private String title;

	@Inject
	private AssetSource imageUrl;

	@Inject
	private AssetSource backUrl;

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

	@Inject
	private SecurityService securityService;

	@Inject
	private RequestGlobals requestGlobals;

	@Inject
	private ComponentResources resources;

	@Inject
	@Path("context:/css/index.css")
	private Asset styles;

	@InjectPage
	private Warning warningPage;

	/* password change */
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

	void beginRender() {
		listLoginHistory = dao.getListLoginHistory(loginState.getEmployee());
		orgs = new HashSet<Organization>(loginState.getEmployee()
				.getMapEmpOrg());

		if (orgs.isEmpty())
			orgs.add(loginState.getOrganization());
	}

	/*
	 * Object onActionFromGoPageWarning() { return warningPage; }
	 */

	/* Alarm */

	/* password change */
	@CommitAfter
	void onSelectedFromChangePass() {
		boolean encPass = false;

		User user = dao.getUser(loginState.getEmployee());

		if (currentPassword != null)
			encPass = UserUtil.setPassword(currentPassword,
					user.getPasswordSalt(), user.getEncodedPassword());
		else
		// loginState.setErrMessage(messages.get("passwordNull"));

		if (!encPass)
			// loginState.setErrMessage(messages.get("currentPasswordIsWrong"));
			System.err.println(messages.get("currentPasswordIsWrong"));
		else {

			if (!newPassword.equals(confirmPassword))
				/*
				 * loginState
				 * .setErrMessage(messages.get("confrimPasswordIsWrong"));
				 */
				System.err.println(messages.get("confrimPasswordIsWrong"));
			else {
				if (newPassword != null && confirmPassword != null) {
					user.setPassword(confirmPassword);
					dao.saveOrUpdateObject(user);
				} else
					// loginState.setErrMessage(messages.get("passwordNull"));
					System.err.println(messages.get("passwordNull"));

			}
		}
	}

	void onActionFromGetUserGuide() {
		System.err.println("userGuide");

	}

	/* Getter Setter */

	public JSONObject getParam() {
		JSONObject defaults = new JSONObject();
		defaults.put("modal", true);
		defaults.put("resizable", false);
		defaults.put("draggable", true);
		defaults.put("autoOpen", false);
		defaults.put("width", 450);

		return defaults;
	}

	public String getCountRetire() {
		return "";
	}

	public String getUsername() {
		if (loginState.getEmployee() == null
				|| loginState.getEmployee().getFullNameFirstChar() == null)
			return "";
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

	public String getErrMessage() {

		/*
		 * String str = loginState.getErrMessage();
		 * loginState.setErrMessage(null);
		 */
		return "";
	}

	public String getAppointment() {
		return loginState.getAppointment();
	}

	public Boolean getCheckErrMessage() {
		/*
		 * Boolean b = (loginState.getErrMessage() == null || loginState
		 * .getErrMessage().equals("")) ? false : true;
		 */

		return true;
	}

	public Boolean getHasForm() {
		return hasForm;
	}

	public void setHasForm(Boolean hasForm) {
		this.hasForm = hasForm;
	}

	public String getGridRowCSS() {
		return GRID_ROW_CSS;
	}

	public String getLastName() {
		if (loginState.getEmployee() == null
				|| loginState.getEmployee().getLastname() == null)
			return "";

		return loginState.getEmployee().getLastname();
	}

	public String getFirstName() {
		if (loginState.getEmployee() == null
				|| loginState.getEmployee().getFirstName() == null)
			return "";

		return loginState.getEmployee().getFirstName();
	}

	public String getRegisterNo() {
		if (loginState.getEmployee() == null
				|| loginState.getEmployee().getRegisterNo() == null)
			return "";

		return loginState.getEmployee().getRegisterNo();
	}

	public String getAppointmentName() {
		if (loginState.getEmployee() == null
				|| loginState.getEmployee().getAppointment() == null)
			return "";

		return loginState.getEmployee().getAppointment().getAppointmentName();
	}

	public String getEmployeeName() {
		if (valueLoginHistory.getEmployee() == null
				|| valueLoginHistory.getEmployee().getFirstName() == null)
			return "";

		return valueLoginHistory.getEmployee().getFirstName();
	}

	public String getLoginDate() {
		if (valueLoginHistory.getLoginDate() == null
				|| valueLoginHistory.getLoginDate() == null)
			return "";

		return format.format(valueLoginHistory.getLoginDate());
	}

	public boolean isMcseAdmin() {
		if (loginState.getEmployee().getMcseAdmin() != null
				&& loginState.getEmployee().getMcseAdmin())
			return true;
		return false;
	}

}
