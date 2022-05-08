package com.training.dao.impl;

import com.training.dao.AttachmentDao;
import com.training.dao.GenericJPADAO;
import com.training.model.Attachment;
import org.springframework.stereotype.Repository;

@Repository
public class AttachmentDaoImpl extends GenericJPADAO<Attachment, Long> implements AttachmentDao {

    protected AttachmentDaoImpl() {
        super(Attachment.class);
    }
}
