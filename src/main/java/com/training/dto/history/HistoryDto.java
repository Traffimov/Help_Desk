package com.training.dto.history;

import com.training.dto.user.UserDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoryDto {

    private Long id;

    private LocalDateTime date;

    private String description;

    private String action;

    private UserDto userDto;
}
