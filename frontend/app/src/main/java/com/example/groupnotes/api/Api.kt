package com.example.groupnotes.api

import kotlinx.serialization.Serializable
import reactor.core.publisher.Flux
import retrofit2.http.GET

@Serializable
data class HelloString(
    val contents: String
)

interface ApiService {
    @GET("group/get_all")
    fun getAllGroups(): Flux<StoredGroups>

    @GET("group/get_strings")
    fun getAllStrings(): Flux<HelloString>
}
