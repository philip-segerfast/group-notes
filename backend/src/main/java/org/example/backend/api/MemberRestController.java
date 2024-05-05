package org.example.backend.api;

import lombok.RequiredArgsConstructor;
import org.example.backend.api.modules.groups.member.AddMemberRequest;
import org.example.backend.service.MemberService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberRestController {
    private final MemberService memberService;

    @PatchMapping("/create")
    public Mono<Void> addMembers(@RequestBody AddMemberRequest memberRequest) {
        return memberService.addAllMembers(memberRequest);
    }

    @DeleteMapping("/delete/{memberId}")
    public Mono<Void> deleteMember(@PathVariable Long memberId) {
        return memberService.deleteById(memberId);
    }
}
