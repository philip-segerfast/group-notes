package org.example.backend.api.modules;

import lombok.Builder;
import org.example.backend.entity.UserGroup;

@Builder
public record GroupResponse(UserGroup userGroup) {
}
