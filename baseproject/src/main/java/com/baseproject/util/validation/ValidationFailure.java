package com.baseproject.util.validation;

public class ValidationFailure {

	private String clazz;

	private String field;

	private FailureCause failureCause;

	public ValidationFailure(String clazz, String field, FailureCause failureCause) {
		this.clazz = clazz;
		this.field = field;
		this.failureCause = failureCause;
	}

	public String getMessage() {
		String message = clazz + "." + field + "." + failureCause.toString();

		return message.toLowerCase();
	}
}
