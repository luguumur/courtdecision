/* 
 * File Name: CommonDAOHibernate.java
 * 
 * Created By: T.Maralerdene
 * Created Date: 2011/03/21
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * 2011/03/23 1.0.0 			T.Maralerdene				Шинээр үүсгэв.
 * 
 * -----------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2011 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */
package mn.mcs.electronics.court.dao.hibernate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.validator.xml.PropertyType;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.Permission;
import mn.mcs.electronics.court.entities.Role;
import mn.mcs.electronics.court.entities.User;
import mn.mcs.electronics.court.entities.configuration.AcademicRank;
import mn.mcs.electronics.court.entities.configuration.Appointment;
import mn.mcs.electronics.court.entities.configuration.AppointmentSort;
import mn.mcs.electronics.court.entities.configuration.ApprovedPositions;
import mn.mcs.electronics.court.entities.configuration.Ascription;
import mn.mcs.electronics.court.entities.configuration.Award;
import mn.mcs.electronics.court.entities.configuration.City;
import mn.mcs.electronics.court.entities.configuration.CityProvince;
import mn.mcs.electronics.court.entities.configuration.CommandSubject;
import mn.mcs.electronics.court.entities.configuration.ComputerProgram;
import mn.mcs.electronics.court.entities.configuration.Country;
import mn.mcs.electronics.court.entities.configuration.DegreeType;
import mn.mcs.electronics.court.entities.configuration.DegreeTypeOfEducationDoctor;
import mn.mcs.electronics.court.entities.configuration.DegreeTypeOfScienceDoctor;
import mn.mcs.electronics.court.entities.configuration.DisplacementCause;
import mn.mcs.electronics.court.entities.configuration.DistrictSum;
import mn.mcs.electronics.court.entities.configuration.EducationDegree;
import mn.mcs.electronics.court.entities.configuration.Facility;
import mn.mcs.electronics.court.entities.configuration.FamilyAppointmentType;
import mn.mcs.electronics.court.entities.configuration.ForeignLanguage;
import mn.mcs.electronics.court.entities.configuration.LandIntention;
import mn.mcs.electronics.court.entities.configuration.MilitaryRank;
import mn.mcs.electronics.court.entities.configuration.Occupation;
import mn.mcs.electronics.court.entities.configuration.OccupationRank;
import mn.mcs.electronics.court.entities.configuration.OccupationRole;
import mn.mcs.electronics.court.entities.configuration.OccupationType;
import mn.mcs.electronics.court.entities.configuration.Origin;
import mn.mcs.electronics.court.entities.configuration.ProjectConfig;
import mn.mcs.electronics.court.entities.configuration.ProjectMenuConfig;
import mn.mcs.electronics.court.entities.configuration.QuitJobCause;
import mn.mcs.electronics.court.entities.configuration.RelativeType;
import mn.mcs.electronics.court.entities.configuration.RetireAge;
import mn.mcs.electronics.court.entities.configuration.SalaryNetwork;
import mn.mcs.electronics.court.entities.configuration.SalaryScale;
import mn.mcs.electronics.court.entities.configuration.School;
import mn.mcs.electronics.court.entities.configuration.SkillGroup;
import mn.mcs.electronics.court.entities.configuration.SkillType;
import mn.mcs.electronics.court.entities.configuration.TravelType;
import mn.mcs.electronics.court.entities.configuration.TuAaLevel;
import mn.mcs.electronics.court.entities.configuration.TuAaSort;
import mn.mcs.electronics.court.entities.configuration.UserIP;
import mn.mcs.electronics.court.entities.configuration.UserLoginHistory;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuLevel;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuSort;
import mn.mcs.electronics.court.entities.configuration.WorkedYear;
import mn.mcs.electronics.court.entities.configuration.salary.AdditionalSalaryType;
import mn.mcs.electronics.court.entities.configuration.salary.TuriinZahirgaaSalaryNetwork;
import mn.mcs.electronics.court.entities.employee.Addition;
import mn.mcs.electronics.court.entities.employee.AdditionalSalary;
import mn.mcs.electronics.court.entities.employee.Allowance;
import mn.mcs.electronics.court.entities.employee.AllowanceType;
import mn.mcs.electronics.court.entities.employee.Computer;
import mn.mcs.electronics.court.entities.employee.ComputerOther;
import mn.mcs.electronics.court.entities.employee.DegreeGrade;
import mn.mcs.electronics.court.entities.employee.DegreeGradeRank;
import mn.mcs.electronics.court.entities.employee.Degrees;
import mn.mcs.electronics.court.entities.employee.Demerit;
import mn.mcs.electronics.court.entities.employee.Displacement;
import mn.mcs.electronics.court.entities.employee.Educations;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.EmployeeMilitary;
import mn.mcs.electronics.court.entities.employee.EmployeeSalaryHistory;
import mn.mcs.electronics.court.entities.employee.Establishment;
import mn.mcs.electronics.court.entities.employee.Experience;
import mn.mcs.electronics.court.entities.employee.Honour;
import mn.mcs.electronics.court.entities.employee.Language;
import mn.mcs.electronics.court.entities.employee.OfficeEquipment;
import mn.mcs.electronics.court.entities.employee.Passport;
import mn.mcs.electronics.court.entities.employee.QuitJob;
import mn.mcs.electronics.court.entities.employee.Relatives;
import mn.mcs.electronics.court.entities.employee.ResultContract;
import mn.mcs.electronics.court.entities.employee.Sahilga;
import mn.mcs.electronics.court.entities.employee.SahilgaShiitgel;
import mn.mcs.electronics.court.entities.employee.SahilgaTurul;
import mn.mcs.electronics.court.entities.employee.SalaryHistory;
import mn.mcs.electronics.court.entities.employee.Skills;
import mn.mcs.electronics.court.entities.employee.Training;
import mn.mcs.electronics.court.entities.employee.TravelAbroad;
import mn.mcs.electronics.court.entities.employee.Vacation;
import mn.mcs.electronics.court.entities.employee.VacationType;
import mn.mcs.electronics.court.entities.map.MapRolePermission;
import mn.mcs.electronics.court.entities.organization.AppurtenanceLead;
import mn.mcs.electronics.court.entities.organization.AppurtenanceLocation;
import mn.mcs.electronics.court.entities.organization.Department;
import mn.mcs.electronics.court.entities.organization.FinancingType;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.entities.organization.OrganizationType;
import mn.mcs.electronics.court.entities.organization.Saving;
import mn.mcs.electronics.court.entities.organization.SubDepartment;
import mn.mcs.electronics.court.entities.other.AES;
import mn.mcs.electronics.court.entities.other.LoginHistory;
import mn.mcs.electronics.court.entities.other.Notice;
import mn.mcs.electronics.court.entities.other.OpenPosition;
import mn.mcs.electronics.court.entities.other.OpenPositionList;
import mn.mcs.electronics.court.enums.AdditionalSalaryCategory;
import mn.mcs.electronics.court.enums.AppointmentLevel;
import mn.mcs.electronics.court.enums.AppointmentSortEmployee;
import mn.mcs.electronics.court.enums.AppointmentType;
import mn.mcs.electronics.court.enums.AwardType;
import mn.mcs.electronics.court.enums.DisplacementCauseType;
import mn.mcs.electronics.court.enums.DisplacementType;
import mn.mcs.electronics.court.enums.EmployeeCurrentStatus;
import mn.mcs.electronics.court.enums.EmployeeStatus;
import mn.mcs.electronics.court.enums.Gender;
import mn.mcs.electronics.court.enums.MilitaryOrSimple;
import mn.mcs.electronics.court.enums.MilitaryRankType;
import mn.mcs.electronics.court.enums.OrganizationGroup;
import mn.mcs.electronics.court.enums.OrganizationTypeExperience;
import mn.mcs.electronics.court.enums.QuitJobCauseName;
import mn.mcs.electronics.court.enums.RelativeFamily;
import mn.mcs.electronics.court.enums.SahilgaSubject;
import mn.mcs.electronics.court.enums.SchoolType;
import mn.mcs.electronics.court.enums.UniversityType;
import mn.mcs.electronics.court.enums.YesNo;
import mn.mcs.electronics.court.util.CalendarUtil;
import mn.mcs.electronics.court.util.beans.AlertEmployeeSalaryBean;
import mn.mcs.electronics.court.util.beans.AlertMilitaryRankEmployeeBean;
import mn.mcs.electronics.court.util.beans.AlertUdaanJilEmployeeBean;
import mn.mcs.electronics.court.util.beans.DisciplinedEmployeeBean;
import mn.mcs.electronics.court.util.beans.EmployeeCardDegreeBean;
import mn.mcs.electronics.court.util.beans.EmployeeCardDemeritBean;
import mn.mcs.electronics.court.util.beans.EmployeeCardEducationBean;
import mn.mcs.electronics.court.util.beans.EmployeeCardExperienceBean;
import mn.mcs.electronics.court.util.beans.EmployeeCardOtherAwardBean;
import mn.mcs.electronics.court.util.beans.EmployeeCardSalaryBean;
import mn.mcs.electronics.court.util.beans.EmployeeCardStateAwardBean;
import mn.mcs.electronics.court.util.beans.EmployeeLastDemeritBean;
import mn.mcs.electronics.court.util.beans.EmployeeSearchBean;
import mn.mcs.electronics.court.util.beans.EstReportBean;
import mn.mcs.electronics.court.util.beans.EstablishmentSearchBean;
import mn.mcs.electronics.court.util.beans.GeneralReportBean;
import mn.mcs.electronics.court.util.beans.GridPager;
import mn.mcs.electronics.court.util.beans.OpenPositionListSearchBean;
import mn.mcs.electronics.court.util.beans.OpenPositionSearchBean;
import mn.mcs.electronics.court.util.beans.OrganizationSearchBean;
import mn.mcs.electronics.court.util.beans.ShagnuulahEmployeeBean;
import mn.mcs.electronics.court.util.beans.TetgevriinEmployeeBean;
import mn.mcs.electronics.court.util.beans.UserLoginHistorySearchBean;

/**
 * @author root
 * 
 */

public class CoreDAOHibernate implements CoreDAO {

	private Session session;

	public CoreDAOHibernate(Session session) {
		this.session = session;
	}

	public void saveObject(Object obj) {
		session.save(obj);
	}

	public void updateObject(Object obj) {
		session.saveOrUpdate(obj);
	}

	public void saveOrUpdateObject(Object obj) {
		session.saveOrUpdate(obj);
	}

	public void deleteObject(Object obj) {
		session.delete(obj);
	}

	public Object mergeObject(Object obj) {
		return session.merge(obj);
	}

	public Object getObject(Class clazz, Long id) {

		return session.get(clazz, id);
	}

	/* Jul 17, 2012 T.Maralerdene begin */
	public Employee getEmployee(String username) {
		Criteria crit = session.createCriteria(User.class);

		crit.add(Restrictions.eq("username", username));

		if (crit.list().isEmpty())
			return null;

		User user = (User) crit.list().get(0);

		return user.getEmployee();
	}

	/* Jul 17, 2012 T.Maralerdene end */
	/*
	 * Role тохиргоо
	 */

	public List<Role> getRoleList() {

		Criteria crit = session.createCriteria(Role.class);

		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<Role> list = crit.list();

		return list;
	}

	/*
	 * Permission тохиргоо
	 */
	public List<Permission> getPermissionList() {
		Criteria crit = session.createCriteria(Permission.class);

		crit.add(Restrictions.eq("isShow", true));
		crit.add(Restrictions.eq("isComponent", false));

		crit.addOrder(Order.asc("permissionName"));

		List<Permission> list = crit.list();

		return list;
	}

	/*
	 * Role тус бүрт хамаарах Permission-г авчирна.
	 */
	public List<Permission> getPermissionList(Role role) {

		String sql = "SELECT p FROM MapRolePermission p WHERE p.role = :role ORDER BY p.permission.permissionName";

		Query q = session.createQuery(sql);

		q.setParameter("role", role);

		List<MapRolePermission> list = q.list();

		List<Permission> lst = new ArrayList<Permission>();

		for (MapRolePermission p : list)
			lst.add(p.getPermission());

		return lst;
	}

	/*
	 * OrganizationType-г select хийнэ. Parameter OrganizationGroup
	 */
	/*
	 * public List<OrganizationType> getListOrganizationType(OrganizationGroup
	 * group) { Criteria crit = session.createCriteria(OrganizationType.class);
	 * List<OrganizationType> list = crit.list(); return list; }
	 */

	/*
	 * Organization-г select хийнэ. Parameter OrganizationType
	 */
	public List<Organization> getListOrganization() {

		Criteria crit = session.createCriteria(Organization.class);

		crit.addOrder(Order.asc("name"));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Organization> list = crit.list();
		return list;
	}

	/*
	 * Бүх улсын жагсаалт гаргана.
	 */
	public List<Country> getListCountry() {
		Criteria crit = session.createCriteria(Country.class);
		crit.addOrder(Order.asc("countryName"));
		List<Country> list = crit.list();

		return list;
	}

	/*
	 * RelativeType selection model
	 */
	public List<RelativeType> getRelativeType() {
		Criteria crit = session.createCriteria(RelativeType.class);

		List<RelativeType> list = crit.list();

		return list;
	}

	/*
	 * Гэр бүлийн мэдээллийг employee-р select хийнэ.
	 */
	public List<Relatives> getRelatives(Long empID, RelativeFamily type) {
		Criteria crit = session.createCriteria(Relatives.class);
		crit.add(Restrictions.eq("employee.id", empID));
		crit.add(Restrictions.eq("relativeType", type));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Relatives> list = crit.list();

		return list;
	}

	/*
	 * Сургалтын мэдээллийг employee-р select хийнэ.
	 */
	public List<Training> getTraining(Employee emp) {
		Criteria crit = session.createCriteria(Training.class);
		crit.add(Restrictions.eq("employee", emp));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Training> list = crit.list();

		return list;
	}

	/*
	 * Гадаад хэлний мэдээллийг employee-р select хийнэ.
	 */
	public List<Language> getLanguage(Employee emp) {
		Criteria crit = session.createCriteria(Language.class);
		crit.add(Restrictions.eq("employee", emp));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Language> list = crit.list();

		return list;
	}

	/*
	 * Компьютерийн мэдлэгийн мэдээллийг employee-р select хийнэ.
	 */
	public List<Computer> getComputer(Employee emp) {
		Criteria crit = session.createCriteria(Computer.class);
		crit.add(Restrictions.eq("employee", emp));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Computer> list = crit.list();

		return list;
	}

	/*
	 * Хотын жагсаалт буцаана.
	 */
	public List<CityProvince> getListCityProvince(Country country) {

		Criteria crit = session.createCriteria(CityProvince.class);

		if (country != null)
			crit.add(Restrictions.eq("country", country));

		crit.addOrder(Order.asc("cityProvinceName"));
		List<CityProvince> list = crit.list();
		return list;
	}

	/*
	 * Аймаг хотын жагсаалт буцаана.
	 */
	public List<DistrictSum> getListDistrictSum(City city) {
		Criteria crit = session.createCriteria(DistrictSum.class);

		if (city != null)
			crit.add(Restrictions.eq("city", city));

		crit.addOrder(Order.asc("city"));
		crit.addOrder(Order.asc("districtSumName"));

		List<DistrictSum> list = crit.list();
		return list;
	}

	/*
	 * Сургуулийн жагсаалт буцаана.
	 */
	public List<School> getListSchool(UniversityType type) {
		Criteria crit = session.createCriteria(School.class);

		if (type != null)
			crit.add(Restrictions.eq("universityType", type));

		return crit.list();
	}

	public List<School> getListSchool() {
		Criteria crit = session.createCriteria(School.class);
		return crit.list();
	}

	/*
	 * DegreeType буцаана.
	 */
	public List<DegreeType> getListDegreeType() {
		Criteria crit = session.createCriteria(DegreeType.class);

		crit.addOrder(Order.asc("id"));
		List<DegreeType> list = crit.list();

		return list;
	}

	public List<DegreeTypeOfScienceDoctor> getListScienceDoctor() {

		Criteria crit = session.createCriteria(DegreeTypeOfScienceDoctor.class);

		List<DegreeTypeOfScienceDoctor> list = crit.list();

		return list;
	}

	public List<DegreeTypeOfEducationDoctor> getListEducationDoctor() {

		Criteria crit = session.createCriteria(DegreeTypeOfEducationDoctor.class);

		List<DegreeTypeOfEducationDoctor> list = crit.list();

		return list;
	}

	/*
	 * Мэргэжил list буцаана.
	 */
	public List<Occupation> getListOccupation(SchoolType type) {
		Criteria crit = session.createCriteria(Occupation.class);

		if (type != null)
			crit.add(Restrictions.eq("schoolType", type));

		return crit.list();
	}

	/*
	 * Ажилтны боловсролын мэдээллийг буцаана.
	 */
	public List<Educations> getListEducation(Employee emp) {
		Criteria crit = session.createCriteria(Educations.class);
		crit.add(Restrictions.eq("employee", emp));
		crit.addOrder(Order.asc("ingoingDate"));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Educations> list = crit.list();

		if (list.isEmpty())
			return null;

		return list;
	}

	/*
	 * Ажилтын эрдмийн зэргийн жагсаалтыг буцаана.
	 */
	public List<Degrees> getListDegrees(Employee emp) {
		Criteria crit = session.createCriteria(Degrees.class);
		crit.add(Restrictions.eq("employee", emp));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Degrees> list = crit.list();
		return list;
	}

	public List<Skills> getListSkills(Employee emp) {
		Criteria crit = session.createCriteria(Skills.class);
		crit.add(Restrictions.eq("employee", emp));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Skills> list = crit.list();
		return list;
	}

	/*
	 * Ажилтны шагнал урамшууллын мэдээллийг буцаана.
	 */
	public List<Honour> getListHonour(Employee emp) {
		Criteria crit = session.createCriteria(Honour.class);
		crit.add(Restrictions.eq("employee", emp));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Honour> list = crit.list();
		return list;
	}

	public List<Honour> getListStateHonourEmpCard(Employee emp) {
		Criteria crit = session.createCriteria(Honour.class);
		crit.add(Restrictions.eq("employee", emp));
		crit.add(Restrictions.eq("awardType", AwardType.GOVERNMENTPRIZE));
		crit.add(Restrictions.eq("awardType", AwardType.STATEPRIZE));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Honour> list = crit.list();
		return list;
	}

	public List<Honour> getListOtherHonourEmpCard(Employee emp) {
		Criteria crit = session.createCriteria(Honour.class);
		crit.add(Restrictions.eq("employee", emp));
		crit.add(Restrictions.eq("awardType", AwardType.COURTPRIZE));
		crit.add(Restrictions.eq("awardType", AwardType.JUSTICE_MINISTRYPRIZE));
		crit.add(Restrictions.eq("awardType", AwardType.OTHER_ORGANIZATIONPRIZE));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Honour> list = crit.list();
		return list;
	}

	/*
	 * Ур чадварын группын жагсаалт буцаана.
	 */
	public List<SkillGroup> getListSkillGroup() {
		Criteria crit = session.createCriteria(SkillGroup.class);
		List<SkillGroup> list = crit.list();

		return list;
	}

	/*
	 * Ур чадварын жагсаалт буцна.
	 */
	public List<SkillType> getListSkillType(SkillGroup skill) {
		Criteria crit = session.createCriteria(SkillType.class);

		if (skill != null)
			crit.add(Restrictions.eq("skillGroup", skill));
		List<SkillType> list = crit.list();

		return list;
	}

	/*
	 * Туршлагын мэдээллийг employee-р select хийнэ.
	 */
	public List<Experience> getExperience(Employee emp) {
		Criteria crit = session.createCriteria(Experience.class);

		crit.add(Restrictions.eq("employee", emp));

		crit.addOrder(Order.desc("startDate"));

		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<Experience> list = crit.list();

		return list;
	}

	/*
	 * Өмнөх үр дүнгийн гэрээний биелэлтийг employee-р select хийнэ.
	 */
	public List<ResultContract> getResultContract(Employee emp) {

		Criteria crit = session.createCriteria(ResultContract.class);
		crit.add(Restrictions.eq("employee", emp));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<ResultContract> list = crit.list();
		return list;

	}

	/*
	 * PropertyType selection model
	 */
	public List<PropertyType> getPropertyType() {
		Criteria crit = session.createCriteria(PropertyType.class);

		List<PropertyType> list = crit.list();

		return list;
	}

	/*
	 * Ажилтныг хайх
	 */

	public List<Employee> getListEmployeeSearch() {
		Criteria crit = session.createCriteria(Employee.class);
		return crit.list();
	}

	public List<Employee> getListEmployeeSearch(Long appointmentID) {
		String sql = "SELECT e FROM Employee e  where e.appointment=" + appointmentID;

		Query q = session.createQuery(sql);
		// q.setParameter("appointmentID", appointmentID);

		return q.list();

	}

	public List<Establishment> getListEstablishmentSearch() {
		Criteria crit = session.createCriteria(Establishment.class);
		return crit.list();
	}

