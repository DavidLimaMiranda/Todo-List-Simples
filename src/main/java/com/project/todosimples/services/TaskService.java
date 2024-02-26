package com.project.todosimples.services;

import com.project.todosimples.models.Task;
import com.project.todosimples.models.User;
import com.project.todosimples.repositories.TaskRepository;
import com.project.todosimples.services.exceptions.DataBindingViolationException;
import com.project.todosimples.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findById(Long id) {

        Optional<Task> task = taskRepository.findById(id);

        return task.orElseThrow(() -> new ObjectNotFoundException(
                "Tarefa não encontrada!, Id: " + id +", Tipo: " + Task.class.getName()));
    }

    public List<Task> findAllByUserId(Long userId) {

        return this.taskRepository.findByUsers_Id(userId);
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
            throw new DataBindingViolationException("Não é possivel deletar pois há entidades relacionadas");
        }
    }
}
