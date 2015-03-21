package com.baseproject.test.config;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.baseproject.service.services.FeatureServiceTest;
import com.baseproject.service.services.FixtureServiceTest;
import com.baseproject.util.utils.DatabaseUtils;

@RunWith(Suite.class)
@SuiteClasses({ FeatureServiceTest.class, FixtureServiceTest.class })
public class TestsSuite {
	
	final static Logger logger = Logger.getLogger(TestsSuite.class);

	private static TomcatEmbeddedRunner tomcat;

	@BeforeClass
	public static void testRunStarted() {
		logger.info("\n\n\n\u001b[0;38;2;233;35;35m Starting Tomcat... \u001b[m\n\n\n");
		
		DatabaseUtils.dropAllTables();
		tomcat = new TomcatEmbeddedRunner();
		tomcat.start();
		
		logger.info("Tomcat is up! Running tests...");
	}

	@AfterClass
	public static void testRunFinished() {
		logger.info("Tests finished! Stoping Tomcat...");
		
		tomcat.stop();

		logger.info("Tomcat is down!");
	}
}
