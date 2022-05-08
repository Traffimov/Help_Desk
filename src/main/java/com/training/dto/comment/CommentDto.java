package com.training.dto.comment;

import com.training.dto.user.UserDto;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
public class CommentDto {

    private Long id;

    @Pattern(regexp = "^[A-Za-z0-9~.\"(),:;<>@\\[\\]!#$%&'*+-/=?^_`{|}]{0,500}$", message = "Invalid comment name")
    private String text;

    private LocalDateTime date;

    private UserDto userDto;
}
