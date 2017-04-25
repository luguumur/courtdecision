/* 
 * File Name: 		DisplacementCause.java
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
import mn.mcs.electronics.court.entities.configuration.DisplacementCause;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class DisplacementCauseSelectionModel extends AbstractSelectModel implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7098533486778454040L;

	private final List<OptionModel> options = CollectionFactory.newList();

	private Comparator<DisplacementCause> empComparator = new Comparator<DisplacementCause>() {

		public int compare(DisplacementCause o1, DisplacementCause o2) {
			return o1.getName().compareTo(
					o2.getName());
		}

	};

	public DisplacementCauseSelectionModel(CoreDAO cdao) {
		List<DisplacementCause> list = cdao.getListDisplacementCause();

		setLists(list,true);
	}
	
	private void setLists(List<DisplacementCause> list, boolean preSurname) {

		Collections.sort(list, empComparator);

		for (int i = 0; i < list.size(); i++) {
			
			DisplacementCause value = (DisplacementCause) list.get(i);
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