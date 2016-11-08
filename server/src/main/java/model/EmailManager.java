package model;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by tongshen on 11/2/16.
 */

public class EmailManager {
    static final String FROM = "acal.techteam@gmail.com";   // Replace with your "From" address. This address must be verified.
    // sandbox, this address must be verified.

//    static final String BODY = "This email was sent through the Amazon SES SMTP interface by using Java.";
//    static final String SUBJECT = "Amazon SES test (SMTP interface accessed using Java)";

    // Supply your SMTP credentials below. Note that your SMTP credentials are different from your AWS credentials.
    static final String SMTP_USERNAME = "AKIAJMIDWWBA4IJ52LAA";  // Replace with your SMTP username.
    static final String SMTP_PASSWORD = "Amw6YMGORQe4BbP7Ub1xTxq9FNevsL7FGARJK4honlc6";  // Replace with your SMTP password.

    // Amazon SES SMTP host name. This example uses the US West (Oregon) region.
    static final String HOST = "email-smtp.us-west-2.amazonaws.com";

    // The port you will connect to on the Amazon SES SMTP endpoint. We are choosing port 25 because we will use
    // STARTTLS to encrypt the connection.
    static final int PORT = 25;

    public static void main(String[] args) throws Exception{
        InternetAddress sender = new InternetAddress(FROM);
        Address[] recipients = new Address[3];
        recipients[0] = new InternetAddress("shent3@uw.edu");
        recipients[1] = new InternetAddress("gaoz6@cs.washington.edu");
        recipients[2] = new InternetAddress("ziyaoh@cs.washington.edu");
        Email e = new Email("Verification", "no-reply: testing email module", "This is a auto-generated test message. Please ignore it. Testing email sending to multiple users", sender, recipients);
        send(e);
    }

    public static void send(Email e) throws Exception{
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtp.port", PORT);

        // Set properties indicating that we want to use STARTTLS to encrypt the connection.
        // The SMTP session will begin on an unencrypted connection, and then the client
        // will issue a STARTTLS command to upgrade to an encrypted connection.
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");

        // Create a Session object to represent a mail session with the specified properties.
        Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information.
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(e.getSender());
        msg.setRecipients(Message.RecipientType.TO, e.getRecipients());
        msg.setSubject(e.getTitle());
        msg.setContent(e.getHtmlContent(),"text/html");

        // Create a transport.
        Transport transport = session.getTransport();

        // Send the message.
        try
        {
            System.out.println("Attempting to send an email through the Amazon SES SMTP interface...");

            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);

            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
        }
        catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }
        finally
        {
            // Close and terminate the connection.
            transport.close();
        }
    }
}
