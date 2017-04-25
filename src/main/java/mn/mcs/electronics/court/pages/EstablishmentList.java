package mn.mcs.electronics.court.pages;

import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.Establishment;
import mn.mcs.electronics.court.enums.MilitaryRankType;
import mn.mcs.electronics.court.model.AppointmentOrgSelectionModel;
import mn.mcs.electronics.court.model.OccupationTypeSelectionModel;
import mn.mcs.electronics.court.model.OrganizationSelectionModel;
import mn.mcs.electronics.court.model.UtTzTtTuSortSelectionModel;
import mn.mcs.electronics.court.model.UtTzTtUtLevelSM;
import mn.mcs.electronics.court.util.beans.EstablishmentSearchBean;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.ajax.JavaScriptCallback;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.apache.tapestry5.util.EnumSelectModel;
import org.hibernate.Session;

public class EstablishmentList {

	@SessionState
	private LoginState loginState;

	@Inject
	private CoreDAO dao;

	@Inject
	private Session session;

	@Inject
	private Messages messages;

	@Inject
	private Request request;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@InjectComponent
	private Zone listZone, utTzTtTuLevelZone, appointmentZone;

	/*
	 * PERSISTS
	 */

	@Persist
	@Property
	private EstablishmentSearchBean establishment;

	/*
	 * PROPERTIES
	 */

	@Property
	private List<Establishment> listEstablishment;

	@Property
	private Establishment valueEstablishment;

	void beginRender() {

		if (establishment == null)
			establishment = new EstablishmentSearchBean();

		initGrid();
	}

	void initGrid() {
		listEstablishment = dao.getListEstablishment(establishment);
	}

	/*
	 * EVENTS
	 */

	void onSelectedFromSearch() {
		if (request.isXHR()) {
			initGrid();
			ajaxResponseRenderer.addRender(listZone);

			ajaxResponseRenderer.addCallback(new JavaScriptCallback() {
				public void run(JavaScriptSupport javaScriptSupport) {
					javaScriptSupport
							.addScript("new loaderHide('loaderEstablishment');");
				}
			});

		}
	}

	void onActionFromCancel() {
		if (request.isXHR()) {

			this.establishment = new EstablishmentSearchBean();
			initGrid();

			ajaxResponseRenderer.addRender(listZone);

			ajaxResponseRenderer.addCallback(new JavaScriptCallback() {
				public void run(JavaScriptSupport javaScriptSupport) {
					javaScriptSupport
							.addScript("new loaderHide('loaderEstablishment');");
				}
			});
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
	 * SELECT MODELS
	 */

	public SelectModel getOrganizationSelectionModel() {
		return new OrganizationSelectionModel(dao);
	}

	public SelectModel getAppointmentTypeSelectionModel() {
		return new OccupationTypeSelectionModel(dao);
	}

	public SelectModel getAppointmentSelectionModel() {
		return new AppointmentOrgSelectionModel(dao,
				establishment.getAppointmentType());
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

	/*
	 * METHODS
	 */

	public String getMainName() {
		if (valueEstablishment != null && valueEstablishment.getMain() != null
				&& valueEstablishment.getMain())
			return "Тийм";
		return "Үгүй";
	}

	public String getStatusName() {
		if (valueEstablishment != null
				&& valueEstablishment.getStatus() != null
				&& valueEstablishment.getStatus())

			return "Тийм";
		return "Үгүй";
	}

	public String getNumName() {
		if (valueEstablishment != null && valueEstablishment.getNum() != null)

			return valueEstablishment.getNum().toString();
		return "0";
	}

	public String getNumber() {
		return listEstablishment.indexOf(valueEstablishment) + 1 + "";
	}

	public String getNuhugdsunName() {
		if (valueEstablishment.getAppointment() != null
				&& valueEstablishment.getAppointment().getId() != null)

			return Integer.toString(dao.getListEmployeeSearch(
					valueEstablishment.getAppointment().getId()).size());

		return "0";
	}

	public String getSulName() {
		if (valueEstablishment.getAppointment() != null
				&& valueEstablishment.getAppointment().getId() != null
				&& valueEstablishment.getNum() != null)

			return Integer
					.toString(valueEstablishment.getNum()
							- dao.getListEmployeeSearch(
									valueEstablishment.getAppointment().getId())
									.size());

		return "0";
	}

	public String getMilitaryRankTypeName() {
		if (valueEstablishment != null
				&& valueEstablishment.getMilitaryRankType() != null)

			return messages
					.get(valueEstablishment.getMilitaryRankType().name());
		return "";
	}

	public String getUtTzTtTuLevelName() {
		if (valueEstablishment != null
				&& valueEstablishment.getUtTzTtTuLevel() != null
				&& valueEstablishment.getUtTzTtTuLevel().getUtTzTtTuRank() != null)

			return valueEstablishment.getUtTzTtTuLevel().getUtTzTtTuRank()
					.getRank().toString();
		return "";
	}

	public String getCount() {
		return Integer.toString(listEstablishment.size());
	}
}