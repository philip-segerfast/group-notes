package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.api.modules.groups.GroupRequest;
import org.example.backend.api.modules.groups.GroupResponse;
import org.example.backend.api.modules.groups.StoredGroups;
import org.example.backend.entity.UserGroup;
import org.example.backend.repository.GroupRepository;
import org.example.backend.service.converter.GroupConverter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupConverter groupConverter;

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

    public GroupResponse createGroup (GroupRequest groupRequest) {
        return GroupResponse.builder()
                .userGroup(groupRepository.save(groupConverter.convert(groupRequest)))
                .build();
    }
}
