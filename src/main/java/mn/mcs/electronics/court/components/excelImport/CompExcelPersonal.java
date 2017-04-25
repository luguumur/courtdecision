package mn.mcs.electronics.court.components.excelImport;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.Ascription;
import mn.mcs.electronics.court.entities.configuration.City;
import mn.mcs.electronics.court.entities.configuration.DistrictSum;
import mn.mcs.electronics.court.entities.configuration.Origin;
import mn.mcs.electronics.court.entities.configuration.RelativeType;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.EmployeeStatus;
import mn.mcs.electronics.court.enums.FamilyStatus;
import mn.mcs.electronics.court.enums.Gender;
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

public class CompExcelPersonal {

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

	@Persist
	private Organization organization;

	@Persist
	@Property
	private UploadedFile fileEx;

	@Persist("flash")
	@Property
	private List<String> errMsg;

	private ImportUtil importUtil;
	private Boolean isAdd;
	private Employee employee;

	@Property
	private String strRow;

	void beginRender() {

	}

	/** EXCEL-ээс оруулах **/
	@CommitAfter
	void onSelectedFromImportFromExcel() throws Exception {
		if (fileEx != null) {
			String extension = fileEx.getFileName().substring(fileEx.getFileName().length() - 3,
					fileEx.getFileName().length());

			if (!extension.equals("xls"))
				manager.alert(Duration.SINGLE, Severity.ERROR, messages.get("pleaseInsertXSL"));
			else {

				importExcel();
			}
		} else {
			manager.alert(Duration.SINGLE, Severity.ERROR, "Файл сонгогдоогүй байна!");
		}
	}

	public void onActionFromCancelImport() {
		fileEx = null;
	}

