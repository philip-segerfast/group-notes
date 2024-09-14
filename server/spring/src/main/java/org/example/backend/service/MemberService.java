package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.entity.Member;
import org.example.backend.repository.MemberRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;


    public Mono<Void> addAllMembers(List<Member> memberList) {
        return memberRepository
                .saveAll(memberList)
                .then();
    }

    public Mono<Void> deleteById(Long memberId) {
        //
        return memberRepository.deleteById(memberId);
    }
}
