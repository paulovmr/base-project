package com.baseproject.util.utils;

import org.junit.Assert;
import org.junit.Test;

public class TwoWayEncryptionUtilsTest {

	@Test
	public void encryptionTest() {
		String text = "{ \"text\" : \"This is a test text&%$!@,.!\\\"\\\"''\" }";
		
		String encryptedText = OneWayEncryptionUtils.encode(text);
		
		Assert.assertTrue(OneWayEncryptionUtils.match(text, encryptedText));
	}
}
