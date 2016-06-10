package com.my.lab.dao.db;

import com.my.lab.dao.DbPersistent;
import com.my.lab.dao.entity.JPAEntity;
import com.my.lab.dao.exception.EntityAlreadyExistsException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import javax.persistence.*;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

public abstract class AbstractDbDao<T extends JPAEntity> implements DbPersistent<T> {

    @PersistenceContext(unitName = "demo-persistence-unit")
    private EntityManager entityManager;

    @Override
    public abstract Class<T> getEntityClass();

    @Override
    public T saveEntity(T entity) throws EntityAlreadyExistsException {
        // as ugly as it is, but this check is required to get rid of exceptions
        // on attempt to persist an entity with naturalId saved before

        T entityCheck = getEntityByNaturalId(entity.getNaturalId());
        if (entityCheck != null) {
            Entry<String, ?> natId = entity.getNaturalId().entrySet().iterator().next();
            throw new EntityAlreadyExistsException.Builder().
                    setEntityNaturalIdMessage(natId.getKey(), natId.getValue().toString()).build();
        } else {
            for (final ListIterator<JPAEntity> iterator = entity.getNestedEntities().listIterator(); iterator.hasNext(); ) {
                // get a nested entity
                JPAEntity nested = iterator.next();

                // TODO: consider recursion
                // create a db search criteria to check if the nested entity
                // was persisted before by its natural id
                Criterion criteria = null;
                for (String naturalIdField : nested.getNaturalId().keySet()) {
                    Object naturalIdValue = nested.getNaturalId().get(naturalIdField);
                    criteria = Restrictions.naturalId().set(naturalIdField, naturalIdValue);
                }
                // thy to acquire a persisted entity with the criteria
                JPAEntity persistedNested = (JPAEntity) DetachedCriteria.forClass(nested.getClass()).add(criteria)
                        .getExecutableCriteria(entityManager.unwrap(Session.class)).uniqueResult();

                if (nested != null) {
                    // replicate fetched entity with values from a passed transient one (all besides ID)
                    persistedNested.replicate(nested);
                    iterator.set(persistedNested);
                    // trying to persist a transient entity with ID in use would lead to exception
                }
            }
            entityManager.merge(entity);
        }
        return entity;
    }

    @Override
    public T updateEntity(T entity) {
        entity = entityManager.merge(entity);
        return entity;
    }

    @Override
    public T getEntity(Integer id) {
        return entityManager.find(getEntityClass(), id);
    }

    @Override
    public T deleteEntity(Integer id) {
        T entity = entityManager.find(getEntityClass(), id);
        if (entity != null) {
            entityManager.remove(entity);
        }
        return entity;
    }

    protected T executeQuerySingleResult(String queryName, Map<String, ?> params) {
        TypedQuery<T> query = getTypedQuery(queryName, params);
        try {
            return query.getSingleResult();
          // JPA throws an exception in case there's nothing matching the query instead of returning null
          // so here's a workaround
        } catch (NoResultException exc) {
            return null;
        }
    }

    private TypedQuery<T> getTypedQuery(String queryName, Map<String, ?> params) {
        TypedQuery<T> query = entityManager.createNamedQuery(queryName, getEntityClass());
        return setQueryParams(query, params);
    }

    private <T extends Query> T setQueryParams(T query, Map<String, ?> params) {
        for (Map.Entry<String, ?> param : params.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }
        return query;
    }

    protected <X extends Object> Map<String, X> getSingleParamMap(String key, X value) {
        Map<String, X> param = new HashMap<>();
        param.put(key, value);
        return param;
    }
}
