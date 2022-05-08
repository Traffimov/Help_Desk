package com.training.dto.feedback;

import com.training.dto.user.UserDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedbackDto {

    private Long id;

    private int rate;

    private String text;

    private LocalDateTime date;

    private UserDto userDto;
}
