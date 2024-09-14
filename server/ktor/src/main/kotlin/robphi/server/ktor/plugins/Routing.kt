package robphi.server.ktor.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sse.*
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



fun ServerSSESession.emitFlow() {

}

/*
val priorityAsText = call.parameters["priority"]
            if (priorityAsText == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            try {
                val priority = Priority.valueOf(priorityAsText)
                val tasks = TaskRepository.tasksByPriority(priority)

                if (tasks.isEmpty()) {
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }

                call.respondText(
                    contentType = ContentType.parse("text/html"),
                    text = tasks.tasksAsTable()
                )
            } catch(ex: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest)
        }
    }
 */