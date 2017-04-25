/* 
 * File Name: 		UtTzTtTuSortSelectionModel.java
 * 
 * Created By: 		B.Erdenetuya
 * Created Date: 	Jul 4, 2012
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Jul 4, 2012 1.0.0 			B.Erdenetuya					Шинээр үүсгэв.
 * 
 * -----------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2011 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */
package mn.mcs.electronics.court.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuSort;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class UtTzTtTuSortSelectionModel extends AbstractSelectModel implements
		Serializable {

	private static final long serialVersionUID = 1438843132757579237L;

	private final List<OptionModel> options = CollectionFactory.newList();

	private Comparator<UtTzTtTuSort> empComparator = new Comparator<UtTzTtTuSort>() {

		public int compare(UtTzTtTuSort o1, UtTzTtTuSort o2) {
			return o1.getNameDetail().compareTo(
					o2.getNameDetail());
		}

	};

	public UtTzTtTuSortSelectionModel(CoreDAO cdao) {
		List<UtTzTtTuSort> list = cdao.getListUtTzTtTuSort();

		setLists(list,true);
	}
	
	private void setLists(List<UtTzTtTuSort> list, boolean preSurname) {

		Collections.sort(list, empComparator);

		for (int i = 0; i < list.size(); i++) {
			
			UtTzTtTuSort value = (UtTzTtTuSort) list.get(i);
			options.add((preSurname)? new OptionModelImpl(value.getNameDetail(), value) :  new OptionModelImpl(value.getNameDetail(), value));
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