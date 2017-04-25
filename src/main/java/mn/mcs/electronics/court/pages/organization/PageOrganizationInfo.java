package mn.mcs.electronics.court.pages.organization;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.organization.Department;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.OrganizationGroup;
import mn.mcs.electronics.court.model.AppurtenanceLeadSelectionModel;
import mn.mcs.electronics.court.model.AppurtenanceLocationSelectionModel;
import mn.mcs.electronics.court.model.CityProvinceSelectionModel;
import mn.mcs.electronics.court.model.DistrictSumSelectionModel;
import mn.mcs.electronics.court.model.FinancingTypeSelectionModel;
import mn.mcs.electronics.court.model.OrganizationTypeSelectionModel;
import mn.mcs.electronics.court.model.UserSelectionModel;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.util.EnumSelectModel;

public class PageOrganizationInfo {

	/*
	 * INJECTS
	 */

	@Inject
	private CoreDAO dao;

	@Inject
	private Messages messages;

	@SessionState
	private LoginState loginState;

	@InjectComponent
	private Zone CityZone;

	@Inject
	private Request request;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@InjectComponent
	private Zone organizationFormZone;

	/*
	 * PERSISTS
	 */

	/*@Property
	@Persist
	private Department department;

	@Property
	@Persist
	private List<Department> listOrganizationDepartment;

	@Property
	@Persist
	private Department valueOrganizationDepartment;*/

	@Persist
	private Organization organization;

	@Persist
	private boolean subDepart;

	private static final String GRID_ROW_CSS = "myGrid";

	void beginRender() {

//		if (department == null)
//			department = new Department();

		if (organization == null)
			organization = new Organization();

		Subject currentUser = SecurityUtils.getSubject();

		if (currentUser.isPermitted("go_subDepartment"))
			subDepart = false;
		else
			subDepart = true;

//		if (organization.getId() == null)
//			listOrganizationDepartment = null;
//		else
//			listOrganizationDepartment = dao.getListDepartment(organization);
	}

	@CommitAfter
	void onSelectedFromSave() {
		if (request.isXHR()) {
			if (dao.getListOrganizationByName(organization.getName()) == null
					|| dao.getListOrganizationByName(organization.getName())
							.isEmpty())
				dao.saveOrUpdateObject(dao.mergeObject(organization));
			else if (dao.getListOrganizationByName(organization.getName())
					.size() == 1
					|| dao.getListOrganizationByName(organization.getName())
							.get(0).getId().equals(organization.getId()))
				dao.saveOrUpdateObject(dao.mergeObject(organization));
			else
				// loginState.setErrMessage("Байгууллага бүртгэгдсэн байна!");
				System.err.println("Байгууллага бүртгэгдсэн байна!");

			ajaxResponseRenderer.addRender(organizationFormZone);
		}
	}

//	@CommitAfter
//	void onSelectedFromSaveDepartment() {
//		department.setOrganization(organization);
//		dao.saveOrUpdateObject(department);
//	}

//	void onActionFromEdit(Department department) {
//		this.department = department;
//	}

	public boolean getVisibleDepartment() {
		if (organization != null && organization.getId() != null)
			return true;
		return false;
	}

	/* getter, setter */

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getGridRowCSS() {
		return GRID_ROW_CSS;
	}

	Object onValueChangedFromOrgClick() {
		return request.isXHR() ? CityZone.getBody() : null;
	}

//	public String getSubDepartmentName() {
//		if (valueOrganizationDepartment != null
//				&& valueOrganizationDepartment.getName() != null)
//			return valueOrganizationDepartment.getName();
//		return "";
//	}

	public boolean isSubDepartmentPermission() {
		return subDepart;
	}

//	public Department getDepartmentOrg() {
//		return valueOrganizationDepartment;
//	}

	/*
	 * SELECT MODEL
	 */

	public SelectModel getOrganizationGroup() {
		return new EnumSelectModel(OrganizationGroup.class, messages);
	}

	public SelectModel getFinancingTypeSelectionModel() {
		return new FinancingTypeSelectionModel(dao);
	}

	public SelectModel getAppurtenanceLeadSelectionModel() {
		return new AppurtenanceLeadSelectionModel(dao);
	}

	public SelectModel getAppurtenanceLocationSelectionModel() {
		return new AppurtenanceLocationSelectionModel(dao);
	}

	public SelectModel getCityProvinceSelectionModel() {
		return new CityProvinceSelectionModel(dao);
	}

	public SelectModel getDistrictSumSelectionModel() {
		return new DistrictSumSelectionModel(dao,
				organization.getCityProvince());
	}

	public SelectModel getOrganizationTypeSelectionModel() {
		return new OrganizationTypeSelectionModel(dao);
	}

	public SelectModel getUserSelectionModel() {
		return new UserSelectionModel(dao);
	}
}
