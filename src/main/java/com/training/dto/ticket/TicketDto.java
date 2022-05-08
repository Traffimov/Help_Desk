package com.training.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.training.dto.attachment.AttachmentDto;
import com.training.dto.feedback.FeedbackDto;
import com.training.dto.user.UserDto;
import com.training.model.Category;
import com.training.model.enums.Action;
import com.training.model.enums.State;
import com.training.model.enums.Urgency;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TicketDto {

    private Long id;

    private String name;

    private String description;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime createdOn;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate desiredResolutionDate;

    private UserDto assignee;

    private UserDto owner;

    private UserDto approver;

    private Category category;

    private State state;

    private Urgency urgency;

    private List<AttachmentDto> attachments;

    private FeedbackDto feedBack;

    private List<Action> actions;

}
