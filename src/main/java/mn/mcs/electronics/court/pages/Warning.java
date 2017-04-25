/* 
 * File Name: 		Warning.java
 * 
 * Created By: 		maralerdene.t
 * Created Date: 	Jan 7, 2013
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Jan 7, 2013 			            maralerdene.t					    	Шинээр үүсгэв.
 * 
 * -----------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2013 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */
package mn.mcs.electronics.court.pages;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.enums.AlertType;
import mn.mcs.electronics.court.pages.employee.PageEmployeeDetail;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.EnumSelectModel;

/**
 * @author maralerdene.t s
 */
public class Warning {

	/*
	 * INJECTS
	 */

	@Inject
	private Messages messages;

	@Inject
	private CoreDAO dao;

	@Persist
	@Property
	private AlertType alertType;

	@InjectPage
	private PageEmployeeDetail employeeDetail;

	@Inject
	private Block disciplineBlock, tsolBlock, tetgeverBlock, shagnalBlock,
			tsalinBlock, demeritBlock, udaanJilBlock;

	void beginRender() {

	}

	public Block getActiveBlock() {
		if (alertType != null) {

			switch (alertType) {
			case sahilgaDuusahHugatsaa:
				return disciplineBlock;
			case tsolniiAlert:
				return tsolBlock;
			case tetgeverNas:
				return tetgeverBlock;
			case shagnalAlert:
				return shagnalBlock;
			case tsalinAlert:
				return tsalinBlock;
			case sahilgaAwsan:
				return demeritBlock;
			case udaanJil:
				return udaanJilBlock;

			default:
				return null;
			}
		}

		return null;
	}

	/*
	 * SELECT MODELS
	 */

	public SelectModel getAlertTypeSelectionModel() {
		return new EnumSelectModel(AlertType.class, messages);
	}

}
