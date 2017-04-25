package mn.mcs.electronics.court.enums;

/* 
 * Package Name: mn.mcs.electronics.court.enums
 * File Name: 	 SolvingType.java
 * Description:  Сахилгын хорооны шийдвэрийн төрөл сонгох enum
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

public enum SolvingType {
	TOENTERDISCIPLINE(0),
	TOABANDONDISCIPLINE(1),
	DECISION(2);
	
	private final int val;
	
	SolvingType(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
}
