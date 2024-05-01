package org.example.backend.repository;

import org.example.backend.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    User findById(long id);
}
