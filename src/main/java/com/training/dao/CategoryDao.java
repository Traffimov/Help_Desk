package com.training.dao;

import com.training.model.Category;

import java.util.List;

public interface CategoryDao {

    Category save(Category category);

    List<Category> findAll();
}
