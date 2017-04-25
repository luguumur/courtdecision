/* 
 * File Name: 		AlertType.java
 * 
 * Created By: 		maralerdene.t
 * Created Date: 	Mar 11, 2013
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Mar 11, 2013 			            maralerdene.t					    	Шинээр үүсгэв.
 * 
 * -----------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2013 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */
package mn.mcs.electronics.court.enums;

/**
 * @author maralerdene.t
 *
 */
public enum AlertType {
	tsolniiAlert(0), tsalinAlert(1), sahilgaDuusahHugatsaa(2),
	shagnalAlert(3),tetgeverNas(4), udaanJil(5), sahilgaAwsan(6);
	
	private final int val;
	
	AlertType(int val){
		this.val = val;
	}
	
	public int getVal() {
		return val;
	}
}
