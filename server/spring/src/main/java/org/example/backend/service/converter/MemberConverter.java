package org.example.backend.service.converter;

import org.example.backend.entity.Member;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class MemberConverter {
    public Mono<List<Member>> convert(List<Long> userIdList, long groupId) {
        return Mono.just(userIdList.stream()
                .map(userId -> Member.of(userId, groupId))
                .toList());
    }
}
