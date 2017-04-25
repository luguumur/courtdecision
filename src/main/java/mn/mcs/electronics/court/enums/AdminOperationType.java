package mn.mcs.electronics.court.enums;

public enum AdminOperationType {
	PASSWDCHANGE(0), DISABLEUSER(1), ENABLEUSER(2), SHOWPHONENUMBERS(3);

	private final int val;

	AdminOperationType(int val) {
		this.val = val;
	}

	/**
	 * @return the val
	 */
	public int getVal() {
		return val;
	}

}
