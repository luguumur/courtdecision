package mn.mcs.electronics.court.components.warning;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.util.Constants;
import mn.mcs.electronics.court.util.beans.AlertMilitaryRankEmployeeBean;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

public class CompWarningTsol {

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

	/*
	 * PERSISTS
	 */

	@Persist
	@Property
	private List<AlertMilitaryRankEmployeeBean> listEmployeeMilitary;

	@Persist
	@Property
	private AlertMilitaryRankEmployeeBean rowEmployeeMilitary;

	@Property
	@Persist
	private Date startDate;

	@Property
	@Persist
	private Date endDate;

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

		if (orgLoaded == null || orgLoaded == false) {
			orgs = new HashSet<Organization>(loginState.getEmployee()
					.getMapEmpOrg());

			if (orgs.isEmpty())
				orgs.add(loginState.getEmployee().getOrganization());

			orgLoaded = true;
		}

		initGrid();
	}

	void initGrid() {
		Subject currentUser = SecurityUtils.getSubject();

		if (currentUser.isPermitted("show_all_organization")) {
			listEmployeeMilitary = dao.getListEmployeeByMilitary(null);
		} else {
			listEmployeeMilitary = dao.getListEmployeeByMilitary(orgs);
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
		return listEmployeeMilitary.indexOf(rowEmployeeMilitary) + 1 + "";
	}

	public String getIsTop() {
		if (rowEmployeeMilitary.getIsTop() != null
				&& rowEmployeeMilitary.getIsTop())
			return "Тийм";
		return "Үгүй";
	}

	public String getTsolOgnoo() {
		if (rowEmployeeMilitary.getTsolAvsanOgnoo() == null)
			return "";

		return format.format(rowEmployeeMilitary.getTsolAvsanOgnoo());
	}

	public String getTsolDuusahOgnoo() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(rowEmployeeMilitary.getTsolAvsanOgnoo());
		cal.add(Calendar.YEAR, rowEmployeeMilitary.getTsolHugatsaa());
		Date day = cal.getTime();

		return format.format(day);
	}

	public String getRegisterNoDegree() {
		if (rowEmployeeMilitary.getEmployee().getRegisterNo() == null)
			return "";

		return rowEmployeeMilitary.getEmployee().getRegisterNo();
	}

	public String getOrganizationDegree() {
		if (rowEmployeeMilitary.getEmployee().getOrganization() != null)
			return rowEmployeeMilitary.getEmployee().getOrganization()
					.getName();
		return "-";
	}

	public String getTsolWarningTypeCount() {
		return String.valueOf(listEmployeeMilitary.size());
	}

}
