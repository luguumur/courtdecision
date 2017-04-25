package mn.mcs.electronics.court.enums;

public enum PropertyHouseType {
	ORONSUUTS(0),
	ALBANBAIR(1);
	
	private final int val;
	
	private PropertyHouseType(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}	
}
