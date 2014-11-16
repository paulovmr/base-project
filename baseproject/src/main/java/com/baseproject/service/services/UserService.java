package com.baseproject.service.services;

import java.net.URI;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.baseproject.model.entities.Users;
import com.baseproject.util.crypt.LoginUtils;
import com.baseproject.util.validation.ValidationException;

@Path("/users")
public class UserService {
	 
	@POST
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(Users user) {
		
		if (user.getPassword() != null) {
			user.setPassword(LoginUtils.encode(user.getPassword()));
		}
		
		try {
			user = user.save();
		} catch (ValidationException e) {
			return Response.status(422).entity(e.getValidationFailures()).build();
		}
		
		URI location = UriBuilder.fromPath("/users/" + user.getId()).build();
		return Response.status(200).location(location).build();
	}
	 
	@GET
	@Path("/{id}")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchUser(@PathParam("id") Long id) {
		
		Users user = Users.repository().fetch(id);
		
		if (user != null) {
			return Response.status(200).entity(user).build();
		} else {
			return Response.status(404).build();
		}
	}
}
