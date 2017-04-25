/* 
 * Package Name: mn.mcs.elex.pmms.entities
 * File Name: User.java
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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import mn.mcs.electronics.court.entities.employee.Employee;
import mn.mcs.electronics.court.entities.map.MapUserRole;
import mn.mcs.electronics.court.util.Constants;
import mn.mcs.electronics.court.util.UUIDUtil;
import mn.mcs.electronics.court.util.UserUtil;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.util.ByteSource;
import org.apache.tapestry5.beaneditor.NonVisual;


@Entity
@Table(name="user")
public class User extends BaseObject
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9019531928265637358L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NonVisual
	private Integer id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	
	@Column(name="username",unique = true)
	private String username;

	@Column(name="encoded_password")
	private String encodedPassword;

	@ManyToOne
	@JoinColumn(name="employee")
	private Employee employee;
	
	@Column(name="created_date")
	private Date created = new Date();
	
	@Column(name="accountLocked")
	private boolean accountLocked;

	private boolean credentialsExpired;
	@ManyToOne
	@JoinColumn(name = "current_role")
	private Role roles;
	
//	@NonVisual
//	private Set<Role> roles = new HashSet<Role>();

	private byte[] passwordSalt;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

/*	@Validate("required,regexp=^[0-9a-zA-Z._%+-]+@[0-9a-zA-Z]+[\\.]{1}[0-9a-zA-Z]+[\\.]?[0-9a-zA-Z]+$")
	public String getEmailAddress() {
		return emailAddress;
	}
*/

	@NonVisual
	public String getEncodedPassword() {
		return encodedPassword;
	}

	public void setEncodedPassword(String encodedPassword) {
		this.encodedPassword = encodedPassword;
	}

	@NonVisual
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}

	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}
	
//	public void setRoles(Set<Role> roles) {
//		this.roles = roles;
//	}
//
//	public Set<Role> getRoles() {
//		return roles;
//	}

	public boolean isAccountLocked() {
		return accountLocked;
	}

	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	@Transient
	public String getPassword() {
		return encodedPassword;
	}

	public void setPassword(String password) {
		if (password != null && !password.equals(encodedPassword) && !"".equals(password)) {
			ByteSource saltSource = new SecureRandomNumberGenerator().nextBytes();
			this.passwordSalt = saltSource.getBytes();
			this.encodedPassword = new Sha1Hash(password, saltSource).toString();
		}
	}

	public void setPasswordSalt(byte[] passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	public String generateRandomPassword() {
	
		String password = UserUtil.generateRandom(
				Constants.PASSWORD_RANDOM_LENGTH_MIN,
				Constants.PASSWORD_RANDOM_LENGTH_MAX);

		return password;
	}
	
	@NonVisual
	@Column(length = 128)
	public byte[] getPasswordSalt() {
		return passwordSalt;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public boolean isEnable() {
		return accountLocked;
	}

	public void setEnable(boolean isEnable) {
		this.accountLocked = isEnable;
	}
	
	public Role getRoles() {
		return roles;
	}

	public void setRoles(Role roles) {
		this.roles = roles;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/******** SET *********/

	@OneToMany(orphanRemoval=true, cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "user")
	private Set<MapUserRole> user = new HashSet<MapUserRole>();

	public Set<MapUserRole> getUser() {
		return user;
	}

	public void setUser(Set<MapUserRole> user) {
		this.user = user;
	}
	
	// ********************** Common Methods ********************** //
	public User() {
		
		uuid = UUIDUtil.getUUID();
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof User)) {
			return false;
		}
		
		final User e = (User) o;

		if (e.getId() == null)
			return false;
		
		/* May 31, 2011 У.Наранхүү Start */
		if (this.getId() == null)
			return false;
		/* May 31, 2011 У.Наранхүү End */

		return getUuid().equals(e.getUuid());
	}

	public int hashCode() {
		return getUuid().hashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("uuid",
				this.uuid).toString();
	}
	
}
