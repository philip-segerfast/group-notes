package robphi.server.ktor.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.log
import io.ktor.server.response.ApplicationResponse
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject
import robphi.server.ktor.api.groupRoutes
import robphi.server.ktor.api.memberRoutes
import robphi.server.ktor.api.noteRoutes
import robphi.server.ktor.api.userRoutes
import robphi.server.ktor.model.UserModel
import robphi.server.ktor.repo.UserRepository

fun Application.configureRouting() {

    log.debug("Log debug!!")

    routing {

        get {
            call.respondText("Hello World!")
        }

        get("test1") {
            val user = UserModel(33, "Pelle")
//            call.respond(user)
        }

        val userRepository by inject<UserRepository>()
        userRoutes(userRepository)

        memberRoutes()

        noteRoutes()

        groupRoutes()
    }
}
