package org.example.backend.api;

import lombok.RequiredArgsConstructor;
import org.example.backend.api.modules.note.CreateNoteReponse;
import org.example.backend.api.modules.note.CreateNoteRequest;
import org.example.backend.entity.Note;
import org.example.backend.service.NoteService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/note")
public class NoteRestController {
    private final NoteService noteService;

    @PostMapping("/create")
    public Mono<CreateNoteReponse> createNote(@RequestBody CreateNoteRequest createNoteRequest) {
        return noteService.createNote(createNoteRequest.note())
                .map(CreateNoteReponse::new);
    }

    @PatchMapping("/update")
    public String updateNote(@RequestBody CreateNoteRequest createNoteRequest) {
        return null;
    }

    @DeleteMapping("/delete/{noteId}")
    public Mono<Void> deleteNote(@PathVariable Long noteId) {
        return noteService.deleteById(noteId);
    }
}
