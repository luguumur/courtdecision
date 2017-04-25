package mn.mcs.electronics.court.enums;

/* 
 * Package Name: mn.mcs.electronics.court.enums
 * File Name: 	 ImposedDisciplineJudgement.java
 * Description:  Оногдуулсан сахилгын шийтгэлийн төрөл сонгох enum
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

public enum ImposedDisciplineJudgement {
	TOMENTION(0),
	TODEBASECOURTDEGREE(1),
	TORESIGNCOMPLAINTTODCC(2);
	
	private final int val;
	
	ImposedDisciplineJudgement(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
}
