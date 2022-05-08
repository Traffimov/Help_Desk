package com.training.service;

import com.training.model.Attachment;
import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {

    Attachment save(Attachment attachment);

    void saveAttachment(Long ticketId, MultipartFile multipartFile);

    Attachment getById(Long id);

    void deleteAttachment(Long attachmentId);
}
