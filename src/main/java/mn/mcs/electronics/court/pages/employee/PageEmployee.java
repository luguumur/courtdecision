package mn.mcs.electronics.court.pages.employee;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.components.LayoutEmployee;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.RetireAge;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.model.OrganizationSelectionModel;
import mn.mcs.electronics.court.pages.excel.PageImportExcel;
import mn.mcs.electronics.court.pages.organization.PageOrganization;
import mn.mcs.electronics.court.util.Constants;
import mn.mcs.electronics.court.util.ExcelAPI;
import mn.mcs.electronics.court.util.ReportUtil;
import mn.mcs.electronics.court.util.beans.EmployeeSearchBean;
import mn.mcs.electronics.court.util.dto.EmployeeDTO;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;

public class PageEmployee {

	@Inject
	private Messages messages;

	@Inject
	private Response response;

	@SessionState
	private LoginState loginState;

	@Inject
	@Path("context:/images/b_edit.png")
	private Asset editIcon;

	@Inject
	@Path("context:/images/b_drop.png")
	private Asset deleteIcon;

	@Inject
	@Path("context:/images/excel.jpg")
	private Asset imgExcel;

	@Inject
	private CoreDAO dao;

	@InjectPage
	private PageEmployeeDetail pageEmpDetail;

	@InjectPage
	private PageOrganization pageOrganization;

	@InjectPage
	private PageImportExcel pageImportExcel;

	@Property
	private List<EmployeeDTO> listEmployee;

	@Property
	@Persist
	private EmployeeDTO valueEmployee;

	// @Persist
	// private Employee employee;

