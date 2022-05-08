package com.training.converter;

import com.training.dto.feedback.FeedbackDto;
import com.training.model.Feedback;
import com.training.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class FeedbackConverter implements Converter<FeedbackDto, Feedback> {

    private final UserConverter userConverter;

    private final UserService userService;

    @Override
    public FeedbackDto toDto(Feedback feedBack) {
        if (feedBack != null) {
            FeedbackDto feedBackDto = new FeedbackDto();
            feedBackDto.setId(feedBack.getId());
            feedBackDto.setUserDto(userConverter.toDto(feedBack.getUser()));
            feedBackDto.setDate(feedBack.getDate());
            feedBackDto.setRate(feedBack.getRate());
            feedBackDto.setText(feedBack.getText());
            return feedBackDto;
        }
        return null;
    }

    @Override
    public Feedback toEntity(FeedbackDto feedbackDto) {
        Feedback feedback = new Feedback();
        feedback.setUser(userService.getCurrent());
        feedback.setText(feedbackDto.getText());
        feedback.setRate(feedbackDto.getRate());
        feedback.setDate(LocalDateTime.now());
        return feedback;
    }

}
