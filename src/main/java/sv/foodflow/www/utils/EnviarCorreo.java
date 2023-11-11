package sv.foodflow.www.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;




public class EnviarCorreo {
	private String emailFrom = "cuponerarivas@gmail.com";
	private String passFrom = "awlyzqjlivvmbmsb";
	private String emailTo;
	private String asunto;
	private String contenido;
	
	private Properties mProperties;
	private Session mSession;
	private MimeMessage mCorreo;
	

	public EnviarCorreo(String correo, String asunto2, String cont) {
		emailTo = correo;
		asunto = asunto2;
		contenido = cont;
		mProperties = new Properties();
	}

	public void createEmail() {	
		//Protocolo para enviar un correo
		mProperties.put("mail.smtp.host", "smtp.gmail.com");
		mProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		mProperties.put("mail.smtp.starttls.enable", "true");
		mProperties.put("mail.smtp.port", "587");
		mProperties.put("mail.smtp.user", emailFrom);
		mProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
		mProperties.put("mail.smtp.auth", "true");
		
		mSession = Session.getDefaultInstance(mProperties);
		
		try {
			mCorreo = new MimeMessage(mSession);
			mCorreo.setFrom(new InternetAddress(emailFrom));
			mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
			mCorreo.setSubject(asunto);
			mCorreo.setText(contenido, "ISO-8859-1", "html");
			
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public void sendEmail() {
		try {
			Transport mTransport = mSession.getTransport("smtp");
			mTransport.connect(emailFrom, passFrom);
			mTransport.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));
			mTransport.close();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
		
}
