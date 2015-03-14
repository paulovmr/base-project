package com.baseproject.test.config;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.baseproject.model.common.BaseEntity;
import com.baseproject.util.utils.ReflectionUtils;
import com.baseproject.util.validation.ValidationException;

public class BaseTest {
	
	private static TomcatEmbeddedRunner tomcat;
	
	protected Browser browser = new Browser();
	
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
	
	public <T extends BaseEntity<T>> void fetch(Class<T> clazz, Long id) {
		browser.get(clazz, "/fixtures/" + getTableFromClass(clazz) + "/{0}", id);
	}
	
	public <T extends BaseEntity<T>> void insert(Class<T> clazz, T entity) throws ValidationException {
		browser.post(entity, "/fixtures/" + getTableFromClass(clazz));
	}
	
	public <T extends BaseEntity<T>> void update(Class<T> clazz, T entity) throws ValidationException {
		browser.put(entity, "/fixtures/" + getTableFromClass(clazz) + "/{0}", entity.getId());
	}
	
	public <T extends BaseEntity<T>> void delete(Class<T> clazz, T entity) {
		browser.delete("/fixtures/" + getTableFromClass(clazz) + "/{0}", entity.getId());
	}
	
	private <T extends BaseEntity<T>> String getTableFromClass(Class<T> clazz) {
		return ReflectionUtils.getTableFromClass(clazz);
	}
}
