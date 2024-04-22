package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.api.modules.StoredGroups;
import org.example.backend.entity.Group;
import org.example.backend.repository.GroupRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    public Mono<StoredGroups> getAllGroups() {
        return Mono.just(StoredGroups.builder()
                .groups(getMockedGroups())
                .build());
    }

    private static List<Group> getMockedGroups() {
        return List.of(new Group(1, "PhilleGroup", 2),
                new Group(2, "RobinGroup", 3),
                new Group(3, "FamiljGroup", 4));
    }
}
