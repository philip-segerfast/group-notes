package org.example.backend.api;

import lombok.RequiredArgsConstructor;
import org.example.backend.service.MemberService;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/member")
public class MemberRestController {
    private final MemberService memberService;

    //TOOD: TBD
}
