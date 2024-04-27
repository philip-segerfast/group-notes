package org.example.backend.api;

import lombok.RequiredArgsConstructor;
import org.example.backend.api.modules.groups.GroupRequest;
import org.example.backend.api.modules.groups.GroupResponse;
import org.example.backend.api.modules.groups.StoredGroups;
import org.example.backend.entity.UserGroup;
import org.example.backend.service.GroupService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/group")
public class GroupRestController {
    private final GroupService groupService;

    @GetMapping("/getById/{id}")
    public Mono<GroupResponse> getById(@PathVariable long id) {
        return Mono.just(groupService.getGroupByUserId(id));
    }

    @GetMapping("/get_all")
    public Mono<StoredGroups> getAll() {
        return Mono.just(groupService.getAllGroups());
    }

    @PostMapping("/create")
    public Mono<GroupResponse> createGroup(@RequestBody GroupRequest groupRequest) {
        return Mono.just(groupService.createGroup(groupRequest));
    }

    @DeleteMapping("/delete/{groupId}")
    public Mono<Void> deleteGroup(@PathVariable Long groupId) {
        return Mono.empty();
    }
}
