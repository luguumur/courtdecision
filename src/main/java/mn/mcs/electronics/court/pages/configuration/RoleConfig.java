package mn.mcs.electronics.court.pages.configuration;

import java.util.ArrayList;
import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.Permission;
import mn.mcs.electronics.court.entities.Role;
import mn.mcs.electronics.court.entities.map.MapRolePermission;
import mn.mcs.electronics.court.model.PermissionSelectionModel;
import mn.mcs.electronics.court.util.PaletteValueEncoder;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

public class RoleConfig {

	@Inject
	private CoreDAO dao;

	@Inject
	@Property
	@Path("context:/images/b_edit.png")
	private Asset editIcon;

	@Inject
	private Block blockList;

	@Inject
	private Block blockView;

	@Inject
	private Session session;

	@Property
	@Persist
	private List<Role> listRole;

	@Property
	@Persist
	private Role valueRole;

	@Persist
	private boolean switchBlock;

	@Persist
	@Property
	private List<Permission> selectedValues;

	@Persist
	private List<Permission> handling;

	@Persist
	private List<Permission> tempValues;

	@Persist
	private Boolean onload;

	void beginRender() {

		listRole = dao.getRoleList();

		if (selectedValues == null)
			selectedValues = new ArrayList<Permission>();

		if (tempValues == null)
			tempValues = new ArrayList<Permission>();

		if (valueRole != null && valueRole.getId() != null && onload == null) {

			selectedValues = dao.getPermissionList(valueRole);

			tempValues.addAll(selectedValues);

		}

		onload = true;
	}

	public Block getActiveBlock() {
		if (switchBlock)
			return blockView;
		else {

			return blockList;
		}
	}

	void onActionFromEdit(Role role) {
		this.valueRole = role;
		selectedValues = null;
		tempValues = null;
		onload = null;
		switchBlock = true;
	}

	void onActionFromAdd() {
		valueRole = new Role();
		onload = null;
		switchBlock = true;
	}

	@CommitAfter
	void onSelectedFromSave() {

		if (valueRole.getId() == null)
			dao.saveOrUpdateObject(valueRole);
		else {
			valueRole = (Role) dao.getObject(Role.class, valueRole.getId());
			dao.saveOrUpdateObject(dao.mergeObject(valueRole));
		}

		for (Permission perm : selectedValues) {

			boolean b = true;

			for (Permission temp : tempValues) {

				if (perm.getId().equals(temp.getId())) {
					b = false;
					break;
				}
			}

			if (b) {

				MapRolePermission mrp = new MapRolePermission();
				mrp.setPermission(perm);
				mrp.setRole(valueRole);
				dao.saveOrUpdateObject(mrp);

			}

		}

		tempValues.removeAll(selectedValues);

		for (Permission temp : tempValues) {

			temp = (Permission) dao.getObject(Permission.class, temp.getId());
			valueRole = (Role) dao.getObject(Role.class, valueRole.getId());

			MapRolePermission mrp = dao.getMapRolePermission(temp, valueRole);

			if (mrp != null) {
				dao.deleteObject(mrp);
			}

		}

		onload = null;
	}

	void onActionFromCancel() {
		switchBlock = false;
	}

	public List<Permission> getHandling() {
		return handling;
	}

	public void setHandling(List<Permission> handling) {
		this.handling = handling;
	}

	public PaletteValueEncoder getEncoder() {
		return new PaletteValueEncoder(session);
	}

	public SelectModel getModel() {
		SelectModel sm = new PermissionSelectionModel(dao);
		return sm;
	}

	public boolean isSwitchBlock() {
		return switchBlock;
	}

	public void setSwitchBlock(boolean switchBlock) {
		this.switchBlock = switchBlock;
	}

}
