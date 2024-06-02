package org.example.backend.repository;

import org.example.backend.entity.Member;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.List;

public interface MemberRepository extends ReactiveCrudRepository<Member, Long> {
    Mono<Void> addAll(List<Member> members);
}
