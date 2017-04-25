/* 
 * Package Name: mn.mcs.electronics.court.enums
 * File Name: AwardType.java
 * Description: 
 * 
 * Created By: tuguldur.j
 * Created Date: Jul 8, 2012
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Jul 8, 2012 			tuguldur.j					Шинээр үүсгэв.
 * ------------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2012 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */
package mn.mcs.electronics.court.enums;

/**
 * @author tuguldur.j
 *
 */
public enum AwardType {
	
	STATEPRIZE(0),
	COURTPRIZE(1),
	OTHER_ORGANIZATIONPRIZE(2),
	GOVERNMENTPRIZE(3),
	JUSTICE_MINISTRYPRIZE(4);
	
	private final int val;
	
	AwardType(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
}
