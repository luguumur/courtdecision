package mn.mcs.electronics.court.enums;

public enum ToriinUndurAjilAlbaCategory {
	TURIINUNDUR(0),
	AJLIINALBA(1);
	
	private final int val;
	
	ToriinUndurAjilAlbaCategory(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
}
