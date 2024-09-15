package org.example.backend.api.modules.user;

import lombok.Builder;
import org.example.backend.entity.User;

import java.util.List;

@Builder
public record CreateUserResponse(long id,
                                 String name) {

    public static CreateUserResponse of(User user) {
        return CreateUserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    public static List<CreateUserResponse> from(List<User> users) {
        return users.stream()
                .map(user -> CreateUserResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .build())
                .toList();
    }
}
