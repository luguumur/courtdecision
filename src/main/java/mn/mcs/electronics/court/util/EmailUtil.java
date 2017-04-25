package mn.mcs.electronics.court.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {
	private String host = "smtp.gmail.com";
	private  String from = "courthrmn@gmail.com";
	private  String pass = "12b456789";
	private Properties props = System.getProperties();
	
	
	public void sendEmail(String username,int count,String ip) throws Exception{
		
	    config();

	    String[] to = {"tmaralerdene@gmail.com","jargalsuren.b@mcs.mn","tuguldur.j@mcs.mn"}; 
	    
	    Session session = Session.getDefaultInstance(props, null);
	    MimeMessage message = new MimeMessage(session);
	    message.setFrom(new InternetAddress(from));
	 
	    InternetAddress[] toAddress = new InternetAddress[to.length];
	 
	    // To get the array of addresses
	    for( int i=0; i < to.length; i++ ) { // changed from a while loop
	        toAddress[i] = new InternetAddress(to[i]);
	    }
	    System.out.println(Message.RecipientType.TO);
	 
	    for( int i=0; i < toAddress.length; i++) { // changed from a while loop
	        message.addRecipient(Message.RecipientType.TO, toAddress[i]);
	    }
	    message.setSubject(" COURT-DECISION HR системд буруу хандалт илэрлээ.");
	    message.setText(username + " хэрэглэгч " + ip + " хаягнаас" + " нууц үгээ " + count + " удаа буруу оруулж байна.");
	  
	    Transport transport = session.getTransport("smtp");
	    transport.connect(host, from, pass);
	    transport.sendMessage(message, message.getAllRecipients());
	    transport.close();
	}
	
	public void sendExceptionEmail(String exception) throws Exception{
		
		config();
		
		 //String[] to = {"maralerdene.t@itzone.mn","jargalsuren.s@itzone.mn"}; 
		 String[] to = {"",""};
		
		  Session session = Session.getDefaultInstance(props, null);
		    MimeMessage message = new MimeMessage(session);
		    message.setFrom(new InternetAddress(from));
		 
		    InternetAddress[] toAddress = new InternetAddress[to.length];
		 
		    // To get the array of addresses
		    for( int i=0; i < to.length; i++ ) { // changed from a while loop
		        toAddress[i] = new InternetAddress(to[i]);
		    }
		    System.out.println(Message.RecipientType.TO);
		 
		    for( int i=0; i < toAddress.length; i++) { // changed from a while loop
		        message.addRecipient(Message.RecipientType.TO, toAddress[i]);
		    }
		    message.setSubject(" COURT HR системд доорх алдаа гарлаа.");
		    message.setText(exception);
		  
		    Transport transport = session.getTransport("smtp");
		    transport.connect(host, from, pass);
		    transport.sendMessage(message, message.getAllRecipients());
		    transport.close();
		
	}
	
	private void config(){
		
		    props.put("mail.smtp.starttls.enable", "true"); // added this line
		    props.put("mail.smtp.host", host);
		    props.put("mail.smtp.user", from);
		    props.put("mail.smtp.password", pass);
		    props.put("mail.smtp.port", "587");
		    props.put("mail.smtp.auth", "true");
	}
}
