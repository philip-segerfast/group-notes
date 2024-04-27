package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.api.modules.GroupResponse;
import org.example.backend.api.modules.HelloString;
import org.example.backend.api.modules.StoredGroups;
import org.example.backend.repository.GroupRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    public Mono<StoredGroups> getAllGroups() {
        return Mono.just(StoredGroups.builder()
                .userGroups(groupRepository.findAll())
                .build());
    }

    public Flux<HelloString> getAllStrings() {
        return Flux.just(new HelloString("Hello there"));
    }

    public GroupResponse getGroupByUserId(long id) {
        return GroupResponse.builder()
                .userGroup(groupRepository.findByUserId(id))
                .build();
    }
}
