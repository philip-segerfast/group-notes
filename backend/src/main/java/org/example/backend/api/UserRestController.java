package org.example.backend.api;

import lombok.RequiredArgsConstructor;
import org.example.backend.entity.User;
import org.example.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController("/user")
public class UserRestController {
    private final UserService userService;

    @PostMapping("/create/{id}")
    public String createUser(@PathVariable int id) {
        return "test";
    }

    @PatchMapping("/update/{id}")
    public String updateUser(@PathVariable int id) {
        return "test";
    }

    @GetMapping("/getUsers")
    public List<User> getAllUsers() {
        return null;
    }
}
