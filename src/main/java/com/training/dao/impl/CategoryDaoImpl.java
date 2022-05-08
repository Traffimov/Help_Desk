package com.training.dao.impl;

import com.training.dao.CategoryDao;
import com.training.dao.GenericJPADAO;
import com.training.model.Category;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDaoImpl extends GenericJPADAO<Category, Long> implements CategoryDao {

    protected CategoryDaoImpl() {
        super(Category.class);
    }

}
