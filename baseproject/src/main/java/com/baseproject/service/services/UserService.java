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

import com.baseproject.model.entities.User;
import com.baseproject.util.utils.OneWayEncryptionUtils;
import com.baseproject.util.validation.ValidationException;

@Path("/users")
public class UserService extends BaseService {
	 
	@POST
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(User user) {
		
		if (user.encodedPassword() != null) {
			user.setPassword(OneWayEncryptionUtils.encode(user.encodedPassword()));
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
		User user = User.repository().fetch(id);
		System.out.println("asdasd");
		if (user != null) {
			user.detach();
			return Response.status(200).entity(user).build();
		} else {
			return Response.status(404).build();
		}
	}
}
