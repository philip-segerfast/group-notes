package org.example.backend.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@RequiredArgsConstructor
public class Member {
        @Id
        private long memberId;
        private long userId;
        private long groupId;

        public Member(long userId, long groupId, long memberId) {
                this.userId = userId;
                this.groupId = groupId;
                this.memberId = memberId;
        }

        public Member(long userId, long groupId) {
                this.userId = userId;
                this.groupId = groupId;
        }

        public static Member of(long userId, long groupId) {
                return new Member(userId, groupId);
        }
}
