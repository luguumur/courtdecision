package mn.mcs.electronics.court.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.Ascription;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class AscriptionSelectionModel extends AbstractSelectModel implements 
		Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5552127032554677696L;


	private final List<OptionModel> options = CollectionFactory.newList();


	private Comparator<Ascription> empComparator = new Comparator<Ascription>() {

		public int compare(Ascription o1, Ascription o2) {
			return o1.getName().compareTo(
					o2.getName());
		}

	};

	
	public AscriptionSelectionModel(CoreDAO cdao) {
		List<Ascription> list = cdao.getListAscription();

		setLists(list,true);
	}

	
	private void setLists(List<Ascription> list, boolean preSurname) {

		/* Sep 1, 2011 У.Наранхүү Start */
		Collections.sort(list, empComparator);

		/* Sep 1, 2011 У.Наранхүү End */

		for (int i = 0; i < list.size(); i++) {
			
			Ascription value = (Ascription) list.get(i);
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
