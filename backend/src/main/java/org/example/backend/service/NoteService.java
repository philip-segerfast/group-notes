package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.entity.Note;
import org.example.backend.repository.NoteRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    public Mono<Note> createNote(Note note) {
        return noteRepository.save(note);
    }

    public Mono<Void> deleteById(Long noteId) {
        return noteRepository.deleteById(noteId);
    }
}
