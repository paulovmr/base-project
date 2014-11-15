package com.baseproject.service.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.baseproject.model.entities.User;

@Path("/user")
public class UserService {
 
	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(@PathParam("name") String name) {
		
		User user = new User();
		user.setName(name);
		user.save();
		
		return Response.status(200).entity(user).build();
	}
}
