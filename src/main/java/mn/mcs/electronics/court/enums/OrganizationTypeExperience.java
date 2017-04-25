package mn.mcs.electronics.court.enums;

public enum OrganizationTypeExperience {
	ULSIIN(0),
	HUVIIN(1),
	SHUUH(2);
	
	private final int val;
	

	OrganizationTypeExperience(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
}
