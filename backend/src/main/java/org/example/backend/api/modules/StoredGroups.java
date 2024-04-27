package org.example.backend.api.modules;

import jakarta.persistence.Entity;
import lombok.Builder;
import org.example.backend.entity.Group;
import reactor.core.publisher.Flux;

import java.util.List;

@Builder
public record StoredGroups(List<Group> groups) {}
