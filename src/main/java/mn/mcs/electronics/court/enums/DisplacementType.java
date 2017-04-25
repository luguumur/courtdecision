package mn.mcs.electronics.court.enums;

public enum DisplacementType {
	SHILJIH(0),
	AJLAASGARAH(1);
	
	private final int val;
	
	DisplacementType(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
}
