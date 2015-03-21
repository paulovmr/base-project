package com.baseproject.service.services;

import java.net.URI;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.jboss.resteasy.annotations.Form;

import com.baseproject.model.common.Loader;
import com.baseproject.model.entities.User;
import com.baseproject.model.filters.UserFilter;
import com.baseproject.service.common.BaseService;
import com.baseproject.service.data.UserData;
import com.baseproject.util.validation.ValidationException;

@Path(UserService.PATH)
public class UserService extends BaseService {
	
	public static final String PATH = "/users";

	@GET
	@Path("/")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchUsers(@Form UserFilter userFilter) {
		List<User> users = User.repository().list(userFilter);
		
		return Response.status(200).entity(UserData.unbuild(users)).build();
	}
	 
	@GET
	@Path("/{id}")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchUser(@PathParam("id") Long id, @QueryParam("load") String fields) {
		User user = User.repository().fetch(id, Loader.load(fields, User.class));
		
		if (user != null) {
			return Response.status(200).entity(UserData.unbuild(user)).build();
		} else {
			return Response.status(404).build();
		}
	}
	 
	@POST
	@Path("/")
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(UserData userData) {
		User user = UserData.build(userData);
		
		try {
			user.prepareForPersist();
			user = user.save();
		} catch (ValidationException e) {
			return Response.status(422).entity(e.getValidationFailures()).build();
		}
		
		URI location = UriBuilder.fromPath(PATH + "/" + user.getId()).build();
		return Response.status(201).location(location).build();
	}
	 
	@PUT
	@Path("/{id}")
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(@PathParam("id") Long id, UserData userData) {
		User persistedUser = User.repository().fetch(id);
		
		if (persistedUser == null) {
			return Response.status(404).build();
		}

		User user = UserData.build(userData);
		
		try {
			user.prepareForUpdate(persistedUser);
			user = user.save();
		} catch (ValidationException e) {
			return Response.status(422).entity(e.getValidationFailures()).build();
		}
		
		return Response.status(200).build();
	}
	 
	@DELETE
	@Path("/{id}")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("id") Long id) {
		User user = User.repository().fetch(id);
		
		if (user != null) {
			user.remove();
		} else {
			return Response.status(404).build();
		}
		
		return Response.status(200).build();
	}
}
