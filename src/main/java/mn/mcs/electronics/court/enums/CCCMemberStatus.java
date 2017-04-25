package mn.mcs.electronics.court.enums;

public enum CCCMemberStatus {
	WORKING(0),
	TIRED(1);
	
	private final int val;
	
	CCCMemberStatus(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
}
