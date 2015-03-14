package com.baseproject.service.services;

import java.lang.reflect.Method;
import java.net.URI;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.baseproject.model.common.BaseEntity;
import com.baseproject.model.common.Repository;
import com.baseproject.service.common.BaseService;
import com.baseproject.util.utils.DatabaseUtils;
import com.baseproject.util.utils.JsonUtils;
import com.baseproject.util.utils.ReflectionUtils;
import com.baseproject.util.validation.ValidationException;

@Path(FixtureService.PATH)
public class FixtureService extends BaseService {
	
	public static final String PATH = "/fixtures";

	@GET
	@Path("/{tableName}/{id}")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetch(@PathParam("tableName") String tableName, @PathParam("id") Long id) {
		Class<?> clazz = getClassFromTable(tableName);
		BaseEntity<?> obj;
				
		try {
			Method m = clazz.getMethod("repository");
			Repository<?> repository = (Repository<?>) m.invoke(null);
			obj = repository.fetch(id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		if (obj != null) {
			return Response.status(200).entity(clazz.cast(obj)).build();
		} else {
			return Response.status(404).build();
		}
	}
	 
	@POST
	@Path("/{tableName}")
	@PermitAll
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(@PathParam("tableName") String tableName, String json) {
		Class<?> clazz = getClassFromTable(tableName);
		BaseEntity<?> obj = (BaseEntity<?>) JsonUtils.fromJson(json, clazz);
				
		try {
			obj.prepareForPersist();
			obj.save();
		} catch (ValidationException e) {
			return Response.status(422).entity(e.getValidationFailures()).build();
		}
		
		Long id = obj.getId();
		URI location = UriBuilder.fromPath(PATH + "/" + tableName + "/" + id).build();
		return Response.status(200).location(location).build();
	}
	 
	@PUT
	@Path("/{tableName}/{id}")
	@PermitAll
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("tableName") String tableName, @PathParam("id") Long id, String json) {
		Class<?> clazz = getClassFromTable(tableName);
		BaseEntity<?> obj = (BaseEntity<?>) JsonUtils.fromJson(json, clazz);
		try {
			obj.prepareForUpdate();
			obj.setId(id);
			obj.save();
		} catch (ValidationException e) {
			return Response.status(422).entity(e.getValidationFailures()).build();
		}
		
		return Response.status(200).build();
	}
	 
	@DELETE
	@Path("/{tableName}/{id}")
	@PermitAll
	public Response delete(@PathParam("tableName") String tableName, @PathParam("id") Long id) {
		Class<?> clazz = getClassFromTable(tableName);
		BaseEntity<?> obj;
		
		try {
			Method m = clazz.getMethod("repository");
			Repository<?> repository = (Repository<?>) m.invoke(null);
			obj = repository.fetch(id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		if (obj != null) {
			obj.remove();
			return Response.status(200).build();
		} else {
			return Response.status(404).build();
		}
	}
	 
	@DELETE
	@Path("/")
	@PermitAll
	public Response deleteAll() {
		DatabaseUtils.cleanAllTables();
		return Response.status(200).build();
	}
	
	private Class<?> getClassFromTable(String tableName) {
		return ReflectionUtils.getClassByTableName().get(tableName);
	}
}
