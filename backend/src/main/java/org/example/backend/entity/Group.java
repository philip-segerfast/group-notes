package org.example.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;

@Entity
@Builder
public record Group(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        long id,
        String name,
        int userId) {
}
