package org.example.backend.repository;

import org.example.backend.entity.Note;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends ReactiveCrudRepository<Note, Long> {
}
