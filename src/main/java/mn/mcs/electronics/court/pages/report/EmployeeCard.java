package mn.mcs.electronics.court.pages.report;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.Relatives;
import mn.mcs.electronics.court.entities.employee.ResultContract;
import mn.mcs.electronics.court.enums.Gender;
import mn.mcs.electronics.court.enums.RelativeFamily;
import mn.mcs.electronics.court.pages.employee.PageEmployeeDetail;
import mn.mcs.electronics.court.util.PDFStreamResponse;
import mn.mcs.electronics.court.util.beans.EmployeeCardDegreeBean;
import mn.mcs.electronics.court.util.beans.EmployeeCardDemeritBean;
import mn.mcs.electronics.court.util.beans.EmployeeCardEducationBean;
import mn.mcs.electronics.court.util.beans.EmployeeCardExperienceBean;
import mn.mcs.electronics.court.util.beans.EmployeeCardOtherAwardBean;
import mn.mcs.electronics.court.util.beans.EmployeeCardSalaryBean;
import mn.mcs.electronics.court.util.beans.EmployeeCardStateAwardBean;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class EmployeeCard {

	@Inject
	private CoreDAO dao;

	@Inject
	private Messages messages;

	@SessionState
	private LoginState loginState;

	@InjectPage
	private PageEmployeeDetail pageEmployee;

	@Persist
	private List<Relatives> listRelative;

	@Persist
	private List<EmployeeCardEducationBean> listEducations;

	@Persist
	private List<EmployeeCardDegreeBean> listDegrees;

	@Persist
	private List<EmployeeCardExperienceBean> listExperience;

	@Persist
	private List<Employee> listEmployee;

	@Persist
	private List<EmployeeCardOtherAwardBean> listHonourOther;

	@Persist
	private List<EmployeeCardStateAwardBean> listHonourState;

	@Persist
	private List<ResultContract> listResultContract;

	@Persist
	private List<EmployeeCardDemeritBean> listSahilga;

	@Persist
	private List<EmployeeCardSalaryBean> listSalary;

	@Persist
	private List<Relatives> listRelatives;

	@Persist
	private Employee emp;

	@Persist
	private EmployeeCardExperienceBean beanExperience;

	@Property
	@Persist
	private int familyMemberCount;

	private static String fontType = "/fonts/arial.ttf";
	private static String fontTypeBold = "/fonts/arialbd.ttf";
	private static String fontStyleItalic = "/fonts/ariali.ttf";
	public static final String IMAGE_PATH_EMP = "/images/profile/";

	private SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");

	void beginRender() {
		emp = pageEmployee.getEmployee();
		familyMemberCount = 0;

		if (beanExperience == null)
			beanExperience = new EmployeeCardExperienceBean();

		if (listRelative == null)
			listRelative = new ArrayList<Relatives>();

		if (listEducations == null)
			listEducations = new ArrayList<EmployeeCardEducationBean>();

		if (listDegrees == null)
			listDegrees = new ArrayList<EmployeeCardDegreeBean>();

		if (listExperience == null)
			listExperience = new ArrayList<EmployeeCardExperienceBean>();

		if (listEmployee == null)
			listEmployee = new ArrayList<Employee>();

		if (listHonourOther == null)
			listHonourOther = new ArrayList<EmployeeCardOtherAwardBean>();

		if (listHonourState == null)
			listHonourState = new ArrayList<EmployeeCardStateAwardBean>();

		if (listResultContract == null)
			listResultContract = new ArrayList<ResultContract>();

		if (listSahilga == null)
			listSahilga = new ArrayList<EmployeeCardDemeritBean>();

		if (listSalary == null)
			listSalary = new ArrayList<EmployeeCardSalaryBean>();

		if (listRelatives == null)
			listRelatives = new ArrayList<Relatives>();

		if (emp == null) {
			emp = new Employee();
		}

		listRelative = dao.getRelatives(pageEmployee.getEmployee().getId(),
				RelativeFamily.ISFAMILY);
		listDegrees = dao.getEmployeeCardDegreeBean(pageEmployee.getEmployee());
		listExperience = dao.getEmployeeCardExperienceBean(pageEmployee
				.getEmployee());
		listEmployee = dao.getListEmployee(pageEmployee.getOrganization());
		listEducations = dao.getEmployeeCardEducationBean(pageEmployee
				.getEmployee());
		listHonourState = dao.getEmployeeCardStateAwardBean(pageEmployee
				.getEmployee());
		listHonourOther = dao.getEmployeeCardOtherAwardBean(pageEmployee
				.getEmployee());
		listResultContract = dao.getResultContract(pageEmployee.getEmployee());
		listSahilga = dao
				.getEmployeeCardDemeritBean(pageEmployee.getEmployee());
		listSalary = dao.getEmployeeCardSalaryBean(pageEmployee.getEmployee());
		familyMemberCount = dao.getEmployeeFamilyMemberCount(pageEmployee
				.getEmployee()) + 1;
		listRelatives = dao
				.getEmployeeFamilyMembers(pageEmployee.getEmployee());
	}

	public String getDateOfDemerit(Date inputDate) {
		if (inputDate == null)
			return "";

		return format.format(inputDate);
	}

	public EmployeeCardExperienceBean getBeanExperience() {
		return beanExperience;
	}

	public void setBeanExperience(EmployeeCardExperienceBean beanExperience) {
		this.beanExperience = beanExperience;
	}

	public StreamResponse onSubmit() throws IOException, DocumentException {
		beginRender();
		InputStream is = createPdf("aa");
		return new PDFStreamResponse(is, "employee" + "_card");
	}

	public InputStream createPdf(String filename) throws IOException,
			DocumentException {

		Document document = new Document(PageSize.A4.rotate());

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		PdfWriter writer = PdfWriter.getInstance(document, baos);

		if (!document.isOpen())
			document.open();

		BaseFont arno, arnoi;
		arno = BaseFont.createFont(fontType, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		arno = BaseFont.createFont(fontStyleItalic, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);

		BaseFont Header;
		Header = BaseFont.createFont(fontTypeBold, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);

		/*
		 * BaseFont italicfont; italicfont = BaseFont.createFont(fontTypeItalic,
		 * BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		 */

		Paragraph p = new Paragraph();
		PdfContentByte canvas = writer.getDirectContentUnder();
		writer.setCompressionLevel(0);

		int tableFont = 8;
		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100);
		float[] widths = new float[] { 40f, 20f, 45f };
		table.setWidths(widths);
		table.getDefaultCell().setBorderColor(new Color(255, 255, 255));

		PdfPCell c;

		// 1-р багана
		PdfPTable row1Table = new PdfPTable(2);
		row1Table.setWidthPercentage(95);
		row1Table.getDefaultCell().setBorderColor(new Color(255, 255, 255));
		c = new PdfPCell();
		c.setColspan(2);
		c.setBorderColor(new Color(255, 255, 255));
		p = new Paragraph("Регистрийн дугаар: "
				+ (((emp.getRegisterNo() != null) ? emp.getRegisterNo()
						.toString() : "")), new Font(arno, tableFont));
		p.setAlignment(Element.ALIGN_LEFT);
		c.addElement(p);

		p = new Paragraph("Иргэний үнэмлэхний дугаар: "
				+ (((emp.getIdCardNumber() != null) ? emp.getIdCardNumber()
						.toString() : "")), new Font(arno, tableFont));
		p.setAlignment(Element.ALIGN_LEFT);
		c.addElement(p);

		p = new Paragraph("НД дэвтрийн дугаар: "
				+ (((emp.getSocialInsuranceNumber() != null) ? emp
						.getSocialInsuranceNumber().toString() : "")),
				new Font(arno, tableFont));
		p.setAlignment(Element.ALIGN_LEFT);
		c.addElement(p);

		p = new Paragraph("ЭМД дэвтрийн дугаар: "
				+ (((emp.getHealthInsuranceNumber() != null) ? emp
						.getHealthInsuranceNumber().toString() : "")),
				new Font(arno, tableFont));
		p.setAlignment(Element.ALIGN_LEFT);
		c.addElement(p);
		p = new Paragraph(" ");
		c.addElement(p);

		p = new Paragraph("Овог: "
				+ (((emp.getFamilyName() != null) ? emp.getFamilyName()
						.toString() : "")), new Font(arno, tableFont));
		p.setAlignment(Element.ALIGN_LEFT);
		c.addElement(p);

		p = new Paragraph("Эцэг /эх/-ийн нэр: "
				+ (((emp.getLastname() != null) ? emp.getLastname().toString()
						: "")), new Font(arno, tableFont));
		p.setAlignment(Element.ALIGN_LEFT);
		c.addElement(p);

		p = new Paragraph("Өөрийн нэр: "
				+ (((emp.getFirstName() != null) ? emp.getFirstName()
						.toString() : "")), new Font(arno, tableFont));
		p.setAlignment(Element.ALIGN_LEFT);
		c.addElement(p);
		row1Table.addCell(c);

		// Ажилтаны хүйс, ургын овгийн мэдээлэл
		p = new Paragraph(
				"Хүйс: "
						+ ((emp.getGender() != null) ? ((emp.getGender() == Gender.FEMALE) ? "Эмэгтэй"
								: "Эрэгтэй")
								: ""), new Font(arno, tableFont));
		row1Table.addCell(p);

		p = new Paragraph("Үндэс угсаа: "
				+ ((emp.getFamilyName() != null) ? emp.getFamilyName()
						.toString() : ""), new Font(arno, tableFont));
		row1Table.addCell(p);

		p = new Paragraph("Төрсөн аймаг, хот: "
				+ ((emp.getBirthCityProvince() != null) ? emp
						.getBirthCityProvince().getCityName().toString() : ""),
				new Font(arno, tableFont));
		row1Table.addCell(p);

		p = new Paragraph("сум, дүүрэг: "
				+ ((emp.getBirthDistrictSum() != null) ? emp
						.getBirthDistrictSum().getDistrictSumName().toString()
						: ""), new Font(arno, tableFont));
		p.setAlignment(Element.ALIGN_LEFT);
		row1Table.addCell(p);

		table.addCell(row1Table);

		// 2-р багана
		PdfPTable row1Table2 = new PdfPTable(1);
		row1Table2.setWidthPercentage(95);
		row1Table2.getDefaultCell().setBorderColor(new Color(255, 255, 255));

		p = new Paragraph("Хувийн дугаар . . .", new Font(arno, tableFont));
		p.setAlignment(Element.ALIGN_CENTER);
		row1Table2.addCell(p);

		p = new Paragraph(" ");
		row1Table2.addCell(p);

		table.addCell(row1Table2);

		// 3-р багана
		row1Table = new PdfPTable(2);
		row1Table.setWidthPercentage(95);
		row1Table.getDefaultCell().setBorderColor(new Color(255, 255, 255));
		c = new PdfPCell();
		c.setColspan(2);
		c.setBorderColor(new Color(255, 255, 255));

		p = new Paragraph("Боловсролын зэрэг: "
				+ (((emp.getIdCardNumber() != null) ? emp.getIdCardNumber()
						.toString() : "")), new Font(arno, tableFont));
		p.setAlignment(Element.ALIGN_LEFT);
		c.addElement(p);

		p = new Paragraph("Мэргэжил: "
				+ (((emp.getOccupation() != null) ? emp.getOccupation()
						.getName().toString() : "")), new Font(arno, tableFont));
		p.setAlignment(Element.ALIGN_LEFT);
		c.addElement(p);

		p = new Paragraph("Мэргэшил: ", new Font(arno, tableFont));
		p.setAlignment(Element.ALIGN_LEFT);
		c.addElement(p);

		p = new Paragraph("Оршин суугаа хаяг: "
				+ "   "
				+ ((emp.getAddCity() != null) ? emp.getAddCity().getCityName()
						: "")
				+ "   "
				+ "аймаг/хот,  "
				+ "   "
				+ ((emp.getAddDistrictSum() != null) ? emp.getAddDistrictSum()
						.getDistrictSumName() : "") + "   " + "сум/дүүрэг,  "
				+ ((emp.getAddress() != null) ? emp.getAddress() : ""),
				new Font(arno, tableFont));
		p.setAlignment(Element.ALIGN_LEFT);
		c.addElement(p);

		row1Table.addCell(c);

		p = new Paragraph("Утас, үүрэн утас: "
				+ (((emp.getPhoneNo() != null) ? emp.getPhoneNo().toString()
						+ ", " : ""))
				+ (((emp.getMobileNo() != null) ? emp.getMobileNo().toString()
						: "")), new Font(arno, tableFont));
		p.setAlignment(Element.ALIGN_LEFT);
		row1Table.addCell(p);

		p = new Paragraph("Факс:"
				+ (((emp.getWorkFax() != null) ? emp.getWorkFax().toString()
						: "")), new Font(arno, tableFont));
		p.setAlignment(Element.ALIGN_LEFT);
		row1Table.addCell(p);

		c = new PdfPCell();
		c.setBorderColor(new Color(255, 255, 255));
		c.setColspan(2);
		p = new Paragraph(
				"E-mail: "
						+ (((emp.geteMail() != null) ? emp.geteMail()
								.toString() : "")), new Font(arno, tableFont));
		p.setAlignment(Element.ALIGN_LEFT);
		c.addElement(p);

		p = new Paragraph(
				"Онцын шаардлага гарвал харилцах хүний нэр, хаяг, утас: "
						+ (((emp.geteCallPersonName() != null) ? emp
								.geteCallPersonName().toString() + ", " : ""))
						+ (((emp.geteCallPersonAddress() != null) ? emp
								.geteCallPersonAddress().toString() + ", " : ""))
						+ (((emp.geteCallPersonMobileNo() != null) ? emp
								.geteCallPersonMobileNo().toString() : "")),
				new Font(arno, tableFont));
		p.setAlignment(Element.ALIGN_LEFT);
		c.addElement(p);

		String familyMembers = "";
		if (listRelatives.size() != 0) {
			for (int i = 0; i < listRelatives.size(); i++) {
				if (i == listRelatives.size() - 1)
					familyMembers += listRelatives.get(i).getRelation()
							.getName();
				else
					familyMembers += listRelatives.get(i).getRelation()
							.getName()
							+ ", ";
			}

			familyMembers += " - ийн хамт";
		} else
			familyMembers = "";

		p = new Paragraph("Ам бүл: " + familyMemberCount
				+ "     Гэр бүлийн байдал: " + familyMembers, new Font(arno,
				tableFont));
		p.setAlignment(Element.ALIGN_LEFT);
		c.addElement(p);
		row1Table.addCell(c);

		table.addCell(row1Table);

		document.add(table);

		p = new Paragraph("ТОМИЛГООНЫ МЭДЭЭЛЭЛ:", new Font(Header, 10));
		p.setAlignment(Element.ALIGN_CENTER);
		p.setSpacingBefore(5);
		document.add(p);

		p = new Paragraph("");
		p.setFont(new Font(arno, 12));
		p.setAlignment(Element.ALIGN_LEFT);
		p.add(getMovement());
		p.setSpacingAfter(5);
		document.add(p);

		// /3р мөр
		PdfPTable table2 = new PdfPTable(3);
		table2.setWidthPercentage(100);
		float[] width1 = new float[] { 30f, 30f, 40f };
		table2.setWidths(width1);
		table2.getDefaultCell().setBorderColor(new Color(255, 255, 255));

		// /3р мөр 1р багана
		PdfPTable row3Table1 = new PdfPTable(2);
		row3Table1.setWidthPercentage(80);
		row3Table1.getDefaultCell().setBorderColor(new Color(255, 255, 255));

		p = new Paragraph("Хугацаа ЦА хааж эхэлсэн", new Font(arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table1.addCell(p);

		p = new Paragraph("    он            сар           өдөр", new Font(
				arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table1.addCell(p);

		p = new Paragraph("Хугацаа ЦА хааж дууссан", new Font(arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table1.addCell(p);

		p = new Paragraph("    он            сар           өдөр", new Font(
				arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table1.addCell(p);

		p = new Paragraph("Хугацаа ЦА хааж хугацаа", new Font(arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table1.addCell(p);

		p = new Paragraph("    жил         сар           өдөр", new Font(arno,
				8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table1.addCell(p);

		p = new Paragraph("ТТАлба хааж эхэлсэн", new Font(arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table1.addCell(p);

		p = new Paragraph("    он            сар           өдөр", new Font(
				arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table1.addCell(p);

		p = new Paragraph("Халсан, чөлөөлсөн", new Font(arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table1.addCell(p);

		p = new Paragraph("    он            сар           өдөр", new Font(
				arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table1.addCell(p);

		p = new Paragraph("Эргэн орсон", new Font(arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table1.addCell(p);

		p = new Paragraph("    он            сар           өдөр", new Font(
				arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table1.addCell(p);

		p = new Paragraph("Халсан чөлөөлсөн", new Font(arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table1.addCell(p);

		p = new Paragraph("    он            сар           өдөр", new Font(
				arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table1.addCell(p);

		p = new Paragraph("Эргэн орсон", new Font(arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table1.addCell(p);

		p = new Paragraph("    он            сар           өдөр", new Font(
				arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table1.addCell(p);

		p = new Paragraph("Халсан чөлөөлсөн", new Font(arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table1.addCell(p);

		p = new Paragraph("    он            сар           өдөр", new Font(
				arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table1.addCell(p);

		p = new Paragraph("Эргэн орсон", new Font(arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table1.addCell(p);

		p = new Paragraph("    он            сар           өдөр", new Font(
				arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table1.addCell(p);

		p = new Paragraph("Завсардсан хугацаа", new Font(arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table1.addCell(p);

		p = new Paragraph("    жил         сар           өдөр", new Font(arno,
				8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table1.addCell(p);

		// c.addElement(row3Table1);
		table2.addCell(row3Table1);

		// 3р мөр 2р багана
		PdfPTable row3Table2 = new PdfPTable(2);
		row3Table2.setWidthPercentage(80);
		row3Table2.getDefaultCell().setBorderColor(new Color(255, 255, 255));

		p = new Paragraph("Улсад анх ажилласан", new Font(arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table2.addCell(p);

		p = new Paragraph("    он            сар           өдөр", new Font(
				arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table2.addCell(p);

		p = new Paragraph("Хувийн хэвшилд", new Font(arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table2.addCell(p);

		p = new Paragraph("    жил         сар           өдөр", new Font(arno,
				8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table2.addCell(p);

		p = new Paragraph("Цэргийн сургуульд", new Font(arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table2.addCell(p);

		p = new Paragraph("    жил         сар           өдөр", new Font(arno,
				8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table2.addCell(p);

		p = new Paragraph("Нийт ажилласан жил", new Font(arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table2.addCell(p);

		p = new Paragraph("    жил         сар           өдөр", new Font(arno,
				8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table2.addCell(p);

		p = new Paragraph("Үүнээс ТАлбанд", new Font(arno, 8));
		p.setAlignment(Element.ALIGN_RIGHT);
		row3Table2.addCell(p);

		p = new Paragraph("    жил         сар           өдөр", new Font(arno,
				8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table2.addCell(p);

		p = new Paragraph("Үүнээс ТТАлбанд", new Font(arno, 8));
		p.setAlignment(Element.ALIGN_RIGHT);
		row3Table2.addCell(p);

		p = new Paragraph("    жил         сар           өдөр", new Font(arno,
				8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table2.addCell(p);

		p = new Paragraph("Ахлагчийн бүрэлдэхүүнд", new Font(arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table2.addCell(p);

		p = new Paragraph("    он            сар           өдөр", new Font(
				arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table2.addCell(p);

		p = new Paragraph("Офицерийн бүрэлдэхүүнд", new Font(arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table2.addCell(p);

		p = new Paragraph("    он            сар           өдөр", new Font(
				arno, 8));
		p.setAlignment(Element.ALIGN_LEFT);
		row3Table2.addCell(p);

		table2.addCell(row3Table2);

		// 3р мөр 3р багана
		p = new Paragraph();
		p.setAlignment(Element.ALIGN_LEFT);
		p.add(row3Table2);
		table2.addCell(getMilitaryDegree());
		document.add(table2);

		p = new Paragraph(
				"Карт нээсэн . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .",
				new Font(arno, 8));
		p.setAlignment(Element.ALIGN_RIGHT);
		document.add(p);

		// / 2-р хуудас
		p = new Paragraph("Шагнал, сахилгын арга хэмжээний тэмдэглэл:",
				new Font(Header, 10));
		p.setAlignment(Element.ALIGN_CENTER);
		p.setSpacingBefore(10);
		document.add(p);

		p = new Paragraph("");
		p.setFont(new Font(arno, 12));
		p.setAlignment(Element.ALIGN_LEFT);
		p.add(getAwardAndDemerit());
		document.add(p);

		p = new Paragraph("Сургалтанд хамрагдсан тэмдэглэл: ", new Font(Header,
				10));
		p.setAlignment(Element.ALIGN_CENTER);
		p.setSpacingBefore(10);
		document.add(p);

		p = new Paragraph("");
		p.setFont(new Font(arno, 12));
		p.setAlignment(Element.ALIGN_LEFT);
		p.add(getEducation());
		document.add(p);

		p = new Paragraph("Цалин, нэмэгдэл:", new Font(Header, 10));
		p.setAlignment(Element.ALIGN_CENTER);
		p.setSpacingBefore(10);
		document.add(p);

		p = new Paragraph("");
		p.setFont(new Font(arno, 12));
		p.setAlignment(Element.ALIGN_LEFT);
		p.add(getSalary());
		document.add(p);

		document.close();

		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

		return bais;
	}

	public PdfPTable getMovement() throws DocumentException, IOException {
		Paragraph p = new Paragraph();
		PdfPTable table = new PdfPTable(8);
		table.setSpacingBefore(3);
		table.setWidthPercentage(100);
		BaseFont arno;

		arno = BaseFont.createFont(fontType, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		Font f = new Font(arno, 8);

		if (listExperience.size() == 0) {
			for (int i = 0; i < 40; i++) {
				if (i == 0) {
					p = new Paragraph("Байгууллага, нэгж", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else if (i == 8) {
					p = new Paragraph("Албан тушаал", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else if (i == 16) {
					p = new Paragraph("Томилсон /чөлөөлсөн/ огноо", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else if (i == 24) {
					p = new Paragraph("Шийдвэрийн огноо, дугаар", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else if (i == 32) {
					p = new Paragraph("Шийдвэрийн гаргасан албан тушаалтан", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			PdfPCell spanCell1 = new PdfPCell();
			spanCell1.setColspan(8);
			p = new Paragraph(" ");
			spanCell1.addElement(p);
			table.addCell(spanCell1);
			for (int i = 0; i < 41; i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}
		} else {
			// Байгууллага, нэгж
			p = new Paragraph("Байгууллага, нэгж", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			if (listExperience.size() > 7) {
				// for (EmployeeCardExperienceBean r : listExperience) {
				for (int i = 0; i < 7; i++) {
					if (listExperience.get(i).getOrganization() != null) {
						p = new Paragraph((listExperience.get(i)
								.getOrganization() != null) ? listExperience
								.get(i).getOrganization() : "", f);
						p.setAlignment(Element.ALIGN_CENTER);
						table.addCell(p);
					} else {
						p = new Paragraph(" ");
						table.addCell(p);
					}
				}
			} else {
				for (EmployeeCardExperienceBean r : listExperience) {
					if (r.getOrganization() != null) {
						p = new Paragraph(
								(r.getOrganization() != null) ? r.getOrganization()
										: "", f);
						p.setAlignment(Element.ALIGN_CENTER);
						table.addCell(p);
					} else {
						p = new Paragraph(" ");
						table.addCell(p);
					}
				}

				for (int i = 1; i < (8 - listExperience.size()); i++) {
					p = new Paragraph("");
					table.addCell(p);
				}
			}

			// Албан тушаал
			p = new Paragraph("Албан тушаал", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			if (listExperience.size() > 7) {
				for (int i = 0; i < 7; i++) {
					if (listExperience.get(i).getAppointment() != null) {
						p = new Paragraph((listExperience.get(i)
								.getAppointment() != null) ? listExperience
								.get(i).getAppointment() : "", f);
						p.setAlignment(Element.ALIGN_CENTER);
						table.addCell(p);
					} else {
						p = new Paragraph(" ");
						table.addCell(p);
					}
				}
			} else {
				for (EmployeeCardExperienceBean r : listExperience) {
					if (r.getAppointment() != null) {
						p = new Paragraph(
								(r.getAppointment() != null) ? r.getAppointment()
										: "", f);
						p.setAlignment(Element.ALIGN_CENTER);
						table.addCell(p);
					} else {
						p = new Paragraph(" ");
						table.addCell(p);
					}
				}

				for (int i = 1; i < (8 - listExperience.size()); i++) {
					p = new Paragraph("");
					table.addCell(p);
				}
			}

			// Томилсон /чөлөөлсөн/ огноо
			p = new Paragraph("Томилсон /чөлөөлсөн/ огноо", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			if (listExperience.size() > 7) {
				for (int i = 0; i < 7; i++) {
					if (listExperience.get(i).getIssuedDate() != null) {
						p = new Paragraph(getDateOfDemerit(listExperience
								.get(i).getIssuedDate()), f);
						p.setAlignment(Element.ALIGN_CENTER);
						table.addCell(p);
					} else {
						p = new Paragraph(" ");
						table.addCell(p);
					}
				}
			} else {
				for (EmployeeCardExperienceBean r : listExperience) {
					if (r.getIssuedDate() != null) {
						p = new Paragraph(getDateOfDemerit(r.getIssuedDate()),
								f);
						p.setAlignment(Element.ALIGN_CENTER);
						table.addCell(p);
					} else {
						p = new Paragraph(" ");
						table.addCell(p);
					}
				}

				for (int i = 1; i < (8 - listExperience.size()); i++) {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			// Шийдвэрийн дугаар, огноо
			p = new Paragraph("Шийдвэрийн огноо, дугаар", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			if (listExperience.size() > 7) {
				for (int i = 0; i < 7; i++) {
					if (listExperience.get(i).getCommandStartDate() != null) {
						p = new Paragraph(getDateOfDemerit(listExperience
								.get(i).getCommandStartDate())
								+ ", "
								+ listExperience.get(i).getCommandNumber(), f);
						p.setAlignment(Element.ALIGN_CENTER);
						table.addCell(p);
					} else {
						p = new Paragraph(" ");
						table.addCell(p);
					}
				}
			} else {
				for (EmployeeCardExperienceBean r : listExperience) {
					if (r.getCommandStartDate() != null) {
						p = new Paragraph(
								getDateOfDemerit(r.getCommandStartDate())
										+ ", " + r.getCommandNumber(), f);
						p.setAlignment(Element.ALIGN_CENTER);
						table.addCell(p);
					} else {
						p = new Paragraph(" ");
						table.addCell(p);
					}
				}

				for (int i = 1; i < (8 - listExperience.size()); i++) {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			// Шийдвэр гаргасан албан тушаалтан
			p = new Paragraph("Шийдвэр гаргасан албан тушаалтан", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			if (listExperience.size() > 7) {
				for (int i = 0; i < 7; i++) {
					if (listExperience.get(i).getCommandSubject() != null) {
						p = new Paragraph((listExperience.get(i)
								.getCommandSubject() != null) ? listExperience
								.get(i).getCommandSubject().getSubjectName()
								: "", f);
						p.setAlignment(Element.ALIGN_CENTER);
						table.addCell(p);
					} else {
						p = new Paragraph(" ");
						table.addCell(p);
					}
				}
			} else {
				for (EmployeeCardExperienceBean r : listExperience) {
					if (r.getCommandSubject() != null) {
						p = new Paragraph((r.getCommandSubject() != null) ? r
								.getCommandSubject().getSubjectName() : "", f);
						p.setAlignment(Element.ALIGN_CENTER);
						table.addCell(p);
					} else {
						p = new Paragraph(" ");
						table.addCell(p);
					}
				}

				for (int i = 1; i < (8 - listExperience.size()); i++) {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			if (listExperience.size() <= 7) {
				PdfPCell spanCell1 = new PdfPCell();
				spanCell1.setColspan(8);
				p = new Paragraph(" ");
				spanCell1.addElement(p);
				table.addCell(spanCell1);
				for (int i = 0; i < 41; i++) {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}
		}

		return table;
	}

	public PdfPTable getMilitaryDegree() throws DocumentException, IOException {
		BaseFont arno = BaseFont.createFont(fontType, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		Font f = new Font(arno, 8);
		Paragraph p = new Paragraph();
		PdfPTable table = new PdfPTable(8);
		PdfPCell cell = new PdfPCell();
		table.setWidthPercentage(100);
		// row3Table3.getDefaultCell().setBorderColor(new Color(255, 255, 255));

		p = new Paragraph("Цэргийн цол", new Font(arno, 9));
		p.setAlignment(Element.ALIGN_CENTER);
		cell = new PdfPCell();
		cell.setColspan(9);
		cell.addElement(p);
		table.addCell(cell);

		if (listDegrees.size() == 0) {
			for (int i = 0; i < 21; i++) {
				if (i == 0) {
					p = new Paragraph("Цол", f);
					p.setAlignment(Element.ALIGN_CENTER);
					cell = new PdfPCell();
					cell.setColspan(2);
					cell.addElement(p);
					table.addCell(cell);
				} else if (i == 7) {
					p = new Paragraph("Олгосон огноо", f);
					p.setAlignment(Element.ALIGN_CENTER);
					cell = new PdfPCell();
					cell.setColspan(2);
					cell.addElement(p);
					table.addCell(cell);
				} else if (i == 14) {
					p = new Paragraph("Тушаалын дугаар", f);
					p.setAlignment(Element.ALIGN_CENTER);
					cell = new PdfPCell();
					cell.setColspan(2);
					cell.addElement(p);
					table.addCell(cell);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}
			p = new Paragraph(" ", f);
			p.setAlignment(Element.ALIGN_CENTER);
			cell = new PdfPCell();
			cell.setColspan(9);
			cell.addElement(p);
			table.addCell(cell);
			for (int i = 0; i < 24; i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}
		} else {
			if (listDegrees.size() <= 6) {
				p = new Paragraph("Цол", f);
				p.setAlignment(Element.ALIGN_CENTER);
				cell = new PdfPCell();
				cell.setColspan(2);
				cell.addElement(p);
				table.addCell(cell);
				for (EmployeeCardDegreeBean r : listDegrees) {
					if (r.getDegree() != null) {
						String shortDeg = "";
						String[] tokens = r.getDegree().getMilitaryName()
								.split("\\s+");
						if (tokens.length >= 2) {
							for (int l = 0; l < tokens.length; l++) {
								if (l == (tokens.length - 1))
									shortDeg += tokens[l].charAt(0);
								else
									shortDeg += tokens[l].charAt(0) + "/";
							}
						} else
							shortDeg = r.getDegree().getMilitaryName();

						p = new Paragraph(shortDeg, f);
						p.setAlignment(Element.ALIGN_CENTER);
						table.addCell(p);
					} else {
						p = new Paragraph(" ");
						table.addCell(p);
					}
				}
				for (int i = 0; i < 6 - listDegrees.size(); i++) {
					p = new Paragraph(" ");
					table.addCell(p);
				}

				p = new Paragraph("Олгосон огноо", f);
				p.setAlignment(Element.ALIGN_CENTER);
				cell = new PdfPCell();
				cell.setColspan(2);
				cell.addElement(p);
				table.addCell(cell);
				for (EmployeeCardDegreeBean r : listDegrees) {
					if (r.getDegree() != null) {
						p = new Paragraph(getDateOfDemerit(r.getIssuedDate()),
								f);
						p.setAlignment(Element.ALIGN_CENTER);
						table.addCell(p);
					} else {
						p = new Paragraph(" ");
						table.addCell(p);
					}
				}
				for (int i = 0; i < 6 - listDegrees.size(); i++) {
					p = new Paragraph(" ");
					table.addCell(p);
				}

				p = new Paragraph("Тушаалын дугаар", f);
				p.setAlignment(Element.ALIGN_CENTER);
				cell = new PdfPCell();
				cell.setColspan(2);
				cell.addElement(p);
				table.addCell(cell);
				for (EmployeeCardDegreeBean r : listDegrees) {
					if (r.getDegree() != null) {
						p = new Paragraph(r.getCommandNumber(), f);
						p.setAlignment(Element.ALIGN_CENTER);
						table.addCell(p);
					} else {
						p = new Paragraph("");
						table.addCell(p);
					}
				}
				for (int i = 0; i < 6 - listDegrees.size(); i++) {
					p = new Paragraph(" ");
					table.addCell(p);
				}

				p = new Paragraph(" ", f);
				p.setAlignment(Element.ALIGN_CENTER);
				cell = new PdfPCell();
				cell.setColspan(9);
				cell.addElement(p);
				table.addCell(cell);
				for (int i = 0; i < 24; i++) {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			} else {
				for (int i = 0; i < 6; i++) {
					if (i == 0) {
						p = new Paragraph("Цол", f);
						p.setAlignment(Element.ALIGN_CENTER);
						cell = new PdfPCell();
						cell.setColspan(2);
						cell.addElement(p);
						table.addCell(cell);
					}
					if (listDegrees.get(i).getDegree().getMilitaryName() != null) {
						String shortDeg = "";
						String[] tokens = listDegrees.get(i).getDegree()
								.getMilitaryName().split("\\s+");
						if (tokens.length >= 2) {
							for (int l = 0; l < tokens.length; l++) {
								if (l == (tokens.length - 1))
									shortDeg += tokens[l].charAt(0);
								else
									shortDeg += tokens[l].charAt(0) + "/";
							}
						} else
							shortDeg = listDegrees.get(i).getDegree()
									.getMilitaryName();
						p = new Paragraph(shortDeg, f);
						p.setAlignment(Element.ALIGN_CENTER);
						table.addCell(p);
					} else {
						p = new Paragraph(" ");
						table.addCell(p);
					}
				}

				for (int i = 0; i < 6; i++) {
					if (i == 0) {
						p = new Paragraph("Олгосон огноо", f);
						p.setAlignment(Element.ALIGN_CENTER);
						cell = new PdfPCell();
						cell.setColspan(2);
						cell.addElement(p);
						table.addCell(cell);
					}
					if (listDegrees.get(i).getIssuedDate() != null) {
						p = new Paragraph(getDateOfDemerit(listDegrees.get(i)
								.getIssuedDate()), f);
						p.setAlignment(Element.ALIGN_CENTER);
						table.addCell(p);
					} else {
						p = new Paragraph(" ");
						table.addCell(p);
					}
				}

				for (int i = 0; i < 6; i++) {
					if (i == 0) {
						p = new Paragraph("Тушаалын дугаар", f);
						p.setAlignment(Element.ALIGN_CENTER);
						cell = new PdfPCell();
						cell.setColspan(2);
						cell.addElement(p);
						table.addCell(cell);
					}
					if (listDegrees.get(i).getCommandNumber() != null) {
						p = new Paragraph(
								listDegrees.get(i).getCommandNumber(), f);
						p.setAlignment(Element.ALIGN_CENTER);
						table.addCell(p);
					} else {
						p = new Paragraph(" ");
						table.addCell(p);
					}
				}
				p = new Paragraph(" ", f);
				p.setAlignment(Element.ALIGN_CENTER);
				cell = new PdfPCell();
				cell.setColspan(8);
				cell.addElement(p);
				table.addCell(cell);

				for (int i = 6; i < listDegrees.size(); i++) {
					if (listDegrees.get(i).getDegree().getMilitaryName() != null) {
						String shortDeg = "";
						String[] tokens = listDegrees.get(i).getDegree()
								.getMilitaryName().split("\\s+");
						if (tokens.length >= 2) {
							for (int l = 0; l < tokens.length; l++) {
								if (l == (tokens.length - 1))
									shortDeg += tokens[l].charAt(0);
								else
									shortDeg += tokens[l].charAt(0) + "/";
							}
						} else
							shortDeg = listDegrees.get(i).getDegree()
									.getMilitaryName();
						p = new Paragraph(shortDeg, f);
						p.setAlignment(Element.ALIGN_CENTER);
						table.addCell(p);
					} else {
						p = new Paragraph(" ");
						table.addCell(p);
					}
				}
				for (int i = 0; i < 8 - (listDegrees.size() - 6); i++) {
					p = new Paragraph(" ");
					table.addCell(p);
				}

				for (int i = 6; i < listDegrees.size(); i++) {
					if (listDegrees.get(i).getIssuedDate() != null) {
						p = new Paragraph(getDateOfDemerit(listDegrees.get(i)
								.getIssuedDate()), f);
						p.setAlignment(Element.ALIGN_CENTER);
						table.addCell(p);
					} else {
						p = new Paragraph(" ");
						table.addCell(p);
					}
				}
				for (int i = 0; i < 8 - (listDegrees.size() - 6); i++) {
					p = new Paragraph(" ");
					table.addCell(p);
				}

				for (int i = 6; i < listDegrees.size(); i++) {
					if (listDegrees.get(i).getCommandNumber() != null) {
						p = new Paragraph(
								listDegrees.get(i).getCommandNumber(), f);
						p.setAlignment(Element.ALIGN_CENTER);
						table.addCell(p);
					} else {
						p = new Paragraph(" ");
						table.addCell(p);
					}
				}
				for (int i = 0; i < 8 - (listDegrees.size() - 6); i++) {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}
		}

		return table;
	}

	public PdfPTable getAwardAndDemerit() throws DocumentException, IOException {
		Paragraph p = new Paragraph();
		PdfPTable table = new PdfPTable(9);
		table.setSpacingBefore(3);
		table.setWidthPercentage(100);
		BaseFont arno;

		arno = BaseFont.createFont(fontStyleItalic, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		Font f = new Font(arno, 8);

		// Төр засгийн шагнал
		PdfPCell col1 = new PdfPCell();
		col1.setRowspan(3);
		p = new Paragraph("Төр засгийн шагнал", f);
		p.setAlignment(Element.ALIGN_CENTER);
		col1.addElement(p);
		table.addCell(col1);

		if (listHonourState.size() == 0) {
			for (int i = 0; i < 24; i++) {
				if (i == 0) {
					p = new Paragraph("Шагналын нэр", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else if (i == 8) {
					p = new Paragraph("Шагнагдсан огноо", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else if (i == 16) {
					p = new Paragraph("Шийдвэрийн дугаар", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}
		} else {
			// Шагналын нэр
			p = new Paragraph("Шагналын нэр", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			for (EmployeeCardStateAwardBean r : listHonourState) {
				if (r.getAwardName() != null) {
					p = new Paragraph(
							(r.getAwardName() != null) ? r.getAwardName() : "",
							f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			for (int i = 0; i < (7 - listHonourState.size()); i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}

			// Шагнагдсан огноо
			p = new Paragraph("Шагнагдсан огноо", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			for (EmployeeCardStateAwardBean r : listHonourState) {
				if (r.getDateOfAward() != null) {
					p = new Paragraph(getDateOfDemerit(r.getDateOfAward()), f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			for (int i = 0; i < (7 - listHonourState.size()); i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}

			// Шагнагдсан огноо
			p = new Paragraph("Шийдвэрийн дугаар", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			for (EmployeeCardStateAwardBean r : listHonourState) {
				if (r.getCommandNumber() != null) {
					p = new Paragraph(
							(r.getCommandNumber() != null) ? r.getCommandNumber()
									: "", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			for (int i = 0; i < (7 - listHonourState.size()); i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}
		}

		// Бусад газрын шагнал
		PdfPCell col2 = new PdfPCell();
		col2.setRowspan(3);
		p = new Paragraph("Бусад шагнал", f);
		p.setAlignment(Element.ALIGN_CENTER);
		col2.addElement(p);
		table.addCell(col2);
		if (listHonourOther.size() == 0) {
			for (int i = 0; i < 24; i++) {
				if (i == 0) {
					p = new Paragraph("Шагналын нэр", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else if (i == 8) {
					p = new Paragraph("Шагнагдсан огноо", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else if (i == 16) {
					p = new Paragraph("Шийдвэрийн дугаар", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}
		} else {
			// Шагналын нэр
			p = new Paragraph("Шагналын нэр", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			for (EmployeeCardOtherAwardBean r : listHonourOther) {
				if (r.getAwardName() != null) {
					p = new Paragraph(
							(r.getAwardName() != null) ? r.getAwardName() : "",
							f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			for (int i = 0; i < (7 - listHonourOther.size()); i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}

			// Шагнагдсан огноо
			p = new Paragraph("Шагнагдсан огноо", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			for (EmployeeCardOtherAwardBean r : listHonourOther) {
				if (r.getDateOfAward() != null) {
					p = new Paragraph(getDateOfDemerit(r.getDateOfAward()), f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			for (int i = 0; i < (7 - listHonourOther.size()); i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}

			// Шагнагдсан огноо
			p = new Paragraph("Шийдвэрийн дугаар", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			for (EmployeeCardOtherAwardBean r : listHonourOther) {
				if (r.getCommandNumber() != null) {
					p = new Paragraph(
							(r.getCommandNumber() != null) ? r.getCommandNumber()
									: "", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			for (int i = 0; i < (7 - listHonourOther.size()); i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}
		}

		// Сахилга
		PdfPCell col3 = new PdfPCell();
		col3.setRowspan(3);
		p = new Paragraph("Сахилгын арга хэмжээ", f);
		p.setAlignment(Element.ALIGN_CENTER);
		col3.addElement(p);
		table.addCell(col3);
		if (listSahilga.size() == 0) {
			for (int i = 0; i < 32; i++) {
				if (i == 0) {
					p = new Paragraph("Үндэслэл", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else if (i == 8) {
					p = new Paragraph("Түүний төрөл", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else if (i == 16) {
					p = new Paragraph("Шийдвэрийн дугаар", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else if (i == 24) {
					PdfPCell col4 = new PdfPCell();
					col4.setColspan(2);
					p = new Paragraph("Арилгасан тушаал шийдвэрийн огноо, №", f);
					p.setAlignment(Element.ALIGN_CENTER);
					col4.addElement(p);
					table.addCell(col4);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}
		} else {
			// Үндэслэл
			p = new Paragraph("Үндэслэл", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			for (EmployeeCardDemeritBean r : listSahilga) {
				if (r.getCause() != null) {
					p = new Paragraph((r.getCause() != null) ? r.getCause()
							: "", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			for (int i = 0; i < (7 - listSahilga.size()); i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}

			// Түүний төрөл
			p = new Paragraph("Түүний төрөл", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			for (EmployeeCardDemeritBean r : listSahilga) {
				if (r.getDemeritType() != null) {
					p = new Paragraph((r.getDemeritType() != null) ? r
							.getDemeritType().getSahilgaTurulName() : "", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			for (int i = 0; i < (7 - listSahilga.size()); i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}

			// Шийдвэрийн дугаар
			p = new Paragraph("Шийдвэрийн дугаар", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			for (EmployeeCardDemeritBean r : listSahilga) {
				if (r.getAvagdsanShiitgelDugaar() != null) {
					p = new Paragraph(
							(r.getAvagdsanShiitgelDugaar() != null) ? r.getAvagdsanShiitgelDugaar()
									: "", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			for (int i = 0; i < (7 - listSahilga.size()); i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}

			// Шийдвэрийн дугаар
			PdfPCell col5 = new PdfPCell();
			col5.setColspan(2);
			p = new Paragraph("Арилгасан тушаал шийдвэрийн огноо, №", f);
			p.setAlignment(Element.ALIGN_CENTER);
			col5.addElement(p);
			table.addCell(col5);

			for (EmployeeCardDemeritBean r : listSahilga) {
				if (r.getArilgasanTushaalOgnoo() != null) {
					p = new Paragraph(
							(r.getArilgasanTushaalOgnoo() != null) ? getDateOfDemerit(r
									.getArilgasanTushaalOgnoo()) : "" + ", "
									+ r.getArilgasanTushaalDugaar(),
							f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			for (int i = 0; i < (7 - listSahilga.size()); i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}
		}

		return table;
	}

	public PdfPTable getEducation() throws DocumentException, IOException {
		Paragraph p = new Paragraph();
		PdfPTable table = new PdfPTable(8);
		table.setSpacingBefore(3);
		table.setWidthPercentage(100);
		BaseFont arno;

		arno = BaseFont.createFont(fontStyleItalic, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		Font f = new Font(arno, 8);

		if (listEducations.size() == 0) {
			for (int i = 0; i < 32; i++) {
				if (i == 0) {
					p = new Paragraph("Дүүргэсэн сургууль, дамжаа курс", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else if (i == 8) {
					p = new Paragraph("Хамрагдсан огноо", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else if (i == 16) {
					p = new Paragraph("Диплом, сертификатын №", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else if (i == 24) {
					p = new Paragraph("Зэрэг хамгаалсан сэдэв", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}
		} else {
			// Дүүргэсэн сургууль, дамжаа курс
			p = new Paragraph("Дүүргэсэн сургууль, дамжаа курс", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			for (EmployeeCardEducationBean r : listEducations) {
				if (r.getSchoolName() != null) {
					p = new Paragraph(
							(r.getSchoolName() != null) ? r.getSchoolName()
									: "", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}
			for (int i = 0; i < (7 - listEducations.size()); i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}

			// Хамрагдсан огноо;
			p = new Paragraph("Хамрагдсан огноо", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			for (EmployeeCardEducationBean r : listEducations) {
				if (r.getStartYear() != null) {
					p = new Paragraph(
							r.getStartYear() + " - " + r.getEndYear(), f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			for (int i = 0; i < (7 - listEducations.size()); i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}
			// Диплом, сертификатын №
			p = new Paragraph("Диплом, сертификатын №", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			for (EmployeeCardEducationBean r : listEducations) {
				if (r.getCertificateNo() != null) {
					p = new Paragraph(r.getCertificateNo(), f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			for (int i = 0; i < (7 - listEducations.size()); i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}
			// Зэрэг хамгаалсан сэдэв
			p = new Paragraph("Зэрэг хамгаалсан сэдэв", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			for (EmployeeCardEducationBean r : listEducations) {
				if (r.getDiplomaSubject() != null) {
					p = new Paragraph(r.getDiplomaSubject(), f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			for (int i = 0; i < (7 - listEducations.size()); i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}
		}

		return table;
	}

	public PdfPTable getSalary() throws DocumentException, IOException {
		Paragraph p = new Paragraph();
		PdfPTable table = new PdfPTable(8);
		table.setSpacingBefore(3);
		table.setWidthPercentage(100);
		BaseFont arno;

		arno = BaseFont.createFont(fontStyleItalic, BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		Font f = new Font(arno, 8);

		if (listSalary.size() == 0) {
			for (int i = 0; i < 96; i++) {
				if (i == 0) {
					p = new Paragraph("Олгож эхэлсэн огноо", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else if (i == 8) {
					p = new Paragraph("Анги, алба", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else if (i == 16) {
					p = new Paragraph("Албан тушаал", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else if (i == 24) {
					p = new Paragraph("Зэрэглэл, шатлал", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else if (i == 32) {
					p = new Paragraph("Үндсэн цалин", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else if (i == 40) {
					p = new Paragraph("ТТАлбаны нэмэгдэл %", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else if (i == 48) {
					p = new Paragraph("ТАлбаны нэмэгдэл %", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else if (i == 56) {
					p = new Paragraph("ГА нэмэгдэл %", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else if (i == 64) {
					p = new Paragraph("Бусад нэмэгдэл %", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else if (i == 72) {
					p = new Paragraph("Тогтоосон огноо", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else if (i == 80) {
					p = new Paragraph("Тогтоосон цалин хөлсний мэргэжилтэн", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else if (i == 88) {
					p = new Paragraph("Хянасан СҮБТЗГ-ын дарга", f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph("");
					table.addCell(p);
				}
			}
		} else {
			// Зэрэг хамгаалсан сэдэв
			p = new Paragraph("Олгож эхэлсэн огноо", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			for (EmployeeCardSalaryBean r : listSalary) {
				if (r.getOlgosonOgnoo() != null) {
					p = new Paragraph(getDateOfDemerit(r.getOlgosonOgnoo()), f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			for (int i = 0; i < (8 - listSalary.size()); i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}

			// Анги, алба
			p = new Paragraph("Анги, алба", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			for (EmployeeCardSalaryBean r : listSalary) {
				if (r.getEmployee().getOrganization() != null) {
					p = new Paragraph((r.getEmployee().getOrganization()
							.getShortName() != null) ? r.getEmployee()
							.getOrganization().getShortName() : r.getEmployee()
							.getOrganization().getName(), f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			for (int i = 0; i < (8 - listSalary.size()); i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}

			// Албан тушаал
			p = new Paragraph("Албан тушаал", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			for (EmployeeCardSalaryBean r : listSalary) {
				if (r.getEmployee().getAppointment() != null) {
					p = new Paragraph((r.getEmployee().getAppointment()
							.getAppointmentName() != null) ? r.getEmployee()
							.getAppointment().getAppointmentName() : null, f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			for (int i = 0; i < (8 - listSalary.size()); i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}

			// Албан тушаал
			p = new Paragraph("Зэрэглэл, шатлал", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			for (EmployeeCardSalaryBean r : listSalary) {
				if (r.getLevel() != null) {
					p = new Paragraph((r.getLevel() != null) ? r.getLevel()
							.getUtTzTtTuSort().getName()
							+ "-" + r.getLevel().getUtTzTtTuRank().getRank()
							: null, f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			for (int i = 0; i < (8 - listSalary.size()); i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}

			// Үндсэн цалин
			p = new Paragraph("Үндсэн цалин", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			for (EmployeeCardSalaryBean r : listSalary) {
				if (r.getUndsenTsalin() != null) {
					p = new Paragraph(r.getUndsenTsalin(), f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			for (int i = 0; i < (8 - listSalary.size()); i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}

			// ТТАлбаны нэмэгдэл %
			p = new Paragraph("ТТАлбаны нэмэгдэл %", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			for (EmployeeCardSalaryBean r : listSalary) {
				if (r.getUndsenTsalin() != null) {
					p = new Paragraph(r.getUndsenTsalin(), f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			for (int i = 0; i < (8 - listSalary.size()); i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}

			// ТАлбаны нэмэгдэл %
			p = new Paragraph("ТАлбаны нэмэгдэл %", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			for (EmployeeCardSalaryBean r : listSalary) {
				if (r.getUndsenTsalin() != null) {
					p = new Paragraph(r.getUndsenTsalin(), f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			for (int i = 0; i < (8 - listSalary.size()); i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}

			// ГА нэмэгдэл %
			p = new Paragraph("ГА нэмэгдэл %", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			for (EmployeeCardSalaryBean r : listSalary) {
				if (r.getUndsenTsalin() != null) {
					p = new Paragraph(r.getUndsenTsalin(), f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			for (int i = 0; i < (8 - listSalary.size()); i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}

			// Бусад нэмэгдэл %
			p = new Paragraph("Бусад нэмэгдэл %", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			for (EmployeeCardSalaryBean r : listSalary) {
				if (r.getUndsenTsalin() != null) {
					p = new Paragraph(r.getUndsenTsalin(), f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			for (int i = 0; i < (8 - listSalary.size()); i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}

			// Тогтоосон огноо
			p = new Paragraph("Тогтоосон огноо", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			for (EmployeeCardSalaryBean r : listSalary) {
				if (r.getUndsenTsalin() != null) {
					p = new Paragraph(r.getUndsenTsalin(), f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			for (int i = 0; i < (8 - listSalary.size()); i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}

			// Тогтоосон цалин хөлсний мэргэжилтэн
			p = new Paragraph("Тогтоосон цалин хөлсний мэргэжилтэн", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			for (EmployeeCardSalaryBean r : listSalary) {
				if (r.getUndsenTsalin() != null) {
					p = new Paragraph(r.getUndsenTsalin(), f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			for (int i = 0; i < (8 - listSalary.size()); i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}

			// Хянасан СҮБТЗГ-ын дарга
			p = new Paragraph("Хянасан СҮБТЗГ-ын дарга", f);
			p.setAlignment(Element.ALIGN_CENTER);
			table.addCell(p);

			for (EmployeeCardSalaryBean r : listSalary) {
				if (r.getUndsenTsalin() != null) {
					p = new Paragraph(r.getUndsenTsalin(), f);
					p.setAlignment(Element.ALIGN_CENTER);
					table.addCell(p);
				} else {
					p = new Paragraph(" ");
					table.addCell(p);
				}
			}

			for (int i = 0; i < (8 - listSalary.size()); i++) {
				p = new Paragraph(" ");
				table.addCell(p);
			}
		}
		return table;
	}
}
