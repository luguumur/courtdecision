package mn.mcs.electronics.court.enums;

public enum AppointmentType {
	JUDGE(0),
	OTHEREMPLOYEE(1);
	
	private final int val;
	
	AppointmentType(int val){
		this.val = val;
	}
	
	public int getVal() {
		return val;
	}
}