	public List<Employee> getListEmployeeSearch(EmployeeSearchBean bean, RetireAge ra, HashSet<Organization> orgs,
			GridPager pager) {

		String sql = "";
		String where = "";

		if (orgs != null)
			where += " s.organization IN(:orgs) AND ";

		if (bean != null && (bean.getEducationDegree() != null || bean.getOccupation() != null
				|| bean.getEduStartDateFrom() != null || bean.getEduStartDateTo() != null
				|| bean.getEduGraduateDateFrom() != null || bean.getEduGraduateDateTo() != null)) {
			sql = sql + " LEFT JOIN s.educations e ";

			if (bean.getEducationDegree() != null)
				where = where + " e.degreeType = :educationDegree AND ";

			if (bean.getOccupation() != null)
				where = where + " e.occupation = :occupation AND ";

			if (bean.getEduStartDateFrom() != null)
				where = where + "  e.ingoingDate >= :eduStartDateFrom AND";

			if (bean.getEduStartDateTo() != null)
				where = where + " e.ingoingDate <= :eduStartDateTo AND";

			if (bean.getEduGraduateDateFrom() != null)
				where = where + " e.graduatedDate >= :eduGraduateDateFrom AND";

			if (bean.getEduGraduateDateTo() != null)
				where = where + " e.graduatedDate <= :eduGraduateDateTo AND";
		}

		if (bean != null && bean.getFamilyStatus() != null) {
			where = where + " s.familyStatus = :familyStatus AND ";
		}

		if (bean != null && bean.getAgeFrom() != null && bean != null && bean.getAgeTo() != null) {
			where = where + " (DATEDIFF(CURDATE(),s.birthDate) >= (:ageFrom*365)) AND "
					+ " (floor(DATEDIFF(CURDATE(),s.birthDate)/365) <= (:ageTo)) AND ";
		} else if (bean != null && bean.getAgeFrom() != null) {
			where = where + " ABS((DATEDIFF(CURDATE(),s.birthDate)-(:ageFrom*365))) < 365 AND ";
		}

		if (bean != null && (bean.getAwardType() != null || bean.getAwardDateStart() != null
				|| bean.getAwardDateEnd() != null || bean.getIsAward() == YesNo.YES || bean.getIsAward() == YesNo.NO)) {
			sql = sql + " LEFT JOIN s.honour h ";

			if (bean.getIsAward() == YesNo.YES)
				where = where + " s.id IN (SELECT DISTINCT h1.employee FROM Honour h1) AND ";

			if (bean.getIsAward() == YesNo.NO)
				where = where + " s.id NOT IN (SELECT DISTINCT h1.employee FROM Honour h1) AND ";

			if (bean.getAwardType() != null) {
				where = where + " h.awardType = :awardType AND ";

				if (bean.getAward() != null)
					where = where + " h.otherPrize LIKE :award AND ";
			}

			if (bean.getAwardDateStart() != null)
				where = where + " h.dateOfAwarded >= :awardDateStart AND ";

			if (bean.getAwardDateEnd() != null)
				where = where + " h.dateOfAwarded <= :awardDateEnd AND ";
		}

		if (bean != null && (bean.getSahilga() != null || bean.getSahilgaCancelDateEnd() != null
				|| bean.getSahilgaCancelDateStart() != null || bean.getSahilgaDateEnd() != null
				|| bean.getSahilgaDateStart() != null || bean.getSahilgaTurul() != null
				|| bean.getIsDemerit() == YesNo.YES || bean.getIsDemerit() == YesNo.NO
				|| bean.getSahilgaTushaalDugaar() != null)) {
			sql = sql + " LEFT JOIN s.sahilga sa ";

			if (bean.getIsDemerit() == YesNo.YES)
				where = where + " s.id IN (SELECT DISTINCT h1.employee FROM Sahilga h1) AND ";

			if (bean.getIsDemerit() == YesNo.NO)
				where = where + " s.id NOT IN (SELECT DISTINCT h1.employee FROM Sahilga h1) AND ";

			if (bean.getSahilga() != null)
				where = where + " sa.sahilgaShiitgel = :sahilga AND ";

			if (bean.getCommandSubject() != null)
				where = where + "sa.commandSubject = :commandSubject AND";

			if (bean.getSahilgaDateStart() != null)
				where = where + " sa.shiitgelAvagdsanOgnoo >= :sahilgaDateStart AND";

			if (bean.getSahilgaDateEnd() != null)
				where = where + " sa.shiitgelAvagdsanOgnoo <= :sahilgaDateEnd AND";

			if (bean.getSahilgaCancelDateStart() != null)
				where = where + " sa.arilgasanTushaalOgnoo >= :sahilgaCancelDateStart AND";

			if (bean.getSahilgaCancelDateEnd() != null)
				where = where + " sa.arilgasanTushaalOgnoo <= :sahilgaCancelDateEnd AND";

			if (bean.getSahilgaTurul() != null)
				where = where + " sa.sahilgaTurul = :sahilgaTurul AND";

			if (bean.getSahilgaTushaalDugaar() != null)
				where = where + " sa.avagdsanShiitgelDugaar = :sahilgaTushaalDugaar AND";
		}

		if (bean != null && bean.getOrganization() != null)
			where = where + " s.organization = :org AND";

		if (bean != null && bean.getLastname() != null)
			where = where + " s.lastname = :lastname AND";

		if (bean != null && bean.getFirstname() != null)
			where = where + " s.firstName = :firstName AND";

		if (bean != null && bean.getRegisterNo() != null)
			where = where + " s.registerNo = :registerNo AND";

		if (bean != null && bean.getGender() != null)
			where = where + " s.gender = :gender AND";

		if (bean != null && bean.getCityProvince() != null)
			where = where + " s.addCityProvince = :addCityProvince AND";

		if (bean != null && bean.getDistrictSum() != null)
			where = where + " s.addDistrictSum = :addDistrictSum AND";

		if (bean != null && bean.getAppointment() != null)
			where = where + " s.appointment = :appointment AND";

		if (bean != null && bean.getStatus() != null)
			where = where + " s.employeeStatus = :employeeStatus AND";

		if (bean != null && bean.getCurrentStatus() != null)
			where = where + " s.employeeCurrentStatus = :employeeCurrentStatus AND";

		if (bean != null && (bean.getDisplacementType() == DisplacementType.AJLAASGARAH)) {
			sql = sql + " LEFT JOIN s.quitJob q ";

			if (bean.getDisplacementType() == DisplacementType.AJLAASGARAH && bean.getQuitJobType() == null)
				where = where + " q.employee = s.id AND";

			if (bean.getQuitJobType() != null)
				where = where + " q.quitCause = :quitCause AND";

			if (bean.getQuiteDateFrom() != null)
				where = where + " YEAR(q.quitDate) >= :quitDateFrom AND";

			if (bean.getQuiteDateTo() != null)
				where = where + " YEAR(q.quitDate) <= :quitDateTo AND";
		}

		if (bean != null && (bean.getIsAllowance() == YesNo.YES || bean.getAllowanceDateStart() != null
				|| bean.getAllowanceDateEnd() != null || bean.getAllowanceMoneyFrom() != null
				|| bean.getAllowanceMoneyTo() != null || bean.getIsAllowance() == YesNo.NO
				|| bean.getAllowanceCommandSubject() != null || bean.getAllowanceTushaalDugaar() != null
				|| bean.getAllowanceType() != null)) {
			sql = sql + " LEFT JOIN s.allowance ac ";

			if (bean.getIsAllowance() == YesNo.YES)
				where = where + " s.id IN (SELECT DISTINCT h1.employee FROM Allowance h1) AND ";

			if (bean.getIsAllowance() == YesNo.NO)
				where = where + " s.id NOT IN (SELECT DISTINCT h1.employee FROM Allowance h1) AND ";

			if (bean.getAllowanceDateStart() != null)
				where = where + " YEAR(ac.olgosonOgnoo) >= :allowanceDateStart AND ";

			if (bean.getAllowanceDateEnd() != null)
				where = where + " YEAR(ac.olgosonOgnoo) <= :allowanceDateEnd AND ";

			if (bean.getAllowanceMoneyFrom() != null)
				where = where + " ac.allowanceMoney >= :allowanceMoneyFrom AND ";

			if (bean.getAllowanceMoneyTo() != null)
				where = where + " ac.allowanceMoney <= :allowanceMoneyTo AND ";

			if (bean.getAllowanceCommandSubject() != null)
				where = where + " ac.commandSubject <= :allowanceCommandSubject AND ";

			if (bean.getAllowanceTushaalDugaar() != null)
				where = where + " ac.tushaalDugaar <= :allowanceTushaalDugaar AND ";

			if (bean.getAllowanceType() != null)
				where = where + " ac.allowanceType <= :allowanceType AND ";
		}

		if (bean != null && (bean.getEmployeeDegreeStatus() != null || bean.getMilitary() != null
				|| bean.getMilitaryType() != null || bean.getDegreeDateFrom() != null || bean.getDegreeDateTo() != null
				|| bean.getTsolTushaalDugaar() != null)) {
			sql = sql + " LEFT JOIN s.employeeMilitary em ";
			where = where
					+ " (em.employee, em.olgosonOgnoo) IN (SELECT em1.employee, MAX(em1.olgosonOgnoo) FROM EmployeeMilitary em1 GROUP BY em1.employee) AND ";

			if (bean.getMilitaryType() != null)
				where = where + " em.militaryType = :militaryType AND ";

			if (bean.getMilitary() != null)
				where = where + " em.military = :military AND ";

			if (bean.getEmployeeDegreeStatus() != null)
				where = where + " em.degreeStatus = :employeeDegreeStatus AND ";

			if (bean.getDegreeDateFrom() != null)
				where = where + " em.olgosonOgnoo >= :degreeDateFrom AND ";

			if (bean.getDegreeDateTo() != null)
				where = where + " em.olgosonOgnoo <= :degreeDateTo AND ";

			if (bean.getTsolTushaalDugaar() != null)
				where = where + " em.tushaalDugaar = :tsolTushaalDugaar AND ";
		}

		if (bean != null && bean.getDisplacementType() != null
				&& bean.getDisplacementType() == DisplacementType.SHILJIH) {
			sql = sql + " LEFT JOIN s.displacement d ";

			if (bean.getDisplacementType() == DisplacementType.SHILJIH && bean.getDisplacementCause() == null)
				where = where + " (d.displacementType = " + DisplacementCauseType.AJILSELGEH.getVal()
						+ " OR d.displacementType = " + DisplacementCauseType.SHINEERTOMILOGDOH.getVal()
						+ " OR d.displacementType = " + DisplacementCauseType.TUSHAALDEVSHIH.getVal()
						+ " OR d.displacementType = " + DisplacementCauseType.TUSHAALBUURAH.getVal() + ") AND ";

			if (bean.getDisplacementCause() != null)
				where = where + " d.displacementType = :displacementCause AND ";

			if (bean.getQuiteDateFrom() != null)
				where = where + " YEAR(d.issuedDate) >= :quiteDateFrom AND ";

			if (bean.getQuiteDateTo() != null)
				where = where + " YEAR(d.issuedDate) <= :quiteDateTo AND ";
		}

		if (bean != null && (bean.getVacationType() != null || bean.getChuluuEhlehOgnoo() != null
				|| bean.getChuluuDuusahOgnoo() != null) || bean.getChuluuDuusahOgnooJiremsen() != null) {
			sql = sql + " LEFT JOIN s.vacation v ";

			if (bean.getVacationType() != null)
				where = where + " v.vacationType = :vacationType AND ";

			if (bean.getChuluuEhlehOgnoo() != null)
				where = where + " v.chuluuEhlehOgnoo >= :chuluuEhlehOgnoo AND ";

			if (bean.getChuluuDuusahOgnoo() != null)
				where = where + " v.chuluuDuusahOgnoo <= :chuluuDuusahOgnoo AND ";

			if (bean.getChuluuDuusahOgnooJiremsen() != null)
				where = where + " v.chuluuDuusahOgnoo >= :chuluuDuusahOgnooJiremsen AND ";

			if (bean.getChuluuDuusahOgnooSurgalt() != null)
				where = where + " v.chuluuDuusahOgnoo >= :chuluuDuusahOgnooSurgalt AND ";
		}

		if (bean != null && (bean.getWorkedYearFrom() != null || bean.getWorkedYearTo() != null)) {
			sql = sql + " LEFT JOIN s.experience ex ";

			if (bean.getWorkedYearFrom() != null && bean.getWorkedYearTo() == null) {
				where = where + "(ex.employee, 1) IN" + "(SELECT ex1.employee, " + "(CASE WHEN" + "	  SUM((CASE "
						+ "     	   WHEN ex1.endDate IS NULL THEN datediff(CURDATE(),ex1.startDate)"
						+ "     ELSE 0 END)) + " + "     SUM((CASE "
						+ "     	   WHEN ex1.endDate IS NOT NULL THEN datediff(ex1.endDate,ex1.startDate)"
						+ "     ELSE 0 END)) >= (:workedYearFrom*365) THEN 1  "
						+ "ELSE 0 END) FROM Experience ex1 GROUP BY ex1.employee) AND";
			}

			if (bean.getWorkedYearFrom() == null && bean.getWorkedYearTo() != null) {
				where = where + "(ex.employee, 1) IN" + "(SELECT ex1.employee, " + "(CASE WHEN" + "	  SUM((CASE "
						+ "     	   WHEN ex1.endDate IS NULL THEN datediff(CURDATE(),ex1.startDate)"
						+ "     ELSE 0 END)) + " + "     SUM((CASE "
						+ "     	   WHEN ex1.endDate IS NOT NULL THEN datediff(ex1.endDate,ex1.startDate)"
						+ "     ELSE 0 END)) <= (:workedYearTo*365) THEN 1  "
						+ "ELSE 0 END) FROM Experience ex1  GROUP BY ex1.employee" + " ) AND";
			}

			if (bean.getWorkedYearFrom() != null && bean.getWorkedYearTo() != null) {
				where = where + "(ex.employee, 1) IN" + "(SELECT ex1.employee, " + "(CASE WHEN" + "	  SUM((CASE "
						+ "     	   WHEN ex1.endDate IS NULL THEN datediff(CURDATE(),ex1.startDate)"
						+ "     ELSE 0 END)) + " + "     SUM((CASE "
						+ "     	   WHEN ex1.endDate IS NOT NULL THEN datediff(ex1.endDate,ex1.startDate)"
						+ "     ELSE 0 END)) BETWEEN (:workedYearFrom*365) AND (:workedYearTo*365) THEN 1  "
						+ "ELSE 0 END) FROM Experience ex1  GROUP BY ex1.employee" + " ) AND";
			}
		}

		if (bean != null && (bean.getCourtWorkedYearFrom() != null || bean.getCourtWorkedYearTo() != null)) {
			sql = sql + " LEFT JOIN s.experience ex2 ";

			if (bean.getCourtWorkedYearFrom() != null && bean.getCourtWorkedYearTo() == null) {
				where = where + "(ex2.employee, 1) IN" + "(SELECT ex3.employee, " + "(CASE WHEN" + "	  SUM((CASE "
						+ "     	   WHEN ex3.endDate IS NULL THEN datediff(CURDATE(),ex3.startDate)"
						+ "     ELSE 0 END)) + " + "     SUM((CASE "
						+ "     	   WHEN ex3.endDate IS NOT NULL THEN datediff(ex3.endDate,ex3.startDate)"
						+ "     ELSE 0 END)) >= (:courtWorkedYearFrom*365) THEN 1  "
						+ "ELSE 0 END) FROM Experience ex3 WHERE ex3.organizationtype="
						+ OrganizationTypeExperience.SHUUH.getVal() + "  GROUP BY ex3.employee) AND";
			}

			if (bean.getCourtWorkedYearFrom() == null && bean.getCourtWorkedYearTo() != null) {
				where = where + "(ex2.employee, 1) IN" + "(SELECT ex3.employee, " + "(CASE WHEN" + "	  SUM((CASE "
						+ "     	   WHEN ex3.endDate IS NULL THEN datediff(CURDATE(),ex3.startDate)"
						+ "     ELSE 0 END)) + " + "     SUM((CASE "
						+ "     	   WHEN ex3.endDate IS NOT NULL THEN datediff(ex3.endDate,ex3.startDate)"
						+ "     ELSE 0 END)) <= (:courtWorkedYearTo*365) THEN 1  "
						+ "ELSE 0 END) FROM Experience ex3 WHERE ex3.organizationtype="
						+ OrganizationTypeExperience.SHUUH.getVal() + "  GROUP BY ex3.employee) AND";
			}

			if (bean.getCourtWorkedYearFrom() != null && bean.getCourtWorkedYearTo() != null) {
				where = where + "(ex2.employee, 1) IN" + "(SELECT ex3.employee, " + "(CASE WHEN" + "	  SUM((CASE "
						+ "     	   WHEN ex3.endDate IS NULL THEN datediff(CURDATE(),ex3.startDate)"
						+ "     ELSE 0 END)) + " + "     SUM((CASE "
						+ "     	   WHEN ex3.endDate IS NOT NULL THEN datediff(ex3.endDate,ex3.startDate)"
						+ "     ELSE 0 END)) BETWEEN (:courtWorkedYearFrom*365) AND (:courtWorkedYearTo*365) THEN 1  "
						+ "ELSE 0 END) FROM Experience ex3 WHERE ex3.organizationtype="
						+ OrganizationTypeExperience.SHUUH.getVal() + "  GROUP BY ex3.employee) AND";
			}
		}

		if (bean != null && (bean.getAcademinRank() != null || bean.getAcademicRankFrom() != null
				|| bean.getAcademicRankTo() != null)) {
			sql = sql + " LEFT JOIN s.degreeGradeRank deg ";

			if (bean.getAcademinRank() != null)
				where = where + " deg.academicRank = :academicRank AND";

			if (bean.getAcademicRankFrom() != null)
				where = where + " YEAR(deg.rankDate) >= :degreeGradeRankFrom AND";

			if (bean.getAcademicRankTo() != null)
				where = where + " YEAR(deg.rankDate) <= :degreeGradeRankTo AND";
		}

		if (bean != null && (bean.getTrainingCountry() != null || bean.getTrainingCity() != null
				|| bean.getTrainingProvince() != null || bean.getTrainingStartDate() != null
				|| bean.getTrainingEndDate() != null)) {
			sql = sql + " LEFT JOIN s.training tr ";

			if (bean.getTrainingCountry() != null)
				where = where + " tr.country = :trainingCountry AND";

			if (bean.getTrainingCity() != null)
				where = where + " tr.city >= :trainingCity AND";

			if (bean.getTrainingProvince() != null)
				where = where + " tr.city <= :trainingProvince AND";

			if (bean.getTrainingStartDate() != null)
				where = where + " tr.startDate >= :trainingStartDate AND";

			if (bean.getTrainingEndDate() != null)
				where = where + " tr.endDate <= :trainingEndDate AND";
		}

		if (!where.equals("")) {
			where = where.substring(0, (where.length() - 4));

			sql += "WHERE " + where;
		}

		System.err.println("sqlSudal=" + sql);

		Query q = session.createQuery("SELECT DISTINCT s FROM Employee s " + sql).setFirstResult(pager.getFirstRow())
				.setMaxResults(pager.getMaxResult());

		Query q2 = session.createQuery("SELECT count(DISTINCT s) FROM Employee s " + sql);

		if (orgs != null) {
			q.setParameterList("orgs", orgs.toArray());
			q2.setParameterList("orgs", orgs.toArray());
		}

		if (bean != null && bean.getOrganization() != null) {
			q.setParameter("org", bean.getOrganization());
			q2.setParameter("org", bean.getOrganization());
		}

		if (bean != null && bean.getFamilyStatus() != null) {
			q.setParameter("familyStatus", bean.getFamilyStatus());
			q2.setParameter("familyStatus", bean.getFamilyStatus());
		}

		if (bean != null && bean.getLastname() != null) {
			q.setParameter("lastname", bean.getLastname());
			q2.setParameter("lastname", bean.getLastname());
		}

		if (bean != null && bean.getFirstname() != null) {
			q.setParameter("firstName", bean.getFirstname());
			q2.setParameter("firstName", bean.getFirstname());
		}

		if (bean != null && bean.getRegisterNo() != null) {
			q.setParameter("registerNo", bean.getRegisterNo());
			q2.setParameter("registerNo", bean.getRegisterNo());
		}

		if (bean != null && bean.getGender() != null) {
			q.setParameter("gender", bean.getGender());
			q2.setParameter("gender", bean.getGender());
		}

		if (bean != null && bean.getCityProvince() != null) {
			q.setParameter("addCityProvince", bean.getCityProvince());
			q2.setParameter("addCityProvince", bean.getCityProvince());
		}

		if (bean != null && bean.getDistrictSum() != null) {
			q.setParameter("addDistrictSum", bean.getDistrictSum());
			q2.setParameter("addDistrictSum", bean.getDistrictSum());
		}

		if (bean != null && bean.getAppointment() != null) {
			q.setParameter("appointment", bean.getAppointment());
			q2.setParameter("appointment", bean.getAppointment());
		}

		if (bean != null && bean.getStatus() != null) {
			q.setParameter("employeeStatus", bean.getStatus());
			q2.setParameter("employeeStatus", bean.getStatus());
		}

		if (bean != null && bean.getCurrentStatus() != null) {
			q.setParameter("employeeCurrentStatus", bean.getCurrentStatus());
			q2.setParameter("employeeCurrentStatus", bean.getCurrentStatus());
		}

		if (bean != null && bean.getEducationDegree() != null) {
			q.setParameter("educationDegree", bean.getEducationDegree());
			q2.setParameter("educationDegree", bean.getEducationDegree());
		}

		if (bean != null && bean.getAwardType() != null) {
			q.setParameter("awardType", bean.getAwardType());
			q2.setParameter("awardType", bean.getAwardType());

			if (bean != null && bean.getAward() != null) {
				q.setParameter("award", bean.getAward());
				q2.setParameter("award", bean.getAward());
			}
		}

		if (bean != null && bean.getAwardDateStart() != null) {
			q.setParameter("awardDateStart", bean.getAwardDateStart());

			q2.setParameter("awardDateStart", bean.getAwardDateStart());
		}

		if (bean != null && bean.getAwardDateEnd() != null) {
			q.setParameter("awardDateEnd", bean.getAwardDateEnd());

			q2.setParameter("awardDateEnd", bean.getAwardDateEnd());
		}

		if (bean != null && bean.getSahilgaDateStart() != null) {
			q.setParameter("sahilgaDateStart", bean.getSahilgaDateStart());

			q2.setParameter("sahilgaDateStart", bean.getSahilgaDateStart());
		}

		if (bean != null && bean.getSahilgaDateEnd() != null) {
			q.setParameter("sahilgaDateEnd", bean.getSahilgaDateEnd());

			q2.setParameter("sahilgaDateEnd", bean.getSahilgaDateEnd());
		}

		if (bean != null && bean.getSahilgaCancelDateStart() != null) {
			q.setParameter("sahilgaCancelDateStart", bean.getSahilgaCancelDateStart());

			q2.setParameter("sahilgaCancelDateStart", bean.getSahilgaCancelDateStart());
		}

		if (bean != null && bean.getSahilgaCancelDateEnd() != null) {
			q.setParameter("sahilgaCancelDateEnd", bean.getSahilgaCancelDateEnd());

			q2.setParameter("sahilgaCancelDateEnd", bean.getSahilgaCancelDateEnd());
		}

		if (bean != null && bean.getSahilga() != null) {
			q.setParameter("sahilga", bean.getSahilga());
			q2.setParameter("sahilga", bean.getSahilga());
		}

		if (bean != null && bean.getSahilgaTurul() != null) {
			q.setParameter("sahilgaTurul", bean.getSahilgaTurul());
			q2.setParameter("sahilgaTurul", bean.getSahilgaTurul());
		}

		if (bean != null && bean.getCommandSubject() != null) {
			q.setParameter("commandSubject", bean.getCommandSubject());
			q2.setParameter("commandSubject", bean.getCommandSubject());
		}

		if (bean != null && bean.getEmployeeDegreeStatus() != null) {
			q.setParameter("employeeDegreeStatus", bean.getEmployeeDegreeStatus());

			q2.setParameter("employeeDegreeStatus", bean.getEmployeeDegreeStatus());
		}

		if (bean.getDegreeDateFrom() != null) {
			q.setParameter("degreeDateFrom", bean.getDegreeDateFrom());

			q2.setParameter("degreeDateFrom", bean.getDegreeDateFrom());
		}

		if (bean.getDegreeDateTo() != null) {
			q.setParameter("degreeDateTo", bean.getDegreeDateTo());

			q2.setParameter("degreeDateTo", bean.getDegreeDateTo());
		}

		if (bean.getMilitaryType() != null) {
			q.setParameter("militaryType", bean.getMilitaryType());
			q2.setParameter("militaryType", bean.getMilitaryType());
		}

		if (bean.getMilitary() != null) {
			q.setParameter("military", bean.getMilitary());
			q2.setParameter("military", bean.getMilitary());
		}

		if (bean != null && bean.getQuitJobType() != null) {
			q.setParameter("quitCause", bean.getQuitJobType());
			q2.setParameter("quitCause", bean.getQuitJobType());
		}

		if (bean.getAllowanceDateStart() != null) {
			q.setParameter("allowanceDateStart", Integer.parseInt(bean.getAllowanceDateStart()));

			q2.setParameter("allowanceDateStart", Integer.parseInt(bean.getAllowanceDateStart()));
		}

		if (bean.getAllowanceDateEnd() != null) {
			q.setParameter("allowanceDateEnd", Integer.parseInt(bean.getAllowanceDateEnd()));

			q2.setParameter("allowanceDateEnd", Integer.parseInt(bean.getAllowanceDateEnd()));
		}

		if (bean.getAllowanceMoneyFrom() != null) {
			q.setParameter("allowanceMoneyFrom", bean.getAllowanceMoneyFrom());

			q2.setParameter("allowanceMoneyFrom", bean.getAllowanceMoneyFrom());
		}

		if (bean.getAllowanceMoneyTo() != null) {
			q.setParameter("allowanceMoneyTo", bean.getAllowanceMoneyTo());
			q2.setParameter("allowanceMoneyTo", bean.getAllowanceMoneyTo());
		}

		if (bean.getDisplacementCause() != null) {
			q.setParameter("displacementCause", bean.getDisplacementCause());
			q2.setParameter("displacementCause", bean.getDisplacementCause());
		}

		if (bean.getDisplacementType() != null && bean.getQuiteDateFrom() != null) {
			q.setParameter("quitDateFrom", Integer.parseInt(bean.getQuiteDateFrom()));

			q2.setParameter("quitDateFrom", Integer.parseInt(bean.getQuiteDateFrom()));
		}

		if ((bean.getDisplacementType() != null && (bean.getDisplacementType() == DisplacementType.AJLAASGARAH
				|| bean.getDisplacementType() == DisplacementType.SHILJIH)) && bean.getQuiteDateTo() != null) {
			q.setParameter("quitDateTo", Integer.parseInt(bean.getQuiteDateTo()));

			q2.setParameter("quitDateTo", Integer.parseInt(bean.getQuiteDateTo()));
		}

		if (bean != null && (bean.getVacationType() != null)) {
			q.setParameter("vacationType", bean.getVacationType());
			q2.setParameter("vacationType", bean.getVacationType());
		}

		if (bean != null && bean.getChuluuEhlehOgnoo() != null) {
			q.setParameter("chuluuEhlehOgnoo", bean.getChuluuEhlehOgnoo());
			q2.setParameter("chuluuEhlehOgnoo", bean.getChuluuEhlehOgnoo());
		}

		if (bean != null && bean.getChuluuDuusahOgnoo() != null) {
			q.setParameter("chuluuDuusahOgnoo", bean.getChuluuDuusahOgnoo());
			q2.setParameter("chuluuDuusahOgnoo", bean.getChuluuDuusahOgnoo());
		}

		if (bean != null && bean.getChuluuDuusahOgnooJiremsen() != null) {
			q.setParameter("chuluuDuusahOgnooJiremsen", bean.getChuluuDuusahOgnooJiremsen());
			q2.setParameter("chuluuDuusahOgnooJiremsen", bean.getChuluuDuusahOgnooJiremsen());
		}

		if (bean != null && bean.getChuluuDuusahOgnooSurgalt() != null) {
			q.setParameter("chuluuDuusahOgnooSurgalt", bean.getChuluuDuusahOgnooSurgalt());
			q2.setParameter("chuluuDuusahOgnooSurgalt", bean.getChuluuDuusahOgnooSurgalt());
		}

		if (bean != null && bean.getAgeFrom() != null) {
			q.setParameter("ageFrom", Integer.parseInt(bean.getAgeFrom()));
			q2.setParameter("ageFrom", Integer.parseInt(bean.getAgeFrom()));
		}

		if (bean != null && bean.getAgeTo() != null) {
			q.setParameter("ageTo", Integer.parseInt(bean.getAgeTo()));
			q2.setParameter("ageTo", Integer.parseInt(bean.getAgeTo()));
		}

		if (bean != null && bean.getOccupation() != null) {
			q.setParameter("occupation", bean.getOccupation());
			q2.setParameter("occupation", bean.getOccupation());
		}

		if (bean != null && bean.getWorkedYearFrom() != null) {
			q.setParameter("workedYearFrom", Integer.parseInt(bean.getWorkedYearFrom()));

			q2.setParameter("workedYearFrom", Integer.parseInt(bean.getWorkedYearFrom()));
		}

		if (bean != null && bean.getWorkedYearTo() != null) {
			q.setParameter("workedYearTo", Integer.parseInt(bean.getWorkedYearTo()));

			q2.setParameter("workedYearTo", Integer.parseInt(bean.getWorkedYearTo()));
		}

		if (bean != null && bean.getCourtWorkedYearFrom() != null) {
			q.setParameter("courtWorkedYearFrom", Integer.parseInt(bean.getCourtWorkedYearFrom()));

			q2.setParameter("courtWorkedYearFrom", Integer.parseInt(bean.getCourtWorkedYearFrom()));
		}

		if (bean != null && bean.getCourtWorkedYearTo() != null) {
			q.setParameter("courtWorkedYearTo", Integer.parseInt(bean.getCourtWorkedYearTo()));

			q2.setParameter("courtWorkedYearTo", Integer.parseInt(bean.getCourtWorkedYearTo()));

		}

		if (bean != null && bean.getAcademinRank() != null) {
			q.setParameter("academicRank", bean.getAcademinRank());
			q2.setParameter("academicRank", bean.getAcademinRank());
		}

		if (bean != null && bean.getAcademicRankFrom() != null) {
			q.setParameter("degreeGradeRankFrom", Integer.parseInt(bean.getAcademicRankFrom()));
			q2.setParameter("degreeGradeRankFrom", Integer.parseInt(bean.getAcademicRankFrom()));
		}

		if (bean != null && bean.getAcademicRankTo() != null) {
			q.setParameter("degreeGradeRankTo", Integer.parseInt(bean.getAcademicRankTo()));
			q2.setParameter("degreeGradeRankTo", Integer.parseInt(bean.getAcademicRankTo()));
		}

		/*
		 * if (bean != null && bean.getEducationDegree() != null) {
		 * q.setParameter("educationDegreeType", bean.getEducationDegree());
		 * q2.setParameter("educationDegreeType", bean.getEducationDegree()); }
		 */

		if (bean != null && bean.getEduStartDateFrom() != null) {
			q.setParameter("eduStartDateFrom", Integer.parseInt(bean.getEduStartDateFrom()));
			q2.setParameter("eduStartDateFrom", Integer.parseInt(bean.getEduStartDateFrom()));
		}

		if (bean != null && bean.getEduStartDateTo() != null) {
			q.setParameter("eduStartDateTo", Integer.parseInt(bean.getEduGraduateDateTo()));
			q2.setParameter("eduStartDateTo", Integer.parseInt(bean.getEduGraduateDateTo()));
		}

		if (bean != null && bean.getEduGraduateDateFrom() != null) {
			q.setParameter("eduGraduateDateFrom", Integer.parseInt(bean.getEduGraduateDateFrom()));
			q2.setParameter("eduGraduateDateFrom", Integer.parseInt(bean.getEduGraduateDateFrom()));
		}

		if (bean != null && bean.getEduGraduateDateTo() != null) {
			q.setParameter("eduGraduateDateTo", Integer.parseInt(bean.getEduGraduateDateTo()));
			q2.setParameter("eduGraduateDateTo", Integer.parseInt(bean.getEduGraduateDateTo()));
		}

		if (bean.getTrainingCountry() != null) {
			q.setParameter("trainingCountry", bean.getTrainingCountry());
			q2.setParameter("trainingCountry", bean.getTrainingCountry());
		}

		if (bean.getTrainingCity() != null) {
			q.setParameter("trainingCity", bean.getTrainingCity().getCityProvinceName());
			q2.setParameter("trainingCity", bean.getTrainingCity().getCityProvinceName());
		}

		if (bean.getTrainingProvince() != null) {
			q.setParameter("trainingProvince", bean.getTrainingProvince().getCityName());
			q2.setParameter("trainingProvince", bean.getTrainingProvince().getCityName());
		}

		if (bean.getTrainingStartDate() != null) {
			q.setParameter("trainingStartDate", Integer.parseInt(bean.getTrainingStartDate()));
			q2.setParameter("trainingStartDate", Integer.parseInt(bean.getTrainingStartDate()));
		}

		if (bean.getTrainingEndDate() != null) {
			q.setParameter("trainingEndDate", Integer.parseInt(bean.getTrainingEndDate()));
			q2.setParameter("trainingEndDate", Integer.parseInt(bean.getTrainingEndDate()));
		}

		if (bean.getTsolTushaalDugaar() != null) {
			q.setParameter("tsolTushaalDugaar", bean.getTsolTushaalDugaar());
			q2.setParameter("tsolTushaalDugaar", bean.getTsolTushaalDugaar());
		}

		if (bean.getSahilgaTushaalDugaar() != null) {
			q.setParameter("sahilgaTushaalDugaar", bean.getSahilgaTushaalDugaar());
			q2.setParameter("sahilgaTushaalDugaar", bean.getSahilgaTushaalDugaar());
		}

		if (bean.getAllowanceCommandSubject() != null) {
			q.setParameter("allowanceCommandSubject", bean.getAllowanceCommandSubject());
			q2.setParameter("allowanceCommandSubject", bean.getAllowanceCommandSubject());
		}

		if (bean.getAllowanceTushaalDugaar() != null) {
			q.setParameter("allowanceTushaalDugaar", bean.getAllowanceTushaalDugaar());
			q2.setParameter("allowanceTushaalDugaar", bean.getAllowanceTushaalDugaar());
		}

		if (bean.getAllowanceType() != null) {
			q.setParameter("allowanceType", bean.getAllowanceType());
			q2.setParameter("allowanceType", bean.getAllowanceType());
		}

		Long res = (Long) q2.uniqueResult();
		pager.setCount(res.doubleValue());
		List<Employee> employees = q.list();

		return employees;
	}

