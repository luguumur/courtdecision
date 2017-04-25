package mn.mcs.electronics.court.pages;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.EmployeeStatus;
import mn.mcs.electronics.court.enums.ExcelTypes;
import mn.mcs.electronics.court.model.AppointmentOrgSelectionModel;
import mn.mcs.electronics.court.model.OrganizationSelectionModel;
import mn.mcs.electronics.court.util.beans.EmployeeSearchBean;
import mn.mcs.electronics.court.util.beans.GridPager;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.ajax.JavaScriptCallback;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.util.EnumSelectModel;
import org.tynamo.routing.annotations.At;

/**
 * Start page of application example.
 */
@At("/")
public class Sudalgaa {

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
	private Zone formZone, listZone;

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
	private ExcelTypes excelType;

	@Persist
	@Property
	private List<Employee> listEmployee;

	@Persist
	private HashSet<Organization> orgs;

	@Persist
	private Boolean orgLoaded;
	
	@Persist
	private GridPager pager;

	/*
	 * PROPERTIES
	 */

	@Property
	private Employee valueEmployee;

	@Property
	private String strRow;

	void beginRender() {

		if (bean == null)
			bean = new EmployeeSearchBean();

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
		// Одоо ажиллаж буй ажилчдын жагсаалт
		bean.setStatus(EmployeeStatus.WORKING);
		
		Subject currentUser = SecurityUtils.getSubject();
		
		
		
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

	/*
	 * METHODS
	 */

	public String getCount() {
		return Integer.toString(listEmployee.size());
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

	/* SELECT MODEL */

	public SelectModel getOrganizationSelectionModel() {
		return new OrganizationSelectionModel(dao);
	}

	public SelectModel getAppointmentSelectionModel() {
		return new AppointmentOrgSelectionModel(dao);
	}

	public SelectModel getExcelTypeSelectionModel() {
		return new EnumSelectModel(ExcelTypes.class, messages);
	}
}