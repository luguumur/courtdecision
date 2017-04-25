/* 
 * File Name: 		OpenPositionSelectionSelectionModel.java
 * 
 * Created By: 		G.Bileg-Ochir
 * Created Date: 	Aug 15, 2012
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Aug 15, 2011 1.0.0 			G.Bileg-Ochir					Шинээр үүсгэв.
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
import mn.mcs.electronics.court.entities.other.OpenPosition;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class OpenPositionSelectionModel extends AbstractSelectModel implements
		Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -5428683883101522252L;


	private final List<OptionModel> options = CollectionFactory.newList();


	private Comparator<OpenPosition> empComparator = new Comparator<OpenPosition>() {

		public int compare(OpenPosition o1, OpenPosition o2) {
			return o1.getOpNumber().toString().compareTo(
					o2.getOpNumber().toString());
		}

	};

	public OpenPositionSelectionModel(CoreDAO cdao) {
		List<OpenPosition> list = cdao.getListOpenPosition();

		setLists(list,true);
	}
	
	private void setLists(List<OpenPosition> list, boolean preSurname) {

		Collections.sort(list, empComparator);

		for (int i = 0; i < list.size(); i++) {
			
			OpenPosition value = (OpenPosition) list.get(i);
			options.add((preSurname)? new OptionModelImpl(value.getOpNumber().toString(), value) :  new OptionModelImpl(value.getOpNumber().toString(), value));
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
