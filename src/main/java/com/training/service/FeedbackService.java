package com.training.service;

import com.training.dto.feedback.FeedbackDto;
import com.training.model.Feedback;

public interface FeedbackService {

    Feedback save(Feedback feedBack);

    Feedback getByTicketId(Long ticketId);

    void saveFeedback(Long ticketId, FeedbackDto feedBackDto);

    Feedback getById(Long id);
}
