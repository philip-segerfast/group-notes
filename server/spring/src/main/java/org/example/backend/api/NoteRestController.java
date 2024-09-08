package org.example.backend.api;

import lombok.RequiredArgsConstructor;
import org.example.backend.api.modules.note.CreateNoteReponse;
import org.example.backend.api.modules.note.CreateNoteRequest;
import org.example.backend.entity.Note;
import org.example.backend.service.NoteService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/note")
public class NoteRestController {
    private final NoteService noteService;

    @GetMapping("/get_notes/{groupId}")
    public Flux<Note> getAllNotes(@PathVariable long groupId) {
        return noteService.getAllNotes(groupId);
    }

    @PostMapping("/create")
    public Mono<CreateNoteReponse> createNote(@RequestBody CreateNoteRequest createNoteRequest) {
        return noteService.createNote(createNoteRequest)
                .map(CreateNoteReponse::new);
    }

    @PatchMapping("/update")
    public Mono<CreateNoteReponse> updateNote(@RequestBody CreateNoteRequest createNoteRequest) {
        return noteService.updateNote(createNoteRequest)
                .map(CreateNoteReponse::new);
    }

    @DeleteMapping("/delete/{noteId}")
    public Mono<Void> deleteNote(@PathVariable Long noteId) {
        return noteService.deleteById(noteId);
    }
}
