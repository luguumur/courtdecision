package mn.mcs.electronics.court.enums;

public enum Gender {
	MALE(0),
	FEMALE(1);
	
	private final int val;
	
	Gender(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
}
