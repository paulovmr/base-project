package com.baseproject.util.http;

import java.util.List;

import com.baseproject.util.utils.JsonUtils;

public class Response {

	private int code;
	
	private String entity;
	
	private String location;

	public Response(int code, String entity) {
		super();
		this.code = code;
		this.entity = entity;
	}

	public Response(int code, String entity, String location) {
		this(code, entity);
		this.location = location;
	}

	public int getCode() {
		return code;
	}
	
	public String getEntity() {
		return this.entity;
	}
	
	public String getLocation() {
		return location;
	}
	
	public Long getId() {
		if (getLocation() != null) {
			String[] path = getLocation().split("/");
			return Long.parseLong(path[path.length - 1]);
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getEntities(Class<T> clazz) {
		return JsonUtils.fromJson(entity, List.class);
	}

	public <T> T getEntity(Class<T> clazz) {
		return (T) JsonUtils.fromJson(entity, clazz);
	}
	
	public boolean isSuccess() {
		return (code >= 200) && (code < 300);
	}
	
	public void process() {
		if (!isSuccess()) {
			throw new ResponseError(this);
		}
	}
	
	public <T> T process(Class<T> clazz) {
		process();
		return getEntity(clazz);
	}
	
	public boolean hasLocation() {
		return location != null;
	}
	
	public boolean ok() {
		return code == 200;
	}
	
	public boolean created() {
		return code == 201;
	}
	
	public boolean validationError() {
		return code == 422;
	}
	
	public boolean notFound() {
		return code == 404;
	}
}
