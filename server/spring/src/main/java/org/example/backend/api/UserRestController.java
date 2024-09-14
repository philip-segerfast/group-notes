package org.example.backend.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.api.modules.user.CreateUserRequest;
import org.example.backend.api.modules.user.GetUserResponse;
import org.example.backend.api.modules.user.StoredUsersResponse;
import org.example.backend.entity.User;
import org.example.backend.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@Slf4j
public class UserRestController {
    private final UserService userService;

    @GetMapping
    public String getUser() {
        return "User Information";
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal OAuth2User principal) {
        return "Hello, " + principal.getAttribute("name") + "!";
    }

    @PostMapping("/create/{userName}")
    public Mono<User> createUser(@PathVariable CreateUserRequest userName) {
        log.info("user: create/userName");
        return userService.createUser(userName.name());
    }

    @PatchMapping("/update/{id}")
    public String updateUser(@PathVariable int id) {
        return "test";
    }

    @GetMapping("/getUsers")
    public Mono<StoredUsersResponse> getAllUsers() {
        log.info("user: getUsers");
        return userService.getAllUsers()
                .map(StoredUsersResponse::new);
    }

    @GetMapping("/getUser/{id}")
    public Mono<GetUserResponse> getUser(@PathVariable long id) {
        log.info("user: getUsers/{id}");
        return userService.getUserById(id)
                .map(GetUserResponse::new);
    }
}
