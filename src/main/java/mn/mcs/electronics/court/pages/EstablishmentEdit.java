package mn.mcs.electronics.court.pages;

import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.Establishment;
import mn.mcs.electronics.court.enums.MilitaryRankType;
import mn.mcs.electronics.court.model.AppointmentOrgSelectionModel;
import mn.mcs.electronics.court.model.OccupationTypeSelectionModel;
import mn.mcs.electronics.court.model.OrganizationSelectionModel;
import mn.mcs.electronics.court.model.SalaryNetworkSelectionModel;
import mn.mcs.electronics.court.model.UtTzTtTuSortSelectionModel;
import mn.mcs.electronics.court.model.UtTzTtUtLevelSM;

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
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.util.EnumSelectModel;
import org.hibernate.Session;

public class EstablishmentEdit {

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
	private Session session;

	@Inject
	private Request request;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@InjectComponent
	private Zone formZone, utTzTtTuLevelZone, listZone, appointmentZone;

	@Inject
	private AlertManager manager;

	/*
	 * PERSISTS
	 */

	@Persist
	@Property
	private Establishment establishment;

	/*
	 * PROPERTIES
	 */

	@Property
	private List<Employee> listEmployee;

	@Property
	private Employee valueEmployee;

	private Long establishmentID;

	void onActivate(Long id) {
		establishmentID = id;
	}

	Long onPassivate() {
		return establishmentID;
	}

	void beginRender() {
		this.establishment = (Establishment) dao.getObject(Establishment.class,
				establishmentID);

		if (establishment == null)
			establishment = new Establishment();

		initGrid();
	}

	void initGrid() {
		if (establishment != null && establishment.getAppointment() != null
				&& establishment.getAppointment().getId() != null) {

			listEmployee = dao.getListEmployeeSearch(establishment
					.getAppointment().getId());
			System.err.println("lisrEmpSize:" + listEmployee.size());
		}
	}

	/*
	 * EVENTS
	 */

	@CommitAfter
	void onSelectedFromSave() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(establishment);

			initGrid();

			ajaxResponseRenderer.addRender(formZone).addRender(listZone);

			manager.alert(Duration.SINGLE, Severity.INFO,
					"Амжилттай хадгалагдлаа!");
		}
	}

	void onValueChangedFromUtTzTtTuSort() {
		if (request.isXHR())
			ajaxResponseRenderer.addRender(utTzTtTuLevelZone);
	}

	void onValueChangedFromAppointmentType() {
		if (request.isXHR())
			ajaxResponseRenderer.addRender(appointmentZone);
	}

	/*
	 * METHODS
	 */

	public String getNumber() {
		return listEmployee.indexOf(valueEmployee) + 1 + "";
	}

	/*
	 * SELECT MODELS
	 */

	public SelectModel getOrganizationSelectionModel() {
		return new OrganizationSelectionModel(dao);
	}

	public SelectModel getAppointmentSelectionModel() {
		return new AppointmentOrgSelectionModel(dao,
				establishment.getAppointmentType());
	}

	public SelectModel getAppointmentTypeSelectionModel() {
		return new OccupationTypeSelectionModel(dao);
	}

	public SelectModel getUtTzTtTuSortSelectionModel() {
		return new UtTzTtTuSortSelectionModel(dao);
	}

	public SelectModel getUtTzTtTuLevelSelectionModel() {
		return new UtTzTtUtLevelSM(dao, establishment.getUtTzTtTuSort());
	}

	public SelectModel getMilitaryRankSelectionModel() {
		return new EnumSelectModel(MilitaryRankType.class, messages);
	}

	public SelectModel getSalaryNetworkSelectionModel() {
		return new SalaryNetworkSelectionModel(dao);
	}

}