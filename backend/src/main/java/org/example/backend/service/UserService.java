package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.entity.User;
import org.example.backend.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void createUser(String name) {
        userRepository.save(new User(name));
    }
}
