package com.project.todosimples.repositories;

import com.project.todosimples.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
