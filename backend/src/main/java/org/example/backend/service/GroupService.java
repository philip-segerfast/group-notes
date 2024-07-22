package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.entity.UserGroup;
import org.example.backend.repository.GroupRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GroupService {
    //lol
    private final GroupRepository groupRepository;

    public Mono<List<UserGroup>> getAllGroups() {
        return groupRepository.findAll()
                .take(100)
                .collectList();
    }

    public Mono<UserGroup> getGroupByUserId(long id) {
        return groupRepository.findByUserId(id);
    }

    public Mono<UserGroup> createGroup (UserGroup userGroup) {
        return groupRepository.save(userGroup);
    }

    public Mono<Void> deleteGroup(Long groupId) {
        return groupRepository.deleteById(groupId);
    }
}
