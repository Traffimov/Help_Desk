package com.training.service.impl;

import com.training.converter.FeedbackConverter;
import com.training.dao.FeedbackDao;
import com.training.dao.TicketDao;
import com.training.dto.feedback.FeedbackDto;
import com.training.model.Feedback;
import com.training.model.Ticket;
import com.training.model.enums.Template;
import com.training.service.EmailService;
import com.training.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackDao feedBackDao;

    private final EmailService emailService;

    private final TicketDao ticketDao;

    private final FeedbackConverter feedbackConverter;

    @Override
    @Transactional
    public Feedback save(Feedback feedBack) {
        return feedBackDao.save(feedBack);
    }

    @Override
    @Transactional
    public void saveFeedback(Long ticketId, FeedbackDto feedBackDto) {
        Ticket ticket = ticketDao.findById(ticketId);

        Feedback feedBack = feedbackConverter.toEntity(feedBackDto);
        feedBack.setTicket(ticket);

        emailService.sendEmail(Collections.singletonList(ticket.getAssignee()), ticket, Template.FEEDBACK_PROVIDED);

        save(feedBack);
    }

    @Override
    @Transactional(readOnly = true)
    public Feedback getById(Long id) {
        return feedBackDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Feedback getByTicketId(Long ticketId) {
        return feedBackDao.getByTicketId(ticketId);
    }
}
