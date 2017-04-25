package mn.mcs.electronics.court.enums;

public enum EmployeeStatus {
	WORKING(0),
	TIRED(1);
	
	private final int val;
	
	EmployeeStatus(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
}
