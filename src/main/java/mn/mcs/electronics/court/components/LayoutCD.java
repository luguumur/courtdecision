package mn.mcs.electronics.court.components;

import java.util.List;

import javax.servlet.http.HttpSession;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.User;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.EmployeeMilitary;
import mn.mcs.electronics.court.pages.Home;
import mn.mcs.electronics.court.util.UserUtil;
import mn.mcs.electronics.court.util.beans.EmployeeSearchBean;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectPage;
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
import org.apache.tapestry5.services.RequestGlobals;
import org.tynamo.security.services.SecurityService;

@Import(library = { "classpath:org/got5/tapestry5/jquery/ui_1_8_19/jquery.ui.datepicker.js",
		"context:css/jquery.ui.datepicker-mn.js", "context:js/jquery.dataTables.js",
		"context:js/dataTables.buttons.min.js", "context:js/buttons.flash.min.js", "context:js/jszip.min.js",
		"context:js/buttons.html5.min.js", "classpath:org/got5/tapestry5/jquery/ui_1_8_19/jquery-ui-1.8.19.custom.js",
		"classpath:org/got5/tapestry5/jquery/ui_1_8_19/jquery.ui.tabs.js", "context:js/jquery.showLoading.js",
		"context:js/loader.js" }, stylesheet = { "classpath:org/tynamo/themes/tapestryskin/theme.css",
				"context:css/report.css", "context:css/index.css", "context:css/buttons.dataTables.min.css",
				"context:css/jquery-ui-1.10.3.custom.css" })
public class LayoutCD {

	/*
	 * STATES
	 */

	@SessionState
	private LoginState loginState;

	/*
	 * INJECTS
	 */

	@Inject
	private Context context;

	@Inject
	private Messages messages;

	@Inject
	private CoreDAO dao;

	@Inject
	private AssetSource imageUrl;

	@Inject
	private AssetSource backUrl;

	@Inject
	private AlertManager manager;

	@Inject
	private SecurityService securityService;

	@Inject
	private RequestGlobals requestGlobals;

	@Inject
	private ComponentResources resources;

	@Inject
	@Property
	@Path("context:/images/favicon.ico")
	private Asset favicon;

	/*
	 * PERSISTS
	 */

	@Property
	@Persist
	private String currentPassword;

	@Property
	@Persist
	private String newPassword;

	@Property
	@Persist
	private String confirmPassword;

	@Property
	private Employee valueEmployee;

	/*
	 * PAGE
	 */

	@InjectPage
	private Home home;

	public static final String IMAGE_PATH_EMP = "/images/profile/";

	void beginRender() {

	}

	/*
	 * METHODS
	 */

	public JSONObject getParam() {
		JSONObject defaults = new JSONObject();
		defaults.put("modal", true);
		defaults.put("resizable", false);
		defaults.put("draggable", true);
		defaults.put("autoOpen", false);
		defaults.put("width", 450);

		return defaults;
	}

	public String getUsername() {
		if (loginState.getEmployee() == null && loginState.getEmployee().getFullNameFirstChar() == null)
			return "";

		List<EmployeeMilitary> military = dao.getListEmployeeMilitary(loginState.getEmployee());

		String militaryRank = "";

		if (loginState.getEmployee() != null && military != null && !military.isEmpty()) {
			militaryRank = military.get(0).getMilitary().getMilitaryName();

		}

		return militaryRank + " " + loginState.getEmployee().getFullNameFirstChar();
	}

	public String getOrganiz() {
		if (loginState.getEmployee() == null && loginState.getEmployee().getFullNameFirstChar() == null)
			return "";

		List<EmployeeMilitary> military = dao.getListEmployeeMilitary(loginState.getEmployee());

		String militaryRank = "";

		if (loginState.getEmployee() != null && military != null && !military.isEmpty()) {
			militaryRank = military.get(0).getEmployee().getOrganization().getName();

		}

		return militaryRank;
	}

	public String getAppointment() {
		if (loginState.getEmployee() == null && loginState.getEmployee().getFullNameFirstChar() == null)
			return "";

		List<EmployeeMilitary> military = dao.getListEmployeeMilitary(loginState.getEmployee());

		String militaryRank = "";

		if (loginState.getEmployee() != null && military != null && !military.isEmpty() && military.get(0).getEmployee().getAppointment() != null) {
			militaryRank = military.get(0).getEmployee().getAppointment().getAppointmentName();
		}

		return militaryRank;
	}

	Link onActionFromLogout() {
		return this.getLogout();
	}

	Home onActionFromNowWorking() {
		home.setBean(new EmployeeSearchBean());
		return home;
	}

	private Link getLogout() {
		HttpSession session = requestGlobals.getHTTPServletRequest().getSession(false);

		if (session != null) {

			session.invalidate();
		}

		return resources.createPageLink("Login", false);
	}

	public boolean isMcseAdmin() {
		if (loginState.getEmployee().getMcseAdmin() != null && loginState.getEmployee().getMcseAdmin())
			return true;
		return false;
	}

	/* password change */
	@CommitAfter
	void onSelectedFromChangePass() {
		boolean encPass = false;

		User user = dao.getUser(loginState.getEmployee());

		if (currentPassword != null)
			encPass = UserUtil.setPassword(currentPassword, user.getPasswordSalt(), user.getEncodedPassword());
		else
			manager.alert(Duration.SINGLE, Severity.ERROR, "Нууц үгээ оруулна уу!");

		if (!encPass)
			System.err.println(messages.get("currentPasswordIsWrong"));
		else {

			if (!newPassword.equals(confirmPassword))
				System.err.println(messages.get("confrimPasswordIsWrong"));
			else {
				if (newPassword != null && confirmPassword != null) {
					user.setPassword(confirmPassword);
					dao.saveOrUpdateObject(user);
				} else
					manager.alert(Duration.SINGLE, Severity.ERROR, messages.get("passwordNull"));

			}
		}
	}

}
