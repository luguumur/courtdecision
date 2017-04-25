package mn.mcs.electronics.court.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * JavaScript Confirm Mixin.
 * @author Christophe Ribeiro
 */
@Import(library = "context:script/confirm.js")
public class ConfirmDelete {
	@Parameter(value = "Та итгэлтэй байна уу?", defaultPrefix = BindingConstants.LITERAL)
	private String message;

	@Inject
	private JavaScriptSupport js;

	@InjectContainer
	private ClientElement element;

	/**
	 * Add script.
	 */
	@SuppressWarnings("deprecation")
	@AfterRender
	public void afterRender() {
		js.addInitializerCall("confirmation", new JSONObject("id", this.element.getClientId(), "message",  this.message));
	}
}
