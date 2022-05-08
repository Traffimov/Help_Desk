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
public class ApproveStatusAction extends UpdateStatusAction {

    private static final List<State> MANAGER_ALLOWED_STATES = List.of(State.NEW);

    public ApproveStatusAction(UserService userService, TicketDao ticketDao,
                               EmailService emailService, HistoryService historyService) {
        super(userService, ticketDao, emailService, historyService);
    }

    @Override
    protected Map<UserRole, List<State>> roleAndStatusMap() {
        Map<UserRole, List<State>> mapUserWithState = new EnumMap<>(UserRole.class);
        mapUserWithState.put(UserRole.ROLE_MANAGER, MANAGER_ALLOWED_STATES);
        return mapUserWithState;
    }

    @Override
    public void doUpdateStatus(EmailDataHolder emailDataHolder, Ticket ticket) {
        User user = userService.getCurrent();
        ticket.setApprover(user);
        emailDataHolder.getUsers().add(ticket.getOwner());
        emailDataHolder.getUsers().addAll(userService.getAllByRole(UserRole.ROLE_ENGINEER));
        emailDataHolder.setTemplate(Template.APPROVED_TICKET);
    }

    @Override
    protected void validate(Ticket ticket) {
        UserRole userRole = userService.getCurrent().getUserRole();
        if(!userRole.equals(UserRole.ROLE_MANAGER)){
            throw new ValidationException(INVALID_ROLE);
        }
        if (!roleAndStatusMap().get(userRole).contains(ticket.getState())) {//^!!!!!
            throw new ValidationException(INVALID_STATE);
        }
    }

    @Override
    public State getState() {
        return State.APPROVED;
    } //Pochemy publick
}
