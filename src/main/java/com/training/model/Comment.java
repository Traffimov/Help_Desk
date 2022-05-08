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
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    private LocalDateTime date;

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
        Comment comment1 = (Comment) o;

        return Objects.equals(id, comment1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
