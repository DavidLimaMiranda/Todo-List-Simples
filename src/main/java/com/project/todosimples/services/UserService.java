package com.project.todosimples.services;

import com.project.todosimples.models.User;
import com.project.todosimples.repositories.TaskRepository;
import com.project.todosimples.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    public User findById(Long id) {

        Optional<User> user = userRepository.findById(id);

        return user.orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
    }

    @Transactional
    public User createUser(User user) {

        user.setId(null);
        user = this.userRepository.save(user);
        taskRepository.saveAll(user.getTasks());

        return user;
    }

    @Transactional
    public User updateUser(User user) {

        User newUser = findById(user.getId());
        newUser.setPassword(user.getPassword());

        return userRepository.save(newUser);
    }

    public void delete(Long id) {

        findById(id);
        try {
            userRepository.deleteById(id);
        } catch (Exception e)
        {
            throw new RuntimeException("Não é possivel deletar pois há entidades relacionadas");
        }
    }
}
