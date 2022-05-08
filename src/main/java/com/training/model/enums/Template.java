package com.training.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Template {

    CREATED_TICKET("createdTicket.html", "New ticket for approval"),
    APPROVED_TICKET("approvedTicket.html", "Ticket was approved"),
    PROVIDED_TICKET("providedTicket.html", "Ticket was provided"),
    DECLINED_TICKET("declinedTicket.html", "Ticket was declined"),
    CANCELED_TICKET_BY_ENGINEER("canceledTicketByEngineer.html", "Ticket was canceled"),
    CANCELED_TICKET_BY_MANAGER("canceledTicketByManager.html", "Ticket was canceled"),
    TICKET_DONE("ticketDone.html", "Ticket was done"),
    FEEDBACK_PROVIDED("providedTicket.html", "Feedback was provided");

    private final String templateName;
    private final String subject;
    }
