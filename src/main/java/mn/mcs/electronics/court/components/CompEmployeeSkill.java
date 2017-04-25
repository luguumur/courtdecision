package mn.mcs.electronics.court.components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.Role;
import mn.mcs.electronics.court.entities.configuration.SkillGroup;
import mn.mcs.electronics.court.entities.configuration.SkillType;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.Skills;
import mn.mcs.electronics.court.model.SkillPointSelectionModel;
import mn.mcs.electronics.court.pages.Home;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
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

public class CompEmployeeSkill {

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

	@InjectPage
	private Home home;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@InjectComponent
	private Zone skillZone;

	@Inject
	private AlertManager manager;

	/*
	 * PERSISTS
	 */

	@Persist
	private Employee employee;

	@Persist
	private Integer empSkillPoint;

	@Property
	@Persist
	private Role empRole;

	@Property
	@Persist
	private SkillType valueSkillType;

	@Property
	private List<SkillGroup> listSkillGroup;

	@Property
	private List<SkillType> listSkillType;

	@Property
	private List<SkillType> listSkillType1;

	@Property
	private List<SkillType> listSkillType2;

	@Property
	private List<SkillType> listSkillType3;

	@Property
	private Set<Skills> empSkillRates;

	void beginRender() {
		if (employee == null)
			employee = new Employee();

		initSkillLists();

		if (empSkillRates == null)
			empSkillRates = new HashSet<Skills>();

	}

	void initSkillLists() {
		empSkillRates = employee.getSkills();

		listSkillGroup = dao.getListSkillGroup();

		listSkillType = dao.getListSkillType(listSkillGroup.get(0));

		listSkillType1 = dao.getListSkillType(listSkillGroup.get(1));

		listSkillType2 = dao.getListSkillType(listSkillGroup.get(2));

		listSkillType3 = dao.getListSkillType(listSkillGroup.get(3));

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

	public Integer getEmpSkillPoint() {
		for (Skills _skillRate : empSkillRates) {

			if (_skillRate.getSkillType().equals(valueSkillType))
				return _skillRate.getSkillPoint();
		}

		return new Integer(0);
	}

	public void setEmpSkillPoint(Integer empSkillPoint) {

		if (empSkillRates == null) {
			empSkillRates = new HashSet<Skills>();
		}

		for (Skills empSkillRate : empSkillRates) {

			if (empSkillRate.getSkillType().equals(valueSkillType)) {
				empSkillRate.setSkillPoint(empSkillPoint);
				System.err.println("valueSkillType:" + valueSkillType);

				return;
			}
		}

		Skills _skillRate = new Skills();

		_skillRate.setSkillPoint(empSkillPoint);

		_skillRate.setSkillType(valueSkillType);

		_skillRate.setEmployee(employee);

		empSkillRates.add(_skillRate);
	}

	/*
	 * EVENTS
	 */

	@CommitAfter
	void onSelectedFromSave() {
		if (request.isXHR()) {
			employee.setSkills(empSkillRates);
			dao.saveOrUpdateObject(employee);

			initSkillLists();
			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));
			ajaxResponseRenderer.addRender(skillZone);
		}
	}

	void onActionFromCancel() {

		initSkillLists();

		empSkillRates = new HashSet<Skills>();
		employee.setBusadSkillName1(null);
		employee.setBusadSkillName2(null);
		employee.setBusadSkillName3(null);
		employee.setBusadSkillOnoo1(null);
		employee.setBusadSkillOnoo2(null);
		employee.setBusadSkillOnoo3(null);

		ajaxResponseRenderer.addRender("skillZone", skillZone);
	}

	/*
	 * SELECT MODELS
	 */

	public SelectModel getSkillPointSelectionModel() {

		List<Integer> skillOnoonuud = new ArrayList<Integer>();

		skillOnoonuud.add(1);
		skillOnoonuud.add(2);
		skillOnoonuud.add(3);

		SelectModel sm = new SkillPointSelectionModel(skillOnoonuud);

		return sm;
	}

}
