package mn.mcs.electronics.court.pages.excel;

import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.EnumSelectModel;

public class PageImportExcel {
	
	public enum ImportFileType{
		PERSONALINFO,
		FAMILY,
		EDUCATION,
		TRAINING,
		DEGREE,
		SKILL,
		LANGUAGE,
		EXPERIENCE,
		DISPLACEMENT;
	}
	
	@Inject
	private Messages messages;
	
	@InjectPage
	private PagePersonalInfo pagePersonalInfo;
	
	@InjectPage
	private PageLanguage pageLanguage;
	
	@InjectPage
	private PageEmployeeGradeExcel pageEmployeeGradeExcel;
	
	@InjectPage
	private PageEmployeeTrainingExcel pageEmployeeTrainingExcel;
	
	@InjectPage
	private PageEmployeeDisplacementExcel pageEmployeeDisplacementExcel;
	
	@InjectPage
	private PageEmployeeFamilyRelativesExcel pageEmployeeFamilyRelativesExcel;
	
	@InjectComponent
	private Zone importZone;
	
	@Inject
	private Request request;
	
	@Persist
	@Property
	private ImportFileType valueFileType;

	
	/*
	 * Импорт хийх файлаа сонгоод сонгосон
	 *  хуудаснаас шалтгаалан өөр өөр хуудас луу үсэрнэ.
	 */ 
	
	
	void beginRender(){
		if(valueFileType == null)
			valueFileType = ImportFileType.PERSONALINFO;
	}
	
	Object onActionFromGoImportPage(){
			
		Object page = null;
		System.err.println("valueType:"+valueFileType);
		if(valueFileType == ImportFileType.PERSONALINFO)
			page = pagePersonalInfo;
		if(valueFileType == ImportFileType.DEGREE)
			page = pageEmployeeGradeExcel;
		if(valueFileType == ImportFileType.DISPLACEMENT)
			page = pageEmployeeDisplacementExcel;
		if(valueFileType == ImportFileType.LANGUAGE)
			page = pageLanguage;
		
		if(valueFileType == ImportFileType.FAMILY)
			page = pageEmployeeFamilyRelativesExcel;

		return page;
	}
	
	Object onValueChangedFromTypeClick() {
		return request.isXHR() ? importZone.getBody() : null;
	}

	public SelectModel getImportFileTypeSelectionModel(){
		return new EnumSelectModel(ImportFileType.class,messages);
	}
}
