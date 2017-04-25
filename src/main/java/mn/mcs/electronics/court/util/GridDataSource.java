package mn.mcs.electronics.court.util;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.util.beans.EmployeeSearchBean;
import mn.mcs.electronics.court.util.beans.GridPager;

import org.apache.tapestry5.grid.SortConstraint;

public class GridDataSource implements org.apache.tapestry5.grid.GridDataSource {

	private CoreDAO dao;
	// private int startIndex;
	private List<Employee> preparedResults;
	private GridPager pager;
	private EmployeeSearchBean bean;

	public GridDataSource(CoreDAO dao, EmployeeSearchBean bean) {
		this.dao = dao;
		this.bean = bean;
	}

	@Override
	public int getAvailableRows() {
		return 500;
	}

	@Override
	public Class<Employee> getRowType() {
		return Employee.class;
	}

	@Override
	public Object getRowValue(int index) {
		return preparedResults.get(index);
	}

	@Override
	public void prepare(final int startIndex, final int endIndex,
			final List<SortConstraint> sortConstraints) {

		preparedResults = dao.getListEmployeeSearch(bean, null, null, pager);
	}

}
