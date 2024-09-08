package org.example.backend.repository;

import org.example.backend.entity.UserGroup;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface GroupRepository extends ReactiveCrudRepository<UserGroup, Long> {
    Mono<UserGroup> findByUserId(long userId);
    Flux<UserGroup> findAllByUserId(long userId);
}
