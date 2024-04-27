package org.example.backend.api.modules.groups.user;

import lombok.Builder;
import org.example.backend.entity.User;

@Builder
public record GetUserResponse(User user) {
}
