package org.example.backend.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@RequiredArgsConstructor
public class Member {
        @Id
        private long userId;
        private long groupId;
        private long memberId;

        public Member(long userId, long groupId, long memberId) {
                this.userId = userId;
                this.groupId = groupId;
                this.memberId = memberId;
        }
}
