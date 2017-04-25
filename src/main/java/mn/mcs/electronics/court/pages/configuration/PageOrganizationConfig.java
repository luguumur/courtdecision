package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuLevel;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuSort;
import mn.mcs.electronics.court.entities.organization.AppurtenanceLead;
import mn.mcs.electronics.court.entities.organization.AppurtenanceLocation;
import mn.mcs.electronics.court.entities.organization.OrganizationType;
import mn.mcs.electronics.court.enums.UtTzTtTuCategory;
import mn.mcs.electronics.court.model.OccupationRankSelectionModel;
import mn.mcs.electronics.court.model.UtTzTtTuSortNameSelectionModel;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.util.EnumSelectModel;

public class PageOrganizationConfig {

	@Inject
	private CoreDAO dao;

	@Inject
	private Messages messages;

	@SessionState
	private LoginState loginState;

	@Inject
	private Request request;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@InjectComponent
	private Zone leadFormZone, leadListZone, locationFormZone,
			locationListZone, orgTypeListZone, orgTypeFormZone,
			utTzTtTuSortFormZone, utTzTtTuSortListZone, utTzTtTuLevelListZone,
			utTzTtTuLevelFormZone;

	/*
	 * PERSISTS
	 */

	@Property
	@Persist
	private AppurtenanceLead appurtenanceLead;

	@Property
	@Persist
	private List<AppurtenanceLead> listAppurtenanceLead;

	@Property
	@Persist
	private AppurtenanceLocation appurtenanceLocation;

	@Property
	@Persist
	private OrganizationType organizationType;

	@Property
	@Persist
	private UtTzTtTuSort utTzTtTuSort;

	@Property
	@Persist
	private UtTzTtTuLevel utTzTtTuLevel;

	// private List<Employee> listEmp;

	/*
	 * PROPERTY
	 */

	@Property
	private AppurtenanceLead valueAppurtenanceLead;

	@Property
	private List<AppurtenanceLocation> listAppurtenanceLocation;

	@Property
	private AppurtenanceLocation valueAppurtenanceLocation;

	@Property
	private List<OrganizationType> listOrganizationType;

	@Property
	private OrganizationType valueOrganizationType;

	@Property
	private List<UtTzTtTuSort> listUtTzTtTuSort;

	@Property
	private UtTzTtTuSort valueUtTzTtTuSort;

	@Property
	private UtTzTtTuLevel valueUtTzTtTuLevel;

	@Property
	private List<UtTzTtTuLevel> listUtTzTtTuLevel;

	@CommitAfter
	void beginRender() {

		if (appurtenanceLead == null)
			appurtenanceLead = new AppurtenanceLead();

		if (appurtenanceLocation == null)
			appurtenanceLocation = new AppurtenanceLocation();

		if (organizationType == null)
			organizationType = new OrganizationType();

		if (utTzTtTuSort == null)
			utTzTtTuSort = new UtTzTtTuSort();

		if (utTzTtTuLevel == null)
			utTzTtTuLevel = new UtTzTtTuLevel();

		initGridAppurtenanceLead();
		initGridAppurtenanceLocation();
		initGridOrganizationType();
		initGridlistUtTzTtTuSort();
		initGridUtTzTtTuLevel();

		// listEmp = dao.getListEmployee();

		/*
		 * for (Employee emp : listEmp) { if (emp != null && emp.getRegisterNo()
		 * != null) {
		 * 
		 * if (emp.getRegisterNo().length() > 23) {
		 * 
		 * emp.setOldReg(emp.getRegisterNo());
		 * 
		 * String regNum = null;
		 * 
		 * try { regNum = AES.decrypt(emp.getRegisterNo()); } catch (Exception
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 * 
		 * emp.setRegisterNo(regNum); dao.saveOrUpdateObject(emp); }
		 * 
		 * } }
		 */

		/*
		 * Integer counter = 0; for (Employee emp : listEmp) { if (emp != null
		 * && emp.getRegisterNo() != null && emp.getRegisterNo().length() > 10)
		 * {
		 * 
		 * if(emp.getRegisterNo().contains(":") ||
		 * emp.getRegisterNo().contains(" ") ||
		 * emp.getRegisterNo().contains("   ")) {
		 * emp.setRegisterNo(emp.getRegisterNo().replace(":", ""));
		 * emp.setRegisterNo(emp.getRegisterNo().replace(" ", ""));
		 * emp.setRegisterNo(emp.getRegisterNo().replace("   ", "")); }
		 * 
		 * dao.saveOrUpdateObject(emp);
		 * 
		 * System.err.println(counter++ + ". Error: " + emp.getRegisterNo()); }
		 * }
		 */
	}

	void initGridAppurtenanceLead() {
		listAppurtenanceLead = dao.getListAppurtenanceLead();
	}

	void initGridAppurtenanceLocation() {
		listAppurtenanceLocation = dao.getListAppurtenanceLocation();
	}

	void initGridOrganizationType() {
		listOrganizationType = dao.getListOrganizationType();
	}

	void initGridlistUtTzTtTuSort() {
		listUtTzTtTuSort = dao.getListUtTzTtTuSort();
	}

	void initGridUtTzTtTuLevel() {
		listUtTzTtTuLevel = dao.getListUtTzTtTuLevel();
	}

