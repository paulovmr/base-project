package com.baseproject.model.common;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.baseproject.model.config.MyEntityManager;
import com.baseproject.util.utils.DateUtils;
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

	public E fetch(final Long id, String... loads) {
        return fetch(null, null, loads);
    }

	public E fetch(String attribute, Object value, String... loads) {
        StringBuilder hql = new StringBuilder();
        hql.append("FROM " + clazz.getAnnotation(Entity.class).name() + " e");
        
        for (String load : loads) {
        	hql.append(" JOIN FETCH e." + load);
        }
        
        if (attribute != null) {
        	hql.append(" WHERE " + attribute + " = :value");
        }
        
		TypedQuery<E> query = createQuery(hql.toString());
        if (attribute != null) {
        	query.setParameter("value", value);
        }
        List<E> result = query.getResultList();
        
        if (result != null && !result.isEmpty()) {
        	return result.get(0);
        } 
        return null;
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

	public TypedQuery<E> createQuery(String query) {
		return MyEntityManager.get().createQuery(query, clazz);
	}

	public void setParameters(Query query, Map<String, String> parameters) {
		for (String parameter : parameters.keySet()) {
			query.setParameter(parameter, parameters.get(parameter));
		}
	}
	
	public void detach(E entity) {
		MyEntityManager.get().detach(entity);
	}
}