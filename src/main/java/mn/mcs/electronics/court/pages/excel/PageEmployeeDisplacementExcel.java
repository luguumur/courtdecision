package mn.mcs.electronics.court.pages.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.Displacement;
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

public class PageEmployeeDisplacementExcel {
	
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
	private Displacement displacement;
	
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
	
	@CommitAfter
	void onSelectedFromImportFile() throws IOException{
		
		InputStream fis = null;
		
		importUtil = new ImportUtil();
		
		try{
			String[] arr = fileEx.getFileName().split("\\.");
				if (arr[arr.length - 1].equals(excelFormat)){
					fis = fileEx.getStream();
	
					HSSFWorkbook wb = new HSSFWorkbook(fis);
	
					HSSFSheet sheet = wb.getSheetAt(0);
	
					Object obj = null;
	
					Iterator rows = sheet.rowIterator();
	
					List<Employee> empData = new ArrayList<Employee>();
					
					List<Displacement> disData = new ArrayList<Displacement>();
					
					empRegister = new ArrayList<String>();
	
					errMsg = new ArrayList<String>();
					
					while(rows.hasNext()){
						emp= new Employee();
						
						displacement= new Displacement();
						
						HSSFRow row = (HSSFRow) rows.next();
	
						Iterator cells = row.cellIterator();
						
						if (row.getRowNum() > 1 && row.getRowNum() < 300){
							while (cells.hasNext()) {
								HSSFCell cell = (HSSFCell) cells.next();
								int cellIndex = cell.getCellNum();
								obj = ExcelAPI.getCellValue(cell);
								if (obj != null && cellIndex != 0){
									switch (cellIndex) {
									case 1:
										if (obj.getClass().getSimpleName().equals("String")) 
										{
											displacement.setEmployee(getByRegistration(obj));
											emp = getByRegistration(obj);
											break;
										}
										//else
											//errMsg="";
									case 2:
									
									default:
										break;
									}
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
