package mn.mcs.electronics.court.util.beans;

import java.util.List;

import mn.mcs.electronics.court.entities.configuration.salary.AdditionalSalaryType;

public class SalaryListBean {
	
	private List<AdditionalSalaryType> listSalaryType;

	public List<AdditionalSalaryType> getListSalaryType() {
		return listSalaryType;
	}

	public void setListSalaryType(List<AdditionalSalaryType> listSalaryType) {
		this.listSalaryType = listSalaryType;
	}
	
}
