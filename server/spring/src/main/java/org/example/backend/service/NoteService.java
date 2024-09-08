package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.api.modules.note.CreateNoteRequest;
import org.example.backend.entity.Note;
import org.example.backend.repository.NoteRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    public Mono<Note> createNote(CreateNoteRequest noteRequest) {
        return noteRepository.save(Note.of(noteRequest));
    }

    public Mono<Void> deleteById(Long noteId) {
        return noteRepository.deleteById(noteId);
    }

    public Mono<Note> updateNote(CreateNoteRequest noteRequest) {
        return noteRepository.save(Note.of(noteRequest));
    }

    public Flux<Note> getAllNotes(long groupId){
        return noteRepository.findAllByGroupId(groupId);
    }
}
