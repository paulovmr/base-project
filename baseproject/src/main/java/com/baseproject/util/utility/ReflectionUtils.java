package com.baseproject.util.utility;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtils {

	public static List<Field> retrieveAllFieldsFromClass(Class<?> clazz) {
		List<Field> fields = new ArrayList<Field>();

		for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
			fields.addAll(Arrays.asList(c.getDeclaredFields()));
		}

		return fields;
	}
}
