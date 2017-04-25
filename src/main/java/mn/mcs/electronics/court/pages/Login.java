/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package mn.mcs.electronics.court.pages;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.Cookies;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.Response;
import org.tynamo.security.SecuritySymbols;
import org.tynamo.security.services.PageService;
import org.tynamo.security.services.SecurityService;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.User;
import mn.mcs.electronics.court.entities.configuration.UserLoginHistory;
import mn.mcs.electronics.court.entities.other.Notice;
import mn.mcs.electronics.court.util.Constants;

/**
 * Login form component \
 * 
 * 
 * 
 */
@SuppressWarnings("deprecation")
public class Login {

	private static final Logger logger = LogManager.getLogger(Login.class);

	@SessionState
	private LoginState loginState;

	@Inject
	private Messages messages;

	@Property
	private String username;

	@Property
	private String password;

	@Inject
	private AssetSource imageUrl;

	@Persist(PersistenceConstants.FLASH)
	private String loginMessage;

	@Inject
	private Response response;

	@Inject
	private RequestGlobals requestGlobals;

	@Inject
	private HttpServletRequest http;

	@Component
	private Form tynamoLoginForm;

	@Inject
	private SecurityService securityService;

	@Inject
	private PageService pageService;

	@Inject
	private Cookies cookies;

	@Inject
	@Symbol(SecuritySymbols.REDIRECT_TO_SAVED_URL)
	private boolean redirectToSavedUrl;

	@Inject
	@Path("context:/css/login.css")
	private Asset styles;

	@Inject
	private CoreDAO dao;

	private SimpleDateFormat format = new SimpleDateFormat(
			Constants.DATE_FORMAT);

	/*
	 * PERSISTS
	 */

	@Persist
	@Property
	private Notice notice;

	public static final String IMAGE_PATH_EMP = "/images/profile/";

	void beginRender() {
		notice = dao.getContent();

		if (notice == null)
			notice = new Notice();
	}

	@CommitAfter
	public Object onActionFromTynamoLoginForm() throws IOException {
		
		System.err.println("test");

		Subject currentUser = securityService.getSubject();

		if (currentUser == null) {
			throw new IllegalStateException("Subject can`t be null");
		}

		UsernamePasswordToken token = new UsernamePasswordToken(username,
				password);
		User user = dao.getUserByUsername(username);
		System.err.println("user: " + user);
		String encPass = new Sha1Hash(password, user.getPasswordSalt()).toString();;
		System.err.println("pass:" + encPass);

		try {
			currentUser.login(token);

			loginState.setLogin(username);
			loginState.setUser(dao.getUserByUsername(username));
			
			UserLoginHistory history = new UserLoginHistory();
			history.setUser(loginState.getUser());
			history.setLoginDate(new Date());
			dao.saveOrUpdateObject(history);
			
		} catch (UnknownAccountException e) {

			loginMessage = messages.get("accountNotCreated");

			return null;
		} catch (IncorrectCredentialsException e) {

			loginMessage = messages.get("passwordIncorrect");

			return null;
		} catch (LockedAccountException e) {

			loginMessage = messages.get("accountLocked");

			return null;
		} catch (AuthenticationException e) {

			loginMessage = messages.get("loginError");

			return null;
		}

		SavedRequest savedRequest = WebUtils
				.getAndClearSavedRequest(requestGlobals.getHTTPServletRequest());

		if (savedRequest != null
				&& savedRequest.getMethod().equalsIgnoreCase("GET")) {
			try {
				response.sendRedirect(savedRequest.getRequestUrl());
				return null;
			} catch (IOException e) {
				logger.warn("Can't redirect to saved request.");
				return pageService.getSuccessPage();
			}
		} else {

			return pageService.getSuccessPage();
		}
	}

	public void setLoginMessage(String loginMessage) {
		this.loginMessage = loginMessage;
	}

	public String getLoginMessage() {
		if (StringUtils.hasText(loginMessage)) {
			return messages.get("usernameOrPasswordIncorrect");
		} else {
			return " ";
		}
	}

	public String getContent() {
		if (dao.getLastNotice() != null)
			return format.format(dao.getLastNotice().getCreatedDate()) + " - "
					+ dao.getLastNotice().getContent();
		return "";
	}

	public Asset getStyles() {
		return styles;
	}

	public String getDateFormat() {
		return Constants.DATE_FORMAT;
	}

}
