package com.training.service.action;

import com.training.dao.TicketDao;
import com.training.dto.email.EmailDataHolder;
import com.training.model.Ticket;
import com.training.model.enums.HistoryAction;
import com.training.model.enums.State;
import com.training.model.enums.UserRole;
import com.training.service.EmailService;
import com.training.service.HistoryService;
import com.training.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public abstract class UpdateStatusAction {

    public static final String INVALID_ROLE = "Invalid role";

    public static final String INVALID_STATE = "Invalid state";

    protected final UserService userService;

    protected final TicketDao ticketDao;

    protected final EmailService emailService;

    protected final HistoryService historyService;

    protected Map<UserRole, List<State>> mapUserWithState = roleAndStatusMap();

    protected abstract Map<UserRole, List<State>> roleAndStatusMap();

    protected abstract void doUpdateStatus(EmailDataHolder emailDataHolder, Ticket ticket);

    public void updateStatus(Long ticketId) {
        EmailDataHolder emailDataHolder = new EmailDataHolder();

        Ticket ticket = ticketDao.findById(ticketId);

        validate(ticket);
        State oldState = ticket.getState();

        doUpdateStatus(emailDataHolder, ticket);
        ticket.setState(getState());

        ticketDao.update(ticket);
        emailService.sendEmail(emailDataHolder.getUsers(), ticket, emailDataHolder.getTemplate());
        historyService.saveStatusUpdate(ticket, HistoryAction.STATUS_CHANGED, oldState);
    }

    protected abstract void validate(Ticket ticket);

    public abstract State getState();

}
