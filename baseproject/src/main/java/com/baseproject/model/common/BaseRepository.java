package com.baseproject.model.common;

import java.util.Map;

import javax.persistence.Query;

public interface BaseRepository<E extends BaseEntity> {

	E findById(Long id);

    E save(E e);

    void remove(Long id);
    
    Query createQuery(String query);
    
    void setParameters(Query query, Map<String, String> parameters);
}