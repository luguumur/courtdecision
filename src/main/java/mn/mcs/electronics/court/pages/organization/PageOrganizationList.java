package mn.mcs.electronics.court.pages.organization;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.model.CityProvinceSelectionModel;
import mn.mcs.electronics.court.model.OrganizationTypeSelectionModel;
import mn.mcs.electronics.court.util.Constants;
import mn.mcs.electronics.court.util.ExcelAPI;
import mn.mcs.electronics.court.util.ReportUtil;
import mn.mcs.electronics.court.util.beans.OrganizationSearchBean;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

public class PageOrganizationList {

	/*
	 * INJECTS
	 */

	@Inject
	private CoreDAO dao;

	@Inject
	private Messages messages;

	@Inject
	private Response response;

	@SessionState
	private LoginState loginState;

	@InjectPage
	private PageOrganization pageOrganization;

	@InjectPage
	private PageOrganizationInfo pageOrganizationInfo;

	@Inject
	@Path("context:/images/excel.jpg")
	private Asset imgExcel;

	@InjectComponent
	private Zone orgFormZone, orgListZone;

	@Inject
	private Request request;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@Inject
	private AlertManager manager;

	/*
	 * PERSISTS
	 */

	@Property
	@Persist
	private OrganizationSearchBean bean;

	@Property
	@Persist
	private List<Organization> listOrganization;

	@Property
	@Persist
	private Organization valueOrganization;

	/*
	 * PROPERTIES
	 */

	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);

	void beginRender() {

		if (bean == null)
			bean = new OrganizationSearchBean();

		if (listOrganization == null)
			listOrganization = new ArrayList<Organization>();

		initGridOrganization();

	}

	void initGridOrganization() {
		listOrganization = dao.getListOrganization(bean);

		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isPermitted("show_all_organization")) {
			HashSet<Organization> orgs = new HashSet<Organization>(loginState
					.getEmployee().getMapEmpOrg());

			if (orgs.isEmpty())
				orgs.add(loginState.getOrganization());
		}
	}

	void onSelectedFromSearch() {
		if (request.isXHR()) {
			initGridOrganization();
			ajaxResponseRenderer.addRender(orgListZone);
		}
	}

	PageOrganizationInfo onActionFromEdit(Organization org) {
		pageOrganizationInfo.setOrganization(org);
		return pageOrganizationInfo;
	}

	@CommitAfter
	void onActionFromDelete(Organization org) {
		dao.deleteObject(org);
	}

	public Integer getNumber() {
		return listOrganization.indexOf(valueOrganization) + 1;
	}

	Object onActionFromGoToOrganizationPage() {
		pageOrganizationInfo.setOrganization(new Organization());
		return pageOrganizationInfo;
	}

	/* getter, setter */

	public String getName() {
		if (valueOrganization == null || valueOrganization.getName() == null)
			return "";
		return valueOrganization.getName();
	}

	public Asset getImgExcel() {
		return imgExcel;
	}

	public String getListSize() {
		if (listOrganization.isEmpty())
			return "0";

		return listOrganization.size() + "";
	}

	/*
	 * SELECT MODELS
	 */

	public SelectModel getCitySelectionModel() {
		return new CityProvinceSelectionModel(dao);
	}

	public SelectModel getOrganizationTypeSelectionModel() {
		return new OrganizationTypeSelectionModel(dao);
	}

	/* export excel */
	void onActionFromExport() {

		try {
			HSSFWorkbook document = new HSSFWorkbook();
			HSSFSheet sheet = ReportUtil.createSheet(document);

			sheet.setMargin((short) 0, 0.5);
			ReportUtil.setColumnWidths(sheet, 0, 0.5, 1, 0.5, 2, 2, 3, 3, 4, 3,
					5, 2, 6, 2, 7, 2, 8, 2, 9, 2);

			Map<String, HSSFCellStyle> styles = ReportUtil
					.createStyles(document);

			int sheetNumber = 0;
			int rowIndex = 1;
			int colIndex = 1;
			Long index = 1L;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2,
					messages.get("organizationList"), styles.get("title"), 5);

			ExcelAPI.setCellValue(document, sheetNumber, ++rowIndex, 1,
					messages.get("date") + ": " + format.format(new Date()),
					styles.get("plain-left-wrap"), 5);
			rowIndex += 2;

			/* column headers */

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1,
					messages.get("number-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2,
					messages.get("cityProvince-label"),
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3,
					messages.get("organizationName-label"),
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 4,
					messages.get("shortName-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 5,
					messages.get("registerNo-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 6,
					messages.get("phoneNo-label"), styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 7,
					messages.get("address-label"), styles.get("header-wrap"));

			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;
			if (listOrganization != null)
				for (Organization s : listOrganization) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, listOrganization.indexOf(s) + 1 + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getCityProvince() != null) ? s
									.getCityProvince().getCityName() : "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, (s.getName() != null) ? s.getName()
									: "", styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,
							(s.getShortName() != null) ? s.getShortName() : "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,
							(s.getRegister() != null) ? s.getRegister() : "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,
							(s.getPhoneNo() != null) ? s.getPhoneNo() : "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,
							(s.getAddress() != null) ? s.getAddress() : "",
							styles.get("plain-left-wrap-border"));

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
					"attachment; filename=\"organizationList.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
