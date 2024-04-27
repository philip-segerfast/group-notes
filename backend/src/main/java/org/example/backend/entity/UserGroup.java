package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
public class UserGroup {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        private String name;
        private long userId;

        public UserGroup(String name, long userId) {
                this.name = name;
                this.userId = userId;
        }
}
