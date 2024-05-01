package org.example.backend.api;

import lombok.RequiredArgsConstructor;
import org.example.backend.api.modules.groups.member.AddMemberRequest;
import org.example.backend.service.MemberService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberRestController {
    private final MemberService memberService;

    @PatchMapping("/addMembers")
    public void addMembers(@RequestBody AddMemberRequest memberRequest) {
        memberService.addAllMembers(memberRequest);
    }
}
