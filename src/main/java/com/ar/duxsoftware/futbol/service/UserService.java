package com.ar.duxsoftware.futbol.service;

import com.ar.duxsoftware.futbol.entity.User;
import com.ar.duxsoftware.futbol.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {
        final Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        return user.get();
    }

    public Optional<User> findByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    public User save(final User user) {
        return userRepository.save(user);
    }
}
