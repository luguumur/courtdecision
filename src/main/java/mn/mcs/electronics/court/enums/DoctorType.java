package mn.mcs.electronics.court.enums;

public enum DoctorType {
	EDUCATIONDOCTOR(0),
	SCIENCEDOCTOR(1);
	
	private final int val;
	
	DoctorType(int val){
		this.val = val;
	}
}
