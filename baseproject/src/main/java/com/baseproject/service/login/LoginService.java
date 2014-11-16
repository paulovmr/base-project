package com.baseproject.service.login;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import com.baseproject.model.entities.Users;
import com.baseproject.util.crypt.LoginUtils;

@Path("/login")
public class LoginService {
	 
	@POST
	@PermitAll
	@Consumes("application/json")
	public Response login(AuthenticationData authenticationData) {
		String username = authenticationData.getUsername();
		String password = authenticationData.getPassword();
		
		Users user = Users.repository().fetch("username", username);
		
		if (LoginUtils.match(password, user.getPassword())) {			
			NewCookie cookie = new NewCookie("auth", LoginUtils.generateToken(username, password));
			return Response.status(200).cookie(cookie).build();
		}
		
		return Response.status(401).build();
	}
}
