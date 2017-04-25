/* 
 * File Name: 		PagePromoteReport.java
 * 
 * Created By: 		G.Bileg-Ochir
 * Created Date: 	Jul 20, 2012
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Jul 20, 2012 1.0.0 			G.Bileg-Ochir					Шинээр үүсгэв.
 * 
 * -----------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2011 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */
package mn.mcs.electronics.court.pages.report;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.AppointmentType;
import mn.mcs.electronics.court.model.OrganizationSelectionModel;
import mn.mcs.electronics.court.util.Constants;
import mn.mcs.electronics.court.util.ExcelAPI;
import mn.mcs.electronics.court.util.ReportUtil;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.util.EnumSelectModel;

public class PagePromoteReport {
	@SessionState
	private LoginState loginState;

	@Inject
	private Response response;

	@Inject
	private CoreDAO dao;

	@Inject
	private Messages messages;

	@Property
	@Persist
	private Long listPromote;

	@Property
	@Persist
	private Long valuePromote;

	private static final String gridRowCss = "myGrid";

	@Property
	@Persist
	private Organization org;

	@Property
	@Persist
	private AppointmentType appointmentType;

	@Property
	@Persist
	private Date fromDate;

	@Property
	@Persist
	private Date toDate;

	@Inject
	@Path("context:/images/excel.jpg")
	private Asset imgExcel;

	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);

	void beginRender() {

		Subject currentUser = SecurityUtils.getSubject();

		if (!currentUser.isPermitted("show_all_organization"))
			org = loginState.getOrganization();

		listPromote = dao.getPromoteReport(org, fromDate, toDate,
				appointmentType);
	}

	/* select model */
	public SelectModel getOrganizationSelectionModel() {
		OrganizationSelectionModel sm = new OrganizationSelectionModel(dao);

		return sm;
	}

	public SelectModel getAppointmentTypeSelectionModel() {
		return new EnumSelectModel(AppointmentType.class, messages);
	}

	/* getter setter */

	public String getGridrowcss() {
		return gridRowCss;
	}

	public Asset getImgExcel() {
		return imgExcel;
	}

	public String getCount() {
		if (valuePromote == null)
			return "";

		return valuePromote.toString();
	}

	public String getOrgName() {
		if (org == null || org == null)
			return "Бүгд";

		return org.getName();
	}

	/* export to excel */
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
					messages.get("employeePromote"), styles.get("title"), 5);

			ExcelAPI.setCellValue(document, sheetNumber, ++rowIndex, 1,
					messages.get("date") + ": " + format.format(new Date()),
					styles.get("plain-left-wrap"), 5);
			rowIndex += 2;

			/* column headers */

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1,
					messages.get("number-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2,
					messages.get("organization-label"),
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3,
					messages.get("countPromote-label"),
					styles.get("header-wrap"));

			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++,
					"1", styles.get("plain-left-wrap-border"));

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++,
					(org != null) ? org.getName() : "",
					styles.get("plain-left-wrap-border"));

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++,
					listPromote + "", styles.get("plain-left-wrap-border"));

			ReportUtil.setRowHeight(sheet, rowIndex, 3);
			rowIndex++;
			index++;
			colIndex = 1;

			rowIndex += 2;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1,
					"ТАЙЛАН ГАРГАСАН: "
							// + loginState.getLogin()
							+ "......................  / "
							+ loginState.getEmployee().getLastname().charAt(0)
							+ "." + loginState.getEmployee().getFirstName()
							+ " /", styles.get("plain-left-wrap"), 8);
			rowIndex++;

			OutputStream out = response
					.getOutputStream("application/vnd.ms-excel");
			response.setHeader("Content-Disposition",
					"attachment; filename=\"employeePromote.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
