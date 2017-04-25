package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.components.excelImport.CompExcelAcademicRank;
import mn.mcs.electronics.court.components.excelImport.CompExcelAllowance;
import mn.mcs.electronics.court.components.excelImport.CompExcelEducation;
import mn.mcs.electronics.court.components.excelImport.CompExcelExperience;
import mn.mcs.electronics.court.components.excelImport.CompExcelFamily;
import mn.mcs.electronics.court.components.excelImport.CompExcelGrade;
import mn.mcs.electronics.court.components.excelImport.CompExcelHonour;
import mn.mcs.electronics.court.components.excelImport.CompExcelLanguage;
import mn.mcs.electronics.court.components.excelImport.CompExcelOfficeEquipment;
import mn.mcs.electronics.court.components.excelImport.CompExcelPersonal;
import mn.mcs.electronics.court.components.excelImport.CompExcelPreResult;
import mn.mcs.electronics.court.components.excelImport.CompExcelProgram;
import mn.mcs.electronics.court.components.excelImport.CompExcelSahilga;
import mn.mcs.electronics.court.components.excelImport.CompExcelSalary;
import mn.mcs.electronics.court.components.excelImport.CompExcelSkill;
import mn.mcs.electronics.court.components.excelImport.CompExcelTraining;
import mn.mcs.electronics.court.components.excelImport.CompExcelVacation;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.ExcelTypes;
import mn.mcs.electronics.court.model.OrganizationSelectionModel;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.EnumSelectModel;

public class PageExcelConfig {

	@Inject
	private CoreDAO dao;

	@Inject
	private Messages messages;

	@SessionState
	private LoginState loginState;

	@Inject
	private Block personalBlock, familyBlock, preResultBlock, gradeBlock,
			experienceBlock, trainigBlock, educationBlock, languageBlock,
			skillBlock, salaryBlock, honourBlock,allowanceBlock,sahilgaBlock, 
			programBlock, officeItBlock, erdmiinTsolBlock, vacationBlock;

	@InjectComponent
	private CompExcelPersonal compExcelPersonal;

	@InjectComponent
	private CompExcelPreResult compExcelPreResult;

	@InjectComponent
	private CompExcelFamily compExcelFamily;

	@InjectComponent
	private CompExcelGrade compExcelGrade;

	@InjectComponent
	private CompExcelExperience compExcelExperience;

	@InjectComponent
	private CompExcelTraining compExcelTraining;

	@InjectComponent
	private CompExcelEducation compExcelEducation;

	@InjectComponent
	private CompExcelLanguage compExcelLanguage;

	@InjectComponent
	private CompExcelSalary compExcelSalary;

	@InjectComponent
	private CompExcelSkill compExcelSkill;

	@InjectComponent
	private CompExcelHonour compExcelHonour;
	
	@InjectComponent
	private CompExcelAllowance compExcelAllowance;
	
	@InjectComponent
	private CompExcelSahilga compExcelSahilga;
	
	@InjectComponent
	private CompExcelProgram compExcelProgram;
	
	@InjectComponent
	private CompExcelOfficeEquipment compExcelOfficeEquipment;
	
	@InjectComponent
	private CompExcelAcademicRank compExcelAcademicRank;
	
	@InjectComponent
	private CompExcelVacation compExcelVacation;
	
	/*
	 * PERSISTS
	 */

	@Persist
	@Property
	private ExcelTypes excelType;

	@Persist
	@Property
	private Organization organization;

	@Persist
	@Property
	private List<Employee> listEmp;

