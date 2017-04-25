package mn.mcs.electronics.court.enums;

public enum InOut {
	IN(0),
	OUT(1);
	
	private final int val;
	
	InOut(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
}
