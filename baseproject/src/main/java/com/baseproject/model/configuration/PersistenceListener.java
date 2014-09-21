package com.baseproject.model.configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
 
public class PersistenceListener implements ServletContextListener {
 
    private EntityManagerFactory entityManagerFactory;
 
    public void contextInitialized(ServletContextEvent sce){
        sce.getServletContext();
        entityManagerFactory = Persistence.createEntityManagerFactory("baseproject-pu");
    }
 
    public void contextDestroyed(ServletContextEvent sce) {
        entityManagerFactory.close();
    }
}