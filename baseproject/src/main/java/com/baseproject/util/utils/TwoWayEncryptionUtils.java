package com.baseproject.util.utils;

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

public class TwoWayEncryptionUtils {

	private static final String ALGORITHM = "AES/CTS/PKCS5PADDING";
	private static final byte[] SALT = new byte[] { 0x6b, 0x4a, 0x34, 0x6d, 0x0b, (byte) 0xc6, (byte) 0xb0, (byte) 0xfd };
	private static final char[] KEY = "adHG769FDVd07d9dbcV0980VDs09sd80HjK70sx8sp2U80NB".toCharArray();

	public static String encrypt(String text) {
		try {
			Cipher cipher = createCipher(Cipher.ENCRYPT_MODE);
		    
			byte[] encryptedText = cipher.doFinal(text.getBytes());
			String encodedText = Base64.getEncoder().encodeToString(encryptedText);
			
		    return encodedText;
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String decrypt(String encryptedText) {
		try {
			Cipher cipher = createCipher(Cipher.DECRYPT_MODE);
			
			byte[] decodedText = Base64.getDecoder().decode(encryptedText);
		    byte[] text = cipher.doFinal(decodedText);
		    
		    return new String(text);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static SecretKey generateSecretKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
	    KeySpec keySpec = new PBEKeySpec(KEY, SALT, 2048, 128);
	    
	    SecretKey tmpKey = keyFactory.generateSecret(keySpec);
	    SecretKey secretKey = new SecretKeySpec(tmpKey.getEncoded(), "AES");
	    
	    return secretKey;
	}

	private static Cipher createCipher(int mode) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		SecretKey secretKey = generateSecretKey();
	    Cipher cipher = Cipher.getInstance(ALGORITHM);
		IvParameterSpec parameterSpec = new IvParameterSpec(new byte[cipher.getBlockSize()]);
	    cipher.init(mode, secretKey, parameterSpec);
	    
		return cipher;
	}
}
