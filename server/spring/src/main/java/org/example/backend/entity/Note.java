package org.example.backend.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.backend.api.modules.note.CreateNoteRequest;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Data
@RequiredArgsConstructor
public class Note {
        @Id
        private long id;
        private String title;
        private String content;
        private String timestamp;
        private long groupId;

        public Note(long id, String title, String content, String timestamp, long groupId) {
                this.id = id;
                this.title = title;
                this.content = content;
                this.timestamp = timestamp;
                this.groupId = groupId;
        }
        public Note(String title, String content, String timestamp, long groupId) {
                this.title = title;
                this.content = content;
                this.timestamp = timestamp;
                this.groupId = groupId;
        }

        public static Note of(CreateNoteRequest noteRequest) {
                return new Note(noteRequest.title(),
                        noteRequest.content(),
                        Instant.now().toString(),
                        noteRequest.groupId());
        }
}
