package com.baseproject.service.security;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

public class MySecurityContext implements SecurityContext {
	
	private Token token;
	
	public MySecurityContext(Token token) {
		this.token = token;
	}

	@Override
	public String getAuthenticationScheme() {
		return SecurityContext.BASIC_AUTH;
	}

	@Override
	public Principal getUserPrincipal() {
		return token;
	}

	@Override
	public boolean isSecure() {
		return true;
	}

	@Override
	public boolean isUserInRole(String role) {
		return token.getFeatures().contains(role);
	}
}
