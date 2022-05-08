package com.training.dao.impl;

import com.training.dao.GenericJPADAO;
import com.training.dao.HistoryDao;
import com.training.model.History;
import org.springframework.stereotype.Repository;

@Repository
public class HistoryDaoImpl extends GenericJPADAO<History, Long> implements HistoryDao {

    protected HistoryDaoImpl() {
        super(History.class);
    }
}
