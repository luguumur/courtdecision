package mn.mcs.electronics.court.enums;

public enum AdditionalSalaryCategory {
	MAIN(0),
	OTHER(1);
	
	private final int val;
	
	private AdditionalSalaryCategory(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
}
