package org.example.backend.repository;

import org.example.backend.entity.Member;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.List;

public interface MemberRepository extends ReactiveCrudRepository<Member, Long> {
    void addAll(List<Member> members);
}
