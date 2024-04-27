package org.example.backend.api;

import lombok.RequiredArgsConstructor;
import org.example.backend.api.modules.groups.user.CreateUserRequest;
import org.example.backend.api.modules.groups.user.GetUserResponse;
import org.example.backend.api.modules.groups.user.StoredUsersResponse;
import org.example.backend.service.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserRestController {
    private final UserService userService;

    @PostMapping("/create/{name}")
    public void createUser(@PathVariable CreateUserRequest name) {
        userService.createUser(name);
    }

    @PatchMapping("/update/{id}")
    public String updateUser(@PathVariable int id) {
        return "test";
    }

    @GetMapping("/getUsers")
    public Mono<StoredUsersResponse> getAllUsers() {
        return Mono.just(userService.getAllUsers());
    }

    @GetMapping("/getUser/{id}")
    public Mono<GetUserResponse> getUser(@PathVariable long id) {
        return Mono.just(userService.getUserById(id));
    }
}
