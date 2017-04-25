package mn.mcs.electronics.court.pages.employee;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mn.mcs.electronics.court.components.LayoutEmployee;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.SkillGroup;
import mn.mcs.electronics.court.entities.configuration.SkillType;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.Skills;
import mn.mcs.electronics.court.model.SkillPointSelectionModel;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

public class PageEmployeeSkill {

	@InjectComponent
	private LayoutEmployee layoutEmployee;

	@Inject
	private CoreDAO dao;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@InjectComponent
	private Zone skillZone;

	@Persist
	private Employee employee;

	@Inject
	private Messages messages;

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
	@Persist
	private SkillType valueSkillType;

	@Property
	private Set<Skills> empSkillRates;

	@Persist
	private Integer empSkillPoint;

	@Persist
	private boolean viewEmployee;

	private Long empID;

	void onActivate(Long id) {
		empID = id;
	}

	Long onPassivate() {
		return empID;
	}

	void beginRender() {

		this.employee = (Employee) dao.getObject(Employee.class, empID);
		empSkillRates = employee.getSkills();

		layoutEmployee.setValueEmployee(employee);
		//
		// empSkillRates = getEmployee().getSkills();

		initSkillLists();

		viewEmployee = true;
		if (empSkillRates == null)
			empSkillRates = new HashSet<Skills>();
	}

	void initSkillLists() {
		listSkillGroup = dao.getListSkillGroup();

		listSkillType = dao.getListSkillType(listSkillGroup.get(0));

		listSkillType1 = dao.getListSkillType(listSkillGroup.get(1));

		listSkillType2 = dao.getListSkillType(listSkillGroup.get(2));

		listSkillType3 = dao.getListSkillType(listSkillGroup.get(3));

	}

	@CommitAfter
	void onSelectedFromEmpSkills() {

		employee.setSkills(empSkillRates);

		dao.saveOrUpdateObject(employee);

		initSkillLists();
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

	/* Selection model */
	public SelectModel getSkillPointSelectionModel() {

		List<Integer> skillOnoonuud = new ArrayList<Integer>();

		skillOnoonuud.add(1);
		skillOnoonuud.add(2);
		skillOnoonuud.add(3);

		SelectModel sm = new SkillPointSelectionModel(skillOnoonuud);

		return sm;
	}

	/* Getter setter */

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

				return;
			}
		}

		Skills _skillRate = new Skills();

		_skillRate.setSkillPoint(empSkillPoint);

		_skillRate.setSkillType(valueSkillType);

		_skillRate.setEmployee(employee);

		empSkillRates.add(_skillRate);
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public boolean isShow() {
		if (viewEmployee == true)
			return true;
		else
			return false;
	}

	public boolean isViewEmployee() {
		return viewEmployee;
	}

	public void setViewEmployee(boolean viewEmployee) {
		this.viewEmployee = viewEmployee;
	}
}
