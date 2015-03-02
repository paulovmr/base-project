package com.baseproject.service.common;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import com.baseproject.service.security.Token;

public abstract class BaseService {
	
	@Context
	private SecurityContext securityContext;
	
	protected Token getToken() {
		return (Token) securityContext.getUserPrincipal();
	}
}
