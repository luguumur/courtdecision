package mn.mcs.electronics.court.pages.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.Permission;
import mn.mcs.electronics.court.entities.Role;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.map.MapRolePermission;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.AdminOperationType;
import mn.mcs.electronics.court.model.EmployeeSelectionModel;
import mn.mcs.electronics.court.model.OrganizationSelectionModel;
import mn.mcs.electronics.court.model.PermissionSelectionModel;
import mn.mcs.electronics.court.util.PaletteValueEncoder;
import mn.mcs.electronics.court.util.PaletteValueEncoderOrganization;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.util.EnumSelectModel;
import org.hibernate.Session;

public class PageConfig {

	/*
	 * INJECTS
	 */

	@Inject
	private CoreDAO dao;

	@Inject
	private Messages messages;

	@Inject
	private Session session;

	@SessionState
	private LoginState loginState;

	@Inject
	private Response response;

	@Inject
	@Property
	@Path("context:/images/excel.jpg")
	private Asset imgExcel;

	@Inject
	private Request request;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@InjectComponent
	private Zone roleZone, roleListZone, permZone, permListZone, medZone;

	/*
	 * PERSISTS
	 */

	@Persist
	@Property
	private AdminOperationType operation;

	@Persist("flash")
	@Property
	private List<String> usernames;

	@Property
	private String usernameLoop;

	@Persist("flash")
	@Property
	private HashMap<String, String> names;

	@Persist("flash")
	@Property
	private HashMap<String, String> passwds;

	@Persist
	@Property
	private List<String> userNamesE;

	@Persist
	@Property
	private HashMap<String, String> namesE;

	@Persist
	@Property
	private HashMap<String, String> passwdsE;

	@Persist("flash")
	@Property
	private List<String> phones;

	@Persist
	@Property
	private List<String> phones2;

	@Persist("flash")
	@Property
	private HashMap<String, String> homePhones;

	@Persist("flash")
	@Property
	private HashMap<String, String> homePhones2;

	@Persist("flash")
	@Property
	private HashMap<String, String> mobiles;

	@Persist("flash")
	@Property
	private HashMap<String, String> mobiles2;

	@Persist("flash")
	@Property
	private HashMap<String, String> workPhones;

	@Persist("flash")
	@Property
	private HashMap<String, String> workPhones2;

	@Persist
	@Property
	private List<Permission> selectedValues;

	@Persist
	@Property
	private Role role;

	@Persist
	private List<Permission> handling;

	@Persist
	private List<Permission> tempValues;

	@Persist
	@Property
	private Permission permission;

	@Property
	@Persist
	private List<Organization> selectedValuesInfo;

	@Property
	@Persist
	private Employee rowEmp;

	/*
	 * PROPERTY
	 */

	@Property
	private List<Employee> listUser;

	@Property
	private List<Role> listRole;

	@Property
	private Employee emp;

	@Property
	private Role valueRole;

	@Property
	private String namesLoop;

	@Property
	private List<Permission> listPermission;

	@Property
	private Permission valuePermission;

	void beginRender() {
		listPermission = dao.getPermissionList();
		listUser = dao.getListUser();
		initRoleList();

		if (selectedValues == null)
			selectedValues = new ArrayList<Permission>();

		if (tempValues == null)
			tempValues = new ArrayList<Permission>();

		if (role == null)
			role = new Role();

		if (role != null && role.getId() != null) {
			selectedValues = dao.getPermissionList(role);
			tempValues.addAll(selectedValues);
		}

		if (permission == null) {
			permission = new Permission();
		}

		if (selectedValuesInfo == null)
			selectedValuesInfo = new ArrayList<Organization>();

		if (rowEmp != null) {
			rowEmp = (Employee) dao.getObject(Employee.class, rowEmp.getId());
			selectedValuesInfo = new ArrayList<Organization>(
					rowEmp.getMapEmpOrg());
		}
	}

	void initRoleList() {
		listRole = dao.getRoleList();
	}

	/*
	 * EVENTS
	 */

	void onActionFromEdit(Role role) {
		this.role = role;
		selectedValues = dao.getPermissionList(role);
		tempValues.addAll(selectedValues);
		ajaxResponseRenderer.addRender(roleZone);
	}

	@CommitAfter
	void onSelectedFromSave() {
		// dao.saveOrUpdateObject(role);

		for (Permission perm : selectedValues) {

			boolean b = true;

			for (Permission temp : tempValues) {

				if (perm.getId().equals(temp.getId())) {
					b = false;
					break;
				}
			}

			if (b) {

				MapRolePermission mrp = new MapRolePermission();
				mrp.setPermission(perm);
				mrp.setRole(role);
				dao.saveOrUpdateObject(mrp);

			}
		}

		tempValues.removeAll(selectedValues);

		for (Permission temp : tempValues) {

			temp = (Permission) dao.getObject(Permission.class, temp.getId());
			role = (Role) dao.getObject(Role.class, role.getId());

			MapRolePermission mrp = dao.getMapRolePermission(temp, role);

			if (mrp != null) {
				dao.deleteObject(mrp);
			}
		}

		initRoleList();

		ajaxResponseRenderer.addRender(roleListZone).addRender(roleZone);

	}

	void onActionFromCancel() {
		role = new Role();
		tempValues = null;
		selectedValues = null;
		ajaxResponseRenderer.addRender(roleZone);
	}

	void onActionFromEditPermission(Permission permission) {
		this.permission = permission;
		ajaxResponseRenderer.addRender(permZone);
	}

	@CommitAfter
	void onSelectedFromSavePermission() {
		dao.saveOrUpdateObject(permission);
		listPermission = dao.getPermissionList();
		permission = new Permission();
		ajaxResponseRenderer.addRender(permListZone).addRender(permZone);
	}

	@CommitAfter
	void onSelectedFromSaveInfo() {
		rowEmp.setMapEmpOrg(new HashSet<Organization>(selectedValuesInfo));
		dao.saveOrUpdateObject(rowEmp);
		selectedValuesInfo = new ArrayList<Organization>(rowEmp.getMapEmpOrg());
		ajaxResponseRenderer.addRender(medZone);
	}

	/*
	 * GETTER, SETTER
	 */

	public List<Permission> getHandling() {
		return handling;
	}

	public void setHandling(List<Permission> handling) {
		this.handling = handling;
	}

	public PaletteValueEncoder getEncoder() {
		return new PaletteValueEncoder(session);
	}

	public String getFullnameLoop() {
		return names.get(usernameLoop);
	}

	public String getPasswdLoop() {
		return passwds.get(usernameLoop);
	}

	public String getPhoneLoop() {
		return homePhones.get(namesLoop);
	}

	public String getMobileLoop() {
		return mobiles.get(namesLoop);
	}

	public String getWorkPhoneLoop() {
		return workPhones.get(namesLoop);
	}

	public PaletteValueEncoderOrganization getValueEncoderInfo() {
		return new PaletteValueEncoderOrganization(session);
	}

	/*
	 * SELECT MODELS
	 */

	public SelectModel getEmployeeSelectionModel() {
		return new EmployeeSelectionModel(dao);
	}

	public SelectModel getOperationSelectionModel() {
		return new EnumSelectModel(AdminOperationType.class, messages);
	}

	public SelectModel getModel() {
		return new PermissionSelectionModel(dao);
	}

	public SelectModel getUserSelectionModel() {
		return new EmployeeSelectionModel(dao, true);
	}

	public SelectModel getOrganizationSelectionModel() {
		return new OrganizationSelectionModel(dao);
	}

}
