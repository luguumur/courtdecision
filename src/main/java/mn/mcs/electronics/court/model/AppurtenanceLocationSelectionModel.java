package mn.mcs.electronics.court.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.organization.AppurtenanceLocation;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class AppurtenanceLocationSelectionModel extends AbstractSelectModel implements
Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5941665200694042282L;
	
	private final List<OptionModel> options = CollectionFactory.newList();


	private Comparator<AppurtenanceLocation> empComparator = new Comparator<AppurtenanceLocation>() {

		public int compare(AppurtenanceLocation o1, AppurtenanceLocation o2) {
			return o1.getName().compareTo(
					o2.getName());
		}

	};

	public AppurtenanceLocationSelectionModel(CoreDAO cdao) {
		List<AppurtenanceLocation> list = cdao.getListAppurtenanceLocation();

		setLists(list,true);
	}
	
	private void setLists(List<AppurtenanceLocation> list, boolean preSurname) {

		Collections.sort(list, empComparator);

		for (int i = 0; i < list.size(); i++) {
			
			AppurtenanceLocation value = (AppurtenanceLocation) list.get(i);
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
