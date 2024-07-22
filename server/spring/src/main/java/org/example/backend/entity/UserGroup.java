package org.example.backend.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@RequiredArgsConstructor
public class UserGroup {
        @Id
        private long id;
        private String name;
        private long userId;

        public UserGroup(String name, long userId) {
                this.name = name;
                this.userId = userId;
        }
}