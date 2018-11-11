package org.tbalden.cryptokit;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Hasher {
	
	public static BigInteger hash(String pwd) {
		try {
		    MessageDigest md = MessageDigest.getInstance("SHA-256");
		    md.update(pwd.getBytes()); 
		    byte byteData[] = md.digest(); 
		    BigInteger number = new BigInteger(1,byteData);
		    return number;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
