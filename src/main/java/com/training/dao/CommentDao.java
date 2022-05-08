package com.training.dao;

import com.training.model.Comment;

import java.util.List;

public interface CommentDao {

    Comment save(Comment comment);

    Comment findById(Long id);

    List<Comment> getAllByTicketId(Long ticketId);

    List<Comment> findAll();
}
