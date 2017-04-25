/* 
 * Package Name: mn.mcs.elex.pmms.util
 * File Name: UUIDUtil.java
 * Description: UUID Utility Class This is used to generate uuid
 * 
 * Created By: S.Khishigbaatar
 * Created Date: 2011/03/23
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * 2011/03/23 1.0.0 			S.Khishigbaatar					Шинээр үүсгэв.
 * ------------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2011 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */
package mn.mcs.electronics.court.util;


public class UUIDUtil {
    //~ Methods ================================================================
    public static String getUUID() {
		String uuid = null;
    	try {
			GUIDGenerator gen = new GUIDGenerator();
			uuid = gen.getUnformatedUUID();
		} catch (GUIDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return uuid;
    }
}
