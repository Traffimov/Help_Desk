package com.training.service.impl;

import com.training.dto.ticket.TicketCreationDto;
import com.training.dto.ticket.TicketDto;
import com.training.converter.TicketConverter;
import com.training.dao.TicketDao;
import com.training.exceptions.ValidationException;
import com.training.model.Ticket;
import com.training.model.User;
import com.training.model.enums.HistoryAction;
import com.training.model.enums.State;
import com.training.model.enums.UserRole;
import com.training.service.EmailService;
import com.training.service.HistoryService;
import com.training.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Mock
    private TicketDao ticketDao;

    @Mock
    private HistoryService historyService;

    @Mock
    private TicketConverter ticketConverter;

    @Mock
    private UserService userService;

    @Mock
    private EmailService emailService;

    @Test
    void testSaveWithDraft() {
        TicketCreationDto ticketCreationDto = new TicketCreationDto();
        ticketCreationDto.setState(State.DRAFT);

        Ticket ticket = new Ticket();
        ticket.setState(State.DRAFT);

        TicketDto ticketDto = new TicketDto();
        ticketDto.setState(State.DRAFT);

        Mockito.when(ticketConverter.toEntity(ticketCreationDto)).thenReturn(ticket);
        Mockito.when(ticketDao.save(ticket)).thenReturn(ticket);
        Mockito.when(ticketConverter.toDto(ticket)).thenReturn(ticketDto);
        TicketDto newTicketDto = ticketService.save(ticketCreationDto);

        Assertions.assertEquals(newTicketDto.getState(), ticketDto.getState());

        Mockito.verify(historyService, Mockito.times(1)).save(ticket, HistoryAction.CREATED);
    }

    @Test
    void testSaveWithNew() {
        TicketCreationDto ticketCreationDto = new TicketCreationDto();
        ticketCreationDto.setState(State.NEW);

        Ticket ticket = new Ticket();
        ticket.setState(State.NEW);

        User user = new User();
        ticket.setOwner(user);

        TicketDto ticketDto = new TicketDto();
        ticketDto.setState(State.NEW);

        User manager = new User();
        manager.setEmail("g@g.g");

        Mockito.when(ticketConverter.toEntity(ticketCreationDto)).thenReturn(ticket);
        Mockito.when(ticketDao.save(ticket)).thenReturn(ticket);
        Mockito.when(userService.getAllByRole(UserRole.ROLE_MANAGER)).thenReturn(Collections.singletonList(manager));
        Mockito.when(ticketConverter.toDto(ticket)).thenReturn(ticketDto);
        TicketDto newTicketDto = ticketService.save(ticketCreationDto);

        Assertions.assertEquals(newTicketDto.getState(), ticketDto.getState());

        Mockito.verify(emailService, Mockito.times(1)).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.verify(historyService, Mockito.times(1)).save(ticket, HistoryAction.CREATED);

    }

    @Test
    void testUpdateWithDraft() {
        Ticket ticket = new Ticket();
        ticket.setState(State.DRAFT);
        ticket.setId(1L);

        TicketCreationDto ticketCreationDto = new TicketCreationDto();
        ticketCreationDto.setState(State.DRAFT);

        TicketDto ticketDto = new TicketDto();
        ticketDto.setState(State.DRAFT);
        ticket.setId(1L);


        Mockito.when(ticketDao.findById(1L)).thenReturn(ticket);
        Mockito.when(ticketConverter.toEntity(ticketCreationDto)).thenReturn(ticket);
        Mockito.when(ticketConverter.toDto(ticket)).thenReturn(ticketDto);

        TicketDto newTicketDto = ticketService.update(ticketCreationDto, 1L);

        Assertions.assertEquals(newTicketDto.getState(), ticketDto.getState());

        Mockito.verify(historyService, Mockito.times(1)).save(ticket, HistoryAction.EDITED);
        Mockito.verify(ticketDao, Mockito.times(1)).update(ticket);

    }

    @Test
    void testUpdateWithNew() {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setState(State.DRAFT);

        User user = new User();
        ticket.setOwner(user);

        TicketCreationDto ticketCreationDto = new TicketCreationDto();
        ticketCreationDto.setState(State.NEW);
        ticketCreationDto.setName("NewName");

        Ticket newTicket = new Ticket();
        newTicket.setId(1L);
        newTicket.setState(State.NEW);

        TicketDto ticketDto = new TicketDto();
        ticketDto.setState(State.NEW);
        ticketDto.setName("OldName");

        User manager = new User();
        manager.setEmail("g@g.g");

        Mockito.when(ticketDao.findById(1L)).thenReturn(ticket);
        Mockito.when(ticketConverter.toEntity(ticketCreationDto)).thenReturn(newTicket);
        Mockito.when(userService.getAllByRole(UserRole.ROLE_MANAGER)).thenReturn(Collections.singletonList(manager));
        Mockito.when(ticketConverter.toDto(newTicket)).thenReturn(ticketDto);

        TicketDto newTicketDto = ticketService.update(ticketCreationDto, 1L);

        Assertions.assertEquals(ticketDto.getName(), newTicketDto.getName());

        Mockito.verify(historyService, Mockito.times(1))
                .saveStatusUpdate(newTicket, HistoryAction.STATUS_CHANGED, State.DRAFT);
        Mockito.verify(emailService, Mockito.times(1))
                .sendEmail(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.verify(ticketDao, Mockito.times(1))
                .update(newTicket);
        Mockito.verify(historyService, Mockito.times(1))
                .save(newTicket, HistoryAction.EDITED);
    }

    @Test
    void testUpdateValidation() {
        TicketCreationDto ticketCreationDto = new TicketCreationDto();

        Ticket ticket = new Ticket();
        ticket.setState(State.NEW);
        ticket.setId(1L);

        Mockito.when(ticketDao.findById(1L)).thenReturn(ticket);

        ValidationException thrown = assertThrows(
                ValidationException.class,
                () -> ticketService.update(ticketCreationDto, 1L)
        );

        assertTrue(thrown.getMessage().contains("Ticket is not draft"));

    }

}