package org.example.backend.service.converter;

import org.example.backend.entity.Member;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberConverter {
    public List<Member> convert(List<Long> userIdList, long groupId) {
        return userIdList.stream()
                .map(userId -> Member.of(userId, groupId))
                .toList();
    }
}
