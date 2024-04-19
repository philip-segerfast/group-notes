package org.example.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public record Note(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        long id,
        String title,
        String content,
        long groupId) {
}
