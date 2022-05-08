package com.training.dao.impl;

import com.training.dao.FeedbackDao;
import com.training.dao.GenericJPADAO;
import com.training.model.Feedback;
import org.springframework.stereotype.Service;

@Service
public class FeedbackDaoImpl extends GenericJPADAO<Feedback, Long> implements FeedbackDao {

    private static final String GET_BY_TICKET_ID = "FROM Feedback fb WHERE ticket.id = :id";

    protected FeedbackDaoImpl() {
        super(Feedback.class);
    }

    @Override
    public Feedback getByTicketId(Long ticketId) {
        if (ticketId == null) {
            throw new IllegalArgumentException("Invalid id");
        }
        return getEntityManager()
                .createQuery(GET_BY_TICKET_ID, Feedback.class)
                .setParameter("id", ticketId)
                .getResultStream().findFirst().orElse(null);
    }
}
