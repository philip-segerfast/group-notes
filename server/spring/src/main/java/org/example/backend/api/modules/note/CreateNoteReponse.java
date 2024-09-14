package org.example.backend.api.modules.note;

import lombok.Builder;
import org.example.backend.entity.Note;

@Builder
public record CreateNoteReponse(long id,
                                String title,
                                String content,
                                String timestamp,
                                long groupId) {

    public static CreateNoteReponse of(Note note) {
        return CreateNoteReponse.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .timestamp(note.getTimestamp())
                .groupId(note.getGroupId())
                .build();
    }
}
