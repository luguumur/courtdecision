package mn.mcs.electronics.court.components.excelImport;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.Appointment;
import mn.mcs.electronics.court.entities.configuration.CommandSubject;
import mn.mcs.electronics.court.entities.configuration.ForeignLanguage;
import mn.mcs.electronics.court.entities.configuration.SkillType;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.Language;
import mn.mcs.electronics.court.entities.employee.Skills;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.EmployeeStatus;
import mn.mcs.electronics.court.enums.LanguageLevel;
import mn.mcs.electronics.court.enums.MilitaryOrSimple;
import mn.mcs.electronics.court.enums.OrganizationTypeExperience;
import mn.mcs.electronics.court.util.ExcelAPI;
import mn.mcs.electronics.court.util.ImportUtil;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.upload.services.UploadedFile;

public class CompExcelSkill {

	/*
	 * INJECTS
	 */

	@Inject
	private Request request;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@Inject
	private CoreDAO dao;

	@Inject
	private AlertManager manager;

	@Inject
	private Messages messages;

	/*
	 * PERSISTS
	 */

	@Persist
	private Organization organization;

	@Persist
	@Property
	private UploadedFile fileEx;

	@Persist("flash")
	@Property
	private List<String> errMsg;

	/*
	 * PROPERTIES
	 */
	@Property
	private String strRow;

	private ImportUtil importUtil;

	@Persist
	private Boolean isAdd;

	@Persist
	private Employee employee;

	private Skills empSkill;

	@Persist
	private List<SkillType> skillTypes;

	@Persist
	private List<Skills> skills;

	void beginRender() {

	}

	/** EXCEL-ээс оруулах **/
	@CommitAfter
	void onSelectedFromImportFromExcel() throws Exception {

		String extension = fileEx.getFileName().substring(fileEx.getFileName().length() - 3,
				fileEx.getFileName().length());

		if (!extension.equals("xls"))
			manager.alert(Duration.SINGLE, Severity.ERROR, messages.get("pleaseInsertXSL"));
		else {

			importExcel();
		}
	}

	public void onActionFromCancelImport() {
		fileEx = null;
	}

	void importExcel() throws IOException {

		InputStream fis = null;

		importUtil = new ImportUtil();

		skillTypes = dao.getEmpSkillTypes();

		int savedSkill = 0;

		try {
			fis = fileEx.getStream();

			HSSFWorkbook wb = new HSSFWorkbook(fis);

			HSSFSheet sheet = wb.getSheetAt(0);

			Iterator rows = sheet.rowIterator();

			while (rows.hasNext()) {

				HSSFRow row = (HSSFRow) rows.next();
				Iterator cells = row.cellIterator();
				Object obj = null;
				isAdd = true;

				if (row.getRowNum() >= 2) {

					empSkill = new Skills();

					this.importExcel(row);

					if (isAdd) {

						for (Skills skill : skills) {
							dao.saveOrUpdateObject(skill);
						}
						savedSkill++;
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				fis.close();
			}

		}

		errMsg = importUtil.getErrMsgs();
		manager.alert(Duration.SINGLE, Severity.INFO, "Хадгалагдсан ур чадварын тоо: " + savedSkill);
	}

	void importExcel(HSSFRow row) {

		if (organization != null && organization.getId() != null) {
			Iterator cells = row.cellIterator();
			Object obj = null;

			skills = new ArrayList<Skills>();
			while (cells.hasNext()) {
				HSSFCell cell = (HSSFCell) cells.next();
				int cellIndex = cell.getCellNum();
				obj = ExcelAPI.getCellValue(cell);
				Skills skill;
				Integer skillPoint;

				System.err.println(obj + " obj cellIndex " + cellIndex);

				if (obj != null) {
					if (cellIndex == 0) {

						try {
							employee = dao.getEmployeeByOrg(organization.getId(),
									Integer.parseInt(obj.toString().substring(0, (obj.toString().contains("."))
											? obj.toString().indexOf(".") : obj.toString().length())));

							if (employee == null) {

								isAdd = false;
								importUtil.regErrMsg("Ажилтан олдсонгүй!", row.getRowNum() + 1, cellIndex + 1);
							}

						} catch (NumberFormatException ex) {
							System.err.println("№ талбар: " + ex);
						} catch (Exception ex) {
							System.err.println("№ талбар: " + ex);
						}

					} else {

						if (employee != null) {
							try {

								int skillIndex = cellIndex - 1;

								skill = dao.getSkillsByEmpSkillType(employee, skillTypes.get(skillIndex));

								if (skill == null) {

									skill = new Skills();
									skill.setSkillType(skillTypes.get(skillIndex));
									skill.setEmployee(employee);
								}

								skillPoint = Integer.parseInt(obj.toString().substring(0, (obj.toString().contains("."))
										? obj.toString().indexOf(".") : obj.toString().length()));

								if (skillPoint >= 1 && skillPoint <= 3) {

									skill.setSkillPoint(skillPoint);
									skills.add(skill);

								} else {
									isAdd = false;
									importUtil.regErrMsg("1-ээс 3-ын хооронд оноо өгнө үү!", row.getRowNum() + 1,
											cellIndex + 1);
								}

							} catch (Exception ex) {
								isAdd = false;
								System.err.println(ex);
							}
						}
					}
				} else {

					if (cellIndex == 0) {
						isAdd = false;
					}
				}
			}
		}

	}

	public boolean tryParseInt(String value) {
		try {
			Long id = Long.parseLong(value);
		} catch (NumberFormatException nfe) {
			return false;
		}

		return true;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	private LanguageLevel getLangLevel(String level) {

		if (level == null)
			return null;

		if (level.equals(messages.get(LanguageLevel.EXCELLENT.name())))
			return LanguageLevel.EXCELLENT;

		if (level.equals(messages.get(LanguageLevel.GOOD.name())))
			return LanguageLevel.GOOD;

		if (level.equals(messages.get(LanguageLevel.MEDIUM.name())))
			return LanguageLevel.MEDIUM;

		if (level.equals(messages.get(LanguageLevel.BAD.name())))
			return LanguageLevel.BAD;

		return null;
	}
}