	/*
	 * Ажилтны жагсаалт
	 */
	public List<Employee> getListEmployee(Organization org) {
		Criteria crit = session.createCriteria(Employee.class);

		if (org != null)
			crit.add(Restrictions.eq("organization", org));

		crit.addOrder(Order.asc("firstName"));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<Employee> list = crit.list();

		return list;
	}

	public List<Employee> getListEmployee(Organization org, Appointment appointment) {
		Criteria crit = session.createCriteria(Employee.class);

		if (org != null)
			crit.add(Restrictions.eq("organization", org));

		if (appointment != null)
			crit.add(Restrictions.eq("appointment", appointment));

		crit.addOrder(Order.asc("firstName"));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<Employee> list = crit.list();

		return list;
	}

	public List<Employee> getListEmployeeJudje(Organization org, AppointmentLevel appointmentLevel) {
		List<Employee> employee = new ArrayList<Employee>();

		String sql = "SELECT s FROM Employee s ";
		String where = "";

		if (org != null)
			where = where + " s.organization = :org AND";

		if (appointmentLevel != null)
			where = where + " s.appointment.appointmentLevel = :level AND";

		if (!where.equals("")) {
			where = where.substring(0, (where.length() - 4));

			sql += "WHERE " + where;
		}

		Query q = session.createQuery(sql);

		if (org != null)
			q.setParameter("org", org);

		if (appointmentLevel != null)
			q.setParameter("level", appointmentLevel);

		employee = q.list();

		return employee;
	}

	public List<Employee> getListEmployee() {
		Criteria crit = session.createCriteria(Employee.class);

		crit.addOrder(Order.asc("firstName"));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<Employee> list = crit.list();

		return list;
	}

	/*
	 * OccupationRole selection model
	 */
	public List<OccupationRole> getListOccupationRole() {
		Criteria crit = session.createCriteria(OccupationRole.class);

		List<OccupationRole> list = crit.list();

		return list;
	}

	/*
	 * OccupationType selection model
	 */
	public List<OccupationType> getListOccupationType() {
		Criteria crit = session.createCriteria(OccupationType.class);

		List<OccupationType> list = crit.list();

		return list;
	}

	/*
	 * UtTzTtTuSort selection model УТ,ТЗ,ТТ,ТӨ албаны ангилалуудын зэрэглэл
	 */
	public List<UtTzTtTuSort> getListUtTzTtTuSort() {
		Criteria crit = session.createCriteria(UtTzTtTuSort.class);

		List<UtTzTtTuSort> list = crit.list();

		return list;
	}

	/*
	 * TuAA selection model ТӨ,АА-ны ангилалудын зэрэглэл
	 */
	public List<TuAaSort> getListTuAaSort() {
		Criteria crit = session.createCriteria(TuAaSort.class);

		List<TuAaSort> list = crit.list();
		return list;
	}

	/*
	 * SalaryNetwork selection model: Цалингийн сүлжээ сонгох
	 */
	public List<SalaryNetwork> getListSalaryNetwork() {
		Criteria crit = session.createCriteria(SalaryNetwork.class);

		List<SalaryNetwork> list = crit.list();

		return list;
	}

	/*
	 * SalaryScale selection model: Цалингийн шатлал сонгох
	 */
	public List<SalaryScale> getListSalaryScale() {
		Criteria crit = session.createCriteria(SalaryScale.class);

		List<SalaryScale> list = crit.list();

		return list;
	}

	/*
	 * MilitaryRank selection model: Цэргийн цол сонгох
	 */
	public List<AcademicRank> getListMilitaryRank() {
		Criteria crit = session.createCriteria(AcademicRank.class);

		List<AcademicRank> list = crit.list();

		return list;
	}

	/*
	 * Award selection model: Шагнал
	 */
	public List<Award> getListAward(AwardType awardType) {
		Criteria crit = session.createCriteria(Award.class);
		crit.add(Restrictions.eq("awardType", awardType));

		List<Award> list = crit.list();

		return list;
	}

	/*
	 * CommandSubject selection model: Тушаал гаргасан субъект
	 */
	public List<CommandSubject> getListCommandSubject() {
		Criteria crit = session.createCriteria(CommandSubject.class);

		List<CommandSubject> list = crit.list();

		return list;
	}

	/*
	 * AllowanceType selection model: Тэтгэмжийн төрөл
	 */
	public List<AllowanceType> getListAllowanceType() {
		Criteria crit = session.createCriteria(AllowanceType.class);

		List<AllowanceType> list = crit.list();

		return list;
	}

	public List<Allowance> getListAllowance(Employee emp) {
		Criteria crit = session.createCriteria(Allowance.class);
		crit.add(Restrictions.eq("employee", emp));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Allowance> list = crit.list();

		return list;
	}

	public List<VacationType> getListVacationType() {
		Criteria crit = session.createCriteria(VacationType.class);

		List<VacationType> list = crit.list();

		return list;
	}

	public List<Vacation> getListVacation(Employee emp) {
		Criteria crit = session.createCriteria(Vacation.class);
		crit.add(Restrictions.eq("employee", emp));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Vacation> list = crit.list();

		return list;
	}

	/**** 2012-12-7 ****/

	public List<LandIntention> getListLandIntention() {
		Criteria crit = session.createCriteria(LandIntention.class);

		List<LandIntention> list = crit.list();

		return list;
	}

	/*
	 * AwardType selection model: Шагналын төрөл сонгох
	 */
	public List<AwardType> getListAwardType() {
		Criteria crit = session.createCriteria(AwardType.class);
		crit.addOrder(Order.asc("name"));
		List<AwardType> list = crit.list();
		return list;
	}

	/*
	 * Санхүүжилтийн төрөл сонгох
	 */
	public List<FinancingType> getListFinancingType() {
		Criteria crit = session.createCriteria(FinancingType.class);

		List<FinancingType> list = crit.list();

		return list;
	}

	/*
	 * Харьяалагдах захиргаа сонгох
	 */
	public List<AppurtenanceLead> getListAppurtenanceLead() {
		Criteria crit = session.createCriteria(AppurtenanceLead.class);

		List<AppurtenanceLead> list = crit.list();

		return list;
	}

	/*
	 * Байгууллагын төрөл сонгох
	 */
	public List<OrganizationType> getListOrganizationType() {
		Criteria crit = session.createCriteria(OrganizationType.class);

		List<OrganizationType> list = crit.list();

		return list;
	}

	/*
	 * Шилжих шалтгааны төрөл сонгох Jul 5, 2012, B.Erdenetuya
	 */
	public List<DisplacementCause> getListDisplacementCause() {
		Criteria crit = session.createCriteria(DisplacementCause.class);

		List<DisplacementCause> list = crit.list();

		return list;
	}

	/*
	 * Ажилласан түүхийн жагсаалт Jul 5, 2012, B.Erdenetuya
	 */
	public List<Displacement> getListCurrentOccupation(Employee emp) {
		Criteria crit = session.createCriteria(Displacement.class);
		crit.add(Restrictions.eq("employee", emp));
		crit.addOrder(Order.desc("issuedDate"));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Displacement> list = crit.list();

		return list;
	}

	/* Jul 17, 2012 B.Erdenetuya begin */

	public Displacement getCurrentOccupation(Employee emp) {
		Criteria crit = session.createCriteria(Displacement.class);

		crit.add(Restrictions.eq("employee", emp));
		crit.add(Restrictions.eq("isNowDisplacement", true));

		if (crit.list().isEmpty())
			return null;

		return (Displacement) crit.list().get(0);
	}

	/* Jul 17, 2012 B.Erdenetuya end */

	/*
	 * Харьяалагдах байрлал сонгох
	 */
	public List<AppurtenanceLocation> getListAppurtenanceLocation() {
		Criteria crit = session.createCriteria(AppurtenanceLocation.class);

		List<AppurtenanceLocation> list = crit.list();

		return list;
	}

	/*
	 * Харьяaлагдах нэгж хайх
	 */

	public List<Department> getListDepartmentSearch(Organization org) {
		Criteria crit = session.createCriteria(Department.class);

		if (org != null)
			crit.add(Restrictions.like("organization", "%" + org + "%"));

		crit.addOrder(Order.asc("code"));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<Department> list = crit.list();
		return list;
	}

	@Override
	public MapRolePermission getMapRolePermission(Permission perm, Role role) {

		Criteria crit = session.createCriteria(MapRolePermission.class);

		crit.add(Restrictions.eq("permission", perm));

		crit.add(Restrictions.eq("role", role));

		if (crit.list().isEmpty())
			return null;

		MapRolePermission list = (MapRolePermission) crit.list().get(0);
		return list;
	}

	/* Jul 7, 2012 tuguldur.j Start */

	@Override
	public List<Addition> getAddition(Employee emp) {
		Criteria crit = session.createCriteria(Addition.class);
		crit.add(Restrictions.eq("employee", emp));
		List<Addition> list = crit.list();
		return list;
	}

	/* Jul 7, 2012 tuguldur.j End */

	/* Jul 6, 2012 T.Maralerdene begin */

	public List<Employee> getListUser() {

		Criteria crit = session.createCriteria(Employee.class);

		crit.add(Restrictions.eq("systemUser", true));

		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<Employee> list = crit.list();

		return list;
	}

	/* Jul 6, 2012 T.Maralerdene end */

	/* Jul 7, 2012 T.Maralerdene begin */
	public User getUser(Employee emp) {
		Criteria crit = session.createCriteria(User.class);

		crit.add(Restrictions.eq("employee", emp));

		if (crit.list().isEmpty())
			return null;

		User user = (User) crit.list().get(0);

		return user;
	}

	/* Jul 7, 2012 T.Maralerdene end */

	/* Jul 7, 2012 B.Erdenetuya end */
	public List<OccupationRank> getListOccupationRank() {
		Criteria crit = session.createCriteria(OccupationRank.class);

		List<OccupationRank> list = crit.list();

		return list;
	}

	/* Jul 7, 2012 B.Erdenetuya begin */

	/* Jul 8, 2012 T.Maralerdene begin */
	public List<UtTzTtTuLevel> getListUtTzTtTuLevel(UtTzTtTuSort cat) {
		Criteria crit = session.createCriteria(UtTzTtTuLevel.class);
		crit.createCriteria("utTzTtTuRank", "utTzTtTuRank", Criteria.LEFT_JOIN);

		if (cat != null)
			crit.add(Restrictions.eq("utTzTtTuSort", cat));

		crit.addOrder(Order.asc("utTzTtTuRank.rank"));

		List<UtTzTtTuLevel> list = crit.list();

		return list;
	}

	/* Jul 8, 2012 T.Maralerdene end */

	/* Jul 9, 2012 B.Erdenetuya end */
	public List<TuAaLevel> getListTuAaLevel(TuAaSort cat) {
		Criteria crit = session.createCriteria(TuAaLevel.class);

		if (cat != null)
			crit.add(Restrictions.eq("tuAaSort", cat));

		List<TuAaLevel> list = crit.list();

		return list;
	}

	/* Jul 9, 2012 B.Erdenetuya begin */

	/* Jul 9, 2012 T.Maralerdene begin */
	public List<TuriinZahirgaaSalaryNetwork> getListTuriinZahirgaaSalaryNetwork() {
		Criteria crit = session.createCriteria(TuriinZahirgaaSalaryNetwork.class);

		crit.addOrder(Order.desc("date"));

		crit.addOrder(Order.asc("level"));

		List<TuriinZahirgaaSalaryNetwork> list = crit.list();

		return list;

	}

	public List<TuriinZahirgaaSalaryNetwork> getListTuriinZahirgaaSalaryDate(UtTzTtTuLevel level, SalaryScale scale) {
		Criteria crit = session.createCriteria(TuriinZahirgaaSalaryNetwork.class);

		if (level != null)
			crit.add(Restrictions.eq("level", level));

		if (scale != null)
			crit.add(Restrictions.eq("phase", scale));

		if (scale != null)
			crit.addOrder(Order.desc("date"));

		if (level != null)
			crit.addOrder(Order.asc("level"));

		List<TuriinZahirgaaSalaryNetwork> list = crit.list();

		return list;

	}

	public Displacement getCurrentPosition(Employee emp) {
		Criteria crit = session.createCriteria(Displacement.class);

		crit.add(Restrictions.eq("id", emp));
		crit.addOrder(Order.desc("issuedDate"));

		if (crit.list().isEmpty())
			return null;

		return (Displacement) crit.list().get(0);

	}

	public Displacement getCurrentOccupationId(Experience emp) {
		System.err.println("emp dis id");
		Criteria crit = session.createCriteria(Displacement.class);

		crit.add(Restrictions.eq("id", emp.getDisplacement().getId()));
		crit.addOrder(Order.desc("issuedDate"));

		if (crit.list().isEmpty()) {
			return null;
		} else {
			return (Displacement) crit.list().get(0);
		}
	}

	/* Jul 9, 2012 T.Maralerdene end */

	/* Jul 14, 2012 T.Maralerdene begin */
	public TuriinZahirgaaSalaryNetwork getCurrentSalaryAmount(UtTzTtTuLevel level, SalaryScale phase, Date date) {
		Criteria crit = session.createCriteria(TuriinZahirgaaSalaryNetwork.class);

		crit.add(Restrictions.eq("level", level));

		crit.add(Restrictions.eq("phase", phase));

		crit.add(Restrictions.le("date", date));

		crit.addOrder(Order.desc("date"));

		// crit.addOrder(Order.asc("level"));

		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		if (crit.list().isEmpty())
			return null;

		return (TuriinZahirgaaSalaryNetwork) crit.list().get(0);
	}

	public List<AdditionalSalaryType> getListAdditionalSalaryTypes(AdditionalSalaryCategory category) {
		Criteria crit = session.createCriteria(AdditionalSalaryType.class);
		if (category != null)
			crit.add(Restrictions.eq("category", category));

		return crit.list();
	}

	/* Jul 14, 2012 T.Maralerdene end */

	/* Jul 16, 2012 G.Bileg-Ochir begin */

	public Organization getOrganization(OrganizationGroup org) {
		Criteria crit = session.createCriteria(Organization.class);

		crit.add(Restrictions.eq("organizationGroup", org));

		if (crit.list().isEmpty())
			return null;

		Organization organization = (Organization) crit.list().get(0);

		return organization;
	}

	/* Jul 16, 2012 G.Bileg-Ochir end */

	/* Jul 17, 2012 B.Erdenetuya end */

	public List<Department> getListDepartment(Organization org) {
		Criteria crit = session.createCriteria(Department.class);

		if (org != null)
			crit.add(Restrictions.eq("organization", org));

		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<Department> list = crit.list();

		return list;
	}

	public List<Appointment> getListAppointment(AppointmentType type, AppointmentSortEmployee sort) {
		Criteria crit = session.createCriteria(Appointment.class);

		if (type != null)
			crit.add(Restrictions.eq("appointmentType", type));

		if (sort != null) {
			Criteria crit1 = crit.createCriteria("occupationType", Criteria.INNER_JOIN);
			crit1.add(Restrictions.eq("type", sort));
		}

		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<Appointment> list = crit.list();

		return list;
	}

	/* Jul 17, 2012 B.Erdenetuya begin */

	/* Jul 16, 2012 G.Bileg-Ochir begin */

	public List<Origin> getListOrigin() {
		Criteria crit = session.createCriteria(Origin.class);

		List<Origin> list = crit.list();

		return list;
	}

	public List<Ascription> getListAscription() {
		Criteria crit = session.createCriteria(Ascription.class);

		List<Ascription> list = crit.list();

		return list;
	}

	/* Jul 16, 2012 G.Bileg-Ochir end */

	/* Jul 16, 2012 T.Maralerdene begin */
	public List<AdditionalSalary> getListAdditionalSalary(SalaryHistory salary) {
		Criteria crit = session.createCriteria(AdditionalSalary.class);

		crit.add(Restrictions.eq("salary", salary));

		return crit.list();
	}

	public List<AdditionalSalary> getListAdditionalSalary(EmployeeSalaryHistory salary) {
		Criteria crit = session.createCriteria(AdditionalSalary.class);

		crit.add(Restrictions.eq("salaryHistory", salary));

		return crit.list();
	}

	public List<SalaryHistory> getListSalary(Employee emp) {
		Criteria crit = session.createCriteria(SalaryHistory.class);

		crit.add(Restrictions.eq("employee", emp));

		crit.addOrder(Order.desc("year"));

		crit.addOrder(Order.desc("month"));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return crit.list();
	}

	/* Jul 16, 2012 T.Maralerdene end */

	/* Jul 17, 2012 B.Erdenetuya end */
	/*
	 * Ажилланы ажлаас гарсан түүхийг харуулна
	 */
	public List<QuitJob> getQuitJob(Employee emp) {
		Criteria crit = session.createCriteria(QuitJob.class);
		crit.add(Restrictions.eq("employee", emp));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<QuitJob> list = crit.list();

		return list;
	}

	/* Jul 17, 2012 B.Erdenetuya begin */

	/* Jul 17, 2012 T.Maralerdene begin */
	public Notice getContent() {
		Criteria crit = session.createCriteria(Notice.class);

		crit.addOrder(Order.desc("createdDate"));

		if (crit.list().isEmpty())
			return null;

		return (Notice) crit.list().get(0);
	}

	/* Jul 17, 2012 T.Maralerdene end */

	/* Jul 18, 2012 G.Bileg-Ochir begin */
	public List<City> getListCity() {
		Criteria crit = session.createCriteria(City.class);

		List<City> list = crit.list();

		return list;
	}

	/* Jul 18, 2012 G.Bileg-Ochir end */

	/* Jul 19, 2012 tuguldur.j Start */
	@Override
	public List<Employee> getEmployeeList(Employee emp) {
		Criteria crit = session.createCriteria(Employee.class);

		crit.add(Restrictions.eq("employee", emp));

		return crit.list();
	}

	/* Jul 20, 2012 tuguldur.j End */

	/* Jul 20, 2012 G.Bileg-Ochir begin */

	public List<Object[]> getListEmployeeTuSearch(Organization organization) {

		String sql = "SELECT COUNT(e),e.degreeType FROM Educations e";

		String where = "";

		if (organization != null)
			where = " e.employee.organization = :org ";

		if (!where.equals(""))
			sql += " WHERE " + where;

		sql = sql + " GROUP BY e.degreeType";

		Query q = session.createQuery(sql);

		if (organization != null)
			q.setParameter("org", organization);

		List<Object[]> list = q.list();

		return list;
	}

