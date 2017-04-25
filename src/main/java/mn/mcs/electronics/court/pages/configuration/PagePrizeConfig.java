package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.Award;
import mn.mcs.electronics.court.entities.employee.SahilgaShiitgel;
import mn.mcs.electronics.court.entities.employee.SahilgaTurul;
import mn.mcs.electronics.court.enums.AwardType;

import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

public class PagePrizeConfig {

	/*
	 * INJECTS
	 */

	@Inject
	private CoreDAO dao;

	@Inject
	private Messages messages;

	@SessionState
	private LoginState loginState;

	@Inject
	private Request request;

	@Inject
	private AlertManager manager;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@InjectComponent
	private Zone statePrizeFormZone, statePrizeListZone,
			governmentPrizeFormZone, governmentPrizeListZone,
			justiceMinistryPrizeFormZone, justiceMinistryPrizeListZone,
			courtPrizeListZone, courtPrizeFormZone, sahilgaTurulFormZone,
			sahilgaTurulListZone, sahilgaShiitgelFormZone,sahilgaShiitgelListZone;

	/*
	 * PERSISTS
	 */
	@Property
	@Persist
	private Award statePrize, governmentPrize, justiceMinistryPrize, courtPrize;

	@Property
	@Persist
	private List<Award> listCourtPrize, listJusticeMinistryPrize, listGovernmentPrize, listStatePrize;
	
	@Property
	@Persist
	private SahilgaTurul sahilgaTurul;
	
	@Property
	private List<SahilgaTurul> listSahilgaTurul;
	
	@Property
	@Persist
	private SahilgaShiitgel sahilgaShiitgel;
	
	@Property
	private List<SahilgaShiitgel> listSahilgaShiitgel;
	/*
	 * PROPERTIES
	 */

	@Property
	private Award valueStatePrize, valueGovernmentPrize, valueJusticeMinistryPrize, valueCourtPrize;	
	
	@Property
	private SahilgaTurul valueSahilgaTurul;
	
	@Property
	private SahilgaShiitgel valueSahilgaShiitgel;
	
	void beginRender() {		
		if (statePrize == null)
			statePrize = new Award();
		
		if (governmentPrize == null)
			governmentPrize = new Award();
		
		if (justiceMinistryPrize == null)
			justiceMinistryPrize = new Award();
		
		if (courtPrize == null)
			courtPrize = new Award();
		
		if(sahilgaTurul ==null)
			sahilgaTurul = new SahilgaTurul();
		
		if(sahilgaShiitgel ==null)
			sahilgaShiitgel = new SahilgaShiitgel();
		
		initGridStatePrize();
		initGridGovernmentPrize();
		initGridJusticeMinistryPrize();
		initGridCourtPrize();
		initGridSahilgaTurul();
		initGridSahilgaShiitgel();
	}

	void initGridStatePrize() {
		listStatePrize = dao.getListAward(AwardType.STATEPRIZE);
	}

	void initGridGovernmentPrize() {
		listGovernmentPrize = dao.getListAward(AwardType.GOVERNMENTPRIZE);
	}

	void initGridJusticeMinistryPrize() {
		listJusticeMinistryPrize = dao.getListAward(AwardType.JUSTICE_MINISTRYPRIZE);
	}

	void initGridCourtPrize() {
		listCourtPrize = dao.getListAward(AwardType.COURTPRIZE);
	}

	void initGridSahilgaTurul(){
		listSahilgaTurul = dao.getListSahilgaTurul();	
	}
	
	void initGridSahilgaShiitgel(){
		listSahilgaShiitgel = dao.getListSahilgaShiitgel();	
	}
	/*
	 * EVENTS
	 */

