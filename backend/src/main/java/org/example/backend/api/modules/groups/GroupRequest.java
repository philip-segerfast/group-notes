package org.example.backend.api.modules.groups;

import lombok.Builder;
import org.example.backend.entity.UserGroup;

@Builder
public record GroupRequest(UserGroup userGroup) {
}
