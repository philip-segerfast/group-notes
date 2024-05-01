package org.example.backend.api;

import lombok.RequiredArgsConstructor;
import org.example.backend.api.modules.groups.GroupRequest;
import org.example.backend.api.modules.groups.GroupResponse;
import org.example.backend.api.modules.groups.StoredGroups;
import org.example.backend.entity.UserGroup;
import org.example.backend.service.GroupService;
import org.example.backend.service.converter.GroupConverter;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/group")
public class GroupRestController {
    private final GroupService groupService;
    private final GroupConverter groupConverter;

    @GetMapping("/getById/{id}")
    public Mono<GroupResponse> getById(@PathVariable long id) {
        return groupService.getGroupByUserId(id)
                .map(GroupResponse::new);
    }

    @GetMapping("/get_all")
    public Mono<StoredGroups> getAll() {
        return groupService.getAllGroups()
                .collectList()
                .map(StoredGroups::new);
    }

    @PostMapping("/create")
    public Mono<GroupResponse> createGroup(@RequestBody GroupRequest groupRequest) {
        return groupService.createGroup(groupConverter.convert(groupRequest))
                .map(GroupResponse::new);
    }

    @DeleteMapping("/delete/{groupId}")
    public Mono<Void> deleteGroup(@PathVariable Long groupId) {
        return Mono.empty();
    }
}
