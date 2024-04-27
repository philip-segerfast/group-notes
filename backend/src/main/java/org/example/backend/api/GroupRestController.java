package org.example.backend.api;

import lombok.RequiredArgsConstructor;
import org.example.backend.api.modules.GroupResponse;
import org.example.backend.api.modules.HelloString;
import org.example.backend.api.modules.StoredGroups;
import org.example.backend.entity.UserGroup;
import org.example.backend.service.GroupService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
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
    public Flux<StoredGroups> getAll() {
        return groupService.getAllGroups();
    }

    @GetMapping("/get_strings")
    public Flux<HelloString> getStrings() {
        return groupService.getAllStrings();
    }

    @PostMapping("/create")
    public UserGroup createGroup(@RequestBody UserGroup userGroup) {
        return userGroup;
    }

    @DeleteMapping("/delete/{groupId}")
    public Mono<Void> deleteGroup(@PathVariable Long groupId) {
        return Mono.empty();
    }
}
