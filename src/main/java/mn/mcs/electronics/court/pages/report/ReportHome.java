package mn.mcs.electronics.court.pages.report;

import java.util.ArrayList;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

public class ReportHome {
	
	@Inject 
	private CoreDAO dao;
	
	@SessionState
	private LoginState loginState;
	
	@InjectPage
	private PageEducationReport pageEducationReport;
	
	@InjectPage
	private PageGenderReport pageGenderReport;
	
	@InjectPage
	private PageAgeReport pageAgeReport;
	
	@InjectPage
	private PageSalaryReport pageSalaryReport;
	
	@InjectPage
	private PagePromoteReport pagePromoteReport;
	
	@InjectPage
	private PageTrainingReport pageTrainingReport;
	
	@InjectPage
	private PageDegreeGradeReport pageDegreeGradeReport;
	
	@InjectPage
	private PageWorkedYearReport pageWorkedYearReport;
	
	@InjectPage
	private PageQuitJobReport pageQuitJobReport;
	
	@InjectPage
	private PageRetiredEmployeeReport pageRetiredEmployeeReport;
	
	@InjectPage
	private PageRetireSoonReport pageRetireSoonReport;
	
	@InjectPage
	private PageGeneralJudgeReport pageGeneralJudgeReport;
	
	@InjectPage
	private PageHonourReport pageHonourReport;
	
	@InjectPage
	private PagePromotionReport pagePromotionReport;
	
	@InjectPage
	private PageMovementReport pageMovementReport;
	
	@InjectPage
	private PageDisciplineReport pageDisciplineReport;
	
	@Persist
	public static ArrayList<Object> breadCrumb;
	
	@Persist
	public static ArrayList<Object> breadCrumbCell;
	
	void beginRender(){
		
		/*LoginState.breadCrumb = new ArrayList<Object>();
		
		LoginState.breadCrumbCell = new ArrayList<Object>(2);
		LoginState.breadCrumbCell.add("Тайлан");
		LoginState.breadCrumbCell.add("report/reportHome");
		
		LoginState.breadCrumb.add(LoginState.breadCrumbCell);*/
	}
	
	Object onActionFromGoEducationReport(){
	
		return pageEducationReport;
	}
	
	Object onActionFromGoWorkedYearReport(){
		return pageWorkedYearReport;
	} 
	
	Object onActionFromGoGenderReport(){
		return pageGenderReport;
	}
	
	Object onActionFromGoAgeReport(){
		return pageAgeReport;
	}
	
	Object onActionFromGoSalaryReport(){
		return pageSalaryReport;
	}
	
	/*Object onActionFromGoCandidateReport(){
		return pageCandidateReport;
	}*/
	
	Object onActionFromGoPromoteReport(){
		return pagePromoteReport;
	}
	
	Object onActionFromGoDegreeGradeReport(){
		return pageDegreeGradeReport;
	}
	
	Object onActionFromGoTrainingReport(){
		return pageTrainingReport;
	}
	
	Object onActionFromGoQuitReport(){
		return pageQuitJobReport;
	}
	
	Object onActionFromGoRetiredEmployeeReport(){
		return pageRetiredEmployeeReport;
	}
	
	Object onActionFromGoRetireSoonReport(){
		return pageRetireSoonReport;
	}
	
	/*Object onActionFromGoGeneralJudgeReport(){
		return pageGeneralJudgeReport;
	}*/
		Object onActionFromGoPromotionReport(){
		return pagePromotionReport;
	}
	
	Object onActionFromGoMovementReport(){
		return pageMovementReport;
	}
	
	Object onActionFromGoHonourReport(){
		return pageHonourReport;
	}
	
	Object onActionFromGoDisciplineReport(){
		return pageDisciplineReport;
	}
}
