package mn.mcs.electronics.court.enums;

public enum EmployeeCurrentStatus {
	CHULUULUGDUKH(0),
	KHALAGDAH(1),
	TETGEVERTGARAKH(2);
	
	private final int val;
	
	EmployeeCurrentStatus(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
}