	void importExcel() throws IOException {

		InputStream fis = null;

		importUtil = new ImportUtil();

		int savedEmp = 0;

		try {
			System.err.println("Файлын нэр:" + fileEx.getFileName());

			fis = fileEx.getStream();

			HSSFWorkbook wb = new HSSFWorkbook(fis);

			HSSFSheet sheet = wb.getSheetAt(0);

			Object obj = null;

			Iterator rows = sheet.rowIterator();

			while (rows.hasNext()) {

				HSSFRow row = (HSSFRow) rows.next();

				Iterator cells = row.cellIterator();

				isAdd = true;

				if (row.getRowNum() >= 1) {
					employee = new Employee();
					this.importExcel(row);

					if (isAdd) {
						if (dao.getEmployeeByRegister(employee.getRegisterNo()) == null
								|| dao.getEmployeeByRegister(employee.getRegisterNo()).isEmpty()) {

							employee.setOrganization(organization);
							employee.setEmployeeStatus(EmployeeStatus.WORKING);
							dao.saveOrUpdateObject(employee);

							savedEmp++;
						} else {
							importUtil.regErrMsg(
									employee.getRegisterNo() + " регистерийн дугаартай ажилтан бүртгэлтэй байна", 0, 0);

						}
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

		if (!errMsg.isEmpty() && errMsg != null) {
			manager.alert(Duration.SINGLE, Severity.ERROR, messages.get("empImportExcelInfo") + " " + errMsg);
		}

		manager.alert(Duration.SINGLE, Severity.INFO, messages.get("saved_emp_num") + savedEmp);
	}

	void importExcel(HSSFRow row) {

		Iterator cells = row.cellIterator();
		Object obj = null;
		while (cells.hasNext()) {
			HSSFCell cell = (HSSFCell) cells.next();
			int cellIndex = cell.getCellNum();
			obj = ExcelAPI.getCellValue(cell);
			if (obj != null) {
				switch (cellIndex) {
				case 0:
					Integer iTmp = null;
					if (obj.getClass().getSimpleName().equals("String")) {
						if (tryParseInt((String) obj))
							iTmp = Integer.valueOf((String) obj);

						employee.setEmpNumber(iTmp);

					} else if (obj.getClass().getSimpleName().equals("Double")) {
						iTmp = importUtil.getInteger(obj);
						employee.setEmpNumber(iTmp);
					}

					if (employee.getEmpNumber() == null) {
						isAdd = false;
						importUtil.regErrMsg("№ талбар буруу байна", row.getRowNum() + 1, cellIndex + 1);
					}

					break;
				case 1:
					/* Ургийн овог */
					if (obj.getClass().getSimpleName().equals("String")) {

						char first = Character.toUpperCase(obj.toString().charAt(0));
						String betterIdea = first + obj.toString().substring(1);

						employee.setFamilyName(betterIdea);

					} else if (obj.getClass().getSimpleName().equals("Double")) {
						isAdd = false;
						importUtil.regErrMsg("Ургийн овог талбарт зөвхөн үг оруулна уу.", row.getRowNum() + 1,
								cellIndex + 1);

					} else if (obj.getClass().getSimpleName().equals("Date")) {
						isAdd = false;
						importUtil.regErrMsg("Ургийн овог талбарт зөвхөн үг оруулна уу.", row.getRowNum() + 1,
								cellIndex + 1);
					}
					break;
				case 2:
					/* Овог */
					if (obj.getClass().getSimpleName().equals("String")) {

						char first = Character.toUpperCase(obj.toString().charAt(0));
						String betterIdea = first + obj.toString().substring(1);

						employee.setLastname(betterIdea);

					} else if (obj.getClass().getSimpleName().equals("Double")) {
						isAdd = false;
						importUtil.regErrMsg("Овог талбарт зөвхөн үг оруулна уу.", row.getRowNum() + 1, cellIndex + 1);

					} else if (obj.getClass().getSimpleName().equals("Date")) {
						isAdd = false;
						importUtil.regErrMsg("Овог талбарт зөвхөн үг оруулна уу.", row.getRowNum() + 1, cellIndex + 1);
					}
					break;

				case 3:
					/* Нэр */
					if (obj.getClass().getSimpleName().equals("String")) {

						char first = Character.toUpperCase(obj.toString().charAt(0));
						String betterIdea = first + obj.toString().substring(1);

						employee.setFirstName(betterIdea);

					} else if (obj.getClass().getSimpleName().equals("Double")) {
						isAdd = false;
						importUtil.regErrMsg("Нэр талбарт зөвхөн үг оруулна уу.", row.getRowNum() + 1, cellIndex + 1);

					} else if (obj.getClass().getSimpleName().equals("Date")) {
						isAdd = false;
						importUtil.regErrMsg("Нэр талбарт зөвхөн үг оруулна уу.", row.getRowNum() + 1, cellIndex + 1);
					}
					break;

				case 4:
					/* Регистерийн дугаар */
					if (obj.getClass().getSimpleName().equals("String")) {
						employee.setRegisterNo(obj.toString().replace(" ", ""));
					} else if (obj.getClass().getSimpleName().equals("Double")) {

						employee.setRegisterNo(String.valueOf(importUtil.getInteger(obj)));
					} else if (obj.getClass().getSimpleName().equals("Date")) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						employee.setRegisterNo(format.format(obj));
					}

					if (employee.getRegisterNo().matches(
							"^[а-яА-Я|Ө|Ү]{2}[0-9]{2}((0[1-9])|(1[0-2])|(2[1-9])|(3[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))[0-9]{2}$") == false) {
						isAdd = false;
						importUtil.regErrMsg(
								"Иргэний регистрийн дугаар буруу форматтай байна - " + employee.getEmpNumber(),
								row.getRowNum() + 1, cellIndex + 1);
					}
					break;

				case 5:
					/* Хүйс */
					if (obj.getClass().getSimpleName().equals("String")) {

						if (String.valueOf(obj).contains(messages.get("MALE"))) {
							employee.setGender(Gender.MALE);
						} else if (String.valueOf(obj).contains(messages.get("FEMALE"))) {
							employee.setGender(Gender.FEMALE);
						} else {
							isAdd = false;
							importUtil.regErrMsg("Хүйс талбар тохирохгүй байна", row.getRowNum() + 1, cellIndex + 1);
						}

					} else {
						isAdd = false;
						importUtil.regErrMsg("Хүйс талбарын төрөл талбар буруу байна - " + employee.getEmpNumber(),
								row.getRowNum() + 1, cellIndex + 1);
					}

					break;

				case 6:
					/* Төрсөн огноо */
					if (obj.getClass().getSimpleName().equals("Date")) {
						employee.setBirthDate((Date) obj);
					} else if (obj.getClass().getSimpleName().equals("String")) {
						Date createdDate = importUtil.getDateFromString((String) obj);

						employee.setBirthDate(createdDate);
					}

					if (employee.getBirthDate() == null) {
						isAdd = false;

						importUtil.regErrMsg("Төрсөн огноо талбар буруу байна - " + employee.getEmpNumber(),
								row.getRowNum() + 1, cellIndex + 1);
					}
					break;

				case 7:
					/* Төрсөн аймаг хот */
					if (obj.getClass().getSimpleName().equals("String")) {
						City aimagNiislel = dao.getCityByName(String.valueOf(obj).replace(" ", ""));

						if (aimagNiislel != null) {
							employee.setBirthCityProvince(aimagNiislel);
						} else {
							isAdd = false;
							importUtil.regErrMsg("Төрсөн аймаг, хот нэр буруу байна", row.getRowNum() + 1,
									cellIndex + 1, String.valueOf(obj), null);
						}

					} else {
						isAdd = false;
						importUtil.regErrMsg("Төрсөн аймаг, хот талбар буруу байна - " + employee.getEmpNumber(),
								row.getRowNum() + 1, cellIndex + 1);
					}

					break;

				case 8:
					/* Төрсөн сум дүүрэг */
					if (obj.getClass().getSimpleName().equals("String")) {
						DistrictSum sumDuureg = dao.getDistrictSumByName(employee.getBirthCityProvince(),
								String.valueOf(obj));
						if (sumDuureg != null) {
							employee.setBirthDistrictSum(sumDuureg);
						} else {
							isAdd = false;
							importUtil.regErrMsg("Төрсөн сум, дүүрэг нэр буруу байна", row.getRowNum() + 1,
									cellIndex + 1, String.valueOf(obj), null);
						}

					} else {
						isAdd = false;
						importUtil.regErrMsg("Төрсөн сум, дүүрэг талбар буруу байна - " + employee.getEmpNumber(),
								row.getRowNum() + 1, cellIndex + 1);
					}

					break;

				case 9:
					/* Төрсөн газар */
					if (obj.getClass().getSimpleName().equals("String")) {
						employee.setBirthPlace((String) obj);

					} else if (obj.getClass().getSimpleName().equals("Double")) {
						isAdd = false;
						importUtil.regErrMsg("Төрсөн газар талбарт зөвхөн үг оруулна уу.", row.getRowNum() + 1,
								cellIndex + 1);

					} else if (obj.getClass().getSimpleName().equals("Date")) {
						isAdd = false;
						importUtil.regErrMsg("Төрсөн газар талбарт зөвхөн үг оруулна уу.", row.getRowNum() + 1,
								cellIndex + 1);
					}
					break;
				case 10:
					/* Үндэс угсаа */
					if (obj.getClass().getSimpleName().equals("String")) {
						Origin origin = dao.getOriginByName(String.valueOf(obj));

						if (origin != null) {
							employee.setOrigin(origin);
						} else {
							isAdd = false;
							importUtil.regErrMsg("Үндэс угсаа буруу байна", row.getRowNum() + 1, cellIndex + 1);
						}

					} else {
						isAdd = false;
						importUtil.regErrMsg("Үндэс угсаа талбар буруу байна - " + employee.getEmpNumber(),
								row.getRowNum() + 1, cellIndex + 1);
					}

					break;

				case 11:
					/* Нийгмийн гарал */
					if (obj.getClass().getSimpleName().equals("String")) {
						Ascription ascription = dao.getAscriptionByName(String.valueOf(obj));

						if (ascription != null) {
							employee.setAscription(ascription);
						} else {
							isAdd = false;
							importUtil.regErrMsg("Нийгмийн гарал буруу байна", row.getRowNum() + 1, cellIndex + 1);
						}

					} else {
						isAdd = false;
						importUtil.regErrMsg("Нийгмийн гарал талбар буруу байна - " + employee.getEmpNumber(),
								row.getRowNum() + 1, cellIndex + 1);
					}

					break;
				case 12:
					/* Оршин суугаа хаяг */
					if (obj.getClass().getSimpleName().equals("String")) {
						employee.setAddress((String) obj);
					} else if (obj.getClass().getSimpleName().equals("Double")) {

						employee.setAddress(String.valueOf(importUtil.getInteger(obj)));
					} else if (obj.getClass().getSimpleName().equals("Date")) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						employee.setAddress(format.format(obj));
					} else {
						isAdd = false;
						importUtil.regErrMsg("Оршин суугаа хаяг талбар буруу байна - " + employee.getEmpNumber(),
								row.getRowNum() + 1, cellIndex + 1);
					}
					break;

				case 13:
					/* Гэрийн утас */
					if (obj.getClass().getSimpleName().equals("String")) {
						employee.setPhoneNo((String) obj);
					} else if (obj.getClass().getSimpleName().equals("Double")) {

						employee.setPhoneNo(String.valueOf(importUtil.getInteger(obj)));
					} else if (obj.getClass().getSimpleName().equals("Date")) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						employee.setPhoneNo(format.format(obj));
					} else {
						isAdd = false;
						importUtil.regErrMsg("Гэрийн утас талбар буруу байна - " + employee.getEmpNumber(),
								row.getRowNum() + 1, cellIndex + 1);
					}
					break;

				case 14:
					/* Гар утас */
					if (obj.getClass().getSimpleName().equals("String")) {
						employee.setMobileNo((String) obj);
					} else if (obj.getClass().getSimpleName().equals("Double")) {

						employee.setMobileNo(String.valueOf(importUtil.getInteger(obj)));
					} else if (obj.getClass().getSimpleName().equals("Date")) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						employee.setMobileNo(format.format(obj));
					} else {
						isAdd = false;
						importUtil.regErrMsg("Гар утас талбар буруу байна - " + employee.getEmpNumber(),
								row.getRowNum() + 1, cellIndex + 1);
					}
					break;

				case 15:
					/* Ажлын утас */
					if (obj.getClass().getSimpleName().equals("String")) {
						employee.setWorkPhoneNo((String) obj);
					} else if (obj.getClass().getSimpleName().equals("Double")) {

						employee.setWorkPhoneNo(String.valueOf(importUtil.getInteger(obj)));
					} else if (obj.getClass().getSimpleName().equals("Date")) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						employee.setWorkPhoneNo(format.format(obj));
					} else {
						isAdd = false;
						importUtil.regErrMsg("Ажлын утас талбар буруу байна - " + employee.getEmpNumber(),
								row.getRowNum() + 1, cellIndex + 1);
					}
					break;

				case 16:
					/* Факс */
					if (obj.getClass().getSimpleName().equals("String")) {
						employee.setWorkFax((String) obj);
					} else if (obj.getClass().getSimpleName().equals("Double")) {

						employee.setWorkFax(String.valueOf(importUtil.getInteger(obj)));
					} else if (obj.getClass().getSimpleName().equals("Date")) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						employee.setWorkFax(format.format(obj));
					} else {
						isAdd = false;
						importUtil.regErrMsg("Факс талбар буруу байна - " + employee.getEmpNumber(),
								row.getRowNum() + 1, cellIndex + 1);
					}
					break;

				case 17:
					/* Е-майл хаяг */
					if (obj.getClass().getSimpleName().equals("String")) {
						employee.seteMail((String) obj);
					} else if (obj.getClass().getSimpleName().equals("Double")) {

						employee.seteMail(String.valueOf(importUtil.getInteger(obj)));
					} else if (obj.getClass().getSimpleName().equals("Date")) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						employee.seteMail(format.format(obj));
					} else {
						isAdd = false;
						importUtil.regErrMsg("Е-майл хаяг талбар буруу байна - " + employee.getEmpNumber(),
								row.getRowNum() + 1, cellIndex + 1);
					}
					break;

				case 18:
					/* Шуудангийн хаяг */
					if (obj.getClass().getSimpleName().equals("String")) {
						employee.setPostAddress((String) obj);
					} else if (obj.getClass().getSimpleName().equals("Double")) {

						employee.setPostAddress(String.valueOf(importUtil.getInteger(obj)));
					} else if (obj.getClass().getSimpleName().equals("Date")) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						employee.setPostAddress(format.format(obj));
					} else {
						isAdd = false;
						importUtil.regErrMsg("Шуудангийн хаяг талбар буруу байна - " + employee.getEmpNumber(),
								row.getRowNum() + 1, cellIndex + 1);
					}
					break;

