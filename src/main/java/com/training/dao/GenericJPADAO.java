package com.training.dao;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
public abstract class GenericJPADAO<T, ID> implements GenericDao<T, ID> {

    private final Class<T> persistentClass;

    @PersistenceContext
    private EntityManager entityManager;

    protected GenericJPADAO(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    @Transactional(readOnly = true)
    public T findById(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid id");
        }
        return getEntityManager().find(getPersistentClass(), id);
    }

    @Transactional(readOnly = true)
    public List<T> findAll() {
        return getEntityManager()
                .createQuery("FROM " + getPersistentClass().getSimpleName())
                .getResultList();
    }

    public T save(T entity) {
        getEntityManager().persist(entity);
        return entity;
    }

    public void update(T entity) {
        getEntityManager().merge(entity);
    }

    public void delete(Long id) {
        getEntityManager()
                .createQuery("DELETE " + getPersistentClass().getSimpleName() + " e WHERE e.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }
}
