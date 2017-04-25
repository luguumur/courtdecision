package mn.mcs.electronics.court.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.City;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class CityProvinceSelectionModel extends AbstractSelectModel implements 
		Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6238009913803902490L;


	private final List<OptionModel> options = CollectionFactory.newList();

	private Comparator<City> empComparator = new Comparator<City>() {

		public int compare(City o1, City o2) {
			return o1.getCityName().compareTo(
					o2.getCityName());
		}

	};
	
	
	public CityProvinceSelectionModel(CoreDAO cdao) {
		List<City> list = cdao.getListCity();

		setLists(list,true);
	}
	
	private void setLists(List<City> list, boolean preSurname) {

		Collections.sort(list, empComparator);

		for (int i = 0; i < list.size(); i++) {
			
			City value = (City) list.get(i);
			options.add((preSurname)? new OptionModelImpl(value.getCityName(), value) :  new OptionModelImpl(value.getCityName(), value));
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