				case 19:
					/* Холбоо барих хүний нэр */
					if (obj.getClass().getSimpleName().equals("String")) {
						employee.seteCallPersonName((String) obj);

					} else if (obj.getClass().getSimpleName().equals("Double")) {
						isAdd = false;
						importUtil.regErrMsg("Холбоо барих хүний нэр талбарт зөвхөн үг оруулна уу.",
								row.getRowNum() + 1, cellIndex + 1);

					} else if (obj.getClass().getSimpleName().equals("Date")) {
						isAdd = false;
						importUtil.regErrMsg("Холбоо барих хүний нэр талбарт зөвхөн үг оруулна уу.",
								row.getRowNum() + 1, cellIndex + 1);
					}
					break;

				case 20:
					/* Юу нь болох */
					if (obj.getClass().getSimpleName().equals("String")) {
						RelativeType relativeType = dao.getRelativeTypeByName(String.valueOf(obj));

						if (relativeType != null) {
							employee.seteCallPersonRelativeType(relativeType);
						} else {
							isAdd = false;
							importUtil.regErrMsg("Юу нь болох талбар буруу байна", row.getRowNum() + 1, cellIndex + 1);
						}

					} else {
						isAdd = false;
						importUtil.regErrMsg("Юу нь болох талбар буруу байна - " + employee.getEmpNumber(),
								row.getRowNum() + 1, cellIndex + 1);
					}

