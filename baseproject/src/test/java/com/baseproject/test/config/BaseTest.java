package com.baseproject.test.config;

import org.junit.Rule;

import com.baseproject.util.http.Browser;
import com.baseproject.util.http.Response;
import com.baseproject.util.utils.FixtureUtils;

public abstract class BaseTest {
	
    @Rule public TestResultHandler testWatcher = new TestResultHandler();
	
	protected Browser browser = new Browser();
	
	protected FixtureUtils fx = new FixtureUtils(browser);
	
	protected Long id;
	
	protected Response lastResponse() {
		return browser.lastResponse();
	}
}
