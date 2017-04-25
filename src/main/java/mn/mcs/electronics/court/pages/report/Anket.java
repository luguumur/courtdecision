package mn.mcs.electronics.court.pages.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.SkillGroup;
import mn.mcs.electronics.court.entities.configuration.SkillType;
import mn.mcs.electronics.court.entities.employee.Addition;
import mn.mcs.electronics.court.entities.employee.Computer;
import mn.mcs.electronics.court.entities.employee.ComputerOther;
import mn.mcs.electronics.court.entities.employee.DegreeGradeRank;
import mn.mcs.electronics.court.entities.employee.Degrees;
import mn.mcs.electronics.court.entities.employee.Demerit;
import mn.mcs.electronics.court.entities.employee.Educations;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.EmployeeMilitary;
import mn.mcs.electronics.court.entities.employee.Experience;
import mn.mcs.electronics.court.entities.employee.Honour;
import mn.mcs.electronics.court.entities.employee.Language;
import mn.mcs.electronics.court.entities.employee.OfficeEquipment;
import mn.mcs.electronics.court.entities.employee.Relatives;
import mn.mcs.electronics.court.entities.employee.ResultContract;
import mn.mcs.electronics.court.entities.employee.Sahilga;
import mn.mcs.electronics.court.entities.employee.Skills;
import mn.mcs.electronics.court.entities.employee.Training;
import mn.mcs.electronics.court.entities.employee.TravelAbroad;
import mn.mcs.electronics.court.enums.DoctorType;
import mn.mcs.electronics.court.enums.RelativeFamily;
import mn.mcs.electronics.court.pages.employee.PageEmployeeDetail;
import mn.mcs.electronics.court.util.Constants;
import mn.mcs.electronics.court.util.PDFStreamResponse;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Context;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class Anket {

	@Inject
	private CoreDAO dao;

	@Inject
	private Messages messages;

	@Persist
	private List<Skills> listSkills;

	@SessionState
	private LoginState loginState;

	@Persist
	private List<SkillGroup> listSkillGroup;

	@Persist
	private List<SkillType> listSkillType;

	@Persist
	private Educations educations;

	@InjectPage
	private PageEmployeeDetail pageEmployee;

	@Persist
	private List<Relatives> listRelative;

	@Persist
	private List<Relatives> listRelativeType;

	@Persist
	private List<Educations> listEducations;

	@Persist
	private List<Degrees> listDegrees;

	@Persist
	private List<Training> listTraining;

	@Persist
	private List<Experience> listExperience;

	@Persist
	private List<Employee> listEmployee;

	@Persist
	private List<Language> listLanguage;

	@Persist
	private List<DegreeGradeRank> listDegreeGradeRank;

	@Persist
	private List<EmployeeMilitary> listDegreeGrade;

	@Persist
	private List<Computer> listComputer;

	@Persist
	private List<ComputerOther> listComputerOther;

	@Persist
	private List<Addition> listAddition;

	@Persist
	private List<Honour> listHonour;

	@Persist
	private List<TravelAbroad> listTravelAbroad;

	@Persist
	private List<ResultContract> listResultContract;

	@Persist
	private List<Demerit> listDemerit;

	@Persist
	private List<Sahilga> listSahilga;

	@Persist
	private Document document;

	@Persist
	private Employee emp;

	@Persist
	private Degrees degrees;

	@Persist
	private List<OfficeEquipment> listOfficeEquipment;

	@Inject
	private Context _context;

	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);

	private static String fontType = "/fonts/arial.ttf";
	private static String fontTypeBold = "/fonts/arialbd.ttf";
	/* private static String fontTypeItalic = "/fonts/ariali.ttf"; */
	public static final String IMAGE_PATH_EMP = "/images/profile/";

	void beginRender() {

		if (listRelative == null)
			listRelative = new ArrayList<Relatives>();

		if (listRelativeType == null)
			listRelativeType = new ArrayList<Relatives>();

		if (listEducations == null)
			listEducations = new ArrayList<Educations>();

		if (listDegreeGradeRank == null)
			listDegreeGradeRank = new ArrayList<DegreeGradeRank>();

		if (listDegrees == null)
			listDegrees = new ArrayList<Degrees>();

		if (listTraining == null)
			listTraining = new ArrayList<Training>();

		if (listExperience == null)
			listExperience = new ArrayList<Experience>();

		if (listEmployee == null)
			listEmployee = new ArrayList<Employee>();

		if (listDegreeGrade == null)
			listDegreeGrade = new ArrayList<EmployeeMilitary>();

		if (listComputer == null)
			listComputer = new ArrayList<Computer>();

		if (listComputerOther == null)
			listComputerOther = new ArrayList<ComputerOther>();

		if (listLanguage == null)
			listLanguage = new ArrayList<Language>();

		if (listComputer == null)
			listComputer = new ArrayList<Computer>();

		if (listSkills == null)
			listSkills = new ArrayList<Skills>();

		if (listHonour == null)
			listHonour = new ArrayList<Honour>();

		if (listAddition == null)
			listAddition = new ArrayList<Addition>();

		if (listTravelAbroad == null)
			listTravelAbroad = new ArrayList<TravelAbroad>();

		if (listResultContract == null)
			listResultContract = new ArrayList<ResultContract>();

		if (listDemerit == null)
			listDemerit = new ArrayList<Demerit>();

		if (listSahilga == null)
			listSahilga = new ArrayList<Sahilga>();

		if (listOfficeEquipment == null)
			listOfficeEquipment = new ArrayList<OfficeEquipment>();

		if (emp == null) {
			emp = new Employee();
		}

		listRelative = dao.getRelatives(emp.getId(), RelativeFamily.ISFAMILY);
		listRelativeType = dao.getRelatives(emp.getId(),
				RelativeFamily.ISRELATIVE);
		listDegrees = dao.getListDegrees(emp);
		listTraining = dao.getTraining(emp);
		listExperience = dao.getExperience(emp);
		listEmployee = dao.getListEmployee(pageEmployee.getOrganization());
		listDegreeGrade = dao.getListEmployeeMilitary(emp);
		listDegreeGradeRank = dao.getListDegreeGradeRank(pageEmployee
				.getEmployee());
		listLanguage = dao.getLanguage(emp);
		listComputer = dao.getComputer(emp);
		listComputerOther = dao.getListComputerOther(emp);
		listSkills = dao.getListSkills(emp);
		listSkillGroup = dao.getListSkillGroup();
		listEducations = dao.getListEducation(emp);
		listAddition = dao.getAddition(emp);
		listHonour = dao.getListHonour(emp);
		listTravelAbroad = dao.getListTravelAbroad(emp);
		listResultContract = dao.getResultContract(emp);
		listDemerit = dao.getListDemerit(emp);
		listOfficeEquipment = dao.getListOfficeEquipment(emp);
		listSahilga = dao.getListSahilga(emp);
	}

	public StreamResponse onSubmit() throws IOException, DocumentException {
		beginRender();
		InputStream is = createPdf("aa");
		return new PDFStreamResponse(is, "anket");
	}

	public InputStream createPdf(String filename) throws IOException,
			DocumentException {

		document = new Document();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		PdfWriter writer = PdfWriter.getInstance(document, baos);

		if (!document.isOpen())
			document.open();

		BaseFont arno;
		arno = BaseFont.createFont(fontType, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);

		BaseFont Header;
		Header = BaseFont.createFont(fontTypeBold, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);

		/*
		 * BaseFont italicfont; italicfont = BaseFont.createFont(fontTypeItalic,
		 * BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		 */

		Paragraph p = new Paragraph(
				"\"Төрийн албан хаагчийн хувийн хэрэг хөтлөх журам\"-ын 1 дүгээр хавсралт",
				new Font(arno, 8));
		p.setAlignment(Element.ALIGN_RIGHT);
		p.setSpacingAfter(10);
		document.add(p);

		p = new Paragraph("ТӨРИЙН АЛБАН ХААГЧИЙН АНКЕТ", new Font(Header, 12));
		p.setAlignment(Element.ALIGN_CENTER);
		document.add(p);

		PdfContentByte canvas = writer.getDirectContentUnder();
		writer.setCompressionLevel(0);

		canvas.saveState();
		canvas.beginText();
		canvas.moveText(42, 768);
		canvas.setFontAndSize(BaseFont.createFont(fontType,
				BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 8);
		canvas.showText(" Маягтыг өөрийн гараар хар");
		canvas.endText();
		canvas.restoreState();

		canvas.saveState();
		canvas.beginText();
		canvas.moveText(40, 758);
		canvas.setFontAndSize(BaseFont.createFont(fontType,
				BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 8);
		canvas.showText(" буюу хар хөх өнгийн бэхээр");
		canvas.endText();
		canvas.restoreState();

		canvas.saveState();
		canvas.beginText();
		canvas.moveText(80, 748);
		canvas.setFontAndSize(BaseFont.createFont(fontType,
				BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 8);
		canvas.showText("бөглөнө");
		canvas.endText();
		canvas.restoreState();

		canvas.saveState(); // q
		canvas.beginText(); // BT
		canvas.moveText(43, 779); // 36 788 Td
		canvas.setFontAndSize(BaseFont.createFont(), 9); // /F1 12 Tf
		canvas.showText("_____________________"); // (Hello World)Tj
		canvas.endText(); // ET
		canvas.restoreState();

		canvas.saveState();
		canvas.beginText();
		canvas.moveText(43, 746);
		canvas.setFontAndSize(BaseFont.createFont(), 9);
		canvas.showText("_____________________");
		canvas.endText();
		canvas.restoreState();

		canvas.saveState(); // q
		canvas.beginText(); // BT
		canvas.moveText(454, 779); // 36 788 Td
		canvas.setFontAndSize(BaseFont.createFont(), 9); // /F1 12 Tf
		canvas.showText("_____________________"); // (Hello World)Tj
		canvas.endText(); // ET
		canvas.restoreState();

		canvas.saveState();
		canvas.beginText();
		canvas.moveText(454, 746);
		canvas.setFontAndSize(BaseFont.createFont(), 9);
		canvas.showText("_____________________");
		canvas.endText();
		canvas.restoreState();

		canvas.saveState();
		canvas.beginText();
		canvas.moveText(480, 768);
		canvas.setFontAndSize(BaseFont.createFont(fontType,
				BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 8);
		canvas.showText("Биеийн байцаалт");
		canvas.endText();
		canvas.restoreState();

		canvas.saveState();
		canvas.beginText();
		canvas.moveText(488, 758);
		canvas.setFontAndSize(BaseFont.createFont(fontType,
				BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 8);
		canvas.showText("бичих санамж");
		canvas.endText();
		canvas.restoreState();

		canvas.saveState();
		canvas.beginText();
		canvas.moveText(492, 748);
		canvas.setFontAndSize(BaseFont.createFont(fontType,
				BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 8);
		canvas.showText("ашиглаарай");
		canvas.endText();
		canvas.restoreState();

		/*
		 * canvas.saveState(); canvas.beginText(); canvas.moveText(337, 729);
		 * canvas.setFontAndSize(BaseFont.createFont(fontType,
		 * BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 8);
		 * canvas.showText("Иргэний үнэмлэхний дугаар"); canvas.endText();
		 * canvas.restoreState();
		 */

		canvas.saveState();
		canvas.beginText();
		canvas.moveText(337, 707);
		canvas.setFontAndSize(BaseFont.createFont(fontType,
				BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 8);
		canvas.showText("Эрүүл мэндийн даатгалын гэрчилгээний дугаар ");
		canvas.endText();
		canvas.restoreState();

		p = new Paragraph("   Регистрийн дугаар" + "  "
				+ ((emp.getRegisterNo() != null) ? emp.getRegisterNo() : ""),
				new Font(arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		p.setSpacingBefore(25);
		document.add(p);

		/*
		 * canvas.saveState(); canvas.beginText(); canvas.moveText(447, 729);
		 * canvas.setFontAndSize(BaseFont.createFont(fontType,
		 * BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 8);
		 * canvas.showText(((emp.getIdCardNumber() != null) ? emp
		 * .getIdCardNumber() : "")); canvas.endText(); canvas.restoreState();
		 */

		p = new Paragraph(
				"   Нийгмийн даатгалын дэвтрийн дугаар "
						+ "  "
						+ ((emp.getSocialInsuranceNumber() != null) ? emp.getSocialInsuranceNumber()
								: ""), new Font(arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		p.setSpacingBefore(10);
		document.add(p);

		canvas.saveState();
		canvas.beginText();
		canvas.moveText(520, 707);
		canvas.setFontAndSize(BaseFont.createFont(fontType,
				BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 8);
		canvas.showText(((emp.getHealthInsuranceNumber() != null) ? emp
				.getHealthInsuranceNumber() : ""));
		canvas.endText();
		canvas.restoreState();

		p = new Paragraph(
				"______________________________________________________________________________",
				new Font(arno, 12, Font.BOLD));
		p.setAlignment(Element.ALIGN_CENTER);
		document.add(p);

		p = new Paragraph("1. ХУВЬ ХҮНИЙ ТАЛААРХ МЭДЭЭЛЭЛ",
				new Font(Header, 10));
		p.setAlignment(Element.ALIGN_CENTER);
		p.setSpacingAfter(10);
		p.setSpacingBefore(10);
		document.add(p);

		if (emp != null) {
			p = new Paragraph("1.1 Эцэг/эх/-ийн нэр:" + "  "
					+ ((emp.getLastname() != null) ? emp.getLastname() : ""),
					new Font(arno, 10));
			p.setAlignment(Element.ALIGN_LEFT);
			document.add(p);
		}

		canvas.saveState();
		canvas.beginText();
		canvas.moveText(240, 639);
		canvas.setFontAndSize(BaseFont.createFont(fontType,
				BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 10);
		canvas.showText("Нэр:");
		canvas.endText();
		canvas.restoreState();

		if (emp != null) {
			canvas.saveState();
			canvas.beginText();
			canvas.moveText(485, 712);
			canvas.setFontAndSize(BaseFont.createFont(fontType,
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 10);

			if (emp.getPicturePath() != null) {

				Image img = null;

				File copied = new File(_context.getRealFile(
						IMAGE_PATH_EMP + emp.getPicturePath()).getPath());

				if (copied.exists()) {
					img = Image.getInstance(copied.getPath());
					img.setAbsolutePosition(472, 552);
					img.scaleAbsolute(85, 113);
					canvas.addImage(img);
				}
			}
			canvas.endText();
			canvas.restoreState();
		}

		if (emp != null) {
			canvas.saveState();
			canvas.beginText();
			canvas.moveText(265, 639);
			canvas.setFontAndSize(BaseFont.createFont(fontType,
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 10);
			canvas.showText((emp.getFirstName() != null) ? emp.getFirstName()
					.toString() : "");
			canvas.endText();
			canvas.restoreState();
		}

		if (emp != null) {
			p = new Paragraph(
					"1.2 Хүйс:"
							+ "  "
							+ ((emp.getGender() != null && emp.getGender()
									.name() != null) ? messages.get(emp
									.getGender().name()) : ""), new Font(arno,
							10));
			p.setAlignment(Element.ALIGN_LEFT);
			document.add(p);
		}

		if (emp != null) {
			Calendar cal = Calendar.getInstance();
			if (emp.getBirthDate() != null) {
				cal.setTime(emp.getBirthDate());
			}
			canvas.saveState();
			canvas.beginText();
			canvas.moveText(240, 624);
			canvas.setFontAndSize(BaseFont.createFont(fontType,
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 10);
			canvas.showText("1.3 Төрсөн	" + "  " + (cal.get(Calendar.YEAR))
					+ "  " + "	он" + "  " + (cal.get(Calendar.MONTH) + 1)
					+ "  " + "	сар" + "  " + (cal.get(Calendar.DATE)) + "  "
					+ "	өдөр");
			canvas.endText();
			canvas.restoreState();
		}

		if (emp != null) {
			p = new Paragraph("1.4 Төрсөн аймаг, хот:"
					/*
					 * + "  " + ((emp.getBirthPlace() != null) ? emp
					 * .getBirthPlace() : "") + "  "
					 */
					+ ((emp.getBirthCityProvince() != null) ? emp
							.getBirthCityProvince().getCityName().toString()
							: ""), new Font(arno, 10));
			p.setAlignment(Element.ALIGN_LEFT);
			document.add(p);
		}

		if (emp != null) {
			canvas.saveState();
			canvas.beginText();
			canvas.moveText(240, 609);
			canvas.setFontAndSize(BaseFont.createFont(fontType,
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 10);
			canvas.showText("Сум, дүүрэг:	"
					+ "  "
					+ ((emp.getBirthDistrictSum() != null) ? emp
							.getBirthDistrictSum().getDistrictSumName()
							.toString() : ""));
			canvas.endText();
			canvas.restoreState();
		}

		if (emp != null) {
			p = new Paragraph("      Төрсөн газар: "
					+ (((emp.getBirthPlace() != null) ? emp.getBirthPlace()
							.toString() : "")), new Font(arno, 10));
			p.setAlignment(Element.ALIGN_LEFT);
			document.add(p);
		}

		if (emp != null) {
			canvas.saveState();
			canvas.beginText();
			canvas.moveText(240, 594);
			canvas.setFontAndSize(BaseFont.createFont(fontType,
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 10);
			canvas.showText("Овог: "
					+ "  "
					+ (((emp.getFamilyName() != null) ? emp.getFamilyName()
							: "")));
			canvas.endText();
			canvas.restoreState();
		}

		if (emp != null) {
			p = new Paragraph("1.5 Үндэс, угсаа: "
					+ "  "
					+ ((emp.getOrigin() != null) ? emp.getOrigin().getName()
							: ""), new Font(arno, 10));
			p.setAlignment(Element.ALIGN_LEFT);
			document.add(p);
		}

		if (emp != null) {
			canvas.saveState();
			canvas.beginText();
			canvas.moveText(240, 579);
			canvas.setFontAndSize(BaseFont.createFont(fontType,
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 10);
			canvas.showText("1.6 Нийгмийн гарал:"
					+ "  "
					+ ((emp.getAscription() != null) ? emp.getAscription()
							.getName() : ""));
			canvas.endText();
			canvas.restoreState();
		}

		p = new Paragraph(
				"1.7 Гэр бүлийн байдал (Зөвхөн гэр бүлийн бүртгэлд байгаа хүмүүсийг бичнэ.): ",
				new Font(arno, 10));
		p.setAlignment(Element.ALIGN_LEFT);
		p.setSpacingAfter(15);
		p.setSpacingBefore(5);
		document.add(p);

		p = new Paragraph();
		p.setFont(new Font(arno, 8));
		p.setAlignment(Element.ALIGN_CENTER);
		p.add(getRelativeList());
		document.add(p);

		p = new Paragraph(
				"1.8 Садан төрлийн байдал (Таны эцэг, эх, төрсөн ах, эгч, дүү, өрх тусгаарласан хүүхэд болон таны эхнэр /нөхөр/-ийн  эцэг, эхийг оруулна.): ",
				new Font(arno, 10));
		p.setAlignment(Element.ALIGN_LEFT);
		p.setSpacingAfter(15);
		p.setSpacingBefore(5);
		document.add(p);

		p = new Paragraph();
		p.setFont(new Font(arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		p.add(getRelativeType());
		document.add(p);

		if (emp != null) {
			p = new Paragraph("1.9 Оршин суугаа хаяг:"
					+ "   "
					+ ((emp.getAddCity() != null) ? emp.getAddCity()
							.getCityName() : "")
					+ "   "
					+ "аймаг, хот"
					+ "   "
					+ ((emp.getAddDistrictSum() != null) ? emp
							.getAddDistrictSum().getDistrictSumName() : "")
					+ "   " + "сум, дүүрэг", new Font(arno, 10));
			document.add(p);
		}

		if (emp != null) {
			p = new Paragraph("        " + "гэрийн хаяг:" + "   "
					+ ((emp.getAddress() != null) ? emp.getAddress() : ""),
					new Font(arno, 10));
			document.add(p);
		}

		if (emp != null) {
			p = new Paragraph("        "
					+ "Утас, үүрэн утас:"
					+ "   "
					+ ((emp.getMobileNo() != null) ? emp.getMobileNo()
							.toString() : "")
					+ "    "
					+ ((emp.getPhoneNo() != null) ? emp.getPhoneNo().toString()
							: "")
					+ "   "
					+ "Факс:"
					+ "    "
					+ ((emp.getWorkFax() != null) ? emp.getWorkFax().toString()
							: "")
					+ "   "
					+ "И-мэйл хаяг:"
					+ "    "
					+ ((emp.geteMail() != null) ? emp.geteMail().toString()
							: ""), new Font(arno, 10));
			document.add(p);
		}

		if (emp != null) {
			p = new Paragraph(
					"1.10 Шуудангийн хаяг:"
							+ "   "
							+ ((emp.getPostAddress() != null) ? emp
									.getPostAddress().toString() : "")
							+ "   Индекс:   "
							+ (((emp.getPostIndex()) != null) ? emp.getPostIndex()
									: ""), new Font(arno, 10));
			document.add(p);
		}

		if (emp != null) {
			p = new Paragraph(
					"1.11 Онцгой шаардлага гарвал харилцах хүний нэр:"
							+ "   "
							+ ((emp.geteCallPersonName() != null) ? emp.geteCallPersonName()
									: "")
							+ "     "
							+ " ,түүний утас:"
							+ "   "
							+ ((emp.geteCallPersonMobileNo() != null) ? emp.geteCallPersonMobileNo()
									: "")
							+ "    "
							+ ((emp.geteCallPersonPhoneNo() != null) ? emp.geteCallPersonPhoneNo()
									: ""), new Font(arno, 10));
			document.add(p);
		}
		p = new Paragraph("2. БОЛОВСРОЛЫН ТАЛААРХ МЭДЭЭЛЭЛ", new Font(Header,
				10));
		p.setAlignment(Element.ALIGN_CENTER);
		p.setSpacingBefore(15);
		document.add(p);

		p = new Paragraph(
				"2.1 Боловсрол /ерөнхий, тусгай дунд, дээд боловсрол, дипломын, бакалаврын болон магистрийн зэргийг оролцуулан/ ",
				new Font(arno, 10));
		p.setAlignment(Element.ALIGN_LEFT);
		p.setSpacingBefore(10);
		p.setSpacingAfter(5);
		document.add(p);

		p = new Paragraph();
		p.setFont(new Font(arno, 12));
		p.setAlignment(Element.ALIGN_LEFT);
		p.add(getListEducation());
		document.add(p);

		p = new Paragraph(
				"2.2 Боловсролын докторын болон шинжлэх ухааны докторын зэрэг",
				new Font(arno, 10));
		p.setAlignment(Element.ALIGN_LEFT);
		p.setSpacingBefore(10);
		p.setSpacingAfter(5);
		document.add(p);

		p = new Paragraph();
		p.setFont(new Font(arno, 12));
		p.setAlignment(Element.ALIGN_LEFT);
		p.add(getListDegrees());
		document.add(p);

		if (degrees != null) {
			if (degrees.getDoctorType() != null
					&& degrees.getDoctorType().equals(
							DoctorType.EDUCATIONDOCTOR)) {
				p = new Paragraph(
						"Боловсролын докторын зэрэг хамгаалсан сэдэв: "
								+ ((degrees.getSubject() != null) ? degrees.getSubject()
										: ""), new Font(arno, 10));
				p.setAlignment(Element.ALIGN_LEFT);
				p.setSpacingAfter(10);
				document.add(p);

				p = new Paragraph(
						"Шинжлэх ухааны докторын зэрэг хамгаалсан сэдэв: ",
						new Font(arno, 10));
				p.setAlignment(Element.ALIGN_LEFT);
				p.setSpacingAfter(15);
				document.add(p);

			} else if (degrees.getDoctorType() != null
					&& degrees.getDoctorType().equals(DoctorType.SCIENCEDOCTOR)) {

				p = new Paragraph(
						"Боловсролын докторын зэрэг хамгаалсан сэдэв: ",
						new Font(arno, 10));
				p.setAlignment(Element.ALIGN_LEFT);
				p.setSpacingAfter(10);
				document.add(p);

				p = new Paragraph(
						"Шинжлэх ухааны докторын зэрэг хамгаалсан сэдэв: "
								+ ((degrees.getSubject() != null) ? degrees.getSubject()
										: ""), new Font(arno, 10));
				p.setAlignment(Element.ALIGN_LEFT);
				p.setSpacingAfter(15);
				document.add(p);

			} else if (degrees.getDoctorType() == null) {
				p = new Paragraph(
						"Боловсролын докторын зэрэг хамгаалсан сэдэв: ",
						new Font(arno, 10));
				p.setAlignment(Element.ALIGN_LEFT);
				p.setSpacingAfter(10);
				document.add(p);
				p = new Paragraph(
						"Шинжлэх ухааны докторын зэрэг хамгаалсан сэдэв: ",
						new Font(arno, 10));
				p.setAlignment(Element.ALIGN_LEFT);
				p.setSpacingAfter(15);
				document.add(p);
			}
		}

		p = new Paragraph("3. МЭРГЭШЛИЙН БЭЛТГЭЛИЙН ТАЛААРХ МЭДЭЭЛЭЛ",
				new Font(Header, 10));
		p.setAlignment(Element.ALIGN_CENTER);
		p.setSpacingBefore(15);
		document.add(p);

		p = new Paragraph(
				"3.1 Мэргэшлийн бэлтгэл /Мэргэжлийн болон бусад чиглэлээр нарийн мэргэшүүлэх сургалтад хамрагдсан байдлыг бичнэ./",
				new Font(arno, 10));
		p.setAlignment(Element.ALIGN_LEFT);
		p.setSpacingBefore(10);
		p.setSpacingAfter(5);
		document.add(p);

		p = new Paragraph();
		p.setFont(new Font(arno, 12));
		p.setAlignment(Element.ALIGN_LEFT);
		p.add(getTraining());
		document.add(p);

		p = new Paragraph("3.2 Албан тушаалын зэрэг дэв, цол", new Font(arno,
				10));
		p.setAlignment(Element.ALIGN_LEFT);
		p.setSpacingBefore(10);
		p.setSpacingAfter(5);
		document.add(p);

		p = new Paragraph();
		p.setFont(new Font(arno, 12));
		p.setAlignment(Element.ALIGN_LEFT);
		p.add(getListDegreeGrade());
		document.add(p);

		p = new Paragraph(
				"3.3 Эрдмийн цол./дэд профессор, профессор, академийн гишүүнийг оролцуулан/",
				new Font(arno, 10));
		p.setAlignment(Element.ALIGN_LEFT);
		p.setSpacingBefore(10);
		p.setSpacingAfter(5);
		document.add(p);

		p = new Paragraph();
		p.setFont(new Font(arno, 12));
		p.setAlignment(Element.ALIGN_LEFT);
		p.add(getListDegreeGradeRank());
		document.add(p);

		p = new Paragraph("4. УР ЧАДВАРЫН ТАЛААРХ МЭДЭЭЛЭЛ", new Font(Header,
				10));
		p.setAlignment(Element.ALIGN_CENTER);
		p.setSpacingBefore(15);
		document.add(p);

		p = new Paragraph(
				"4.1 Ур чадвар (та өөрийн ур чадварын түвшинг 1-3 баллаар үнэлнэ үү /1-тааруу, 2-дунд, 3-сайн/)",
				new Font(arno, 10));
		p.setAlignment(Element.ALIGN_LEFT);
		p.setSpacingBefore(10);
		p.setSpacingAfter(5);
		document.add(p);

		p = new Paragraph();
		p.setFont(new Font(arno, 12));
		p.setAlignment(Element.ALIGN_LEFT);
		p.add(getListSkills());
		document.add(p);

		p = new Paragraph();
		p.setFont(new Font(arno, 12));
		p.setAlignment(Element.ALIGN_LEFT);
		p.add(getListOtherSkill());
		document.add(p);

		p = new Paragraph("4.2 Гадаад хэлний мэдлэг", new Font(arno, 10));
		p.setAlignment(Element.ALIGN_LEFT);
		p.setSpacingBefore(10);
		p.setSpacingAfter(5);
		document.add(p);

		p = new Paragraph();
		p.setFont(new Font(arno, 12));
		p.setAlignment(Element.ALIGN_LEFT);
		p.add(getLanguage());
		document.add(p);

		p = new Paragraph(
				"4.3 Компьютерийн болон оффисийн тоног төхөөрөмж, технологи эзэмшсэн байдал",
				new Font(arno, 10));
		p.setAlignment(Element.ALIGN_LEFT);
		p.setSpacingBefore(10);
		p.setSpacingAfter(5);
		document.add(p);

		p = new Paragraph();
		p.setFont(new Font(arno, 12));
		p.setAlignment(Element.ALIGN_LEFT);
		p.add(getComputer());
		p.add(getComputerOther());
		document.add(p);

		p = new Paragraph();
		p.setFont(new Font(arno, 12));
		p.setAlignment(Element.ALIGN_LEFT);
		p.add(getOfficeEquipment());
		document.add(p);

		p = new Paragraph("5. ТУРШЛАГЫН ТАЛААРХ МЭДЭЭЛЭЛ", new Font(Header, 10));
		p.setAlignment(Element.ALIGN_CENTER);
		p.setSpacingBefore(15);
		document.add(p);

		p = new Paragraph("5.1 Хөдөлмөр эрхлэлтийн байдал", new Font(arno, 10));
		p.setAlignment(Element.ALIGN_LEFT);
		p.setSpacingBefore(10);
		p.setSpacingAfter(5);
		document.add(p);

		p = new Paragraph();
		p.setFont(new Font(arno, 12));
		p.setAlignment(Element.ALIGN_LEFT);
		p.add(getExperience());
		p.setSpacingAfter(20);
		document.add(p);

		p = new Paragraph(
				"Дээрх мэдээлэл үнэн зөв болохыг баталж /гарын үсэг/  ................................../"
						+ ((emp != null) ? emp.getFullNameFirstChar()
								.toString() : "") + "/", new Font(arno, 10));
		p.setAlignment(Element.ALIGN_LEFT);
		p.setKeepTogether(true);
		p.setSpacingAfter(15);
		document.add(p);

		p = new Paragraph("20.. он .. сар .. өдөр", new Font(arno, 10));

		p.setAlignment(Element.ALIGN_CENTER);
		p.setSpacingAfter(15);
		document.add(p);

		document.newPage();

		p = new Paragraph(
				"\"Төрийн албан хаагчийн хувийн хэрэг хөтлөх журам\"-ын 2 дугаар хавсралт",
				new Font(arno, 8));
		p.setAlignment(Element.ALIGN_RIGHT);
		p.setSpacingAfter(5);
		document.add(p);

		p = new Paragraph("Маягт 2", new Font(arno, 10));
		p.setAlignment(Element.ALIGN_RIGHT);
		p.setSpacingAfter(5);
		document.add(p);

		p = new Paragraph(
				"Төрийн албан хаагчийн анкетанд ороогүй зарим мэдээллийг энэхүү",
				new Font(arno, 10));
		p.setIndentationLeft(60);
		p.setSpacingAfter(5);
		document.add(p);

		p = new Paragraph("нэмэгдэл хуудсанд бичнэ.", new Font(arno, 10));
		p.setIndentationLeft(60);
		p.setSpacingAfter(5);
		document.add(p);

		p = new Paragraph("НЭМЭГДЭЛ ХУУДАС", new Font(Header, 10));
		p.setAlignment(Element.ALIGN_CENTER);
		p.setSpacingAfter(10);
		p.setSpacingBefore(10);
		document.add(p);

		p = new Paragraph("Регистрийн дугаар: "
				+ ((emp.getRegisterNo() != null) ? emp.getRegisterNo() : ""),
				new Font(arno, 10));
		p.setIndentationLeft(60);
		p.setSpacingAfter(5);
		document.add(p);

		p = new Paragraph("Эцэг(эх)-ийн нэр: "
				+ ((emp.getLastname() != null) ? emp.getLastname() : ""),
				new Font(arno, 10));
		p.setIndentationLeft(60);
		p.setSpacingAfter(5);
		document.add(p);

		p = new Paragraph("Өөрийн нэр: "
				+ ((emp.getFirstName() != null) ? emp.getFirstName() : ""),
				new Font(arno, 10));
		p.setIndentationLeft(60);
		p.setSpacingAfter(15);
		document.add(p);

		if (emp != null) {
			canvas.saveState();
			canvas.beginText();
			canvas.moveText(485, 692);
			canvas.setFontAndSize(BaseFont.createFont(fontType,
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 10);

			if (emp.getPicturePath() != null) {

				Image img = null;

				File copied = new File(_context.getRealFile(
						IMAGE_PATH_EMP + emp.getPicturePath()).getPath());

				if (copied.exists()) {
					{
						img = Image.getInstance(copied.getPath());
						img.setAbsolutePosition(460, 625);
						img.scaleAbsolute(85, 113);
						canvas.addImage(img);
					}
				}
			}
			canvas.endText();
			canvas.restoreState();
		}

		p = new Paragraph(
				"1. Төрийн албан хаагчийн шагнал урамшлын талаарх мэдээлэл "
						+ "(Энд Төрийн дээд шагнал болон Засгийн газрын шагналыг бичнэ)",
				new Font(Header, 10));
		p.setAlignment(Element.ALIGN_LEFT);
		p.setSpacingAfter(15);
		document.add(p);

		p = new Paragraph();
		p.setFont(new Font(arno, 10));
		p.setAlignment(Element.ALIGN_LEFT);
		p.add(getListAddition());
		p.setSpacingAfter(20);
		document.add(p);

		p = new Paragraph("2. Сахилгын шийтгэлийн мэдээлэл :", new Font(Header,
				10));
		p.setAlignment(Element.ALIGN_LEFT);
		p.setSpacingAfter(15);
		document.add(p);

		p = new Paragraph();
		p.setFont(new Font(arno, 10));
		p.setAlignment(Element.ALIGN_LEFT);
		// p.add(getListDemerit());
		p.add(getListSahilga());
		p.setSpacingAfter(20);
		document.add(p);

		p = new Paragraph("3. Сүүлийн 5 жил гадаадад явсан байдал", new Font(
				Header, 10));
		p.setAlignment(Element.ALIGN_LEFT);
		p.setSpacingAfter(15);
		document.add(p);

		p = new Paragraph();
		p.setFont(new Font(arno, 10));
		p.setAlignment(Element.ALIGN_LEFT);
		p.add(getListTravelAbroad());
		p.setSpacingAfter(20);
		document.add(p);

		p = new Paragraph(
				"4. Төрийн жинхэнэ албан хаагчийн үйл ажиллагаа, мэргэшлийн төвшний үнэлгээ, "
						+ "төрийн үйлчилгээний албан хаагчийн үр дүнгийн гэрээний биелэлт "
						+ "буюу ажлын үр дүнгийн үнэлгээ (Энд "
						+ "зөвхөн жилийн эцсийн үнэлгээг тэмдэглэнэ)",
				new Font(arno, 10));
		p.setAlignment(Element.ALIGN_LEFT);
		p.setSpacingAfter(15);
		document.add(p);

		p = new Paragraph();
		p.setFont(new Font(arno, 10));
		p.setAlignment(Element.ALIGN_LEFT);
		p.add(getListResultContract());
		p.setSpacingAfter(20);
		document.add(p);

		p = new Paragraph("Шалгаж баяжуулсан хүний:", new Font(Header, 10));
		p.setAlignment(Element.ALIGN_LEFT);
		p.setSpacingAfter(15);
		document.add(p);

		p = new Paragraph("Нэр: .....................................",
				new Font(arno, 10));
		p.setAlignment(Element.ALIGN_LEFT);
		p.setSpacingAfter(10);
		document.add(p);

		p = new Paragraph("Гарын үсэг: ..............................",
				new Font(arno, 10));
		p.setAlignment(Element.ALIGN_LEFT);
		p.setSpacingAfter(10);
		document.add(p);

		p = new Paragraph("Огноо: ...................................",
				new Font(arno, 10));
		p.setAlignment(Element.ALIGN_LEFT);
		p.setSpacingAfter(10);
		document.add(p);

		document.close();

		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

		return bais;
	}

	/*
	 * Jul 23, 2012 tuguldur.j Start
	 * 
	 * Гэр бүлийн мэдээлэл
	 * 
	 * Jul 23, 2012 tuguldur.j End
	 */
	public PdfPTable getRelativeList() throws DocumentException, IOException {

		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100);

		BaseFont arno;

		arno = BaseFont.createFont(fontType, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);

		Font f = new Font(arno, 8);

		Paragraph p = new Paragraph("Таны юу болох", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph(
				"Гэр бүлийн гишүүдийн эцэг/эх/-ийн нь болон өөрийн нэр", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph("Төрсөн он", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph("Төрсөн аймаг, хот, сум, дүүрэг", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph("Одоо эрхэлж буй ажил", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		if (listRelative != null) {
			for (Relatives r : listRelative) {
				if (r.getRelativeType().equals(RelativeFamily.ISFAMILY)) {

					p = new Paragraph((r.getRelation() != null) ? r
							.getRelation().getName() : "", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);

					p = new Paragraph(
							((r.getLastName() != null) ? r.getLastName() : "")
									+ "\n"
									+ ((r.getFirstName() != null) ? r.getFirstName()
											: ""), f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);

					p = new Paragraph(
							(Integer.toString(r.getBirthDate()) != null) ? Integer.toString(r
									.getBirthDate())
									: "", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);

					p = new Paragraph((r.getBirthCityProvince() != null) ? r
							.getBirthCityProvince().getCityName() : "", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);

					p = new Paragraph(
							(r.getOccupation() != null) ? r.getOccupation()
									: "", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				}
			}
		}
		return table;
	}

	/*
	 * Jul 23, 2012 tuguldur.j Start
	 * 
	 * Садан төрлийн мэдээлэл
	 * 
	 * Jul 23, 2012 tuguldur.j End
	 */

	public PdfPTable getRelativeType() throws DocumentException, IOException {

		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100);

		BaseFont arno;

		arno = BaseFont.createFont(fontType, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);

		Font f = new Font(arno, 8);

		Paragraph p = new Paragraph("Таны юу болох", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph(
				"Садан төрлийн хүмүүсийн эцэг/эх/-ийн нь болон өөрийн нэр", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph("Төрсөн он", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph("Төрсөн аймаг, хот, сум, дүүрэг", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph("Одоо эрхэлж буй ажил", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		if (listRelativeType != null) {
			for (Relatives r : listRelativeType) {
				if (r.getRelativeType().equals(RelativeFamily.ISRELATIVE)) {

					r = (Relatives) dao.getObject(Relatives.class, r.getId());

					p = new Paragraph((r.getRelation() != null) ? r
							.getRelation().getName() : "", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);

					p = new Paragraph(
							((r.getLastName() != null) ? r.getLastName() : "")
									+ "\n"
									+ ((r.getFirstName() != null) ? r.getFirstName()
											: ""), f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);

					p = new Paragraph(
							(Integer.toString(r.getBirthDate()) != null) ? Integer.toString(r
									.getBirthDate())
									: "", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);

					p = new Paragraph((r.getBirthCityProvince() != null) ? r
							.getBirthCityProvince().getCityName() : "", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);

					p = new Paragraph(
							(r.getOccupation() != null) ? r.getOccupation()
									: "", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				}
			}
		}
		return table;
	}

	/*
	 * Jul 23, 2012 tuguldur.j Start
	 * 
	 * Боловсролын талаарх мэдээлэл
	 * 
	 * Jul 23, 2012 tuguldur.j End
	 */

	public PdfPTable getListEducation() throws DocumentException, IOException {

		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);
		BaseFont arno;

		arno = BaseFont.createFont(fontType, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);

		Font f = new Font(arno, 10);

		Paragraph p = new Paragraph("Сургуулийн нэр", f);
		p.setAlignment(Element.ALIGN_CENTER);

		table.addCell(p);

		p = new Paragraph("Элссэн он", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph("Төгссөн он", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph(
				"Эзэмшсэн боловсрол, мэргэжил, гэрчилгээ, дипломын дугаар", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		if (listEducations != null) {
			for (Educations r : listEducations) {

				p = new Paragraph((r.getSchool() != null) ? r.getSchool()
						: (r.getUniversity() != null) ? r.getUniversity()
								.getName() : "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				/*
				 * p = new Paragraph((r.getIngoingDate() != null) ? format
				 * .format(r.getIngoingDate()) : "", f);
				 */
				p = new Paragraph((r.getIngoingDate() != null) ? r
						.getIngoingDate().toString() : "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);
				/*
				 * p = new Paragraph((r.getGraduatedDate() != null) ? format
				 * .format(r.getGraduatedDate()) : "", f);
				 */
				p = new Paragraph((r.getGraduatedDate() != null) ? r
						.getGraduatedDate().toString() : "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				p = new Paragraph((r.getOccupation() != null && r
						.getOccupation().getName() != null) ? r.getOccupation()
						.getName() : "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);
			}
		}
		return table;
	}

	/*
	 * Jul 23, 2012 tuguldur.j Start
	 * 
	 * Боловсролын зэрэглэл
	 * 
	 * Jul 23, 2012 tuguldur.j End
	 */

	public PdfPTable getListDegrees() throws DocumentException, IOException {

		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);

		BaseFont arno;
		arno = BaseFont.createFont(fontType, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);

		Font f = new Font(arno, 10);

		Paragraph p = new Paragraph("Зэрэг", f);
		p.setAlignment(Element.ALIGN_CENTER);

		table.addCell(p);

		p = new Paragraph("Хамгаалсан газар", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph("Хамгаалсан он", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph("Гэрчилгээ, дипломын дугаар", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);
		if (listDegrees != null) {
			for (Degrees r : listDegrees) {

				p = new Paragraph((r.getDoctor() != null) ? r.getDoctor()
						.toString() : "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				p = new Paragraph(
						(r.getIssuedCountry().getCountryName() != null) ? r
								.getIssuedCountry().getCountryName() : "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				/*
				 * p = new Paragraph((r.getIssuedDate() != null) ?
				 * format.format(r .getIssuedDate()) : "", f);
				 */
				p = new Paragraph(
						(r.getIssuedDate() != null) ? r.getIssuedDate() : "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				p = new Paragraph(
						(r.getCertificatedNo().toString() != null) ? r.getCertificatedNo()
								: "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);
			}
		}
		return table;
	}

	/*
	 * Jul 23, 2012 tuguldur.j Start
	 * 
	 * Мэргэшлийн бэлтгэлийн талаарх мэдээлэл
	 * 
	 * Jul 23, 2012 tuguldur.j End
	 */

	public PdfPTable getTraining() throws DocumentException, IOException {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100);

		BaseFont arno;

		arno = BaseFont.createFont(fontType, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);

		Font f = new Font(arno, 10);

		Paragraph p = new Paragraph("Хаана, ямар байгууллагад ", f);
		p.setAlignment(Element.ALIGN_CENTER);

		table.addCell(p);

		p = new Paragraph("Эхэлсэн дууссан он, сар, өдөр", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph("Хугацаа /хоногоор/", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph("Ямар чиглэлээр", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph("Үнэмлэх гэрчилгээний дугаар, олгосон он, сар, өдөр",
				f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		if (listTraining != null) {
			for (Training r : listTraining) {

				r.getStartDate();
				r.getEndDate();
				long milis1 = cal1.getTimeInMillis();
				long milis2 = cal2.getTimeInMillis();
				long diff = milis2 - milis1;

				long TrainedDay = diff / (24 * 60 * 60 * 1000);

				if (r.getStartDate() != null && r.getEndDate() != null) {
					p = new Paragraph((r.getTrainingOrganization() != null) ? r
							.getTrainingOrganization().toString() : "", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);

					p = new Paragraph(
							((r.getStartDate() != null) ? format.format(r
									.getStartDate()) : "")
									+ "\n"
									+ ((r.getEndDate() != null) ? format.format(r
											.getEndDate())
											: ""), f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);

					p = new Paragraph(String.valueOf(TrainedDay), f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
					p = new Paragraph((r.getCourse() != null) ? r.getCourse()
							: "", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);

					p = new Paragraph(
							(r.getGradeDate() != null) ? format.format(r
									.getGradeDate()) : "", f);

					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				}
			}
		}
		return table;
	}

	public PdfPTable getExperience() throws DocumentException, IOException {

		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);

		BaseFont arno;

		arno = BaseFont.createFont(fontType, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);

		Font f = new Font(arno, 10);

		Paragraph p = new Paragraph(
				"Ажилласан байгууллага, газар, түүний хэлтэс, алба", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph("Албан тушаал", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph("Ажилд орсон он, сар", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph("Ажлаас гарсан он, сар", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		if (listExperience != null) {
			for (Experience r : listExperience) {

				p = new Paragraph(
						(r.getOrganizationname() != null) ? r.getOrganizationname()
								: "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				p = new Paragraph(
						(r.getAppointment() != null) ? r.getAppointment() : "",
						f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				p = new Paragraph((r.getStartDate() != null) ? format.format(r
						.getStartDate()) : "", f);

				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				p = new Paragraph((r.getEndDate() != null) ? format.format(r
						.getEndDate()) : "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);
			}
		}
		return table;
	}

	/*
	 * Jul 24, 2012 tuguldur.j Start
	 * 
	 * 4. Туршлагын талаарх мэдээлэл
	 * 
	 * Jul 24, 2012 tuguldur.j End
	 */

	public PdfPTable getSkillType() throws DocumentException, IOException {

		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);
		BaseFont arno;

		arno = BaseFont.createFont(fontType, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);

		Font f = new Font(arno, 10);

		Paragraph p = new Paragraph(
				"Ажилласан байгууллага, газар, түүний хэлтэс, алба", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph("Албан тушаал", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph("Ажилд орсон он, сар", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph("Ажлаас гарсан он, сар", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		for (Experience r : listExperience) {

			p = new Paragraph(
					(r.getOrganizationname() != null) ? r.getOrganizationname()
							: "", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			p = new Paragraph((r.getAppointment() != null) ? r.getAppointment()
					: "", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			p = new Paragraph((r.getStartDate() != null) ? format.format(r
					.getStartDate()) : "", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			p = new Paragraph((r.getEndDate() != null) ? format.format(r
					.getEndDate()) : "", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);
		}
		return table;
	}

	/*
	 * Jul 25, 2012 tuguldur.j Start
	 * 
	 * 3.2 Албан тушаалын зэрэг дэв, цол
	 * 
	 * Jul 25, 2012 tuguldur.j End
	 */

	public PdfPTable getListDegreeGrade() throws DocumentException, IOException {

		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100);
		BaseFont arno;

		arno = BaseFont.createFont(fontType, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);

		Font f = new Font(arno, 10);

		Paragraph p = new Paragraph(Element.ALIGN_CENTER, "Цолны нэр", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph(Element.ALIGN_CENTER, "Тушаалын дугаар ", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph(Element.ALIGN_CENTER, "Цол олгосон огноо", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		if (listDegreeGrade != null) {
			for (EmployeeMilitary r : listDegreeGrade) {
				p = new Paragraph((r.getMilitary() != null) ? r.getMilitary()
						.getMilitaryName().toString() : "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				p = new Paragraph(
						(r.getTushaalDugaar() != null) ? r.getTushaalDugaar()
								: "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				p = new Paragraph(
						(r.getOlgosonOgnoo() != null) ? format.format(r
								.getOlgosonOgnoo()) : "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);
			}
		}
		return table;
	}

	/*
	 * Jul 25, 2012 tuguldur.j Start
	 * 
	 * Хэлний мэдлэгийн түвшин
	 * 
	 * Jul 25, 2012 tuguldur.j End
	 */

	public PdfPTable getLanguage() throws DocumentException, IOException {

		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100);
		BaseFont arno;

		arno = BaseFont.createFont(fontType, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);

		Font f = new Font(arno, 10);

		Paragraph p = new Paragraph(Element.ALIGN_CENTER,
				"Гадаад хэлний мэдлэг", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph(Element.ALIGN_CENTER, "Сонсож ойлгох", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph(Element.ALIGN_CENTER, "Ярих ", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph(Element.ALIGN_CENTER, "Унших", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph(Element.ALIGN_CENTER, "Бичих", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		if (listLanguage != null) {
			for (Language r : listLanguage) {

				p = new Paragraph(
						(r.getForeignLanguage() != null) ? r
								.getForeignLanguage().getLanguageName()
								.toString() : "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				p = new Paragraph((r.getListening() != null && r.getListening()
						.name() != null) ? messages.get(r.getListening().name()
						.toString()) : "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				p = new Paragraph((r.getSpeaking() != null && r.getSpeaking()
						.name() != null) ? messages.get(r.getSpeaking().name()
						.toString()) : "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				p = new Paragraph((r.getReading() != null && r.getReading()
						.name() != null) ? messages.get(r.getReading().name()
						.toString()) : "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				p = new Paragraph((r.getWriting() != null && r.getWriting()
						.name() != null) ? messages.get(r.getWriting().name()
						.toString()) : "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);
			}
		}
		return table;
	}

	/*
	 * Jul 25, 2012 tuguldur.j Start
	 * 
	 * Компьютерийн мэдлэгийн түвшиг
	 * 
	 * Jul 25, 2012 tuguldur.j End
	 */

	public PdfPTable getComputer() throws DocumentException, IOException {

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		BaseFont arno;

		arno = BaseFont.createFont(fontType, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);

		Font f = new Font(arno, 10);

		Paragraph p = new Paragraph(Element.ALIGN_CENTER,
				"Эзэмшсэн программын нэр", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph(Element.ALIGN_CENTER, "Түвшин", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		if (listComputer != null) {
			for (Computer r : listComputer) {

				p = new Paragraph((r.getProgram().getProgramName() != null) ? r
						.getProgram().getProgramName().toString() : "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				p = new Paragraph(
						(r.getProgramlevel().toString() != null) ? messages.get(r
								.getProgramlevel().toString())
								: "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);
			}
		}
		return table;
	}

	public PdfPTable getComputerOther() throws DocumentException, IOException {

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		BaseFont arno;

		arno = BaseFont.createFont(fontType, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);

		Font f = new Font(arno, 10);

		if (listComputerOther != null) {
			for (ComputerOther r : listComputerOther) {

				Paragraph p = new Paragraph((r.getOtherProgram() != null) ? r
						.getOtherProgram().toString() : "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				p = new Paragraph(
						(r.getOtherProgramlevel() != null) ? messages.get(r
								.getOtherProgramlevel().name().toString()) : "",
						f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);
			}
		}
		return table;
	}

	public PdfPTable getOfficeEquipment() throws DocumentException, IOException {

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		BaseFont arno;

		arno = BaseFont.createFont(fontType, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);

		Font f = new Font(arno, 10);

		Paragraph p = new Paragraph(Element.ALIGN_CENTER,
				"Оффисийн тоног төхөөрөмж ашиглах", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph(Element.ALIGN_CENTER, "", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		if (listOfficeEquipment != null) {
			for (OfficeEquipment r : listOfficeEquipment) {

				p = new Paragraph(
						(r.getFacility().getFacilityName() != null) ? r
								.getFacility().getFacilityName().toString()
								: "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				p = new Paragraph(
						(r.getFacilityLevel().toString() != null) ? messages.get(r
								.getFacilityLevel().toString())
								: "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);
			}
		}
		return table;
	}

	/*
	 * Jul 25, 2012 tuguldur.j Start
	 * 
	 * Ур чадварын мэдээлэл
	 * 
	 * Jul 25, 2012 tuguldur.j End
	 */

	public PdfPTable getListSkills() throws DocumentException, IOException {

		PdfPTable table = new PdfPTable(2);

		table.setWidthPercentage(100);
		BaseFont arno;
		BaseFont bold;

		arno = BaseFont.createFont(fontType, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		Font f = new Font(arno, 10);

		bold = BaseFont.createFont(fontTypeBold, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		Font fb = new Font(bold, 10);

		HashMap<SkillGroup, List<Skills>> maps = new HashMap<SkillGroup, List<Skills>>();
		List<Skills> listSkills = dao.getListSkills(emp);

		for (Skills s : listSkills) {
			SkillGroup sg = s.getSkillType().getSkillGroup();
			if (!maps.containsKey(sg)) {
				List<Skills> skills = new ArrayList<Skills>();
				skills.add(s);
				maps.put(sg, skills);
			} else {
				List<Skills> skills = maps.get(sg);
				skills.add(s);
			}
		}

		for (SkillGroup sg : maps.keySet()) {

			Paragraph p = new Paragraph((sg.getName() != null) ? sg.getName()
					.toString() : "", fb);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			p = new Paragraph(Element.ALIGN_CENTER, "Түвшин", fb);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);
			table.completeRow();

			for (Skills s : (List<Skills>) maps.get(sg)) {

				p = new Paragraph((s.getSkillType() != null) ? s.getSkillType()
						.getName() : "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				p = new Paragraph((s.getSkillPoint() != null) ? s
						.getSkillPoint().toString() : "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);
			}
		}
		return table;
	}

	// Busad Skills
	public PdfPTable getListOtherSkill() throws DocumentException, IOException {

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		BaseFont arno;
		BaseFont bold;

		arno = BaseFont.createFont(fontType, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		Font f = new Font(arno, 10);

		bold = BaseFont.createFont(fontTypeBold, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		Font fb = new Font(bold, 10);

		Paragraph p = new Paragraph(Element.ALIGN_CENTER,
				"Дээр дурдсанаас бусад ур чадвараасаа заримыг нэрлэнэ үү", fb);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph(Element.ALIGN_CENTER, "Түвшин", fb);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		if (listEmployee != null) {

			p = new Paragraph((emp.getBusadSkillName1() != null) ? emp
					.getBusadSkillName1().toString() : "", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			p = new Paragraph((emp.getBusadSkillOnoo1() != null) ? emp
					.getBusadSkillOnoo1().toString() : "", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			p = new Paragraph((emp.getBusadSkillName2() != null) ? emp
					.getBusadSkillName2().toString() : "", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			p = new Paragraph((emp.getBusadSkillOnoo2() != null) ? emp
					.getBusadSkillOnoo1().toString() : "", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			p = new Paragraph((emp.getBusadSkillName3() != null) ? emp
					.getBusadSkillName3().toString() : "", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			p = new Paragraph((emp.getBusadSkillOnoo3() != null) ? emp
					.getBusadSkillOnoo3().toString() : "", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);
		}
		return table;
	}

	// Цол зэрэг
	public PdfPTable getListDegreeGradeRank() throws DocumentException,
			IOException {

		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);
		BaseFont arno;

		arno = BaseFont.createFont(fontType, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);

		Font f = new Font(arno, 10);

		Paragraph p = new Paragraph(Element.ALIGN_CENTER, "Цол ", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph(Element.ALIGN_CENTER, "Цол олгосон байгууллага", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph(Element.ALIGN_CENTER, "Он, сар ", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph(Element.ALIGN_CENTER, "Гэрчилгээ, дипломын дугаар", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);
		if (listDegreeGradeRank != null) {

			for (DegreeGradeRank r : listDegreeGradeRank) {
				p = new Paragraph((r.getAcademicRank() != null) ? r
						.getAcademicRank().getName() : "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				p = new Paragraph(
						(r.getRankOrganization() != null) ? r.getRankOrganization()
								: "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				/*
				 * p = new Paragraph(((r.getRankDate() != null) ?
				 * format.format(r .getRankDate()) : ""), f);
				 */
				p = new Paragraph(((r.getRankDate() != null) ? r.getRankDate()
						: ""), f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				p = new Paragraph(
						(r.getRankCertificate() != null) ? r.getRankCertificate()
								: "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);
			}
		}
		return table;
	}

	// Нэмэгдэл хуудас

	public PdfPTable getListAddition() throws DocumentException, IOException {

		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);
		BaseFont arno;

		arno = BaseFont.createFont(fontType, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);

		Font f = new Font(arno, 10);

		Paragraph p = new Paragraph(Element.ALIGN_CENTER, "Шагнагдсан огноо", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph(Element.ALIGN_CENTER,
				"Алдар цол, одон, медалийн нэр", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph(Element.ALIGN_CENTER, "Шийдвэрийн нэр, дугаар", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph(Element.ALIGN_CENTER, "Шагнал авсан үндэслэл", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		for (Honour r : listHonour) {

			if (r.getAwardType().getVal() == 0
					|| r.getAwardType().getVal() == 3) {

				p = new Paragraph(
						((r.getDateOfAwarded() != null) ? format.format(r
								.getDateOfAwarded()) : ""), f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				p = new Paragraph((r.getAward() != null) ? r.getAward()
						.getName() : "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				p = new Paragraph(
						((r.getDictateId() != null) ? r.getDescription() : "")
								+ "\n"
								+ ((r.getDictateId() != null) ? r.getDictateId()
										: ""), f);

				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				p = new Paragraph(
						(r.getDescription() != null) ? r.getDescription() : "",
						f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

			}
		}

		return table;
	}

	// Сүүлийн 5 жил гадаадад зорчсон байдал
	public PdfPTable getListTravelAbroad() throws DocumentException,
			IOException {

		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100);
		BaseFont arno;

		arno = BaseFont.createFont(fontType, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);

		Font f = new Font(arno, 10);

		Paragraph p = new Paragraph(Element.ALIGN_CENTER, "Огноо ", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph(Element.ALIGN_CENTER, "Ямар улсад", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph(Element.ALIGN_CENTER, "Явсан зорилго ", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		if (listTravelAbroad != null) {

			for (TravelAbroad r : listTravelAbroad) {
				p = new Paragraph((r.getWentDate() != null) ? format.format(r
						.getWentDate()) : "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				p = new Paragraph((r.getCountry() != null) ? r.getCountry()
						.getCountryName() : "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				p = new Paragraph(((r.getJobTask() != null) ? r.getJobTask()
						: ""), f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

			}
		}
		return table;
	}

	// Үр дүнгийн гэрээний биелэлт
	public PdfPTable getListResultContract() throws DocumentException,
			IOException {

		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100);
		BaseFont arno;

		arno = BaseFont.createFont(fontType, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);

		Font f = new Font(arno, 10);

		Paragraph p = new Paragraph(Element.ALIGN_CENTER, "Он ", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph(Element.ALIGN_CENTER,
				"Шууд харъяалагдах даргын үнэлгээ", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		p = new Paragraph(Element.ALIGN_CENTER,
				"Дараагийн шатны даргын үнэлгээ", f);
		p.setAlignment(Element.ALIGN_CENTER);
		table.addCell(p);

		if (listResultContract != null) {

			for (ResultContract r : listResultContract) {

				p = new Paragraph(
						(r.getContractDate() != null) ? format.format(r
								.getContractDate()) : "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				p = new Paragraph((r.getDirectSuperiorScore() != null) ? r
						.getDirectSuperiorScore().toString() : "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

				p = new Paragraph((r.getSuperiorScore() != null) ? r
						.getSuperiorScore().toString() : "", f);
				p.setAlignment(Element.ALIGN_CENTER);
				table.addCell(p);

			}
		}
		return table;
	}

	// Сахилгын мэдээлэл
	public PdfPTable getListSahilga() throws DocumentException, IOException {
		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100);
		BaseFont arno;

		arno = BaseFont.createFont(fontType, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);

		Font f = new Font(arno, 10);

		if (emp != null && emp.getSahilga() != null) {
			Paragraph p = new Paragraph(Element.ALIGN_CENTER,
					"Тушаал гаргасан субъект", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			p = new Paragraph(Element.ALIGN_CENTER, "Шийтгэл", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			p = new Paragraph(Element.ALIGN_CENTER, "Үндэслэл", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			p = new Paragraph(Element.ALIGN_CENTER,
					"Шийтгэл авагдсан дугаар, огноо", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			p = new Paragraph(Element.ALIGN_CENTER,
					"Арилгасан тушаал дугаар, огноо", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			if (listSahilga != null) {
				for (Sahilga r : listSahilga) {

					p = new Paragraph((r.getCommandSubject() != null) ? r
							.getCommandSubject().getSubjectName() : "", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);

					p = new Paragraph((r.getSahilgaShiitgel() != null) ? r
							.getSahilgaShiitgel().getShiitgelName() : "", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);

					p = new Paragraph((r.getCause() != null) ? r.getCause()
							: "", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);

					p = new Paragraph(
							((r.getAvagdsanShiitgelDugaar() != null) ? r.getAvagdsanShiitgelDugaar()
									+ ","
									: "")
									+ "\n"
									+ ((r.getShiitgelAvagdsanOgnoo() != null) ? format.format(r
											.getShiitgelAvagdsanOgnoo())
											: ""), f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);

					p = new Paragraph(
							((r.getArilgasanTushaalDugaar() != null) ? r.getArilgasanTushaalDugaar()
									+ ","
									: "")
									+ "\n"
									+ ((r.getArilgasanTushaalOgnoo() != null) ? format.format(r
											.getArilgasanTushaalOgnoo())
											: ""), f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				}
			}

		}
		return table;
	}

	public Employee getEmp() {
		return emp;
	}

	public void setEmp(Employee emp) {
		this.emp = emp;
	}

}
