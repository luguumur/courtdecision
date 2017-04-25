package mn.mcs.electronics.court.enums;

public enum SchoolType {	
	PRIMARYSCHOOL(0),
	MSUT(1),
	COLLEGE(2),	
	UNIVERSITY(3);
	
	private final int val;
	

	SchoolType(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
}
