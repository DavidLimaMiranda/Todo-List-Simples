package com.project.todosimples.services;

import com.project.todosimples.models.Task;
import com.project.todosimples.models.User;
import com.project.todosimples.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findById(Long id) {

        Optional<Task> task = taskRepository.findById(id);

        return task.orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
    }

    @Transactional
    public Task createTask(Task task) {

        User user = userService.findById(task.getUsers().getId());
        task.setId(null);
        task.setUsers(user);

        task = taskRepository.save(task);

        return task;
    }

    @Transactional
    public Task updateTask(Task task) {

        Task newTask = findById(task.getId());
        newTask.setDescription(task.getDescription());

        return taskRepository.save(newTask);
    }

    public void deleteTask(Long id) {

        findById(id);

        try {
            taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possivel deletar pois há entidades relacionadas");
        }
    }
}
