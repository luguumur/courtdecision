/* 
 * File Name: 		AppointmentSelectionModel.java
 * 
 * Created By: 		B.Erdenetuya
 * Created Date: 	Jul 17, 2012
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
import mn.mcs.electronics.court.entities.configuration.Appointment;
import mn.mcs.electronics.court.entities.configuration.OccupationType;
import mn.mcs.electronics.court.entities.organization.Organization;
import mn.mcs.electronics.court.enums.AppointmentSortEmployee;
import mn.mcs.electronics.court.enums.AppointmentType;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class AppointmentOrgSelectionModel extends AbstractSelectModel implements
		Serializable {

	private static final long serialVersionUID = 1438843132757579237L;

	private final List<OptionModel> options = CollectionFactory.newList();


	private Comparator<Appointment> empComparator = new Comparator<Appointment>() {

		public int compare(Appointment o1, Appointment o2) {
			return o1.getAppointmentName().compareTo(
					o2.getAppointmentName());
		}

	};

	public AppointmentOrgSelectionModel(CoreDAO cdao) {
		List<Appointment> list = cdao.getListAppointmentOrg();

		setLists(list,true);
	}
	
	public AppointmentOrgSelectionModel(CoreDAO cdao, OccupationType type) {
		List<Appointment> list = cdao.getListAppointmentByType(type);

		setLists(list,true);
	}
	
	private void setLists(List<Appointment> list, boolean preSurname) {

		Collections.sort(list, empComparator);

		for (int i = 0; i < list.size(); i++) {
			
			Appointment value = (Appointment) list.get(i);
			options.add((preSurname)? new OptionModelImpl(value.getAppointmentName(), value) :  new OptionModelImpl(value.getAppointmentName(), value));
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