package com.baseproject.test.config;

import java.io.File;
import java.net.MalformedURLException;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.startup.Tomcat;

public class TomcatEmbeddedRunner {
	
	Tomcat tomcat;

	public void start() {
		tomcat = new Tomcat();
		tomcat.setPort(8081);
		tomcat.enableNaming();

		try {
			File configFile = new File("resources/tomcat/context_test.xml");
			Context context = tomcat.addWebapp("/baseproject", new File("").getAbsolutePath() + "/target/baseproject");
			context.setConfigFile(configFile.toURI().toURL());

			tomcat.start();
		} catch (ServletException e) {
			throw new RuntimeException(e);
		} catch (LifecycleException e) {
			throw new RuntimeException(e);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public void stop() {
		try {
			if (tomcat.getServer() != null && tomcat.getServer().getState() != LifecycleState.DESTROYED && tomcat.getServer().getState() != LifecycleState.STOPPED) {
        		tomcat.stop();
	        }
	        tomcat.destroy();
		} catch (LifecycleException e) {
			throw new RuntimeException(e);
		}
	}
}
