package mn.mcs.electronics.court.pages.employee;

import java.text.SimpleDateFormat;

import mn.mcs.electronics.court.components.CompEmp;
import mn.mcs.electronics.court.components.LayoutEmployee;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.ResultContract;
import mn.mcs.electronics.court.enums.Score;
import mn.mcs.electronics.court.util.Constants;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.EnumSelectModel;

public class PageEmployeeNowResult {

	@Inject
	private CoreDAO dao;

	@Inject
	private Messages messages;

	@InjectComponent
	private LayoutEmployee layoutEmployee;

	@Persist
	private Employee employee;

	/*@InjectPage
	private CompEmp pageEmployee;*/

	// @Property
	@Persist
	private ResultContract result;

	@Persist
	private boolean viewEmployee;

	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);

	private Long empID;

	void onActivate(Long id) {
		empID = id;
	}

	Long onPassivate() {
		return empID;
	}

	void beginRender() {

		viewEmployee = true;

		this.employee = (Employee) dao.getObject(Employee.class, empID);

		layoutEmployee.setValueEmployee(employee);

		/*
		 * this.employee = pageEmployee.getEmployee();
		 * 
		 * layoutEmployee.setValueEmployee(getEmployee());
		 */

		if (result == null)
			result = new ResultContract();
	}

	@CommitAfter
	void onSelectedFromSaveResultContract() {
		result.setEmployee(employee);
		dao.saveOrUpdateObject(result);
		result = new ResultContract();
	}

	void onActionFromCancel() {
		result = new ResultContract();
	}

	/* getter,setter */

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getDateFormat() {
		return Constants.DATE_FORMAT;
	}

	public ResultContract getResult() {
		return result;
	}

	public void setResult(ResultContract result) {
		this.result = result;
	}

	/* selection model */
	public SelectModel getLanguageLevelSelectionModel() {
		return new EnumSelectModel(Score.class, messages);
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
