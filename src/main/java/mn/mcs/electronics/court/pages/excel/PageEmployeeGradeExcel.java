package mn.mcs.electronics.court.pages.excel;

import java.io.InputStream;

import mn.mcs.electronics.court.util.ImportUtil;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.upload.services.UploadedFile;



public class PageEmployeeGradeExcel {
	
	@Property
	@Validate("required")
	private UploadedFile fileEx;
	
	private ImportUtil importUtil;
	
	private Boolean isAdd;
	
	@CommitAfter
	void onSelectedFromImportFile(){
		
		InputStream fis = null;
		
		importUtil = new ImportUtil();
		
		int savedHN = 0;

		
	}
}
