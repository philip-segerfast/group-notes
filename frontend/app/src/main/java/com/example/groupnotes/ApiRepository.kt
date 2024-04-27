package com.example.groupnotes

import com.example.groupnotes.api.GroupService
import com.jakewharton.retrofit2.adapter.reactor.ReactorCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class ApiRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.36.241:8080/")
        .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json; charset=utf-8")))
        .addCallAdapterFactory(ReactorCallAdapterFactory.create())
        .build()

    val apiService: GroupService = retrofit.create(GroupService::class.java)

}