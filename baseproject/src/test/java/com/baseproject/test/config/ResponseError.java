package com.baseproject.test.config;

public class ResponseError extends RuntimeException {

	private static final long serialVersionUID = -2741480103969378235L;
	
	private Response response;
	
	public ResponseError(Response response) {
		super("Request failed with code " + response.getCode() + ". Http entity: " + response.getEntity());
		this.response = response;
	}
	
	public Response getResponse() {
		return response;
	}
}
