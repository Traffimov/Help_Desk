package com.training.dao;

import com.training.dao.impl.SortingPage;
import com.training.model.Ticket;

import java.util.List;

public interface TicketDao {

    Ticket save(Ticket ticket);

    Ticket findById(Long id);

    List<Ticket> getAllManagerTickets(Long id, SortingPage sortingPage);

    List<Ticket> getAllEngineerTickets(Long id, SortingPage sortingPage);

    List<Ticket> getMyTickets(Long id, SortingPage sortingPage);

    List<Ticket> getManagerTickets(Long id, SortingPage sortingPage);

    List<Ticket> getEngineerTickets(Long id, SortingPage sortingPage);

    void update(Ticket ticket);
}
