package com.baseproject.util.utils;

import java.util.Map;

import javax.persistence.Query;

public class QueryUtils {
	
	public static void setParameters(Query query, Map<String, Object> parameters) {
		for (String parameter : parameters.keySet()) {
			query.setParameter(parameter, parameters.get(parameter));
		}
	}
	
	public static String loadQuery(String[] loads) {
		String loadQuery = "";
		for (String load : loads) {
			loadQuery += " JOIN FETCH e." + load;
        }
		
		return loadQuery;
	}
}
