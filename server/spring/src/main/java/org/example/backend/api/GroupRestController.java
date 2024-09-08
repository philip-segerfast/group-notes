package org.example.backend.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.api.modules.groups.GroupRequest;
import org.example.backend.api.modules.groups.GroupResponse;
import org.example.backend.api.modules.groups.StoredGroups;
import org.example.backend.service.GroupService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/group")
@Slf4j
public class GroupRestController {
    private final GroupService groupService;

    @GetMapping("/getById/{id}")
    public Mono<GroupResponse> getById(@PathVariable long id) {
        return groupService.getGroupByUserId(id)
                .map(GroupResponse::new);
    }

    @GetMapping("/get_all/{userId}")
    public Flux<StoredGroups> getAll(@PathVariable long userId) {
        return groupService.getAllGroups(userId)
                .map(StoredGroups::new);
    }

    @PostMapping("/create")
    public Mono<GroupResponse> createGroup(@RequestBody GroupRequest groupRequest) {
        log.info("Create group was called with {}", groupRequest);
        return groupService.createGroup(groupRequest)
                .map(GroupResponse::new);
    }

    @DeleteMapping("/delete/{groupId}")
    public Mono<Void> deleteGroup(@PathVariable Long groupId) {
        return groupService.deleteGroup(groupId);
    }
}
