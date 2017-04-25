package mn.mcs.electronics.court.dao.hibernate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.Permission;
import mn.mcs.electronics.court.entities.User;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class UserRealm extends AuthorizingRealm {
	protected final Session session;

	@Inject
	protected CoreDAO dao;

	public UserRealm(Session session) {

		super(new MemoryConstrainedCacheManager());/*, new CredentialsMatcher() {

			@Override
			public boolean doCredentialsMatch(AuthenticationToken token,
					AuthenticationInfo info) {
				// TODO Auto-generated method stub
				return true;
			}
		});*/

		this.session = session;

		setName("localaccounts");
		setAuthenticationTokenClass(UsernamePasswordToken.class);
		
		setCredentialsMatcher(new HashedCredentialsMatcher(
				Sha1Hash.ALGORITHM_NAME));
		
		setPermissionResolver(new WildcardPermissionResolver());
	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		if (principals == null)
			throw new AuthorizationException(
					"PrincipalCollection was null, which should not happen");

		if (principals.isEmpty())
			return null;

		if (principals.fromRealm(getName()).size() <= 0)
			return null;

		String username = (String) principals.fromRealm(getName()).iterator()
				.next();

		if (username == null)
			return null;
		User user = findByUsername(username);

		if (user == null)
			return null;

		Set<String> roles = new HashSet<String>();

		// for (MapUserRole role : user.getUser())
		roles.add(user.getRoles().getName());

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

		authorizationInfo.addRoles(roles);

		Set<String> permissions = new HashSet<String>();

		List<Permission> list = dao.getPermissionList(user.getRoles());

		for (Permission permission : list) {

			permissions.add(permission.getPermissionName());
		}

		authorizationInfo.addStringPermissions(permissions);

		return authorizationInfo;
	}

	private User findByUsername(String username) {
		return (User) session.createCriteria(User.class).add(
				Restrictions.eq("username", username)).uniqueResult();
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;

		String username = upToken.getUsername();

		// Null username is invalid
		if (username == null) {
			throw new AccountException(
					"Null usernames are not allowed by this realm.");
		}

		User user = findByUsername(username);

		if (user.isAccountLocked()) {
			System.out.println("account sis locked");
			throw new LockedAccountException("Account [" + username
					+ "] is locked.");
		}
		if (user.isCredentialsExpired()) {
			System.out.println("accounts  are expired");
			String msg = "The credentials for account [" + username
					+ "] are expired";
			throw new ExpiredCredentialsException(msg);
		}

		return new SimpleAuthenticationInfo(username,
				user.getEncodedPassword(), new SimpleByteSource(user
						.getPasswordSalt()), getName());
	}

}
