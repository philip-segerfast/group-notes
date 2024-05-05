package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.api.modules.groups.member.AddMemberRequest;
import org.example.backend.repository.MemberRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Mono<Void> addAllMembers(AddMemberRequest memberRequest) {
        return memberRepository.addAll(memberRequest.members());
    }

    public Mono<Void> deleteById(Long memberId) {
        return memberRepository.deleteById(memberId);
    }
}
