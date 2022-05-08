package com.training.converter;

import com.training.dto.ticket.TicketCreationDto;
import com.training.dto.ticket.TicketDto;
import com.training.model.Comment;
import com.training.model.Ticket;
import com.training.model.User;
import com.training.model.enums.Action;
import com.training.model.enums.State;
import com.training.model.enums.UserRole;
import com.training.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Component
@RequiredArgsConstructor
public class TicketConverter implements Converter<TicketDto, Ticket> {

    private static final Map<UserRole, Map<State, List<Action>>> actionMap = new HashMap<>();

    private final AttachmentConverter attachmentConverter;

    private final UserConverter userConverter;

    private final UserService userService;

    static {
        actionMap.put(UserRole.ROLE_EMPLOYEE, Map.of(
                State.DRAFT, List.of(Action.CANCEL, Action.SUBMIT),
                State.DECLINED, List.of(Action.CANCEL, Action.SUBMIT),
                State.DONE, List.of(Action.LEAVE_FEEDBACK)));
        actionMap.put(UserRole.ROLE_ENGINEER, Map.of(
                State.APPROVED, List.of(Action.ASSIGN_TO_ME, Action.CANCEL),
                State.IN_PROGRESS, List.of(Action.COMPLETE)));
        actionMap.put(UserRole.ROLE_MANAGER, Map.of(
                State.DRAFT, List.of(Action.CANCEL, Action.SUBMIT),
                State.DECLINED, List.of(Action.CANCEL, Action.SUBMIT),
                State.NEW, List.of(Action.APPROVE, Action.CANCEL, Action.DECLINE)));
    }

    @Override
    public TicketDto toDto(Ticket ticket) {

        User current = userService.getCurrent();
        List<Action> actions = actionMap.get(current.getUserRole()).get(ticket.getState());

        TicketDto ticketDto = new TicketDto();

        ticketDto.setApprover(userConverter.toDto(ticket.getApprover()));
        ticketDto.setAssignee(userConverter.toDto(ticket.getAssignee()));
        ticketDto.setOwner(userConverter.toDto(ticket.getOwner()));
        ticketDto.setCategory(ticket.getCategory());
        ticketDto.setAttachments(ticket.getAttachments()
                .stream()
                .map(attachmentConverter::toDto)
                .collect(Collectors.toList()));
        ticketDto.setDescription(ticket.getDescription());
        ticketDto.setName(ticket.getName());
        ticketDto.setCreatedOn(ticket.getCreatedOn());
        ticketDto.setId(ticket.getId());
        ticketDto.setUrgency(ticket.getUrgency());
        ticketDto.setState(ticket.getState());
        ticketDto.setDesiredResolutionDate(ticket.getDesiredResolutionDate());

        if (ticket.getOwner().equals(current)
                || (!ticket.getState().equals(State.DRAFT) && !ticket.getState().equals(State.DECLINED))) {
            ticketDto.setActions(actions);
        }
        return ticketDto;
    }

    public Ticket toEntity(TicketCreationDto ticketCreationDto) {
        LocalDateTime dateTime = LocalDateTime.now();

        User user = userService.getCurrent();

        Ticket ticket = new Ticket();
        ticket.setOwner(user);
        ticket.setCategory(ticketCreationDto.getCategory());
        ticket.setName(ticketCreationDto.getName());
        ticket.setUrgency(ticketCreationDto.getUrgency());
        ticket.setDesiredResolutionDate(ticketCreationDto.getDesiredResolutionDate());
        ticket.setDescription(ticketCreationDto.getDescription());
        ticket.setCreatedOn(dateTime);
        if (ticketCreationDto.getComment() != null) {
            Comment comment = new Comment();
            comment.setText(ticketCreationDto.getComment());
            comment.setUser(user);
            comment.setDate(dateTime);
            ticket.getComments().add(comment);
            comment.setTicket(ticket);
        }
        return ticket;
    }

    @Override
    public Ticket toEntity(TicketDto ticketDto) {
        throw new UnsupportedOperationException("Method not supported.");
    }
}
