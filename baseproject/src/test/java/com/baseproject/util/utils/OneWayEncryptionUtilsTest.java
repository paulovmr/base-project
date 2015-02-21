package com.baseproject.util.utils;

import org.junit.Assert;
import org.junit.Test;

public class OneWayEncryptionUtilsTest {

	@Test
	public void encryptionTest() {
		String text = "{ \"text\" : \"This is a test text&%$!@,.!\\\"\\\"''\" }";
		
		String encryptedText = TwoWayEncryptionUtils.encrypt(text);
		String decryptedText = TwoWayEncryptionUtils.decrypt(encryptedText);
		
		Assert.assertEquals(text, decryptedText);
	}
}
