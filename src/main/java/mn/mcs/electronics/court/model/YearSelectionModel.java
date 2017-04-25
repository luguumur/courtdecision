package mn.mcs.electronics.court.model;



import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;

public class YearSelectionModel extends AbstractSelectModel implements
		Serializable {

	private static final long serialVersionUID = 1438843132757579237L;

	private final List<OptionModel> options = CollectionFactory.newList();

	public YearSelectionModel(Integer oldYear) {
		
		Calendar cal = Calendar.getInstance();
		
		if(oldYear==null){
			oldYear = cal.get(Calendar.YEAR);
		}
		
		Integer currentYear = cal.get(Calendar.YEAR) + 1;
		
		for(int i = oldYear; i<= currentYear ; i++)
			options.add(new OptionModelImpl(Integer.toString(i),(i)));
		
		
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