package mn.mcs.electronics.court.enums;

public enum OrganizationGroup {
	SHUUH(0),
	YRUNHIIZUVLUL(1),
	SAHILGAHOROO(2),
	MERGESHILHOROO(3);
	
	private final int val;
	
	OrganizationGroup(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
}
