package com.project.todosimples.repositories;

import com.project.todosimples.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser_id(Long id);
}
