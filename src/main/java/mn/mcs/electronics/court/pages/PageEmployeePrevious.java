package mn.mcs.electronics.court.pages;

import java.util.HashSet;
import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.Appointment;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.EmployeeStatus;
import mn.mcs.electronics.court.enums.ExcelTypes;
import mn.mcs.electronics.court.model.AppointmentOrgSelectionModel;
import mn.mcs.electronics.court.model.OccupationTypeSelectionModel;
import mn.mcs.electronics.court.model.OrganizationSelectionModel;
import mn.mcs.electronics.court.util.beans.EmployeeSearchBean;
import mn.mcs.electronics.court.util.beans.GridPager;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.ajax.JavaScriptCallback;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.util.EnumSelectModel;

public class PageEmployeePrevious {

	/*
	 * STATES
	 */

	@SessionState
	private LoginState loginState;

	/*
	 * INJECTS
	 */

	@Inject
	private CoreDAO dao;

	@Inject
	private Messages messages;

	@Inject
	private AlertManager manager;

	@Inject
	private Request request;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@InjectComponent
	private Zone formZone, listZone, appointmentTypeZone, dialogZone;

	/*
	 * PERSISTS
	 */

	@Persist
	@Property
	private EmployeeSearchBean bean;

	@Persist
	@Property
	private Organization organization;

	@Persist
	@Property
	private Employee newEmployee;

	@Persist
	@Property
	private ExcelTypes excelType;

	@Persist
	@Property
	private GridPager pager;

	/*
	 * PROPERTIES
	 */

	@Persist
	@Property
	private List<Employee> listEmployee;

	@Property
	private Employee valueEmployee;

	@Property
	private String strRow;

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

		if (bean == null)
			bean = new EmployeeSearchBean();

		initGrid();
	}

	void initGrid() {
		// Урьд нь ажииллаж бйасан ажилчдын жагсаалт
		bean.setStatus(EmployeeStatus.TIRED);

		Subject currentUser = SecurityUtils.getSubject();

		if (orgLoaded == null || orgLoaded == false) {
			orgs = new HashSet<Organization>(loginState.getEmployee()
					.getMapEmpOrg());

			if (orgs.isEmpty())
				orgs.add(loginState.getOrganization());

			orgLoaded = true;
		}

		if (currentUser.isPermitted("show_all_organization")) {
			listEmployee = dao.getListEmployeeSearch(bean, null, null, pager);
		} else {
			listEmployee = dao.getListEmployeeSearch(bean, null, orgs, pager);
		}
	}

	void onSelectedFromSearch() {
		if (request.isXHR()) {
			initGrid();
			ajaxResponseRenderer.addRender(listZone);

			ajaxResponseRenderer.addCallback(new JavaScriptCallback() {
				public void run(JavaScriptSupport javaScriptSupport) {
					javaScriptSupport.addScript("new loaderHide('loader');");
				}
			});

		}
	}

	void onActionFromCancel() {
		if (request.isXHR()) {
			this.bean = new EmployeeSearchBean();

			initGrid();

			ajaxResponseRenderer.addRender(formZone).addRender(listZone);

			ajaxResponseRenderer.addCallback(new JavaScriptCallback() {
				public void run(JavaScriptSupport javaScriptSupport) {
					javaScriptSupport.addScript("new loaderHide('loader');");
				}
			});
		}
	}

	void onActionFromEmployeeSelect(Employee emp) {
		System.err.println("emp:" + emp.getFirstName());
		this.newEmployee = emp;
		ajaxResponseRenderer.addCallback(new JavaScriptCallback() {

			public void run(JavaScriptSupport javascriptSupport) {

				javascriptSupport.addScript("new openDialog();");
			}
		});

		ajaxResponseRenderer.addRender(dialogZone);

	}

	@CommitAfter
	void onSuccessFrompositionForm() {
		this.newEmployee.setEmployeeStatus(EmployeeStatus.WORKING);
		dao.saveOrUpdateObject(newEmployee);
		manager.alert(Duration.TRANSIENT, Severity.INFO,
				messages.get("empSuccessfullyActivated"));
	}

	void onActionFromFirst() {
		if (request.isXHR()) {
			pager.setFirstRow(0);
			pager.setMaxResult(20);

			initGrid();
			ajaxResponseRenderer.addRender(listZone);
		}
	}

	void onActionFromNext() {
		if (request.isXHR()) {
			pager.setFirstRow(pager.getFirstRow() + 20);
			pager.setMaxResult(pager.getMaxResult() + 20);

			initGrid();
			ajaxResponseRenderer.addRender(listZone);
		}
	}

	void onActionFromPrev() {
		if (request.isXHR()) {

			if (pager.getFirstRow() >= 20)
				pager.setFirstRow(pager.getFirstRow() - 20);
			else
				pager.setFirstRow(0);

			if (pager.getFirstRow() >= 40)
				pager.setMaxResult(pager.getMaxResult() - 20);
			else
				pager.setMaxResult(20);

			initGrid();
			ajaxResponseRenderer.addRender(listZone);
		}
	}

	void onActionFromLast() {
		if (request.isXHR()) {
			pager.setFirstRow(pager.getCount().intValue() - 20);
			pager.setMaxResult(pager.getCount().intValue());

			initGrid();
			ajaxResponseRenderer.addRender(listZone);
		}
	}

	void onValueChangedFromOccupationType() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(appointmentTypeZone);
		}
	}

	/*
	 * METHODS
	 */

	public String getListSize() {
		return String.valueOf(pager.getCount().intValue());
	}

	public String getNumber() {
		return listEmployee.indexOf(valueEmployee) + 1 + "";
	}

	public JSONObject getParam() {
		JSONObject defaults = new JSONObject();
		defaults.put("modal", true);
		defaults.put("resizable", true);
		defaults.put("draggable", true);
		defaults.put("position", "center");
		defaults.put("width", 800);
		defaults.put("title", messages.get("ImportExcel"));
		return defaults;
	}

	public Block getActiveBlock() {
		return null;
	}

	public String getExcelNumber() {
		if (valueEmployee != null && valueEmployee.getEmpNumber() != null)
			return valueEmployee.getEmpNumber().toString();
		return "-";
	}

	public JSONObject getParamPos() {
		JSONObject defaults = new JSONObject();
		defaults.put("modal", true);
		defaults.put("resizable", true);
		defaults.put("draggable", true);
		defaults.put("position", "center");
		defaults.put("width", 400);
		defaults.put("title", messages.get("empPositionInfo"));
		return defaults;
	}

	/* SELECT MODEL */

	public SelectModel getOrganizationSelectionModel() {
		return new OrganizationSelectionModel(dao);
	}

	public SelectModel getAppointmentSelectionModel() {
		return new AppointmentOrgSelectionModel(dao, bean.getOccupationType());
	}

	public SelectModel getExcelTypeSelectionModel() {
		return new EnumSelectModel(ExcelTypes.class, messages);
	}

	public SelectModel getOccupationTypeSelectionModel() {
		return new OccupationTypeSelectionModel(dao);
	}

}