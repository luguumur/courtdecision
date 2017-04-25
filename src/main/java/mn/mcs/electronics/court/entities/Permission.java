package mn.mcs.electronics.court.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.configuration.ProjectMenuConfig;
import mn.mcs.electronics.court.entities.map.MapRolePermission;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="permission")
public class Permission extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3614916178738751977L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	
	@Column(name="permission_name")
	private String permissionName;
	
	@Column(name="display_name")
	private String displayName;
	
	@Column(name="is_show", nullable = false, columnDefinition="boolean default false")
	private boolean isShow;
	
	@Column(name="is_component", nullable = false, columnDefinition="boolean default false")
	private boolean isComponent;
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "permission",orphanRemoval=true)
	private Set<MapRolePermission> mapRolePermission = new HashSet<MapRolePermission>();
	
	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "permission")
	private Set<ProjectMenuConfig> projectMenuConfig = new HashSet<ProjectMenuConfig>();
	
	//********** Accessor methods ***********//
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Set<MapRolePermission> getMapRolePermission() {
		return mapRolePermission;
	}

	public void setMapRolePermission(Set<MapRolePermission> mapRolePermission) {
		this.mapRolePermission = mapRolePermission;
	}

	public boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(boolean isShow) {
		this.isShow = isShow;
	}

	public boolean getIsComponent() {
		return isComponent;
	}

	public void setIsComponent(boolean isComponent) {
		this.isComponent = isComponent;
	}

	public Set<ProjectMenuConfig> getProjectMenuConfig() {
		return projectMenuConfig;
	}

	public void setProjectMenuConfig(Set<ProjectMenuConfig> projectMenuConfig) {
		this.projectMenuConfig = projectMenuConfig;
	}

	// ********************** Common Methods ********************** //
	public Permission() {
		uuid = UUIDUtil.getUUID();
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof Permission)) {
			return false;
		}
		
		final Permission e = (Permission) o;

		if (e.getId() == null)
			return false;
		
		/* May 31, 2011 У.Наранхүү Start */
		if (this.getId() == null)
			return false;
		/* May 31, 2011 У.Наранхүү End */

		return getId().equals(e.getId());
	}

	public int hashCode() {
		return getUuid().hashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("uuid",
				this.uuid).toString();
	}
}
