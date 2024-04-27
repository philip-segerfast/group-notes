package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.api.modules.groups.GroupResponse;
import org.example.backend.api.modules.groups.StoredGroups;
import org.example.backend.api.modules.groups.user.CreateUserRequest;
import org.example.backend.api.modules.groups.user.GetUserResponse;
import org.example.backend.api.modules.groups.user.StoredUsersResponse;
import org.example.backend.entity.User;
import org.example.backend.repository.UserRepository;
import org.example.backend.service.converter.UserConverter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public void createUser(CreateUserRequest name) {
        userRepository.save(userConverter.convert(name));
    }

    public StoredUsersResponse getAllUsers() {
        Pageable pageable = PageRequest.of(0, 30); // Fetch first 150 groups
        return StoredUsersResponse.builder()
                .users(userRepository.findAll(pageable).getContent())
                .build();
    }

    public GetUserResponse getUserById(long id) {
        return GetUserResponse.builder()
                .user(userRepository.findById(id))
                .build();
    }
}
