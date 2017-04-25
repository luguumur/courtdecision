package mn.mcs.electronics.court.pages.configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.User;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.enums.AdminOperationType;
import mn.mcs.electronics.court.model.EmployeeSelectionModel;
import mn.mcs.electronics.court.util.Constants;
import mn.mcs.electronics.court.util.ExcelAPI;
import mn.mcs.electronics.court.util.ReportUtil;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.util.EnumSelectModel;

public class UserConfig {

	@Inject
	private CoreDAO dao;

	@Inject
	private Messages messages;

	@SessionState
	private LoginState loginState;

	@Inject
	private Response response;

	@Inject
	@Path("context:/images/excel.jpg")
	private Asset imgExcel;

	@Property
	@Persist
	private List<Employee> listUser;

	@Property
	@Persist
	private Employee valueUser;

	@Property
	private Employee emp;

	@Persist
	private Set<Employee> selectedEmps;

	@Persist
	@Property
	private AdminOperationType operation;

	@Persist("flash")
	@Property
	private List<String> usernames;

	@Property
	private String usernameLoop;

	@Persist("flash")
	@Property
	private HashMap<String, String> names;

	@Persist("flash")
	@Property
	private HashMap<String, String> passwds;

	@Persist
	@Property
	private List<String> userNamesE;

	@Persist
	@Property
	private HashMap<String, String> namesE;

	@Persist
	@Property
	private HashMap<String, String> passwdsE;

	/* User phone list */

	@Persist("flash")
	@Property
	private List<String> phones;

	@Persist
	@Property
	private List<String> phones2;

	@Property
	private String namesLoop;

	@Persist("flash")
	@Property
	private HashMap<String, String> homePhones;

	@Persist("flash")
	@Property
	private HashMap<String, String> homePhones2;

	@Persist("flash")
	@Property
	private HashMap<String, String> mobiles;

	@Persist("flash")
	@Property
	private HashMap<String, String> mobiles2;

	@Persist("flash")
	@Property
	private HashMap<String, String> workPhones;

	@Persist("flash")
	@Property
	private HashMap<String, String> workPhones2;

	void beginRender() {

		listUser = dao.getListUser();

		if (selectedEmps == null)
			selectedEmps = new HashSet<Employee>();
	}

	@CommitAfter
	void onSelectedFromExecute() {

		if (operation.equals(AdminOperationType.PASSWDCHANGE))
			this.changePasswords();

		if (operation.equals(AdminOperationType.SHOWPHONENUMBERS))
			this.showPhones();

	}

	void onActionFromSelectAll() {
		selectedEmps.addAll(listUser);
	}

	void onActionFromDeselectAll() {
		selectedEmps.clear();
	}

	/* additional function */
	private void changePasswords() {

		usernames = new ArrayList<String>();
		names = new HashMap<String, String>();
		passwds = new HashMap<String, String>();

		userNamesE = new ArrayList<String>();
		namesE = new HashMap<String, String>();
		passwdsE = new HashMap<String, String>();

		for (Employee emp : selectedEmps) {
			User user = dao.getUser(emp);

			if (user != null) {
				String passwd = user.generateRandomPassword();

				user.setPassword(passwd);

				dao.saveOrUpdateObject(dao.mergeObject(user));

				usernames.add(user.getUsername());
				names.put(user.getUsername(), emp.getFullNameFirstChar());
				passwds.put(user.getUsername(), passwd);

			}

		}
		userNamesE.addAll(usernames);
		namesE.putAll(names);
		passwdsE.putAll(passwds);
	}

	private void showPhones() {
		phones = new ArrayList<String>();
		phones2 = new ArrayList<String>();

		homePhones = new HashMap<String, String>();
		homePhones2 = new HashMap<String, String>();

		mobiles = new HashMap<String, String>();
		mobiles2 = new HashMap<String, String>();

		workPhones = new HashMap<String, String>();
		workPhones2 = new HashMap<String, String>();

		for (Employee emp : selectedEmps) {
			User user = dao.getUser(emp);

			if (user != null && emp.getPhoneNo() != null
					&& emp.getMobileNo() != null
					&& emp.getWorkPhoneNo() != null) {

				phones.add(user.getUsername());

				homePhones.put(user.getUsername(), emp.getPhoneNo().toString());
				mobiles.put(user.getUsername(), emp.getMobileNo().toString());
				workPhones.put(user.getUsername(), emp.getWorkPhoneNo()
						.toString());
			}
		}
		phones2.addAll(phones);

		homePhones2.putAll(homePhones);
		mobiles2.putAll(mobiles);
		workPhones2.putAll(workPhones);
	}

	/* selection model */
	public SelectModel getEmployeeSelectionModel() {

		EmployeeSelectionModel emp = new EmployeeSelectionModel(dao);

		return emp;
	}

	public SelectModel getOperationSelectionModel() {

		return new EnumSelectModel(AdminOperationType.class, messages);
	}

	/* getter setter */

	public String getUsername() {

		User user = dao.getUser(valueUser);

		if (user != null)
			return user.getUsername();

		return "";
	}

