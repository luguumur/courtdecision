package mn.mcs.electronics.court.pages;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.util.Constants;
import mn.mcs.electronics.court.util.ExcelAPI;
import mn.mcs.electronics.court.util.ReportUtil;
import mn.mcs.electronics.court.util.beans.EstReportBean;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

@Import(library = {}, stylesheet = { "context:css/dataTables.tableTools.css" })
public class EstablishmentReport {

	/*
	 * STATES
	 */

	@SessionState
	private LoginState loginState;

	/*
	 * INJECTS
	 */

	@Inject
	private CoreDAO dao;

	@Inject
	private Messages messages;

	@Inject
	private AlertManager manager;

	@Inject
	private Request request;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@Inject
	private Response response;

	/*
	 * PERSISTS
	 */

	/*
	 * PROPERTIES
	 */

	@Property
	@Persist
	private List<EstReportBean> listBean;

	@Property
	private EstReportBean bean;

	@Property
	private Integer index;

	private SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
	int allCol1Ahlagch = 0;
	int allCol1Engiin = 0;
	int allCol1Off = 0;
	int allCol2Ahlagch = 0;
	int allCol2Engiin = 0;
	int allCol2Off = 0;
	int allCol3Ahlagch = 0;
	int allCol3Engiin = 0;
	int allCol3Off = 0;
	int allColTotal1 = 0;
	int allColTotal2 = 0;
	int allColTotal3 = 0;

	void beginRender() {
		initList();
		for (EstReportBean bean : listBean) {
			allCol1Ahlagch += bean.getCol1Ahlagch();
			allCol1Engiin += bean.getCol1Engiin();
			allCol1Off += bean.getCol1Off();
			allCol2Ahlagch += bean.getCol2Ahlagch();
			allCol2Engiin += bean.getCol2Engiin();
			allCol2Off += bean.getCol2Off();
			allCol3Ahlagch += bean.getCol3Ahlagch();
			allCol3Engiin += bean.getCol3Engiin();
			allCol3Off += bean.getCol3Off();
			allColTotal1 += bean.getColTotal1();
			allColTotal2 += bean.getColTotal2();
			allColTotal3 += bean.getColTotal3();
		}
	}

