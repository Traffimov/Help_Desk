package com.training.service;

import com.training.dto.ticket.TicketCreationDto;
import com.training.dto.ticket.TicketDto;
import com.training.model.Ticket;
import com.training.model.enums.Direction;
import com.training.model.enums.Sort;
import com.training.model.enums.State;

import java.util.List;

public interface TicketService {

    TicketDto save(TicketCreationDto ticketCreationDto);

    TicketDto update(TicketCreationDto ticketCreationDto, Long id);

    TicketDto getById(Long id);

    Ticket getTicketById(Long id);

    List<TicketDto> getAllMyTickets(Sort sort, Direction direction, int page);

    List<TicketDto> getAllTickets(Sort sort, Direction direction, int page);

    void updateStatus(Long ticketId, State newState);

}
