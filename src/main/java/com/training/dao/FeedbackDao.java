package com.training.dao;

import com.training.model.Feedback;

public interface FeedbackDao {

    Feedback save(Feedback feedBack);

    Feedback getByTicketId(Long ticketId);

    Feedback findById(Long id);
}
