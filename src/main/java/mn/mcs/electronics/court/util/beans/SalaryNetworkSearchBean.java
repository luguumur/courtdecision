package mn.mcs.electronics.court.util.beans;

import java.util.List;
import mn.mcs.electronics.court.entities.configuration.SalaryScale;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuLevel;

public class SalaryNetworkSearchBean {
	private List<UtTzTtTuLevel> listUtTzTuLevel;
	
	private List<SalaryScale> listSalaryScale;

	public List<UtTzTtTuLevel> getListUtTzTuLevel() {
		return listUtTzTuLevel;
	}

	public void setListUtTzTuLevel(List<UtTzTtTuLevel> listUtTzTuLevel) {
		this.listUtTzTuLevel = listUtTzTuLevel;
	}
	
	public List<SalaryScale> getListSalaryScale() {
		return listSalaryScale;
	}

	public void setListSalaryScale(List<SalaryScale> listSalaryScale) {
		this.listSalaryScale = listSalaryScale;
	}
}
