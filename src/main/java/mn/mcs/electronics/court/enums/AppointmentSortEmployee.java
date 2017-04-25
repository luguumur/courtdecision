package mn.mcs.electronics.court.enums;

public enum AppointmentSortEmployee {
	SHUUGCH(0),
	TAMGAHELTES(1),
	AJLIINALBA(2);
	
	private final int val;
	
	AppointmentSortEmployee(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
}
