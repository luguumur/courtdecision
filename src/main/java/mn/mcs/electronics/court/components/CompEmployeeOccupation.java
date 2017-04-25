package mn.mcs.electronics.court.components;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuLevel;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuSort;
import mn.mcs.electronics.court.entities.employee.Displacement;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.Experience;
import mn.mcs.electronics.court.enums.DisplacementCauseType;
import mn.mcs.electronics.court.enums.EmployeeStatus;
import mn.mcs.electronics.court.enums.MilitaryOrSimple;
import mn.mcs.electronics.court.enums.OrganizationTypeExperience;
import mn.mcs.electronics.court.enums.YesNo;
import mn.mcs.electronics.court.model.AppointmentOrgSelectionModel;
import mn.mcs.electronics.court.model.DepartmentSelectionModel;
import mn.mcs.electronics.court.model.OccupationLevelSelectionModel;
import mn.mcs.electronics.court.model.OccupationRoleSelectionModel;
import mn.mcs.electronics.court.model.OccupationTypeSelectionModel;
import mn.mcs.electronics.court.model.OrganizationSelectionModel;
import mn.mcs.electronics.court.model.SalaryNetworkSelectionModel;
import mn.mcs.electronics.court.model.SalaryScaleSelectionModel;
import mn.mcs.electronics.court.model.UtTzTtTuSortSelectionModel;
import mn.mcs.electronics.court.util.Constants;

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
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.util.EnumSelectModel;

public class CompEmployeeOccupation {

	@SessionState
	private LoginState loginState;

	/*
	 * INJECTS
	 */

	@Inject
	private Response response;

	@Inject
	private Environment environment;

	@Inject
	private Context context;

	@Inject
	private Messages messages;

	@Inject
	private CoreDAO dao;

	@Inject
	private Request request;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@InjectComponent
	private Zone occupationZone, uTtzZeregZone, occupationListZone;

	@Inject
	private AlertManager manager;

	@InjectComponent
	private Zone ocAppointmentZone, shiljihZone;

	/*
	 * PERSISTS
	 */

	@Persist
	private Employee employee;

	@Property
	@Persist
	private Displacement currentOccupation;

	@Property
	@Persist
	private UtTzTtTuSort utTzTtTuSort;

	@Property
	@Persist
	private UtTzTtTuLevel utTzTtTuLevel;

	@Property
	@Persist
	private Displacement valueCurrentOccupation;

	@Property
	@Persist
	private Displacement lastCurrentOccupation;

	@Property
	@Persist
	private YesNo shiljih;