	/* Jul 20, 2012 G.Bileg-Ochir end */

	/* Jul 21, 2012 G.Bileg-Ochir begin */

	public List<Object[]> getListEmployeeLanguageSearch(Organization organization) {
		String sql = "SELECT COUNT(s),s.foreignLanguage FROM Language s "
				+ "WHERE s.employee.organization = :org GROUP BY s.foreignLanguage";

		Query q = session.createQuery(sql);

		q.setParameter("org", organization);

		List<Object[]> list = q.list();

		return list;
	}

	public List<Employee> getListEmployeeCeoSearch(Organization organization) {
		String sql = "SELECT s.employee FROM Displacement s "
				+ "WHERE s.employee.organization = :org AND s.occupationRole.isUdirdah = TRUE";

		Query q = session.createQuery(sql);

		q.setParameter("org", organization);

		List<Employee> list = q.list();

		return list;
	}

	/* Jul 21, 2012 maralerdene.t End */
	public List<Object[]> getListEmployeeGenderReport(Organization organization, AppointmentType appointmentType) {
		String sql = "SELECT COUNT(e),e.gender FROM Employee e ";

		String where = "";

		if (organization != null)
			where += " e.organization = :org AND";

		if (appointmentType != null)
			where += " e.appointment.appointmentType = :type AND";

		if (!where.equals("")) {

			where = where.substring(0, (where.length() - 4));
			sql += " WHERE " + where;
		}

		sql = sql + " GROUP BY e.gender";

		Query q = session.createQuery(sql);

		if (appointmentType != null)
			q.setParameter("type", appointmentType);

		if (organization != null)
			q.setParameter("org", organization);

		List<Object[]> list = q.list();

		return list;
	}

	public List<Employee> getListEmployeeAgeReport(Organization org, AppointmentType appointmentType) {

		String sql = "SELECT e FROM Employee e";

		String where = "";

		if (org != null)
			where += " e.organization = :org AND";

		if (appointmentType != null)
			where += " e.appointment.appointmentType = :type AND";

		if (!where.equals("")) {

			where = where.substring(0, (where.length() - 4));
			sql += " WHERE " + where;
		}

		Query q = session.createQuery(sql);

		if (org != null)
			q.setParameter("org", org);

		if (appointmentType != null)
			q.setParameter("type", appointmentType);

		return q.list();
	}

	/* Jul 21, 2012 maralerdene.t begin */

	/* Jul 23, 2012 G.Bileg-Ochir begin */

	public List<Organization> getListOrganizationSearch(OrganizationSearchBean bean) {
		Criteria crit = session.createCriteria(Organization.class);

		if (bean != null && bean.getName() != null)
			crit.add(Restrictions.like("name", "%" + bean.getName() + "%"));

		if (bean != null && bean.getRegister() != null)
			crit.add(Restrictions.like("register", "%" + bean.getRegister() + "%"));

		if (bean != null && bean.getShortName() != null)
			crit.add(Restrictions.like("shortName", "%" + bean.getShortName() + "%"));

		crit.addOrder(Order.asc("name"));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<Organization> list = crit.list();
		return list;
	}

	public List<Organization> getListOrganization(OrganizationSearchBean bean) {
		Criteria crit = session.createCriteria(Organization.class);

		if (bean != null && bean.getName() != null)
			crit.add(Restrictions.like("name", "%" + bean.getName() + "%"));

		if (bean != null && bean.getRegister() != null)
			crit.add(Restrictions.like("register", "%" + bean.getRegister() + "%"));

		if (bean != null && bean.getShortName() != null)
			crit.add(Restrictions.like("shortName", "%" + bean.getShortName() + "%"));

		if (bean != null && bean.getOrganizationType() != null)
			crit.add(Restrictions.eq("organizationType", bean.getOrganizationType()));

		crit.addOrder(Order.asc("name"));

		return crit.list();
	}

	public List<Object[]> getCompetenceCommitteeCandidateList(Organization org, Date fromDate, Date toDate,
			OrganizationType orgType) {
		String sql = "SELECT COUNT(DISTINCT c.ccCandidates) FROM CandidateHistory c ";

		String where = "";

		if (org != null)
			where = "c.organization = :org AND";

		if (fromDate == null && toDate != null)
			where = where + " c.candidateDate BETWEEN CURDATE() AND :toDate AND";

		if (fromDate != null && toDate == null)
			where = where + " c.candidateDate BETWEEN :fromDate AND CURDATE() AND";

		if (fromDate != null && toDate != null)
			where = where + " c.candidateDate BETWEEN :fromDate AND :toDate AND";

		if (orgType != null)
			where = "c.organizationType = :organizationType AND";

		if (!where.equals("")) {
			where = where.substring(0, (where.length() - 4));
			sql += " WHERE " + where;
		}

		// sql = sql + " GROUP BY c.ccCandidates";

		Query q = session.createQuery(sql);

		if (org != null)
			q.setParameter("org", org);

		if (fromDate != null)
			q.setParameter("fromDate", fromDate);

		if (toDate != null)
			q.setParameter("toDate", toDate);

		if (orgType != null)
			q.setParameter("organizationType", orgType);

		if (q.list().isEmpty())
			return null;

		return q.list();
	}

	/* Jul 23, 2012 G.Bileg-Ochir end */

	/* Aug 3, 2012 tuguldur.j Start */

	/* Jul 27, 2012 tuguldur.j Start */

	/*
	 * public List<Object[]> getDisciplineCommitteeMembersList(Organization org,
	 * Date fromDate, Date toDate) { String sql =
	 * "SELECT COUNT(h), h.solvingType FROM MeasuredDisciplineMembers h ";
	 * 
	 * String where = "";
	 * 
	 * if (org != null) where = "h.employee.organization = :org AND ";
	 * 
	 * if (fromDate != null && toDate != null) where = where +
	 * "h.dateOfComplaints BETWEEN :fromDate AND :toDate AND ";
	 * 
	 * if (!where.equals("")) { where = where.substring(0, (where.length() -
	 * 4)); sql += "WHERE " + where; }
	 * 
	 * sql = sql + "GROUP BY  h.solvingType"; Query q =
	 * session.createQuery(sql);
	 * 
	 * if (org != null) q.setParameter("org", org);
	 * 
	 * if (fromDate != null) q.setParameter("fromDate", fromDate);
	 * 
	 * if (toDate != null) q.setParameter("toDate", toDate);
	 * 
	 * if (q.list().isEmpty()) return null;
	 * 
	 * List<Object[]> list = q.list();
	 * 
	 * return list; }
	 */

	/* Jul 27, 2012 tuguldur.j End */

	/* Feb, 2013 Jargalsuren.S Start */
	public List<Object[]> getDisciplineCommitteeMembersList(Organization org, Date fromDate, Date toDate) {
		String sql = "SELECT COUNT(h), h.sahilgaShiitgel FROM Sahilga h ";

		String where = "";

		if (org != null)
			where = "h.employee.organization = :org AND ";

		if (fromDate != null)
			where = where + "h.shiitgelAvagdsanOgnoo >= :fromDate AND ";

		if (toDate != null)
			where = where + "h.shiitgelDuusahOgnoo <= :toDate AND ";

		if (!where.equals("")) {
			where = where.substring(0, (where.length() - 4));
			sql += "WHERE " + where;
		}

		sql = sql + "GROUP BY  h.sahilgaShiitgel";
		Query q = session.createQuery(sql);

		if (org != null)
			q.setParameter("org", org);

		if (fromDate != null)
			q.setParameter("fromDate", fromDate);

		if (toDate != null)
			q.setParameter("toDate", toDate);

		if (q.list().isEmpty())
			return null;

		List<Object[]> list = q.list();

		return list;
	}

	/* Feb, 2013 Jargalsuren.S End */

	/* Jul 23, 2012 T.Maralerdene begin */
	public List<Object[]> getListSalaryWage(Organization org) {
		String sql = "SELECT COUNT(DISTINCT e),d.salaryNetwork.getWage FROM Employee e JOIN e.displacement d ";
		String where = "";

		if (org != null)
			where = " e.organization = :org";

		if (!where.equals(""))
			sql += "WHERE " + where;

		sql += " GROUP BY d.salaryNetwork.getWage";

		Query q = session.createQuery(sql);

		if (org != null)
			q.setParameter("org", org);

		return q.list();

	}

	public List<Object[]> getListSalaryReport(Organization org, Integer year) {

		String sql = "SELECT SUM(s.totalAmount),COUNT(DISTINCT e) FROM Employee e JOIN e.salary s";

		String where = "";

		if (org != null)
			where += " e.organization = :org AND ";

		if (year != null)
			where += " s.year = :year AND ";

		if (!where.equals("")) {

			where = where.substring(0, (where.length() - 4));

			sql += " WHERE " + where;
		}

		// sql += " GROUP BY e.organization";

		Query q = session.createQuery(sql);

		if (org != null)
			q.setParameter("org", org);

		if (year != null)
			q.setParameter("year", year);

		List<Object[]> list = q.list();

		return list;

	}

	public List<Object[]> getListAdditionalSalaryReport(Organization org, Integer year) {

		String sql = "SELECT SUM(s.additionalAmount),AVG(s.percent),s.additionalSalaryType FROM AdditionalSalary s ";

		String where = "";

		if (org != null)
			where += " s.salary.employee.organization = :org AND ";

		if (year != null)
			where += " s.salary.year = :year AND ";

		if (!where.equals("")) {

			where = where.substring(0, (where.length() - 4));
			sql += " WHERE " + where;
		}
		sql += " GROUP BY s.additionalSalaryType";

		Query q = session.createQuery(sql);

		if (org != null)
			q.setParameter("org", org);

		if (year != null)
			q.setParameter("year", year);

		List<Object[]> list = q.list();

		return list;

	}

	/* Jul 23, 2012 T.Maralerdene end */

	/* Jul 24, 2012 B.Erdenetuya end */

	public List<Experience> getListEmployeeWorkedYearReport(Organization org, AppointmentType type) {

		String sql = "SELECT MAX(e.startDate),e.employee FROM Experience e";

		String where = "";

		if (org != null)
			where += " e.employee.organization = :org AND ";

		if (type != null)
			where += " e.employee.appointment.appointmentType = :type AND";

		if (!where.equals("")) {

			where = where.substring(0, (where.length() - 4));
			sql += " WHERE " + where;
		}

		sql += " GROUP BY e.employee";

		Query q = session.createQuery(sql);

		if (org != null)
			q.setParameter("org", org);
		if (type != null)
			q.setParameter("type", type);

		List<Object[]> list = q.list();

		List<Experience> l = new ArrayList<Experience>();

		for (Object[] obj : list) {
			Experience d = new Experience();
			d.setStartDate((Date) obj[0]);
			d.setEmployee((Employee) obj[1]);

			l.add(d);
		}

		return l;
	}

	public List<Object[]> getListEmployeeQuitTypeReport(Organization organization, AppointmentType appointmentType) {
		String sql = "SELECT COUNT(e),e.employeeCurrentStatus FROM Employee e ";

		String where = "";

		if (organization != null)
			where += " e.organization = :org AND ";

		if (appointmentType != null)
			where += " e.appointment.appointmentType = :type AND";

		if (!where.equals("")) {

			where = where.substring(0, (where.length() - 4));
			sql += " WHERE " + where;
		}

		sql = sql + " GROUP BY e.employeeCurrentStatus";

		Query q = session.createQuery(sql);

		if (organization != null)
			q.setParameter("org", organization);
		if (appointmentType != null)
			q.setParameter("type", appointmentType);

		List<Object[]> list = q.list();

		return list;
	}

	/* Jul 24, 2012 B.Erdenetuya begin */

	/* Jul 24, 2012 T.Maralerdene begin */
	public User getMaxUserCode() {
		Criteria crit = session.createCriteria(User.class);

		// crit.setProjection(Projections.max("id"));
		crit.addOrder(Order.desc("id"));

		if (crit.list().isEmpty())
			return null;

		return (User) crit.list().get(0);

	}

	/* Jul 24, 2012 T.Maralerdene end */

	/* Jul 24, 2012 G.Bileg-Ochir begin */

	public List<DegreeGrade> getListDegreeGrade(Employee emp) {
		Criteria crit = session.createCriteria(DegreeGrade.class);
		crit.add(Restrictions.eq("employee", emp));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<DegreeGrade> list = crit.list();
		return list;
	}

	/* Jul 24, 2012 G.Bileg-Ochir end */

	/* Aug 8, 2012 tuguldur.j Start */

	public List<DegreeGradeRank> getListDegreeGradeRank(Employee emp) {
		Criteria crit = session.createCriteria(DegreeGradeRank.class);
		crit.add(Restrictions.eq("employee", emp));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<DegreeGradeRank> list = crit.list();
		return list;
	}

	/* Aug 8, 2012 tuguldur.j End */

	/* Jul 25, 2012 B.Erdenetuya end */

	public List<QuitJobCause> getListQuitJobCause(EmployeeCurrentStatus cat) {
		Criteria crit = session.createCriteria(QuitJobCause.class);

		if (cat != null)
			crit.add(Restrictions.eq("causeType", cat));

		List<QuitJobCause> list = crit.list();
		return list;
	}

	public List<Object[]> getListRetiredEmployeeSearch(Organization organization, QuitJobCauseName causetype) {

		String sql = "SELECT COUNT(e),e.quitCause FROM QuitJob e";
		String where = "";

		if (causetype != null)
			where += "  e.quitCause.quitCauseName = :cause ";

		if (organization != null)
			where = " e.employee.organization = :org and" + where;

		if (!where.equals(""))
			sql += " WHERE " + where;

		sql = sql + " GROUP BY e.quitCause.quitCauseName";

		Query q = session.createQuery(sql);

		if (organization != null)
			q.setParameter("org", organization);

		if (causetype != null)
			q.setParameter("cause", causetype);

		if (q.list().isEmpty())
			return null;

		List<Object[]> list = q.list();

		return list;

	}

	/* Jul 25, 2012 B.Erdenetuya begin */

	/* Jul 25, 2012 G.Bileg-Ochir begin */

	public List<Object[]> getListEmployeeTrainingReport(Organization org, Date fromDate, Date toDate,
			AppointmentType appointment) {
		String sql = "SELECT COUNT(e) FROM Training e ";

		String where = "";

		if (org != null)
			where = "e.employee.organization = :org AND";

		if (fromDate == null && toDate != null)
			where = where + " e.startDate BETWEEN CURDATE() AND :toDate AND";

		if (fromDate != null && toDate == null)
			where = where + " e.startDate BETWEEN :fromDate AND CURDATE() AND";

		if (fromDate != null && toDate != null)
			where = where + " e.startDate BETWEEN :fromDate AND :toDate AND";

		if (appointment != null)
			where = where + "e.appointment.appointmentType = :appointment AND";

		if (!where.equals("")) {
			where = where.substring(0, (where.length() - 4));
			sql += " WHERE " + where;
		}

		// sql = sql + " GROUP BY e.employee.organization";

		Query q = session.createQuery(sql);

		if (org != null)
			q.setParameter("org", org);

		if (appointment != null)
			q.setParameter("appointment", appointment);

		if (fromDate != null)
			q.setParameter("fromDate", fromDate);

		if (toDate != null)
			q.setParameter("toDate", toDate);

		if (q.list().isEmpty())
			return null;

		List<Object[]> list = q.list();

		return list;
	}

	/*
	 * public List<Object[]> getListEmployeeDegreeGradeReport( Organization
	 * organization, AppointmentType appointmentType) {
	 * 
	 * String sql = "SELECT COUNT(e.appointmentLevel),e FROM DegreeGrade e";
	 * 
	 * String where = "";
	 * 
	 * if (organization != null) where = " e.employee.organization = :org AND";
	 * 
	 * if (appointmentType != null) where =
	 * " e.employee.appointment.appointmentType = :appointment AND";
	 * 
	 * if (!where.equals("")) { where = where.substring(0, (where.length() -
	 * 4)); sql += " WHERE " + where; }
	 * 
	 * sql = sql + " GROUP BY e.appointmentLevel";
	 * 
	 * Query q = session.createQuery(sql);
	 * 
	 * if (organization != null) q.setParameter("org", organization);
	 * 
	 * if (organization != null) q.setParameter("appointment", appointmentType);
	 * 
	 * List<Object[]> list = q.list();
	 * 
	 * return list; }
	 */
	public List<Object[]> getListEmployeeDegreeGradeReport(Organization organization) {

		String sql = "SELECT COUNT(military) as milCount,e FROM EmployeeMilitary e "
				+ " WHERE (e.employee, e.olgosonOgnoo) IN (SELECT employee, max(olgosonOgnoo) FROM EmployeeMilitary GROUP BY employee)";

		String where = "";

		if (organization != null)
			where = " AND e.employee.organization = :org";

		if (!where.equals("")) {
			// where = where.substring(0, (where.length() - 4));
			sql += " " + where;
		}

		sql = sql + " GROUP BY e.military";

		Query q = session.createQuery(sql);

		if (organization != null)
			q.setParameter("org", organization);

		List<Object[]> list = q.list();

		return list;
	}

	/* Jul 25, 2012 G.Bileg-Ochir end */

	/* Jul 25, 2012 B.Erdenetuya end */

	public List<Object[]> getListRetireSoonReport(Organization organization) {

		String sql = "SELECT COUNT(DISTINCT e),e.gender FROM Employee e";

		String where = "";

		if (organization != null)
			where = " AND e.organization = :org ";

		sql += " WHERE ((e.gender = :male AND (DATEDIFF(CURDATE(),e.birthDate) >= 21915 )) "
				+ "OR (e.gender = :female AND (DATEDIFF(CURDATE(),e.birthDate) >= 20089)))  " + where;

		sql = sql + " GROUP BY e.gender";

		Query q = session.createQuery(sql);

		if (organization != null)
			q.setParameter("org", organization);

		q.setParameter("male", Gender.MALE);
		q.setParameter("female", Gender.FEMALE);

		List<Object[]> list = q.list();

		return list;
	}

	public List<Object[]> getListGeneralJudgeReport(Organization org) {
		String sql = "SELECT COUNT(DISTINCT d.employee),d.employee.appointment.appointmentLevel FROM Displacement d ";
		String where = "";

		if (org != null)
			where = " AND e.organization = :org";

		sql += "WHERE d.employee.appointment.appointmentLevel =:general AND (DATEDIFF(CURDATE(),d.issuedDate) >= 1461) "
				+ where;

		sql += " GROUP BY d.employee.appointment.appointmentLevel";

		Query q = session.createQuery(sql);

		if (org != null)
			q.setParameter("org", org);
		q.setParameter("general", AppointmentLevel.general);

		List<Object[]> list = q.list();

		return list;

	}

	/* Jul 25, 2012 B.Erdenetuya begin */

	public List<Object[]> getListPromotionReport(Organization org, Integer year) {
		String sql = "SELECT COUNT(e),d.organization.name FROM Employee e JOIN e.displacement d ";
		String where = "";

		if (org != null)
			where = " AND d.organization = :org";
		if (year != null)
			where += " AND year(d.issuedDate) = :year ";

		sql += "WHERE d.organization <> d.toOrganization AND d.organization IS NOT NULL " + where;

		sql += " GROUP BY d.organization";

		Query q = session.createQuery(sql);

		if (org != null)
			q.setParameter("org", org);

		if (year != null)
			q.setParameter("year", year);

		if (q.list().isEmpty())
			return null;

		List<Object[]> list = q.list();

		return list;

	}

	public List<Object[]> getListMovementReport(Organization org, Integer year) {
		String sql = "SELECT COUNT(e),d.toOrganization.name,d.organization.name FROM Employee e JOIN e.displacement d ";
		String where = "";

		if (org != null)
			where = " AND d.toOrganization = :org";
		if (year != null)
			where += " AND year(d.issuedDate) = :year ";

		sql += "WHERE d.organization <> d.toOrganization AND d.toOrganization IS NOT NULL " + where;

		sql += " GROUP BY d.organization";

		Query q = session.createQuery(sql);

		if (org != null)
			q.setParameter("org", org);

		if (year != null)
			q.setParameter("year", year);

		if (q.list().isEmpty())
			return null;

		List<Object[]> list = q.list();

		return list;

	}

	public List<TuAaLevel> getListTuAaLevel() {
		Criteria crit = session.createCriteria(TuAaLevel.class);

		List<TuAaLevel> list = crit.list();

		return list;
	}

	public List<UtTzTtTuLevel> getListUtTzTtTuLevel() {
		Criteria crit = session.createCriteria(UtTzTtTuLevel.class);

		List<UtTzTtTuLevel> list = crit.list();

		return list;
	}

	/* Jul 26, 2012 B.Erdenetuya end */

	/* Jul 26, 2012 T.Maralerdene begin */
	public Integer getFirstYearSalaryHistory() {
		Criteria crit = session.createCriteria(SalaryHistory.class);

		crit.setProjection(Projections.property("year"));
		crit.addOrder(Order.asc("year"));

		if (crit.list().isEmpty())
			return null;

		return (Integer) crit.list().get(0);

	}

	/* Jul 26, 2012 T.Maralerdene end */

	/* Jul 26, 2012 G.Bileg-Ochir begin */

	public Long getPromoteReport(Organization org, Date fromDate, Date toDate, AppointmentType appointmentType) {
		String sql = "SELECT COUNT(DISTINCT c.employee) FROM PromoteHistory c ";

		String where = "";

		if (org != null)
			where = "c.promoteOrganization = :org AND";

		if (fromDate == null && toDate != null)
			where = where + " c.promoteDate BETWEEN CURDATE() AND :toDate AND";

		if (fromDate != null && toDate == null)
			where = where + " c.promoteDate BETWEEN :fromDate AND CURDATE() AND";

		if (fromDate != null && toDate != null)
			where = where + " c.promoteDate BETWEEN :fromDate AND :toDate AND";

		if (appointmentType != null)
			where = where + " c.appointment.appointmentType BETWEEN :appointmentType";

		if (!where.equals("")) {
			where = where.substring(0, (where.length() - 4));
			sql += " WHERE " + where;
		}

		// sql = sql + " GROUP BY c.promoteOrganization";

		Query q = session.createQuery(sql);

		if (org != null)
			q.setParameter("org", org);

		if (fromDate != null)
			q.setParameter("fromDate", fromDate);

		if (toDate != null)
			q.setParameter("toDate", toDate);

		if (appointmentType != null)
			q.setParameter("appointmentType", appointmentType);

		if (q.list().isEmpty())
			return null;

		return (Long) q.list().get(0);
	}

	/* Jul 26, 2012 G.Bileg-Ochir end */

	/* Jul 26, 2012 B.Erdenetuya end */

	public List<QuitJobCause> getListQuitJobCause() {
		Criteria crit = session.createCriteria(QuitJobCause.class);

		List<QuitJobCause> list = crit.list();
		return list;
	}

	public Integer getFirstYearDisplacementHistory() {
		Criteria crit = session.createCriteria(Displacement.class);

		crit.setProjection(Projections.property("issuedDate"));
		crit.addOrder(Order.asc("issuedDate"));

		if (crit.list().isEmpty())
			return null;

		Date date = (Date) crit.list().get(0);

		Calendar calendar = Calendar.getInstance();

		if (date != null)
			calendar.setTime(date);

		Integer year = calendar.get(Calendar.YEAR);

		return year;

	}

	/* Jul 26, 2012 B.Erdenetuya begin */

	/* Jul 27, 2012 tuguldur.j Start */

	public List<Object[]> getListHonourReport(Organization org, Date fromDate, Date toDate,
			AppointmentType appointment) {
		String sql = "SELECT COUNT(h), h.awardType FROM Honour h ";

		String where = "";

		if (org != null)
			where = "h.employee.organization = :org AND ";

		if (fromDate != null && toDate != null)
			where = where + "h.dateOfAwarded BETWEEN :fromDate AND :toDate AND ";

		if (appointment != null)
			where = where + "h.appointment.appointmentType = :appointment AND ";

		if (!where.equals("")) {
			where = where.substring(0, (where.length() - 4));
			sql += "WHERE " + where;
		}

		sql = sql + "GROUP BY h.awardType";
		Query q = session.createQuery(sql);

		if (org != null)
			q.setParameter("org", org);

		if (appointment != null)
			q.setParameter("appointment", appointment);

		if (fromDate != null)
			q.setParameter("fromDate", fromDate);

		if (toDate != null)
			q.setParameter("toDate", toDate);

		if (q.list().isEmpty())
			return null;

		List<Object[]> list = q.list();

		return list;
	}

	/* Jul 27, 2012 tuguldur.j End */

