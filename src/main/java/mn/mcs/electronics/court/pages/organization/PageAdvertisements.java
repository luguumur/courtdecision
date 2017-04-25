/* 
 * File Name: 		PageAdvertisements.java
 * 
 * Created By: 		G.Bileg-Ochir
 * Created Date: 	Aug 7, 2012
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Aug 7, 2012 1.0.0 			G.Bileg-Ochir					Шинээр үүсгэв.
 * 
 * -----------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2011 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */
package mn.mcs.electronics.court.pages.organization;

import java.text.SimpleDateFormat;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.other.Notice;
import mn.mcs.electronics.court.util.Constants;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageAdvertisements {

	/*
	 * INJECTS
	 */
	
	@Inject
	private CoreDAO dao;
	
	/*
	 * PERSISTS
	 */
	
	@Property
	@Persist
	private List<Notice> listNotice;
	
	@Property
	@Persist
	private Notice valueNotice;
	
	private SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
	
	void beginRender(){
		listNotice = dao.getListNotice();
	}
	
	@CommitAfter
	void onActionFromDelete(Notice notice){
		dao.deleteObject(notice);
	}
	
	/*
	 * GETTER, SETTER
	 */
	
	public String getEmployeeName(){
		if(valueNotice==null||valueNotice.getCreatedEmp()==null)
			return "";
		
		return valueNotice.getCreatedEmp().getFirstName();
	}
	
	public String getCreatedDate(){
		if(valueNotice==null||valueNotice.getCreatedDate()==null)
			return "";
		
		return format.format(valueNotice.getCreatedDate());
	}
	
	public String getListSize(){
		if(listNotice.isEmpty())
			return "0";
		
		return listNotice.size()+"";
	}
	
	public Integer getNumber(){
		return listNotice.indexOf(valueNotice)+1;
	}
}
