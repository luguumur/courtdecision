/* 
 * File Name: 		OccupationRoleSelectionModel.java
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
import mn.mcs.electronics.court.entities.employee.AllowanceType;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class AllowanceTypeSelectionModel extends AbstractSelectModel implements	Serializable {

	private static final long serialVersionUID = 8196836767211988333L;


	private final List<OptionModel> options = CollectionFactory.newList();


	private Comparator<AllowanceType> empComparator = new Comparator<AllowanceType>() {

		public int compare(AllowanceType o1, AllowanceType o2) {
			return o1.getAllowanceTypeName().compareTo(
					o2.getAllowanceTypeName());
		}

	};

	public AllowanceTypeSelectionModel(CoreDAO cdao) {
		List<AllowanceType> list = cdao.getListAllowanceType();

		setLists(list,true);
	}
	
	private void setLists(List<AllowanceType> list, boolean preSurname) {

		Collections.sort(list, empComparator);

		for (int i = 0; i < list.size(); i++) {
			
			AllowanceType value = (AllowanceType) list.get(i);
			options.add((preSurname)? new OptionModelImpl(value.getAllowanceTypeName(), value) :  new OptionModelImpl(value.getAllowanceTypeName(), value));
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