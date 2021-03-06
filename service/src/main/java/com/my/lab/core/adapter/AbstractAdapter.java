package com.my.lab.core.adapter;

import com.my.lab.core.adapter.exception.AlreadyExistsException;
import com.my.lab.core.adapter.exception.DataPersistenceException;
import com.my.lab.core.adapter.exception.NotAllowedException;
import com.my.lab.dao.exception.DaoException;
import com.my.lab.dao.exception.EntityAlreadyExistsException;
import com.my.lab.dao.exception.EntityNotAllowedException;

public abstract class AbstractAdapter<T> implements Adapter<T> {

    @Override
    public T saveEntity(T entity) throws DataPersistenceException {
        try {
            return saveEntityRoutine(entity);
        } catch (EntityAlreadyExistsException exc) {
            throw new AlreadyExistsException(exc.getMessage(), exc);
        } catch (EntityNotAllowedException exc) {
            throw new NotAllowedException(exc.getMessage(), exc);
        } catch (DaoException exc) {
            throw new DataPersistenceException(exc.getMessage(), exc);
        }
    }

    protected abstract T saveEntityRoutine(T entity) throws DaoException;

    @Override
    public T updateEntity(T entity) throws DataPersistenceException {
        try {
            return updateEntityRoutine(entity);
        } catch (EntityAlreadyExistsException exc) {
            throw new AlreadyExistsException(exc.getMessage(), exc);
        } catch (DaoException exc) {
            throw new DataPersistenceException(exc.getMessage(), exc);
        }
    }

    protected abstract T updateEntityRoutine(T entity) throws DaoException;

    @Override
    public T getEntity(Integer id) throws DataPersistenceException {
        try {
            return getEntityRoutine(id);
        } catch (DaoException exc) {
            throw new DataPersistenceException(exc.getMessage(), exc);
        }
    }

    protected abstract T getEntityRoutine(Integer id) throws DaoException;

    @Override
    public T deleteEntity(Integer id) throws DataPersistenceException {
        try {
            return deleteEntityRoutine(id);
        } catch (DaoException exc) {
            throw new DataPersistenceException(exc.getMessage(), exc);
        }
    }

    protected abstract T deleteEntityRoutine(Integer id) throws DaoException;
}
