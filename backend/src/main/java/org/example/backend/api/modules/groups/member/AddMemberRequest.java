package org.example.backend.api.modules.groups.member;

import org.example.backend.entity.Member;

import java.util.List;

public record AddMemberRequest(List<Member> members) {
}
