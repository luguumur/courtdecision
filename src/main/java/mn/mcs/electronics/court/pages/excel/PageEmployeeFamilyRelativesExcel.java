package mn.mcs.electronics.court.pages.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.Relatives;
import mn.mcs.electronics.court.entities.configuration.RelativeType;
import mn.mcs.electronics.court.entities.employee.Employee;
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
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;

public class PageEmployeeFamilyRelativesExcel {

	@Inject
	private CoreDAO dao;
	
	private static final String excelFormat = "xls";
	
	@Property
	@Validate("required")
	private UploadedFile fileEx;
	
	@Property
	@Persist
	private List<String> empRegister;
	
	@Property
	@Persist
	private Employee emp;
		
	@Property
	@Persist
	private RelativeType rel_type;
	
	@Property
	@Persist
	private Relatives relatives;
	
	@Property
	@Persist
	private List<String> errMsg;
	
	@Property
	@Persist("flash")
	private String msgFileError;
	
	private ImportUtil importUtil;
	
	private Employee getByRegistration(Object obj) {
		String s = (String) obj;
		//Employee emp = dao.getEmpByRegister(s);

		return emp;
	}
	
	private int getRelationTypeNumber(String relativeTypeName){
		int relativeTypeNumber = 1;
		return relativeTypeNumber;
	}
	
	@CommitAfter
	void onSelectedFromImportFile() throws IOException{
        
		InputStream fis = null;
		
		importUtil = new ImportUtil();
		
		try{
			String[] arr = fileEx.getFileName().split("\\.");
				if (arr[arr.length - 1].equals(excelFormat)){					
					fis = fileEx.getStream();

					HSSFWorkbook wb = new HSSFWorkbook(fis);
					
					System.out.print("File format-" + wb+'\n');/**/
	
					HSSFSheet sheet = wb.getSheetAt(0);
	
					Object obj = null;
	
					Iterator rows = sheet.rowIterator();
	
					List<Employee> empData = new ArrayList<Employee>();
					
					List<Relatives> relData = new ArrayList<Relatives>();
					
					empRegister = new ArrayList<String>();				
					
					errMsg = new ArrayList<String>();
					
					while(rows.hasNext()){
						emp= new Employee();
						
						relatives= new Relatives();
						
						HSSFRow row = (HSSFRow) rows.next();
	
						Iterator cells = row.cellIterator();						
						
						if (row.getRowNum() > 1){
							System.out.print("Row number - " + row.getRowNum()+'\n');/**/
							
							while (cells.hasNext()) {
								HSSFCell cell = (HSSFCell) cells.next();
								int cellIndex = cell.getCellNum();
								obj = ExcelAPI.getCellValue(cell);
																
								if (obj != null && cellIndex != 0){
									switch (cellIndex) {
									case 1:										
										if (obj.getClass().getSimpleName().equals("String")) 
										{
											emp.setRegisterNo(obj.toString());
											System.out.print("Case 1-Register - " + emp.getRegisterNo() + '\n');/**/
											break;
										}
									case 2:
										if (obj.getClass().getSimpleName().equals("String")) 
										{	
											int relTypeNumber = getRelationTypeNumber(obj.toString());
											//relatives.setRelation();
											System.out.print("Case 2-" + emp.getRegisterNo() + '\n');/**/
											break;
										}
									case 3:
										if (obj.getClass().getSimpleName().equals("String")) 
										{										
											relatives.setLastName(obj.toString());
											System.out.print("Case 3-LastName" + relatives.getLastName() + '\n');/**/
											break;
										}
									case 4:
										if (obj.getClass().getSimpleName().equals("String")) 
										{										
											relatives.setFirstName(obj.toString());
											System.out.print("Case 4-FirstName" + relatives.getFirstName() + '\n');/**/
											break;
										}
									case 5:
										if (obj.getClass().getSimpleName().equals("String")) 
										{										
											relatives.setOccupation(obj.toString());
											System.out.print("Case 5-Occupation" + emp.getOccupation() + '\n');/**/
											break;
										}
									case 6:
										if (obj.getClass().getSimpleName().equals("Date")) 
										{								
											//relatives.setBirthDate(obj.toString());
											System.out.print("Case 6-Birth date" + obj.toString() + '\n');/**/
											break;
										}
									default:
										break;
									}
									relatives.setEmployee(emp);
									dao.saveOrUpdateObject(relatives);
									relatives = new Relatives();
								}
						}
					}
				}
			}
			else
				msgFileError="Excel файл оруулна уу";
	}
		catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				fis.close();
			}

		}
	}
}
