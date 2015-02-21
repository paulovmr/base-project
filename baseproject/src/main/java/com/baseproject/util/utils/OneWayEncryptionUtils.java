package com.baseproject.util.utils;

import org.mindrot.jbcrypt.BCrypt;

public class OneWayEncryptionUtils {

	public static String encode(String text) {
        return BCrypt.hashpw(text, BCrypt.gensalt(12));
	}
	
	public static boolean match(String text, String supposedEncryptedText) {
		return BCrypt.checkpw(text, supposedEncryptedText);
	}
}
