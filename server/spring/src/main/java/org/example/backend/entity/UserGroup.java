package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class UserGroup {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        private final String name;
        private final long userId;
}