package mn.mcs.electronics.court.enums;

public enum FamilyStatus {
	MARRIED(0), SINGLE(1), DIVORCE(2), WIDOW(3), UNMARRIED(4);

	private final int val;

	FamilyStatus(int val) {
		this.val = val;
	}

	public int getVal() {
		return val;
	}
}
