package com.example.groupnotes.api

import retrofit2.Retrofit

private val retrofit = Retrofit.Builder()
    .baseUrl("http://localhost:8080")
    .build()

var groupService: GroupService = retrofit.create(GroupService::class.java)
