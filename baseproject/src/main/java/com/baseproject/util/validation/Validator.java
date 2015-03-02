package com.baseproject.util.validation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baseproject.model.common.BaseEntity;
import com.baseproject.model.common.Conditions;
import com.baseproject.util.utils.ReflectionUtils;

public class Validator<E extends BaseEntity<E>> {

	public void validate(E entity) throws ValidationException {
		ValidationException validationException = new ValidationException();

		List<Field> fields = ReflectionUtils.retrieveAllFieldsFromClass(entity.getClass());

		checkUniqueAnnotation(entity, fields, validationException);

		for (Field field : fields) {
			checkNotNullAnnotation(entity, field, validationException);
			checkNotEmptyAnnotation(entity, field, validationException);
			checkLengthAnnotation(entity, field, validationException);
			checkRangeForDoubleAnnotation(entity, field, validationException);
			checkRangeAnnotation(entity, field, validationException);
		}

		if (validationException.hasValidationFailures()) {
			throw validationException;
		}
	}

	private void checkUniqueAnnotation(E entity, List<Field> allFields, ValidationException validationException) {
		
		Map<String, List<Field>> fieldsByKey = new HashMap<>();
		
		for (Field field : allFields) {
			Unique uniqueAnnotation = (Unique) field.getAnnotation(Unique.class);
			
			if (uniqueAnnotation != null) {
				String key = uniqueAnnotation.key();
				List<Field> fields = fieldsByKey.get(key);
				
				if (fields == null) {
					fields = new ArrayList<>();
					fields.add(field);
					fieldsByKey.put(key, fields);				
				} else {
					fields.add(field);
				}				
			}
		}
		
		for (String key : fieldsByKey.keySet()) {
			Conditions conditions = Conditions.create();
			
			try {
				for (Field field : fieldsByKey.get(key)) {
					field.setAccessible(true);
					String fieldName = field.getName();
					Object fieldValue = field.get(entity);
					
					conditions.and(fieldName, "=", fieldValue);
				}
				
				E alreadyPersistedEntity = entity.getRepository().fetch(conditions);

				if (alreadyPersistedEntity != null && !alreadyPersistedEntity.getId().equals(entity.getId())) {
					ValidationFailure validationFailure = new ValidationFailure(entity.getClass().getSimpleName(), key, FailureCause.UniqueAttribute);
					validationException.addValidationFailure(validationFailure);
				}
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void checkNotNullAnnotation(E entity, Field field,
			ValidationException validationException) {
		NotNull notNullAnnotation = (NotNull) field.getAnnotation(NotNull.class);

		if (notNullAnnotation != null) {
			try {
				field.setAccessible(true);
				Object fieldValue = field.get(entity);

				if (fieldValue == null) {
					ValidationFailure validationFailure = new ValidationFailure(entity.getClass().getSimpleName(), field.getName(), FailureCause.NullAttribute);
					validationException.addValidationFailure(validationFailure);
				}
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void checkNotEmptyAnnotation(E entity, Field field,
			ValidationException validationException) {
		NotEmpty notEmptyAnnotation = (NotEmpty) field.getAnnotation(NotEmpty.class);

		if (notEmptyAnnotation != null) {
			try {
				field.setAccessible(true);
				Object fieldValue = field.get(entity);

				if (fieldValue == null || stringIsEmpty(fieldValue) || collectionIsEmpty(fieldValue)) {
					ValidationFailure validationFailure = new ValidationFailure(entity.getClass().getSimpleName(), field.getName(), FailureCause.EmptyAttribute);
					validationException.addValidationFailure(validationFailure);
				}
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void checkLengthAnnotation(E entity, Field field,
			ValidationException validationException) {
		Length lengthAnnotation = (Length) field.getAnnotation(Length.class);

		if (lengthAnnotation != null) {
			try {
				int min = lengthAnnotation.min();
				int max = lengthAnnotation.max();

				field.setAccessible(true);
				Object fieldValue = field.get(entity);

				if (fieldValue != null) {
					int length = ((String) fieldValue).length();

					if (length < min) {
						ValidationFailure validationFailure = new ValidationFailure(entity.getClass().getSimpleName(), field.getName(), FailureCause.LowerThanMinimumAttribute);
						validationException.addValidationFailure(validationFailure);
					} else if (length > max) {
						ValidationFailure validationFailure = new ValidationFailure(entity.getClass().getSimpleName(), field.getName(), FailureCause.BiggerThanMaximumAttribute);
						validationException.addValidationFailure(validationFailure);
					}
				}
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void checkRangeForDoubleAnnotation(E entity, Field field,
			ValidationException validationException) {
		RangeForDouble rangeAnnotation = (RangeForDouble) field.getAnnotation(RangeForDouble.class);

		if (rangeAnnotation != null) {
			try {
				double min = rangeAnnotation.min();
				double max = rangeAnnotation.max();

				field.setAccessible(true);
				Object fieldObject = field.get(entity);

				if (fieldObject != null) {
					Double fieldValue = (Double) fieldObject;

					if (fieldValue.compareTo(min) < 0) {
						ValidationFailure validationFailure = new ValidationFailure(entity.getClass().getSimpleName(), field.getName(), FailureCause.LowerThanMinimumAttribute);
						validationException.addValidationFailure(validationFailure);
					} else if (fieldValue.compareTo(max) > 0) {
						ValidationFailure validationFailure = new ValidationFailure(entity.getClass().getSimpleName(), field.getName(), FailureCause.BiggerThanMaximumAttribute);
						validationException.addValidationFailure(validationFailure);
					}
				}
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void checkRangeAnnotation(E entity, Field field,
			ValidationException validationException) {
		Range rangeAnnotation = (Range) field.getAnnotation(Range.class);

		if (rangeAnnotation != null) {
			try {
				int min = rangeAnnotation.min();
				int max = rangeAnnotation.max();

				field.setAccessible(true);
				Object fieldObject = field.get(entity);

				if (fieldObject != null) {
					Integer fieldValue = (Integer) fieldObject;

					if (fieldValue.compareTo(min) < 0) {
						ValidationFailure validationFailure = new ValidationFailure(entity.getClass().getSimpleName(), field.getName(), FailureCause.LowerThanMinimumAttribute);
						validationException.addValidationFailure(validationFailure);
					} else if (fieldValue.compareTo(max) > 0) {
						ValidationFailure validationFailure = new ValidationFailure(entity.getClass().getSimpleName(), field.getName(), FailureCause.BiggerThanMaximumAttribute);
						validationException.addValidationFailure(validationFailure);
					}
				}
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	private boolean stringIsEmpty(Object fieldValue) {
		return fieldValue != null && fieldValue instanceof String && ((String) fieldValue).isEmpty();
	}
	
	private boolean collectionIsEmpty(Object fieldValue) {
		return fieldValue != null && fieldValue instanceof Collection<?> && ((Collection<?>) fieldValue).isEmpty();
	}
}
