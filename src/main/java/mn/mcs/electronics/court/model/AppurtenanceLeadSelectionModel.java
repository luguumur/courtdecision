package mn.mcs.electronics.court.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.organization.AppurtenanceLead;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class AppurtenanceLeadSelectionModel extends AbstractSelectModel implements
Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8608593514142061307L;
	
	private final List<OptionModel> options = CollectionFactory.newList();


	private Comparator<AppurtenanceLead> empComparator = new Comparator<AppurtenanceLead>() {

		public int compare(AppurtenanceLead o1, AppurtenanceLead o2) {
			return o1.getName().compareTo(
					o2.getName());
		}

	};

	public AppurtenanceLeadSelectionModel(CoreDAO cdao) {
		List<AppurtenanceLead> list = cdao.getListAppurtenanceLead();

		setLists(list,true);
	}
	
	private void setLists(List<AppurtenanceLead> list, boolean preSurname) {

		Collections.sort(list, empComparator);

		for (int i = 0; i < list.size(); i++) {
			
			AppurtenanceLead value = (AppurtenanceLead) list.get(i);
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
