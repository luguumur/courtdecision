/* 
 * File Name: 		OrganizationTypelectionModel.java
 * 
 * Created By: 		G.Bileg-Ochir
 * Created Date: 	Jul 4, 2012
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Jul 6, 2012 1.0.0 			G.Bileg-Ochir					Шинээр үүсгэв.
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
import mn.mcs.electronics.court.entities.organization.OrganizationType;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class OrganizationTypeSelectionModel extends AbstractSelectModel implements
		Serializable {

	private static final long serialVersionUID = 1438843132757579237L;

	private final List<OptionModel> options = CollectionFactory.newList();


	private Comparator<OrganizationType> empComparator = new Comparator<OrganizationType>() {

		public int compare(OrganizationType o1, OrganizationType o2) {
			return o1.getName().compareTo(
					o2.getName());
		}

	};

	public OrganizationTypeSelectionModel(CoreDAO cdao) {
		List<OrganizationType> list = cdao.getListOrganizationType();

		setLists(list,true);
	}
	
	private void setLists(List<OrganizationType> list, boolean preSurname) {

		Collections.sort(list, empComparator);

		for (int i = 0; i < list.size(); i++) {
			
			OrganizationType value = (OrganizationType) list.get(i);
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