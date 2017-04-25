package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.Permission;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PermissionConfig {
	
	@Inject
	private CoreDAO dao;
	
	@Inject
	private Block blockList;
	
	@Inject
	private Block blockView;
	
	@Persist
	private boolean switchBlock;
	
	@Inject
	@Property
	@Path("context:/images/b_edit.png")
	private Asset editIcon;
	
	@Inject
	@Property
	@Path("context:/images/b_drop.png")
	private Asset deleteIcon;
	
	@Property
	@Persist
	private List<Permission> listPermission;
	
	@Property
	@Persist
	private Permission valuePermission;
	
	@Property
	@Persist
	private Permission valueComponentPermission;
	
	@Persist
	private Permission permission;
	
	void beginRender(){
		listPermission = dao.getPermissionList();
	}
	
	public Block getActiveBlock(){
		if(switchBlock)
			return blockView;
		else
			return blockList;
	}
	
	void onActionFromAdd(){
		valuePermission = new Permission();
		valuePermission.setIsShow(true);
		switchBlock = true;
	}
	
	void onActionFromEdit(Permission permission){
		this.valuePermission = permission;
		switchBlock = true;
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(valuePermission);
		
		switchBlock = false;
	}
	
//	@CommitAfter
//	void onActionFromDelete(Permission permission){
//		dao.deleteObject(permission);
//	}
	
}
