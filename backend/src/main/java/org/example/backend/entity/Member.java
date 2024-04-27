package org.example.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
public class Member {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long memberId;
        private long userId;
        private long groupId;
}