	@Persist
	private Organization organization;

	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);

	private static final String GRID_ROW_CSS = "myGrid";

	private static String activeTab;

	private static String activeSubTab;

	@Persist
	private RetireAge ra;

	/* Search */
	@Property
	@Persist
	private EmployeeSearchBean bean;

	void beginRender() {

		if (bean == null)
			bean = new EmployeeSearchBean();

		if (dao.getListRetireAge() != null) {
			ra = dao.getListRetireAge().get(0);
		}

		this.organization = pageOrganization.getOrganizationInfo();

		bean.setOrganization(organization);

		HashSet<Organization> orgs = new HashSet<Organization>(loginState
				.getEmployee().getMapEmpOrg());

		if (orgs.isEmpty())
			orgs.add(loginState.getOrganization());

		List<String> properties = new ArrayList<String>();

		properties.add(EmployeeDTO.PROPERTY_FIRSTNAME);

		//listEmployee = dao.getListEmployeeSearch(bean, ra, orgs, properties);
	}

	/* Ажилтан нэмэх */
	Object onActionFromAddEmployee() {
		pageEmpDetail.setEmployee(null);
		pageEmpDetail.setOrganization(organization);
		pageEmpDetail.setMemberType("emp");
		pageEmpDetail.setIsView(false);
		activeTab = "detail";
		activeSubTab = "personal";
		LayoutEmployee.setActiveTab(null);
		LayoutEmployee.setActiveSubTab(null);
		LayoutEmployee.setSwitchBlock(1);
		return pageEmpDetail;
	}

	Object onActionFromEdit(Employee emp) {
		pageEmpDetail.setEmployee(emp);
		pageEmpDetail.setOrganization(organization);
		pageEmpDetail.setMemberType("emp");
		pageEmpDetail.setViewEmployee(true);
		pageEmpDetail.setIsView(false);
		activeTab = "detail";
		activeSubTab = "personal";
		LayoutEmployee.setActiveTab(null);
		LayoutEmployee.setActiveSubTab(null);
		LayoutEmployee.setSwitchBlock(1);
		return pageEmpDetail;
	}

	Object onActionFromView(Employee emp) {
		pageEmpDetail.setEmployee(emp);
		pageEmpDetail.setViewEmployee(false);
		pageEmpDetail.setOrganization(organization);
		pageEmpDetail.setMemberType("emp");
		pageEmpDetail.setIsView(false);
		activeTab = "detail";
		activeSubTab = "personal";
		LayoutEmployee.setActiveTab(null);
		LayoutEmployee.setActiveSubTab(null);
		LayoutEmployee.setSwitchBlock(1);
		return pageEmpDetail;
	}

	@CommitAfter
	void onActionFromDelete(Employee emp) {
		dao.deleteObject(emp);
	}

	// Object onActionFromGoImportExcel(){
	//
	// return pageImportExcel;
	// }

	/* Хайх */
	void onSelectedFromSearch() {

	}

	/* selection model */
	public SelectModel getOrganizationSelectionModel() {
		OrganizationSelectionModel sm = new OrganizationSelectionModel(dao);
		return sm;
	}

	/* Getter Setter */

	public Asset getImgExcel() {
		return imgExcel;
	}

	public Asset getEditIcon() {
		return editIcon;
	}

	public Asset getDeleteIcon() {
		return deleteIcon;
	}

	public void setEditIcon(Asset editIcon) {
		this.editIcon = editIcon;
	}

	public int getNumber() {
		return listEmployee.indexOf(valueEmployee) + 1;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	/*
	 * public Employee getEmployee() { return employee; }
	 * 
	 * 
	 * public void setEmployee(Employee employee) { this.employee = employee; }
	 */

	public String getGridRowCSS() {
		return GRID_ROW_CSS;
	}

	public String getListSize() {
		if (listEmployee.isEmpty())
			return "0";

		return listEmployee.size() + "";
	}

	public String getFirstname() {
		if (valueEmployee == null || valueEmployee.getFirstName() == null)
			return "";
		return valueEmployee.getFirstName();
	}

	/* export excel */
	void onActionFromExport() {

		try {
			HSSFWorkbook document = new HSSFWorkbook();
			HSSFSheet sheet = ReportUtil.createSheet(document);

			sheet.setMargin((short) 0, 0.5);
			ReportUtil.setColumnWidths(sheet, 0, 0.5, 1, 0.5, 2, 2, 3, 2, 3, 2,
					4, 3, 5, 2, 6, 2, 7, 2, 8, 2, 9, 2);

			Map<String, HSSFCellStyle> styles = ReportUtil
					.createStyles(document);

			int sheetNumber = 0;
			int rowIndex = 1;
			int colIndex = 1;
			Long index = 1L;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2,
					messages.get("employeeList"), styles.get("title"), 5);

			ExcelAPI.setCellValue(document, sheetNumber, ++rowIndex, 1,
					messages.get("date") + ": " + format.format(new Date()),
					styles.get("plain-left-wrap"), 5);
			rowIndex += 2;

			/* column headers */

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1,
					messages.get("number-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2,
					messages.get("lastname-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3,
					messages.get("firstname-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 4,
					messages.get("registerNo-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 5,
					messages.get("gender-label"), styles.get("header-wrap"));

			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;
			if (listEmployee != null)
				for (EmployeeDTO s : listEmployee) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, listEmployee.indexOf(s) + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, s.getLastname(),
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, s.getFirstName(),
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, s.getRegisterNo(),
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(
							document,
							sheetNumber,
							rowIndex,
							colIndex++,
							s.getGender() != null ? messages.get(s.getGender()
									.toString()) : "", styles
									.get("plain-left-wrap-border"));

					ReportUtil.setRowHeight(sheet, rowIndex, 3);
					rowIndex++;
					index++;
					colIndex = 1;

				}

			rowIndex += 2;

			ExcelAPI.setCellValue(
					document,
					sheetNumber,
					rowIndex,
					1,
					"ГАРГАСАН: " + loginState.getLogin()
							+ "  : ................  / "
							+ loginState.getLogin() + " /",
					styles.get("plain-left-wrap"), 8);
			rowIndex++;

			OutputStream out = response
					.getOutputStream("application/vnd.ms-excel");
			response.setHeader("Content-Disposition",
					"attachment; filename=\"employeeList.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
