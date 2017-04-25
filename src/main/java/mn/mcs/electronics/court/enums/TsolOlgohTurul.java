package mn.mcs.electronics.court.enums;

public enum TsolOlgohTurul {
	HugatsaaniiUmnu(0),
	Hugatsaanii(1);
	
	private final int val;
	
	private TsolOlgohTurul(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
}
