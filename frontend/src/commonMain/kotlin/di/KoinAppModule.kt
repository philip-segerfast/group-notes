package di

import api.group.GroupRepository
import api.group.GroupService
import api.note.NoteRepository
import api.note.NoteService
import api.user.UserRepository
import api.user.UserService
import auth.Auth
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ui.screen.group.GroupScreenViewModel
import ui.screen.grouplist.GroupListScreenViewModel
import ui.screen.login.LoginScreenViewModel

val appModule = module {

    viewModelDependencies()

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
    // Normal ViewModel
    viewModelOf(::GroupListScreenViewModel)
    viewModelOf(::LoginScreenViewModel)

    // This ViewModel has a custom parameter
    viewModel { parameters -> GroupScreenViewModel(parameters.get(), get(), get()) }
}