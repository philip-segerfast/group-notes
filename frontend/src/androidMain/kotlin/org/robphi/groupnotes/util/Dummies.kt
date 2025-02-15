package org.robphi.groupnotes.util

import org.robphi.groupnotes.api.Group
import org.robphi.groupnotes.api.GroupId
import org.robphi.groupnotes.api.Note
import org.robphi.groupnotes.api.NoteId
import org.robphi.groupnotes.api.UserId
import java.time.Instant

fun Group.Companion.createDummy(
    name: String,
    groupId: GroupId = 0,
    creatorId: UserId = 0
): Group = Group(groupId, name, creatorId)

fun Note.Companion.createDummy(
    title: String,
    id: NoteId = 0,
    content: String = "",
    groupId: GroupId = 0,
    timestamp: String = Instant.now().toString()
): Note = Note(id, title, content, groupId, timestamp)
