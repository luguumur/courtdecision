package mn.mcs.electronics.court.pages.configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.model.EmployeeSelectionModel;
import mn.mcs.electronics.court.model.OrganizationSelectionModel;
import mn.mcs.electronics.court.util.PaletteValueEncoderOrganization;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

public class InfoConfig {
	
	@Inject
	private CoreDAO dao;
	
	@Inject
	private Session session;
	
	@Property
	@Persist
	private Employee rowEmp;
	
	@Property
	@Persist
	private List<Organization> selectedValues; 
	
	@Property
	@Persist
	private Boolean onload;
	
	/*====================== FUNCTIONS ================================*/
	
	void beginRender(){
		
		
		
		if(selectedValues==null)
			selectedValues = new ArrayList<Organization>();
		
		if(rowEmp!=null){
			
			rowEmp = (Employee) dao.getObject(Employee.class, rowEmp.getId());
			
			selectedValues = new ArrayList<Organization>(rowEmp.getMapEmpOrg());
		}
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		
		rowEmp.setMapEmpOrg(new HashSet<Organization>(selectedValues));
		
		dao.saveOrUpdateObject(rowEmp);
	}
	
	/*====================== SELECTION MODEL ================================*/
	public SelectModel getUserSelectionModel(){
		EmployeeSelectionModel sm = new EmployeeSelectionModel(dao,true);
		
		return sm;
	}
	
	public SelectModel getOrganizationSelectionModel(){
		OrganizationSelectionModel sm = new OrganizationSelectionModel(dao);
		
		return sm;
	}
	
	/*====================== GETTER SETTER ================================*/
	public PaletteValueEncoderOrganization getValueEncoder() {
		return new PaletteValueEncoderOrganization(session);
	}
}
