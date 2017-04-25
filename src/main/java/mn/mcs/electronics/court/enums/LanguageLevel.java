package mn.mcs.electronics.court.enums;

public enum LanguageLevel {
	EXCELLENT(0),
	GOOD(1),
	MEDIUM(2),
	BAD(3);
	
	private final int val;
	
	LanguageLevel(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
}
