package com.training.dto.ticket;

import com.training.model.Category;
import com.training.model.enums.State;
import com.training.model.enums.Urgency;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class TicketCreationDto {

    @Pattern(regexp = "^[A-Za-z0-9~.\"(),:;<>@\\[\\]!#$%&'*+-/=?^_`{|}]{0,100}$", message = "Invalid ticket name")
    private String name;

    private Category category;

    private Urgency urgency;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate desiredResolutionDate;

    @Size(max = 500, message = "description size is more than 500 characters.")
    private String description;

    private String comment;

    private State state;

}
