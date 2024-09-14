package org.example.backend.api.modules.note;

import lombok.Builder;

@Builder
public record CreateNoteRequest(
        String title,
        String content,
        long groupId) {
}
