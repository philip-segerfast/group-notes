package di

import api.group.GroupService
import api.note.NoteService
import api.user.UserService
import auth.Auth
import org.koin.core.module.Module
import org.koin.core.scope.Scope
import org.koin.dsl.module

actual object PlatformSpecificDependencies {
    actual fun Scope.providePlatformUserService(): UserService = TODO("UserService not yet implemented on Desktop")

    actual fun Scope.providePlatformGroupService(): GroupService = TODO("GroupService not yet implemented on Desktop")

    actual fun Scope.providePlatformNoteService(): NoteService = TODO("NoteService not yet implemented on Desktop")

    actual fun Scope.providePlatformAuth(): Auth = TODO("Auth not yet implemented on Desktop")

}

actual val platformModule: Module = module {

}