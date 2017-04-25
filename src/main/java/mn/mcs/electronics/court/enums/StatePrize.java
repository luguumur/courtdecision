/* 
 * Package Name: mn.mcs.electronics.court.entities.employee
 * File Name: StatePrize.java
 * Description: 
 * 
 * Created By: tuguldur.j
 * Created Date: Jul 9, 2012
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Jul 9, 2012 			tuguldur.j					Шинээр үүсгэв.
 * ------------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2012 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */

package mn.mcs.electronics.court.enums;

/**
 * @author tuguldur.j
 *
 */
public enum StatePrize {
	
	GALLANTLAWYER(0), //Гавъяат хуульч
	SUKHBAATARMEDAL(1),//Сүхбааатарын одон
	WORKINGREDMEDAL(2), //хөдөлмөрийн гавъяаны улаан тугын одон
	ALTANGADAS(3), //Алтан гадас
	WORKINGHEADMEDAL(4), //Хөдөлмөрийн хүндэт медал
	STATECREDENTIAL (5); //Төрийн жуух бичих
	
	private final int val;
	
	StatePrize(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
}
