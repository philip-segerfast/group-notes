package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.api.modules.groups.user.CreateUserRequest;
import org.example.backend.repository.UserRepository;
import org.example.backend.service.converter.UserConverter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public void createUser(CreateUserRequest name) {
        userRepository.save(userConverter.convert(name));
    }
}
