package com.training.service.impl;

import com.training.converter.CommentConverter;
import com.training.dao.CommentDao;
import com.training.dto.comment.CommentDto;
import com.training.model.Comment;
import com.training.model.Ticket;
import com.training.service.CommentService;
import com.training.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;

    private final CommentConverter commentConverter;

    private final TicketService ticketService;

    @Override
    @Transactional
    public Comment saveComment(Long ticketId, CommentDto commentDto) {
        Comment comment = commentConverter.toEntity(commentDto);
        Ticket ticket = ticketService.getTicketById(ticketId);

        comment.setTicket(ticket);
        return commentDao.save(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getById(Long id) {
        return commentDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getAllCommentByTicketId(Long id) {
        return commentDao.getAllByTicketId(id)
                .stream()
                .map(commentConverter::toDto)
                .collect(Collectors.toList());
    }

}
