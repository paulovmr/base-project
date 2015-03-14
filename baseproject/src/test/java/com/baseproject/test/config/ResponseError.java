package com.baseproject.test.config;

public class ResponseError extends RuntimeException {

	private static final long serialVersionUID = -2741480103969378235L;
	
	public ResponseError(String message) {
		super(message);
	}
}
