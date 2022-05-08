package com.training.dao.impl;

import com.training.dao.GenericJPADAO;
import com.training.dao.TicketDao;
import com.training.exceptions.ValidationException;
import com.training.model.Ticket;
import com.training.model.enums.Direction;
import com.training.model.enums.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TicketDaoImpl extends GenericJPADAO<Ticket, Long> implements TicketDao {

    public static final int MAX_PAGE_RESULT = 5;

    private static final String GET_PERSONAL_TICKETS_FOR_EMPLOYEE = "FROM Ticket WHERE owner.id = :id";

    private static final String GET_ALL_MANAGER_TICKETS = "FROM Ticket WHERE owner.id = :id OR (owner.id != :id AND state = 'NEW') " +
            "OR (approver.id= :id AND state = 'APPROVED' or state='DECLINED' or state='CANCELED' or state='IN_PROGRESS' or state='DONE')";

    private static final String GET_ALL_ENGINEER_TICKETS = "FROM Ticket WHERE (assignee.id = :id AND state = 'DONE' or state = 'IN_PROGRESS') " +
            "OR (state = 'APPROVED')";

    private static final String GET_PERSONAL_TICKETS_FOR_MANAGER = "FROM Ticket WHERE owner.id = :id OR (approver.id= :id AND state = 'APPROVED')";

    private static final String GET_PERSONAL_TICKETS_FOR_ENGINEER = "FROM Ticket WHERE assignee.id = :id";

    private static final String ORDER_BY_NAME = " order by name ";
    private static final String ORDER_BY_ID = " order by id ";
    private static final String ORDER_BY_DESIRED_DATE = " order by desiredResolutionDate ";
    private static final String ORDER_BY_STATUS = " order by state ";
    private static final String ORDER_BY_URGENCY = " order by urgency ";

    private static final Map<Sort, String> SORTING_TYPES = Map.of(
            Sort.ID, ORDER_BY_ID,
            Sort.NAME, ORDER_BY_NAME,
            Sort.STATE, ORDER_BY_STATUS,
            Sort.URGENCY, ORDER_BY_URGENCY,
            Sort.DESIRED_DATE, ORDER_BY_DESIRED_DATE);

    private static final Map<Direction, String> SORTING_DIRECTIONS = Map.of(
            Direction.DESC, Direction.DESC.name(),
            Direction.ASC, Direction.ASC.name()
    );

    protected TicketDaoImpl() {
        super(Ticket.class);
    }

    @Override
    public List<Ticket> getAllManagerTickets(Long id, SortingPage sortingPage) {
        String orderBy = generateOrderBy(sortingPage);
        TypedQuery<Ticket> query = getEntityManager()
                .createQuery(GET_PERSONAL_TICKETS_FOR_MANAGER + orderBy, Ticket.class)
                .setParameter("id", id)
                .setFirstResult(getStartPosition(sortingPage.getPage()))
                .setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    @Override
    public List<Ticket> getAllEngineerTickets(Long id, SortingPage sortingPage) {
        String orderBy = generateOrderBy(sortingPage);
        TypedQuery<Ticket> query = getEntityManager()
                .createQuery(GET_PERSONAL_TICKETS_FOR_ENGINEER + orderBy, Ticket.class)
                .setParameter("id", id)
                .setFirstResult(getStartPosition(sortingPage.getPage()))
                .setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    @Override
    public List<Ticket> getMyTickets(Long id, SortingPage sortingPage) {
        String orderBy = generateOrderBy(sortingPage);
        TypedQuery<Ticket> query = getEntityManager()
                .createQuery(GET_PERSONAL_TICKETS_FOR_EMPLOYEE + orderBy, Ticket.class)
                .setParameter("id", id)
                .setFirstResult(getStartPosition(sortingPage.getPage()))
                .setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    @Override
    public List<Ticket> getManagerTickets(Long id, SortingPage sortingPage) {
        String orderBy = generateOrderBy(sortingPage);
        TypedQuery<Ticket> query = getEntityManager()
                .createQuery(GET_ALL_MANAGER_TICKETS + orderBy, Ticket.class)
                .setParameter("id", id)
                .setFirstResult(getStartPosition(sortingPage.getPage()))
                .setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    @Override
    public List<Ticket> getEngineerTickets(Long id, SortingPage sortingPage) {
        String orderBy = generateOrderBy(sortingPage);
        TypedQuery<Ticket> query = getEntityManager()
                .createQuery(GET_ALL_ENGINEER_TICKETS + orderBy, Ticket.class)
                .setParameter("id", id)
                .setFirstResult(getStartPosition(sortingPage.getPage()))
                .setMaxResults(MAX_PAGE_RESULT);
        return query.getResultList();
    }

    private int getStartPosition(int page) {
        return page * MAX_PAGE_RESULT - MAX_PAGE_RESULT;
    }

    public String generateOrderBy(SortingPage sortingPage) {

        return Optional.ofNullable(SORTING_TYPES.get(sortingPage.getSort()))
                .orElseThrow(() -> new ValidationException("Invalid sort")) +
                Optional.ofNullable(SORTING_DIRECTIONS.get(sortingPage.getDirection()))
                        .orElseThrow(() -> new ValidationException("Invalid sort"));

    }
}
