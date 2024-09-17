package org.example.backend.api.modules.groups;

import lombok.Builder;
import org.example.backend.entity.UserGroup;

@Builder
public record GroupResponse(long id,
                            String name,
                            long userId) {

    public static GroupResponse of(UserGroup userGroup) {
        return GroupResponse.builder()
                .id(userGroup.getId())
                .name(userGroup.getName())
                .userId(userGroup.getUserId())
                .build();
    }
}
