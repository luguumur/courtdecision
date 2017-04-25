package mn.mcs.electronics.court.components.warning;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.pages.employee.PageEmployeeDetail;
import mn.mcs.electronics.court.util.Constants;
import mn.mcs.electronics.court.util.beans.EmployeeLastDemeritBean;

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

public class CompWarningDemerit {

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
	 * Сүүлд сахилгын шийтгэл авсан ажилчид
	 */
	@Persist
	@Property
	private List<EmployeeLastDemeritBean> listSahilgaEmployee;

	@Persist
	@Property
	private EmployeeLastDemeritBean rowSahilgaEmployee;

	@Property
	@Persist
	private Date startDate;

	@Property
	@Persist
	private Date endDate;

	/*
	 * CONSTANTS
	 */

	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);

	void beginRender() {

		HashSet<Organization> orgs = new HashSet<Organization>(loginState
				.getEmployee().getMapEmpOrg());

		if (orgs.isEmpty())
			orgs.add(loginState.getOrganization());

		listSahilgaEmployee = dao.getEmployeeLastDemeritBean(startDate,
				endDate, orgs);
	}

	Object onActionFromEditDemerit(Long id) {

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
		return listSahilgaEmployee.indexOf(rowSahilgaEmployee) + 1 + "";
	}

	public String getDateOfDemerit() {
		if (rowSahilgaEmployee == null
				|| rowSahilgaEmployee.getShiitgelAvagdsanOgnoo() == null)
			return "";

		return format.format(rowSahilgaEmployee.getShiitgelAvagdsanOgnoo());
	}

	public String getCommandSubject() {
		return rowSahilgaEmployee.getCommandSubject().getSubjectName();
	}

	public String getSahilgaShiitgel() {
		return rowSahilgaEmployee.getSahilgaShiitgel().getShiitgelName();
	}

	public String getSahilgaTurul() {
		return rowSahilgaEmployee.getSahilgaTurul().getSahilgaTurulName();
	}

	public String getAppointment() {
		return rowSahilgaEmployee.getEmployee().getAppointment()
				.getAppointmentName();
	}

	public String getFirstName() {
		return rowSahilgaEmployee.getEmployee().getLastname().charAt(0) + "."
				+ rowSahilgaEmployee.getEmployee().getFirstName();
	}

	public String getOrgSahilga() {
		return rowSahilgaEmployee.getEmployee().getOrganization().getName();
	}

	public String getSahilgaCount() {
		return listSahilgaEmployee.size() + "";
	}

}
