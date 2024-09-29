package di

import api.group.FakeGroupService
import api.group.GroupService
import api.note.FakeNoteService
import api.note.NoteService
import api.user.FakeUserService
import api.user.UserService
import auth.Auth
import di.PlatformSpecificDependencies.providePlatformAuth
import di.PlatformSpecificDependencies.providePlatformGroupService
import di.PlatformSpecificDependencies.providePlatformNoteService
import di.PlatformSpecificDependencies.providePlatformUserService
import org.koin.core.module.Module
import org.koin.core.scope.Scope

private const val FAKE_IMPL = true

expect val platformModule: Module

object PlatformDependencies {
    fun Scope.provideUserService(): UserService = when(FAKE_IMPL) {
        true -> FakeUserService(get())
        false -> providePlatformUserService()
    }

    fun Scope.provideGroupService(): GroupService = when(FAKE_IMPL) {
        true -> FakeGroupService(get())
        false -> providePlatformGroupService()
    }

    fun Scope.provideNoteService(): NoteService = when(FAKE_IMPL) {
        true -> FakeNoteService()
        false -> providePlatformNoteService()
    }

    fun Scope.provideAuth(): Auth = when(FAKE_IMPL) {
        true -> error("No fake auth available yet.")
        false -> providePlatformAuth()
    }
}

expect object PlatformSpecificDependencies {

    fun Scope.providePlatformUserService(): UserService

    fun Scope.providePlatformGroupService(): GroupService

    fun Scope.providePlatformNoteService(): NoteService

    fun Scope.providePlatformAuth(): Auth

}
