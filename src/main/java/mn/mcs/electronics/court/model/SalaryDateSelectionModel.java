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
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.SalaryScale;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuLevel;
import mn.mcs.electronics.court.entities.configuration.salary.TuriinZahirgaaSalaryNetwork;
import mn.mcs.electronics.court.util.Constants;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class SalaryDateSelectionModel extends AbstractSelectModel implements
		Serializable {

	private static final long serialVersionUID = 1438843132757579237L;

	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);
	
	private final List<OptionModel> options = CollectionFactory.newList();

	private Comparator<TuriinZahirgaaSalaryNetwork> empComparator = new Comparator<TuriinZahirgaaSalaryNetwork>() {

		@Override
		public int compare(TuriinZahirgaaSalaryNetwork o1, TuriinZahirgaaSalaryNetwork o2) {
				return o1.getDate().compareTo(o2.getDate());
		}
	};

	public SalaryDateSelectionModel(CoreDAO cdao, UtTzTtTuLevel level, SalaryScale scale) {
		List<TuriinZahirgaaSalaryNetwork> list = cdao.getListTuriinZahirgaaSalaryDate(level, scale);
		
		setLists(list,true);
	}
	
	private void setLists(List<TuriinZahirgaaSalaryNetwork> list, boolean preSurname) {
		Collections.sort(list, empComparator);
		
	for (int i = 0; i < list.size(); i++) {
			
		TuriinZahirgaaSalaryNetwork value = (TuriinZahirgaaSalaryNetwork) list.get(i);
			options.add(new OptionModelImpl(value.getDate()!=null?format.format(value.getDate()):"",value.getDate()!=null? format.format(value.getDate()):""));
		}
	}
	
	public List<OptionGroupModel> getOptionGroups() {
		return null;
	}

	public List<OptionModel> getOptions() {
		return options;
	}

}