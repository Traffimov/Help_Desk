package com.training.dao.impl;

import com.training.dao.CommentDao;
import com.training.dao.GenericJPADAO;
import com.training.model.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDaoImpl extends GenericJPADAO<Comment, Long> implements CommentDao {

    private static final String GET_ALL_BY_TICKET_ID = "From Comment c WHERE ticket.id = :id";

    protected CommentDaoImpl() {
        super(Comment.class);
    }

    @Override
    public List<Comment> getAllByTicketId(Long ticketId) {
        if (ticketId == null) {
            throw new IllegalArgumentException("Invalid id");
        }
        return getEntityManager()
                .createQuery(GET_ALL_BY_TICKET_ID, Comment.class)
                .setParameter("id", ticketId)
                .getResultList();
    }

}
