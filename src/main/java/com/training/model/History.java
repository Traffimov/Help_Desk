package com.training.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime date;

    private String description;

    private String action;
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        History history = (History) o;

        return Objects.equals(id, history.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
