package org.example.backend.api.modules.groups;

import lombok.Builder;

@Builder
public record GroupRequest(String name,
                           long userId) {
}
