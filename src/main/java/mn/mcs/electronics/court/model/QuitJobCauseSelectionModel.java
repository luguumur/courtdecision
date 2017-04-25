package mn.mcs.electronics.court.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.QuitJobCause;
import mn.mcs.electronics.court.enums.EmployeeCurrentStatus;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class QuitJobCauseSelectionModel extends AbstractSelectModel implements
Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3727156458229064787L;


	private final List<OptionModel> options = CollectionFactory.newList();


	private Comparator<QuitJobCause> empComparator = new Comparator<QuitJobCause>() {

		public int compare(QuitJobCause o1,QuitJobCause o2) {
			return o1.getCauseType().compareTo(
					o2.getCauseType());
		}

	};


	public QuitJobCauseSelectionModel(CoreDAO cdao,EmployeeCurrentStatus cat) {
		List<QuitJobCause> list = cdao.getListQuitJobCause(cat);

		setLists(list,true);
	}
	
	private void setLists(List<QuitJobCause> list, boolean preSurname) {

		/* Sep 1, 2011 У.Наранхүү Start */
		Collections.sort(list, empComparator);

		/* Sep 1, 2011 У.Наранхүү End */

		for (int i = 0; i < list.size(); i++) {
			
			QuitJobCause value = (QuitJobCause) list.get(i);
			options.add( 
					new OptionModelImpl(value.getCauseName(), value));
			
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
