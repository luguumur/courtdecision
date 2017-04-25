package mn.mcs.electronics.court.pages.configuration;

import java.text.SimpleDateFormat;
import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.UserLoginHistory;
import mn.mcs.electronics.court.model.OrganizationSelectionModel;
import mn.mcs.electronics.court.model.UserSelectionModel;
import mn.mcs.electronics.court.util.Constants;
import mn.mcs.electronics.court.util.beans.UserLoginHistorySearchBean;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;

public class PageUserLoginHistory {

	@Inject
	private CoreDAO dao;

	@Inject
	private Messages messages;

	@SessionState
	private LoginState loginState;

	@Inject
	private Response response;

	@Persist
	@Property
	private List<UserLoginHistory> list;

	@Property
	private UserLoginHistory _rowHistory;

	@Persist
	@Property
	private UserLoginHistorySearchBean bean;

	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_TIME_FORMAT);

	void beginRender() {

		if (bean == null)
			bean = new UserLoginHistorySearchBean();

		list = dao.getUserLoginHistory(bean);
	}

	void onSelectedFromSearch() {

	}

	public String getSystemName() {
		if (_rowHistory != null || _rowHistory.getUser() != null)
			return _rowHistory.getUser().getUsername();
		return "-";
	}

	public String getOrganization() {
		if (_rowHistory != null
				&& _rowHistory.getUser() != null
				&& _rowHistory.getUser().getEmployee() != null
				&& _rowHistory.getUser().getEmployee().getOrganization() != null)
			return _rowHistory.getUser().getEmployee().getOrganization()
					.getName();
		return "-";
	}

	public String getName() {
		if (_rowHistory != null && _rowHistory.getUser() != null
				&& _rowHistory.getUser().getEmployee() != null)
			return _rowHistory.getUser().getEmployee().getFullNameFirstChar();
		return "-";
	}

	public String getLoginDate() {
		if (_rowHistory == null || _rowHistory.getLoginDate() == null)
			return "";
		return format.format(_rowHistory.getLoginDate());
	}

	public SelectModel getOrganizationSelectionModel() {
		return new OrganizationSelectionModel(dao);
	}

	public SelectModel getUserSelectionModel() {
		return new UserSelectionModel(dao);
	}

}
