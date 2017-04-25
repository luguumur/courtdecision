package mn.mcs.electronics.court.enums;

public enum Month {
	JAN(0),
	FEB(1),
	MAR(2),
	APR(3),
	MAY(4),
	JUN(5),
	JUL(6),
	AUG(7),
	SEP(8),
	OCT(9),
	NOV(10),
	DEC(11);
	
	private final int val;
	
	Month(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
}
