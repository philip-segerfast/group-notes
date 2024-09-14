package org.robphi.groupnotes.di

import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.robphi.groupnotes.api.group.FakeGroupService
import org.robphi.groupnotes.api.group.GroupRepository
import org.robphi.groupnotes.api.group.GroupService
import org.robphi.groupnotes.api.note.FakeNoteService
import org.robphi.groupnotes.api.note.NoteRepository
import org.robphi.groupnotes.api.note.NoteService
import org.robphi.groupnotes.api.user.FakeUserService
import org.robphi.groupnotes.api.user.UserRepository
import org.robphi.groupnotes.api.user.UserService
import org.robphi.groupnotes.auth.Auth
import org.robphi.groupnotes.auth.FakeAuth
import org.robphi.groupnotes.ui.screen.grouplist.GroupListScreenViewModel
import org.robphi.groupnotes.ui.screen.group.GroupScreenViewModel
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

private const val FAKE = false

val appModule = module {

    viewModelDependencies()

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("http://172.20.10.3:8080/")
            .addConverterFactory(Json.asConverterFactory("application/json; charset=utf-8".toMediaType()))
            .addCallAdapterFactory(ResultCallAdapterFactory.create())
            .build()
    }

    single<GroupService> {
        when {
            FAKE -> FakeGroupService(get<UserRepository>())
            else -> get<Retrofit>().create<GroupService>()
        }
    }

    single<NoteService> {
        val retrofit = get<Retrofit>()
        when {
            // TODO - implement fake NoteService
            FAKE -> FakeNoteService()
            else -> retrofit.create<NoteService>()
        }
    }

    single<UserService> {
        when {
            FAKE -> FakeUserService()
            else -> get<Retrofit>().create<UserService>()
        }
    }

    single<GroupRepository> {
        GroupRepository(get<GroupService>(), get<Auth>())
    }

    single<NoteRepository> {
        NoteRepository(get<NoteService>(), get<Auth>())
    }

    single<UserRepository> {
        UserRepository(get<UserService>(), get<Auth>())
    }
}

private fun Module.viewModelDependencies() {
    singleOf(::FakeAuth) { bind<Auth>() }
    // Normal ViewModel
    viewModelOf(::GroupListScreenViewModel)
    // This ViewModel has a custom parameter
    viewModel { parameters -> GroupScreenViewModel(parameters.get(), get(), get()) }
}