package mn.mcs.electronics.court.pages.organization;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.organization.Department;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.util.beans.DepartmentSearchBean;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageDepartment {
	
	@Inject
	private CoreDAO dao;

	@Inject
	@Property
	@Path("context:/images/b_edit.png")
	private Asset editIcon;
	
	@Inject
	@Path("context:/css/index.css")
	private Asset styles;
	
	@Inject
	@Property
	@Path("context:/images/b_drop.png")
	private Asset deleteIcon;
	
	@Persist
	private boolean selectedBlockId;
	
	@Persist
	private boolean subDepart;
	
	@Inject
	private Block search;
	
	@Inject
	private Block subUnit;
	
	private static final String GRID_ROW_CSS = "myGrid";
	
	@Property
	@Persist
	private Department department;
	
	@Property
	@Persist
	private List<Department> listOrganizationDepartment;
	
	@Property
	@Persist
	private Department valueOrganizationDepartment;
	
	@InjectPage
	private PageOrganization pageOrganization;
	
	@InjectPage
	private PageDepartment pageDepartment;
	
	@Persist
	private Organization organization;
	
	@Property
	@Persist
	private DepartmentSearchBean bean;
	
	void beginRender(){
		
		if(bean == null)
			bean = new DepartmentSearchBean();
		
		if(department == null)
			department = new Department();
		
		if(organization == null)
			organization = new Organization();
		
		Subject currentUser = SecurityUtils.getSubject();
		
		if(currentUser.isPermitted("go_subDepartment"))
			subDepart = false;
		else
			subDepart = true;
		
		organization = pageOrganization.getOrganizationInfo();
		
		listOrganizationDepartment = dao.getListDepartment(organization);
	}
	
	public Block getActiveBlock(){
		if (selectedBlockId) {
			return search;
		} else{
			return subUnit;
		}
	}
	
	@CommitAfter
	void onSelectedFromSave(){	
		department.setOrganization(organization);
		dao.saveOrUpdateObject(department);
		setSelectedBlockId(false);
	}

	@CommitAfter
	void onActionFromDelete(Department department){
		dao.deleteObject(department);
	}
	
	void onActionFromEdit(Department department){	
		this.department = department;
		setSelectedBlockId(false);
	}
	
	void onActionFromCancel(){
		setSelectedBlockId(false);
	}
	
	void onSelectedFromAdd(){
		setSelectedBlockId(true);
		department = new Department();
	}
	
	Object onActionFromSubDepartment(){
		return pageDepartment;
	}
	
	/* Getter setter */
	
	public String getGridRowCSS() {
		return GRID_ROW_CSS;
	}
	
	public boolean isSelectedBlockId() {
		return selectedBlockId;
	}

	public void setSelectedBlockId(boolean selectedBlockId) {
		this.selectedBlockId = selectedBlockId;
	}
	
	public Asset getStyles() {
		return styles;
	}
	
	public String getListSize(){
		if(listOrganizationDepartment.isEmpty())
			return "0";
		
		return listOrganizationDepartment.size()+"";
	}

}
