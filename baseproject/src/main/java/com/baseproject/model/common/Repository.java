package com.baseproject.model.common;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.TypedQuery;

import com.baseproject.util.utils.DateUtils;
import com.baseproject.util.utils.QueryUtils;
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

		new Validator<E>().validate(entity);

        MyEntityManager.get().persist(entity);
        E mergedEntity = MyEntityManager.get().merge(entity);
        
        return mergedEntity;
    }

	public void remove(final E entity) {
		MyEntityManager.get().remove(entity);
	}

	public E fetch(final Long id, String... loads) {
        return fetch(Conditions.create(), loads);
    }

	public E fetch(Conditions conditions, String... loads) {
        StringBuilder hql = new StringBuilder();

		Map<String, Object> params = new HashMap<>();
		
        hql.append("FROM " + clazz.getAnnotation(Entity.class).name() + " e");
       	hql.append(QueryUtils.loadQuery(loads));
        hql.append(" WHERE 1 = 1");
        
        if (conditions != null) {
	        int i = 0;
	       	for (Condition condition : conditions.getConditions()) {
	        	hql.append(" AND " + condition.getField() + " " + condition.getOperator() + " :value" + i);
	        	params.put("value" + i, condition.getValue());
	        	i++;
	       	}
        }
       	
       	TypedQuery<E> query = createQuery(hql.toString());
       	QueryUtils.setParameters(query, params);
       	
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

    public E single(Filter<E> filter) {
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
	
	public void detach(E entity) {
		MyEntityManager.get().detach(entity);
	}
}