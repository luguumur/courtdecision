package mn.mcs.electronics.court.components.warning;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.pages.employee.PageEmployeeDetail;
import mn.mcs.electronics.court.util.Constants;
import mn.mcs.electronics.court.util.beans.DisciplinedEmployeeBean;
import mn.mcs.electronics.court.util.beans.GridPager;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

public class CompWarningDiscipline {

	/*
	 * INJECTS
	 */

	@SessionState
	private LoginState loginState;

	@Inject
	private Request request;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@Inject
	private CoreDAO dao;

	@Inject
	private AlertManager manager;

	@Inject
	private Messages messages;

	@Inject
	@Path("context:/images/b_edit.png")
	private Asset editIcon;

	@InjectPage
	private PageEmployeeDetail employeeDetail;

	/*
	 * PERSISTS
	 */

	/*
	 * Сахилгын шийтгэлийн хугацаа дуусах дөхсөн
	 */
	@Persist
	@Property
	private List<DisciplinedEmployeeBean> listEmployee;

	@Persist
	@Property
	private DisciplinedEmployeeBean rowEmployee;

	@Property
	@Persist
	private Date startDate;

	@Property
	@Persist
	private Date endDate;

	@Persist
	@Property
	private GridPager pager;

	@Persist
	private HashSet<Organization> orgs;

	@Persist
	private Boolean orgLoaded;

	/*
	 * CONSTANTS
	 */

	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);

	void beginRender() {

		if (pager == null) {
			pager = new GridPager();
			pager.setFirstRow(0);
			pager.setMaxResult(20);
		}

		if (orgLoaded == null || orgLoaded == false) {
			orgs = new HashSet<Organization>(loginState.getEmployee()
					.getMapEmpOrg());

			if (orgs.isEmpty())
				orgs.add(loginState.getEmployee().getOrganization());

			orgLoaded = true;
		}
	}

	void initGrid() {
		Subject currentUser = SecurityUtils.getSubject();

		if (currentUser.isPermitted("show_all_organization")) {
			listEmployee = dao.getListEmployeeByDiscipline(orgs);
		} else {
			listEmployee = dao.getListEmployeeByDiscipline(orgs);
		}
	}

	/*
	 * GETTERS, SETTERS
	 */

	public Asset getEditIcon() {
		return editIcon;
	}

	public void setEditIcon(Asset editIcon) {
		this.editIcon = editIcon;
	}

	public String getNumber() {
		return 0 + "";
	}

	public String getWarningTypeCount() {
		return ((listEmployee != null) ? listEmployee.size() : 0) + "";
	}

	public String getOrganization() {
		return rowEmployee.getOrg();
	}

	public String getStartDate() {
		if (rowEmployee == null
				|| rowEmployee.getShiitgelAvagdsanOgnoo() == null)
			return "";

		return format.format(rowEmployee.getShiitgelAvagdsanOgnoo());
	}

	public String getEndDate() {
		if (rowEmployee.getShiitgelDuusahOgnoo() == null)
			return "";

		return format.format(rowEmployee.getShiitgelDuusahOgnoo());
	}

}
