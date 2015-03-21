package com.baseproject.util.utils;

import javax.ws.rs.core.MediaType;

import com.baseproject.model.common.BaseEntity;
import com.baseproject.util.http.Browser;
import com.baseproject.util.http.Response;

public class FixtureUtils {
	
	private Browser browser;
	
	public FixtureUtils(Browser browser) {
		this.browser = browser;
	}
	
	public <T extends BaseEntity<T>> T fetch(Class<T> clazz, Long id) {
		Response response = browser.get("/fixtures/" + getTableFromClass(clazz) + "/%d", id);
		return response.process(clazz);
	}
	
	public <T extends BaseEntity<T>> Long insert(T entity) {
		Class<T> clazz = getClass(entity);
		Response response = browser.post(MediaType.TEXT_PLAIN, entity, "/fixtures/" + getTableFromClass(clazz));
		response.process();
				
		return response.getId();
	}
	
	public <T extends BaseEntity<T>> void update(T entity) {
		Class<T> clazz = getClass(entity);
		Response response = browser.put(MediaType.TEXT_PLAIN, entity, "/fixtures/" + getTableFromClass(clazz) + "/%d", entity.getId());
		response.process();
	}
	
	public <T extends BaseEntity<T>> void delete(Class<T> clazz, Long id) {
		Response response = browser.delete("/fixtures/" + getTableFromClass(clazz) + "/%d", id);
		response.process();
	}
	
	public <T extends BaseEntity<T>> void delete(T entity) {
		Class<T> clazz = getClass(entity);
		delete(clazz, entity.getId());
	}
	
	private <T extends BaseEntity<T>> String getTableFromClass(Class<T> clazz) {
		return ReflectionUtils.getTableFromClass(clazz);
	}

	private <T extends BaseEntity<T>> Class<T> getClass(T entity) {
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) entity.getClass();
		return clazz;
	}
}
