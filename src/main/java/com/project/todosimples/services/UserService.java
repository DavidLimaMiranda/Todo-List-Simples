package com.project.todosimples.services;

import com.project.todosimples.models.User;
import com.project.todosimples.models.enums.ProfileEnum;
import com.project.todosimples.repositories.TaskRepository;
import com.project.todosimples.repositories.UserRepository;
import com.project.todosimples.services.exceptions.DataBindingViolationException;
import com.project.todosimples.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    public User findById(Long id) {

        Optional<User> user = userRepository.findById(id);

        return user.orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));
    }

    @Transactional
    public User createUser(User user) {

        user.setId(null);
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        user.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));

        user = this.userRepository.save(user);
        taskRepository.saveAll(user.getTasks());

        return user;
    }

    @Transactional
    public User updateUser(User user) {

        User newUser = findById(user.getId());
        newUser.setPassword(user.getPassword());
        newUser.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

        return userRepository.save(newUser);
    }

    public void delete(Long id) {

        findById(id);
        try {
            userRepository.deleteById(id);
        } catch (Exception e)
        {
            throw new DataBindingViolationException("Não é possivel deletar pois há entidades relacionadas");
        }
    }
}
