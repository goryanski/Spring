package com.softserve.itacademy.model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Table(name = "todos")
public class ToDo {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "todo_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "15"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private long id;

    @Column(name = "created_at")
    private LocalDateTime  createdAt;

    @Column(name = "title")
    @NotBlank(message = "Todo name should not be null or empty")
    private String title;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @ManyToMany(mappedBy = "collectiveTodos")
    private Set<User> collaborants;

    public ToDo() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime  getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime  createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<User> getCollaborants() {
        return collaborants;
    }

    public void setCollaborants(Set<User> collaborants) {
        this.collaborants = collaborants;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "id=" + id +
                ", createdAt='" + createdAt + '\'' +
                ", title='" + title + '\'' +
                ", owner=" + owner +
                ", collaborants=" + collaborants +
                '}';
    }
}