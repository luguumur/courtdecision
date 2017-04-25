/* 
 * Package Name: mn.mcs.elex.pmms.util
 * File Name: UserUtil.java
 * Description: 
 * 
 * Created By: S.Khishigbaatar
 * Created Date: 2009/11/13
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * 2009/11/13 1.0.0 			S.Khishigbaatar					Шинээр үүсгэв.	
 * ****************************************************************************** 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2011 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */
package mn.mcs.electronics.court.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.Role;
import mn.mcs.electronics.court.entities.User;
import mn.mcs.electronics.court.entities.employee.Employee;

import org.apache.shiro.crypto.hash.Sha1Hash;




public class UserUtil {



	/**
	 * Нууц үгийг санамсаргүй утгаар үүсгэх.
	 * 
	 * @param length
	 *            - Санамсаргүйгээр үүсэх нууц үгийн урт
	 * 
	 * @return String - Санамсаргүйгээр үүссэн length урттай нууц үг
	 */
	public static String generateRandom(int length) {
		String password = "";
		BigDecimal bigDecimal = new BigDecimal(Math.random());

		bigDecimal = bigDecimal.movePointRight(length);
		password = bigDecimal.setScale(0, BigDecimal.ROUND_UP).toString();

		bigDecimal = bigDecimal.movePointRight(length - password.length());
		password = bigDecimal.setScale(0, BigDecimal.ROUND_UP).toString();

		return password;
	}

	public static String generateRandom(int minLength, int maxLength) {
		Random rand = new Random();

		int length = minLength + rand.nextInt(maxLength - minLength + 1);

		return PasswordGenerator.generate(PasswordGenerator.SECURE_CHARS,
				length);
	}

	/**
	 * User шинээр үүсгэнэ. Хэл, Идэвхитэй талбарууд default-аар үүснэ. Нууц үг
	 * санамсаргүй утгаар үүснэ.
	 * 
	 * @param username
	 *            - Нэвтрэх нэр
	 * @param currentRole
	 *            - Эрх
	 * @param roles
	 *            - Туслах эрхүүд
	 * 
	 * @return User - Шинээр үүссэн User.
	 */
	public static User getUser(String username, Role currentRole,
			List<Role> roles, Long counter) {
		User user = new User();

		/* May 31, 2011 У.Наранхүү Start */
		// String password = generateRandom(Constants.PASSWORD_RANDOM_LENGTH);

	//	String password = user.generateRandomPassword();
		String password = "123";
		/* May 31, 2011 У.Наранхүү End */

	//	password = StringUtil.encodePassword(password, Constants.ENC_ALGORITHM);
		password = "123";
		user.setUsername(username);
		user.setPassword(password);

//		user.setLanguage(Constants.DEFAULT_LANGUAGE);
//		user.setUserEnabled(Constants.DEFAULT_USER_ENABLE);

	//	user.setCurrentRole(currentRole);

		if (!roles.contains(currentRole)) {
			roles.add(0, currentRole);
		}

	//	user.getRoles().addAll(roles);

	//	user.setCounter(counter);

		return user;
	}

	/* May 31, 2011 У.Наранхүү End */

	public static String getUser(CoreDAO cDao, Employee emp) {

		String nameCode;

		User user = cDao.getMaxUserCode();

		Integer k;

		if (user != null) {


			k = user.getId();

			k++;

			nameCode = String.format("%03d", k.intValue());
		}

		else {
			k = Integer.valueOf("001");

			nameCode = String.format("%03d", k.intValue());

		}

		String username = "";

	
		if (username.length() == 0)
			username = "A" + nameCode;
		


		return username;
	}
	
	public static boolean setPassword(String password,byte[] passwordSalt,String encodedPassword) {
		if (password != null) {
			//ByteSource saltSource = new SecureRandomNumberGenerator().nextBytes();
		//	passwordSalt = saltSource;
			password = new Sha1Hash(password, passwordSalt).toString();
		}
		if(password.equals(encodedPassword))
			return true;
		else
			return false;
		
	}

	
}