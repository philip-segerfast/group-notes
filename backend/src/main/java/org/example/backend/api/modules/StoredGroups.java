package org.example.backend.api.modules;

import lombok.Builder;
import org.example.backend.entity.Group;

import java.util.List;

@Builder
public record StoredGroups(List<Group> groups) {
}
