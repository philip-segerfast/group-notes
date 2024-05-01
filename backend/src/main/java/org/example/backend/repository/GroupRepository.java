package org.example.backend.repository;

import org.example.backend.entity.UserGroup;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface GroupRepository extends ReactiveCrudRepository<UserGroup, Long> {
    Mono<UserGroup> findByUserId(long userId);
}
