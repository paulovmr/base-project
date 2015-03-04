package com.baseproject.test.config;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class BaseTest {
	
	private static TomcatEmbeddedRunner tomcat;
	
	@BeforeClass
	public static void setupSuite() {
		DatabaseTestUtils.dropAllTables();
		tomcat = new TomcatEmbeddedRunner();
		tomcat.start();
	}
	
	@Before
	public void setupTest() {
		DatabaseTestUtils.cleanAllTables();
	}
	
	@AfterClass
	public static void endSuite() {
		tomcat.stop();
	}
}
