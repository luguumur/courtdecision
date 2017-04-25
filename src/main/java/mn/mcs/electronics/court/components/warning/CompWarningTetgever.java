package mn.mcs.electronics.court.components.warning;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.pages.employee.PageEmployeeDetail;
import mn.mcs.electronics.court.util.beans.GridPager;
import mn.mcs.electronics.court.util.beans.TetgevriinEmployeeBean;

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

public class CompWarningTetgever {

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

	@Persist
	@Property
	private List<TetgevriinEmployeeBean> listTetgevriinEmployee;

	@Persist
	@Property
	private TetgevriinEmployeeBean rowTetgevriinEmployee;

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

	}

	void initGrid() {
		Subject currentUser = SecurityUtils.getSubject();

		if (currentUser.isPermitted("show_all_organization")) {
			listTetgevriinEmployee = dao.getListEmployeeByTetgevriin(null,
					pager);
		} else {
			listTetgevriinEmployee = dao.getListEmployeeByTetgevriin(orgs,
					pager);
		}
	}

	Object onActionFromEditTetgever(Long id) {

		Employee emp = (Employee) dao.getObject(Employee.class, id);

		employeeDetail.setEmployee(emp);
		employeeDetail.setIsView(false);

		return employeeDetail;
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

	public String getOrganizationRetire() {
		return rowTetgevriinEmployee.getBaiguullaga();
	}

	public String getTetgevriinAjilchidCount() {
		return String.valueOf((pager.getCount() != null) ? pager.getCount()
				.intValue() : 0);
	}

}
