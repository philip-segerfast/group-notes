package org.example.backend.api.modules.groups.user;

import lombok.Builder;
import org.example.backend.entity.User;

import java.util.List;

@Builder
public record StoredUsersResponse(List<User> users) {
}
