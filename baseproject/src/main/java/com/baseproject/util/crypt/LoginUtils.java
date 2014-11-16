package com.baseproject.util.crypt;

import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.mindrot.jbcrypt.BCrypt;

public class LoginUtils {

	public static final String FIRST_SEPARATOR = "###";
	public static final String SECOND_SEPARATOR = "|||||";
	private static final String ALGORITHM = "AES/CTS/PKCS5PADDING";
	private static final byte[] SALT = new byte[] { 0x5a, 0x42, 0x54, 0x6d, 0x0d, (byte) 0xd6, (byte) 0xa0, (byte) 0xed };

	public static String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
	}
	
	public static boolean match(String password, String hash) {
		return BCrypt.checkpw(password, hash);
	}
	
	public static String generateToken(String username, String password) {
		return username + FIRST_SEPARATOR + encrypt(username, password);
	}

	public static String encrypt(String username, String password) {
		try {
			Cipher cipher = getCipher(username, Cipher.ENCRYPT_MODE);
		    String usernamePassword = username + SECOND_SEPARATOR + password;
		    
			byte[] encryptedUsernamePassword = cipher.doFinal(usernamePassword.getBytes());
			String encodedUsernamePassword = Base64.getEncoder().encodeToString(encryptedUsernamePassword);
			
		    return encodedUsernamePassword;
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String decrypt(String username, String encryptedUsernamePassword) {
		try {
			Cipher cipher = getCipher(username, Cipher.DECRYPT_MODE);
			
			byte[] decodedUsernamePassword = Base64.getDecoder().decode(encryptedUsernamePassword);
		    byte[] decryptedUsernamePassword = cipher.doFinal(decodedUsernamePassword);
		    
		    return new String(decryptedUsernamePassword);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static SecretKey getSecretKey(String username) throws NoSuchAlgorithmException, InvalidKeySpecException {
	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
	    KeySpec keySpec = new PBEKeySpec(username.toCharArray(), SALT, 2048, 128);
	    
	    SecretKey tmpKey = keyFactory.generateSecret(keySpec);
	    SecretKey secretKey = new SecretKeySpec(tmpKey.getEncoded(), "AES");
	    
	    return secretKey;
	}

	private static Cipher getCipher(String username, int mode) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		SecretKey secretKey = getSecretKey(username);
	    Cipher cipher = Cipher.getInstance(ALGORITHM);
	    cipher.init(mode, secretKey, new IvParameterSpec(new byte[cipher.getBlockSize()]));
	    
		return cipher;
	}
}
