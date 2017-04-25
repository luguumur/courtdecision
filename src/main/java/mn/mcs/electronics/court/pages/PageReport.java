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
import mn.mcs.electronics.court.util.beans.GeneralReportBean;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

public class PageReport {

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

	@Persist
	@Property
	private List<GeneralReportBean> listBean;

	@Property
	private GeneralReportBean bean;

	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);

	int allnum = 0;
	int allfemale = 0;
	int allmale = 0;
	int allage1 = 0;
	int allage2 = 0;
	int allage3 = 0;
	int allage4 = 0;
	int allage5 = 0;
	int allage6 = 0;
	int allage7 = 0;
	int allage8 = 0;
	int allage9 = 0;
	int alledu1 = 0;
	int alledu2 = 0;
	int alledu3 = 0;
	int alledu4 = 0;
	int alledu5 = 0;
	int alledu6 = 0;
	int alledu7 = 0;
	int alledu8 = 0;
	int alltsol1 = 0;
	int alltsol2 = 0;
	int alltsol3 = 0;
	int alltsol4 = 0;
	int alltsol5 = 0;
	int alltsol6 = 0;
	int alltsol7 = 0;
	int alltsol8 = 0;
	int alltsol9 = 0;
	int alltsol10 = 0;
	int alltsol11 = 0;
	int alltsol12 = 0;
	int allat1 = 0;
	int allat2 = 0;
	int allat3 = 0;
	int allsal1 = 0;
	int allsal2 = 0;

	void beginRender() {

		initList();

		for (GeneralReportBean bean : listBean) {
			allnum += bean.getNum();
			allfemale += bean.getFemale();
			allmale += bean.getMale();
			allage1 += bean.getAge1();
			allage2 += bean.getAge2();
			allage3 += bean.getAge3();
			allage4 += bean.getAge4();
			allage5 += bean.getAge5();
			allage6 += bean.getAge6();
			allage7 += bean.getAge7();
			allage8 += bean.getAge8();
			allage9 += bean.getAge9();
			alledu1 += bean.getEdu1();
			alledu2 += bean.getEdu2();
			alledu3 += bean.getEdu3();
			alledu4 += bean.getEdu5();
			alledu5 += bean.getEdu5();
			alledu6 += bean.getEdu6();
			alledu7 += bean.getEdu7();
			alledu8 += bean.getEdu8();
			alltsol1 += bean.getTsol1();
			alltsol2 += bean.getTsol2();
			alltsol3 += bean.getTsol3();
			alltsol4 += bean.getTsol4();
			alltsol5 += bean.getTsol5();
			alltsol6 += bean.getTsol6();
			alltsol7 += bean.getTsol7();
			alltsol8 += bean.getTsol8();
			alltsol9 += bean.getTsol9();
			alltsol10 += bean.getTsol10();
			alltsol11 += bean.getTsol11();
			alltsol12 += bean.getTsol12();
			allat1 += bean.getAt1();
			allat2 += bean.getAt2();
			allat3 += bean.getAt3();
			allsal1 += bean.getSal1();
			allsal2 += bean.getSal2();

		}
	}

	void initList() {
		Subject currentUser = SecurityUtils.getSubject();

		if (currentUser.isPermitted("show_all_organization")) {
			listBean = dao.generateReport(null);
		} else {

			listBean = dao.generateReport(loginState.getUser().getEmployee()
					.getOrganization().getId());
		}
	}

	public String getOrganizationName() {
		if (bean != null && bean.getOrg() != null) {
			Organization org = (Organization) dao.getObject(Organization.class,
					bean.getOrg());
			return org.getName();
		}

		return "-";
	}

	void onActionFromExportToExcel() {
		try {
			HSSFWorkbook document = new HSSFWorkbook();
			HSSFSheet sheet = ReportUtil.createSheet(document);
			sheet.setMargin((short) 0, 0.5);
			Map<String, HSSFCellStyle> styles = ReportUtil
					.createStyles(document);

			int sheetNumber = 0;
			int rowIndex = 1;
			int colIndex = 1;
			Long index = 1L;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2,
					messages.get("mainReport"), styles.get("title"), 5);

			ExcelAPI.setCellValue(document, sheetNumber, ++rowIndex, 1,
					messages.get("date") + ": " + format.format(new Date()),
					styles.get("plain-left-wrap"), 5);

			rowIndex += 2;

			/* column headers */

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1,
					messages.get("number-label"), styles.get("header-wrap"), 1,
					2);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 2,
					"Анги албад", styles.get("header-wrap"), 1, 2);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 3, "Нийт",
					styles.get("header-wrap"), 1, 2);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 4, "Хүйс",
					styles.get("header-wrap"), 2, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 6, "Нас",
					styles.get("header-wrap"), 9, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 15,
					"Боловсрол", styles.get("header-wrap"), 8, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 23, "Цол",
					styles.get("header-wrap"), 12, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 35,
					"Албан тушаал", styles.get("header-wrap"), 3, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 38, "Цалин",
					styles.get("header-wrap"), 2, 1);

			rowIndex++;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 4,
					"Эрэгтэй", styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 5,
					"Эмэгтэй", styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 6,
					"21 хүртэл", styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 7, "21-30",
					styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 8, "31-35",
					styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 9, "36-40",
					styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 10, "41-45",
					styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 11, "46-50",
					styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 12, "51-55",
					styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 13, "56-60",
					styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 14,
					"60с дээш", styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 15,
					"Доктор", styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 16,
					"Магистр", styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 17,
					"Бакалавр", styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 18,
					"Дип дээд", styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 19,
					"Тусгай дунд", styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 20,
					"Бүрэн дунд", styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 21,
					"Бүрэн бус дунд", styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 22, "Бага",
					styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 23,
					"Хурандаа", styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 24,
					"Дэд хурандаа</", styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 25,
					"Хошууч", styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 26, "Ахмад",
					styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 27,
					"Ахлах дэслэгч", styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 28,
					"Дэслэгч", styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 29,
					"Сургагч ахлагч", styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 30,
					"Ахлах ахлагч", styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 31,
					"Ахлагч", styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 32,
					"Дэд ахлагч", styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 33,
					"Офицер", styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 34,
					"Ахлагч", styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 35,
					"Удирдах", styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 36,
					"Гүйцэтгэх", styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 37,
					"Туслах", styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 38,
					"ТТ-ийн", styles.get("header-wrap"), 1, 1);

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 39,
					"ТҮ-ийн", styles.get("header-wrap"), 1, 1);

			ReportUtil.setRowHeight(sheet, rowIndex, 3);

			rowIndex++;

			if (listBean != null) {
				for (GeneralReportBean bean : listBean) {
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, listBean.indexOf(bean) + 1 + "",
							styles.get("plain-left-wrap-border"));

					Organization organization = null;
					if (bean.getOrg() != null) {
						organization = (Organization) dao.getObject(
								Organization.class, bean.getOrg());
					}

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++,
							organization != null ? organization.getName() : "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getNum() + "",
							styles.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getFemale() + "",
							styles.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getMale() + "",
							styles.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getAge1() + "",
							styles.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getAge2() + "",
							styles.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getAge3() + "",
							styles.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getAge4() + "",
							styles.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getAge5() + "",
							styles.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getAge6() + "",
							styles.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getAge7() + "",
							styles.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getAge8() + "",
							styles.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getAge9() + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getEdu1() + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getEdu2() + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getEdu3() + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getEdu4() + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getEdu5() + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getEdu6() + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getEdu7() + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getEdu8() + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getTsol1() + "",
							styles.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getTsol2() + "",
							styles.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getTsol3() + "",
							styles.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getTsol4() + "",
							styles.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getTsol5() + "",
							styles.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getTsol6() + "",
							styles.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getTsol7() + "",
							styles.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getTsol8() + "",
							styles.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getTsol9() + "",
							styles.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getTsol10() + "",
							styles.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getTsol11() + "",
							styles.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getTsol12() + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getAt1() + "",
							styles.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getAt2() + "",
							styles.get("plain-left-wrap-border"));
					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getAt3() + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getSal1() + "",
							styles.get("plain-left-wrap-border"));

					ExcelAPI.setCellValue(document, sheetNumber, rowIndex,
							colIndex++, bean.getSal1() + "",
							styles.get("plain-left-wrap-border"));

					rowIndex++;
					index++;
					colIndex = 1;
				}
			}
			
			rowIndex ++;

			rowIndex += 2;

			ExcelAPI.setCellValue(document, sheetNumber, rowIndex, 1,
					"ТАЙЛАН ГАРГАСАН: "
							+ ".....................................  / "
							+ loginState.getEmployee().getLastname().charAt(0)
							+ "." + loginState.getEmployee().getFirstName()
							+ " /", styles.get("plain-left-wrap"), 8);
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

	public String getAllNum() {
		return allnum + "";
	}

	public String getAllFemale() {
		return allfemale + "";
	}

	public String getAllMale() {
		return allmale + "";
	}

	public String getAllAge1() {
		return allage1 + "";
	}

	public String getAllAge2() {
		return allage2 + "";
	}

	public String getAllAge3() {
		return allage3 + "";
	}

	public String getAllAge4() {
		return allage4 + "";
	}

	public String getAllAge5() {
		return allage5 + "";
	}

	public String getAllAge6() {
		return allage6 + "";
	}

	public String getAllAge7() {
		return allage7 + "";
	}

	public String getAllAge8() {
		return allage8 + "";
	}

	public String getAllAge9() {
		return allage9 + "";
	}

	public String getAllEdu1() {
		return alledu1 + "";
	}

	public String getAllEdu2() {
		return alledu2 + "";
	}

	public String getAllEdu3() {
		return alledu3 + "";
	}

	public String getAllEdu4() {
		return alledu4 + "";
	}

	public String getAllEdu5() {
		return alledu5 + "";
	}

	public String getAllEdu6() {
		return alledu6 + "";
	}

	public String getAllEdu7() {
		return alledu7 + "";
	}

	public String getAllEdu8() {
		return alledu8 + "";
	}

	public String getAllTsol1() {
		return alltsol1 + "";
	}

	public String getAllTsol2() {
		return alltsol2 + "";
	}

	public String getAllTsol3() {
		return alltsol3 + "";
	}

	public String getAllTsol4() {
		return alltsol4 + "";
	}

	public String getAllTsol5() {
		return alltsol5 + "";
	}

	public String getAllTsol6() {
		return alltsol6 + "";
	}

	public String getAllTsol7() {
		return alltsol7 + "";
	}

	public String getAllTsol8() {
		return alltsol8 + "";
	}

	public String getAllTsol9() {
		return alltsol9 + "";
	}

	public String getAllTsol10() {
		return alltsol10 + "";
	}

	public String getAllTsol11() {
		return alltsol11 + "";
	}

	public String getAllTsol12() {
		return alltsol12 + "";
	}

	public String getAllAt1() {
		return allat1 + "";
	}

	public String getAllAt2() {
		return allat2 + "";
	}

	public String getAllAt3() {
		return allat3 + "";
	}

	public String getAllSal1() {
		return allsal1 + "";
	}

	public String getAllSal2() {
		return allsal2 + "";
	}

}