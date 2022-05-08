package com.training.controller;

import com.training.dao.CommentDao;
import com.training.dto.comment.CommentDto;
import com.training.model.Comment;
import com.training.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentDao commentDao;

    private final CommentService commentService;

    @GetMapping("/comments")
    public ResponseEntity<List<Comment>> findAll() {
        return ResponseEntity.ok(commentDao.findAll());
    }

    @PostMapping("/ticket/{id}/comments")
    public ResponseEntity<Void> addComment(@Valid @RequestBody CommentDto commentDto,
                                           @PathVariable Long id) {
        commentService.saveComment(id, commentDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ticket/{id}/comments")
    public ResponseEntity<List<CommentDto>> getAllCommentByTicketId(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getAllCommentByTicketId(id));
    }

}
