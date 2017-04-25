package mn.mcs.electronics.court.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuLevel;
import mn.mcs.electronics.court.entities.configuration.UtTzTtTuSort;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class OccupationLevelSelectionModel extends AbstractSelectModel implements
Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3727156458229064787L;


	private final List<OptionModel> options = CollectionFactory.newList();


	private Comparator<UtTzTtTuLevel> empComparator = new Comparator<UtTzTtTuLevel>() {

		public int compare(UtTzTtTuLevel o1,UtTzTtTuLevel o2) {
			return o1.getUtTzTtTuSort().getName().compareTo(
					o2.getUtTzTtTuSort().getName());
		}

	};


	public OccupationLevelSelectionModel(CoreDAO cdao, UtTzTtTuSort cat) {
		List<UtTzTtTuLevel> list = cdao.getListUtTzTtTuLevel(cat);

		setLists(list,true);
	}
	
	private void setLists(List<UtTzTtTuLevel> list, boolean preSurname) {

		/* Sep 1, 2011 У.Наранхүү Start */
		Collections.sort(list, empComparator);

		/* Sep 1, 2011 У.Наранхүү End */

		for (int i = 0; i < list.size(); i++) {
			
			UtTzTtTuLevel value = (UtTzTtTuLevel) list.get(i);
			options.add( 
					new OptionModelImpl(value.getUtTzTtTuSort().getName()
					+"-"+value.getUtTzTtTuRank().getRank(), value));
			
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
