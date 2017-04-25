/* 
 * File Name: 		SkillRateSelectionModel.java
 * 
 * Created By: 		U.Narankhuu
 * Created Date: 	Apr 5, 2011
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Apr 5, 2011 1.0.0 			U.Narankhuu						Шинээр үүсгэв.
 * 
 * -----------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2011 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */
package mn.mcs.electronics.court.model;

import java.io.Serializable;
import java.util.List;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public final class SkillPointSelectionModel extends AbstractSelectModel
		implements Serializable {

	private static final long serialVersionUID = -3590412082766899684L;

	private final List<OptionModel> options = CollectionFactory.newList();

	public SkillPointSelectionModel(List<Integer> skillOnoonuud) {
		setLists(skillOnoonuud);
	}

	public void setLists(List<Integer> skillOnoonuud) {
		for (Integer value : skillOnoonuud) {
			options.add(new OptionModelImpl(String.valueOf(value. intValue()), value));
		}
	}

	/**
	 * Returns null.
	 */
	public List<OptionGroupModel> getOptionGroups() {
		return null;
	}

	/**
	 * Returns the option groupos created in the constructor.
	 */
	public List<OptionModel> getOptions() {
		return options;
	}
}