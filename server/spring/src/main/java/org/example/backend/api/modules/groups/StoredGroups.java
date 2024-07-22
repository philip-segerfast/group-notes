package org.example.backend.api.modules.groups;

import lombok.Builder;
import org.example.backend.entity.UserGroup;

import java.util.List;

@Builder
public record StoredGroups(List<UserGroup> userGroups) {}
