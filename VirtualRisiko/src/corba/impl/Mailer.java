/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package corba.impl;



import com.sun.mail.smtp.SMTPAddressFailedException;
import com.sun.mail.smtp.SMTPAddressSucceededException;
import com.sun.mail.smtp.SMTPSendFailedException;
import com.sun.mail.smtp.SMTPTransport;
import java.util.Date;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author grunger
 */
public class Mailer {



    public static String createWelcomeMessage(String username,String password, String codiceAttivazione){
        return "Grazie per esserti registrato a Virtual Risiko "+"\n"+
                "Ecco i tuoi dati di accesso : "+"\n"+
                "username :: "+username+" , password :: "+password+" \n"+
                "Il tuo codice di attivazione è "+codiceAttivazione+"\n ."+"\n"+
                "Buon divertimento DAL TEAM DI VIRTUAL RISIKO !!";
    }

    public static String createRecoveryPasswordMessage(String username,String password){
        return "Ecco i tuoi dati di accesso : "+"\n"+
                "username :: "+username+" , password :: "+password+" \n"+
  
                "Buon divertimento DAL TEAM DI VIRTUAL RISIKO !!";
    }

    public static void inoltraEmail(final String to,final String from,final String subject,final String message){
            new Thread(new Runnable(){

            public void run() {
        // Send Email
        // This clip can be used in a button action
        // method to send an e-mail via SMTP
        // (if no authentication is required from the mailserver)
        // TODO: Create a Text Area component on the page and
        // a Button, place this code in the Button's action method
        // TODO: set the from, to addresses and the mail server
        String mailhost = "smtp.gmail.com"; // SMTP server
        String user = "virtualrisiko";                 // user ID
        String password = "accirete";              // password
		// password
        boolean auth = true;
        boolean ssl = false;
        Properties props = System.getProperties();

        if (mailhost != null) {
            props.put("mail.smtp.host", mailhost);
        }
        if (auth) {
            props.put("mail.smtp.auth", "true");
        }
        // Get a Session object
        javax.mail.Session session = javax.mail.Session.getInstance(props, null);

        // Construct the message
        javax.mail.Message msg = new MimeMessage(session);

        try {
            // Set message details
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(message);

            // Send the thing off
            SMTPTransport t = (SMTPTransport)session.getTransport(ssl ? "smtps" : "smtp");
            t.setStartTLS(true);
            try {
                if (auth) {
                    t.connect(mailhost, user, password);
                } else {
                    t.connect();
                }

                t.sendMessage(msg, msg.getAllRecipients());
            } finally {
                t.close();
            }
            log("Mail was sent successfully.");
        } catch (Exception e) {
            if (e instanceof SendFailedException) {
                MessagingException sfe = (MessagingException)e;
                if (sfe instanceof SMTPSendFailedException) {
                    SMTPSendFailedException ssfe = (SMTPSendFailedException)sfe;
                    e.printStackTrace(System.out);
                    log("Smtp_Send_Failed:");
                }
                Exception ne;
				while ((ne = sfe.getNextException()) != null && ne instanceof MessagingException) {
                    sfe = (MessagingException)ne;
                    if (sfe instanceof SMTPAddressFailedException) {
                        SMTPAddressFailedException ssfe = (SMTPAddressFailedException)sfe;
                        log("Address failed:");
                        log(ssfe.toString());
                        log("  Address: " + ssfe.getAddress());
                        log("  Command: " + ssfe.getCommand());
                        log("  Return Code: " + ssfe.getReturnCode());
                        log("  Response: " + ssfe.getMessage());
                    } else if (sfe instanceof SMTPAddressSucceededException) {
                        log("Address succeeded:");
                        SMTPAddressSucceededException ssfe = (SMTPAddressSucceededException)sfe;
                    }
				}
	    } else {
			log("Got Exception : " + e.getStackTrace());
            e.printStackTrace(System.out);
        }
    }
       }

            }).start();
     }

    private static void log(String string) {
        System.out.println(string);
    }

  

}
