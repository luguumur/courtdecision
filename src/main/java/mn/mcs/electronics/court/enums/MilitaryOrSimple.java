package mn.mcs.electronics.court.enums;

public enum MilitaryOrSimple {
	workingSimple(0),
	workingMilitary(1);
	
	private final int val;
	
	MilitaryOrSimple(int val){
		this.val = val;
	}
	
	public int getVal() {
		return val;
	}
}
