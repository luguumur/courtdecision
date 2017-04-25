package mn.mcs.electronics.court.enums;

public enum ChuluuTimeType {
	LONGTIME(0),
	SHORTTIME(1);
	
	private final int val;
	
	ChuluuTimeType(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
}
