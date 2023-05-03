package fr.eservices.drive.util;

import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("sha256")
public class SHA256Checker implements PasswordChecker {

	@Override
	public String encode(String stringToHash) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(stringToHash.getBytes());
			return new String( java.util.Base64.getEncoder().encode(md.digest()) );
		} catch (Exception e) {
			throw new RuntimeException("SHA256 algorith not available", e);
		}
	}

}
