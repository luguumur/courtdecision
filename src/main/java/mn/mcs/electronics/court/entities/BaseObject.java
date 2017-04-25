/* 
 * Package Name: mn.mcs.elex.pmms.entities
 * File Name: BaseObject.java
 * 
 * Created By: S.Khishigbaatar
 * Created Date: 2011/03/11
 * 
 * History 
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * 2011/03/21 1.0.0 			S.Khishigbaatar					Шинээр үүсгэв.
 * ------------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2011 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */
package mn.mcs.electronics.court.entities;

import java.io.Serializable;

public abstract class BaseObject implements Serializable {

	private static final long serialVersionUID = 20110125L;

	public abstract String toString();

	public abstract boolean equals(Object o);

	public abstract int hashCode();
}
