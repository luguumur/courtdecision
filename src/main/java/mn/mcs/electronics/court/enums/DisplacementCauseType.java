package mn.mcs.electronics.court.enums;

public enum DisplacementCauseType {
	SHINEERTOMILOGDOH(0),
	TUSHAALDEVSHIH(1),
	TUSHAALBUURAH(2),
	AJILSELGEH(3);
	
	private final int val;
	
	DisplacementCauseType(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
}
