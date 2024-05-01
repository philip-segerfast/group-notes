package com.example.groupnotes.api

import retrofit2.Retrofit
import retrofit2.create

private val retrofit = Retrofit.Builder()
    .baseUrl("http://localhost:8080")
    .build()

var groupService: GroupService = retrofit.create()
val userService: UserService = retrofit.create()
val noteService: NoteService = retrofit.create()
