package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.api.modules.GroupResponse;
import org.example.backend.api.modules.StoredGroups;
import org.example.backend.repository.GroupRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    public StoredGroups getAllGroups() {
        return StoredGroups.builder()
                .userGroups(groupRepository.findAll())
                .build();
    }

    public GroupResponse getGroupByUserId(long id) {
        return GroupResponse.builder()
                .userGroup(groupRepository.findByUserId(id))
                .build();
    }
}
