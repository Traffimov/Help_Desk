package com.training.service;

import com.training.dto.history.HistoryDto;
import com.training.model.History;
import com.training.model.Ticket;
import com.training.model.enums.HistoryAction;
import com.training.model.enums.State;

import java.util.List;

public interface HistoryService {

    History save(Ticket ticket, HistoryAction historyAction);

    History saveStatusUpdate(Ticket ticket, HistoryAction historyAction, State oldState);

    History updateAttachment(Ticket ticket, HistoryAction historyAction, String name);

    List<HistoryDto> getAllHistory(Long id);
}
