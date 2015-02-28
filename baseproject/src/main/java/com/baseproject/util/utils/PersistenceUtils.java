package com.baseproject.util.utils;

import com.baseproject.model.config.MyEntityManager;

public class PersistenceUtils {

	public static boolean isLoaded(Object object) {
		return MyEntityManager.getPersistenceUnitUtil().isLoaded(object);
	}
}
