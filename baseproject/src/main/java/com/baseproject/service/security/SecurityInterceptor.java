package com.baseproject.service.security;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;

@Provider
public class SecurityInterceptor implements ContainerRequestFilter {
	
	private static final String AUTHORIZATION_PROPERTY = "auth";
	private static final ServerResponse ACCESS_DENIED = new ServerResponse("Access denied for this resource.", 401, new Headers<Object>());
//	private static final ServerResponse NOT_LOGGED = new ServerResponse("Not logged.", 401, new Headers<Object>());
	private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("Nobody can access this resource.", 403, new Headers<Object>());
	
	@Override
	public void filter(ContainerRequestContext requestContext) {
		ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
		Method method = methodInvoker.getMethod();
		String encryptedToken = getCookieValue(requestContext);
		Token token = null;

		if (encryptedToken != null) {
			token = Token.fromEncryptedJson(encryptedToken);
			requestContext.setSecurityContext(new MySecurityContext(token));
		}

		if (method.isAnnotationPresent(RolesAllowed.class)) {
			if (token == null) {
				requestContext.abortWith(ACCESS_DENIED);
				return;
			}
			
			RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
			List<String> allowedFeatures = Arrays.asList(rolesAnnotation.value());

			if (!isUserAllowed(token.getFeatures(), allowedFeatures)) {
				requestContext.abortWith(ACCESS_DENIED);
				return;
			}
		}

		if (method.isAnnotationPresent(DenyAll.class)) {
			requestContext.abortWith(ACCESS_FORBIDDEN);
			return;
		}
	}
	
	private String getCookieValue(ContainerRequestContext requestContext) {
		Cookie cookie = requestContext.getCookies().get(AUTHORIZATION_PROPERTY);
		
		if (cookie != null) {
			return cookie.getValue();
		}
		
		return null;
	}

	private boolean isUserAllowed(List<String> userFeatures, List<String> allowedFeatures) {
		for (String allowedFeature : allowedFeatures) {
			if (userFeatures.contains(allowedFeature)) {
				return true;
			}
		}
		
		return false;
	}
}