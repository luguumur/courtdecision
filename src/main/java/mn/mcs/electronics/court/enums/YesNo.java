package mn.mcs.electronics.court.enums;

public enum YesNo {
	YES(0),
	NO(1);
	
	private final int val;
	

	YesNo(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
}
