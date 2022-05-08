package com.training.service.action;

import com.training.dao.TicketDao;
import com.training.dto.email.EmailDataHolder;
import com.training.exceptions.ValidationException;
import com.training.model.Ticket;
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
public class CancelStatusAction extends UpdateStatusAction {

    private static final List<State> EMPLOYEE_ALLOWED_STATES = List.of(State.DRAFT, State.DECLINED);
    private static final List<State> MANAGER_ALLOWED_STATES = List.of(State.DRAFT, State.DECLINED, State.NEW);
    private static final List<State> ENGINEER_ALLOWED_STATES = List.of(State.APPROVED);

    public CancelStatusAction(UserService userService, TicketDao ticketDao,
                              EmailService emailService, HistoryService historyService) {
        super(userService, ticketDao, emailService, historyService);
    }

    @Override
    protected Map<UserRole, List<State>> roleAndStatusMap() {
        Map<UserRole, List<State>> mapUserWithState = new EnumMap<>(UserRole.class);
        mapUserWithState.put(UserRole.ROLE_MANAGER, MANAGER_ALLOWED_STATES);
        mapUserWithState.put(UserRole.ROLE_EMPLOYEE, EMPLOYEE_ALLOWED_STATES);
        mapUserWithState.put(UserRole.ROLE_ENGINEER, ENGINEER_ALLOWED_STATES);
        return mapUserWithState;
    }

    @Override
    protected void doUpdateStatus(EmailDataHolder emailDataHolder, Ticket ticket) {
        if (ticket.getState().equals(State.NEW)) {
            emailDataHolder.getUsers().add(ticket.getOwner());
            emailDataHolder.setTemplate(Template.CANCELED_TICKET_BY_MANAGER);
        } else if (ticket.getState().equals(State.APPROVED)) {
            emailDataHolder.getUsers().add(ticket.getOwner());
            emailDataHolder.getUsers().add(ticket.getApprover());
            emailDataHolder.setTemplate(Template.CANCELED_TICKET_BY_ENGINEER);
        }
    }

    @Override
    protected void validate(Ticket ticket) {
        UserRole userRole = userService.getCurrent().getUserRole();
        if(!mapUserWithState.get(userRole).contains(ticket.getState())){
            throw new ValidationException(INVALID_STATE);
        }
    }

    @Override
    public State getState() {
        return State.CANCELED;
    }
}