	public Long getTotalWorkedYear(Long empID) {

		Object d = 0, i = 0, c = 0;
		String sql = sql = "SELECT SUM(datediff(e.endDate,e.startDate)) FROM Experience e"
				+ " WHERE e.employee.id = :emp";

		Query q = session.createQuery(sql);

		if (empID != null)
			q.setParameter("emp", empID);

		if (q == null)
			d = 0;
		else
			d = q.list().get(0);

		sql = "SELECT SUM(datediff(CURDATE(),e.startDate)) FROM Experience e"
				+ " WHERE e.employee.id = :emp AND e.endDate IS NULL";

		Query q1 = session.createQuery(sql);

		if (empID != null)
			q1.setParameter("emp", empID);

		if (q1 != null)
			i = q1.list().get(0);

		Long a = 0l;
		if (d != null)
			a = (Long) d;

		if (i != null)
			a += (Long) i;

		return a;

	}

	public Long getStateOrganizationWorkedYear(Employee emp, OrganizationTypeExperience orgType) {

		Object d = 0, i = 0, c = 0;
		String sql = "SELECT SUM(datediff(CURDATE(),e.startDate)) FROM Experience e"
				+ " WHERE e.employee = :emp AND e.organizationtype=:orgType AND e.endDate IS NULL";

		Query q = session.createQuery(sql);

		if (emp != null)
			q.setParameter("emp", emp);
		if (orgType != null)
			q.setParameter("orgType", orgType);

		if (q == null)
			d = 0;
		else
			d = q.list().get(0);

		sql = "SELECT SUM(datediff(e.endDate,e.startDate)) FROM Experience e"
				+ " WHERE e.employee = :emp AND e.organizationtype=:orgType ";

		Query q1 = session.createQuery(sql);

		if (emp != null)
			q1.setParameter("emp", emp);
		if (orgType != null)
			q1.setParameter("orgType", orgType);

		if (q1 != null)
			i = q1.list().get(0);

		Long a = 0l;
		if (d != null)
			a = (Long) d;

		if (i != null)
			a += (Long) i;

		return a;

	}

	public Long getCurrentOrganizationWorkedYear(Employee emp, OrganizationTypeExperience orgType) {

		Object d = 0, i = 0, c = 0;
		String sql = "SELECT SUM(datediff(CURDATE(),e.startDate)) FROM Experience e "
				+ "WHERE e.employee = :emp AND e.organizationtype=:orgType  AND e.endDate IS NULL";

		Query q = session.createQuery(sql);

		if (emp != null)
			q.setParameter("emp", emp);
		if (orgType != null)
			q.setParameter("orgType", orgType);

		if (q == null)
			d = 0;
		else
			d = q.list().get(0);

		sql = "SELECT SUM(datediff(e.endDate,e.startDate)) FROM Experience e"
				+ " WHERE e.employee = :emp AND e.organizationtype=:orgType";

		Query q1 = session.createQuery(sql);

		if (emp != null)
			q1.setParameter("emp", emp);
		if (orgType != null)
			q1.setParameter("orgType", orgType);

		if (q1 != null)
			i = q1.list().get(0);

		Long a = 0l;
		if (d != null)
			a = (Long) d;

		if (i != null)
			a += (Long) i;

		return a;

	}

	/* Aug 6, 2012 G.Bileg-Ochir begin */

	public Educations getCurrentEducation(Long empID) {
		Criteria crit = session.createCriteria(Educations.class);

		if (empID != null)
			crit.add(Restrictions.eq("employee.id", empID));

		crit.addOrder(Order.desc("graduatedDate"));

		if (crit.list() == null)
			return null;

		return (Educations) crit.list().get(0);
	}

	/* Aug 6, 2012 G.Bileg-Ochir end */

	/* Aug 7, 2012 G.Bileg-Ochir begin */

	public List<Notice> getListNotice() {
		Criteria crit = session.createCriteria(Notice.class);

		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<Notice> list = crit.list();

		return list;
	}

	/* Aug 7, 2012 G.Bileg-Ochir end */

	/* Aug 7, 2012 G.Bileg-Ochir begin */

	public List<Object[]> getListDisplacementTypeSearch(Organization organization, String type, Gender gender) {

		String sql = "";

		if (type.equals("utTzTtTu"))
			sql = "SELECT COUNT(DISTINCT e.employee),e.utTzTtTuLevel.utTzTtTuSort FROM Displacement e";
		else
			sql = "SELECT COUNT(DISTINCT e.employee),e.tuAaLevel.tuAaSort FROM Displacement e";

		String where = "";

		if (organization != null)
			where += "e.employee.organization = :org AND";

		if (gender != null)
			where += " e.employee.gender = :gender AND";

		if (!where.equals("")) {
			where = where.substring(0, (where.length() - 4));
			sql += " WHERE " + where;
		}

		if (type == "utTzTtTu")
			sql = sql + " GROUP BY e.utTzTtTuLevel.utTzTtTuSort";
		else
			sql = sql + " GROUP BY e.tuAaLevel.tuAaSort";

		Query q = session.createQuery(sql);

		if (organization != null)
			q.setParameter("org", organization);

		if (gender != null) {
			q.setParameter("gender", gender);
		}

		List<Object[]> list = q.list();

		return list;
	}

	/* Aug 7, 2012 G.Bileg-Ochir end */

	/* Aug 9, 2012 B.Erdenetuya begin */

	public List<LoginHistory> getListLoginHistory(Employee emp) {

		Criteria crit = session.createCriteria(LoginHistory.class);
		crit.add(Restrictions.eq("employee", emp));
		crit.addOrder(Order.desc("loginDate"));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<LoginHistory> list = crit.list();
		if (crit.list().isEmpty() || list.size() < 5)
			return list;
		else
			return list.subList(0, 5);

	}

	public List<Appointment> getJudgeAppointment() {
		String sql = "SELECT d FROM Appointment d";
		String where = "";

		sql += " WHERE d.appointmentType =:app" + where;

		Query q = session.createQuery(sql);

		q.setParameter("app", AppointmentType.JUDGE);

		if (q.list().isEmpty())
			return null;

		List<Appointment> list = q.list();

		return list;
	}

	/* Aug 9, 2012 B.Erdenetuya end */

	/* Aug 10, 2012 G.Bileg-Ochir begin */

	public List<OpenPosition> getListOpenPositionSearch(OpenPositionSearchBean bean) {

		String sql = "SELECT s FROM OpenPosition s ";
		String where = "";

		if (bean.getRegisteredDate() != null)
			where = where + " s.registeredDate = :registeredDate  AND";

		if (bean.getPositionTerm() != null)
			where = where + " s.positionTerm = :positionTerm  AND";

		if (!where.equals("")) {
			where = where.substring(0, (where.length() - 4));

			sql += "WHERE " + where;
		}

		Query q = session.createQuery(sql);

		if (bean.getRegisteredDate() != null)
			q.setParameter("registeredDate", bean.getRegisteredDate());

		if (bean.getPositionTerm() != null)
			q.setParameter("positionTerm", bean.getPositionTerm());

		List<OpenPosition> openPosition = q.list();

		return openPosition;
	}

	/* Aug 10, 2012 G.Bileg-Ochir end */

	/* Aug 13, 2012 G.Bileg-Ochir begin */

	/* Aug 13, 2012 G.Bileg-Ochir begin */

	public List<Object[]> getListPhoneReport(Organization org) {
		String sql = "SELECT s.mobileNo FROM Employee s ";
		String where = "";

		if (org != null)
			where = where + " s.organization = :organization AND";

		if (!where.equals("")) {
			where = where.substring(0, (where.length() - 4));

			sql += "WHERE " + where;
		}

		Query q = session.createQuery(sql);

		List<Object[]> employee = q.list();

		return employee;
	}

	/* Aug 13, 2012 G.Bileg-Ochir end */

	/* Aug 13, 2012 B.Erdenetuya end */

	public List<EducationDegree> getListEducationDegree() {
		Criteria crit = session.createCriteria(EducationDegree.class);

		List<EducationDegree> list = crit.list();

		return list;
	}

	public List<ForeignLanguage> getListForeignLanguage() {
		Criteria crit = session.createCriteria(ForeignLanguage.class);
		List<ForeignLanguage> list = crit.list();
		return list;
	}

	public List<ComputerProgram> getListComputerProgram() {
		Criteria crit = session.createCriteria(ComputerProgram.class);

		List<ComputerProgram> list = crit.list();

		return list;
	}

	public List<ComputerOther> getListComputerOther(Employee emp) {
		Criteria crit = session.createCriteria(ComputerOther.class);
		crit.add(Restrictions.eq("employee", emp));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<ComputerOther> list = crit.list();

		return list;

	}

	public List<Facility> getListFacility() {
		Criteria crit = session.createCriteria(Facility.class);

		List<Facility> list = crit.list();

		return list;
	}

	public List<OfficeEquipment> getListOfficeEquipment(Employee emp) {
		Criteria crit = session.createCriteria(OfficeEquipment.class);
		crit.add(Restrictions.eq("employee", emp));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<OfficeEquipment> list = crit.list();

		return list;

	}

	/* Aug 13, 2012 B.Erdenetuya begin */

	/* Aug 15, 2012 B.Erdenetuya end */
	public Long getEmployeeAge(Employee emp) {

		Object d = 0;
		String sql = "SELECT SUM(datediff(CURDATE(),e.birthDate)) FROM Employee e" + " WHERE e= :emp";

		Query q = session.createQuery(sql);

		if (emp != null)
			q.setParameter("emp", emp);

		if (q.list().isEmpty())
			d = 0;
		else
			d = q.list().get(0);

		Long a = 0l;
		if (d != null)
			a = (Long) d;

		return a;
	}

	/* Aug 15, 2012 B.Erdenetuya begin */

	/* Aug 13, 2012 G.Bileg-Ochir begin */

	public List<Saving> getListSavingType() {
		Criteria crit = session.createCriteria(Saving.class);

		List<Saving> list = crit.list();

		return list;
	}

	/* Aug 13, 2012 G.Bileg-Ochir end */

	/* Aug 15, 2012, 2012 G.Bileg-Ochir begin */

	public List<OpenPosition> getListOpenPosition() {
		Criteria crit = session.createCriteria(OpenPosition.class);

		List<OpenPosition> list = crit.list();

		return list;
	}

	/* Aug 15, 2012, G.Bileg-Ochir end */

	/* Aug 20, 2012 B.Erdenetuya end */
	public Long getJudgeWorkedYear(Employee emp, Boolean judge) {

		Object d = 0, i = 0, c = 0;
		String sql = "SELECT SUM(datediff(CURDATE(),e.startDate)) FROM Experience e"
				+ " WHERE e.employee = :emp AND e.isJudge=:judge AND e.endDate IS NULL";

		Query q = session.createQuery(sql);

		if (emp != null)
			q.setParameter("emp", emp);
		if (judge != null)
			q.setParameter("judge", judge);

		if (q.list().isEmpty())
			d = 0;
		else
			d = q.list().get(0);

		sql = "SELECT SUM(datediff(e.endDate,e.startDate)) FROM Experience e"
				+ " WHERE e.employee = :emp AND e.isJudge=:judge";

		Query q1 = session.createQuery(sql);

		if (emp != null)
			q1.setParameter("emp", emp);
		if (judge != null)
			q1.setParameter("judge", judge);

		if (!q1.list().isEmpty())
			i = q1.list().get(0);

		Long a = 0l;
		if (d != null)
			a = (Long) d;

		if (i != null)
			a += (Long) i;

		return a;

	}

	public Long getMajorWorkedYear(Employee emp, Boolean major) {

		Object d = 0, i = 0, c = 0;
		String sql = "SELECT SUM(datediff(CURDATE(),e.startDate)) FROM Experience e"
				+ " WHERE e.employee = :emp AND e.isMajor=:major AND e.endDate IS NULL";

		Query q = session.createQuery(sql);

		if (emp != null)
			q.setParameter("emp", emp);
		if (major != null)
			q.setParameter("major", major);

		if (q.list().isEmpty())
			d = 0;
		else
			d = q.list().get(0);

		sql = "SELECT SUM(datediff(e.endDate,e.startDate)) FROM Experience e"
				+ " WHERE e.employee = :emp AND e.isMajor=:major";

		Query q1 = session.createQuery(sql);

		if (emp != null)
			q1.setParameter("emp", emp);
		if (major != null)
			q1.setParameter("major", major);

		if (!q1.list().isEmpty())
			i = q1.list().get(0);

		Long a = 0l;
		if (d != null)
			a = (Long) d;

		if (i != null)
			a += (Long) i;

		return a;

	}

	/* Aug 20, 2012 B.Erdenetuya end */
	/* Aug 20, 2012 B.Erdenetuya begin */

	public List<TravelAbroad> getListTravelAbroad(Employee emp) {
		Criteria crit = session.createCriteria(TravelAbroad.class);
		crit.add(Restrictions.eq("employee", emp));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<TravelAbroad> list = crit.list();

		return list;
	}

	public List<Passport> getListPassport(Employee emp) {
		Criteria crit = session.createCriteria(Passport.class);
		crit.add(Restrictions.eq("employee", emp));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Passport> list = crit.list();
		return list;
	}

	public List<AppointmentSort> getAppointmentSort() {
		Criteria crit = session.createCriteria(AppointmentSort.class);

		List<AppointmentSort> list = crit.list();

		return list;
	}

	public List<AppointmentSort> getAppointmentSort(AppointmentType type) {
		Criteria crit = session.createCriteria(AppointmentSort.class);
		if (type != null)
			crit.add(Restrictions.eq("appointmentType", type));
		List<AppointmentSort> list = crit.list();

		return list;
	}

	public List<mn.mcs.electronics.court.entities.configuration.AppointmentLevel> getListAppointmentLevel(
			AppointmentSort cat, AppointmentType type) {
		Criteria crit = session.createCriteria(mn.mcs.electronics.court.entities.configuration.AppointmentLevel.class);
		if (cat != null)
			crit.add(Restrictions.eq("appointmentSort", cat));
		crit.add(Restrictions.eq("appointmentType", type));
		List<mn.mcs.electronics.court.entities.configuration.AppointmentLevel> list = crit.list();

		return list;
	}

	public List<mn.mcs.electronics.court.entities.configuration.AppointmentLevel> getListAppointmentLevel() {
		Criteria crit = session.createCriteria(mn.mcs.electronics.court.entities.configuration.AppointmentLevel.class);
		List<mn.mcs.electronics.court.entities.configuration.AppointmentLevel> list = crit.list();

		return list;
	}

	/* Aug 20, 2012 B.Erdenetuya begin */

	/* Aug 27, 2012 G.Bileg-Ochir begin */

	public List<RetireAge> getListRetireAge() {
		Criteria crit = session.createCriteria(RetireAge.class);

		List<RetireAge> list = crit.list();

		return list;
	}

	public List<Employee> getEmployeeByRegister(String register) {

		Criteria crit = session.createCriteria(Employee.class);

		crit.add(Restrictions.eq("registerNo", register));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Employee> list = crit.list();
		return list;
	}

	/* Aug 27, 2012 B.Erdenetuya begin */
	public List<Demerit> getListDemerit(Employee emp) {
		Criteria crit = session.createCriteria(Demerit.class);
		crit.add(Restrictions.eq("employee", emp));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Demerit> list = crit.list();

		return list;
	}

	public List<TravelType> getListTravelType() {
		Criteria crit = session.createCriteria(TravelType.class);

		List<TravelType> list = crit.list();

		return list;
	}

	public List<FamilyAppointmentType> getListFamilyAppointmentType() {
		Criteria crit = session.createCriteria(FamilyAppointmentType.class);

		List<FamilyAppointmentType> list = crit.list();

		return list;
	}

	/* Aug 27, 2012 B.Erdenetuya end */

	/* Sep 8, 2012 G.Bileg-Ochir begin */

	public List<OpenPositionList> getListOpenPositionListSearch(Integer opNumber, OpenPositionListSearchBean bean) {

		String sql = "SELECT s FROM OpenPositionList s ";
		String where = "";

		if (opNumber != null)
			where = where + " s.opNumber = :opNumber AND";

		if (bean.getOrganization() != null)
			where = where + " s.organization = :organization AND";

		if (bean.getAppointment() != null)
			where = where + " s.appointment = :appointment AND";

		if (!where.equals("")) {
			where = where.substring(0, (where.length() - 4));

			sql += "WHERE " + where;
		}

		Query q = session.createQuery(sql);

		if (bean.getOrganization() != null)
			q.setParameter("organization", bean.getOrganization());

		if (bean.getAppointment() != null)
			q.setParameter("appointment", bean.getAppointment());

		if (opNumber != null)
			q.setParameter("opNumber", opNumber);

		List<OpenPositionList> openPosition = q.list();

		return openPosition;
	}

	/* Sep 8, 2012 G.Bileg-Ochir end */

	/* Sep 10, 2012 G.Bileg-Ochir begin */

	public List<ApprovedPositions> getListApprovedPosition(Organization org, Integer belenOronToo) {

		String sql = "SELECT COUNT(e),e.appointment FROM Employee e ";

		String where = "";

		if (org != null)
			where += " e.organization = :org AND";

		if (!where.equals("")) {

			where = where.substring(0, (where.length() - 4));
			sql += " WHERE " + where;
		}

		sql = sql + " GROUP BY e.appointment";

		Query q = session.createQuery(sql);

		if (org != null)
			q.setParameter("org", org);

		List<Object[]> listEmployee = q.list();

		Criteria crit = session.createCriteria(ApprovedPositions.class);
		crit.add(Restrictions.eq("organization", org));
		List<ApprovedPositions> list = crit.list();

		for (int i = 0; i < listEmployee.size(); i++) {
			for (int j = 0; j < list.size(); j++) {

				Appointment d = new Appointment();
				d = (Appointment) listEmployee.get(i)[1];

				if (d.getId() == list.get(j).getAppointment().getId()) {
					int count = Integer.parseInt(listEmployee.get(i)[0].toString());
					if (count >= list.get(j).getEstablishment())
						list.remove(j);
				}
			}
		}

		return list;
	}

	/* Sep 10, 2012 G.Bileg-Ochir end */

	/* Sep 10, 2012 B.Erdenetuya begin */

	public List<WorkedYear> getListWorkedYear() {
		Criteria crit = session.createCriteria(WorkedYear.class);

		List<WorkedYear> list = crit.list();

		return list;
	}

	/* Sep 10, 2012 B.Erdenetuya end */

	/* Sep 12, 2012 B.Erdenetuya begin */
	public List<Employee> getMajorWorkedYearEmployee(Long year) {

		String sql = "SELECT e FROM Employee e WHERE (SELECT SUM(datediff(COALESCE(ex.endDate, CURDATE()) ,ex.startDate)) FROM Experience ex "
				+ "WHERE ex.employee =e AND ex.isMajor=:major)>:daysMajor ";

		Query q = session.createQuery(sql);

		q.setParameter("major", true);

		if (year != null)
			q.setParameter("daysMajor", year);

		List<Employee> workedMajorEmployee = q.list();

		return workedMajorEmployee;

	}

	public List<Employee> getStateWorkedYearEmployee(Long year) {

		String sql = "SELECT e FROM Employee e WHERE (SELECT SUM(datediff(COALESCE(ex.endDate, CURDATE()),ex.startDate)) FROM Experience ex WHERE ex.employee =e "
				+ "AND (ex.organizationtype=:orgType1 OR ex.organizationtype=:orgType2 ) AND ex.endDate IS NOT NULL)>:daysState ";

		Query q = session.createQuery(sql);

		q.setParameter("orgType1", OrganizationTypeExperience.ULSIIN);

		q.setParameter("orgType2", OrganizationTypeExperience.SHUUH);

		if (year != null)
			q.setParameter("daysState", year);

		List<Employee> workedMajorEmployee = q.list();

		return workedMajorEmployee;

	}

	/* Sep 12, 2012 B.Erdenetuya end */

	/* Sep 12, 2012 G.Bileg-Ochir begin */

	public List<ApprovedPositions> getListApprovedPositions(Organization org) {
		Criteria crit = session.createCriteria(ApprovedPositions.class);
		if (org != null)
			crit.add(Restrictions.eq("organization", org));
		crit.addOrder(Order.asc("appointment"));
		List<ApprovedPositions> list = crit.list();
		return list;
	}

	// SELECT COUNT(*) FROM employee WHERE employee.organization=14 GROUP BY
	// employee.appointment;

	public List<Object[]> getListAppointmentQuantity(Organization organization) {
		String sql = "SELECT COUNT(e),e.appointment FROM Employee e ";

		String where = "";

		if (organization != null)
			where += " e.organization = :org AND";

		if (!where.equals("")) {

			where = where.substring(0, (where.length() - 4));
			sql += " WHERE " + where;
		}

		sql = sql + " GROUP BY e.appointment";

		Query q = session.createQuery(sql);

		if (organization != null)
			q.setParameter("org", organization);

		List<Object[]> list = q.list();

		return list;
	}

	/* Sep 12, 2012 G.Bileg-Ochir end */

	public List<User> getListUserName() {
		Criteria crit = session.createCriteria(User.class);

		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<User> list = crit.list();

		return list;
	}

	public List<UserIP> getListUserIP() {
		Criteria crit = session.createCriteria(UserIP.class);

		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<UserIP> list = crit.list();

		return list;

	}

	/* Sep 14, 2012 G.Bileg-Ochir begin */

	public List<Appointment> getListAppointmentByName(String app) {
		Criteria crit = session.createCriteria(Appointment.class);

		if (app != null)
			crit.add(Restrictions.eq("appointmentName", app));

		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<Appointment> list = crit.list();

		return list;
	}

	/* Sep 14, 2012 G.Bileg-Ochir end */

	/* Oct 1, 2012 G.Bileg-Ochir begin */

	public List<Role> getRoleListByEmployee(String user) {

		Criteria crit = session.createCriteria(Role.class);

		crit.add(Restrictions.eq("name", user));

		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<Role> list = crit.list();

		return list;
	}

	/* Oct 1, 2012 G.Bileg-Ochir end */

	/* Oct 1, 2012 B.Erdenetuya begin */

	public List<Appointment> getListAppointmentOrg() {
		Criteria crit = session.createCriteria(Appointment.class);

		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<Appointment> list = crit.list();

		return list;
	}

	public List<Appointment> getListAppointmentByType(OccupationType type) {
		Criteria crit = session.createCriteria(Appointment.class);

		if (type != null)
			crit.add(Restrictions.eq("occupationType", type));

		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<Appointment> list = crit.list();

		return list;
	}

	/* Oct 1, 2012 B.Erdenetuya end */

	/* Nov 13, 2012 G.Bileg-Ochir begin */

	public List<ProjectConfig> getProjectHeader() {
		String sql = "SELECT e FROM ProjectConfig e ";

		Query q = session.createQuery(sql);

		List<ProjectConfig> list = q.list();

		return list;
	}

	public List<ProjectConfig> getProjectConfig() {

		Criteria crit = session.createCriteria(ProjectConfig.class);
		List<ProjectConfig> list = crit.list();

		return list;
	}

	/* Nov 13, 2012 G.Bileg-Ochir end */

	/* Nov 15, 2012 G.Bileg-Ochir begin */

	public List<Permission> getAdminPermissionList() {
		Criteria crit = session.createCriteria(Permission.class);

		crit.addOrder(Order.asc("permissionName"));

		List<Permission> list = crit.list();

		return list;
	}

	/* Nov 15, 2012 G.Bileg-Ochir end */

	/* Nov 20, 2012 G.Bileg-Ochir begin */

	public List<ProjectMenuConfig> getProjectMenuConfig() {

		Criteria crit = session.createCriteria(ProjectMenuConfig.class);
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<ProjectMenuConfig> list = crit.list();

		return list;
	}

	public List<ProjectMenuConfig> getProjectMenu(Integer location) {

		Criteria crit = session.createCriteria(ProjectMenuConfig.class);

		if (location != null)
			crit.add(Restrictions.eq("menuLocation", location));

		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<ProjectMenuConfig> list = crit.list();

		return list;
	}

	/* Nov 20, 2012 G.Bileg-Ochir end */

	/* Nov 24, 2012 G.Bileg-Ochir begin */

	public List<Organization> getListOrganizationByName(String orgName) {

		Criteria crit = session.createCriteria(Organization.class);

		if (orgName != null)
			crit.add(Restrictions.eq("name", orgName));

		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Organization> list = crit.list();
		return list;
	}

	/* Nov 24, 2012 G.Bileg-Ochir end */

	/* Nov 20, 2012 G.Bileg-Ochir end */

