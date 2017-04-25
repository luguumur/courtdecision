package mn.mcs.electronics.court.components.warning;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.pages.employee.PageEmployeeDetail;
import mn.mcs.electronics.court.util.beans.AlertUdaanJilEmployeeBean;
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

public class CompWarningUdaanJil {

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
	 * Удаан жил
	 */
	@Persist
	@Property
	private List<AlertUdaanJilEmployeeBean> listUdaanJilEmployee;

	@Persist
	@Property
	private AlertUdaanJilEmployeeBean rowUdaanJilEmployee;

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
				orgs.add(loginState.getOrganization());

			orgLoaded = true;
		}

		initGrid();

	}

	void initGrid() {
		Subject currentUser = SecurityUtils.getSubject();

		if (currentUser.isPermitted("show_all_organization")) {
			listUdaanJilEmployee = dao
					.getListEmployeeAlertUdaanJil(null, pager);
		} else {
			listUdaanJilEmployee = dao
					.getListEmployeeAlertUdaanJil(orgs, pager);
		}
	}

	Object onActionFromEditUdaanJil(Long id) {

		Employee emp = (Employee) dao.getObject(Employee.class, id);

		employeeDetail.setEmployee(emp);
		employeeDetail.setIsView(false);

		return employeeDetail;
	}

	void onActionFromFirst() {
		pager.setFirstRow(0);
		pager.setMaxResult(20);

		initGrid();

	}

	void onActionFromNext() {
		pager.setFirstRow(pager.getFirstRow() + 20);
		pager.setMaxResult(pager.getMaxResult() + 20);

		initGrid();

	}

	void onActionFromPrev() {

		if (pager.getFirstRow() >= 20)
			pager.setFirstRow(pager.getFirstRow() - 20);
		else
			pager.setFirstRow(0);

		if (pager.getFirstRow() >= 40)
			pager.setMaxResult(pager.getMaxResult() - 20);
		else
			pager.setMaxResult(20);

		initGrid();

	}

	void onActionFromLast() {
		pager.setFirstRow(pager.getCount().intValue() - 20);
		pager.setMaxResult(pager.getCount().intValue());

		initGrid();

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

	public String getUdaanJilTextCount() {
		return String.valueOf((pager.getCount() != null) ? pager.getCount()
				.intValue() : 0);
	}

}
