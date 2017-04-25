package mn.mcs.electronics.court.services;


import java.io.IOException;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.dao.hibernate.CoreDAOHibernate;
import mn.mcs.electronics.court.dao.hibernate.UserRealm;

import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.hibernate.HibernateEntityPackageManager;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.services.BeanBlockContribution;
import org.apache.tapestry5.services.BeanBlockSource;
import org.apache.tapestry5.services.DisplayBlockContribution;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;
import org.slf4j.Logger;
import org.tynamo.builder.Builder;
import org.tynamo.builder.BuilderDirector;
import org.tynamo.security.SecuritySymbols;
import org.tynamo.security.services.SecurityFilterChainFactory;
import org.tynamo.security.services.impl.SecurityFilterChain;


/**
 * This module is automatically included as part of the Tapestry IoC Registry, it's a good place to configure and extend
 * Tapestry, or to place your own service definitions.
 */
//@SubModule(value = { SecurityModule.class, SeedEntity.class, FederatedAccountsModule.class })
public class AppModule {

//	private static final String PATH_PREFIX = "facebook";
//	private static String version = ModuleProperties.getVersion(AppModule.class);

	
	public static void bind(ServiceBinder binder) {
		
		binder.bind(CoreDAO.class,CoreDAOHibernate.class);
		binder.bind(AuthorizingRealm.class, UserRealm.class).withId(UserRealm.class.getSimpleName());
	//	binder.bind(FederatedAccountService.class, DefaultHibernateFederatedAccountServiceImpl.class);
		
//		binder.bind(FederatedAccountService.class, FederatedAccountServiceExample.class);
//		binder.bind(AuthorizingRealm.class, FederatedAccountsAuthorizingRealm.class).withId(
//			FederatedAccountsAuthorizingRealm.class.getSimpleName());
	}

