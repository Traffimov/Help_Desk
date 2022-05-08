package com.training.dao;

import java.util.List;

public interface GenericDao<T, ID> {

    T save(T entity);

    void update(T entity);

    void delete(ID id);

    T findById(ID id);

    List<T> findAll();
}
