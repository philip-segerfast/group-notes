package org.example.backend.repository;

import org.example.backend.entity.Note;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface NoteRepository extends ReactiveCrudRepository<Note, Long> {
    Flux<Note> findAllByGroupId(long groupId);

    @Query("UPDATE note SET byteblob = :byteblob WHERE group_id = :groupId")
    Mono<byte[]> updateBlob(long groupId, byte[] byteblob);

    @Query("""
        SELECT byteblob from note where group_id = :groupId
    """)
    Mono<byte[]> getByteBlobByGroupId(long groupId);
}
