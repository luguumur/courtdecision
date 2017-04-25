/* 
 * Package Name: mn.mcs.electronics.court.entities.other
 * File Name: DCCMemberStatus.java
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
public enum DCCMemberStatus {
	   	WORKING(0),
		TIRED(1);
		
		private final int val;
		
		DCCMemberStatus(int val){
			this.val = val;
		}

		public int getVal() {
			return val;
		}
}
