package mn.mcs.electronics.court.enums;

public enum MilitaryRankType {	
	AhlagchiinBureldehuuun(0),
	DundOfitseriinBureldehuuun(1),
	AhlahOfitseriinBureldehuun(2),
	DeedOfitseriinBureldehuun(3);
	
	private final int val;
	
	MilitaryRankType(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
}
