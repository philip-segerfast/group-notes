package org.example.backend.service;

import org.apache.commons.lang3.tuple.Pair;
import org.automerge.Document;
import org.automerge.ObjectId;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface IAutomerge {
    Pair<Document, ObjectId> createDocument(String key, String value);
    Mono<Document> appendDocumentText(long userId, Document document, ObjectId id, String value);
    Optional<String> findTextDiff(Pair<Document, ObjectId> pair, String value);
}
