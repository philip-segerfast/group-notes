package org.example.backend.api;

import lombok.RequiredArgsConstructor;
import org.example.backend.entity.Note;
import org.example.backend.service.NoteService;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController("/note")
public class NoteRestController {
    private final NoteService noteService;

    @PostMapping("/create/{noteId}/{title}")
    public String createNote(@PathVariable Long noteId, @PathVariable String title) {
        return null;
    }

    @PatchMapping("/update")
    public String updateNote(@RequestBody Note note) {
        return null;
    }

    @DeleteMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable Long noteId) {
        return null;
    }
}
