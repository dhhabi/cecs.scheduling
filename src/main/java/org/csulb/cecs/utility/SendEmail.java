package org.csulb.cecs.utility;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

	public static void sendMail(InternetAddress[] to, String subject, String msg) throws AddressException, MessagingException {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true");
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("cecscsulb@gmail.com",
								"preet@000");// SenderID and Password.
					}
				});
		
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("cecscsulb@gmail.com"));// Sender
																			// Id.
			//message.addRecipients(Message.RecipientType.TO, new InternetAddress(to));
			message.addRecipients(Message.RecipientType.TO, to);
			message.setSubject(subject);
			message.setText(msg,"utf-8", "html");
			// send message.
			Transport.send(message);
			// JOptionPane.showMessageDialog(null, "Mail Sent Successfully");
		
	}

}
