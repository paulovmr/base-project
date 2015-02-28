package com.baseproject.service.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baseproject.model.entities.Feature;
import com.baseproject.model.entities.Profile;
import com.baseproject.model.entities.User;

public class Loader {
	
	public static final Map<Class<?>, List<String>> LOADABLE_FIELDS = new HashMap<Class<?>, List<String>>();
	static {
		LOADABLE_FIELDS.put(User.class, Arrays.asList("profile", "company"));
		LOADABLE_FIELDS.put(Profile.class, Arrays.asList("features"));
		LOADABLE_FIELDS.put(Feature.class, Arrays.asList("profiles"));
	};

	public static String[] load(String fields, Class<?> clazz) {
		List<String> loadableFields = LOADABLE_FIELDS.get(clazz);
		
		if (fields == null || fields.isEmpty() || loadableFields == null) {
			return new String[0];
		}

		String[] fieldsArray = fields.split(",");
		List<String> load = new ArrayList<>();
		for (String field : fieldsArray) {
			if (loadableFields.contains(field)) {
				load.add(field);
			}
		}
		
		String[] result = new String[load.size()];
		load.toArray(result);
		
		return result;
	}
}
