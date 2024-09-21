package org.example.backend.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.api.modules.note.CreateNoteReponse;
import org.example.backend.api.modules.note.CreateNoteRequest;
import org.example.backend.entity.Note;
import org.example.backend.service.NoteService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/note")
public class NoteRestController {
    private final NoteService noteService;

    @GetMapping("/get_notes/{groupId}")
    public Flux<CreateNoteReponse> getAllNotes(@PathVariable long groupId) {
        log.info("note: get_notes/groupId");
        return noteService.getAllNotes(groupId)
                .map(CreateNoteReponse::of);
    }

    @PostMapping("/create")
    public Mono<CreateNoteReponse> createNote(@RequestBody CreateNoteRequest createNoteRequest) {
        log.info("note: create");
        return noteService.createNote(createNoteRequest)
                .map(CreateNoteReponse::of);
    }

    @PatchMapping("/update")
    public Mono<CreateNoteReponse> updateNote(@RequestBody CreateNoteRequest createNoteRequest) {
        log.info("note: update");
        return noteService.updateNote(createNoteRequest)
                .map(CreateNoteReponse::of);
    }

    @DeleteMapping("/delete/{noteId}")
    public Mono<Void> deleteNote(@PathVariable Long noteId) {
        log.info("note: delete/{noteId}");
        return noteService.deleteById(noteId);
    }
}
