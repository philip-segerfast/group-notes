package org.example.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.backend.api.modules.groups.GroupRequest;
import org.springframework.data.annotation.Id;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class UserGroup {
        @Id
        private long id;
        private String name;
        private long userId;

        public UserGroup(String name, long userId) {
                this.name = name;
                this.userId = userId;
        }

        public static UserGroup of(GroupRequest userGroup) {
                return new UserGroup(userGroup.name(),
                        userGroup.userId());
        }
}