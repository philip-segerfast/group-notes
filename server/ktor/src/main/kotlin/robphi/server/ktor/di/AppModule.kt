package robphi.server.ktor.di

import org.koin.dsl.module
import robphi.server.ktor.database.Database
import robphi.server.ktor.database.GroupQueries
import robphi.server.ktor.database.MemberQueries
import robphi.server.ktor.database.NoteQueries
import robphi.server.ktor.database.UserQueries
import robphi.server.ktor.repo.GroupRepository
import robphi.server.ktor.repo.GroupRepositoryImpl
import robphi.server.ktor.repo.NoteRepository
import robphi.server.ktor.repo.NoteRepositoryImpl
import robphi.server.ktor.repo.UserRepository
import robphi.server.ktor.repo.UserRepositoryImpl
import robphi.server.ktor.service.UserService
import robphi.server.ktor.service.UserServiceImpl

val appModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<UserService> { UserServiceImpl(get<UserRepository>()) }

    single<NoteRepository> { NoteRepositoryImpl(get<NoteQueries>()) }
    // single<NoteService> { NoteServiceImpl(get<NoteRepository>()) }

    single<GroupRepository> { GroupRepositoryImpl(get<NoteQueries>()) }

    single<UserQueries> { get<Database>().userQueries }
    single<GroupQueries> { get<Database>().groupQueries }
    single<MemberQueries> { get<Database>().memberQueries }
    single<NoteQueries> { get<Database>().noteQueries }
}