	@Property
	private List<Displacement> listCurrentOccupation;

	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);

	void beginRender() {
		if (employee == null)
			employee = new Employee();

		if (employee != null && employee.getId() != null) {
			listCurrentOccupation = dao.getListCurrentOccupation(employee);
		}

		if (currentOccupation == null) {
			currentOccupation = new Displacement();
		}
		if (shiljih == null)
			shiljih = YesNo.NO;

		lastCurrentOccupation = dao.getCurrentOccupation(employee);

	}

	/*
	 * EVENTS
	 */

	@CommitAfter
	void onSelectedFromSave() {
		employee = (Employee) dao.getObject(Employee.class, employee.getId());

		currentOccupation.setEmployee(employee);

		if (currentOccupation.getIssuedDate().compareTo(new Date()) < 0
				|| currentOccupation.getIssuedDate().compareTo(new Date()) == 0) {

			currentOccupation.setUtTzTtTuLevel(utTzTtTuLevel);
			if (currentOccupation.getQuitDate() != null)
				currentOccupation.setIsNowDisplacement(false);
			else
				currentOccupation.setIsNowDisplacement(true);
			
			System.err.println(" currentOccupation " + currentOccupation);

			dao.saveOrUpdateObject(currentOccupation);
			if (lastCurrentOccupation != null
					&& lastCurrentOccupation.getId() != currentOccupation
							.getId()) {
				lastCurrentOccupation.setIsNowDisplacement(false);
				dao.saveOrUpdateObject(lastCurrentOccupation);
			}

			this.saveExperience();
			if (currentOccupation.getIsNowDisplacement()) {
				employee.setOrganization(currentOccupation.getOrganization());
				employee.setAppointment(currentOccupation.getAppointment());
			} else {
				employee.setOrganization(null);
				employee.setAppointment(null);
			}
			dao.saveOrUpdateObject(employee);
			listCurrentOccupation = dao.getListCurrentOccupation(employee);
			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));
			currentOccupation = new Displacement();
			lastCurrentOccupation = dao.getCurrentOccupation(employee);
			ajaxResponseRenderer.addRender("occupationZone", occupationZone)
					.addRender("occupationListZone", occupationListZone);
		} else {
			manager.alert(Duration.SINGLE, Severity.ERROR,
					messages.get("IssuedDateIsLaterThanCurrentDate"));
		}

	}

	void saveExperience() {
		Experience exp = dao.getExperienceOcc(employee, currentOccupation);

		if (exp == null)
			exp = new Experience();

		exp.setOrganizationtype(OrganizationTypeExperience.SHUUH);
		exp.setOrganizationname(currentOccupation.getOrganization().getName());
		exp.setStatus(EmployeeStatus.WORKING);
		exp.setEmployee(employee);
		exp.setAppointment(currentOccupation.getAppointment()
				.getAppointmentName());
		exp.setStartDate(currentOccupation.getIssuedDate());
		exp.setStatus(EmployeeStatus.WORKING);
		exp.setDisplacement(currentOccupation);
		exp.setMilitaryOrSimple(currentOccupation.getMilitaryOrSimple());
		
		dao.saveOrUpdateObject(exp);
		if (lastCurrentOccupation != null
				&& lastCurrentOccupation.getId() != currentOccupation.getId()) {
			Experience exp1 = dao.getExperienceOcc(employee,
					lastCurrentOccupation);
			exp1.setStatus(EmployeeStatus.TIRED);
			exp1.setEndDate(lastCurrentOccupation.getQuitDate());
			dao.saveOrUpdateObject(exp1);
		}

	}

	public void onActionFromEditDisplacement(Displacement displacement) {
		if (request.isXHR()) {
			this.currentOccupation = displacement;
			this.lastCurrentOccupation = displacement;
			utTzTtTuLevel = currentOccupation.getUtTzTtTuLevel();
			if (utTzTtTuLevel != null)
				utTzTtTuSort = utTzTtTuLevel.getUtTzTtTuSort();
			if (lastCurrentOccupation.getQuitDate() != null) {
				shiljih = YesNo.YES;
			} else {
				shiljih = YesNo.NO;
			}

			ajaxResponseRenderer.addRender("occupationZone", occupationZone);
		}
	}

	@CommitAfter
	public void onActionFromDelete(Displacement displacement) {
		
//		if (request.isXHR()) {
//			
			Experience experience = dao
					.getExperienceOcc(employee, displacement);
			if (experience != null) {
				dao.deleteObject(experience);
			}
//			
			dao.deleteObject(displacement);
			listCurrentOccupation = dao.getListCurrentOccupation(employee);

			ajaxResponseRenderer.addRender("occupationListZone",
					occupationListZone);
//		}
	}

	void onValueChangedFromUtTzClick() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(uTtzZeregZone);
		}
	}

	void onValueChangedFromOccupationType() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(ocAppointmentZone);
		}
	}

	void onValueChangedFromShiljih() {
		if (request.isXHR()) {
			System.err.println("shiljih:" + shiljih);
			System.err
					.println("lastCurrentOccupation:" + lastCurrentOccupation);
			if (shiljih != null && shiljih.equals(YesNo.YES)
					&& lastCurrentOccupation != null) {
				lastCurrentOccupation.setDisplacementCause(null);
				lastCurrentOccupation.setDisplacementType(null);
				lastCurrentOccupation.setQuitDate(null);
			}
			ajaxResponseRenderer.addRender("shiljihZone", shiljihZone);
		}
	}

	/*
	 * SELECT MODELS
	 */

	public SelectModel getOccupationRoleSelectionModel() {
		return new OccupationRoleSelectionModel(dao);
	}

	public SelectModel getOccupationTypeSelectionModel() {
		return new OccupationTypeSelectionModel(dao);
	}

	public SelectModel getAppointmentSelectionModel() {
		return new AppointmentOrgSelectionModel(dao,
				currentOccupation.getOccupationType());
	}

	public SelectModel getYesNoSelectionModel() {
		return new EnumSelectModel(YesNo.class, messages);
	}

	public SelectModel getOccupationLevelSelectionModel() {
		if (utTzTtTuSort == null) {
			List<UtTzTtTuSort> lst = dao.getListUtTzTtTuSort();
			if (!lst.isEmpty())
				utTzTtTuSort = lst.get(1);
		}

		return new OccupationLevelSelectionModel(dao, utTzTtTuSort);
	}

	public SelectModel getUtTzTtTuSortSelectionModel() {
		return new UtTzTtTuSortSelectionModel(dao);
	}

	public SelectModel getSalaryNetworkSelectionModel() {
		return new SalaryNetworkSelectionModel(dao);
	}

	public SelectModel getSalaryScaleSelectionModel() {
		return new SalaryScaleSelectionModel(dao);
	}

	public SelectModel getDepartmentSelectionModel() {
		return new DepartmentSelectionModel(dao, employee.getOrganization());
	}

	public SelectModel getOrganizationSelectionModel() {
		return new OrganizationSelectionModel(dao);
	}

	public SelectModel getDisplacementCauseTypeSelectionModel() {
		return new EnumSelectModel(DisplacementCauseType.class, messages);
	}

	public SelectModel getMilitaryOrSimpleSelectionModel() {
		return new EnumSelectModel(MilitaryOrSimple.class, messages);
	}

	/*
	 * GETTER, SETTER
	 */

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public int getNumberCo() {
		return listCurrentOccupation.indexOf(valueCurrentOccupation) + 1;
	}

	public String getIssuedDate() {

		if (valueCurrentOccupation == null
				|| valueCurrentOccupation.getIssuedDate() == null)
			return "";

		return format.format(valueCurrentOccupation.getIssuedDate());
	}

	public boolean getShiljiltDisabled() {
		if (shiljih == YesNo.YES) {
			return false;
		}

		return true;
	}
}
