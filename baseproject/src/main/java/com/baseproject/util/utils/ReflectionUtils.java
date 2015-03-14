package com.baseproject.util.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;

import org.reflections.Reflections;

import com.baseproject.model.common.BaseEntity;

public class ReflectionUtils {

	public static List<Field> retrieveAllFieldsFromClass(Class<?> clazz) {
		List<Field> fields = new ArrayList<Field>();

		for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
			fields.addAll(Arrays.asList(c.getDeclaredFields()));
		}

		return fields;
	}

	@SuppressWarnings("rawtypes")
	public static Map<String, Class<? extends BaseEntity>> getClassByTableName() {
		Map<String, Class<? extends BaseEntity>> map = new HashMap<>();
		Reflections reflections = new Reflections("com.baseproject.model.entities");

		Set<Class<? extends BaseEntity>> allClasses = reflections.getSubTypesOf(BaseEntity.class);
		
		for (Class<? extends BaseEntity> clazz : allClasses) {
			String tableName = getTableFromClass(clazz);
			
			map.put(tableName, clazz);
		}
		
		return map;
	}

	@SuppressWarnings("rawtypes")
	public static String getTableFromClass(Class<? extends BaseEntity> clazz) {
		Entity entity = clazz.getAnnotation(Entity.class);
		String tableName = entity.name();
		
		return tableName;
	}
}
