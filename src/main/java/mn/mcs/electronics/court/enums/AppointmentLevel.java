package mn.mcs.electronics.court.enums;

public enum AppointmentLevel {
	general(0),
	other(1);
	
	private final int val;
	
	AppointmentLevel(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
}
