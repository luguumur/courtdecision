package mn.mcs.electronics.court.enums;

public enum InOutFromPromote {
	IN(0),
	OUT(1),
	FROM_PROMOTE(2);
	
	private final int val;
	
	InOutFromPromote(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
}
