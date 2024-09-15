package org.example.backend.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.api.modules.groups.GroupRequest;
import org.example.backend.api.modules.groups.GroupResponse;
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

    @GetMapping("/getById/{groupId}")
    public Mono<GroupResponse> getById(@PathVariable long groupId) {
        log.info("group: getById");
        return groupService.getGroupByGroupId(groupId)
                .map(GroupResponse::of);
    }

    @GetMapping("/get_all/{userId}")
    public Flux<GroupResponse> getAll(@PathVariable long userId) {
        log.info("group: getAll");
        return groupService.getAllGroups(userId)
                .map(GroupResponse::of);
    }

    @PostMapping("/create")
    public Mono<GroupResponse> createGroup(@RequestBody GroupRequest groupRequest) {
        log.info("group: create");
        return groupService.createGroup(groupRequest)
                .map(GroupResponse::of);
    }

    @DeleteMapping("/delete/{groupId}")
    public Mono<Void> deleteGroup(@PathVariable Long groupId) {
        log.info("group: delete/{groupId}");
        return groupService.deleteGroup(groupId);
    }
}
