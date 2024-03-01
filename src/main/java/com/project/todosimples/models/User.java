package com.project.todosimples.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.todosimples.models.enums.ProfileEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @CollectionTable(name = "user_profile")
    @Column(nullable = false)
    private Set<Integer> profiles = new HashSet<>();

    public Set<ProfileEnum> getProfiles() {

        return this.profiles.stream().map
                (p -> ProfileEnum.toEnum(p)).collect(Collectors.toSet());
    }

    public void addProfile(ProfileEnum profileEnum) {

        this.profiles.add(profileEnum.getCode());
    }
}
