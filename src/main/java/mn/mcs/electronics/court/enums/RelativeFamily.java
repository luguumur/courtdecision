package mn.mcs.electronics.court.enums;

public enum RelativeFamily {
	ISRELATIVE(0),
	ISFAMILY(1);
	
	private final int val;
	

	RelativeFamily(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
}
