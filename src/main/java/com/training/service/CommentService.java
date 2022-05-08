package com.training.service;

import com.training.dto.comment.CommentDto;
import com.training.model.Comment;

import java.util.List;

public interface CommentService {

    Comment saveComment(Long ticketId, CommentDto commentDto);

    Comment getById(Long id);

    List<CommentDto> getAllCommentByTicketId(Long id);
}