	/**
	 * @param regId
	 * @return Регистрийн дугаараар ажилтанг буцна
	 */
	public Employee getEmpByRegId(String regId) {

		String encryptedRegister = "";
		Criteria crit = session.createCriteria(Employee.class);

		if (regId != null)
			regId = regId.trim();

		try {
			encryptedRegister = AES.encrypt(regId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		crit.add(Restrictions.eq("registerNo", encryptedRegister));

		if (crit.list().size() > 0)
			return (Employee) crit.list().get(0);

		return null;
	}

	public Language getLanguage(Employee emp, String langName) {

		Criteria crit = session.createCriteria(Language.class);

		if (emp != null)
			crit.add(Restrictions.eq("employee", emp));

		if (langName != null)
			crit.add(Restrictions.eq("foreignLanguage", langName));

		if (crit.list().size() > 0)
			return (Language) crit.list().get(0);

		return null;
	}

	/* Nov 26, 2012 B.Erdenetuya begin */

	public Employee getEmpByRegister(String register) {

		String encryptedRegister = "";

		Criteria crit = session.createCriteria(Employee.class);

		try {
			encryptedRegister = AES.encrypt(register);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		crit.add(Restrictions.eq("registerNo", encryptedRegister));

		List<Employee> list = crit.list();
		return list.get(0);
	}

	/* Nov 26, 2012 B.Erdenetuya end */

	public List<Organization> getListEntityFields(Class obj) {

		Criteria crit = session.createCriteria(obj);
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Organization> list = crit.list();

		return list;
	}

	public List<SubDepartment> getListSubDepartment(Organization org, Department dep) {
		Criteria crit = session.createCriteria(SubDepartment.class);

		if (org != null)
			crit.add(Restrictions.eq("organization", org));

		if (dep != null)
			crit.add(Restrictions.eq("department", dep));

		List<SubDepartment> list = crit.list();

		return list;
	}

	/* Nov 27, 2012 G.Bileg-Ochir end */

	/* Nov 28, 2012 B.Erdenetuya begin */

	/* Цэргийн цолны жагсаалт */

	public List<MilitaryRank> getListMilitary() {
		Criteria crit = session.createCriteria(MilitaryRank.class);

		List<MilitaryRank> list = crit.list();

		return list;
	}

	public List<EmployeeMilitary> getListMilitaryByMilitaryRank(MilitaryRank militaryRank) {

		Criteria crit = session.createCriteria(EmployeeMilitary.class);
		crit.add(Restrictions.eq("military", militaryRank));

		return crit.list();
	}

	/* Ажилтны цэргийн цолны мэдээлэл */
	public List<EmployeeMilitary> getListEmployeeMilitary(Employee emp) {
		Criteria crit = session.createCriteria(EmployeeMilitary.class);

		if (emp != null)
			crit.add(Restrictions.eq("employee", emp));

		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		crit.addOrder(Order.desc("olgosonOgnoo"));

		List<EmployeeMilitary> list = crit.list();

		return list;
	}

	public List<MilitaryRank> getListMilitaryByType(MilitaryRankType type) {
		Criteria crit = session.createCriteria(MilitaryRank.class);

		if (type != null)
			crit.add(Restrictions.eq("militaryRankType", type));

		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		crit.addOrder(Order.asc("id"));

		List<MilitaryRank> list = crit.list();

		return list;
	}

	/* Nov 28, 2012 B.Erdenetuya end */

	/* Dec 07, 2012 Jargalsuren.S Start */
	public Long getMilitaryOrSimpleWorkedYear(Long empID, OrganizationTypeExperience orgType,
			MilitaryOrSimple militaryOrSimple) {
		Object d = 0, i = 0, c = 0;
		String sql = "SELECT SUM(datediff(CURDATE(),e.startDate)) FROM Experience e"
				+ " WHERE e.employee.id = :emp AND e.organizationtype=:orgType AND e.endDate IS NULL";

		if (militaryOrSimple != null)
			sql = sql + " AND e.militaryOrSimple = :militaryOrSimple";

		Query q = session.createQuery(sql);

		if (empID != null)
			q.setParameter("emp", empID);
		if (orgType != null)
			q.setParameter("orgType", orgType);
		if (militaryOrSimple != null)
			q.setParameter("militaryOrSimple", militaryOrSimple);

		if (q.list().isEmpty())
			d = 0;
		else
			d = q.list().get(0);

		sql = "SELECT SUM(datediff(e.endDate,e.startDate)) FROM Experience e"
				+ " WHERE e.employee.id = :emp AND e.organizationtype=:orgType";

		if (militaryOrSimple != null)
			sql = sql + " AND e.militaryOrSimple = :militaryOrSimple";

		Query q1 = session.createQuery(sql);

		if (empID != null)
			q1.setParameter("emp", empID);
		if (orgType != null)
			q1.setParameter("orgType", orgType);
		if (militaryOrSimple != null)
			q1.setParameter("militaryOrSimple", militaryOrSimple);

		if (!q1.list().isEmpty())
			i = q1.list().get(0);

		Long a = 0l;
		if (d != null)
			a = (Long) d;

		if (i != null)
			a += (Long) i;

		return a;
	}

	/* Dec 07, 2012 Jargalsuren.S End */

	/* Dec 19, 2012 Bolorchimeg Begin */

	public List<SahilgaShiitgel> getListSahilgaShiitgelByTypeSubject(SahilgaSubject subject) {

		Criteria crit = session.createCriteria(SahilgaShiitgel.class);

		/*
		 * if (type != null) crit.add(Restrictions.eq("sahilgaType", type));
		 */

		/*
		 * if (subject == null) crit.add(Restrictions.isNull("sahilgaSubject"));
		 * else crit.add(Restrictions.eq("sahilgaSubject", subject));
		 */

		List<SahilgaShiitgel> list = crit.list();
		return list;
	}

	public List<SahilgaShiitgel> getListSahilgaShiitgel() {

		Criteria crit = session.createCriteria(SahilgaShiitgel.class);
		List<SahilgaShiitgel> list = crit.list();
		return list;
	}

	public List<Sahilga> getListSahilga(Employee emp) {
		Criteria crit = session.createCriteria(Sahilga.class);
		crit.add(Restrictions.eq("employee", emp));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Sahilga> list = crit.list();

		return list;
	}

	public List<Sahilga> getListSahilga(SahilgaShiitgel sahilgaShiitgel) {
		Criteria crit = session.createCriteria(Sahilga.class);
		crit.add(Restrictions.eq("sahilgaShiitgel", sahilgaShiitgel));
		List<Sahilga> list = crit.list();
		return list;
	}

	public List<SahilgaTurul> getListSahilgaTurul() {

		Criteria crit = session.createCriteria(SahilgaTurul.class);
		List<SahilgaTurul> list = crit.list();
		return list;
	}

	/* Dec 19, 2012 Bolorchimeg End */

	/* Dec 26, 2012 G.Bileg-Ochir begin */

	public List<Permission> getComponentPermissionList() {
		Criteria crit = session.createCriteria(Permission.class);

		crit.add(Restrictions.eq("isComponent", true));
		crit.addOrder(Order.asc("permissionName"));

		List<Permission> list = crit.list();

		return list;
	}

	@Override
	public List<Employee> getListEmployeeDecrypt(Employee emp) {
		// TODO Auto-generated method stub
		return null;
	}

	/* Dec 26, 2012 G.Bileg-Ochir end */

	/*
	 * Сахилгын хугацаа дуусах дөхсөн 15 хоногийн өмнөөс сануулна.
	 */
	public List<DisciplinedEmployeeBean> getListEmployeeByDiscipline(HashSet<Organization> organization) {
		String where = "";

		if (organization != null)
			where += " AND e.organization IN (:orgs)";

		String sql = "SELECT DISTINCT(e.id),e.firstName,e.lastname,e.registerNo,e.gender,s.shiitgelAvagdsanOgnoo,s.sahilgaShiitgel.shiitgelName,s.shiitgelDuusahOgnoo"
				+ ",TO_DAYS(s.shiitgelDuusahOgnoo)-TO_DAYS(s.shiitgelAvagdsanOgnoo), e.organization.name FROM Employee e "
				+ "INNER JOIN e.sahilga s " + "WHERE"
				+ " TO_DAYS(NOW()) - TO_DAYS(s.shiitgelAvagdsanOgnoo ) > (s.sahilgaShiitgel.duration* 30 +15) AND "
				+ "s.arilgasanTushaalDugaar IS NULL " + where + "ORDER BY s.shiitgelAvagdsanOgnoo";

		Query q = session.createQuery(sql);

		if (organization != null)
			q.setParameterList("orgs", organization);

		List<DisciplinedEmployeeBean> lst = new ArrayList<DisciplinedEmployeeBean>();

		List<Object[]> list = q.list();

		for (Object[] obj : list) {

			DisciplinedEmployeeBean deb = new DisciplinedEmployeeBean();

			deb.setId((Long) obj[0]);

			deb.setFirstname((String) obj[1]);

			deb.setLastname((String) obj[2]);

			deb.setRegisterNo((String) obj[3]);

			deb.setGender((Gender) obj[4]);

			deb.setShiitgelAvagdsanOgnoo((Date) obj[5]);

			deb.setShiitgelName((String) obj[6]);

			deb.setShiitgelDuusahOgnoo((Date) obj[7]);

			deb.setHetersenHonog(obj[8] + "");

			deb.setOrg((String) obj[9]);

			lst.add(deb);
		}

		return lst;
	}

	/*
	 * Цэргийн цол авах хугацаа дөхсөн 30 хоногийн өмнөөс сануулна.
	 */
	public List<AlertMilitaryRankEmployeeBean> getListEmployeeByMilitary(HashSet<Organization> organization) {

		String sql = "SELECT DISTINCT(e.id),e.firstName,e.lastname,m.employee,e.gender,m.military.militaryName,m.olgosonOgnoo,m.military.period "
				+ "FROM Employee e INNER JOIN e.employeeMilitary m " + " WHERE "
				+ " TO_DAYS(NOW()) - TO_DAYS(m.olgosonOgnoo) > (m.military.period * 12 * 30 + 30) "
				+ "AND m.olgosonOgnoo = (SELECT MAX(em.olgosonOgnoo) FROM EmployeeMilitary em WHERE em.employee = e) AND m.military.isTop != 1 ";

		if (organization != null)
			sql += " AND e.organization IN (:orgs)";

		Query q = session.createQuery("" + sql);

		if (organization != null)
			q.setParameterList("orgs", organization);

		List<AlertMilitaryRankEmployeeBean> lst = new ArrayList<AlertMilitaryRankEmployeeBean>();

		List<Object[]> list = q.list();

		for (Object[] obj : list) {

			AlertMilitaryRankEmployeeBean mreb = new AlertMilitaryRankEmployeeBean();

			mreb.setId((Long) obj[0]);

			mreb.setFirstname((String) obj[1]);

			mreb.setLastname((String) obj[2]);

			mreb.setEmployee((Employee) obj[3]);

			mreb.setGender((Gender) obj[4]);

			mreb.setOdoogiinTsol((String) obj[5]);

			mreb.setTsolAvsanOgnoo((Date) obj[6]);

			mreb.setTsolHugatsaa((Integer) obj[7]);

			// mreb.setTsolAvahOgnoo((Date)obj[7]);

			lst.add(mreb);
		}

		Integer res = q.list().size();
		return lst;
	}

	/*
	 * Тэтгэврийн насны хугацаа болсон ажилчдын жагсаалт
	 */
	public List<TetgevriinEmployeeBean> getListEmployeeByTetgevriin(HashSet<Organization> organization,
			GridPager pager) {

		List<TetgevriinEmployeeBean> beanList = new ArrayList<TetgevriinEmployeeBean>();

		String sql = "SELECT e FROM Employee e WHERE employeeStatus=:working ";

		if (organization != null)
			sql += " AND e.organization IN (:orgs)";

		Query q = session.createQuery("" + sql).setFirstResult(pager.getFirstRow()).setMaxResults(pager.getMaxResult());

		q.setLong("working", EmployeeStatus.WORKING.getVal());

		if (organization != null)
			q.setParameterList("orgs", organization);

		List<Employee> empList = new ArrayList<Employee>();

		if (q.list().size() > 0) {
			empList = q.list();

			for (Employee emp : empList) {

				Long ajillasanMill = 0l, ajillasanMillTsergiin = 0l;
				Date today = new Date();

				CalendarUtil calUtil = new CalendarUtil();

				/*
				 * Ажилчдын нийт ажилласан жилийг олохын тулд ажилтанаар select
				 * хийж байна.
				 */
				sql = "SELECT e FROM Experience e " + "WHERE e.employee.id=:employeeId "
						+ "AND (e.organizationtype=:SHSHGEG OR e.organizationtype=:ULS OR e.organizationtype = :HUVIIN) ";
				// "AND e.militaryOrSimple=:Military ";

				q = session.createQuery(sql);

				q.setLong("employeeId", emp.getId());
				q.setLong("SHSHGEG", OrganizationTypeExperience.SHUUH.getVal());
				q.setLong("ULS", OrganizationTypeExperience.ULSIIN.getVal());
				// q.setLong("Military",MilitaryOrSimple.workingMilitary.getVal());
				q.setLong("HUVIIN", OrganizationTypeExperience.HUVIIN.getVal());

				Long ajillasanJil = 0l, ajillasanJilTsergiin = 0l;
				if (q.list().size() > 0) {

					List<Experience> experiences = q.list();
					ajillasanMill = 0l;
					ajillasanMillTsergiin = 0l;

					for (Experience experience : experiences) {
						/* Нийт ажилласан жил */
						if (experience.getStartDate() != null) {
							if (experience.getEndDate() != null)
								ajillasanMill += experience.getEndDate().getTime()
										- experience.getStartDate().getTime();
							else
								ajillasanMill += today.getTime() - experience.getStartDate().getTime();
						}

						/* Цэргийн цолтой ажилласан жил */
						if (experience.getStartDate() != null
								&& experience.getMilitaryOrSimple() == MilitaryOrSimple.workingMilitary) {
							if (experience.getEndDate() != null)
								ajillasanMillTsergiin += experience.getEndDate().getTime()
										- experience.getStartDate().getTime();
							else
								ajillasanMillTsergiin += today.getTime() - experience.getStartDate().getTime();
						}
					}

					ajillasanJil = (ajillasanMill / (1000 * 60 * 60 * 24)) / 365;
					ajillasanJilTsergiin = (ajillasanMillTsergiin / (1000 * 60 * 60 * 24)) / 365;
				}

				TetgevriinEmployeeBean bean = new TetgevriinEmployeeBean();

				/*
				 * Хэрвээ эмэгтэй ажилтан байвал хэдэн хүүхэдтэйг тоолох
				 * selection
				 */
				String fsql = "SELECT count(*) FROM Relatives r " + " WHERE r.employee = :employeeId "
						+ " AND r.employee.gender = " + Gender.FEMALE.getVal()
						+ " AND (r.relation.name LIKE 'Хүү' OR r.relation.name LIKE 'Охин')";
				Query fq = session.createQuery(fsql);

				fq.setLong("employeeId", emp.getId());
				int childCount = Integer.parseInt(fq.list().get(0).toString());

				/* Ажилтаны насыг тооцох */
				String sqlAge = "SELECT SUM(datediff(CURDATE(),e.birthDate)) FROM Employee e"
						+ " WHERE e= :employeeId AND e.birthDate IS NOT NULL";
				Query ageQ = session.createQuery(sqlAge);
				ageQ.setLong("employeeId", emp.getId());
				Integer empAge = 0;

				if (ageQ.list().get(0) != null)
					empAge = Integer.parseInt(ageQ.list().get(0).toString()) / 365;
				else
					empAge = 0;

				/* Ажилтаны цолны төрлийг тодорхойлох */
				String sqlDegree = "SELECT e FROM EmployeeMilitary e" + " WHERE e.employee= :employeeId AND "
						+ "e.olgosonOgnoo = (SELECT MAX(olgosonOgnoo) FROM EmployeeMilitary WHERE employee = :employeeId)";
				Query degreeQ = session.createQuery(sqlDegree);
				degreeQ.setLong("employeeId", emp.getId());
				List<EmployeeMilitary> empDegreeTypeList = degreeQ.list();

				String empDegreeType = null;
				if (empDegreeTypeList != null) {
					for (EmployeeMilitary empDeg : empDegreeTypeList) {
						empDegreeType = Integer.toString(empDeg.getMilitaryType().getVal());
					}
				} else
					empDegreeType = null;

				CalendarUtil birthCal = new CalendarUtil();

				if (emp.getBirthDate() != null)
					birthCal.setDate(emp.getBirthDate());
				else
					birthCal.setDate(new Date());

				if (emp.getGender() != null && emp.getGender().equals(Gender.FEMALE))
					birthCal.addYear(55);
				else
					birthCal.addYear(60);

				/*
				 * System.out.print("FirstName-" + emp.getFirstName() + ", Age-"
				 * + empAge + ", Tsol-" + empDegreeType + ", Gender - " +
				 * emp.getGender() + "\n");
				 */
				if (calUtil.calculateDifferent(birthCal.getDate()) >= 0
						// 4 хүүхэдтэй 20-с дээш жил нийгмийн даатгал төлсөн
						// эмэгтэй ажилчин
						|| (emp.getGender() != null && emp.getGender().equals(Gender.FEMALE) && ajillasanJil >= 20
								&& childCount >= 4)
						// Дунд офицерийн бүрэлдэхүүнд хамаарагдах
						// цолтой,
						// ажилласан жил 10 - с дээш, нас 45 хүрсэн бол
						|| (emp.getGender() != null
								&& empDegreeType == Integer
										.toString(MilitaryRankType.DundOfitseriinBureldehuuun.getVal())
								&& ajillasanJilTsergiin >= 10 && empAge >= 45)
						/*
						 * Ахлах офицерийн бүрэлдэхүүнд хамаарагдах
						 * цолтой,ажилласан жил 10 - с дээш, 50 нас хүрсэн бол
						 * эрэгтэй, * 45 нас хүрсэн эмэгтэй
						 */
						|| (emp.getGender() != null
								&& ((emp.getGender() == Gender.MALE && empAge >= 50)
										|| (emp.getGender().equals(Gender.FEMALE) && empAge >= 45))
								&& empDegreeType == Integer
										.toString(MilitaryRankType.AhlahOfitseriinBureldehuun.getVal())
								&& ajillasanJilTsergiin >= 10)
						/*
						 * Дээд офицерийн бүрэлдэхүүнд хамаарагдах цолтой,
						 * ажилласан жил 10 - с дээш, нас 55 хүрсэн бол 45 нас
						 * хүрсэн эмэгтэй ажилтан
						 */
						|| (emp.getGender() != null
								&& ((emp.getGender() == Gender.MALE && empAge >= 55)
										|| emp.getGender().equals(Gender.FEMALE) && empAge >= 45)
								&& empDegreeType == Integer
										.toString(MilitaryRankType.DeedOfitseriinBureldehuun.getVal())
								&& ajillasanJilTsergiin >= 10 && empAge >= 50)
						// Ахлахчийн бүрэлдэхүүнд хамаарагдах цолтой,
						// ажилласан
						// жил 10 - с дээш, нас 45 хүрсэн бол
						|| (emp.getGender() != null
								&& empDegreeType == Integer.toString(MilitaryRankType.AhlagchiinBureldehuuun.getVal())
								&& ajillasanJilTsergiin >= 10 && empAge >= 45)
						// Ажилласан жил 25-с дээш бол
						|| (emp.getGender() != null && emp.getGender().equals(Gender.MALE) && ajillasanJil >= 25)) {

					bean.setFirstname(emp.getFirstName());
					bean.setLastname(emp.getLastname());
					bean.setGender(emp.getGender());
					bean.setRegisterNo(emp.getRegisterNo());

					calUtil = new CalendarUtil();
					bean.setAjillasanHugatsaa(calUtil.calculateMill(ajillasanMill));
					bean.setAge(calUtil.calculateDifferent(emp.getBirthDate()));
					bean.setHetersenHonog(calUtil.getOverDays(ajillasanMill, emp.getGender(), birthCal).toString());
					bean.setBaiguullaga(
							(emp.getOrganization() != null)
									? ((emp.getOrganization().getShortName() != null)
											? emp.getOrganization().getShortName() : emp.getOrganization().getName())
									: "");
					bean.setAppointment(
							(emp.getAppointment() != null) ? emp.getAppointment().getAppointmentName() : "");
					beanList.add(bean);
				}
			}
			Integer res = beanList.size();
			pager.setCount(res.doubleValue());
		}

		return beanList;
	}

	/*
	 * Шагнуулах хугацаа дөхсөнийг 5 жил тутамд сануулна
	 */
	public List<ShagnuulahEmployeeBean> getListEmployeeByShagnal(HashSet<Organization> organization, GridPager pager) {

		List<ShagnuulahEmployeeBean> list = new ArrayList<ShagnuulahEmployeeBean>();
		CalendarUtil calUtil = new CalendarUtil();

		String sql = "WHERE employeeStatus=:working ";

		if (organization != null)
			sql += " AND e.organization IN (:orgs)";

		Query q = session.createQuery("SELECT DISTINCT e FROM Employee e " + sql).setFirstResult(pager.getFirstRow())
				.setMaxResults(pager.getMaxResult());

		q.setLong("working", EmployeeStatus.WORKING.getVal());

		if (organization != null)
			q.setParameterList("orgs", organization);

		List<Employee> empList = new ArrayList<Employee>();

		if (q.list().size() > 0) {
			empList = q.list();

			for (Employee emp : empList) {

				boolean sahilgagui = false;
				sql = "SELECT s.shiitgelDuusahOgnoo FROM Sahilga s WHERE s.employee=:empId "
						+ "AND s.shiitgelAvagdsanOgnoo="
						+ "(SELECT MAX(sa.shiitgelAvagdsanOgnoo) FROM Sahilga sa WHERE sa.employee=s.employee)";

				q = session.createQuery(sql);
				q.setLong("empId", emp.getId());

				if (q.list().size() > 0) {

					if ((Date) q.list().get(0) == null)
						sahilgagui = true;

				} else
					sahilgagui = true;

				if (sahilgagui) {

					if (emp.getHonour() != null && emp.getHonour().size() > 0) {

						sql = "SELECT h.dateOfAwarded, (TO_DAYS(NOW()) - TO_DAYS(h.dateOfAwarded)-1825) as hetersenHonog "
								+ "FROM Honour h " + "WHERE h.employee=:empId "
								+ "AND (TO_DAYS(NOW()) - TO_DAYS(h.dateOfAwarded)) > 1825 "
								+ "AND h.dateOfAwarded=(SELECT MAX(ho.dateOfAwarded) FROM Honour ho WHERE ho.employee=h.employee)";

						q = session.createQuery(sql);
						q.setLong("empId", emp.getId());

					} else {
						sql = "SELECT e.startDate,(TO_DAYS(NOW()) - TO_DAYS(e.startDate)- 90) as hetersenHonog "
								+ "FROM Experience e " + "WHERE e.employee=:empId " + "AND e.status=:working "
								+ "AND e.organizationtype=:shshgeg " + "AND e.endDate IS NULL "
								+ "AND (TO_DAYS(NOW()) - TO_DAYS(e.startDate)) > 90 ";
						q = session.createQuery(sql);
						q.setLong("empId", emp.getId());
						q.setLong("working", EmployeeStatus.WORKING.getVal());
						q.setLong("shshgeg", OrganizationTypeExperience.SHUUH.getVal());
					}

					List<Object[]> objList = q.list();

					for (Object[] obj : objList) {

						ShagnuulahEmployeeBean bean = new ShagnuulahEmployeeBean();

						bean.setId(emp.getId());
						bean.setBaiguullaga(
								(emp.getOrganization() != null) ? ((emp.getOrganization().getShortName() != null)
										? emp.getOrganization().getShortName() : emp.getOrganization().getName()) : "");
						bean.setHeltes((emp.getDepartment() != null) ? emp.getDepartment().getName() : "");
						bean.setFirstname(emp.getFirstName());
						bean.setLastname(emp.getLastname());
						bean.setRegisterNo(emp.getRegisterNo());
						bean.setGender(emp.getGender());
						bean.setLastAwardedDate((Date) obj[0]);
						bean.setHetersenHonog(calUtil.convertDay((Long) obj[1]));

						list.add(bean);
					}
				}
			}
		}

		Integer res = list.size();
		pager.setCount(res.doubleValue());

		return list;
	}

	/*
	 * Цалингийн шатлал ахих хугацаа дөхсөнийг 3 жилийн давтамжаар 30 хоногийн
	 * өмнөөс сануулна.
	 */
	public List<AlertEmployeeSalaryBean> getListEmployeeAlertSalary(HashSet<Organization> organization,
			GridPager pager) {
		List<AlertEmployeeSalaryBean> lst = new ArrayList<AlertEmployeeSalaryBean>();

		String hql = "SELECT d FROM Employee e INNER JOIN e.displacement d " + "WHERE e.employeeStatus ="
				+ EmployeeStatus.WORKING.getVal() + "";

		if (organization != null)
			hql += " AND e.organization IN (:orgs)";

		Query q = session.createQuery("" + hql).setFirstResult(pager.getFirstRow()).setMaxResults(pager.getMaxResult());

		if (organization != null)
			q.setParameterList("orgs", organization);

		List<Displacement> empDisp = new ArrayList<Displacement>();
		List<TuriinZahirgaaSalaryNetwork> salNet = new ArrayList<TuriinZahirgaaSalaryNetwork>();

		String salarySql;
		String sTimePeriod;
		Date nowDate = new Date();
		if (q.list().size() > 0) {
			empDisp = q.list();

			for (Displacement disp : empDisp) {
				salNet = new ArrayList<TuriinZahirgaaSalaryNetwork>();
				sTimePeriod = null;

				salarySql = "SELECT t FROM TuriinZahirgaaSalaryNetwork t " + " WHERE t.level = :level"
						+ " AND t.phase = :phase" + " AND t.active = 1";
				Query salaryQ = session.createQuery(salarySql);
				salaryQ.setParameter("level", disp.getUtTzTtTuLevel());
				salaryQ.setParameter("phase", disp.getSalaryScale());
				salNet = salaryQ.list();

				for (TuriinZahirgaaSalaryNetwork net : salNet)
					if (net.getValidTime() != null)
						sTimePeriod = Integer.toString(net.getValidTime());

				Calendar cal1 = Calendar.getInstance();
				Calendar cal2 = Calendar.getInstance();
				Date addSalDay = null;
				if (disp.getSalaryDate() != null && sTimePeriod != null) {
					cal1.setTime(disp.getSalaryDate());
					cal1.add(Calendar.YEAR, Integer.parseInt(sTimePeriod));
					cal1.add(Calendar.MONTH, -1);
					addSalDay = cal1.getTime();

					/*
					 * System.out.print("*************\n Now-" + nowDate+
					 * " , addSalDay-" + addSalDay + " , Comparing " +
					 * addSalDay.compareTo(nowDate) + "\n");
					 */
				}

				if (disp.getSalaryDate() != null && sTimePeriod != null
						&& (addSalDay.compareTo(nowDate) == 0 || addSalDay.compareTo(nowDate) == -1)) {
					AlertEmployeeSalaryBean aesb = new AlertEmployeeSalaryBean();

					aesb.setId((Long) disp.getEmployee().getId());
					aesb.setFirstname((String) disp.getEmployee().getFirstName());
					aesb.setLastname((String) disp.getEmployee().getLastname());
					aesb.setEmployee((Employee) disp.getEmployee());
					aesb.setGender((Gender) disp.getEmployee().getGender());
					aesb.setOdoogiinShatlalOgnoo((Date) disp.getSalaryDate());
					aesb.setTsalinSuljee((String) disp.getSalaryScale().getSalaryScale());
					aesb.setOdoogiinShatlal((String) disp.getUtTzTtTuLevel().getUtTzTtTuSort().getName() + "-"
							+ disp.getUtTzTtTuLevel().getUtTzTtTuRank().getRank());
					lst.add(aesb);
				}
			}
		}
		Integer res = lst.size();
		pager.setCount(res.doubleValue());
		return lst;
	}

	/*
	 * Удаан жилийн хугацаа болсоныг 1 сарын өмнөөс мэдэгдэх
	 */
	public List<AlertUdaanJilEmployeeBean> getListEmployeeAlertUdaanJil(HashSet<Organization> organization,
			GridPager pager) {
		List<AlertUdaanJilEmployeeBean> beanList = new ArrayList<AlertUdaanJilEmployeeBean>();

		String sql = "SELECT e FROM Employee e WHERE employeeStatus=:working ";

		if (organization != null)
			sql += " AND e.organization IN (:orgs)";

		Query q = session.createQuery("" + sql).setFirstResult(pager.getFirstRow()).setMaxResults(pager.getMaxResult());

		q.setLong("working", EmployeeStatus.WORKING.getVal());

		if (organization != null)
			q.setParameterList("orgs", organization);

		List<Employee> empList = new ArrayList<Employee>();

		if (q.list().size() > 0) {
			empList = q.list();

			for (Employee emp : empList) {
				Date today = new Date();
				Long ajillasanJil = 0l, ajillasanMill;
				Long yearX = 0l, monthX = 0l, dayX = 0l;

				/*
				 * Ажилчдын нийт ажилласан жилийг олохын тулд ажилтанаар select
				 * хийж байна.
				 */
				sql = "SELECT e FROM Experience e " + "WHERE e.employee.id=:employeeId AND e.organizationtype=:SHSHGEG";
				q = session.createQuery(sql);

				q.setLong("employeeId", emp.getId());
				q.setLong("SHSHGEG", OrganizationTypeExperience.SHUUH.getVal());

				if (q.list().size() > 0) {

					List<Experience> experiences = q.list();
					ajillasanMill = 0l;
					ajillasanJil = 0l;

					for (Experience experience : experiences) {
						/* Нийт ажилласан жил */
						if (experience.getStartDate() != null) {
							if (experience.getEndDate() != null)
								ajillasanMill += experience.getEndDate().getTime()
										- experience.getStartDate().getTime();
							else
								ajillasanMill += today.getTime() - experience.getStartDate().getTime();
						}
					}
					ajillasanJil = (ajillasanMill / (1000 * 60 * 60 * 24));
					yearX = ajillasanJil / 365;
					monthX = (ajillasanJil - yearX * 365) / 30;
					dayX = ajillasanJil - yearX * 365 - monthX * 30;

					if ((yearX == 4 || yearX == 7 || yearX == 10 || yearX == 14 || yearX == 19 || yearX == 25)
							&& (monthX >= 9)) {

						AlertUdaanJilEmployeeBean aesb = new AlertUdaanJilEmployeeBean();

						aesb.setId((Long) emp.getId());
						aesb.setFirstname((String) emp.getFirstName());
						aesb.setLastname((String) emp.getLastname());
						aesb.setBaiguullaga(
								(emp.getOrganization() != null) ? ((emp.getOrganization().getShortName() != null)
										? emp.getOrganization().getShortName() : emp.getOrganization().getName()) : "");
						aesb.setGender((Gender) emp.getGender());
						aesb.setHeltes((emp.getDepartment() != null) ? emp.getDepartment().getName() : "");
						aesb.setAppointment(
								(emp.getAppointment() != null) ? emp.getAppointment().getAppointmentName() : "");
						aesb.setWorkedYear((String) Long.toString(yearX) + "ж " + Long.toString(monthX) + "с "
								+ Long.toString(dayX) + "х ");

						beanList.add(aesb);

					}
				}
			}
		}

		Integer res = beanList.size();
		pager.setCount(res.doubleValue());
		return beanList;
	}

	public Appointment getAppointmentByName(Long appointmentID) {

		Criteria crit = session.createCriteria(Appointment.class);

		if (appointmentID != null)
			crit.add(Restrictions.eq("appointmentName", appointmentID));

		return (crit.list().size() > 0) ? (Appointment) crit.list().get(0) : null;
	}

	public Experience getWorkingExperience(Employee emp) {

		Criteria crit = session.createCriteria(Experience.class);
		crit.add(Restrictions.eq("status", EmployeeStatus.WORKING));
		crit.add(Restrictions.eq("employee", emp));
		return (crit.list().size() > 0) ? (Experience) crit.list().get(0) : null;
	}

	/*
	 * Туршлагын мэдээллийг employee-р select хийнэ.
	 */
	public List<EmployeeCardExperienceBean> getEmployeeCardExperienceBean(Employee emp) {
		String hql = "SELECT e.appointment, e.commandStartDate, e.commandNumber,"
				+ " e.commandSubject, e.startDate, e.organizationname,"
				+ " e.commandEndDate, e.endDate FROM Experience e  WHERE e.employee = :emp" + " ORDER BY e.startDate";

		Query q = session.createQuery(hql);

		if (emp != null)
			q.setLong("emp", emp.getId());

		List<EmployeeCardExperienceBean> list = new ArrayList<EmployeeCardExperienceBean>();

		List<Object[]> listObj = q.list();

		for (Object[] obj : listObj) {
			String hqlOrg = "SELECT o FROM Organization o WHERE o.name = :org";
			Query orgQ = session.createQuery(hqlOrg);
			orgQ.setParameter("org", obj[5]);
			String orgShortName = null;
			List<Organization> orglist = orgQ.list();
			if (orglist.size() == 1) {
				for (Organization org : orglist) {
					orgShortName = org.getShortName();
				}
			} else
				orgShortName = null;

			EmployeeCardExperienceBean bean = new EmployeeCardExperienceBean();
			bean.setAppointment((String) obj[0]);
			bean.setCommandStartDate((Date) obj[1]);
			bean.setCommandNumber((String) obj[2]);
			bean.setCommandSubject((CommandSubject) obj[3]);
			bean.setIssuedDate((Date) obj[4]);
			bean.setOrganization((String) ((orgShortName != null) ? orgShortName : obj[5]));
			bean.setCommandEndDate((Date) obj[6]);
			bean.setQuitDate((Date) obj[7]);

			list.add(bean);
		}

		return list;
	}

	/*
	 * Төр засгийн шагналын мэдээллийг employee-р select хийнэ.
	 */
	public List<EmployeeCardStateAwardBean> getEmployeeCardStateAwardBean(Employee emp) {
		String hql = "SELECT h.otherPrize, h.dateOfAwarded, h.dictateId FROM Honour h  WHERE " + " (h.awardType ='"
				+ AwardType.GOVERNMENTPRIZE.getVal() + "' OR h.awardType ='" + AwardType.STATEPRIZE.getVal()
				+ "') AND h.employee = :emp ORDER BY h.dateOfAwarded";

		Query q = session.createQuery(hql);

		if (emp != null)
			q.setLong("emp", emp.getId());

		List<EmployeeCardStateAwardBean> list = new ArrayList<EmployeeCardStateAwardBean>();

		List<Object[]> listObj = q.list();

		for (Object[] obj : listObj) {
			EmployeeCardStateAwardBean bean = new EmployeeCardStateAwardBean();

			bean.setAwardName((String) obj[0]);
			bean.setDateOfAward((Date) obj[1]);
			bean.setCommandNumber((String) obj[2]);

			list.add(bean);
		}

		return list;
	}

	/*
	 * Бусад газрын шагналын мэдээллийг employee-р select хийнэ.
	 */
	public List<EmployeeCardOtherAwardBean> getEmployeeCardOtherAwardBean(Employee emp) {
		String hql = "SELECT h.otherPrize, h.dateOfAwarded, h.dictateId FROM Honour h  WHERE " + " (h.awardType ='"
				+ AwardType.COURTPRIZE.getVal() + "' OR h.awardType ='" + AwardType.JUSTICE_MINISTRYPRIZE.getVal() + "'"
				+ "OR h.awardType = " + AwardType.OTHER_ORGANIZATIONPRIZE.getVal() + ") "
				+ "AND h.employee = :emp ORDER BY h.dateOfAwarded";

		Query q = session.createQuery(hql);

		if (emp != null)
			q.setLong("emp", emp.getId());

		List<EmployeeCardOtherAwardBean> list = new ArrayList<EmployeeCardOtherAwardBean>();

		List<Object[]> listObj = q.list();

		for (Object[] obj : listObj) {
			EmployeeCardOtherAwardBean bean = new EmployeeCardOtherAwardBean();

			bean.setAwardName((String) obj[0]);
			bean.setDateOfAward((Date) obj[1]);
			bean.setCommandNumber((String) obj[2]);

			list.add(bean);
		}

		return list;
	}

	/*
	 * Бусад газрын шагналын мэдээллийг employee-р select хийнэ.
	 */
	public List<EmployeeCardDemeritBean> getEmployeeCardDemeritBean(Employee emp) {
		String hql = "SELECT s.cause, s.sahilgaTurul, s.avagdsanShiitgelDugaar, s.arilgasanTushaalOgnoo,"
				+ "s.arilgasanTushaalDugaar FROM Sahilga s  WHERE s.employee = :emp ORDER BY s.id";

		Query q = session.createQuery(hql);

		if (emp != null)
			q.setLong("emp", emp.getId());

		List<EmployeeCardDemeritBean> list = new ArrayList<EmployeeCardDemeritBean>();

		List<Object[]> listObj = q.list();

		for (Object[] obj : listObj) {
			EmployeeCardDemeritBean bean = new EmployeeCardDemeritBean();

			bean.setCause((String) obj[0]);
			bean.setDemeritType((SahilgaTurul) obj[1]);
			bean.setAvagdsanShiitgelDugaar((String) obj[2]);
			bean.setArilgasanTushaalOgnoo((Date) obj[3]);
			bean.setArilgasanTushaalDugaar((String) obj[4]);

			list.add(bean);
		}

		return list;
	}

	/*
	 * Бусад газрын шагналын мэдээллийг employee-р select хийнэ.
	 */
	public List<EmployeeCardEducationBean> getEmployeeCardEducationBean(Employee emp) {
		String hql = "SELECT e.ingoingDate, e.graduatedDate, e.diplomaNo, e.diplomaSubject, e.school"
				+ " FROM Educations e  WHERE e.employee = :emp ORDER BY e.ingoingDate";

		Query q = session.createQuery(hql);

		if (emp != null)
			q.setLong("emp", emp.getId());

		List<EmployeeCardEducationBean> list = new ArrayList<EmployeeCardEducationBean>();

		List<Object[]> listObj = q.list();

		for (Object[] obj : listObj) {
			EmployeeCardEducationBean bean = new EmployeeCardEducationBean();

			bean.setStartYear((String) obj[0]);
			bean.setEndYear((String) obj[1]);
			bean.setCertificateNo((String) obj[2]);
			bean.setDiplomaSubject((String) obj[3]);
			bean.setSchoolName((String) obj[4]);

			list.add(bean);
		}

		return list;
	}

	/*
	 * Цалингийн мэдээллийг employee-р select хийнэ.
	 */
	public List<EmployeeCardSalaryBean> getEmployeeCardSalaryBean(Employee emp) {
		String hql = "SELECT s.employee, s.level" + " FROM SalaryHistory s  "
				+ " WHERE s.employee = :employeeId ORDER BY s.createdDate";

		Query q = session.createQuery(hql);

		if (emp != null)
			q.setLong("employeeId", emp.getId());

		List<EmployeeCardSalaryBean> list = new ArrayList<EmployeeCardSalaryBean>();

		List<Object[]> listObj = q.list();

		for (Object[] obj : listObj) {
			EmployeeCardSalaryBean bean = new EmployeeCardSalaryBean();

			bean.setEmployee((Employee) obj[0]);
			bean.setLevel((UtTzTtTuLevel) obj[1]);
			list.add(bean);
		}
		return list;
	}

	/*
	 * Зэрэг дэвийн мэдээллийг employee-р select хийнэ.
	 */
	public List<EmployeeCardDegreeBean> getEmployeeCardDegreeBean(Employee emp) {
		String hql = "SELECT e.military, e.tushaalDugaar, e.olgosonOgnoo"
				+ " FROM EmployeeMilitary e WHERE e.employee = :emp ORDER BY e.olgosonOgnoo";

		Query q = session.createQuery(hql);

		if (emp != null)
			q.setLong("emp", emp.getId());

		List<EmployeeCardDegreeBean> list = new ArrayList<EmployeeCardDegreeBean>();

		List<Object[]> listObj = q.list();

		for (Object[] obj : listObj) {
			EmployeeCardDegreeBean bean = new EmployeeCardDegreeBean();

			bean.setDegree((MilitaryRank) obj[0]);
			bean.setCommandNumber((String) obj[1]);
			bean.setIssuedDate((Date) obj[2]);

			list.add(bean);
		}

		return list;
	}

	/*
	 * Сүүлийн 30 хоногт сахилгын шийтгэл авсан ажилчидын жагсаалт
	 */
	public List<EmployeeLastDemeritBean> getEmployeeLastDemeritBean(Date startDate, Date endDate,
			HashSet<Organization> organization) {
		/*
		 * String hql =
		 * "SELECT s.sahilgaShiitgel, s.sahilgaTurul, s.cause, s.shiitgelAvagdsanOgnoo, s.commandSubject, s.employee"
		 * + " FROM Sahilga s LEFT JOIN s.employeeMilitary m WHERE " +
		 * "(m.employee, m.olgosonOgnoo) IN (SELECT em.employee, MAX(em.olgosonOgnoo) FROM EmployeeMilitary em)"
		 * ;
		 */

		String hql = null;
		String where = "";
		hql = "SELECT s.sahilgaShiitgel, s.sahilgaTurul, s.cause, s.shiitgelAvagdsanOgnoo, s.commandSubject, s.employee"
				+ " FROM Sahilga s   ";
		if (startDate == null && endDate == null) {
			where += " TO_DAYS(NOW()) - TO_DAYS(s.shiitgelAvagdsanOgnoo ) <= 30 AND ";
		}

		if (startDate != null)
			where = where + " s.shiitgelAvagdsanOgnoo >= :startDate AND ";

		if (endDate != null)
			where = where + " s.shiitgelAvagdsanOgnoo <= :endDate AND ";

		if (organization != null)
			where = where + " s.employee.organization IN (" + StringUtils.join(organization, ",") + ")" + " AND ";

		if (!where.equals("")) {
			where = where.substring(0, (where.length() - 4));

			hql += " WHERE " + where + " ORDER BY s.shiitgelAvagdsanOgnoo ASC";
		}

		Query q = session.createQuery(hql);

		if (startDate != null)
			q.setParameter("startDate", startDate);

		if (endDate != null)
			q.setParameter("endDate", endDate);

		List<EmployeeLastDemeritBean> list = new ArrayList<EmployeeLastDemeritBean>();

		List<Object[]> listObj = q.list();

		for (Object[] obj : listObj) {
			EmployeeLastDemeritBean bean = new EmployeeLastDemeritBean();
			bean.setSahilgaShiitgel((SahilgaShiitgel) obj[0]);
			bean.setSahilgaTurul((SahilgaTurul) obj[1]);
			bean.setCause((String) obj[2]);
			bean.setShiitgelAvagdsanOgnoo((Date) obj[3]);
			bean.setCommandSubject((CommandSubject) obj[4]);
			bean.setEmployee((Employee) obj[5]);

			list.add(bean);
		}

		return list;
	}

	/* Гэр бүлийн гишүүдийн тоо */
	public int getEmployeeFamilyMemberCount(Employee emp) {
		String hql = "SELECT COUNT(*) FROM Relatives r WHERE r.employee = :emp" + " AND r.relativeType ="
				+ RelativeFamily.ISFAMILY.getVal();
		Query q = session.createQuery(hql);

		q.setParameter("emp", emp);

		int fCount = Integer.parseInt(q.list().get(0).toString());

		return fCount;
	}

	/* Гэр бүлийн гишүүд */
	public List<Relatives> getEmployeeFamilyMembers(Employee emp) {
		String hql = "SELECT r FROM Relatives r WHERE r.employee = :emp" + " AND r.relativeType ="
				+ RelativeFamily.ISFAMILY.getVal();
		Query q = session.createQuery(hql);

		q.setParameter("emp", emp);

		List<Relatives> list = q.list();

		return list;
	}

	public EmployeeMilitary getEmployeeMilitary(Long empID) {
		Criteria crit = session.createCriteria(EmployeeMilitary.class);

		crit.add(Restrictions.eq("employee.id", empID));
		crit.addOrder(Order.desc("olgosonOgnoo"));

		if (crit.list().isEmpty())
			return null;

		return (EmployeeMilitary) crit.list().get(0);
	}

	public List<Employee> getEmployeeIsUserList() {
		String sql = "SELECT e FROM Employee e INNER JOIN e.user u";

		Query q = session.createQuery(sql);

		return q.list();

	}

	/* Oct 2, 2013 Erdenetuya.B Begin */
	/***
	 * Хот, аймаг нэрээр нь шалгаж авах
	 */

	public City getCityByName(String cityName) {
		City city = null;
		Criteria criteria = session.createCriteria(City.class);
		if (cityName != null) {
			criteria.add(Restrictions.like("cityName", "%" + cityName + "%"));
			if (criteria.list() != null && criteria.list().size() > 0) {
				city = (City) criteria.list().get(0);
			}
		}

		return city;
	}

	/***
	 * Сум дүүрэг нэр, аймгаар шалгаж авах
	 */
	public DistrictSum getDistrictSumByName(City city, String sumName) {
		Criteria criteria = session.createCriteria(DistrictSum.class);

		if (city != null) {
			criteria.add(Restrictions.eq("city", city));

			if (sumName != null) {
				criteria.add(Restrictions.eq("districtSumName", sumName));

				if (criteria.list() != null && !criteria.list().isEmpty()) {
					return (DistrictSum) criteria.uniqueResult();
				}
			}
		}

		return null;
	}

	public Origin getOriginByName(String originName) {
		Origin origin = null;
		Criteria criteria = session.createCriteria(Origin.class);
		if (originName != null) {
			criteria.add(Restrictions.like("name", "%" + originName + "%"));
			if (criteria.list() != null && criteria.list().size() > 0) {
				origin = (Origin) criteria.list().get(0);
			}
		}

		return origin;
	}

	public Ascription getAscriptionByName(String ascriptionName) {
		Ascription ascription = null;
		Criteria criteria = session.createCriteria(Ascription.class);
		if (ascriptionName != null) {
			criteria.add(Restrictions.like("name", "%" + ascriptionName + "%"));
			if (criteria.list() != null && criteria.list().size() > 0) {
				ascription = (Ascription) criteria.list().get(0);
			}
		}

		return ascription;
	}

	public RelativeType getRelativeTypeByName(String relativeTypeName) {
		RelativeType relativeType = null;
		Criteria criteria = session.createCriteria(RelativeType.class);
		if (relativeTypeName != null) {
			criteria.add(Restrictions.like("name", "%" + relativeTypeName + "%"));
			if (criteria.list() != null && criteria.list().size() > 0) {
				relativeType = (RelativeType) criteria.list().get(0);
			}
		}

		return relativeType;
	}

	public Employee getEmployeeByExcelRowNumber(Integer rowNumber) {

		Criteria criteria = session.createCriteria(Employee.class);
		criteria.add(Restrictions.eq("empNumber", rowNumber));

		return (criteria.list().isEmpty()) ? null : (Employee) criteria.list().get(0);
	}

	public FamilyAppointmentType getFamilyAppointmentByName(String appName) {

		Criteria criteria = session.createCriteria(FamilyAppointmentType.class);

		if (appName != null) {
			criteria.add(Restrictions.like("name", "%" + appName + "%"));
			if (criteria.list() != null && criteria.list().size() > 0) {
				return (FamilyAppointmentType) criteria.list().get(0);
			}
		}

		return null;
	}

	/* Oct 2, 2013 Erdenetuya.B End */

	/* Oct 3, 2013 Bileg-Ochir.G Start */

	public List<Establishment> getListEstablishment(EstablishmentSearchBean bean) {
		Criteria criteria = session.createCriteria(Establishment.class);

		if (bean.getAppointment() != null)
			criteria.add(Restrictions.eq("appointment.id", bean.getAppointment().getId()));

		if (bean.getOrganization() != null)
			criteria.add(Restrictions.eq("organization.id", bean.getOrganization().getId()));

		if (bean.getName() != null)
			criteria.add(Restrictions.eq("name", bean.getName()));

		if (bean.getStatus() != null && bean.getStatus())
			criteria.add(Restrictions.eq("status", bean.getStatus()));

		if (bean.getMain() != null && bean.getMain())
			criteria.add(Restrictions.eq("main", bean.getMain()));

		if (bean.getUtTzTtTuSort() != null)
			criteria.add(Restrictions.eq("utTzTtTuSort", bean.getUtTzTtTuSort()));

		if (bean.getUtTzTtTuLevel() != null)
			criteria.add(Restrictions.eq("utTzTtTuLevel", bean.getUtTzTtTuLevel()));

		return criteria.list();
	}

	public Employee getEmployeeByOrg(Long orgID, Integer empNumber) {

		Criteria crit = session.createCriteria(Employee.class);

		crit.add(Restrictions.eq("organization.id", orgID));
		crit.add(Restrictions.eq("empNumber", empNumber));

		return (Employee) crit.uniqueResult();
	}

	public Employee getEmployeeByNum(Integer empNumber) {

		Criteria crit = session.createCriteria(Employee.class);

		crit.add(Restrictions.eq("empNumber", empNumber));

		return (Employee) crit.uniqueResult();
	}

	public MilitaryRank getMilitaryRankByName(String militaryRankName, MilitaryRankType type) {
		MilitaryRank militaryRank = null;
		Criteria criteria = session.createCriteria(MilitaryRank.class);
		if (militaryRankName != null && type != null) {
			criteria.add(Restrictions.eq("militaryRankType", type));
			criteria.add(Restrictions.eq("militaryName", militaryRankName));

			if (criteria.list() != null && criteria.list().size() > 0) {
				militaryRank = (MilitaryRank) criteria.uniqueResult();
			}
		}

		return militaryRank;
	}

	/* Oct 3, 2013 Bileg-Ochir.G End */

	/* Oct 7, 2013 Erdenetuya.B Begin */

	public CommandSubject getCommandSubjectByName(String commandSubjectName) {
		CommandSubject commandSubject = null;
		Criteria criteria = session.createCriteria(CommandSubject.class);
		if (commandSubjectName != null) {
			criteria.add(Restrictions.eq("subjectName", commandSubjectName));
			if (criteria.list() != null && criteria.list().size() > 0) {
				commandSubject = (CommandSubject) criteria.uniqueResult();
			}
		}

		return commandSubject;
	}

	public Country getCountryByName(String countryName) {
		Country country = null;
		Criteria criteria = session.createCriteria(Country.class);
		if (countryName != null) {
			criteria.add(Restrictions.ilike("countryName", "%" + countryName + "%"));
			if (criteria.list() != null && criteria.list().size() > 0) {
				country = (Country) criteria.list().get(0);
			}
		}

		return country;
	}

	/* Oct 7, 2013 Erdenetuya.B End */

	/* Oct 7, 2013 Bileg-Ochir.G Start */
	public DegreeType getDegreeTypeName(String degreeName) {
		DegreeType degree = null;
		Criteria criteria = session.createCriteria(DegreeType.class);
		if (degreeName != null) {
			criteria.add(Restrictions.eq("name", degreeName));
			if (criteria.list() != null && criteria.list().size() > 0) {
				degree = (DegreeType) criteria.uniqueResult();
			}
		}

		return degree;
	}

	public Occupation getOccupationName(String occupationName) {
		Occupation occupation = null;
		Criteria criteria = session.createCriteria(Occupation.class);
		if (occupationName != null) {
			criteria.add(Restrictions.eq("name", occupationName));
			if (criteria.list() != null && criteria.list().size() > 0) {
				occupation = (Occupation) criteria.uniqueResult();
			}
		}

		return occupation;
	}

	public School getSchoolByName(String schoolName) {
		School school = null;
		Criteria criteria = session.createCriteria(School.class);
		if (schoolName != null) {
			criteria.add(Restrictions.eq("name", schoolName));
			if (criteria.list() != null && criteria.list().size() > 0) {
				school = (School) criteria.uniqueResult();
			}
		}

		return school;
	}

	public List<Educations> getListEducation() {
		Criteria crit = session.createCriteria(Educations.class);
		return crit.list();
	}

	public List<School> getListSchool(String name) {
		Criteria crit = session.createCriteria(School.class);

		if (name != null)
			crit.add(Restrictions.eq("name", name));

		return crit.list();
	}

	/* Oct 7, 2013 Bileg-Ochir.G End */

	/* Oct 8, 2013 Jargalsuren.S Begin */
	public ForeignLanguage getListForeignLanguage(String languageName) {
		ForeignLanguage language = null;
		Criteria crit = session.createCriteria(ForeignLanguage.class);

		if (languageName != null) {
			crit.add(Restrictions.eq("languageName", languageName));
			if (crit.list() != null && crit.list().size() > 0) {
				language = (ForeignLanguage) crit.uniqueResult();
			}
		}

		return language;
	}

	public ComputerProgram getListComputerProgram(String program) {
		ComputerProgram computerProgram = null;
		Criteria crit = session.createCriteria(ComputerProgram.class);

		if (program != null) {
			crit.add(Restrictions.eq("programName", program));
			if (crit.list() != null && crit.list().size() > 0) {
				computerProgram = (ComputerProgram) crit.uniqueResult();
			}
		}

		return computerProgram;
	}

	public Facility getOfficeIt(String program) {
		Facility officeIt = null;
		Criteria crit = session.createCriteria(Facility.class);

		if (program != null) {
			crit.add(Restrictions.eq("facilityName", program));
			if (crit.list() != null && crit.list().size() > 0) {
				officeIt = (Facility) crit.uniqueResult();
			}
		}

		return officeIt;
	}

	public AcademicRank getAcademicRank(String academicRankName) {
		AcademicRank academicRank = null;
		Criteria crit = session.createCriteria(AcademicRank.class);

		if (academicRankName != null) {
			crit.add(Restrictions.eq("name", academicRankName));
			if (crit.list() != null && crit.list().size() > 0) {
				academicRank = (AcademicRank) crit.uniqueResult();
			}
		}

		return academicRank;
	}

	/* Oct 8, 2013 Jargalsuren.S Begin */

	public List<SkillType> getEmpSkillTypes() {

		Criteria crit = session.createCriteria(SkillType.class);
		crit.addOrder(Order.asc("id"));
		return (crit.list().isEmpty()) ? null : crit.list();
	}

	public Skills getSkillsByEmpSkillType(Employee employee, SkillType skillType) {

		Criteria crit = session.createCriteria(Skills.class);
		crit.add(Restrictions.eq("employee.id", employee.getId()));
		crit.add(Restrictions.eq("skillType.id", skillType.getId()));
		if (crit.list().isEmpty()) {

			return null;
		} else {

			return (Skills) crit.list().get(0);
		}
	}

	/* Oct 10, 2013 Erdenetuya.B Begin */

	public UtTzTtTuSort getUtTzTtTuSortByName(String name) {
		UtTzTtTuSort utTzTtTuSort = null;
		Criteria criteria = session.createCriteria(UtTzTtTuSort.class);
		if (name != null) {
			criteria.add(Restrictions.eq("name", name));
			if (criteria.list() != null && criteria.list().size() > 0) {
				utTzTtTuSort = (UtTzTtTuSort) criteria.uniqueResult();
			}
		}

		return utTzTtTuSort;

	}

	public OccupationRank getOccupationRankByRank(Integer rank) {
		OccupationRank occupationRank = null;
		Criteria criteria = session.createCriteria(OccupationRank.class);
		if (rank != null) {
			criteria.add(Restrictions.eq("rank", rank));
			if (criteria.list() != null && criteria.list().size() > 0) {
				occupationRank = (OccupationRank) criteria.uniqueResult();
			}
		}

		return occupationRank;
	}

	public SalaryScale getSalaryScaleByName(String name) {
		SalaryScale salaryScale = null;
		Criteria criteria = session.createCriteria(SalaryScale.class);
		if (name != null) {
			criteria.add(Restrictions.eq("salaryScale", name));
			if (criteria.list() != null && criteria.list().size() > 0) {
				salaryScale = (SalaryScale) criteria.uniqueResult();
			}
		}

		return salaryScale;
	}

	public UtTzTtTuLevel getUtTzLevel(UtTzTtTuSort utTzTtTuSort, OccupationRank occupationRank) {
		UtTzTtTuLevel level = null;
		Criteria criteria = session.createCriteria(UtTzTtTuLevel.class);

		if (utTzTtTuSort != null) {
			criteria.add(Restrictions.eq("utTzTtTuSort", utTzTtTuSort));
		}

		if (occupationRank != null) {
			criteria.add(Restrictions.eq("utTzTtTuRank", occupationRank));
		}
		if (criteria.list() != null && criteria.list().size() > 0) {
			level = (UtTzTtTuLevel) criteria.list().get(0);
		}

		return level;

	}

	/* Oct 10, 2013 Erdenetuya.B End */

	/* Oct 10, 2013 Bileg-Ochir.G Begin */
	public String getOccupation(Long empID) {
		Educations occ = null;

		Criteria crit = session.createCriteria(Educations.class);
		crit.add(Restrictions.eq("employee.id", empID));
		crit.addOrder(Order.desc("graduatedDate"));

		if (!crit.list().isEmpty())
			occ = (Educations) crit.list().get(0);

		if (occ != null && occ.getOccupation() != null)
			return occ.getOccupation().getName();

		if (occ != null && occ.getOccupationOther() != null)
			return occ.getOccupationOther();

		return "";

	}

	/* Oct 10, 2013 Bileg-Ochir.G Begin */

	/* Oct 11, 2013 Bileg-Ochir.G Begin */
	public VacationType getVacationTypeByName(String name) {
		VacationType vacation = null;
		Criteria criteria = session.createCriteria(VacationType.class);
		if (name != null) {
			criteria.add(Restrictions.like("vacationTypeName", "%" + name + "%"));
			if (criteria.list() != null && criteria.list().size() > 0) {
				vacation = (VacationType) criteria.uniqueResult();
			}
		}

		return vacation;

	}

	/* Oct 11, 2013 Bileg-Ochir.G End */
	public AllowanceType getAllowanceTypeByName(String name) {

		Criteria criteria = session.createCriteria(AllowanceType.class);

		if (name != null) {

			criteria.add(Restrictions.ilike("allowanceTypeName", "%" + name + "%"));
			return (criteria.list().isEmpty()) ? null : (AllowanceType) criteria.list().get(0);
		}

		return null;

	}

	public SahilgaTurul getSahilgaTurulByName(String name) {

		Criteria criteria = session.createCriteria(SahilgaTurul.class);

		if (name != null) {

			criteria.add(Restrictions.ilike("sahilgaTurulName", "%" + name + "%"));
			return (criteria.list().isEmpty()) ? null : (SahilgaTurul) criteria.list().get(0);
		}

		return null;

	}

	public SahilgaShiitgel getSahilgaShiitgelByName(String name) {

		Criteria criteria = session.createCriteria(SahilgaShiitgel.class);

		if (name != null) {

			criteria.add(Restrictions.ilike("shiitgelName", "%" + name + "%"));
			return (criteria.list().isEmpty()) ? null : (SahilgaShiitgel) criteria.list().get(0);
		}

		return null;

	}

	public AdditionalSalaryType getAdditionalSalaryTypeById(Long id) {
		Criteria criteria = session.createCriteria(AdditionalSalaryType.class);

		if (id != null) {
			criteria.add(Restrictions.eq("id", id));
			return (AdditionalSalaryType) criteria.uniqueResult();
		}

		return null;
	}

	public User getUserByUsername(String username) {
		Criteria crit = session.createCriteria(User.class);
		crit.add(Restrictions.eq("username", username));
		return (User) crit.uniqueResult();
	}

	/* Excel import: Шагналын нэрээр хайх */
	public Award getAwardByName(AwardType awardType, String name) {

		Criteria criteria = session.createCriteria(Award.class);

		if (awardType != null)
			criteria.add(Restrictions.eq("awardType", awardType));

		if (name != null) {

			criteria.add(Restrictions.ilike("name", name));
			return (criteria.list().isEmpty()) ? null : (Award) criteria.list().get(0);
		}

		return null;
	}

	public Award getAwardByNameName(String name) {

		Criteria criteria = session.createCriteria(Award.class);

		if (name != null) {

			criteria.add(Restrictions.ilike("name", name));
			return (criteria.list().isEmpty()) ? null : (Award) criteria.list().get(0);
		}

		return null;
	}

	public Notice getLastNotice() {
		Criteria crit = session.createCriteria(Notice.class);
		crit.addOrder(Order.desc("createdDate"));
		if (crit.list() != null && crit.list().size() > 0)
			return (Notice) crit.list().get(0);
		return null;
	}

	public List<GeneralReportBean> generateReport(Long orgID) {
		String sql = "SELECT e.organization as org, count(distinct e.id) as num,"

				+ " sum(case when e.gender = 0 then 1 else 0 end) as female,"
				+ " sum(case when e.gender = 1 then 1 else 0 end) as male,"

				+ " sum(case when datediff(CURDATE(),e.birth_date)/365.25 < 21 then 1 else 0 end) as age1,"
				+ " sum(case when datediff(CURDATE(),e.birth_date)/365.25 between 21 and 31 then 1 else 0 end) as age2,"
				+ " sum(case when datediff(CURDATE(),e.birth_date)/365.25 between 31 and 36 then 1 else 0 end) as age3,"
				+ " sum(case when datediff(CURDATE(),e.birth_date)/365.25 between 36 and 41 then 1 else 0 end) as age4,"
				+ " sum(case when datediff(CURDATE(),e.birth_date)/365.25 between 41 and 46 then 1 else 0 end) as age5,"
				+ " sum(case when datediff(CURDATE(),e.birth_date)/365.25 between 46 and 51 then 1 else 0 end) as age6,"
				+ " sum(case when datediff(CURDATE(),e.birth_date)/365.25 between 51 and 56 then 1 else 0 end) as age7,"
				+ " sum(case when datediff(CURDATE(),e.birth_date)/365.25 between 56 and 60 then 1 else 0 end) as age8,"
				+ " sum(case when datediff(CURDATE(),e.birth_date)/365.25 > 60 then 1 else 0 end) as age9,"

				+ " sum(case when edus.degree_type in (16,17) then 1 else 0 end) as edu1,"
				+ " sum(case when edus.degree_type = 15 then 1 else 0 end) as edu2,"
				+ " sum(case when edus.degree_type = 14 then 1 else 0 end) as edu3,"
				+ " sum(case when edus.degree_type = 13 then 1 else 0 end) as edu4,"
				+ " sum(case when edus.degree_type = 12 then 1 else 0 end) as edu5,"
				+ " sum(case when edus.degree_type = 11 then 1 else 0 end) as edu6,"
				+ " sum(case when edus.degree_type = 10 then 1 else 0 end) as edu7,"
				+ " sum(case when edus.degree_type = 9 then 1 else 0 end) as edu8,"

				+ " sum(case when milit.military = 11 then 1 else 0 end) as tsol1,"
				+ " sum(case when milit.military = 10 then 1 else 0 end) as tsol2,"
				+ " sum(case when milit.military = 9 then 1 else 0 end) as tsol3,"
				+ " sum(case when milit.military = 20 then 1 else 0 end) as tsol4,"
				+ " sum(case when milit.military = 18 then 1 else 0 end) as tsol5,"
				+ " sum(case when milit.military = 7 then 1 else 0 end) as tsol6,"
				+ " sum(case when milit.military = 17 then 1 else 0 end) as tsol7,"
				+ " sum(case when milit.military = 5 then 1 else 0 end) as tsol8,"
				+ " sum(case when milit.military = 4 then 1 else 0 end) as tsol9,"
				+ " sum(case when milit.military = 2 then 1 else 0 end) as tsol10,"
				+ " sum(case when milit.military in (11,10,9,20,18,7) then 1 else 0 end) as tsol11,"
				+ " sum(case when milit.military in (17,5,4,2) then 1 else 0 end) as tsol12,"

				+ " sum(case when disp.occupation_role = 4 then 1 else 0 end) as at1,"
				+ " sum(case when disp.occupation_role = 5 then 1 else 0 end) as at2,"
				+ " sum(case when disp.occupation_role = 6 then 1 else 0 end) as at3,"

				+ " sum(case when disp.ut_tz_tt_tu_level in (35, 28, 29, 30, 31, 32, 33, 34, 36, 27, 26, 25, 19, 20, 21, 22, 23, 24) then 1 else 0 end) as sal1,"
				+ " sum(case when disp.ut_tz_tt_tu_level in (44, 43, 45, 42, 41, 40, 39, 16, 17, 15, 37, 38) then 1 else 0 end) as sal2 "

				+ " FROM employee e " + "LEFT JOIN educations edus ON e.id = edus.employee "
				+ "AND edus.graduated_date in (select max(ed.graduated_date) from educations ed where ed.employee = e.id) "
				+ "LEFT JOIN employee_military milit ON e.id = milit.employee "
				+ "AND milit.olgoson_date in (select max(mi.olgoson_date) from employee_military mi where mi.employee = e.id) "
				+ "LEFT JOIN displacement disp ON e.id = disp.employee AND disp.is_now_displacement = 1 "
				+ "AND disp.issued_date in (select max(di.issued_date) from displacement di where di.employee = e.id) "
				+ "LEFT JOIN organization org ON e.organization = org.id " + "  ";

		if (orgID != null) {
			sql += " WHERE org.id = " + orgID;
		}

		sql += " GROUP BY e.organization ORDER BY e.organization";
		System.err.println("sqlReport:" + sql);

		Query query = session.createSQLQuery(sql).addScalar("female", StandardBasicTypes.INTEGER)
				.addScalar("male", StandardBasicTypes.INTEGER).addScalar("org", StandardBasicTypes.LONG)
				.addScalar("num", StandardBasicTypes.INTEGER).addScalar("age1", StandardBasicTypes.INTEGER)
				.addScalar("age2", StandardBasicTypes.INTEGER).addScalar("age3", StandardBasicTypes.INTEGER)
				.addScalar("age4", StandardBasicTypes.INTEGER).addScalar("age5", StandardBasicTypes.INTEGER)
				.addScalar("age6", StandardBasicTypes.INTEGER).addScalar("age7", StandardBasicTypes.INTEGER)
				.addScalar("age8", StandardBasicTypes.INTEGER).addScalar("age9", StandardBasicTypes.INTEGER)
				.addScalar("edu1", StandardBasicTypes.INTEGER).addScalar("edu2", StandardBasicTypes.INTEGER)
				.addScalar("edu3", StandardBasicTypes.INTEGER).addScalar("edu4", StandardBasicTypes.INTEGER)
				.addScalar("edu5", StandardBasicTypes.INTEGER).addScalar("edu6", StandardBasicTypes.INTEGER)
				.addScalar("edu7", StandardBasicTypes.INTEGER).addScalar("edu8", StandardBasicTypes.INTEGER)
				.addScalar("tsol1", StandardBasicTypes.INTEGER).addScalar("tsol2", StandardBasicTypes.INTEGER)
				.addScalar("tsol3", StandardBasicTypes.INTEGER).addScalar("tsol4", StandardBasicTypes.INTEGER)
				.addScalar("tsol5", StandardBasicTypes.INTEGER).addScalar("tsol6", StandardBasicTypes.INTEGER)
				.addScalar("tsol7", StandardBasicTypes.INTEGER).addScalar("tsol8", StandardBasicTypes.INTEGER)
				.addScalar("tsol9", StandardBasicTypes.INTEGER).addScalar("tsol10", StandardBasicTypes.INTEGER)
				.addScalar("tsol11", StandardBasicTypes.INTEGER).addScalar("tsol12", StandardBasicTypes.INTEGER)
				.addScalar("at1", StandardBasicTypes.INTEGER).addScalar("at2", StandardBasicTypes.INTEGER)
				.addScalar("at3", StandardBasicTypes.INTEGER).addScalar("sal1", StandardBasicTypes.INTEGER)
				.addScalar("sal2", StandardBasicTypes.INTEGER);

		return query.setResultTransformer(Transformers.aliasToBean(GeneralReportBean.class)).list();
	}

	public List<EstReportBean> generateEstablishmentReport(Long orgID) {
		System.err.println("orgID " + orgID);
		String sql = "SELECT e.organization as org, "
				+ "sum(case when d.salary_network = 2 then 1 else 0 end) as colTotal1, "
				+ "sum(case when d.salary_network = 3 then 1 else 0 end) as colTotal2, "
				+ "sum(case when d.salary_network is null then 1 else 0 end) as colTotal3, "
				+ "sum(case when d.salary_network = 2 and (mills.militaryType = 1 or mills.militaryType = 2 or mills.militaryType = 3) then 1 else 0 end) as col1Off, "
				+ "sum(case when d.salary_network = 2 and mills.militaryType = 0 then 1 else 0 end) as col1Ahlagch, "
				+ "sum(case when d.salary_network = 2 and ex.militaryOrSimple = 0 then 1 else 0 end) as col1Engiin, "
				+ "sum(case when d.salary_network = 3 and (mills.militaryType = 1 or mills.militaryType = 2 or mills.militaryType = 3) then 1 else 0 end) as col2Off, "
				+ "sum(case when d.salary_network = 3 and mills.militaryType = 0 then 1 else 0 end) as col2Ahlagch, "
				+ "sum(case when d.salary_network = 3 and ex.militaryOrSimple = 0 then 1 else 0 end) as col2Engiin, "
				+ "sum(case when d.salary_network is null and (mills.militaryType = 1 or mills.militaryType = 2 or mills.militaryType = 3) then 1 else 0 end) as col3Off, "
				+ "sum(case when d.salary_network is null and mills.militaryType = 0 then 1 else 0 end) as col3Ahlagch, "
				+ "sum(case when d.salary_network is null and mills.militaryType is null then 1 else 0 end) as col3Engiin "
				+ " from (SELECT t.id as id, t.olgoson_date as olgoson_date, t.militaryType as militaryType, t.employee as employee FROM ( SELECT employee, MAX(olgoson_date) as MaxTime FROM employee_military GROUP BY employee ) r INNER JOIN employee_military t ON t.employee = r.employee AND t.olgoson_date = r.MaxTime ) mills "
				+ " INNER JOIN employee e on e.id = mills.employee "
				+ "INNER JOIN displacement d ON e.id=d.employee "
				+ "LEFT JOIN experience ex on ex.displacement=d.id "
				+ "LEFT JOIN organization orga ON e.organization = orga.id ";

		if (orgID != null) {
			sql += " WHERE orga.id = " + orgID;
		}

		sql += " GROUP BY e.organization ORDER BY e.organization";

		Query query = session.createSQLQuery(sql).addScalar("colTotal1", StandardBasicTypes.INTEGER)
				.addScalar("colTotal2", StandardBasicTypes.INTEGER).addScalar("colTotal3", StandardBasicTypes.INTEGER)
				.addScalar("col1Off", StandardBasicTypes.INTEGER).addScalar("col1Ahlagch", StandardBasicTypes.INTEGER)
				.addScalar("col1Engiin", StandardBasicTypes.INTEGER).addScalar("col2Off", StandardBasicTypes.INTEGER)
				.addScalar("col2Ahlagch", StandardBasicTypes.INTEGER)
				.addScalar("col2Engiin", StandardBasicTypes.INTEGER).addScalar("col3Off", StandardBasicTypes.INTEGER)
				.addScalar("col3Ahlagch", StandardBasicTypes.INTEGER)
				.addScalar("col3Engiin", StandardBasicTypes.INTEGER).addScalar("org", StandardBasicTypes.LONG);

		return query.setResultTransformer(Transformers.aliasToBean(EstReportBean.class)).list();

	}

	public List<UserLoginHistory> getUserLoginHistory(UserLoginHistorySearchBean bean) {
		Criteria crit = session.createCriteria(UserLoginHistory.class);

		if (bean != null && bean.getDateFrom() != null)
			crit.add(Restrictions.ge("loginDate", bean.getDateFrom()));

		if (bean != null && bean.getDateTo() != null)
			crit.add(Restrictions.le("loginDate", bean.getDateTo()));

		if (bean != null && bean.getUser() != null)
			crit.add(Restrictions.eq("user", bean.getUser()));

		if (bean != null && bean.getOrganization() != null) {
			crit.createCriteria("user", "user", Criteria.LEFT_JOIN);
			crit.createCriteria("user.employee", "emp", Criteria.LEFT_JOIN);
			crit.add(Restrictions.eq("emp.organization", bean.getOrganization()));
		}

		crit.addOrder(Order.desc("loginDate"));
		return crit.list();
	}

	public List<EmployeeSalaryHistory> getEmployeeSalaryHistory(Long empID) {
		Criteria crit = session.createCriteria(EmployeeSalaryHistory.class);
		crit.add(Restrictions.eq("employee.id", empID));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		crit.addOrder(Order.desc("month"));
		List<EmployeeSalaryHistory> list = crit.list();

		return list;
	}

	public Experience getExperienceShuuh(Employee emp) {
		Criteria crit = session.createCriteria(Experience.class);

		crit.add(Restrictions.eq("employee", emp));
		crit.add(Restrictions.eq("organizationtype", OrganizationTypeExperience.SHUUH));
		crit.addOrder(Order.desc("startDate"));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<Experience> list = crit.list();
		if (list.size() > 0)
			return list.get(0);
		return null;
	}

	public Experience getExperienceOcc(Employee emp, Displacement displacement) {
		Criteria crit = session.createCriteria(Experience.class);
		if (emp != null)
			crit.add(Restrictions.eq("employee", emp));
		if (displacement != null)
			crit.add(Restrictions.eq("displacement", displacement));
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<Experience> list = crit.list();

		if (list.size() > 0)
			return list.get(0);
		return null;
	}

	public <T> List<T> getAnyList(Class<T> clss, String eqProperty, Long propertyID) {
		try {
			Criteria criteria = session.createCriteria(clss);

			if (eqProperty != null && propertyID != null)
				criteria.add(Restrictions.eq(eqProperty, propertyID));

			return criteria.list();

		} catch (HibernateException he) {
			return null;
		}
	}
}
