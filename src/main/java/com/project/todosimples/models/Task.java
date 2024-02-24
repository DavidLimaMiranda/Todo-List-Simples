package com.project.todosimples.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "task")
@Getter
@Setter
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Task task = (Task) obj;
        return Objects.equals(id, task.id) && Objects.equals(users, task.users) && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;

        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }
}