					break;

				case 21:
					/* Холбоо барих хүний утас */
					if (obj.getClass().getSimpleName().equals("String")) {
						employee.seteCallPersonMobileNo((String) obj);
					} else if (obj.getClass().getSimpleName().equals("Double")) {
						employee.seteCallPersonMobileNo(String.valueOf(importUtil.getInteger(obj)));
					} else if (obj.getClass().getSimpleName().equals("Date")) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						employee.seteCallPersonMobileNo(format.format(obj));
					} else {
						isAdd = false;
						importUtil.regErrMsg("Холбоо барих хүний утас талбар буруу байна - " + employee.getEmpNumber(),
								row.getRowNum() + 1, cellIndex + 1);
					}
					break;
				case 22:
					/* Холбоо барих хүний хаяг */
					if (obj.getClass().getSimpleName().equals("String")) {
						employee.seteCallPersonAddress((String) obj);
					} else if (obj.getClass().getSimpleName().equals("Double")) {

						employee.seteCallPersonAddress(String.valueOf(importUtil.getInteger(obj)));
					} else if (obj.getClass().getSimpleName().equals("Date")) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						employee.seteCallPersonAddress(format.format(obj));
					} else {
						isAdd = false;
						importUtil.regErrMsg("Холбоо барих хүний хаяг талбар буруу байна - " + employee.getEmpNumber(),
								row.getRowNum() + 1, cellIndex + 1);
					}
					break;

				case 23:
					/* Гэр бүлийн байдал */
					if (obj.getClass().getSimpleName().equals("String")) {

						if (String.valueOf(obj).equalsIgnoreCase(messages.get("MARRIED"))) {
							employee.setFamilyStatus(FamilyStatus.MARRIED);
						} else if (String.valueOf(obj).equalsIgnoreCase(messages.get("SINGLE"))) {
							employee.setFamilyStatus(FamilyStatus.SINGLE);
						} else if (String.valueOf(obj).equalsIgnoreCase(messages.get("DIVORCE"))) {
							employee.setFamilyStatus(FamilyStatus.DIVORCE);
						} else if (String.valueOf(obj).equalsIgnoreCase(messages.get("WIDOW"))) {
							employee.setFamilyStatus(FamilyStatus.WIDOW);
						} else {
							isAdd = false;
							importUtil.regErrMsg("Гэр бүлийн байдал талбар тохирохгүй байна", row.getRowNum() + 1,
									cellIndex + 1);
						}

					} else {
						isAdd = false;
						importUtil.regErrMsg("Гэр бүлийн байдал талбар буруу байна - " + employee.getEmpNumber(),
								row.getRowNum() + 1, cellIndex + 1);
					}

					break;

				case 24:
					/* Систем ашиглах эсэх */
					if (obj.getClass().getSimpleName().equals("String")) {

						if (String.valueOf(obj).equalsIgnoreCase(messages.get("YES"))) {
							employee.setSystemUser(true);
						} else if (String.valueOf(obj).equalsIgnoreCase(messages.get("NO"))) {
							employee.setSystemUser(false);
						} else {
							isAdd = false;
							importUtil.regErrMsg("Систем ашиглах эсэх талбар тохирохгүй байна", row.getRowNum() + 1,
									cellIndex + 1);
						}

					} else {
						isAdd = false;
						importUtil.regErrMsg("Систем ашиглах эсэх талбар буруу байна - " + employee.getEmpNumber(),
								row.getRowNum() + 1, cellIndex + 1);
					}

					break;

				}
			} else {
				if (cellIndex == 0 || cellIndex == 1 || cellIndex == 2 || cellIndex == 3 || cellIndex == 4
						|| cellIndex == 5 || cellIndex == 6 || cellIndex == 7 || cellIndex == 8 || cellIndex == 14
						|| cellIndex == 19) {
					importUtil.regErrMsg("Заавал оруулах талбар оруулаагүй байна", row.getRowNum() + 1, cellIndex + 1);

					isAdd = false;
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

}
