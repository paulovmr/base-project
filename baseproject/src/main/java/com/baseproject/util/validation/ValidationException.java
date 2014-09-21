package com.baseproject.util.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends Exception {

	private static final long serialVersionUID = -9130657400435023L;

	private List<ValidationFailure> validationFailures = new ArrayList<ValidationFailure>();

	public void addValidationFailure(ValidationFailure validationFailure) {
		validationFailures.add(validationFailure);
	}

	public List<ValidationFailure> getValidationFailures() {
		return validationFailures;
	}

	public boolean hasValidationFailures() {
		return !validationFailures.isEmpty();
	}
}
