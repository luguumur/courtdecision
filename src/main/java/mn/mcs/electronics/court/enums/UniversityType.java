package mn.mcs.electronics.court.enums;

public enum UniversityType {
	TuriinSurguuli(0),
	HuviinSurguuli(1);
	
	private final int val;
	
	UniversityType(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
}
