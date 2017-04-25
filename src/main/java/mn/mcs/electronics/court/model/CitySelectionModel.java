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
import mn.mcs.electronics.court.entities.configuration.CityProvince;
import mn.mcs.electronics.court.entities.configuration.Country;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class CitySelectionModel extends AbstractSelectModel implements
		Serializable {

	private static final long serialVersionUID = 1438843132757579237L;

	private final List<OptionModel> options = CollectionFactory.newList();


	private Comparator<CityProvince> empComparator = new Comparator<CityProvince>() {

		public int compare(CityProvince o1, CityProvince o2) {
			return o1.getCityProvinceName().compareTo(
					o2.getCityProvinceName());
		}

	};


	public CitySelectionModel(CoreDAO cdao,Country country) {
		List<CityProvince> list = cdao.getListCityProvince(country);

		setLists(list,true);
	}


	
	private void setLists(List<CityProvince> list, boolean preSurname) {

		/* Sep 1, 2011 У.Наранхүү Start */
		Collections.sort(list, empComparator);

		/* Sep 1, 2011 У.Наранхүү End */

		for (int i = 0; i < list.size(); i++) {
			
			CityProvince value = (CityProvince) list.get(i);
			options.add((preSurname)? new OptionModelImpl(value.getCityProvinceName(), value) :  new OptionModelImpl(value.getCityProvinceName(), value));
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