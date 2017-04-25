package mn.mcs.electronics.court.enums;

public enum GCCMemberStatus {
	WORKING(0),
	TIRED(1);
	
	private final int val;
	
	GCCMemberStatus(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
}