	void initList() {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isPermitted("show_all_organization")) {
			listBean = dao.generateEstablishmentReport(null);
		} else {
			listBean = dao.generateEstablishmentReport(loginState.getUser().getEmployee().getOrganization().getId());
		}
	}

	public String getAllCol1Ahlagch() {
		return allCol1Ahlagch + "";
	}

	public String getAllCol1Engiin() {
		return allCol1Engiin + "";
	}

	public String getAllCol1Off() {
		return allCol1Off + "";
	}

	public String getAllCol2Ahlagch() {
		return allCol2Ahlagch + "";
	}

	public String getAllCol2Engiin() {
		return allCol2Engiin + "";
	}

	public String getAllCol2Off() {
		return allCol2Off + "";
	}

	public String getAllCol3Ahlagch() {
		return allCol3Ahlagch + "";
	}

	public String getAllCol3Engiin() {
		return allCol3Engiin + "";
	}

	public String getAllCol3Off() {
		return allCol3Off + "";
	}

	public String getAllColTotal1() {
		return allColTotal1 + "";
	}

	public String getAllColTotal2() {
		return allColTotal2 + "";
	}

	public String getAllColTotal3() {
		return allColTotal3 + "";
	}

	public String getOrganizationName() {
		if (bean != null && bean.getOrg() != null) {
			Organization org = (Organization) dao.getObject(Organization.class, bean.getOrg());
			return org.getName();
		}

		return "-";
	}

	public String getOrgName(EstReportBean s) {
		if (s != null && s.getOrg() != null) {
			Organization org = (Organization) dao.getObject(Organization.class, s.getOrg());
			return org.getName();
		}

		return "-";
	}

	public int getTotal() {
		return bean.getColTotal1() + bean.getColTotal2() + bean.getColTotal3();
	}

	public int getRowTotal() {
		return allColTotal3 + allColTotal2 + allColTotal1;
	}

	public int getTotalFooter() {
		return bean.getCol1Ahlagch();
	}

	/* export excel */
	void onActionFromExport() {

		try {
			HSSFWorkbook document = new HSSFWorkbook();
			HSSFSheet sheet = ReportUtil.createSheet(document);

			sheet.setMargin((short) 0, 0.5);
			ReportUtil.setColumnWidths(sheet, 0, 0.5, 1, 0.5, 2, 2, 3, 2, 3, 2, 4, 3, 5, 2, 6, 2, 7, 2, 8, 2, 9, 2);

			Map<String, HSSFCellStyle> styles = ReportUtil.createStyles(document);

			int sheetNumber = 0;
			int rowIndex = 1;
			int colIndex = 1;
			Long index = 1L;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2, messages.get("excel-report"), styles.get("title"),
					8);

			ExcelAPI.setCellValue(document, sheetNumber, ++rowIndex, 1,
					messages.get("date") + ": " + format.format(new Date()), styles.get("plain-left-wrap"), 5);

			rowIndex += 2;

			/* column headers */

			ExcelAPI.mergeCells(document, sheetNumber, rowIndex, 1, rowIndex + 1, 1);
			ExcelAPI.mergeCells(document, sheetNumber, rowIndex, 2, rowIndex + 1, 2);
			ExcelAPI.mergeCells(document, sheetNumber, rowIndex, 3, rowIndex, 5);
			ExcelAPI.mergeCells(document, sheetNumber, rowIndex, 6, rowIndex + 1, 6);
			ExcelAPI.mergeCells(document, sheetNumber, rowIndex, 7, rowIndex, 9);
			ExcelAPI.mergeCells(document, sheetNumber, rowIndex, 10, rowIndex + 1, 10);
			ExcelAPI.mergeCells(document, sheetNumber, rowIndex, 11, rowIndex, 13);
			ExcelAPI.mergeCells(document, sheetNumber, rowIndex, 14, rowIndex + 1, 14);
			ExcelAPI.mergeCells(document, sheetNumber, rowIndex, 15, rowIndex + 1, 15);
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1, "№", styles.get("header-wrap"));
			ExcelAPI.setRangeCellStyle(document, sheetNumber, rowIndex, rowIndex + 1, 1, 1, styles.get("header-wrap"));

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2, messages.get("angi-albad"),
					styles.get("header-wrap"));
			ExcelAPI.setRangeCellStyle(document, sheetNumber, rowIndex, rowIndex + 1, 2, 2, styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3, messages.get("tusvuus_tsalinjdag"),
					styles.get("header-wrap"));
			ExcelAPI.setRangeCellStyle(document, sheetNumber, rowIndex, rowIndex, 3, 5, styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 6, messages.get("total"), styles.get("header-wrap"));
			ExcelAPI.setRangeCellStyle(document, sheetNumber, rowIndex, rowIndex + 1, 6, 6, styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 7, messages.get("orlogoos_tsalinjdag"),
					styles.get("header-wrap"));
			ExcelAPI.setRangeCellStyle(document, sheetNumber, rowIndex, rowIndex, 7, 9, styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 10, messages.get("total"),
					styles.get("header-wrap"));
			ExcelAPI.setRangeCellStyle(document, sheetNumber, rowIndex, rowIndex + 1, 10, 10,
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 11, messages.get("dutuu_orontoo"),
					styles.get("header-wrap"));
			ExcelAPI.setRangeCellStyle(document, sheetNumber, rowIndex, rowIndex, 11, 13, styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 14, messages.get("total"),
					styles.get("header-wrap"));
			ExcelAPI.setRangeCellStyle(document, sheetNumber, rowIndex, rowIndex + 1, 14, 14,
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 15, messages.get("niit_orontoo"),
					styles.get("header-wrap"));
			ExcelAPI.setRangeCellStyle(document, sheetNumber, rowIndex, rowIndex + 1, 15, 15,
					styles.get("header-wrap"));

			rowIndex++;
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3, messages.get("officer"),
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 4, messages.get("ahlagch"),
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 5, messages.get("engiin"),
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 7, messages.get("officer"),
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 8, messages.get("ahlagch"),
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 9, messages.get("engiin"),
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 11, messages.get("officer"),
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 12, messages.get("ahlagch"),
					styles.get("header-wrap"));
			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 13, messages.get("engiin"),
					styles.get("header-wrap"));

			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;
			int p = 0;
			if (listBean != null)
				for (EstReportBean s : listBean) {

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++, p + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++, getOrgName(s),
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++, s.getCol1Off().toString(),
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++, s.getCol1Ahlagch().toString(),
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++, s.getCol1Engiin().toString(),
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++, s.getColTotal1().toString(),
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++, s.getCol2Off().toString(),
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++, s.getCol2Ahlagch().toString(),
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++, s.getCol2Engiin().toString(),
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++, s.getColTotal2().toString(),
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++, s.getCol3Ahlagch().toString(),
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++, s.getCol3Ahlagch().toString(),
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++, s.getCol3Engiin().toString(),
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++, s.getColTotal3().toString(),
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex, colIndex++,
							(s.getColTotal1() + s.getColTotal2() + s.getColTotal3()) + "",
							styles.get("plain-left-wrap-border"));

					ReportUtil.setRowHeight(sheet, rowIndex, 3);
					rowIndex++;
					index++;
					colIndex = 1;

				}

			rowIndex += 2;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1,
					"ТАЙЛАН ГАРГАСАН: " + ".....................................  / "
							+ loginState.getEmployee().getLastname().charAt(0) + "."
							+ loginState.getEmployee().getFirstName() + " /",
					styles.get("plain-left-wrap"), 8);
			rowIndex++;

			OutputStream out = response.getOutputStream("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=\"establishmentreport.xls\"");

			document.write(out);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}