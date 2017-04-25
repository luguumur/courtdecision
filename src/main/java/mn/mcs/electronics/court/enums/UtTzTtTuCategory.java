package mn.mcs.electronics.court.enums;

public enum UtTzTtTuCategory {
	ULSTURIIN(0),
	TURIINZAHIRGAANII(1),
	TURIINTUSGAI(2),
	TURIINUILCHILGEENII(3);
	
	private final int val;
	
	UtTzTtTuCategory(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
}
