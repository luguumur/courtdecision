package mn.mcs.electronics.court.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.TuAaLevel;
import mn.mcs.electronics.court.entities.configuration.TuAaSort;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class TuAaOccupationLevelSelectionModel extends AbstractSelectModel implements
Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3727156458229064787L;


	private final List<OptionModel> options = CollectionFactory.newList();


	private Comparator<TuAaLevel> empComparator = new Comparator<TuAaLevel>() {

		public int compare(TuAaLevel o1,TuAaLevel o2) {
			return o1.getTuAaSort().getName().compareTo(
					o2.getTuAaSort().getName());
		}

	};


	public TuAaOccupationLevelSelectionModel(CoreDAO cdao,TuAaSort cat) {
		List<TuAaLevel> list = cdao.getListTuAaLevel(cat);

		setLists(list,true);
	}


	
	private void setLists(List<TuAaLevel> list, boolean preSurname) {

		/* Sep 1, 2011 У.Наранхүү Start */
		Collections.sort(list, empComparator);

		/* Sep 1, 2011 У.Наранхүү End */

		for (int i = 0; i < list.size(); i++) {
			
			TuAaLevel value = (TuAaLevel) list.get(i);
			options.add( 
					new OptionModelImpl(value.getTuAaSort().getName()
					+"-"+value.getTuAaRank().getRank(), value));
			
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
