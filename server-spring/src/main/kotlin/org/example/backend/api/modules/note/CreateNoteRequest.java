package org.example.backend.api.modules.note;

import lombok.Builder;
import org.example.backend.entity.Note;

@Builder
public record CreateNoteRequest(Note note) {
}
