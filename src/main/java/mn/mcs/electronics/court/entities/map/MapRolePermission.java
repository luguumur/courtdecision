package mn.mcs.electronics.court.entities.map;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.Permission;
import mn.mcs.electronics.court.entities.Role;

@Entity
@Table(name="map_role_permission")
public class MapRolePermission implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5596246685964325236L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="role")
	private Role role;
	
	@ManyToOne
	@JoinColumn(name="permission")
	private Permission permission;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	
}
