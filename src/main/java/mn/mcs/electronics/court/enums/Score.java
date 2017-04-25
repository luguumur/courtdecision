package mn.mcs.electronics.court.enums;

public enum Score {
	A(0),
	B(1),
	C(2),
	D(3),
	F(4);
	
	private final int val;
	
	Score(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
}
