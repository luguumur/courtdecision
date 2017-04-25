package mn.mcs.electronics.court.enums;

public enum EmployeeDegreeStatus {
	TSOLNEMEKH(0),
	TSOLBUURAKH(1),
	TSOLSHINEERAVSAN(2),
	TSOLSERGEEKH(3);
	
	private final int val;
	
	private EmployeeDegreeStatus(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
}
