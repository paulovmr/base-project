package com.baseproject.test.config;

import org.junit.Before;

import com.baseproject.util.http.Browser;
import com.baseproject.util.http.Response;
import com.baseproject.util.utils.DatabaseUtils;
import com.baseproject.util.utils.FixtureUtils;

//@RunWith(TestRunner.class)
public abstract class BaseTest {
	
	protected Browser browser = new Browser();
	
	protected FixtureUtils fx = new FixtureUtils(browser);
	
	protected Long id;
	
	protected Response lastResponse() {
		return browser.lastResponse();
	}
	
	@Before
	public void testFinished() {
		System.out.println("============= begin3");
		DatabaseUtils.cleanAllTables();
	}
}
