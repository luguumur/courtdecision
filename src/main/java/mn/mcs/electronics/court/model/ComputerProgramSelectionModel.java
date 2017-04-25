/* 
 * File Name: 		EmployeeSelectionModel.java
 * 
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Aut 14, 2011 1.0.0 			B. Erdenetuya					Шинээр үүсгэв.
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
import mn.mcs.electronics.court.entities.configuration.ComputerProgram;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class ComputerProgramSelectionModel extends AbstractSelectModel implements
		Serializable {

	private static final long serialVersionUID = 1438843132757579237L;

	private final List<OptionModel> options = CollectionFactory.newList();


	private Comparator<ComputerProgram> empComparator = new Comparator<ComputerProgram>() {

		public int compare(ComputerProgram o1, ComputerProgram o2) {
			return o1.getProgramName().compareTo(
					o2.getProgramName());
		}

	};


	public ComputerProgramSelectionModel(CoreDAO cdao) {
		List<ComputerProgram> list = cdao.getListComputerProgram();

		setLists(list,true);
	}


	
	private void setLists(List<ComputerProgram> list, boolean preSurname) {

		/* Sep 1, 2011 У.Наранхүү Start */
		Collections.sort(list, empComparator);

		/* Sep 1, 2011 У.Наранхүү End */

		for (int i = 0; i < list.size(); i++) {
			
			ComputerProgram value = (ComputerProgram) list.get(i);
			options.add((preSurname)? new OptionModelImpl(value.getProgramName(), value) :  new OptionModelImpl(value.getProgramName(), value));
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