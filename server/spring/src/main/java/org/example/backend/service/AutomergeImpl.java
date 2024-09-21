package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.automerge.*;
import org.example.backend.repository.NoteRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AutomergeImpl implements IAutomerge {
    private final NoteRepository noteRepository;

    @Override
    public Optional<String> findTextDiff(Pair<Document, ObjectId> pair, String value) {
        var doc = pair.getLeft();
        var id = pair.getRight();

        var firstHeads = doc.getHeads();
        try (Transaction tx = doc.startTransaction()) {
            tx.spliceText(id, 0, 0, value);
            tx.commit();
        }

        var secondHead = doc.getHeads();
        List<Patch> result = doc.diff(firstHeads, secondHead);

        return !result.isEmpty() ? result.stream()
                .map(Patch::getAction)
                .map(s -> ((PatchAction.SpliceText) s).getText())
                .findFirst() : Optional.empty();
    }

    @Override
    public Pair<Document, ObjectId> createDocument(String key, String value) {
        Document doc = new Document();
        Transaction tx = doc.startTransaction();

        ObjectId text = tx.set(ObjectId.ROOT, key, ObjectType.TEXT);
        tx.spliceText(text, 0, 0, value);
        tx.commit();

        return Pair.of(doc, text);
    }

    @Override
    public Mono<Document> appendDocumentText(long groupId, Document document, ObjectId id, String value) {
        try (Transaction tx = document.startTransaction()) {
            tx.spliceText(id, 0, 0, value);
            tx.commit();
        }

        return noteRepository.updateBlob(groupId, document.save())
                .map(Document::load);
    }
}
