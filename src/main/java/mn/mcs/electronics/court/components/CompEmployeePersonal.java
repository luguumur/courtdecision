package mn.mcs.electronics.court.components;

import java.util.HashSet;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.Role;
import mn.mcs.electronics.court.entities.User;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.FamilyStatus;
import mn.mcs.electronics.court.enums.Gender;
import mn.mcs.electronics.court.model.AscriptionSelectionModel;
import mn.mcs.electronics.court.model.CityProvinceSelectionModel;
import mn.mcs.electronics.court.model.CitySelectionModel;
import mn.mcs.electronics.court.model.CountrySelectionModel;
import mn.mcs.electronics.court.model.DistrictSumSelectionModel;
import mn.mcs.electronics.court.model.OccupationSelectionModel;
import mn.mcs.electronics.court.model.OrganizationSelectionModel;
import mn.mcs.electronics.court.model.OriginSelectionModel;
import mn.mcs.electronics.court.model.RelativeTypeSelectionModel;
import mn.mcs.electronics.court.model.RoleSelectionModel;
import mn.mcs.electronics.court.pages.Home;
import mn.mcs.electronics.court.util.UserUtil;

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
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.util.EnumSelectModel;

public class CompEmployeePersonal {

	@SessionState
	private LoginState loginState;;

	/*
	 * INJECTS
	 */

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
	private Zone formZone, aimagZone, aimagHotZone, countryZone;

	@Inject
	private AlertManager manager;

	/*
	 * PERSISTS
	 */

	@Persist
	private Employee employee;

	@Property
	@Persist
	private Role empRole;

	@Persist
	private HashSet<Organization> orgs;

	@Persist
	private Boolean orgLoaded;

	void beginRender() {
		if (employee == null)
			employee = new Employee();

		if (employee.getBirthCityProvince() != null)
			employee.setBirthCountry(dao.getCountryByName("Монгол"));

		// empRole = dao.getUser(employee).getRoles();
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

	/*
	 * EVENTS
	 */

	@CommitAfter
	void onSelectedFromSave() {

		if (request.isXHR()) {
			dao.saveOrUpdateObject(employee);

			if (employee.getSystemUser() != null) {
				if (employee.getSystemUser()) {
					User user = dao.getUser(employee);

					if (user == null) {
						user = new User();
						user.setEmployee(employee);
						user.setUsername(UserUtil.getUser(dao, employee));
						user.setAccountLocked(false);
					}
					if (empRole != null) {
						user.setRoles(empRole);
						dao.saveOrUpdateObject(user);
					}
				}
			}

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));

			ajaxResponseRenderer.addRender(formZone);
		}
	}

	Object onValueChangedFromAimagClick() {
		return request.isXHR() ? aimagZone.getBody() : null;
	}

	Object onValueChangedFromAimagHotClick() {
		return request.isXHR() ? aimagHotZone.getBody() : null;
	}

	void onValueChangedFromBirthCountry() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(countryZone);
		}
	}

	/*
	 * METHODS
	 */
	/*
	 * public String getEducation() { Educations currentEducation =
	 * dao.getCurrentEducation(employee.getId()); if (currentEducation != null
	 * && currentEducation.getDegreeType() != null) return
	 * currentEducation.getDegreeType().getName(); return "-"; }
	 */

	public String getGender() {
		if (employee != null && employee.getRegisterNo() != null) {
			if (Integer.parseInt(employee.getRegisterNo().substring(9, 10)) % 2 == 1) {
				return "Эрэгтэй";
			} else {
				return "Эмэгтэй";
			}
		}
		return "-";
	}

	public String getOccupation() {
		return dao.getOccupation(employee.getId());
	}

	public boolean isMongolianProvince() {

		/*if (employee.getBirthCityProvince() != null)
			return true;*/

		if (employee.getBirthCountry() != null
				&& employee.getBirthCountry().getIsMongolia())
			return true;

		return false;
	}

	/*
	 * SELECT MODELS
	 */

	public SelectModel getOccupationSelectionModel() {
		return new OccupationSelectionModel(dao, null);
	}

	public SelectModel getCityProvinceSelectionModel() {
		return new CityProvinceSelectionModel(dao);
	}

	public SelectModel getCitySelectionModel() {
		return new CitySelectionModel(dao, employee.getBirthCountry());
	}

	public SelectModel getDistrictSumSelectionModel() {
		return new DistrictSumSelectionModel(dao,
				employee.getBirthCityProvince());
	}

	public SelectModel getOriginSelectionModel() {
		return new OriginSelectionModel(dao);
	}

	public SelectModel getAscriptionSelectionModel() {
		return new AscriptionSelectionModel(dao);
	}

	public SelectModel getDistrictSumSelectionModel2() {
		return new DistrictSumSelectionModel(dao, employee.getAddCity());
	}

	public SelectModel getCountrySelectionModel() {
		return new CountrySelectionModel(dao);
	}

	public SelectModel getRelativeTypeSelectionModel() {
		return new RelativeTypeSelectionModel(dao);
	}

	public SelectModel getRoleSelectionModel() {
		return new RoleSelectionModel(dao);
	}

	public SelectModel getFamilyStatusSelectionModel() {
		return new EnumSelectModel(FamilyStatus.class, messages);
	}

	public SelectModel getGenderSelectionModel() {
		return new EnumSelectModel(Gender.class, messages);
	}

	public SelectModel getOrganizationSelectionModel() {
		if (orgLoaded == null || orgLoaded == false) {
			orgs = new HashSet<Organization>(loginState.getEmployee()
					.getMapEmpOrg());

			if (orgs.isEmpty())
				orgs.add(loginState.getOrganization());

			orgLoaded = true;
		}

		return new OrganizationSelectionModel(dao);
	}
}
