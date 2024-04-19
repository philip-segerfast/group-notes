package org.example.backend.api;

import lombok.RequiredArgsConstructor;
import org.example.backend.entity.Group;
import org.example.backend.service.GroupService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController("/group")
public class GroupRestController {
    private final GroupService groupService;

    @PostMapping("/create")
    public Group createGroup(@RequestBody Group group) {
        return group;
    }

    @DeleteMapping("/delete/{groupId}")
    public Mono<Void> deleteGroup(@PathVariable Long groupId) {
        return Mono.empty();
    }
}
