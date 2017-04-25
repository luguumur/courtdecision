/* 
 * File Name: 		FinancingTypeSelectionSelectionModel.java
 * 
 * Created By: 		U.Narankhuu
 * Created Date: 	Jul 6, 2012
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Jul 5, 2011 1.0.0 			G.Bileg-Ochir					Шинээр үүсгэв.
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
import mn.mcs.electronics.court.entities.organization.FinancingType;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class FinancingTypeSelectionModel extends AbstractSelectModel implements
		Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8430184309530609413L;


	private final List<OptionModel> options = CollectionFactory.newList();


	private Comparator<FinancingType> empComparator = new Comparator<FinancingType>() {

		public int compare(FinancingType o1, FinancingType o2) {
			return o1.getName().compareTo(
					o2.getName());
		}

	};

	public FinancingTypeSelectionModel(CoreDAO cdao) {
		List<FinancingType> list = cdao.getListFinancingType();

		setLists(list,true);
	}
	
	private void setLists(List<FinancingType> list, boolean preSurname) {

		Collections.sort(list, empComparator);

		for (int i = 0; i < list.size(); i++) {
			
			FinancingType value = (FinancingType) list.get(i);
			options.add((preSurname)? new OptionModelImpl(value.getName(), value) :  new OptionModelImpl(value.getName(), value));
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
