package com.baseproject.model.common;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.baseproject.model.config.MyEntityManager;
import com.baseproject.util.date.DateUtils;

public class Repository<E> {

    public void save(final Entity entity) {
		Date now = DateUtils.now();
		entity.updateEntity(now);
		
        MyEntityManager.get().persist(entity);
    }

    public void update(final Entity entity) {
		Date now = DateUtils.now();
		entity.updateEntity(now);
		
        MyEntityManager.get().merge(entity);
    }

	public void remove(final Entity entity) {
		MyEntityManager.get().remove(entity);
	}

	public E fetch(Class<E> clazz, final Long id) {
        return MyEntityManager.get().find(clazz, id);
    }

    public List<E> list(Filter<E> filter) {
        TypedQuery<E> query = filter.buildQuery();
        return query.getResultList();
    }

    public E only(Filter<E> filter) {
        TypedQuery<E> query = filter.buildQuery();
        return query.getSingleResult();
    }

    public E first(Filter<E> filter) {
    	List<E> list = list(filter);
    	if (list != null && list.size() > 0) {
    		return list.get(0);
    	}

    	return null;
    }

	public Query createQuery(String query) {
		return MyEntityManager.get().createQuery(query);
	}

	public void setParameters(Query query, Map<String, String> parameters) {
		for (String parameter : parameters.keySet()) {
			query.setParameter(parameter, parameters.get(parameter));
		}
	}
}