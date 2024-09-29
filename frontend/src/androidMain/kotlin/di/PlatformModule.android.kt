package di

import api.group.FakeGroupService
import api.group.GroupService
import api.note.NoteService
import api.note.RetrofitNoteService
import api.user.UserService
import auth.Auth
import auth.FakeAuth
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.core.module.Module
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

actual object PlatformSpecificDependencies {
    actual fun Scope.providePlatformUserService(): UserService =
        get<Retrofit>().create<UserService>()

    actual fun Scope.providePlatformGroupService(): GroupService =
        FakeGroupService(get())

    actual fun Scope.providePlatformNoteService(): NoteService =
        get<Retrofit>().create<RetrofitNoteService>()

    actual fun Scope.providePlatformAuth(): Auth =
        FakeAuth(get())
}

actual val platformModule: Module = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("http://172.20.10.3:8080/")
            .addConverterFactory(Json.asConverterFactory("application/json; charset=utf-8".toMediaType()))
            .addCallAdapterFactory(ResultCallAdapterFactory.create())
            .build()
    }
}
