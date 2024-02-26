package com.project.todosimples.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "task")
@Getter
@Setter
@EqualsAndHashCode
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false, updatable = false)
    @NotNull
    private User users;

    @Column(name = "description", length = 255, nullable = false)
    @Size(min = 2, max = 255)
    @NotBlank
    private String description;

    public Task() {
    }

    public Task(long id, User users, String description) {
        this.id = id;
        this.users = users;
        this.description = description;
    }
}
