package org.example.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public record Member(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        long memberId,
        long userId,
        long groupId) {
}
