package mn.mcs.electronics.court.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.City;
import mn.mcs.electronics.court.entities.configuration.DistrictSum;
import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class DistrictSumSelectionModel extends AbstractSelectModel implements
		Serializable {
	private static final long serialVersionUID = 1438843132757579237L;

	private final List<OptionModel> options = CollectionFactory.newList();

	private Comparator<DistrictSum> empComparator = new Comparator<DistrictSum>() {

		public int compare(DistrictSum o1, DistrictSum o2) {
				return o1.getDistrictSumName().compareTo(
						o2.getDistrictSumName());
		}

	};

	public DistrictSumSelectionModel(CoreDAO cdao, City city) {
		List<DistrictSum> list = cdao.getListDistrictSum(city);

		setLists(list, true);
	}

	private void setLists(List<DistrictSum> list, boolean preSurname) {

		/* Sep 1, 2011 У.Наранхүү Start */
		Collections.sort(list, empComparator);

		/* Sep 1, 2011 У.Наранхүү End */

		for (int i = 0; i < list.size(); i++) {

			DistrictSum value = (DistrictSum) list.get(i);
			options.add((preSurname) ? new OptionModelImpl(value
					.getDistrictSumName(), value) : new OptionModelImpl(value
					.getDistrictSumName(), value));
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
