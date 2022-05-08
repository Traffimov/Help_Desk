package com.training.controller;

import com.training.dto.ticket.TicketCreationDto;
import com.training.dto.ticket.TicketDto;
import com.training.model.enums.Direction;
import com.training.model.enums.Sort;
import com.training.model.enums.State;
import com.training.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_MANAGER')")
    @PostMapping
    public ResponseEntity<TicketDto> save(@Valid @RequestBody TicketCreationDto ticketCreationDto) {
        return ResponseEntity.created(URI.create("/OK"))
                .body(ticketService.save(ticketCreationDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketDto> updateTicket(@Valid @RequestBody TicketCreationDto ticketCreationDto, @PathVariable Long id) {
        return ResponseEntity.ok(ticketService.update(ticketCreationDto, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getById(id));
    }

    @GetMapping("/alltickets")
    public ResponseEntity<List<TicketDto>> getAllTicket(@RequestParam(required = false) Sort sort,
                                                        @RequestParam(required = false) Direction direction,
                                                        @RequestParam(required = false) Integer page) {
        return ResponseEntity.ok(ticketService.getAllTickets(sort, direction, Objects.requireNonNullElse(page, 1)));
    }

    @GetMapping("/allmytickets")
    public ResponseEntity<List<TicketDto>> getAllMyTicket(@RequestParam(required = false) Sort sort,
                                                          @RequestParam(required = false) Direction direction,
                                                          @RequestParam(required = false) Integer page) {
        return ResponseEntity.ok(ticketService.getAllMyTickets(sort, direction, Objects.requireNonNullElse(page, 1)));
    }

    @PostMapping("/{id}/update-status/{newState}")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id,
                                             @PathVariable State newState) {
        ticketService.updateStatus(id, newState);
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
