/* 
 * File Name: 		VacationTypeSelectionModel
 * 
 * Created By: 		S.Jargalsuren	
 * Created Date: 	Mar 21, 2013
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Mar 21, 2013 1.0.0 			S.Jargalsuren						Шинээр үүсгэв.
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
import mn.mcs.electronics.court.entities.employee.VacationType;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class VacationTypeSelectionModel extends AbstractSelectModel implements	Serializable {

	private static final long serialVersionUID = 1155405319717559150L;


	private final List<OptionModel> options = CollectionFactory.newList();


	private Comparator<VacationType> empComparator = new Comparator<VacationType>() {

		public int compare(VacationType o1, VacationType o2) {
			return o1.getVacationTypeName().compareTo(
					o2.getVacationTypeName());
		}

	};

	public VacationTypeSelectionModel(CoreDAO cdao) {
		List<VacationType> list = cdao.getListVacationType();

		setLists(list,true);
	}
	
	private void setLists(List<VacationType> list, boolean preSurname) {

		Collections.sort(list, empComparator);

		for (int i = 0; i < list.size(); i++) {
			
			VacationType value = (VacationType) list.get(i);
			options.add((preSurname)? new OptionModelImpl(value.getVacationTypeName(), value) :  new OptionModelImpl(value.getVacationTypeName(), value));
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