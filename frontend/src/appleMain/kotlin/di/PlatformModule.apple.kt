package di

import api.group.GroupService
import api.note.NoteService
import api.user.UserService
import auth.Auth
import org.koin.core.module.Module
import org.koin.core.scope.Scope
import org.koin.dsl.module

actual object PlatformSpecificDependencies {
    actual fun Scope.providePlatformUserService(): UserService = TODO("UserService not implemented on iOS")

    actual fun Scope.providePlatformGroupService(): GroupService = TODO("GroupService not implemented on iOS")

    actual fun Scope.providePlatformNoteService(): NoteService = TODO("NoteService not implemented on iOS")

    actual fun Scope.providePlatformAuth(): Auth {
        TODO("Auth not implemented on iOS")
    }
}

actual val platformModule: Module = module {

}