	public String getRole() {
		User user = dao.getUser(valueUser);

		if (user != null)
			return user.getRoles().getName();

		return "";
	}

	public boolean getSelected() {
		return selectedEmps.contains(valueUser);
	}

	public void setSelected(boolean selected) {
		if (selected) {
			selectedEmps.add(valueUser);
		} else {
			selectedEmps.remove(valueUser);
		}
	}

	public int getNumber() {
		return listUser.indexOf(valueUser) + 1;
	}

	public String getFullnameLoop() {
		return names.get(usernameLoop);
	}

	public String getPasswdLoop() {
		return passwds.get(usernameLoop);
	}

	public String getPhoneLoop() {
		return homePhones.get(namesLoop);
	}

	public String getMobileLoop() {
		return mobiles.get(namesLoop);
	}

	public String getWorkPhoneLoop() {
		return workPhones.get(namesLoop);
	}

	public String getOrganization() {
		if (valueUser != null && valueUser.getOrganization() != null)
			return valueUser.getOrganization().getName();
		return "";
	}

	public Asset getImgExcel() {
		return imgExcel;
	}

	void onActionFromExportToExcel() throws FileNotFoundException {

		int col, row;

		try {

			SimpleDateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT);

			HSSFWorkbook document = new HSSFWorkbook();
			HSSFSheet sheet = ReportUtil.createSheet(document);
			sheet.setSelected(true);
			Map<String, HSSFCellStyle> styles = ReportUtil
					.createStyles(document);

			ReportUtil.setColumnWidths(sheet, 0, 0.5, 1, 2.5, 2, 2, 3, 2, 4, 2,
					5, 2, 6, 2, 7, 2, 8, 2);

			row = 1;
			col = 1;

			/* row 1 Organization */
			ExcelAPI.setCellStyle(document, 0, row, col,
					styles.get("bold-left"));
			ExcelAPI.setCellValue(document, 0, row, col,
					messages.get("Org-label"));

			col++;

			ExcelAPI.setCellValue(document, 0, row, col, loginState
					.getOrganization().getName());

			col = 1;
			row++;

			/* row 2 Employee name */
			ExcelAPI.setCellStyle(document, 0, row, col,
					styles.get("bold-left"));
			ExcelAPI.setCellValue(document, 0, row, col,
					messages.get("Employee-label"));

			col++;

			ExcelAPI.setCellValue(document, 0, row, col, loginState
					.getEmployee().getFullNameFirstChar());

			col = 1;
			row++;

			/* row 3 Date */
			ExcelAPI.setCellStyle(document, 0, row, col,
					styles.get("bold-left"));
			ExcelAPI.setCellValue(document, 0, row, col,
					messages.get("Date-label"));

			col++;

			ExcelAPI.setCellValue(document, 0, row, col, new Date(), df);

			col++;

			row++;

			/* row 4 Title */
			ReportUtil.setRowHeight(sheet, row, 8);
			ExcelAPI.mergeCells(document, 0, row, 2, row, 4);
			ExcelAPI.setCellStyle(document, 0, row, 2, styles.get("title"));
			ExcelAPI.setCellValue(document, 0, row, 2,
					messages.get("Uniform-label"));

			row += 2;

			col = 1;
			row++;
			for (String emp : userNamesE) {

				ExcelAPI.setCellValue(document, 0, row, col,
						messages.get("employeeLastNameFirstName"));
				ExcelAPI.setCellStyle(document, 0, row, col,
						styles.get("normal-center"));
				col++;

				ExcelAPI.setCellValue(document, 0, row, col, namesE.get(emp));

				ExcelAPI.setCellStyle(document, 0, row, col,
						styles.get("normal-center"));

				col = 1;
				row++;

				ExcelAPI.setCellValue(document, 0, row, col,
						messages.get("username"));
				ExcelAPI.setCellStyle(document, 0, row, col,
						styles.get("normal-center"));
				col++;

				ExcelAPI.setCellValue(document, 0, row, col, emp);

				ExcelAPI.setCellStyle(document, 0, row, col,
						styles.get("normal-center"));

				col = 1;
				row++;

				ExcelAPI.setCellValue(document, 0, row, col,
						messages.get("password"));
				ExcelAPI.setCellStyle(document, 0, row, col,
						styles.get("normal-center"));
				col++;

				ExcelAPI.setCellValue(document, 0, row, col, passwdsE.get(emp));

				ExcelAPI.setCellStyle(document, 0, row, col,
						styles.get("normal-center"));

				col = 1;
				row += 2;
				// id = usr.getEmployee().getId();

			}

			/* Signature */

			row += 3;
			ExcelAPI.setCellStyle(document, 0, row, col,
					styles.get("bold-left"));
			ExcelAPI.setCellValue(document, 0, row, col,
					messages.get("Signature"));

			/* Report Data end */

			OutputStream out = response
					.getOutputStream("application/vnd.ms-excel");

			response.setHeader("Content-Disposition",
					"attachment; filename=\"" + messages.get("excelPassword")
							+ " " + df.format(new Date()) + ".xls\"");

			document.write(out);

			out.close();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

}