	@CommitAfter
	void onSelectedFromSave() {
		if (request.isXHR()) {
			statePrize.setAwardType(AwardType.STATEPRIZE);
			dao.saveOrUpdateObject(statePrize);
			statePrize = new Award();
			initGridStatePrize();
			ajaxResponseRenderer.addRender(statePrizeFormZone).addRender(
					statePrizeListZone);

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));
		}
	}

	void onActionFromEdit(Award statePrize) {
		if (request.isXHR()) {
			this.statePrize = statePrize;
			ajaxResponseRenderer.addRender(statePrizeFormZone);
		}
	}

	@CommitAfter
	void onActionFromDelete(Award statePrize) {
//		if (request.isXHR()) {
			dao.deleteObject(statePrize);
			initGridStatePrize();
//			ajaxResponseRenderer.addRender(statePrizeListZone);

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successDeleteMessage"));
//		}
	}

	@CommitAfter
	void onSelectedFromSaveGovernment() {
		if (request.isXHR()) {
			governmentPrize.setAwardType(AwardType.GOVERNMENTPRIZE);
			dao.saveOrUpdateObject(governmentPrize);
			governmentPrize = new Award();
			initGridGovernmentPrize();
			ajaxResponseRenderer.addRender(governmentPrizeFormZone).addRender(
					governmentPrizeListZone);

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));
		}
	}

	void onActionFromEditGovernment(Award governmentPrize) {
		if (request.isXHR()) {
			this.governmentPrize = governmentPrize;
			ajaxResponseRenderer.addRender(governmentPrizeFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteGovernment(Award governmentPrize) {
//		if (request.isXHR()) {
			dao.deleteObject(governmentPrize);
			initGridGovernmentPrize();
//
//			ajaxResponseRenderer.addRender(governmentPrizeListZone);

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));
//		}
	}

	@CommitAfter
	void onSelectedFromSaveJusticeMinistry() {
		if (request.isXHR()) {
			justiceMinistryPrize.setAwardType(AwardType.JUSTICE_MINISTRYPRIZE);
			dao.saveOrUpdateObject(justiceMinistryPrize);
			justiceMinistryPrize = new Award();
			initGridJusticeMinistryPrize();
			
			ajaxResponseRenderer.addRender(justiceMinistryPrizeFormZone)
					.addRender(justiceMinistryPrizeListZone);

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));
		}
	}

	void onActionFromEditJusticeMinistry(
			Award justiceMinistryPrize) {
		if (request.isXHR()) {
			this.justiceMinistryPrize = justiceMinistryPrize;
			ajaxResponseRenderer.addRender(justiceMinistryPrizeFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteJusticeMinistry(
			Award justiceMinistryPrize) {
//		if (request.isXHR()) {
			dao.deleteObject(justiceMinistryPrize);
			initGridJusticeMinistryPrize();

//			ajaxResponseRenderer.addRender(justiceMinistryPrizeListZone);
			
			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successDeleteMessage"));
//		}
	}

	@CommitAfter
	void onSelectedFromSaveCourt() {
		if (request.isXHR()) {
			courtPrize.setAwardType(AwardType.COURTPRIZE);
			dao.saveOrUpdateObject(courtPrize);
			courtPrize = new Award();
			initGridCourtPrize();

			ajaxResponseRenderer.addRender(courtPrizeFormZone).addRender(
					courtPrizeListZone);

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));
		}
	}

	void onActionFromEditCourt(Award courtPrize) {
		if (request.isXHR()) {
			this.courtPrize = courtPrize;
			ajaxResponseRenderer.addRender(courtPrizeFormZone);
		}
	}

	@CommitAfter
	void onActionFromDeleteCourt(Award courtPrize) {
//		if (request.isXHR()) {
			dao.deleteObject(courtPrize);
			initGridCourtPrize();
//			ajaxResponseRenderer.addRender(courtPrizeListZone);

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successDeleteMessage"));
//		}
	}

	/**Sahilga Turul**/
	@CommitAfter
	void onSelectedFromSaveSahilgaTurul() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(sahilgaTurul);
			sahilgaTurul = new SahilgaTurul();
			initGridSahilgaTurul();

			ajaxResponseRenderer.addRender(sahilgaTurulFormZone).addRender(
					sahilgaTurulListZone);

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));
		}
	}
	
	void onActionFromEditSahilgaTurul(SahilgaTurul sahilgaTurul) {
		if (request.isXHR()) {
			this.sahilgaTurul = sahilgaTurul;
			ajaxResponseRenderer.addRender(sahilgaTurulFormZone);
		}
	}
	
	@CommitAfter
	void onActionFromDeleteSahilgaTurul(SahilgaTurul sahilgaTurul) {
//		if (request.isXHR()) {
			dao.deleteObject(sahilgaTurul);
			initGridSahilgaTurul();
//			ajaxResponseRenderer.addRender(sahilgaTurulListZone);

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successDeleteMessage"));
//		}
	}

	/**Sahilga Shiitgel**/
	@CommitAfter
	void onSelectedFromSaveSahilgaShiitgel() {
		if (request.isXHR()) {
			dao.saveOrUpdateObject(sahilgaShiitgel);
			sahilgaShiitgel = new SahilgaShiitgel();
			initGridSahilgaShiitgel();

			ajaxResponseRenderer.addRender(sahilgaShiitgelFormZone).addRender(
					sahilgaShiitgelListZone);

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successMessage"));
		}
	}
	
	void onActionFromEditSahilgaShiitgel(SahilgaShiitgel sahilgaShiitgel) {
		if (request.isXHR()) {
			this.sahilgaShiitgel = sahilgaShiitgel;
			ajaxResponseRenderer.addRender(sahilgaShiitgelFormZone);
		}
	}
	
	@CommitAfter
	void onActionFromDeleteSahilgaShiitgel(SahilgaShiitgel sahilgaShiitgel) {
//		if (request.isXHR()) {
			dao.deleteObject(sahilgaShiitgel);
			initGridSahilgaShiitgel();
//			ajaxResponseRenderer.addRender(sahilgaShiitgelListZone);

			manager.alert(Duration.SINGLE, Severity.INFO,
					messages.get("successDeleteMessage"));
//		}
	}
	/*
	 * METHODS
	 */

	public String getNumber() {
		return listStatePrize.indexOf(valueStatePrize) + 1 + "";
	}

	public String getNumberGov() {
		return listGovernmentPrize.indexOf(valueGovernmentPrize) + 1 + "";
	}

	public String getNumberJus() {
		return listJusticeMinistryPrize.indexOf(valueJusticeMinistryPrize) + 1
				+ "";
	}

	public String getNumberCourt() {
		return listCourtPrize.indexOf(valueCourtPrize) + 1 + "";
	}

	public String getShagnalJil() {
		return Integer.toString(valueCourtPrize.getShagnalZai());
	}
	
	public String getSahilgaTurulNumber() {
		return listSahilgaTurul.indexOf(valueSahilgaTurul) + 1 + "";
	}	
	
	public String getSahilgaShiitgelNumber() {
		return listSahilgaShiitgel.indexOf(valueSahilgaShiitgel) + 1 + "";
	}
	
	public String getDuration(){
		if(valueSahilgaShiitgel.getDuration()==null)
			return "";
		
		return valueSahilgaShiitgel.getDuration()+"";
	}
}