	/*
	 * EVENTS
	 */
	@CommitAfter
	void onSelectedFromSave() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(appurtenanceLead);
			appurtenanceLead = new AppurtenanceLead();
			initGridAppurtenanceLead();
			ajaxResponseRenderer.addRender(leadFormZone)
					.addRender(leadListZone);
		}
	}

	void onActionFromEdit(AppurtenanceLead appurtenanceLead) {
		if (request.isXHR()) {
			this.appurtenanceLead = appurtenanceLead;
			ajaxResponseRenderer.addRender(leadFormZone);
		}
	}

	@CommitAfter
	void onActionFromDelete(AppurtenanceLead appurtenanceLead) {
//		if (request.isXHR()) {
			dao.deleteObject(appurtenanceLead);
			initGridAppurtenanceLead();
//			ajaxResponseRenderer.addRender(leadListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveLocation() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(appurtenanceLocation);
			appurtenanceLocation = new AppurtenanceLocation();
			initGridAppurtenanceLocation();
			ajaxResponseRenderer.addRender(locationFormZone).addRender(
					locationListZone);
		}
	}

	void onActionFromEditLocation(AppurtenanceLocation appurtenanceLocation) {
		if (request.isXHR()) {
			this.appurtenanceLocation = appurtenanceLocation;
			ajaxResponseRenderer.addRender(locationFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteLocation(AppurtenanceLocation appurtenanceLocation) {
//		if (request.isXHR()) {
			dao.deleteObject(appurtenanceLocation);
			initGridAppurtenanceLocation();
//			ajaxResponseRenderer.addRender(locationListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveOrgType() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(organizationType);
			organizationType = new OrganizationType();
			initGridOrganizationType();
			ajaxResponseRenderer.addRender(orgTypeFormZone).addRender(
					orgTypeListZone);
		}
	}

	void onActionFromEditOrgType(OrganizationType organizationType) {
		if (request.isXHR()) {
			this.organizationType = organizationType;
			ajaxResponseRenderer.addRender(orgTypeFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteOrgType(OrganizationType organizationType) {
//		if (request.isXHR()) {
			dao.deleteObject(organizationType);
			initGridOrganizationType();
//			ajaxResponseRenderer.addRender(orgTypeListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveUtTzTtTuSort() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(utTzTtTuSort);
			utTzTtTuSort = new UtTzTtTuSort();
			initGridlistUtTzTtTuSort();
			ajaxResponseRenderer.addRender(utTzTtTuSortFormZone).addRender(
					utTzTtTuSortListZone);
		}
	}

	void onActionFromEditUtTzTtTuSort(UtTzTtTuSort utTzTtTuSort) {
		if (request.isXHR()) {
			this.utTzTtTuSort = utTzTtTuSort;
			ajaxResponseRenderer.addRender(utTzTtTuSortFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteUtTzTtTuSort(UtTzTtTuSort utTzTtTuSort) {
//		if (request.isXHR()) {
			dao.deleteObject(utTzTtTuSort);
			initGridlistUtTzTtTuSort();
//			ajaxResponseRenderer.addRender(utTzTtTuSortListZone);
//		}
	}

	@CommitAfter
	void onSelectedFromSaveUtTzTtTuLevel() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(utTzTtTuLevel);
			utTzTtTuLevel = new UtTzTtTuLevel();
			initGridUtTzTtTuLevel();
			ajaxResponseRenderer.addRender(utTzTtTuLevelFormZone).addRender(
					utTzTtTuLevelListZone);
		}
	}

	void onActionFromEditUtTzTtTuLevel(UtTzTtTuLevel utTzTtTuLevel) {
		if (request.isXHR()) {
			this.utTzTtTuLevel = utTzTtTuLevel;
			ajaxResponseRenderer.addRender(utTzTtTuLevelFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteUtTzTtTuLevel(UtTzTtTuLevel utTzTtTuLevel) {
//		if (request.isXHR()) {
			dao.deleteObject(utTzTtTuLevel);
			initGridUtTzTtTuLevel();
//			ajaxResponseRenderer.addRender(utTzTtTuLevelListZone);
//		}
	}

	/*
	 * METHODS
	 */
	public int getNumber() {
		return listAppurtenanceLead.indexOf(valueAppurtenanceLead) + 1;
	}

	public int getNumberOrgType() {
		return listOrganizationType.indexOf(valueOrganizationType) + 1;
	}

	public int getNumberUtTzTtTuSort() {
		return listUtTzTtTuSort.indexOf(valueUtTzTtTuSort) + 1;
	}

	public int getNumberUtTzTtTuLevel() {
		return listUtTzTtTuLevel.indexOf(valueUtTzTtTuLevel) + 1;
	}

	public String getSortName() {
		if (valueUtTzTtTuLevel == null
				|| valueUtTzTtTuLevel.getUtTzTtTuSort() == null)
			return "";

		return valueUtTzTtTuLevel.getUtTzTtTuSort().getName();
	}

	public String getRankName() {
		if (valueUtTzTtTuLevel == null
				|| valueUtTzTtTuLevel.getUtTzTtTuRank() == null)
			return "";

		return valueUtTzTtTuLevel.getUtTzTtTuRank().getRank().toString();
	}

	/*
	 * SELECT MODELS
	 */
	public SelectModel getUtTzTtTuSortNameSelectionModel() {
		return new EnumSelectModel(UtTzTtTuCategory.class, messages);
	}

	public SelectModel getUtTzTtTuSortSelectionModel() {
		return new UtTzTtTuSortNameSelectionModel(dao);

	}

	public SelectModel getOccupationRankSelectionModel() {
		return new OccupationRankSelectionModel(dao);

	}

}
