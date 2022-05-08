package com.training.service.impl;

import com.training.converter.HistoryConverter;
import com.training.dao.HistoryDao;
import com.training.dao.TicketDao;
import com.training.dto.history.HistoryDto;
import com.training.model.History;
import com.training.model.Ticket;
import com.training.model.enums.HistoryAction;
import com.training.model.enums.State;
import com.training.service.HistoryService;
import com.training.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryConverter historyConverter;

    private final UserService userService;

    private final HistoryDao historyDao;

    private final TicketDao ticketDao;

    @Override
    public History save(Ticket ticket, HistoryAction historyAction) {
        History history = getHistory(ticket, historyAction);
        history.setDescription(historyAction.getDescription());
        return historyDao.save(history);
    }

    @Override
    public History saveStatusUpdate(Ticket ticket, HistoryAction historyAction, State oldState) {
        History history = getHistory(ticket, historyAction);
        history.setDescription(String.format(historyAction.getDescription(), oldState.name(), ticket.getState().name()));
        return historyDao.save(history);
    }

    @Override
    public History updateAttachment(Ticket ticket, HistoryAction historyAction, String name) {
        History history = getHistory(ticket, historyAction);
        history.setDescription(String.format(historyAction.getDescription(), name));
        return historyDao.save(history);
    }

    private History getHistory(Ticket ticket, HistoryAction historyAction) {
        History history = new History();
        history.setTicket(ticket);
        history.setDate(LocalDateTime.now());
        history.setUser(userService.getCurrent());
        history.setAction(historyAction.getName());
        return history;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoryDto> getAllHistory(Long id) {

        Ticket ticket = ticketDao.findById(id);

        return ticket.getHistory()
                .stream()
                .map(historyConverter::toDto)
                .collect(Collectors.toList());
    }
}
