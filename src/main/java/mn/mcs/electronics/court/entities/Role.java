/* 
 * Package Name: mn.mcs.elex.pmms.entities
 * File Name: Role.java
 * 
 * Created By: S.Khishigbaatar
 * Created Date: 2011/03/23
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * 2011/03/23 1.0.0 			S.Khishigbaatar					Шинээр үүсгэв.
 * ------------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2011 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */
package mn.mcs.electronics.court.entities;

import java.io.Serializable;
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
import javax.persistence.Version;
import mn.mcs.electronics.court.entities.map.MapRolePermission;
import mn.mcs.electronics.court.entities.map.MapUserRole;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "role")
public class Role extends BaseObject implements Serializable {

	private static final long serialVersionUID = 3690197650654049848L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Version
	@Column
	private Integer version = 0;

	@Column(name = "name", unique = true, length = 20)
	private String name;

	@Column(name = "description")
	private String description;

	@OneToMany(orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "roles")
	private Set<User> user = new HashSet<User>();

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "role", orphanRemoval = true)
	private Set<MapRolePermission> mapRolePermission = new HashSet<MapRolePermission>();

	/**
	 * No-arg constructor for JavaBean tools
	 */
	public Role() {
		name = "";
	}

	/*
	 * Constructor
	 */
	public Role(String name) {
		this.name = name;
	}

	// ********************** Accessor Methods ********************** //

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<User> getUser() {
		return user;
	}

	public void setUser(Set<User> user) {
		this.user = user;
	}

	public Set<MapRolePermission> getMapRolePermission() {
		return mapRolePermission;
	}

	public void setMapRolePermission(Set<MapRolePermission> mapRolePermission) {
		this.mapRolePermission = mapRolePermission;
	}

	// ********************** Common Methods ********************** //
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null)
			return false;

		if (!(o instanceof Role)) {
			return false;
		}

		final Role r = (Role) o;

		if (r.getId() == null)
			return false;

		if (this.getId() == null)
			return false;

		return getId().equals(r.getId());
	}

	public int hashCode() {
		if (this.name != null)
			return this.name.hashCode();
		return 0;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id").append("name", this.name)
				.toString();
	}

	// ********************** Business Methods ********************** //
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "role")
	@org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE)
	private Set<MapUserRole> role = new HashSet<MapUserRole>();

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "role")
	@org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE)
	private Set<MapRolePermission> permission = new HashSet<MapRolePermission>();

	public Set<MapUserRole> getRole() {
		return role;
	}

	public void setRole(Set<MapUserRole> role) {
		this.role = role;
	}

	public Set<MapRolePermission> getPermissions() {
		return permission;
	}

	public void setPermissions(Set<MapRolePermission> permission) {
		this.permission = permission;
	}
}
