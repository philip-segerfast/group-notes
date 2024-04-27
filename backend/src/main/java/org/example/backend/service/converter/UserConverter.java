package org.example.backend.service.converter;

import org.example.backend.api.modules.groups.user.CreateUserRequest;
import org.example.backend.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public User convert(CreateUserRequest createUserRequest) {
        return new User(createUserRequest.name());
    }
}
