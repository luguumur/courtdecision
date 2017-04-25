/* 
 * File Name: 		ApprovedPositionSelectionModel.java
 * 
 * Created By: 		G.Bileg-Ochir
 * Created Date: 	Nov 24, 2012
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Nov 24, 2012 1.0.0 			G.Bileg-Ochir					Шинээр үүсгэв.
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
import mn.mcs.electronics.court.entities.configuration.ApprovedPositions;
import mn.mcs.electronics.court.entities.organization.Organization;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class ApprovedPositionSelectionModel extends AbstractSelectModel implements
		Serializable {

	private static final long serialVersionUID = 1438843132757579237L;

	private final List<OptionModel> options = CollectionFactory.newList();


	private Comparator<ApprovedPositions> empComparator = new Comparator<ApprovedPositions>() {

		public int compare(ApprovedPositions o1, ApprovedPositions o2) {
			return o1.getId().compareTo(
					o2.getId());
		}
	};

	public ApprovedPositionSelectionModel(CoreDAO cdao, Organization org, Integer belenOronToo) {
		List<ApprovedPositions> list = cdao.getListApprovedPosition(org, belenOronToo);

		setLists(list,true);
	}
	
	private void setLists(List<ApprovedPositions> list, boolean preSurname) {

		Collections.sort(list, empComparator);

		for (int i = 0; i < list.size(); i++) {
			
			ApprovedPositions value = (ApprovedPositions) list.get(i);
			options.add((preSurname)? new OptionModelImpl(value.getAppointment().getAppointmentName(), value) : 
				new OptionModelImpl(value.getAppointment().getAppointmentName(), value));
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