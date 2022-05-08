package com.training.controller;

import com.training.dto.feedback.FeedbackDto;
import com.training.model.Feedback;
import com.training.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedBackService;

    @GetMapping("/feedbacks/{id}")
    public ResponseEntity<Feedback> getById(@PathVariable Long id) {
        return ResponseEntity.ok(feedBackService.getById(id));
    }

    @PostMapping("/ticket/{id}/feedbacks")
    public ResponseEntity<Void> addFeedback(@RequestBody FeedbackDto feedBackDto,
                                            @PathVariable Long id) {
        feedBackService.saveFeedback(id, feedBackDto);
        return ResponseEntity.ok().build();
    }

}
