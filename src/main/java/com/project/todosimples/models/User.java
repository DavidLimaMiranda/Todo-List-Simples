package com.project.todosimples.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode
public class User {

    public interface CreateUser {
    }
    public interface UpdateUser {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id", unique = true)
    private Long id;

    @Column(name = "users_name", length = 80, nullable = false, unique = true)
    @Size(min = 2, groups = CreateUser.class)
    @NotBlank(groups = CreateUser.class)
    private String userName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "passwords", length = 60, nullable = false)
    @Size(min = 8, groups = {CreateUser.class, UpdateUser.class})
    @NotBlank(groups = {CreateUser.class, UpdateUser.class})
    private String password;

    @OneToMany(mappedBy = "users")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    private List<Task> tasks = new ArrayList<Task>();

    public User() {
    }

    public User(Long id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }
}
