/* 
 * File Name: 		DepartmentSelectionModel.java
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
import mn.mcs.electronics.court.entities.organization.Department;
import mn.mcs.electronics.court.entities.organization.Organization;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class DepartmentSelectionModel extends AbstractSelectModel implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6383880892143666438L;


	private final List<OptionModel> options = CollectionFactory.newList();


	private Comparator<Department> empComparator = new Comparator<Department>() {

		public int compare(Department o1, Department o2) {
			return o1.getName().compareTo(
					o2.getName());
		}

	};

	public DepartmentSelectionModel(CoreDAO cdao,Organization org) {
		List<Department> list = cdao.getListDepartment(org);

		setLists(list,true);
	}
	
	private void setLists(List<Department> list, boolean preSurname) {

		Collections.sort(list, empComparator);

		for (int i = 0; i < list.size(); i++) {
			
			Department value = (Department) list.get(i);
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