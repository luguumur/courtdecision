package mn.mcs.electronics.court.enums;

public enum GetWage {
	salaryNetwork(0);
	//salaryJudge(1);
	
	private final int val;
	
	GetWage(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
}
