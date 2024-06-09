package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.entity.User;
import org.example.backend.repository.UserRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public Mono<User> createUser(String name) {
        return userRepository.save(User.builder()
                        .name(name)
                .build());
    }

    public Mono<List<User>> getAllUsers() {
        return userRepository.findAll()
                .take(30)
                .collectList();
    }

    public Mono<User> getUserById(long id) {
        return userRepository.findById(id);
    }
}
