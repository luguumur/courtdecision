package mn.mcs.electronics.court.pages;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.other.Notice;
import mn.mcs.electronics.court.util.Constants;

import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Notification {

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
	private AlertManager manager;

	/*
	 * PERSISTS
	 */

	@Persist
	@Property
	private Notice notice;

	/*
	 * PROPERTIES
	 */

	@Property
	private List<Notice> listNotice;

	@Property
	private Notice valueNotice;

	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);

	void beginRender() {

		if (notice == null)
			notice = new Notice();

		listNotice = dao.getListNotice();
	}

	@CommitAfter
	void onSelectedFromSave() {
		if (notice != null && notice.getCreatedDate() == null)
			notice.setCreatedDate(new Date());
		notice.setCreatedEmp(loginState.getEmployee());

		dao.saveOrUpdateObject(notice);
		notice = new Notice();
	}

	@CommitAfter
	void onSelectedFromDelete(Notice not) {
		dao.deleteObject(not);
	}

	void onActionFromEdit(Notice not) {
		this.notice = not;
	}

	void onActionFromCancel() {
		notice = new Notice();
	}

	/*
	 * METHODS
	 */

	public String getCreatedDate() {
		if (valueNotice == null || valueNotice.getCreatedDate() == null)
			return "";
		return format.format(valueNotice.getCreatedDate());
	}

	public String getEmp() {
		if (valueNotice == null || valueNotice.getCreatedEmp() == null
				|| valueNotice.getCreatedEmp().getFirstName() == null)
			return "";
		return valueNotice.getCreatedEmp().getFirstName();
	}

	public String getNumber() {
		return listNotice.indexOf(valueNotice) + 1 + "";
	}
}