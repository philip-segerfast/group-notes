package org.example.backend.api.modules.member;

import java.util.List;

public record AddMemberRequest(List<Long> userIdList,
                               long groupId) {
}
