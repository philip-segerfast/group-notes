package org.example.backend.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@RequiredArgsConstructor
public class Note {
        @Id
        private long id;
        private String title;
        private String content;
        private long groupId;

        public Note(String title, String content, long groupId) {
                this.title = title;
                this.content = content;
                this.groupId = groupId;
        }
}
