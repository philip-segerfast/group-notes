package com.example.groupnotes.di

import android.content.Context
import com.example.groupnotes.api.group.FakeGroupService
import com.example.groupnotes.api.note.NoteService
import com.example.groupnotes.api.group.GroupRepository
import com.example.groupnotes.api.group.GroupService
import com.example.groupnotes.api.note.NoteRepository
import com.example.groupnotes.api.user.FakeUserService
import com.example.groupnotes.api.user.UserRepository
import com.example.groupnotes.api.user.UserService
import com.example.groupnotes.auth.Auth
import com.example.groupnotes.auth.FakeAuth
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val FAKE = true

    @Singleton
    @Provides
    fun provideAuth(@ApplicationContext context: Context): Auth = FakeAuth(context)

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("http://127.0.0.1:8080/")
        .addConverterFactory(Json.asConverterFactory("application/json; charset=utf-8".toMediaType()))
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideGroupService(retrofit: Retrofit, userRepository: UserRepository): GroupService = when {
        FAKE -> FakeGroupService(userRepository)
        else -> retrofit.create<GroupService>()
    }

    @Singleton
    @Provides
    fun provideNoteService(retrofit: Retrofit): NoteService = when {
        // TODO - implement fake NoteService
        FAKE -> retrofit.create<NoteService>()
        else -> retrofit.create<NoteService>()
    }

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit): UserService = when {
        FAKE -> FakeUserService()
        else -> retrofit.create<UserService>()
    }

    @Singleton
    @Provides
    fun provideGroupRepository(
        groupService: GroupService,
        auth: Auth
    ): GroupRepository = GroupRepository(groupService, auth)

    @Singleton
    @Provides
    fun provideNoteRepository(
        noteService: NoteService,
        auth: Auth
    ): NoteRepository = NoteRepository(noteService, auth)

    @Singleton
    @Provides
    fun provideUserRepository(
        userService: UserService,
        auth: Auth
    ): UserRepository = UserRepository(userService, auth)

}