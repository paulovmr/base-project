package com.baseproject.model.common;

import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.baseproject.util.utility.DateUtils;

public class BaseRepositoryImpl<E extends BaseEntity> implements
		BaseRepository<E> {

	private Class<E> entityClass;
	
	@PersistenceContext
	private EntityManager em;

	protected BaseRepositoryImpl(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	@Override
	public E findById(Long id) {
		return (E) em.find(entityClass, id);
	}

	@Override
	public E save(E e) {
		Date now = DateUtils.now();
		e.updateEntity(now);
		
		return (E) em.merge(e);
	}

	@Override
	public void remove(Long id) {
		E e = findById(id);
		em.remove(e);
	}

	@Override
	public Query createQuery(String query) {
		return em.createQuery(query);
	}

	@Override
	public void setParameters(Query query, Map<String, String> parameters) {
		for (String parameter : parameters.keySet()) {
			query.setParameter(parameter, parameters.get(parameter));
		}
	}
}