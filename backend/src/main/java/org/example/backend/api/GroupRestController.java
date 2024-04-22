package org.example.backend.api;

import lombok.RequiredArgsConstructor;
import org.example.backend.entity.Group;
import org.example.backend.service.GroupService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@RestController("/group")
public class GroupRestController {
    private final GroupService groupService;

    @GetMapping("/get_all")
    public Mono<List<Group>> getAll() {
        return groupService.getAllGroups();
    }

    @PostMapping("/create")
    public Group createGroup(@RequestBody Group group) {
        return group;
    }

    @DeleteMapping("/delete/{groupId}")
    public Mono<Void> deleteGroup(@PathVariable Long groupId) {
        return Mono.empty();
    }
}
