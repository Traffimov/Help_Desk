package com.training.service.impl;

import com.training.dao.AttachmentDao;
import com.training.model.Attachment;
import com.training.model.Ticket;
import com.training.model.enums.HistoryAction;
import com.training.service.AttachmentService;
import com.training.service.HistoryService;
import com.training.service.TicketService;
import com.training.validators.AttachmentValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentDao attachmentDao;

    private final HistoryService historyService;

    private final AttachmentValidator attachmentValidator;

    private final TicketService ticketService;

    @Override
    @Transactional
    public Attachment save(Attachment attachment) {
        return attachmentDao.save(attachment);
    }

    @Override
    @Transactional
    public void saveAttachment(Long ticketId, MultipartFile multipartFile) {
        attachmentValidator.validateFileAttachment(multipartFile);

        Ticket ticket = ticketService.getTicketById(ticketId);

        Attachment attachment = new Attachment();
        try {
            attachment.setTicket(ticket);
            attachment.setName(multipartFile.getOriginalFilename());
            attachment.setBlob(multipartFile.getBytes());
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        save(attachment);
        historyService.updateAttachment(ticket, HistoryAction.ATTACHED_FILE, multipartFile.getOriginalFilename());
    }

    @Override
    @Transactional(readOnly = true)
    public Attachment getById(Long id) {
        return attachmentDao.findById(id);
    }

    @Override
    @Transactional
    public void deleteAttachment(Long attachmentId) {
        Attachment attachment = attachmentDao.findById(attachmentId);
        historyService.updateAttachment(attachment.getTicket(), HistoryAction.REMOVED_FILE, attachment.getName());
        attachmentDao.delete(attachmentId);
    }
}
