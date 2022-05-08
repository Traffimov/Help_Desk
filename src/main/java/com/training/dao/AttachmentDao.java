package com.training.dao;

import com.training.model.Attachment;

public interface AttachmentDao{

    Attachment save(Attachment attachment);

    Attachment findById(Long id);

    void delete(Long id);

}
