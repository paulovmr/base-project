package com.baseproject.test.config;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.baseproject.service.services.FeatureServiceIT;
import com.baseproject.service.services.FixtureServiceIT;
import com.baseproject.util.utils.DatabaseUtils;
import com.baseproject.util.utils.TerminalColors;

@RunWith(Suite.class)
@SuiteClasses({ 
	FeatureServiceIT.class, 
	FixtureServiceIT.class 
})
public class IntegrationTestsSuite {
	
	final static Logger logger = Logger.getLogger(IntegrationTestsSuite.class);

	private static TomcatEmbeddedRunner tomcat;

	@BeforeClass
	public static void testRunStarted() {
		log("   ~>   Starting Tomcat...");
		
		DatabaseUtils.dropAllTables();
		tomcat = new TomcatEmbeddedRunner();
		tomcat.start();
		
		log("   ~>   Tomcat is up! Running tests...");
	}

	@AfterClass
	public static void testRunFinished() {
		log("   ~>   Tests finished! Stoping Tomcat...");
		
		tomcat.stop();

		log("   ~>   Tomcat is down!");
	}
	
	private static void log(String message) {
		TerminalColors.blue();
		System.out.println("\n" + message + "\n");
		TerminalColors.finish();
	}
}
