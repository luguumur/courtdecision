package mn.mcs.electronics.court.enums;

public enum PropertyTypeName {
	carType(0),
	houseType(1),
	equipmentType(2);
	private final int val;
	
	PropertyTypeName(int val){
		this.val = val;
	}
}
