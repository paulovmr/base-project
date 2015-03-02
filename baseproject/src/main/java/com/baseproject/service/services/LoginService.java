package com.baseproject.service.services;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import com.baseproject.model.common.Conditions;
import com.baseproject.model.entities.User;
import com.baseproject.service.common.BaseService;
import com.baseproject.service.data.AuthenticationData;
import com.baseproject.service.security.Token;
import com.baseproject.util.utils.OneWayEncryptionUtils;

@Path("/login")
public class LoginService extends BaseService {
	 
	@POST
	@PermitAll
	@Consumes("application/json")
	public Response login(AuthenticationData ad) {
		Conditions conditions = Conditions.create().and("username", "=", ad.getUsername()).and("company.code", "=", ad.getCompanyCode());
		User user = User.repository().fetch(conditions, "profile", "company");
		
		if (user != null && OneWayEncryptionUtils.match(ad.getPassword(), user.encodedPassword())) {			
			NewCookie cookie = new NewCookie("auth", Token.fromUser(user).encrypted());
			return Response.status(200).cookie(cookie).build();
		}
		
		return Response.status(401).build();
	}
}
