package com.project.todosimples.services;

import com.project.todosimples.models.User;
import com.project.todosimples.repositories.UserRepository;
import com.project.todosimples.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<User> user = this.userRepository.findByUsername(userName);
        if(user.isEmpty())
            throw new UsernameNotFoundException("User not found: " + userName);

        return new UserSpringSecurity
               (user.get().getId(),
                user.get().getUserName(),
                user.get().getPassword(),
                user.get().getProfiles());
    }
}
