package mn.mcs.electronics.court.pages.organization;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.organization.Department;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.entities.organization.SubDepartment;
import mn.mcs.electronics.court.util.beans.DepartmentSearchBean;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageSubDepartment {

	@Inject
	private CoreDAO dao;

	@Inject
	@Path("context:/css/index.css")
	private Asset styles;

	@Property
	@Persist
	private SubDepartment subDepartment;

	@Property
	@Persist
	private List<SubDepartment> listSubDepartment;

	@Property
	@Persist
	private SubDepartment valueSubDepartment;

	@InjectPage
	private PageOrganization pageOrganization;

	@InjectPage
	private PageOrganizationInfo pageOrganizationInfo;

	@InjectPage
	private PageDepartment pageDepartment;

	@Persist
	private Organization organization;

	@Persist
	private Department department;

	@Property
	@Persist
	private DepartmentSearchBean bean;

	void beginRender() {

		if (bean == null)
			bean = new DepartmentSearchBean();

		if (subDepartment == null)
			subDepartment = new SubDepartment();

		if (organization == null)
			organization = new Organization();

		organization = pageOrganization.getOrganizationInfo();

		listSubDepartment = dao.getListSubDepartment(organization, department);
	}

	@CommitAfter
	void onSelectedFromSaveSubDepartment() {
		subDepartment.setOrganization(organization);
		subDepartment.setDepartment(department);
		dao.saveOrUpdateObject(subDepartment);
		subDepartment = new SubDepartment();
	}

	void onActionFromCancelSubDepartment() {

	}

	@CommitAfter
	void onActionFromDeleteDepartment(SubDepartment subDepartment) {
		dao.deleteObject(subDepartment);
	}

	void onActionFromEdit(SubDepartment subDepartment) {
		this.subDepartment = subDepartment;
	}

	/* Getter setter */

	public Asset getStyles() {
		return styles;
	}

	public String getListSize() {
		if (listSubDepartment.isEmpty())
			return "0";

		return listSubDepartment.size() + "";
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}
