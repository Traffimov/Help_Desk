package com.training.service.action;

import com.training.dao.TicketDao;
import com.training.dto.email.EmailDataHolder;
import com.training.exceptions.ValidationException;
import com.training.model.Ticket;
import com.training.model.User;
import com.training.model.enums.State;
import com.training.model.enums.Template;
import com.training.model.enums.UserRole;
import com.training.service.EmailService;
import com.training.service.HistoryService;
import com.training.service.UserService;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class DoneStatusAction extends UpdateStatusAction {

    private static final List<State> ENGINEER_ALLOWED_STATES = List.of(State.IN_PROGRESS);

    public DoneStatusAction(UserService userService, TicketDao ticketDao,
                            EmailService emailService, HistoryService historyService) {
        super(userService, ticketDao, emailService, historyService);
    }

    @Override
    protected Map<UserRole, List<State>> roleAndStatusMap() {
        Map<UserRole, List<State>> mapUserWithState = new EnumMap<>(UserRole.class);
        mapUserWithState.put(UserRole.ROLE_ENGINEER, ENGINEER_ALLOWED_STATES);
        return mapUserWithState;
    }

    @Override
    protected void doUpdateStatus(EmailDataHolder emailDataHolder, Ticket ticket) {
        User user = userService.getCurrent();
        ticket.setAssignee(user);
        emailDataHolder.getUsers().add(ticket.getOwner());
        emailDataHolder.setTemplate(Template.TICKET_DONE);
    }

    @Override
    protected void validate(Ticket ticket) {
        UserRole userRole = userService.getCurrent().getUserRole();
        if (!userRole.equals(UserRole.ROLE_ENGINEER)) {
            throw new ValidationException(INVALID_ROLE);
        }
        if (!mapUserWithState.get(userRole).contains(ticket.getState())) {
            throw new ValidationException(INVALID_STATE);
        }
    }

    @Override
    public State getState() {
        return State.DONE;
    }
}
