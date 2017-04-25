package mn.mcs.electronics.court.enums;

public enum QuitJobCauseName {
	TETGEVERTGARSAN(0),
	GEMTHEREHUILDSEN(1),
	KHUUKHEDHARAH(2),
	BUSAD(3);
	
	private final int val;
	
	QuitJobCauseName(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}	
}