	@CommitAfter
	void beginRender() {
		/* listEmp = dao.getListEmployee(); */

		/*
		 * for (Employee emp : listEmp) { if (emp != null && emp.getRegisterNo()
		 * != null) {
		 * 
		 * if (emp.getRegisterNo().length() > 23) {
		 * 
		 * emp.setOldReg(emp.getRegisterNo());
		 * 
		 * String regNum = null;
		 * 
		 * try { regNum = AES.decrypt(emp.getRegisterNo()); } catch (Exception
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 * 
		 * emp.setRegisterNo(regNum); dao.saveOrUpdateObject(emp); }
		 * 
		 * } }
		 */

		/*
		 * Integer counter = 0; for (Employee emp : listEmp) { if (emp != null
		 * && emp.getRegisterNo() != null && emp.getRegisterNo().length() > 10)
		 * {
		 * 
		 * if(emp.getRegisterNo().contains(":") ||
		 * emp.getRegisterNo().contains(" ") ||
		 * emp.getRegisterNo().contains("   ")) {
		 * emp.setRegisterNo(emp.getRegisterNo().replace(":", ""));
		 * emp.setRegisterNo(emp.getRegisterNo().replace(" ", ""));
		 * emp.setRegisterNo(emp.getRegisterNo().replace("   ", "")); }
		 * 
		 * dao.saveOrUpdateObject(emp);
		 * 
		 * System.err.println(counter++ + ". Error: " + emp.getRegisterNo()); }
		 * }
		 */
	}

	/*
	 * EVENTS
	 */

	/*
	 * METHODS
	 */

	public Block getActiveBlock() {
		if (excelType != null) {

			compExcelPersonal.setOrganization(organization);
			compExcelPreResult.setOrganization(organization);
			compExcelFamily.setOrganization(organization);
			compExcelGrade.setOrganization(organization);
			compExcelExperience.setOrganization(organization);
			compExcelTraining.setOrganization(organization);
			compExcelEducation.setOrganization(organization);
			compExcelLanguage.setOrganization(organization);
			compExcelSalary.setOrganization(organization);
			compExcelSkill.setOrganization(organization);
			compExcelHonour.setOrganization(organization);
			compExcelAllowance.setOrganization(organization);
			compExcelSahilga.setOrganization(organization);
			compExcelProgram.setOrganization(organization);
			compExcelOfficeEquipment.setOrganization(organization);
			compExcelAcademicRank.setOrganization(organization);
			compExcelVacation.setOrganization(organization);

			switch (excelType) {
			case HUVIINMEDEELEL:
				return personalBlock;
			case GERBUL:
				return familyBlock;
			case URDUN:
				return preResultBlock;
			case TSOL:
				return gradeBlock;
			case SURGALT:
				return trainigBlock;
			case TURSHLAGA:
				return experienceBlock;
			case BOLOVSROL:
				return educationBlock;
			case GADAADHEL:
				return languageBlock;
			case URCHADVAR:
				return skillBlock;
			case TSALIN:
				return salaryBlock;
			case SHAGNAL:
				return honourBlock;
			case TETGEMJ:
				return allowanceBlock;
			case SAHILGA:
				return sahilgaBlock;
			case PROGRAMHANGAMJ:
				return programBlock;
			case OFFICEIT:
				return officeItBlock;
			case ERDMIINTSOL:
				return erdmiinTsolBlock;
			case CHULUU:
				return vacationBlock;
				
			default:
				return null;
			}
		}

		return null;
	}

	void onValueChangedFromOrg() {
		compExcelPersonal.setOrganization(organization);
		compExcelPreResult.setOrganization(organization);
		compExcelFamily.setOrganization(organization);
		compExcelGrade.setOrganization(organization);
		compExcelExperience.setOrganization(organization);
		compExcelTraining.setOrganization(organization);
		compExcelEducation.setOrganization(organization);
		compExcelLanguage.setOrganization(organization);
		compExcelSalary.setOrganization(organization);
		compExcelSkill.setOrganization(organization);
		compExcelHonour.setOrganization(organization);
		compExcelAllowance.setOrganization(organization);
		compExcelSahilga.setOrganization(organization);
		compExcelVacation.setOrganization(organization);
	}

	/*
	 * SELECT MODELS
	 */

	public SelectModel getOrganizationSelectionModel() {
		return new OrganizationSelectionModel(dao);
	}

	public SelectModel getExcelTypeSelectionModel() {
		return new EnumSelectModel(ExcelTypes.class, messages);
	}
}
