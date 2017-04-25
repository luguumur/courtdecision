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

public enum ExcelTypes {
	HUVIINMEDEELEL(0),
	GERBUL(1),
	BOLOVSROL(2),
	ERDMIINTSOL(3),
	GADAADHEL(4),
	PROGRAMHANGAMJ(5),
	OFFICEIT(6),
	SURGALT(7),
	TURSHLAGA(8),
	TSOL(9),
	URDUN(10),
	TETGEMJ(11),
	CHULUU(12),
	SHAGNAL(13),
	SAHILGA(14),
	TSALIN(15),
	URCHADVAR(16);
	
	private final int val;
	
	ExcelTypes(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
}