	public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration) {
		// Contributions to ApplicationDefaults will override any contributions to
		// FactoryDefaults (with the same key). Here we're restricting the supported
		// locales to just "en" (English). As you add localised message catalogs and other assets,
		// you can extend this list of locales (it's a comma separated series of locale names;
		// the first locale name is the default when there's no reasonable match).

		configuration.add(SymbolConstants.SUPPORTED_LOCALES, "mn");

		// The factory default is true but during the early stages of an application
		// overriding to false is a good idea. In addition, this is often overridden
		// on the command line as -Dtapestry.production-mode=false
		configuration.add(SymbolConstants.PRODUCTION_MODE, "false");

//		configuration.add(SymbolConstants.SECURE_ENABLED, "true");
		
	
		// The application version number is incorprated into URLs for some
		// assets. Web browsers will cache assets because of the far future expires
		// header. If existing assets are changed, the version number should also
		// change, to force the browser to download new versions.
		configuration.add(SymbolConstants.APPLICATION_VERSION, "0.0.1-SNAPSHOT");
		
		configuration.add(SecuritySymbols.LOGIN_URL, "/login");
		configuration.add(SecuritySymbols.SUCCESS_URL, "/home");
	//	configuration.add(JQuerySymbolConstants.SUPPRESS_PROTOTYPE, "false");
		configuration.add("tapestry.page-pool.hard-limit", "200");
		configuration.add("tapestry.page-pool.soft-limit", "50");
		
		/*configuration.add(SymbolConstants.HOSTNAME, "tynamo-federatedaccounts.tynamo.org");
		configuration.add(SymbolConstants.HOSTPORT, "80");
		*/
		/*configuration.add(FacebookRealm.FACEBOOK_CLIENTID, "<your oauth client id>");
		configuration.add(FacebookRealm.FACEBOOK_CLIENTSECRET, "<your oauth client secret>");
	*/
	//	configuration.add(JQuerySymbolConstants.SUPPRESS_PROTOTYPE, "false");
		//configuration.add(JQuerySymbolConstants.JQUERY_UI_DEFAULT_THEME, "smoothness");
	}
	
	@Contribute(HttpServletRequestFilter.class)
	public static void contributeSecurityConfiguration(Configuration<SecurityFilterChain> configuration,
			SecurityFilterChainFactory factory) {
		
	
		configuration.add(factory.createChain("/css/**").add(factory.anon()).build());
		configuration.add(factory.createChain("/images/**").add(factory.anon()).build());
		configuration.add(factory.createChain("/assets/**").add(factory.anon()).build());
		
		configuration.add(factory.createChain("/login.tynamologinform").add(factory.anon()).build());
		configuration.add(factory.createChain("/login.kaptchaimage:image").add(factory.anon()).build());
		
		configuration.add(factory.createChain("/employee/").add(factory.authc()).add(factory.roles(), "admin").build());
		configuration.add(factory.createChain("/organization/").add(factory.authc()).add(factory.roles(), "admin").build());
		configuration.add(factory.createChain("/configuration/").add(factory.authc()).add(factory.roles(), "admin").build());
		configuration.add(factory.createChain("/report/").add(factory.authc()).add(factory.roles(), "admin").build());
		
		configuration.add(factory.createChain("/**").add(factory.authc()).build());
		
	}	
	
	/*public static void contributeSeedEntity(OrderedConfiguration<Object> configuration) {
		User localUser = new User();
		localUser.setUsername("user");
		localUser.setPassword("user");
		configuration.add("localuser", localUser);
		User fakeFederatedUser = new User();
		fakeFederatedUser.setUsername("fbuser");

		//fakeFederatedUser.setFacebookUserId(0L);
		configuration.add("fakeuser", fakeFederatedUser);
	}*/
	
	public static void contributeWebSecurityManager(Configuration<Realm> configuration,
			@InjectService("UserRealm") AuthorizingRealm userRealm) {
		// FacebookRealm is automatically contributed as long as federatedsecurity is on the classpath
		configuration.add(userRealm);
	}
	
	/*public static void contributeFederatedAccountService(MappedConfiguration<String, Object> configuration) {
		configuration.add("*", User.class);
	//	configuration.add("facebook.id", "facebookId");
	//	configuration.add("twitter.id", "twitterId");
	}*/
	
	@Contribute(BeanBlockSource.class)
	public static void addCustomBlocks(Configuration<BeanBlockContribution> configuration)
	{
		configuration.add(new DisplayBlockContribution("boolean", "blocks/DisplayBlocks", "check"));
		configuration.add(new DisplayBlockContribution("single-valued-association", "blocks/DisplayBlocks", "showPageLink"));
		configuration.add(new DisplayBlockContribution("many-valued-association", "blocks/DisplayBlocks", "showPageLinks"));
	}

	/**
	 * By default tapestry-hibernate will scan
	 * InternalConstants.TAPESTRY_APP_PACKAGE_PARAM + ".entities" (witch is equal to "mn.mcs.electronics.example.example.entities")
	 * for annotated entity classes.
	 *
	 * Contributes the package "mn.mcs.electronics.example.example.model" to the configuration, so that it will be
	 * scanned for annotated entity classes.
	 */
	@Contribute(HibernateEntityPackageManager.class)
	public static void addPackagesToScan(Configuration<String> configuration)
	{
//		If you want to scan other packages add them here:
		configuration.add("mn.mcs.electronics.court.entities");
	
	}

	/**
	 * Contributes Builders to the BuilderDirector's builders map.
	 * Check GOF's <a href="http://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a>
	 */
	@Contribute(BuilderDirector.class)
	public static void addBuilders(MappedConfiguration<Class, Builder> configuration)
	{
//		configuration.add(org.tynamo.examples.recipe.model.Recipe.class, new RecipeBuilder());
	}
	
	/* Path-г хаах бол */
	/*  public static void contributeIgnoredPathsFilter(Configuration<String> configuration)
	  {
	    configuration.add("/employee/");
	  }*/
	  

	/**
	 * This is a service definition, the service will be named "TimingFilter".
	 * The interface, RequestFilter, is used within the RequestHandler service
	 * pipeline, which is built from the RequestHandler service configuration.
	 * Tapestry IoC is responsible for passing in an appropriate Log instance.
	 * Requests for static resources are handled at a higher level, so this
	 * filter will only be invoked for Tapestry related requests.
	 * 
	 * <p>
	 * Service builder methods are useful when the implementation is inline as
	 * an inner class (as here) or require some other kind of special
	 * initialization. In most cases, use the static bind() method instead.
	 * 
	 * <p>
	 * If this method was named "build", then the service id would be taken from
	 * the service interface and would be "RequestFilter". Since Tapestry
	 * already defines a service named "RequestFilter" we use an explicit
	 * service id that we can reference inside the contribution method.
	 */
	public RequestFilter buildTimingFilter(final Logger log) {
		return new RequestFilter() {
			public boolean service(Request request, Response response,
					RequestHandler handler) throws IOException {
				long startTime = System.currentTimeMillis();

				try {
					// The responsibility of a filter is to invoke the
					// corresponding method
					// in the handler. When you chain multiple filters together,
					// each filter
					// received a handler that is a bridge to the next filter.

					return handler.service(request, response);
				} finally {
					long elapsed = System.currentTimeMillis() - startTime;

					log.info(String.format("Request time: %d ms", elapsed));
				}
			}
		};
	}
	
}
