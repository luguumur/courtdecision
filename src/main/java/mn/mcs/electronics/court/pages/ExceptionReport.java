
package mn.mcs.electronics.court.pages;

import java.util.Date;

import mn.mcs.electronics.court.aso.LoginState;
import mn.mcs.electronics.court.util.EmailUtil;
import org.apache.log4j.Logger;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.services.ExceptionReporter;

public class ExceptionReport implements ExceptionReporter {

	private Throwable _exception;

	private Logger log = Logger.getLogger("court_hr");
	
	@SessionState
	private LoginState loginState;
	
	void beginRender() {
		
	}

	/*public void contributeExceptionHandler(MappedConfiguration<Class, Class> configuration) {
		configuration.add(SmtpNotRespondingException.class, ServiceFailure.class);
	}
	
	public void contributeExceptionHandler(OperationQueue operationQueue, MappedConfiguration<Class, Class> configuration) {
        final ExceptionHandlerAssistant assistant = new ExceptionHandlerAssistant() {
            @Override
            public Object handleRequestException(Throwable exception, List<Object> exceptionContext) throws IOException {
                ServiceException serviceException = (ServiceException)exception;
                if (serviceException.isInterruptedOperationRecoverable()) {
                	operationQueue.add(serviceException.getInterruptedOperation());
                	return OperationScheduled.class;
                }
                else return ServiceUnavailable.class;
            }
        };
        configuration.add(ServiceException.class, assistant);
}*/
	
	public void reportException(Throwable exception){
		this._exception = exception;

		EmailUtil util = new EmailUtil();
		StackTraceElement[] array;
		array = (exception!=null && exception.getCause()!=null)?exception.getCause().getStackTrace(): new StackTraceElement[200];
		
		String email = exception.toString() +  "\n " +exception.getMessage()+"\n"+
				"---------------------------------------------------\n\n";
		email += email + "Хэрэглэгчийн нэр : " + loginState.getEmployee().getFirstName() +
				"/"+loginState.getOrganization().getName()+" - "+loginState.getEmployee().getFullNameFirstChar()+"/\n";
		email += email + "Огноо: " + new Date() + " \n\n" ;
		
		if(exception!=null && exception.getCause()!=null){
			for(int i = 0; i < exception.getCause().getStackTrace().length;i++){
				email =  email + "\n " + array[i];
			}
		}
		
		try {
			util.sendExceptionEmail(email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writeInfoLog();
	
	}

	private void writeInfoLog() {
		log.info("Exception Report:");
		log.info(_exception.toString());
	}
	
}
