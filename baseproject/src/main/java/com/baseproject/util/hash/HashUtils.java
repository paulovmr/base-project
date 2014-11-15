package com.baseproject.util.hash;

import org.mindrot.jbcrypt.BCrypt;

public class HashUtils {

	public static String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
	}
	
	public static boolean match(String password, String hash) {
		return BCrypt.checkpw(password, hash);
	}
}
