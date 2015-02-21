package com.baseproject.service.services;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import com.baseproject.model.entities.User;
import com.baseproject.service.dtos.AuthenticationData;
import com.baseproject.service.security.Token;
import com.baseproject.util.utils.OneWayEncryptionUtils;

@Path("/login")
public class LoginService {
	 
	@POST
	@PermitAll
	@Consumes("application/json")
	public Response login(AuthenticationData authenticationData) {
		String username = authenticationData.getUsername();
		String password = authenticationData.getPassword();
		
		User user = User.repository().fetch("username", username);
		
		if (user != null && OneWayEncryptionUtils.match(password, user.encodedPassword())) {			
			NewCookie cookie = new NewCookie("auth", Token.fromUser(user).encrypted());
			return Response.status(200).cookie(cookie).build();
		}
		
		return Response.status(401).build();
	}
}
