package com.baseproject.util.validation;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import com.baseproject.util.utils.ReflectionUtils;

public class Validator {

	public static void validate(Object object) throws ValidationException {
		ValidationException validationException = new ValidationException();

		List<Field> fields = ReflectionUtils.retrieveAllFieldsFromClass(object.getClass());

		for (Field field : fields) {
			checkNotNullAnnotation(object, field, validationException);
			checkNotEmptyAnnotation(object, field, validationException);
			checkLengthAnnotation(object, field, validationException);
			checkRangeForDoubleAnnotation(object, field, validationException);
			checkRangeAnnotation(object, field, validationException);
		}

		if (validationException.hasValidationFailures()) {
			throw validationException;
		}
	}

	private static void checkNotNullAnnotation(Object object, Field field,
			ValidationException validationException) {
		NotNull notNullAnnotation = (NotNull) field.getAnnotation(NotNull.class);

		if (notNullAnnotation != null) {
			try {
				field.setAccessible(true);
				Object fieldValue = field.get(object);

				if (fieldValue == null) {
					ValidationFailure validationFailure = new ValidationFailure(object.getClass().getSimpleName(), field.getName(), FailureCause.NullAttribute);
					validationException.addValidationFailure(validationFailure);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	private static boolean stringIsEmpty(Object fieldValue) {
		return fieldValue != null && fieldValue instanceof String && ((String) fieldValue).isEmpty();
	}
	
	private static boolean collectionIsEmpty(Object fieldValue) {
		return fieldValue != null && fieldValue instanceof Collection<?> && ((Collection<?>) fieldValue).isEmpty();
	}

	private static void checkNotEmptyAnnotation(Object object, Field field,
			ValidationException validationException) {
		NotEmpty notEmptyAnnotation = (NotEmpty) field.getAnnotation(NotEmpty.class);

		if (notEmptyAnnotation != null) {
			try {
				field.setAccessible(true);
				Object fieldValue = field.get(object);

				if (fieldValue == null || stringIsEmpty(fieldValue) || collectionIsEmpty(fieldValue)) {
					ValidationFailure validationFailure = new ValidationFailure(object.getClass().getSimpleName(), field.getName(), FailureCause.EmptyAttribute);
					validationException.addValidationFailure(validationFailure);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private static void checkLengthAnnotation(Object object, Field field,
			ValidationException validationException) {
		Length lengthAnnotation = (Length) field.getAnnotation(Length.class);

		if (lengthAnnotation != null) {
			try {
				int min = lengthAnnotation.min();
				int max = lengthAnnotation.max();

				field.setAccessible(true);
				Object fieldValue = field.get(object);

				if (fieldValue != null) {
					int length = ((String) fieldValue).length();

					if (length < min) {
						ValidationFailure validationFailure = new ValidationFailure(object.getClass().getSimpleName(), field.getName(), FailureCause.LowerThanMinimumAttribute);
						validationException.addValidationFailure(validationFailure);
					} else if (length > max) {
						ValidationFailure validationFailure = new ValidationFailure(object.getClass().getSimpleName(), field.getName(), FailureCause.BiggerThanMaximumAttribute);
						validationException.addValidationFailure(validationFailure);
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private static void checkRangeForDoubleAnnotation(Object object, Field field,
			ValidationException validationException) {
		RangeForDouble rangeAnnotation = (RangeForDouble) field.getAnnotation(RangeForDouble.class);

		if (rangeAnnotation != null) {
			try {
				double min = rangeAnnotation.min();
				double max = rangeAnnotation.max();

				field.setAccessible(true);
				Object fieldObject = field.get(object);

				if (fieldObject != null) {
					Double fieldValue = (Double) fieldObject;

					if (fieldValue.compareTo(min) < 0) {
						ValidationFailure validationFailure = new ValidationFailure(object.getClass().getSimpleName(), field.getName(), FailureCause.LowerThanMinimumAttribute);
						validationException.addValidationFailure(validationFailure);
					} else if (fieldValue.compareTo(max) > 0) {
						ValidationFailure validationFailure = new ValidationFailure(object.getClass().getSimpleName(), field.getName(), FailureCause.BiggerThanMaximumAttribute);
						validationException.addValidationFailure(validationFailure);
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private static void checkRangeAnnotation(Object object, Field field,
			ValidationException validationException) {
		Range rangeAnnotation = (Range) field.getAnnotation(Range.class);

		if (rangeAnnotation != null) {
			try {
				int min = rangeAnnotation.min();
				int max = rangeAnnotation.max();

				field.setAccessible(true);
				Object fieldObject = field.get(object);

				if (fieldObject != null) {
					Integer fieldValue = (Integer) fieldObject;

					if (fieldValue.compareTo(min) < 0) {
						ValidationFailure validationFailure = new ValidationFailure(object.getClass().getSimpleName(), field.getName(), FailureCause.LowerThanMinimumAttribute);
						validationException.addValidationFailure(validationFailure);
					} else if (fieldValue.compareTo(max) > 0) {
						ValidationFailure validationFailure = new ValidationFailure(object.getClass().getSimpleName(), field.getName(), FailureCause.BiggerThanMaximumAttribute);
						validationException.addValidationFailure(validationFailure);
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}
