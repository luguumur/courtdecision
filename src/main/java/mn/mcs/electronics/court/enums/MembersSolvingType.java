package mn.mcs.electronics.court.enums;

/* 
 * Package Name: mn.mcs.electronics.court.enums
 * File Name: 	 TrialCourtSolvingType.java
 * Description:  Анхан шатны шүүхийн хуралдааны шийдвэрийн төрөл сонгох enum
 * 
 * Created By: 	 J.Tuguldur
 * Created Date: Jul 3, 2012 
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Jul 3, 2012  				J.Tuguldur						Шинээр үүсгэв.
 * ------------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2012 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */

public enum MembersSolvingType {
	TOABIDE(0),
	TOIGNOREDISCIPLINE(1),
	TOCHANGEANDCHECK(2);
	
	private final int val;
	
	MembersSolvingType(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
}
