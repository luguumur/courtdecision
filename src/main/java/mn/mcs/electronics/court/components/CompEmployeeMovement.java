package mn.mcs.electronics.court.components;

import java.text.SimpleDateFormat;
import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.QuitJob;
import mn.mcs.electronics.court.enums.EmployeeCurrentStatus;
import mn.mcs.electronics.court.enums.EmployeeStatus;
import mn.mcs.electronics.court.model.QuitJobCauseSelectionModel;
import mn.mcs.electronics.court.util.Constants;

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

public class CompEmployeeMovement {

	@Inject
	private Messages messages;

	@Inject
	private CoreDAO dao;

	@SessionState
	private LoginState loginState;

	@Persist
	private Employee employee;

	@Property
	@Persist
	private QuitJob quitJob;

	@Property
	@Persist
	private EmployeeCurrentStatus quitJobType;

	@Persist
	private boolean viewEmployee;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@Inject
	private Request request;

	@InjectComponent
	private Zone quitJobZone, causeZone, quitJobListZone;

	@Property
	private List<QuitJob> listQuitJob;

	@Property
	@Persist
	private QuitJob valueQuitJob;

	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);

	void beginRender() {
		viewEmployee = true;

		if (quitJob == null)
			quitJob = new QuitJob();

		if (employee != null && employee.getId() != null) {
			listQuitJob = dao.getQuitJob(employee);
		}

	}

	@CommitAfter
	void onSelectedFromSaveQuitJob() {
		quitJob.setEmployee(employee);
		quitJob.setQuitType(quitJobType);
		dao.saveOrUpdateObject(quitJob);
		employee.setEmployeeCurrentStatus(quitJobType);
		employee.setEmployeeStatus(EmployeeStatus.TIRED);
		dao.saveOrUpdateObject(employee);
		listQuitJob = dao.getQuitJob(employee);
		ajaxResponseRenderer.addRender("quitJobZone", quitJobZone).addRender(
				"quitJobListZone", quitJobListZone);
	}

	void onActionFromDeleteQuitJob(QuitJob quitJob) {
//		if (request.isXHR()) {
			dao.deleteObject(quitJob);
			listQuitJob = dao.getQuitJob(employee);
//			ajaxResponseRenderer.addRender("quitJobListZone", quitJobListZone);
//		}
	}

	public void onActionFromEditQuitJob(QuitJob quitJob) {
		if (request.isXHR()) {
			this.quitJob = quitJob;
			quitJobType = quitJob.getQuitType();
			ajaxResponseRenderer.addRender("quitJobZone", quitJobZone);
		}
	}

	/* Ajax zone */

	void onValueChangedFromQuitJobtype() {
		ajaxResponseRenderer.addRender("causeZone", causeZone);
	}

	/* selection model */

	public SelectModel getQuitTypeSelectionModel() {
		return new EnumSelectModel(EmployeeCurrentStatus.class, messages);
	}

	public SelectModel getQuitJobCauseSelectionModel() {
		if (quitJobType == null) {
			quitJobType = EmployeeCurrentStatus.CHULUULUGDUKH;
		}
		QuitJobCauseSelectionModel sm = new QuitJobCauseSelectionModel(dao,
				quitJobType);

		return sm;
	}

	/* getter, setter */

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

	public String getNumberQ() {
		return listQuitJob.indexOf(valueQuitJob) + 1 + "";
	}

	public String getQuitDate() {
		if (valueQuitJob == null || valueQuitJob.getQuitDate() == null)
			return "";
		return format.format(valueQuitJob.getQuitDate());
	}

}
