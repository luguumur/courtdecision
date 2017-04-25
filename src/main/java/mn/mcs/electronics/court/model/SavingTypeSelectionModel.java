/* 
 * File Name: 		SavingTypeSelectionSelectionModel.java
 * 
 * Created By: 		U.Narankhuu
 * Created Date: 	Aug 13, 2012
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Aug 13, 2012 1.0.0 			G.Bileg-Ochir					Шинээр үүсгэв.
 * 
 * -----------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2012 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */
package mn.mcs.electronics.court.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.organization.Saving;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class SavingTypeSelectionModel extends AbstractSelectModel implements
		Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8430184309530609413L;


	private final List<OptionModel> options = CollectionFactory.newList();


	private Comparator<Saving> empComparator = new Comparator<Saving>() {

		public int compare(Saving o1, Saving o2) {
			return o1.getSavingName().compareTo(
					o2.getSavingName());
		}

	};

	public SavingTypeSelectionModel(CoreDAO cdao) {
		List<Saving> list = cdao.getListSavingType();

		setLists(list,true);
	}
	
	private void setLists(List<Saving> list, boolean preSurname) {

		Collections.sort(list, empComparator);

		for (int i = 0; i < list.size(); i++) {
			
			Saving value = (Saving) list.get(i);
			options.add((preSurname)? new OptionModelImpl(value.getSavingName(), value) :  new OptionModelImpl(value.getSavingName(), value));
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
