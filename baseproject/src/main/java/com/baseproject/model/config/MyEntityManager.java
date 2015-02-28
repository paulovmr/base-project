package com.baseproject.model.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;

public final class MyEntityManager {

    private static EntityManagerFactory factory;
    private static ThreadLocal<EntityManager> threadSafeEntityManager = new ThreadLocal<>();

    static {
        try {
            factory = Persistence.createEntityManagerFactory("baseproject-pu");
        } catch (Throwable t) {
            t.printStackTrace();
            throw new RuntimeException(t);
        }
    }

    private MyEntityManager() {
    }

    public static void renewEntityManager() {
        setNewEntityManager();
    }

    public static EntityManager get() {

        if (threadSafeEntityManager.get() == null) {
            setNewEntityManager();
        }

        return threadSafeEntityManager.get();
    }

    private static void setNewEntityManager() {
        EntityManager entityManager = factory.createEntityManager();
        threadSafeEntityManager.set(entityManager);
    }

    public static void free() {
        threadSafeEntityManager.get().close();
        threadSafeEntityManager.remove();
    }

    public static void close() {
        factory.close();
    }
    
    public static PersistenceUnitUtil getPersistenceUnitUtil() {
    	return factory.getPersistenceUnitUtil();
    }
}