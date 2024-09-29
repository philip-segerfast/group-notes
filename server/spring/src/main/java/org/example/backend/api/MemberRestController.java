package org.example.backend.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.api.modules.member.AddMemberRequest;
import org.example.backend.service.MemberService;
import org.example.backend.service.converter.MemberConverter;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberRestController {
    private final MemberService memberService;
    private final MemberConverter memberConverter;

    @PostMapping("/create")
    public Mono<Void> addMembers(@RequestBody AddMemberRequest memberRequest) {
        log.info("member: create");
        return memberConverter.convert(memberRequest.userIdList(), memberRequest.groupId())
                .flatMap(memberService::addAllMembers);
    }

    @DeleteMapping("/delete/{memberId}")
    public Mono<Void> deleteMember(@PathVariable Long memberId) {
        log.info("member: delete/{memberId}");
        return memberService.deleteById(memberId);
    }
}
