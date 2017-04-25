/* 
 * File Name: 		TuAaSortSelectionModel.java
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
import mn.mcs.electronics.court.entities.configuration.TuAaSort;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class TuAaSortSelectionModel extends AbstractSelectModel implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2523309509068811061L;

	private final List<OptionModel> options = CollectionFactory.newList();

	private Comparator<TuAaSort> empComparator = new Comparator<TuAaSort>() {

		public int compare(TuAaSort o1, TuAaSort o2) {
			return o1.getNameDetail().compareTo(
					o2.getNameDetail());
		}

	};

	public TuAaSortSelectionModel(CoreDAO cdao) {
		List<TuAaSort> list = cdao.getListTuAaSort();

		setLists(list,true);
	}
	
	private void setLists(List<TuAaSort> list, boolean preSurname) {

		Collections.sort(list, empComparator);

		for (int i = 0; i < list.size(); i++) {
			
			TuAaSort value = (TuAaSort) list.get(i);
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