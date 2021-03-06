/* 
 * File Name: 		EmployeeSelectionModel.java
 * 
 * Created By: 		U.Narankhuu
 * Created Date: 	Apr 6, 2011
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Apr 6, 2011 1.0.0 			U.Narankhuu						Шинээр үүсгэв.
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
import mn.mcs.electronics.court.entities.configuration.DegreeTypeOfScienceDoctor;
import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class DegreeTypeOfScienceDoctorSelectionModel extends AbstractSelectModel implements
		Serializable {

	private static final long serialVersionUID = 1438843132757579237L;

	private final List<OptionModel> options = CollectionFactory.newList();


	private Comparator<DegreeTypeOfScienceDoctor> empComparator = new Comparator<DegreeTypeOfScienceDoctor>() {

		public int compare(DegreeTypeOfScienceDoctor o1, DegreeTypeOfScienceDoctor o2) {
			return o1.getName().compareTo(
					o2.getName());
		}

	};


	public DegreeTypeOfScienceDoctorSelectionModel(CoreDAO cdao) {
		List<DegreeTypeOfScienceDoctor> list = cdao.getListScienceDoctor();

		setLists(list,true);
	}


	
	private void setLists(List<DegreeTypeOfScienceDoctor> list, boolean preSurname) {

		/* Sep 1, 2011 У.Наранхүү Start */
		Collections.sort(list, empComparator);

		/* Sep 1, 2011 У.Наранхүү End */

		for (int i = 0; i < list.size(); i++) {
			
			DegreeTypeOfScienceDoctor value = (DegreeTypeOfScienceDoctor) list.get(i);
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