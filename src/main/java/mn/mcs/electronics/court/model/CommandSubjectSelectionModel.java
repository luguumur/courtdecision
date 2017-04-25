package mn.mcs.electronics.court.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.CommandSubject;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class CommandSubjectSelectionModel extends AbstractSelectModel implements
		Serializable {

	private static final long serialVersionUID = -6428849680149765092L;

	private final List<OptionModel> options = CollectionFactory.newList();

	private Comparator<CommandSubject> empComparator = new Comparator<CommandSubject>() {

		public int compare(CommandSubject o1, CommandSubject o2) {
			return o1.getSubjectName().compareTo(
					o2.getSubjectName());
		}

	};

	public CommandSubjectSelectionModel(CoreDAO cdao) {
		List<CommandSubject> list = cdao.getListCommandSubject();

		setLists(list,true);
	}
	
	private void setLists(List<CommandSubject> list, boolean preSurname) {

		/* Sep 1, 2011 У.Наранхүү Start */
		Collections.sort(list, empComparator);

		/* Sep 1, 2011 У.Наранхүү End */
		for (int i = 0; i < list.size(); i++) {
			
			CommandSubject value = (CommandSubject) list.get(i);
			options.add((preSurname)? new OptionModelImpl(value.getSubjectName(), value) :  new OptionModelImpl(value.getSubjectName(), value));
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