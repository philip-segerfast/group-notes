package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.api.modules.groups.GroupResponse;
import org.example.backend.api.modules.groups.StoredGroups;
import org.example.backend.repository.GroupRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    public StoredGroups getAllGroups() {
        Pageable pageable = PageRequest.of(0, 30); // Fetch first 150 groups
        return StoredGroups.builder()
                .userGroups(groupRepository.findAll(pageable).getContent())
                .build();
    }

    public GroupResponse getGroupByUserId(long id) {
        return GroupResponse.builder()
                .userGroup(groupRepository.findByUserId(id))
                .build();
    }
}
