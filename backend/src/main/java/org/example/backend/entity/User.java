package org.example.backend.entity;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@RequiredArgsConstructor
public class User {
        @Id
        private long id;
        private String name;

        public User(String name) {
                this.name = name;
        }
}
