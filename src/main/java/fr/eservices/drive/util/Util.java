package fr.eservices.drive.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import fr.eservices.drive.model.User;
import fr.eservices.drive.repository.UserRepository;

public class Util {
	
	public static HttpSession session() {
	    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    return attr.getRequest().getSession(true); // true == allow create
	}
	
	public static User getMyself() {
		HttpSession session = Util.session();
        User currentUser = (User) session.getAttribute("user");
		return currentUser;
	}
	
	public static Boolean iAmSuspended(UserRepository userRepository) { 	
		User myself = getMyself();
		if (myself == null) {return false;}
		User updatedMyself = userRepository.findById(myself.getId());
		return  updatedMyself == null ? false : updatedMyself.getIsBanned();
	}
	
	public static String applyBanishment(Model model) {
		HttpSession session = Util.session();
		model.addAttribute("error", "Your account have been suspended.");
		session.removeAttribute("user");
		return "redirect:/login.html";
	}
	
	
	public static void sendEmail(String to, String token) {
		String from = "contact@e-drive.com";
		String host = "localhost";
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties);
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Conformation d'inscription sur votre site préféré.");
			message.setText("Veuillez cliquer sur ce lien pour confirmer votre inscription : http://localhost:8080/drive/confirmRegistration.html?token=" + token );
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public static Boolean isEmpty (String s) {return (s == null || "".equals(s));}

}
