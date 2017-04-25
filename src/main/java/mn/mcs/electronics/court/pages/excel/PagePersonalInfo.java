package mn.mcs.electronics.court.pages.excel;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.upload.services.UploadedFile;

public class PagePersonalInfo {
	
	@Property
	@Validate("required")
	private UploadedFile fileEx;
	
	@CommitAfter
	void onSelectedFromImportFile(){
		
	}
}
