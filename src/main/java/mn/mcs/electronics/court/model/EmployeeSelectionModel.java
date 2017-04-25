/* 

 * File Name: 		EmployeeSelectionModel.java
 * 
 * Created By: 		
 * Created Date: 	Apr 6, 2011
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Apr 6, 2011 1.0.0 											Шинээр үүсгэв.
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
import mn.mcs.electronics.court.entities.employee.Employee;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class EmployeeSelectionModel extends AbstractSelectModel implements
		Serializable {

	private static final long serialVersionUID = 1438843132757579237L;

	private final List<OptionModel> options = CollectionFactory.newList();


	private Comparator<Employee> empComparator = new Comparator<Employee>() {

		public int compare(Employee o1, Employee o2) {
			return o1.getId().compareTo(
					o2.getId());
		}

	};

	public EmployeeSelectionModel(CoreDAO cdao) {
		List<Employee> list = cdao.getListEmployee(null);

		setLists(list,true);
	}
	
	public EmployeeSelectionModel(CoreDAO cdao, boolean isUser){
		List<Employee> list = cdao.getEmployeeIsUserList();

		setLists(list,true);
	}
	
	private void setLists(List<Employee> list, boolean preSurname) {

		
		Collections.sort(list, empComparator);

		for (int i = 0; i < list.size(); i++) {
			
			Employee value = (Employee) list.get(i);
			options.add((preSurname)? new OptionModelImpl(value.getFullNameFirstChar(), value) :  new OptionModelImpl(value.getFullNameFirstChar(), value));
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