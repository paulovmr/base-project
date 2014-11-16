package com.baseproject.service.config;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;

import com.baseproject.model.entities.Users;
import com.baseproject.util.crypt.LoginUtils;

@Provider
public class SecurityInterceptor implements ContainerRequestFilter {
	
	private static final String AUTHORIZATION_PROPERTY = "auth";
	private static final ServerResponse ACCESS_DENIED = new ServerResponse("Access denied for this resource.", 401, new Headers<Object>());
	private static final ServerResponse NOT_LOGGED = new ServerResponse("Not logged.", 401, new Headers<Object>());
	private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("Nobody can access this resource.", 403, new Headers<Object>());

	@Override
	public void filter(ContainerRequestContext requestContext) {
		ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
		Method method = methodInvoker.getMethod();

		if (!method.isAnnotationPresent(PermitAll.class)) {

			if (method.isAnnotationPresent(DenyAll.class)) {
				requestContext.abortWith(ACCESS_FORBIDDEN);
				return;
			}

			Cookie cookie = requestContext.getCookies().get(AUTHORIZATION_PROPERTY);

			if (cookie == null) {
				requestContext.abortWith(NOT_LOGGED);
				return;
			}
			
			final String cookieValue = cookie.getValue();

			if (cookieValue == null) {
				requestContext.abortWith(NOT_LOGGED);
				return;
			}

			StringTokenizer firstTokenizer = new StringTokenizer(cookieValue, LoginUtils.FIRST_SEPARATOR);
			String username = firstTokenizer.nextToken();
			String encodedUserPassword = firstTokenizer.nextToken();
			
			String usernameAndPassword = LoginUtils.decrypt(username, encodedUserPassword);

			StringTokenizer secondTokenizer = new StringTokenizer(usernameAndPassword, LoginUtils.SECOND_SEPARATOR);
			username = secondTokenizer.nextToken();
			String password = secondTokenizer.nextToken();

			if (method.isAnnotationPresent(RolesAllowed.class)) {
				RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
				Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

				if (!isUserAllowed(username, password, rolesSet)) {
					requestContext.abortWith(ACCESS_DENIED);
					return;
				}
			}
		}
	}

	private boolean isUserAllowed(final String username, final String password,	final Set<String> rolesSet) {
		Users user = Users.repository().fetch("username", username);
		
		if (LoginUtils.match(password, user.getPassword())) {
			String userRole = user.getProfile().name();

			if (rolesSet.contains(userRole)) {
				return true;
			}			
		}
		
		return false;
	}
}