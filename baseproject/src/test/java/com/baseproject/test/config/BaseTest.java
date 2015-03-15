package com.baseproject.test.config;

import javax.ws.rs.core.MediaType;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.baseproject.model.common.BaseEntity;
import com.baseproject.util.utils.DatabaseUtils;
import com.baseproject.util.utils.ReflectionUtils;

public class BaseTest {
	
	private static TomcatEmbeddedRunner tomcat;
	
	protected Browser browser = new Browser();
	
	@BeforeClass
	public static void setupSuite() {
		DatabaseUtils.dropAllTables();
		tomcat = new TomcatEmbeddedRunner();
		tomcat.start();
	}
	
	@Before
	public void setupTest() {
		DatabaseUtils.cleanAllTables();
	}
	
	@AfterClass
	public static void endSuite() {
		tomcat.stop();
	}
	
	public <T extends BaseEntity<T>> T fetch(Class<T> clazz, Long id) {
		Response response = browser.get("/fixtures/" + getTableFromClass(clazz) + "/%d", id);
		return response.process(clazz);
	}
	
	public <T extends BaseEntity<T>> Long insert(Class<T> clazz, T entity) {
		Response response = browser.post(MediaType.TEXT_PLAIN, entity, "/fixtures/" + getTableFromClass(clazz));
		response.process();
		if (response.getLocation() != null) {
			String[] path = response.getLocation().split("/");
			return Long.parseLong(path[path.length - 1]);
		}
		
		return null;
	}
	
	public <T extends BaseEntity<T>> void update(Class<T> clazz, T entity) {
		Response response = browser.put(MediaType.TEXT_PLAIN, entity, "/fixtures/" + getTableFromClass(clazz) + "/%d", entity.getId());
		response.process();
	}
	
	public <T extends BaseEntity<T>> void delete(Class<T> clazz, T entity) {
		Response response = browser.delete("/fixtures/" + getTableFromClass(clazz) + "/%d", entity.getId());
		response.process();
	}
	
	private <T extends BaseEntity<T>> String getTableFromClass(Class<T> clazz) {
		return ReflectionUtils.getTableFromClass(clazz);
	}
}
