package util

import kotlinx.datetime.Clock
import model.Group
import model.GroupId
import model.Note
import model.NoteId
import model.UserId

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
    timestamp: String = Clock.System.now().toString()
): Note = Note(id, title, content, groupId, timestamp)
