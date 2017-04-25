package mn.mcs.electronics.court.enums;

public enum ComponentType {
	TextField(0),
	Datefield(1),
	Select(2),
	Radiogroup(3),
	Checkbox(4),
	none(5);
	
	private final int val;
	
	ComponentType(int val){
		this.val = val;
	}
}
