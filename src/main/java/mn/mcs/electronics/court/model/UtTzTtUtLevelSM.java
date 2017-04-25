/* 
 * Package Name: mn.mcse.lmis.pages.hz
 * File Name: SumDuuregSM.java
 * 
 * Created By: erdenetuya.ba
 * Created Date: Apr 4, 2013
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Apr 4, 2013 1.0.0	 		    erdenetuya.ba				    Шинээр үүсгэв.
 * ------------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2013 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */

package mn.mcs.electronics.court.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuLevel;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuSort;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class UtTzTtUtLevelSM extends AbstractSelectModel implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1949595137888452206L;

	private final List<OptionModel> options = CollectionFactory.newList();

	private Comparator<UtTzTtTuLevel> empComparator = new Comparator<UtTzTtTuLevel>() {

		public int compare(UtTzTtTuLevel o1, UtTzTtTuLevel o2) {
			return o1.getUuid().compareTo(o2.getUuid());
		}

	};

	public UtTzTtUtLevelSM(CoreDAO cdao, UtTzTtTuSort sort) {
		List<UtTzTtTuLevel> list = cdao.getListUtTzTtTuLevel(sort);
		setLists(list, true);
	}

	private void setLists(List<UtTzTtTuLevel> list, boolean preSurname) {
		Collections.sort(list, empComparator);

		for (int i = 0; i < list.size(); i++) {

			UtTzTtTuLevel value = (UtTzTtTuLevel) list.get(i);
			options.add((preSurname) ? new OptionModelImpl(value
					.getUtTzTtTuRank().getRank().toString(), value)
					: new OptionModelImpl(value.getUtTzTtTuRank().getRank().toString(),
							value));
		}
	}

	public List<OptionGroupModel> getOptionGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<OptionModel> getOptions() {
		// TODO Auto-generated method stub
		return options;
	}
}
