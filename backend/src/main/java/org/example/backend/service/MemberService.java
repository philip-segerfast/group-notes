package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.api.modules.groups.member.AddMemberRequest;
import org.example.backend.repository.MemberRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void addAllMembers(AddMemberRequest memberRequest) {
        memberRepository.addAll(memberRequest.members());
    }
}
