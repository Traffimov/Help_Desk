package com.training.model;

import com.training.model.enums.State;
import com.training.model.enums.Urgency;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "desired_resolution_date")
    private LocalDate desiredResolutionDate;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "approver_id")
    private User approver;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "state_id", nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    @Column(name = "urgency_id", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Urgency urgency;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticket")
    @ToString.Exclude
    private List<Attachment> attachments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticket", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<History> history = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticket", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Ticket ticket = (Ticket) o;

        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
