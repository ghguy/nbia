import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

/**
 * 
 */

/**
 * @author lethai
 *
 */
public class SendMail {
	private static Logger logger = Logger.getLogger(SendMail.class);
    public static String MAIL_PROPERTIES = "mail.properties";
    Properties props = null, mailProps;
    String subject;
    String unformattedBody1;
    String unformattedBody2;
    String unformattedBody3;
    String unformattedBody4;
    String unformattedBody5;
    String appSupportNumber;
    String techSupportStartTime;
    String techSupportEndTime;
    String host;
    String from;

    public SendMail() {
    	
//    	get system properties
        props = System.getProperties();

        //	This will get the values from the properties files
        mailProps = new Properties();
        InputStream in = Thread.currentThread().getContextClassLoader()
                               .getResourceAsStream(MAIL_PROPERTIES);

        try {
            mailProps.load(in);
        } catch (IOException ioe) {
            logger.error("Unable to load NCIA properties", ioe);
        }
        
        subject = mailProps.getProperty("subject");
        unformattedBody2 = mailProps.getProperty("body2");
        unformattedBody3 = mailProps.getProperty("body3");
        unformattedBody4 = mailProps.getProperty("body4");
        unformattedBody5 = mailProps.getProperty("body5");
        
        appSupportNumber = mailProps.getProperty("techSupportNumber");
        techSupportStartTime = mailProps.getProperty("techSupportStartTime");
        techSupportEndTime = mailProps.getProperty("techSupportEndTime");
                    
        host = mailProps.getProperty("host");
        from = mailProps.getProperty("fromAddress");
        
    	
    }

    public synchronized void sendMail(String resetPassword, String sendTo, String userFirstName) {
        try {       	           
            
        	unformattedBody1 = mailProps.getProperty("body1");
//          Part 1 is always included  
            String messageBody = new MessageFormat(unformattedBody1).format(new String[] {userFirstName   });

           messageBody += new MessageFormat(unformattedBody2).format(new String[] {
                    
            });
//         Part 3 always appears  
           messageBody += new MessageFormat(unformattedBody3).format(new String[] {
                   sendTo, resetPassword  });
           
           messageBody += new MessageFormat(unformattedBody4).format(new String[] {
                   
           });

            // Part 4 always appears  
            messageBody += new MessageFormat(unformattedBody5).format(new String[] {
                    appSupportNumber, techSupportStartTime, techSupportEndTime
                });
           
            
            // Set up mail server
            props.put("mail.smtp.host", host);

            //Get session
            Session session = Session.getDefaultInstance(props, null);

            //Define Message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,
                new InternetAddress(sendTo));
            message.setSubject(subject);
            message.setText(messageBody);
            
            System.out.println(messageBody);
            //logger.info(messageBody);

            //Send Message
            Transport.send(message);
            System.out.println("message sent");
        } catch (Exception e) {
            logger.error("Send Mail error", e);
        } //catch
    } //send mail
    

     
}
