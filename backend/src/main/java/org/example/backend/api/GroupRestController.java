package org.example.backend.api;

import lombok.RequiredArgsConstructor;
import org.example.backend.api.modules.HelloString;
import org.example.backend.api.modules.StoredGroups;
import org.example.backend.entity.Group;
import org.example.backend.service.GroupService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/group")
public class GroupRestController {
    private final GroupService groupService;

    @GetMapping("/get_all")
    public Flux<StoredGroups> getAll() {
        return groupService.getAllGroups();
    }

    @GetMapping("/get_strings")
    public Flux<HelloString> getStrings() {
        return groupService.getAllStrings();
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
