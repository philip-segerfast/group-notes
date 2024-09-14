package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.api.modules.groups.GroupRequest;
import org.example.backend.entity.UserGroup;
import org.example.backend.repository.GroupRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    public Flux<UserGroup> getAllGroups(long userId) {
        return groupRepository.findAllByUserId(userId);
    }

    public Mono<UserGroup> getGroupByGroupId(long groupId) {
        return groupRepository.findById(groupId);
    }

    public Mono<UserGroup> createGroup(GroupRequest userGroup) {
        return groupRepository.save(UserGroup.of(userGroup));
    }

    public Mono<Void> deleteGroup(Long groupId) {

        return groupRepository.deleteById(groupId);
    }
}
