package org.example.backend.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.api.modules.user.CreateUserRequest;
import org.example.backend.api.modules.user.CreateUserResponse;
import org.example.backend.service.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserRestController {
    private final UserService userService;

    @PostMapping("/create/{userName}")
    public Mono<CreateUserResponse> createUser(@PathVariable CreateUserRequest userName) {
        log.info("user: create/userName");
        return userService.createUser(userName.name())
                .map(CreateUserResponse::of);
    }

    @PatchMapping("/update/{id}")
    public String updateUser(@PathVariable int id) {
        return "test";
    }

    @GetMapping("/getUsers")
    public Mono<List<CreateUserResponse>> getAllUsers() {
        log.info("user: getUsers");
        return userService.getAllUsers()
                .map(CreateUserResponse::from);
    }

    @GetMapping("/getUser/{id}")
    public Mono<CreateUserResponse> getUser(@PathVariable long id) {
        log.info("user: getUsers/{id}");
        return userService.getUserById(id)
                .map(CreateUserResponse::of);
    }
}
