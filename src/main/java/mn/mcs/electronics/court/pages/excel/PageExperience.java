package mn.mcs.electronics.court.pages.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.employee.Language;
import mn.mcs.electronics.court.enums.LanguageLevel;
import mn.mcs.electronics.court.util.ExcelAPI;
import mn.mcs.electronics.court.util.ImportUtil;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.hibernate.Session;

public class PageExperience {
	
	@Property
	@Validate("required")
	private UploadedFile fileEx;
		
	private static final String excelFormat = "xls";
	
	@Inject
	private CoreDAO dao;
	
	@Inject
	private Messages messages;
	
	@Inject
	private Session session;
	
	@Property
	@Persist("flash")
	private String msgFileError;
	
	@Property
	@Persist("flash")
	private List<String> errMsg;
	
	@CommitAfter
	void onSelectedFromImportFile() throws Exception {
		
		InputStream fis = null;

		ImportUtil importUtil = new ImportUtil();

		Language lang = null;

		LanguageLevel level = null;

		Employee emp = null;

		Set<Language> langs = null;

		String prevRegId = "";

		int notFoundEmpCount = 0;

		int savedEmpCount = 0;

		try {
			System.err.println(fileEx.getFileName());

			fis = fileEx.getStream();

			String[] arr = fileEx.getFileName().split("\\.");

			if (arr[arr.length - 1].equals(excelFormat)) {

				HSSFWorkbook wb = new HSSFWorkbook(fis);

				HSSFSheet sheet = wb.getSheetAt(0);

				Object obj = null;

				Iterator rows = sheet.rowIterator();
				langs = new HashSet<Language>();
								
				while (rows.hasNext()) {

					HSSFRow row = (HSSFRow) rows.next();
					Iterator cells = row.cellIterator();
					boolean isAdd = false;

					if (row.getRowNum() > 0) {
						while (cells.hasNext()) {
							HSSFCell cell = (HSSFCell) cells.next();
							int cellIndex = cell.getCellNum();
							obj = ExcelAPI.getCellValue(cell);		
							
							if (obj != null) {
															
								switch (cellIndex) {

								case 1:
									if (obj.getClass().getSimpleName().equals(
											"String")) {

							    		if (!prevRegId.equals((String) obj)) {

											prevRegId = (String) obj;
											emp = dao.getEmpByRegId(prevRegId);
										}

										if (emp == null) {
											importUtil.regErrMsg(
													"Unknown register id", row
															.getRowNum(),
													cellIndex);

											System.err.println("Employee not found: "
															+ (String) obj);

											notFoundEmpCount++; 
										} else {
																	
											isAdd=true;
											 System.err.println("Employee found: "
											 + (String) obj + " -> "
											 + emp.getId()); 
										}
									} else {
										importUtil
												.regErrMsg(
														"Unknown register id",
														row.getRowNum(),
														cellIndex);
									}

									break;

								case 2:
									if (obj.getClass().getSimpleName().equals(
											"String")
											&& isAdd) {
										lang = dao.getLanguage(emp,
												(String) obj);
										
										if (lang == null)
											lang=new Language();
											
											lang.setEmployee(emp);
											//lang.setForeignLanguage((ForiegnLanguage) obj);
																				
									} else {
										importUtil.regErrMsg("languageName",
												row.getRowNum(), cellIndex);
									}

									break;

								case 3:
									if (obj.getClass().getSimpleName().equals(
											"String")
											&& isAdd) {
										level = this.getLangLevel((String) obj);

										lang.setListening(level);
									}

									break;

								case 4:
									if (obj.getClass().getSimpleName().equals(
											"String")
											&& isAdd) {
										level = this.getLangLevel((String) obj);

										lang.setSpeaking(level);
									}

									break;

								case 5:
									if (obj.getClass().getSimpleName().equals(
											"String")
											&& isAdd) {
										level = this.getLangLevel((String) obj);

										lang.setReading(level);
									}

									break;

								case 6:
									if (obj.getClass().getSimpleName().equals(
											"String")
											&& isAdd) {
										level = this.getLangLevel((String) obj);

										lang.setWriting(level);
									}

									break;
								}
							}
						}
						if(isAdd){
							
							session.saveOrUpdate(lang);
							System.err.println("amjilttai hadgalagdlaa");
						}
					}
				}
				
				//System.err.println(langs.size()+" is lang size");
				/*for (Language _lang : langs) 
					session.saveOrUpdate(_lang);*/
				
				/* Jun 11, 2011 У.Наранхүү Start */
				savedEmpCount = langs.size();

				/* Jun 11, 2011 У.Наранхүү End */

			} else
				msgFileError = "Excel файл оруулна уу.";
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				fis.close();
			}

		}

		errMsg = importUtil.getErrMsgs();

		// for (String err : errMsg) {
		// System.err.println(err);
		// }
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
