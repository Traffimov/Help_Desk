package com.training.converter;

import com.training.dto.comment.CommentDto;
import com.training.model.Comment;
import com.training.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CommentConverter implements Converter<CommentDto, Comment> {

    private final UserConverter userConverter;

    private final UserService userService;

    @Override
    public CommentDto toDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setUserDto(userConverter.toDto(comment.getUser()));
        commentDto.setDate(comment.getDate());
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        return commentDto;
    }

    @Override
    public Comment toEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setUser(userService.getCurrent());
        comment.setDate(LocalDateTime.now());
        comment.setText(commentDto.getText());
        return comment;
    }
}
