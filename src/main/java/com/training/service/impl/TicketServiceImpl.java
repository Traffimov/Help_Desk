package com.training.service.impl;

import com.training.converter.FeedbackConverter;
import com.training.converter.TicketConverter;
import com.training.dao.TicketDao;
import com.training.dao.impl.SortingPage;
import com.training.dto.ticket.TicketCreationDto;
import com.training.dto.ticket.TicketDto;
import com.training.exceptions.ValidationException;
import com.training.model.Feedback;
import com.training.model.Ticket;
import com.training.model.User;
import com.training.model.enums.*;
import com.training.service.*;
import com.training.service.action.UpdateStatusAction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketDao ticketDao;

    private final HistoryService historyService;

    private final TicketConverter ticketConverter;

    private final UserService userService;

    private final EmailService emailService;

    private final FeedbackService feedBackService;

    private final FeedbackConverter feedBackConverter;

    private final Map<State, UpdateStatusAction> actionMap;

    @Override
    @Transactional
    public TicketDto save(TicketCreationDto ticketCreationDto) {

        Ticket newTicket = ticketConverter.toEntity(ticketCreationDto);

        if (ticketCreationDto.getState() != null && State.DRAFT.equals(ticketCreationDto.getState())) {
            newTicket.setState(State.DRAFT);
        } else {
            newTicket.setState(State.NEW);
        }

        Ticket createdTicket = ticketDao.save(newTicket);

        if (State.NEW.equals(createdTicket.getState())) {
            emailService.sendEmail(userService.getAllByRole(UserRole.ROLE_MANAGER), createdTicket, Template.CREATED_TICKET);
        }
        historyService.save(createdTicket, HistoryAction.CREATED);
        return ticketConverter.toDto(createdTicket);
    }

    @Override
    @Transactional
    public TicketDto update(TicketCreationDto ticketCreationDto, Long id) {
        Ticket ticket = ticketDao.findById(id);
        if (State.DRAFT.equals(ticket.getState())) {
            Ticket newTicket = ticketConverter.toEntity(ticketCreationDto);
            newTicket.setId(id);
            if (State.DRAFT.equals(ticketCreationDto.getState())) {
                newTicket.setState(State.DRAFT);
            } else {
                newTicket.setState(State.NEW);
                historyService.saveStatusUpdate(newTicket, HistoryAction.STATUS_CHANGED, State.DRAFT);
                emailService.sendEmail(userService.getAllByRole(UserRole.ROLE_MANAGER), newTicket, Template.CREATED_TICKET);
            }
            historyService.save(newTicket, HistoryAction.EDITED);
            ticketDao.update(newTicket);
            return ticketConverter.toDto(newTicket);
        }
        throw new ValidationException("Ticket is not draft");
    }

    @Override
    @Transactional(readOnly = true)
    public TicketDto getById(Long id) {
        TicketDto ticketDto = ticketConverter.toDto(ticketDao.findById(id));
        Feedback feedback = feedBackService.getByTicketId(id);
        ticketDto.setFeedBack(feedBackConverter.toDto(feedback));
        return ticketDto;
    }

    @Override
    @Transactional(readOnly = true)
    public Ticket getTicketById(Long id) {
        return ticketDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketDto> getAllMyTickets(Sort sort, Direction direction, int page) {
        User user = userService.getCurrent();
        SortingPage newSortingPage = getPage(sort, direction, page);
        Long id = user.getId();
        if (UserRole.ROLE_EMPLOYEE.equals(user.getUserRole())) {
            return ticketDao.getMyTickets(id, newSortingPage)
                    .stream()
                    .map(ticketConverter::toDto)
                    .collect(Collectors.toList());
        } else if (UserRole.ROLE_MANAGER.equals(user.getUserRole())) {
            return ticketDao.getAllManagerTickets(id, newSortingPage)
                    .stream()
                    .map(ticketConverter::toDto)
                    .collect(Collectors.toList());
        } else {
            return ticketDao.getAllEngineerTickets(id, newSortingPage)
                    .stream()
                    .map(ticketConverter::toDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketDto> getAllTickets(Sort sort, Direction direction, int page) {
        User user = userService.getCurrent();
        SortingPage newSortingPage = getPage(sort, direction, page);
        Long id = user.getId();
        if (UserRole.ROLE_EMPLOYEE.equals(user.getUserRole())) {
            return ticketDao.getMyTickets(id, newSortingPage)
                    .stream()
                    .map(ticketConverter::toDto)
                    .collect(Collectors.toList());
        } else if (UserRole.ROLE_MANAGER.equals(user.getUserRole())) {
            return ticketDao.getManagerTickets(id, newSortingPage)
                    .stream()
                    .map(ticketConverter::toDto)
                    .collect(Collectors.toList());
        }
        return ticketDao.getEngineerTickets(id, newSortingPage)
                .stream()
                .map(ticketConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateStatus(Long ticketId, State newState) {

        Optional.ofNullable(actionMap.get(newState))
                .orElseThrow(() -> new ValidationException("incorrect state"))
                .updateStatus(ticketId);
    }

    private SortingPage getPage(Sort sort, Direction direction, int page) {
        SortingPage newSortingPage = new SortingPage();
        newSortingPage.setSort(Objects.requireNonNullElse(sort, Sort.ID));
        newSortingPage.setDirection(Objects.requireNonNullElse(direction, Direction.ASC));
        newSortingPage.setPage(page);
        return newSortingPage;
    }

}
