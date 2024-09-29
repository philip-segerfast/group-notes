package org.example.backend.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.automerge.Document;
import org.automerge.ObjectId;
import org.example.backend.repository.NoteRepository;
import org.example.backend.service.IAutomerge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/collab")
public class CollaborativeController {
    private final IAutomerge iAutomerge;
    private final NoteRepository noteRepository;

    @GetMapping("/create")
    public Mono<Void> createDocument() {
        //TODO: save to db.
        Pair<Document, ObjectId> document = iAutomerge.createDocument("test", "hello");
        return Mono.empty();
    }

    @GetMapping
    public Mono<Optional<String>> findTextDifference(@RequestParam String value) {
        Pair<Document, ObjectId> document = iAutomerge.createDocument("test", "hello");
        return Mono.just(iAutomerge.findTextDiff(document, value));
    }

    @GetMapping("/append_text")
    public Mono<Optional<String>> appendText(long groupId, String value) {
        Pair<Document, ObjectId> pair = iAutomerge.createDocument("test", "hello");
        return iAutomerge.appendDocumentText(groupId, pair.getLeft(), pair.getRight(), value)
                .map(document -> document.text(pair.getRight()));
    }

    @GetMapping("/test")
    public Mono<Optional<?>> text() {
        return noteRepository.getByteBlobByGroupId(13)
                .doOnNext(bytes -> System.out.println("Retrieved bytes: " + (bytes != null ? bytes.length : "null")))
                .flatMap(byteArray -> {
                    if (byteArray == null || byteArray.length == 0) {
                        return Mono.just(Optional.empty());
                    }
                    Document document = Document.load(byteArray);
                    return Mono.just(document.text(ObjectId.ROOT));
                })
                .onErrorResume(e -> {
                    System.err.println("Error occurred: " + e.getMessage()); // Log the error
                    return Mono.just(Optional.of("odd as fuck")); // Return your custom message
                });
    }
}
