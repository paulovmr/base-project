package com.baseproject.model.common;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.baseproject.model.config.MyEntityManager;
import com.baseproject.util.date.DateUtils;
import com.baseproject.util.validation.ValidationException;
import com.baseproject.util.validation.Validator;

public class Repository<E extends BaseEntity<E>> {
	
	private Class<E> clazz;
	
	public Repository() {
	}
	
	public Repository(Class<E> clazz) {
		this.clazz = clazz;
	}

    public E save(final E entity) throws ValidationException {
		Date now = DateUtils.now();
		entity.updateEntity(now);
		
		Validator.validate(entity);

        MyEntityManager.get().persist(entity);
        E mergedEntity = MyEntityManager.get().merge(entity);
        
        return mergedEntity;
    }

	public void remove(final E entity) {
		MyEntityManager.get().remove(entity);
	}

	public E fetch(final Long id) {
        return MyEntityManager.get().find(clazz, id);
    }

	public E fetch(String attribute, Object value) {
        TypedQuery<E> query = createNamedQuery("FROM " + clazz.getSimpleName() + " WHERE " + attribute + " = :value");
        query.setParameter("value", value);
        return query.getSingleResult();
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

	public TypedQuery<E> createNamedQuery(String query) {
		return MyEntityManager.get().createNamedQuery(query, clazz);
	}

	public void setParameters(Query query, Map<String, String> parameters) {
		for (String parameter : parameters.keySet()) {
			query.setParameter(parameter, parameters.get(parameter));
		}
	}
}